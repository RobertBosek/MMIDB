import java.sql.*;

class DBAdministration {
    private Statement SQLStatement;
    public Connection connection;
    private ResultSet response;


    public DBAdministration(String url) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url);
            SQLStatement = connection.createStatement();
        }
        catch (ClassNotFoundException e) {
            System.err.print("Klasse nicht gefunden.");
        }
        catch (SQLException e) {
            System.err.print("SQL -Ausnahme: ");
            System.err.println(e.getMessage());
        }
        catch (Exception e) {
            System.err.print("Ein -/Ausgabefehler");
        }
    }

    public String getAssignment(String driverID) {
        try {
            String getLocation = "SELECT id_location FROM locations WHERE id_driver=" + driverID;
            response = SQLStatement.executeQuery(getLocation);
            response.next();
            String locationID = response.getString(1);
            String assignmentID = getClosestAssignment(locationID);
            if (assignmentID != null) {
                String updateAssigned = "INSERT INTO assigned VALUES(" + assignmentID + ", " + driverID + ")";
                SQLStatement.execute(updateAssigned);
                String updateAssignments = "UPDATE assignments SET id_driver=" + driverID + " WHERE id_assignment =" + assignmentID;
                SQLStatement.execute(updateAssignments);
                return assignmentID;
            } else {
                return null;
            }
        }
        catch (SQLException e) {
            System.err.print("getAssignment SQL-Ausnahme: ");
            System.err.println(e.getMessage());
            return null;
        }
    }

    private String getClosestAssignment(String locationID) {
        try {
            String getLocationAddress = "SELECT street, avenue FROM address WHERE id_address =" + locationID;
            response = SQLStatement.executeQuery(getLocationAddress);
            response.next();
            int lStreet = response.getInt("street");
            int lAvenue = response.getInt("avenue");
            String getAssignments = "SELECT assignments.id_assignment, address.street, address.avenue FROM assignments, origin, address WHERE assignments.done='N'" +
                    "AND assignments.id_driver=null AND assignments.id_assignment = origin.id_assignment AND origin.id_address = address.id_address";
            response = SQLStatement.executeQuery(getAssignments);
            String closestAssignment = null;
            int distance = -1;
            while(response.next()) {
                int aStreet = response.getInt("street");
                int aAvenue = response.getInt("avenue");
                int currentDist = Math.abs(lStreet - aStreet) + Math.abs(lAvenue - aAvenue);
                if (distance == -1 || distance > currentDist) {
                    closestAssignment = response.getString("id_assignment");
                    distance = currentDist;
                }
            }
            return closestAssignment;
        }
        catch (SQLException e) {
            System.err.print("getClosest SQL-Ausnahme: ");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public String assignmentDone (String driverID) {
        try {
            String getAssignmentID = "SELECT id_assignment FROM assignments WHERE id_driver=" + driverID;
            response = SQLStatement.executeQuery(getAssignmentID);
            response.next();
            String assignmentID = response.getString("id_assignment");
            String updateAssignment = "UPDATE assignments SET done='Y' WHERE id_assignment=" + assignmentID;
            SQLStatement.execute(updateAssignment);
            String getDestinationID = "SELECT id_address FROM destination WHERE id_assignment=" + assignmentID;
            response = SQLStatement.executeQuery(getDestinationID);
            response.next();
            String destinationID = response.getString(1);
            setLocation(driverID, destinationID);
            String deleteAssignment = "DELETE FROM assigned WHERE id_assignment=" + assignmentID;
            SQLStatement.execute(deleteAssignment);
            String deleteDest = "DELETE FROM destination WHERE id_assignment=" + assignmentID;
            SQLStatement.execute(deleteDest);
            String deleteOrig = "DELETE FROM origin WHERE id_assignment=" + assignmentID;
            SQLStatement.execute(deleteOrig);
            return "successful";
        }
        catch (SQLException e) {
            System.err.print("assignmentDone SQL-Ausnahme: ");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public String updateDriver(String id, String[] name) {
        try {
            String insertDriver = "UPDATE drivers SET prename='" + name[0] + "', surname='" + name[1] + "' WHERE id_driver=" + id;
            SQLStatement.execute(insertDriver);
            return "successful";
        }
        catch (SQLException e) {
            System.err.print("updateDriver SQL-Ausnahme: ");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public String insertDriver(String[] name, String[] home) {
        try {
            String insertDriver = "INSERT INTO drivers(prename, surname) VALUES ('" + name[0] + "', '" + name[1] + "')";
            SQLStatement.executeUpdate(insertDriver, Statement.RETURN_GENERATED_KEYS);
            response = SQLStatement.getGeneratedKeys();
            response.next();
            String driverID = String.valueOf(response.getInt(1));
            String locationID = getAdressID(home);
            setLocation(driverID, locationID);
            return driverID;
        }
        catch (SQLException e) {
            System.err.print("insertDriver SQL-Ausnahme: ");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void setLocation(String driverID, String locationID) {
        try {
            String checkDriver = "SELECT * FROM locations WHERE id_driver=" + driverID;
            response = SQLStatement.executeQuery(checkDriver);
            if(response.next()) {
                String updateLocation = "UPDATE locations SET id_address=" + locationID + " WHERE id_driver =" + driverID;
                SQLStatement.execute(updateLocation);
            } else {
                String insertLocation = "INSERT INTO locations VALUES(" + driverID + ", " + locationID + ")";
                SQLStatement.execute(insertLocation);
            }
        }
        catch (SQLException e) {
            System.err.print("settDriverLocation SQL-Ausnahme: ");
            System.err.println(e.getMessage());
        }
    }

    public String insertJob(String[] origin, String[] destination) {
        try {
            String originID = getAdressID(origin);
            String destinationID = getAdressID(destination);
            long now = System.currentTimeMillis();
            String insertJob = "INSERT INTO assignments(done, id_driver, time) VALUES ('N', null, " + now + ")";
            SQLStatement.executeUpdate(insertJob, Statement.RETURN_GENERATED_KEYS);
            response = SQLStatement.getGeneratedKeys();
            response.next();
            String jobID = String.valueOf(response.getInt(1));
            SQLStatement.execute("INSERT INTO origin VALUES(" + jobID + ", " + originID + ")");
            SQLStatement.execute("INSERT INTO destination VALUES(" + jobID + ", " + destinationID + ")");
            return jobID;
        }
        catch (SQLException e) {
            System.err.print("insertJob SQL-Ausnahme: ");
            System.err.println(e.getMessage());
            return null;
        }
    }

    private String getAdressID (String[] values) {
        try {
            String getAddressID = "SELECT id_address FROM address WHERE street=" + values[0] + " AND avenue =" + values[1];
            response = SQLStatement.executeQuery(getAddressID);
            if (!response.next()) {
                String insertAddress = "INSERT INTO address(street, avenue) VALUES(" + values[0]+ ", " + values[1] + ")";
                SQLStatement.execute(insertAddress);
                response = SQLStatement.executeQuery(getAddressID);
                response.next();
            }
            return response.getString("id_address");
        }
        catch (SQLException e) {
            System.err.print("getAdress SQL-Ausnahme: ");
            System.err.println(e.getMessage());
            return null;
        }
    }
}

