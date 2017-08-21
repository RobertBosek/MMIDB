import java.sql.SQLException;

public class Main {
    @SuppressWarnings("unused")
	public static void main (String[] args) {
        int[] home = {50,50};
        String url = "jdbc:mysql://132.199.139.24:3306/mmdb17_robertbosek?user=r.bosek&password=mmdb";
        DBAdministration db = new DBAdministration(url, home);
        int[] o = {25,4};
        int[] d = {3,17};
        String[] name = {"Rob", "Stark"};
        String[] uname = {"Ed", "Stark"};
        System.out.println(db.getJob(3));
        System.out.println(db.finishedJob(13));
        try {
            db.connection.close();
        }
        catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }
}
