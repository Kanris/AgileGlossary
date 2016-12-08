import java.sql.*;
import javax.swing.*;

public class DBHandler {

	private static Connection conn = null;
	
	private static Connection connectToDB() {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:\\Developing\\ALM\\workspace\\AgileGlossary\\Structure\\Data\\dbAgileGlossary.sqlite");
			
			//JOptionPane.showMessageDialog(null, "Connection Successful");
			return conn;
			
		} catch (Exception e) { //can't connect to the AgileGlossary database
			
			showErrorMessage(e);
			return null;
			
		}
		
	}
	
	private static void showErrorMessage(Exception e) {
		
		JOptionPane.showMessageDialog(null, e);
		
	}
	
	public static ResultSet getTermList() {
		
		try {
			
			conn = connectToDB();
			
			String query = "SELECT * from Glossary";
			PreparedStatement pst = conn.prepareStatement(query);
			
			ResultSet queryResult = pst.executeQuery();
			
			return queryResult;
			
		} catch (Exception e) {
			
			showErrorMessage(e);
			return null;
			
		}
		 
		
	}
	
}
