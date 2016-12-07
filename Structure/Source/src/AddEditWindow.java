import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddEditWindow extends JFrame {

	private JPanel contentPane; //main content pane
	private JTextField txtName; //name edit field
	private JTextField txtTranscription; //transcription edit field
	private JEditorPane txtDescription; //description edit field
	private MainWindow.WindowType type; //this window type
	
	private static final String ADD_WINDOW_TITLE = "Add new Term"; //window title for ADD operation
	private static final String EDIT_WINDOW_TITLE = "Edit Term - "; //window title for EDIT operation

	/**
	 * Create the frame.
	 */
	public AddEditWindow(MainWindow.WindowType type) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 416, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeWindow(false);
			}
		});		
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		lblName.setBounds(12, 11, 53, 14);
		contentPane.add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		txtName.setBounds(12, 36, 174, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblTranscription = new JLabel("Transcription");
		lblTranscription.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		lblTranscription.setBounds(212, 11, 84, 14);
		contentPane.add(lblTranscription);
		
		txtTranscription = new JTextField();
		txtTranscription.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		txtTranscription.setBounds(212, 36, 186, 20);
		contentPane.add(txtTranscription);
		txtTranscription.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		lblDescription.setBounds(12, 73, 77, 14);
		contentPane.add(lblDescription);
		
		txtDescription = new JEditorPane();
		txtDescription.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		txtDescription.setBounds(12, 98, 174, 162);
		contentPane.add(txtDescription);
		
		JPanel btnsPanel = new JPanel();
		btnsPanel.setBorder(UIManager.getBorder("TextField.border"));
		btnsPanel.setBounds(212, 98, 186, 162);
		contentPane.add(btnsPanel);
		
		JButton btnAdd = new JButton("Save");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AddEditClick();
								
			}
		});
		btnAdd.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		btnAdd.setBounds(314, 280, 84, 23);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		btnDelete.setBounds(212, 280, 84, 23);
		contentPane.add(btnDelete);
	
		setWindowType(type, btnAdd, btnDelete);
	}
	
	private void setWindowType(MainWindow.WindowType type, JButton btnAdd, JButton btnDelete) {
		
		if (type == MainWindow.WindowType.ADD) {
			
			btnAdd.setText("Add");
			btnDelete.setVisible(false);
			setTitle(ADD_WINDOW_TITLE);
			
		} else {
			
			setTitle(EDIT_WINDOW_TITLE);
			
		}
		
		this.type = type;
		
	}
	
	private void closeWindow(boolean updateFrame) {
		dispose();
		MainWindow mainWindow = new MainWindow();
		mainWindow.frmAgileGlossary.setVisible(true);
	}
	
	private void AddEditClick() {
		
		String name = txtName.getText();
		String transcription = txtTranscription.getText();
		String description = txtDescription.getText();
										
		if (hasValues(name, transcription, description)) {
			
			Term term = getTerm(name, transcription, description);
			JOptionPane.showMessageDialog(this, term);
			
			if (type == MainWindow.WindowType.ADD) {
				
				
				closeWindow(true);
				
			} else if (type == MainWindow.WindowType.EDIT) {
				
				
				closeWindow(true);
			}
			
		}
		
	}
	
	private Term getTerm(String name, String transcription, String description) {
		
		Term newTerm = new Term(name.trim(), transcription.trim(), description.trim());
		
		return newTerm;
		
	}
	
	private boolean hasValues(String name, String transcription, String description) {
		
		if (name.isEmpty()) return false;
		
		if (transcription.isEmpty()) return false;
		
		if (description.isEmpty()) return false;
		
		return true;
		
	}

}
