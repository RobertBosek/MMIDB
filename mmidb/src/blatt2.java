import java.sql.*;
import java.time.*;
import java.time.format.*;

class blatt2 {
    public static void main (String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(System.currentTimeMillis());
    }
    public blatt2(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://132.199.139.24:3306/mmdb17_robertbosek?user=r.bosek&password=mmdb";
            Connection Verbindung = DriverManager.getConnection(url);
            Statement SQLStatement = Verbindung.createStatement();
            String getAdressID = "SELECT id FROM Adresse WHERE Street=" + args[0] + " AND Avenue =" + args[1];
            ResultSet respond = SQLStatement.executeQuery(getAdressID);
            if (!respond.next()) {
                String insertAdress = "INSERT INTO Adresse(Street, Avenue) VALUES(" + args[0]+ ", " + args[1] + ")";
                SQLStatement.execute(insertAdress);
                respond = SQLStatement.executeQuery(getAdressID);
                respond.next();
            }
            System.out.println(respond.getString("id"));
            Verbindung.close();
        }
        catch (ClassNotFoundException e) {
            System.err.print("Klasse nicht gefunden.");
        }
        catch (SQLException e) {
            System.err.print("SQL-Ausnahme: ");
            System.err.println(e.getMessage());
        }
        catch (Exception e) {
            System.err.print("Ein-/Ausgabefehler");
        }
    }

}
