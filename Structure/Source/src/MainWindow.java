import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;

import javafx.scene.input.MouseEvent;
import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class MainWindow {

	public JFrame frmAgileGlossary;
	private JTable tblTermList;
	private JTextField textField;
		
	public static enum WindowType { ADD, EDIT };
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmAgileGlossary.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAgileGlossary = new JFrame();
		frmAgileGlossary.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		frmAgileGlossary.setTitle("Agile Glossary");
		frmAgileGlossary.setBounds(100, 100, 450, 300);
		frmAgileGlossary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAgileGlossary.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 412, 211);
		frmAgileGlossary.getContentPane().add(scrollPane);
		
		tblTermList = new JTable() {
			
			static final long serialVersionUID = 1;
			
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
	            return false;
	         }
			
		};
		tblTermList.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		tblTermList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
	                JTable target = (JTable) e.getSource();
	                int row = target.getSelectedRow();
	               
	                int fieldsCount = target.getColumnCount();   
	                String[] termFields = new String[fieldsCount];
	                for (int i = 0; i < fieldsCount; ++i) {
	                	
	                	termFields[i] = target.getValueAt(row, i).toString();
	                	
	                }
	                Term newTerm = getTerm(termFields);
	                
	                showAddEditWindow(WindowType.EDIT, newTerm);
	             }
			}
	       });
		scrollPane.setViewportView(tblTermList);
		fillJTable();
		renameJTableColumns();
		
		JButton button = new JButton("Add");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAddEditWindow(WindowType.ADD, null);
			}
		});
		button.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		button.setBounds(12, 227, 76, 23);
		frmAgileGlossary.getContentPane().add(button);
		
		textField = new JTextField();
		textField.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		textField.setText("Search");
		textField.setColumns(10);
		textField.setBounds(99, 228, 323, 22);
		frmAgileGlossary.getContentPane().add(textField);
	}
	
	private void showAddEditWindow(WindowType type, Term newTerm) {
		
		frmAgileGlossary.dispose();
		AddEditWindow addWindow = null;
		
		if (type == WindowType.ADD) {
			
			addWindow = new AddEditWindow(type, null);
			
		} else {
			
			addWindow = new AddEditWindow(type, newTerm);
			
		}
		
		addWindow.setVisible(true);
		
	}
	
	private void fillJTable() {
		
		ResultSet termList = DBHandler.getTermList();
		
		tblTermList.setModel(DbUtils.resultSetToTableModel(termList));
		
		removeIDColumn();
		
	}
	
	private void removeIDColumn() {
		
		TableColumnModel tcm = tblTermList.getColumnModel();
		tcm.removeColumn(tcm.getColumn(0));
		
	}
	
	private void renameJTableColumns() {
		
		String[] columnHeaders = new String[] { "Term", "Transcription", "Description" };
		final int columnNumber = tblTermList.getColumnCount();
		
		for (int i = 0; i < columnNumber; ++i) {
			
			renameJTableColumn(i, columnHeaders[i]);
			
		}
		
		tblTermList.getTableHeader().repaint();
		
	}
	
	private void renameJTableColumn(int columnID, String columnHeader) {
		
		tblTermList.getColumnModel().getColumn(columnID).setHeaderValue(columnHeader);
		
	}
	
	private Term getTerm(String[] termFields) {
		
		Term newTerm = new Term(termFields[0].trim(), termFields[1].trim(), termFields[2].trim());
		
		return newTerm;
		
	}

}
