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
	
	private static void createConnection() {
		
		if (conn == null)
			conn = connectToDB();
		
	}
	
	private static void showErrorMessage(Exception e) {
		
		JOptionPane.showMessageDialog(null, e);
		
	}
	
	public static ResultSet getTermList() {
		
		try {
			
			createConnection();
			
			String query = "SELECT * from Glossary";
			PreparedStatement pst = conn.prepareStatement(query);
			
			ResultSet queryResult = pst.executeQuery();
			
			return queryResult;
			
		} catch (Exception e) {
			
			showErrorMessage(e);
			return null;
			
		}
		 
		
	}
	
	public static void addNewTerm(Term newTerm) {
		
		try {
			
			createConnection();
			
			String query = "INSERT INTO Glossary(term, transcription, description) VALUES(?,?,?)";		
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, newTerm.getName());
			pst.setString(2, newTerm.getTranscription());
			pst.setString(3, newTerm.getDescription());
			
			pst.execute();
			
		} catch (Exception e) {
			
			showErrorMessage(e);

		}
		
	}
	
	public static void deleteTerm(String term) {
		
		try {
			
			createConnection();
			
			String query = "DELETE FROM Glossary WHERE term = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, term);
			
			pst.execute();
			
		} catch (Exception e) {
			
			showErrorMessage(e);
			
		}
		
	}
	
	
	
}
