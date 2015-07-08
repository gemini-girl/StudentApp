import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dbConn {
	public Connection c;
	
	public void connect(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "one", "two");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		dbConn db = new dbConn();
		db.connect();
	}

}
