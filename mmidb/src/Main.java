import java.sql.SQLException;

public class Main {
    public static void main (String[] args) {
        int[] home = {50,50};
        String url = "jdbc:mysql://132.199.139.24:3306/mmdb17_robertbosek?user=r.bosek&password=mmdb";
        DBAdministration db = new DBAdministration(url, home);
        String[] o = {"1","1"};
        String[] d = {"5","7"};
        String[] name = {"Hansi", "Bauer"};
        System.out.println(db.finishedJob(3));
        try {
            db.connection.close();
        }
        catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }
}
