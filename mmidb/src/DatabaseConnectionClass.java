import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
 
public class DatabaseConnectionClass {
   
     public static void main (String[] args) {
     new DatabaseConnectionClass(args);
     }
     public DatabaseConnectionClass(String[] args) {
     try {
     Class.forName("com.mysql.jdbc.Driver").newInstance();
     String url = "jdbc:mysql://132.199.139.24:3306/mmdb17_robertbosek?user=r.bosek&password=mmdb";
     Connection Verbindung = DriverManager.getConnection(url);
     Statement SQLStatement = Verbindung.createStatement();
     if (args.length == 3) {
     String SQLString = new String("INSERT INTO drivers VALUES " + "(" + args[0] + ",'" + args[1] + "'," + args[2] + ");");
     SQLStatement.executeUpdate(SQLString);
     }
     String SQLString = new String("SELECT * FROM drivers");
     ResultSet Ergebnis = SQLStatement.executeQuery(SQLString);
     while(Ergebnis.next()) {
     System.out.println(Ergebnis.getString("prename") + " " + Ergebnis.getString("id_driver"));
     }
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