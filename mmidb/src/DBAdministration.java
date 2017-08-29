import java.sql.*;

class DBAdministration {
    private int[] home = {10,10};
    private Statement statement;
    private ResultSet response;
    private int homeID;
    private Connection connection;
    
public DBAdministration(String url) {
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url);
        statement = connection.createStatement();
        homeID = getAddressID(home);
        }
    catch (SQLException e) {
        System.err.print("SQL -Ausnahme: ");
        System.err.println(e.getMessage());
        }
    catch (Exception e) {
        System.err.print("Ein -/Ausgabefehler");
        }
    }

public String insertJob(int[] origin, int[] destination) {
    try {
        int originID = getAddressID(origin);
        int destinationID = getAddressID(destination);
        if (originID != -1 && destinationID != -1) {
            long now = System.currentTimeMillis();
            statement.executeUpdate("INSERT INTO assignments(done, id_driver, time) VALUES ('N', -1, " + now + ")",
                    Statement.RETURN_GENERATED_KEYS);
            response = statement.getGeneratedKeys();
            response.next();
            String jobID = response.getString(1);
            statement.execute("INSERT INTO origin VALUES(" + jobID + ", " + originID + ")");
            statement.execute("INSERT INTO destination VALUES(" + jobID + ", " + destinationID + ")");
            return "Auftrag erfolgreich erstellt - Auftragsnr. " + jobID;
        } else {
            return null;
        }
    }
    catch (SQLException e) {
        System.err.print("insertJob SQL-Ausnahme: ");
        System.err.println(e.getMessage());
        return null;
    }
}

public String insertDriver(String[] name) {
    try {
        statement.executeUpdate("INSERT INTO drivers(prename, surname) VALUES ('" + name[0] + "', '" + name[1] + "')",
                Statement.RETURN_GENERATED_KEYS);
        response = statement.getGeneratedKeys();
        response.next();
        int driverID = response.getInt(1);
        response = statement.executeQuery("SELECT * FROM locations WHERE id_driver=" + driverID);
        if(response.next()) {
            statement.execute("UPDATE locations SET id_address=" + homeID + " WHERE id_driver =" + driverID);
        } else {
            statement.execute("INSERT INTO locations VALUES(" + driverID + ", " + homeID + ")");
        }
        return "Fahrer wurde mit Personalnummer " + driverID + " erstellt.";
    }
    catch (SQLException e) {
        System.err.print("insertDriver SQL-Ausnahme: ");
        System.err.println(e.getMessage());
        return null;
    }
}

public String updateDriver(int driverID, String[] name) {
    try {
        response = statement.executeQuery("SELECT id_driver FROM drivers WHERE id_driver=" + driverID);
        if (response.next()) {
            statement.execute("UPDATE drivers SET prename='" + name[0] + "', surname='" + name[1] + "' WHERE id_driver=" + driverID);
            return "Fahrer " + driverID + " erfolgreich geändert.";
        } else {
            return "Fahrernummer " + driverID + " nicht vorhanden.";
        }
    }
    catch (SQLException e) {
        System.err.print("updateDriver SQL-Ausnahme: ");
        System.err.println(e.getMessage());
        return null;
    }
}

public String getJob(int driverID) {
    try {
        response = statement.executeQuery("SELECT id_driver FROM assigned WHERE id_driver=" + driverID);
        if (!response.next()) {
            response = statement.executeQuery("SELECT id_address FROM locations WHERE id_driver=" + driverID);
            response.next();
            int locationID = response.getInt(1);
            int[] closestJob = getClosestJob(locationID);
            if (closestJob != null) {
                statement.execute("INSERT INTO assigned VALUES(" + closestJob[0] + ", " + driverID + ")");
                statement.execute("UPDATE assignments SET id_driver=" + driverID + " WHERE id_assignment =" + closestJob[0]);
                String jobInfo = "ID=" + closestJob[0] + " von " + closestJob[1] + "St./" + closestJob[2] + "Av., nach " + closestJob[3] + "St./" + closestJob[4] + "Av.";
                return jobInfo;
            } else {
                return "Derzeit keine neuen Aufträge vorhanden.";
            }
        } else {
            return "Fahrer " + driverID + " bearbeitet bereits einen Auftrag.";
        }
    }
    catch (SQLException e) {
        System.err.print("getJob SQL-Ausnahme: ");
        System.err.println(e.getMessage());
        return null;
    }
}

public String finishedJob (int driverID) {
    try {
        response = statement.executeQuery("SELECT id_driver FROM assigned WHERE id_driver=" + driverID);
        if (response.next()) {
            response = statement.executeQuery("SELECT id_assignment FROM assignments WHERE id_driver=" + driverID + " AND done= 'N'");
            response.next();
            int assignmentID = response.getInt(1);
            statement.execute("UPDATE assignments SET done='Y' WHERE id_assignment=" + assignmentID);
            statement.execute("DELETE FROM assigned WHERE id_assignment=" + assignmentID);
            response = statement.executeQuery("SELECT id_address FROM destination WHERE id_assignment=" + assignmentID);
            response.next();
            int addressID = response.getInt(1);
            statement.execute("UPDATE locations SET id_address=" + addressID + " WHERE id_driver=" + driverID);
            return "Auftrag " + assignmentID + " erfolgreich bageschlossen.";
        } else {
            return "Fahrer " + driverID + " bearbeitet derzeit keinen Auftrag.";
        }
    }
    catch (SQLException e) {
        System.err.print("finishedJob SQL-Ausnahme: ");
        System.err.println(e.getMessage());
        return null;
    }
}

private int[] getClosestJob(int currentLocationID) {
    try {
        response = statement.executeQuery("SELECT street, avenue FROM address WHERE id_address =" + currentLocationID);
        response.next();
        int locationStreet = response.getInt("street");
        int locationAvenue = response.getInt("avenue");
        response = statement.executeQuery("SELECT assignments.id_assignment, address.street, address.avenue " +
                "FROM assignments, origin, address WHERE assignments.done='N' AND assignments.id_driver=-1 " +
                "AND assignments.id_assignment = origin.id_assignment AND origin.id_address = address.id_address");
        int[] closestJob = {-1, -1, -1, -1, -1};
        int distance = -1;
        while(response.next()) {
            int jobStreet = response.getInt("street");
            int jobAvenue = response.getInt("avenue");
            int currentDist = Math.abs(locationStreet - jobStreet) + Math.abs(locationAvenue - jobAvenue);
            if (distance == -1 || distance > currentDist) {
                closestJob[0] = response.getInt("id_assignment");
                distance = currentDist;
                closestJob[1] = jobStreet;
                closestJob[2] = jobAvenue;
            }
        }
        response = statement.executeQuery("SELECT address.street, address.avenue FROM destination, address " +
                "WHERE destination.id_address = address.id_address AND destination.id_assignment=" + closestJob[0]);
        response.next();
        closestJob[3] = response.getInt("street");
        closestJob[4] = response.getInt(("avenue"));
        return closestJob;
    }
    catch (SQLException e) {
        System.err.print("getClosest SQL-Ausnahme: ");
        System.err.println(e.getMessage());
        return null;
    }
}

public int getAddressID (int[] values) {
    try {
        String getAddressIDStr = "SELECT id_address FROM address WHERE street=" + values[0] + " AND avenue=" + values[1];
        response = statement.executeQuery(getAddressIDStr);
        if (!response.next()) {
            statement.execute("INSERT INTO address(street, avenue) VALUES(" + values[0]+ ", " + values[1] + ")");
            response = statement.executeQuery(getAddressIDStr);
            response.next();
        }
        return response.getInt("id_address");
    }
    catch (SQLException e) {
        System.err.print("getAddress SQL-Ausnahme: ");
        System.err.println(e.getMessage());
        return -1;
    }
}
}