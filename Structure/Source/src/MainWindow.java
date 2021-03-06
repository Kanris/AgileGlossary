import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 * <p>This class creates MainWindow JFrame that allows to Add new term, Search term, edit needed term and display list of terms</p>
 * @author Alexey Hlukhov
 * @version 1.0
 * @see Term
 * @see DBHandler
 * @see AddEditWindow
 */
public class MainWindow {

	public JFrame frmAgileGlossary; //main jframe
	private JTable tblTermList; //list of terms
	private JTextField txtSearch; //search text field
		
	/**
	 * Describes AddEditWindow frame modes
	 * @author Alexey
	 *
	 */
	public static enum WindowType { 
		/**
		 * Add AddEditWindow type
		 */
		ADD, 
		/**
		 * Edit AddEditWindow type
		 */
		EDIT 
		}; //AddEdit window types
	
	/**
	 * <p>Application Entry Point.</p>
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow(); //Creates MainWindow object
					window.frmAgileGlossary.setVisible(true); //Displays MainWindow
				} catch (Exception e) { //Can't create/display MainWindow
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * <p>Create the MainWindow JFrame</p>.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * <p>Initialize the contents of the frame.</p>
	 */
	private void initialize() {
		frmAgileGlossary = new JFrame(); //Creates Main JFrame
		frmAgileGlossary.setResizable(false);
		frmAgileGlossary.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		frmAgileGlossary.setTitle("Agile Glossary");
		frmAgileGlossary.setBounds(100, 100, 575, 418);
		frmAgileGlossary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAgileGlossary.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(); //Add scrollpanel to JTable
		scrollPane.setBounds(10, 11, 539, 323);
		frmAgileGlossary.getContentPane().add(scrollPane);
		
		tblTermList = new JTable() { //create JTable (list of terms)
			
			static final long serialVersionUID = 1;
			
			public boolean editCellAt(int row, int column, java.util.EventObject e) { //limits the ability to change the values in the cells
	            return false;
	         }
			
		};
		tblTermList.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		tblTermList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) { //open AddEditWindow in Edit mode if user double clicked on JTable row
				if (e.getClickCount() == 2) { //if double left click
	                JTable target = (JTable) e.getSource();
	                int row = target.getSelectedRow(); //get selected row
	               
	                int fieldsCount = target.getColumnCount(); //get columns count (needed for creating array and for loop
	                String[] termFields = new String[fieldsCount]; //term fields
	                for (int i = 0; i < fieldsCount; ++i) { //get all Term fields from selected row
	                	
	                	termFields[i] = target.getValueAt(row, i).toString();
	                	
	                }
	                Term newTerm = getTerm(termFields); //creates Term from fields
	                
	                showAddEditWindow(WindowType.EDIT, newTerm); //run AddEditWindow in edit mode
	             }
			}
	       });
		scrollPane.setViewportView(tblTermList);
		fillJTable();
		renameJTableColumns();
		
		JButton btnAdd = new JButton("Add..."); //created add button
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAddEditWindow(WindowType.ADD, null);
			}
		});
		btnAdd.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		btnAdd.setBounds(473, 345, 76, 23);
		frmAgileGlossary.getContentPane().add(btnAdd);
		
		txtSearch = new JTextField(); //creates search text field
		txtSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtSearch.setText("");
			}
		});
		txtSearch.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		txtSearch.setColumns(10);
		txtSearch.setBounds(53, 345, 410, 22);
		txtSearch.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) { //on text delete
				// TODO Auto-generated method stub
				search();
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) { //on text insert
				// TODO Auto-generated method stub
				search();
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) { //on text change
				// TODO Auto-generated method stub
				search();
			}
			
			/**
			 * <p>Search in list of terms (JTable) logic</p>
			 */
			 public void search() {
			     
				 String searchText = txtSearch.getText().trim(); //get text from search text field without white space
			     
			     TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(((DefaultTableModel) tblTermList.getModel())); //create list of source (JTable) sorter
				 sorter.setRowFilter(RowFilter.regexFilter(searchText)); //add regex filter

				 tblTermList.setRowSorter(sorter); //display search result
			  }
			 
		});
		
		frmAgileGlossary.getContentPane().add(txtSearch);
		
		JLabel label = new JLabel("Search");
		label.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		label.setBounds(10, 350, 33, 14);
		frmAgileGlossary.getContentPane().add(label);
		
		centerFrame(frmAgileGlossary);
	}
	
	/**
	 * <p>Place MainWindow frame in the center of the screen</p>
	 * @param frame Frame that will be placed in the center of the screen
	 */
	public static void centerFrame(Window frame) {
	    frame.setLocationRelativeTo(null);
	}
	
	/**
	 * <p>Displays AddEditWindow in specific mode (Add or Edit).</p>
	 * @param type    Needed AddEditWindow mode (WindowType.ADD - creates AddEditWindow in Add mode, WindowType.EDIT - creates AddEditWindow in Edit mode)
	 * @param newTerm Term for editing (If WindowType.Add, newTerm will be equals to null)
	 */
	private void showAddEditWindow(WindowType type, Term newTerm) {
		
		frmAgileGlossary.dispose(); //hide this JFrame (MainWindow)
		AddEditWindow addWindow = null;
		
		if (type == WindowType.ADD) { //If needed to creates AddEditWindow in Add mode
			
			addWindow = new AddEditWindow(type, null); //Creates AddEditWindow in Add mode
			
		} else { //If needed to creates AddEditWindow in Edit mode
			
			addWindow = new AddEditWindow(type, newTerm); //Creates AddEditWindow in Edit mode
			
		}
		
		addWindow.setVisible(true); //Displays AddEditWindow
		
	}
	
	/**
	 * <p>Gets list of terms from database and displays it in JTable</p>
	 */
	private void fillJTable() {
		
		ResultSet termList = DBHandler.getTermList(); //get list of terms
		
		tblTermList.setModel(DbUtils.resultSetToTableModel(termList)); //display list of terms
		
	}

	
	/**
	 * <p>Rename JTable columns.</p>
	 */
	private void renameJTableColumns() {
		
		String[] columnHeaders = new String[] { "Term", "Transcription", "Description" }; //Needed columns headers
		final int columnNumber = tblTermList.getColumnCount(); //JTable column count
		
		for (int i = 0; i < columnNumber; ++i) { //Add headers to the JTable
			
			renameJTableColumn(i, columnHeaders[i]);
			
		}
		
		tblTermList.getTableHeader().repaint(); //Displays new column headers
		
	}
	
	/**
	 * <p>Sets JTable header for specific column.</p>
	 * @param columnID     needed column id for rename
	 * @param columnHeader needed column header
	 */
	private void renameJTableColumn(int columnID, String columnHeader) {
		
		tblTermList.getColumnModel().getColumn(columnID).setHeaderValue(columnHeader);
		
	}
	
	/**
	 * <p>Creates Term object from term fields (name, transcription, description).</p>
	 * @param  termFields term fields (name, transcription, description) in the form of String array that needs to create Term object 
	 * @return            Term object
	 */
	private Term getTerm(String[] termFields) {
		
		Term newTerm = new Term(termFields[0].trim(), termFields[1].trim(), termFields[2].trim());
		
		return newTerm;
		
	}
}
