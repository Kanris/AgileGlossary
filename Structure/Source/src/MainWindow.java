import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
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
		
		tblTermList = new JTable();
		tblTermList.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		scrollPane.setViewportView(tblTermList);
		
		JButton button = new JButton("Add");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addClick();
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
	
	private void addClick() {
		
		frmAgileGlossary.dispose();
		AddEditWindow addWindow = new AddEditWindow(WindowType.ADD);
		addWindow.setVisible(true);
		
	}

}
