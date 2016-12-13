import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import javax.swing.*;

/**
 * <p>This class consists exclusively of methods to work with the database.</p>
 * @author Alexey Hlukhov
 * @version 1.0
 * @see Term
 */
public class DBHandler {

	private static Connection conn = null; //connection to the database
	
	/**
	 * <p>Returns a connection to the database.</p>
	 * @return conn if connection to the database is established or null if is not established
	 * @throws Exception if connection to the database wasn't established
	 * @since 1.0
	 */
	private static Connection connectToDB() {
		
		try {
			
			Class.forName("org.sqlite.JDBC"); //class that allows to establish connection to SQLite database
			//Path currentRelativePath = Paths.get(""); //path to the current directory
			//String dbPath = currentRelativePath.toAbsolutePath().toString() + "\\dbAgileGlossary.sqlite"; //path to the database near the jar file
			String dbPath = "D:\\Developing\\ALM\\workspace\\AgileGlossary\\Structure\\Data\\dbAgileGlossary.sqlite"; //path to the database in project structure
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath); //establish connection to the database
			
			return conn; //return connection to the database
			
		} catch (Exception e) { //can't connect to the AgileGlossary database
			
			showErrorMessage(e); //show error message if connection can't be established
			return null;
			
		}
		
	}
	
	/**
	 * <p>Establish connection if it is not established.</p>
	 * @since 1.0
	 */
	private static void createConnection() {
		
		if (conn == null)
			conn = connectToDB();
		
	}
	
	/**
	 * <p>Creates error message dialog.</p>
	 * @param e error message
	 * @since 1.0
	 */
	private static void showErrorMessage(Exception e) {
		
		JOptionPane.showMessageDialog(null, e);
		
	}
	
	/**
	 * <p>Returns a list of terms from database.</p>
	 * @return queryResult returns terms list if query was successful and null if there was some errors
	 * @throws Exception if connection to the database wasn't established
	 * @since 1.0
	 */
	public static ResultSet getTermList() {
		
		try {
			
			createConnection(); //establish connection to the database
			
			String query = "SELECT * from Glossary"; //query to get list of terms
			PreparedStatement pst = conn.prepareStatement(query); //prepare query to execute
			
			ResultSet queryResult = pst.executeQuery(); //executing of query
			
			return queryResult;
			
		} catch (Exception e) {
			
			showErrorMessage(e); //show error message if connection can't be established
			return null;
			
		}
		 
		
	}
	
	/**
	 * <p>Add new term to the database</p>
	 * @throws Exception if you add the term that already exists in the database
	 * @since 1.0
	 */
	public static void addNewTerm(Term newTerm) {
		
		try {
			
			createConnection(); //establish connection to the database
			
			String query = "INSERT INTO Glossary(term, transcription, description) VALUES(?,?,?)"; //query to add new term
			PreparedStatement pst = conn.prepareStatement(query); //prepare query to execute
			pst.setString(1, newTerm.getName()); //set name value in query
			pst.setString(2, newTerm.getTranscription()); //set transcription value in query
			pst.setString(3, newTerm.getDescription()); //set description value in query
			
			pst.execute(); //executing of query
			
		} catch (Exception e) {
			
			showErrorMessage(e); //show error message if connection can't be established

		}
		
	}
	
	/**
	 * <p>Delete term from database.</p>
	 * @param  term  The name of the term to be deleted from the database
	 * @throws Exception If connection to the database wasn't established
	 * @since  1.0
	 */
	public static void deleteTerm(String term) {
		
		try {
			
			createConnection(); //establish connection to the database
			
			String query = "DELETE FROM Glossary WHERE term = ?"; //query to delete term
			PreparedStatement pst = conn.prepareStatement(query); //prepare query to execute
			pst.setString(1, term); //set name value in query
			
			pst.execute(); //executing of query
			
		} catch (Exception e) {
			
			showErrorMessage(e); //show error message if connection can't be established
			
		}
		
	}
	
	/**
	 * <p>Allows you to update term in the database.</p>
	 * @param  term      Term with updated fields
	 * @param  oldTerm   Old term name (Necessary to search for it in the database)
	 * @throws Exception If connection to the database wasn't established
	 */
	public static void updateTerm(Term term, String oldTerm) {
		
		try {
			
			createConnection(); //establish connection to the database
			
			String query = "UPDATE Glossary SET term = ?, transcription = ?, description = ? WHERE term = ?"; //query to update term	
			PreparedStatement pst = conn.prepareStatement(query); //prepare query to execute
			pst.setString(1, term.getName()); //set name value in query
			pst.setString(2, term.getTranscription()); //set transcription value in query
			pst.setString(3, term.getDescription()); //set description value in query
			pst.setString(4, oldTerm); //set old term value in where statement
			
			pst.execute(); //executing of query
			
		} catch (Exception e) {
			
			showErrorMessage(e); //show error message if connection can't be established

		}
		
	}
	
	/**
	 * <p>Allows you to search term in the database.</p>
	 * @param  term      Term for search
	 * @return           true if term is in the database and false if it isn't in database
	 * @throws Exception If connection to the database wasn't established
	 */
	public static boolean isExist(String term) {
		
		try {
			
			createConnection(); //establish connection to the database
			
			String query = "SELECT term FROM Glossary WHERE term = ?"; //query to search term
			PreparedStatement pst = conn.prepareStatement(query); //prepare query to execute
			pst.setString(1, term); //set name value in query
			
			ResultSet rs = pst.executeQuery(); //executing of query
			
			return rs.next();
			
		} catch (Exception e) {
			
			showErrorMessage(e); //show error message if connection can't be established
			return false;
		}
		
	}
	
}
