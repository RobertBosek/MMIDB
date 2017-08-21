import java.sql.*;

class DBAdministration {
    private Statement statement;
    public ResultSet response;
    private int homeID;
    public Connection connection;

    public static void main (String[] args) {
        int[] home = {50,50};
        String url = "jdbc:mysql://132.199.139.24:3306/mmdb17_robertbosek?user=r.bosek&password=mmdb";
        DBAdministration db = new DBAdministration(url, home);
        String[] o = {"1","1"};
        String[] d = {"5","7"};
        String[] name = {"Hansi", "Bauer"};
        
        System.out.println(db.finishedJob(1));
        try {
            db.connection.close();
        }
        catch (SQLException e) {
            System.err.print(e.getMessage());
            }
        }
    
		public DBAdministration(String url, int[] home) {
			
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(url);
				statement = connection.createStatement();
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

public String insertJob(int[] origin, int[] destination) {
    try {
        int originID = getAddressID(origin);
        int destinationID = getAddressID(destination);
        long now = System.currentTimeMillis();
        statement.executeUpdate("INSERT INTO assignments(done, id_driver, time) VALUES ('N', null, " + now + ")",
                Statement.RETURN_GENERATED_KEYS);
        response = statement.getGeneratedKeys();
        response.next();
        String jobID = response.getString(1);
        statement.execute("INSERT INTO origin VALUES(" + jobID + ", " + originID + ")");
        statement.execute("INSERT INTO destination VALUES(" + jobID + ", " + destinationID + ")");
        return jobID; /* hier evtl mehr details als rückgabe?!*/
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
        setLocation(driverID, homeID);
        return String.valueOf(driverID); /*evtl mehr infos?! */
    }
    catch (SQLException e) {
        System.err.print("insertDriver SQL-Ausnahme: ");
        System.err.println(e.getMessage());
        return null;
    }
}

public String updateDriver(int id, String[] name) {
    try {
        statement.execute("UPDATE drivers SET prename='" + name[0] + "', surname='" + name[1] + "' WHERE id_driver=" + id);
        return "successful";
    }
    catch (SQLException e) {
        System.err.print("updateDriver SQL-Ausnahme: ");
        System.err.println(e.getMessage());
        return null;
    }
}

public String getJob(int driverID) {
    try {
        response = statement.executeQuery("SELECT id_address FROM locations WHERE id_driver=" + driverID);
        response.next();
        int locationID = response.getInt(1);
        int jobID = getClosestJob(locationID);
        if (jobID != -1) {
            statement.execute("INSERT INTO assigned VALUES(" + jobID + ", " + driverID + ")");
            statement.execute("UPDATE assignments SET id_driver=" + driverID + " WHERE id_assignment =" + jobID);
            return String.valueOf(jobID); /* evtl mehr infos?! */
        } else {
            return "no job found";
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
        response = statement.executeQuery("SELECT id_assignment FROM assignments WHERE id_driver=" + driverID);
         while (response.next()) {
        statement.execute("UPDATE assignments SET done='Y' WHERE id_assignment=" + response.getInt(1));
        response = statement.executeQuery("SELECT id_address FROM destination WHERE id_assignment=" + response.getInt(1));
        response.next();
        }
        return "successful";
    }
    catch (SQLException e) {
        System.err.print("finishedJob SQL-Ausnahme: ");
        System.err.println(e.getMessage());
        return null;
    }
}

private int getClosestJob(int currentLocationID) {
    try {
        response = statement.executeQuery("SELECT street, avenue FROM address WHERE id_address =" + currentLocationID);
        response.next();
        int locationStreet = response.getInt("street");
        int locationAvenue = response.getInt("avenue");
        response = statement.executeQuery("SELECT assignments.id_assignment, address.street, address.avenue " +
                "FROM assignments, origin, address WHERE assignments.done='N' AND assignments.id_driver=null " +
                "AND assignments.id_assignment = origin.id_assignment AND origin.id_address = address.id_address");
        int closestJobID = -1;
        int distance = -1;
        if(response.next()) { 
            System.out.println(response.getString("id_assignment"));
            int jobStreet = response.getInt("street");
            int jobAvenue = response.getInt("avenue");
            int currentDist = Math.abs(locationStreet - jobStreet) + Math.abs(locationAvenue - jobAvenue);
            if (distance == -1 || distance > currentDist) {
                closestJobID = response.getInt("id_assignment");
                distance = currentDist;
            }
        }
        return closestJobID;
    }
    catch (SQLException e) {
        System.err.print("getClosest SQL-Ausnahme: ");
        System.err.println(e.getMessage());
        return -1;
    }
}

private void setLocation(int driverID, int locationID) {
    try {
        response = statement.executeQuery("SELECT * FROM locations WHERE id_driver=" + driverID);
        if(response.next()) {
            statement.execute("UPDATE locations SET id_address=" + locationID + " WHERE id_driver =" + driverID);
        } else {
            statement.execute("INSERT INTO locations VALUES(" + driverID + ", " + locationID + ")");
        }
    }
    catch (SQLException e) {
        System.err.print("setDriverLocation SQL-Ausnahme: ");
        System.err.println(e.getMessage());
    }
}

public int getAddressID (int[] values) {
    try {
        String getAddressID = "SELECT id_address FROM address WHERE street=" + values[0] + " AND avenue=" + values[1];
        response = statement.executeQuery(getAddressID);
        if (!response.next()) {
            statement.execute("INSERT INTO address(street, avenue) VALUES(" + values[0]+ ", " + values[1] + ")");
            response = statement.executeQuery(getAddressID);
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