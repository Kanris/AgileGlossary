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
import javax.swing.JScrollPane;

public class AddEditWindow extends JFrame {

	/**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; //main content pane
	private JTextField txtName; //name edit field
	private JTextField txtTranscription; //transcription edit field
	private JEditorPane txtDescription; //description edit field
	private MainWindow.WindowType type; //this window type
	private JPanel btnsPanel;
	
	private static final String ADD_WINDOW_TITLE = "Add new Term"; //window title for ADD operation
	private static final String EDIT_WINDOW_TITLE = "Edit Term - "; //window title for EDIT operation
	
	private Term editTerm = null;

	/**
	 * Create the frame.
	 */
	public AddEditWindow(MainWindow.WindowType type, Term editTerm) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 434, 595);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeWindow(false);
			}
		});		
		
		JLabel lblTerm = new JLabel("Term");
		lblTerm.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		lblTerm.setBounds(12, 11, 53, 14);
		contentPane.add(lblTerm);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		txtName.setBounds(12, 36, 406, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblTranscription = new JLabel("Transcription");
		lblTranscription.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		lblTranscription.setBounds(12, 209, 84, 14);
		contentPane.add(lblTranscription);
		
		txtTranscription = new JTextField();
		txtTranscription.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		txtTranscription.setBounds(12, 229, 406, 20);
		contentPane.add(txtTranscription);
		txtTranscription.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		lblDescription.setBounds(12, 67, 77, 14);
		contentPane.add(lblDescription);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 92, 406, 106);
		contentPane.add(scrollPane);
		
		txtDescription = new JEditorPane();
		scrollPane.setViewportView(txtDescription);
		txtDescription.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		
		btnsPanel = new JPanel();
		btnsPanel.setBorder(UIManager.getBorder("TextField.border"));
		btnsPanel.setBounds(12, 260, 406, 257);
		contentPane.add(btnsPanel);
		btnsPanel.setLayout(null);
		
		JButton btnAdd = new JButton("Save");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AddEditClick();
								
			}
		});
		btnAdd.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		btnAdd.setBounds(348, 528, 70, 23);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteTerm();
			}
		});
		btnDelete.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		btnDelete.setBounds(261, 528, 77, 23);
		contentPane.add(btnDelete);
	
		setWindowType(type, btnAdd, btnDelete, editTerm);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeWindow(false);
			}
		});
		btnClose.setBounds(12, 528, 89, 23);
		contentPane.add(btnClose);
		
		createTranscriptionButtons();
	}
	
	private void createTranscriptionButtons() {
		
		String[][] transcriptionSymbols = new String[][] 
				{ 
					{ "ð", "θ", "ʧ", "ʤ", "ʒ", "ʃ", "ŋ" },
					{ "j", "w", "p", "b", "m", "f", "v" },
					{ "s", "z", "t", "d", "n", "l", "r" },
					{ "k", "g", "h", "ɪ", "ɔ", "ɑ", "ʌ" },
					{ "æ", "ə", "ə:", "i:", "e", "u", "u:" },
					{ "o:", "ju:", "ei", "ɑi", "ɑu", "ɔi", "ɔu"},
					{ "iə", "ɛə", "uə", "juə", "ɑiə", "ɑuə", "`"},
				};
		
		int width = 52;
		int height = 25;
		int y = 10;
		
		for (int i = 0; i < 7; i++, y += 34) {
			
			int x = 11;
			for (int j = 0; j < 7; j++, x += 55) {
				
				JButton newTranscBtn = new JButton(transcriptionSymbols[i][j]);
				newTranscBtn.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent arg0) {
						
						JButton thisJbutton = (JButton) arg0.getSource();
						String transcription = txtTranscription.getText() + thisJbutton.getText();
						txtTranscription.setText(transcription);
										
					}
					
				});
				newTranscBtn.setFont(new Font("Segoe UI Light", Font.PLAIN, 11));
				newTranscBtn.setBounds(x, y, width, height);
				btnsPanel.add(newTranscBtn);
			}
		}
		
	}
	
	private void setWindowType(MainWindow.WindowType type, 
			JButton btnAdd, JButton btnDelete, Term editTerm) {
		
		if (type == MainWindow.WindowType.ADD) {
			
			btnAdd.setText("Add");
			btnDelete.setVisible(false);
			setTitle(ADD_WINDOW_TITLE);
			
		} else {
			
			
			setTitle(EDIT_WINDOW_TITLE + editTerm.getName());
			txtName.setText(editTerm.getName());
			txtTranscription.setText(editTerm.getTranscription());
			txtDescription.setText(editTerm.getDescription());
			
			this.editTerm = editTerm;
			
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
			
			if (type == MainWindow.WindowType.ADD && !DBHandler.isExist(name)) {
				
				DBHandler.addNewTerm(term);
								
			} else if (type == MainWindow.WindowType.EDIT) {
				
				DBHandler.updateTerm(term, editTerm.getName());
					
			} else {
				
				JOptionPane.showMessageDialog(this, "Can't add term with this name!");
				return;
				
			}
			
			closeWindow(true);
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
	
	private void deleteTerm() {
		
		if (editTerm != null) {
			
			DBHandler.deleteTerm(editTerm.getName());
			closeWindow(true);
			
		}
		
	}
}
