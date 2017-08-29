import java.sql.SQLException;

public class Main {
    @SuppressWarnings("unused")
	public static void main (String[] args) {
        int[] home = {50,50};
        String url = "jdbc:mysql://132.199.139.24:3306/mmdb17_robertbosek?user=r.bosek&password=mmdb";
        DBAdministration db = new DBAdministration(url);
        int[] o = {10,10};
        int[] d = {3,1};
        String[] name = {"Brann", "Stark"};
        String[] uname = {"Ned", "Stark"};
        System.out.println(db.insertJob(o, d));
        }
}
