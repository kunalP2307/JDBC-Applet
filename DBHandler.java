import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBHandler {
	private static final String URL = "jdbc:mysql://localhost:3306/Cricket?useUnicode=true&characterEncoding=UTF-8";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "Nokia@2004";
	static Connection con;
	
	public static void insertEntry(int p1, int p2, String result) {
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		 LocalDateTime now = LocalDateTime.now();  
		 String ts = dtf.format(now);
		 
		try {
			System.out.println("Connecting");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			String query = "insert into Scores(time_stamp,p1_score,p2_score,winner) values(?,?,?,?)";
			java.sql.PreparedStatement pts = con.prepareStatement(query);
	    	pts.setString(1, ts);
	    	pts.setInt(2, p1);
	    	pts.setInt(3, p2);
	    	pts.setString(4, result);
	    	int count = pts.executeUpdate();
	    	System.out.println("insert count"+count);
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    System.out.println("ENDed");
	}
}
