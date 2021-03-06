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

/**
 * <p>This class creates AddEditWindow JFrame that allows to Add new term, edit or delete the selected term.</p>
 * @author  Alexey Hlukhov
 * @version 1.0
 * @see     Term
 * @see     DBHandler
 * @see     MainWindow
 */
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
	 * <p>Create the AddEditWindow frame.</p>
	 * @param type     Needed window type (WindowType.ADD - creates AddEditWindow in Add mode, WindowType.EDIT - creates AddEditWindow in Edit mode)
	 * @param editTerm The term for editing (has value of null if AddEditWindow creates in add mode)
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
				
				AddEditClick(); //Save button clicked
								
			}
		});
		btnAdd.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		btnAdd.setBounds(348, 528, 70, 23);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteTerm(); //Delte button clicked
			}
		});
		btnDelete.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		btnDelete.setBounds(261, 528, 77, 23);
		contentPane.add(btnDelete);
	
		setWindowType(type, btnAdd, btnDelete, editTerm); //set needed AddEditWindow type
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeWindow(false);
			}
		});
		btnClose.setBounds(12, 528, 89, 23);
		contentPane.add(btnClose);
		
		createTranscriptionButtons(); //creates transcription buttons
	}
	
	/**
	 * <p>Creates transcription buttons in AddEditWindow</p>
	 */
	private void createTranscriptionButtons() {
		
		String[][] transcriptionSymbols = new String[][] //needed transcription buttons
				{ 
					{ "ð", "θ", "ʧ", "ʤ", "ʒ", "ʃ", "ŋ" },
					{ "j", "w", "p", "b", "m", "f", "v" },
					{ "s", "z", "t", "d", "n", "l", "r" },
					{ "k", "g", "h", "ɪ", "ɔ", "ɑ", "ʌ" },
					{ "æ", "ə", "ə:", "i:", "e", "u", "u:" },
					{ "o:", "ju:", "ei", "ɑi", "ɑu", "ɔi", "ɔu"},
					{ "iə", "ɛə", "uə", "juə", "ɑiə", "ɑuə", "`"},
				};
		
		int width = 52; //new button width
		int height = 25; // new button height
		int y = 10; //start y position in btnsPanel
		
		for (int i = 0; i < 7; i++, y += 34) {
			
			int x = 11; //start x position in btnsPanel
			for (int j = 0; j < 7; j++, x += 55) {
				
				JButton newTranscBtn = new JButton(transcriptionSymbols[i][j]); //creates new button with needed transcription text
				newTranscBtn.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent arg0) { //add on click event
						
						JButton thisJbutton = (JButton) arg0.getSource(); //get clicked button
						String transcription = txtTranscription.getText() + thisJbutton.getText(); //get text from transcription text field and clicked button
						txtTranscription.setText(transcription); //set next for transcruption text field
										
					}
					
				});
				newTranscBtn.setFont(new Font("Segoe UI Light", Font.PLAIN, 11));
				newTranscBtn.setBounds(x, y, width, height);
				btnsPanel.add(newTranscBtn);
			}
		}
		
	}
	
	/**
	 * <p>Creats needed AddEditWindow frams</p>
	 * @param type      Needed AddEditWindow type
	 * @param btnAdd    btnAdd object
	 * @param btnDelete btnDelete object
	 * @param editTerm  Term for editing if AddEditWindow launch if Edit mode
	 */
	private void setWindowType(MainWindow.WindowType type, 
			JButton btnAdd, JButton btnDelete, Term editTerm) {
		
		if (type == MainWindow.WindowType.ADD) { //if needed AddEditWindow type is Add
			
			btnAdd.setText("Add"); //change button text from Save to Add
			btnDelete.setVisible(false); //remove Delete button from Frame
			setTitle(ADD_WINDOW_TITLE); //change AddEditWindow title
			
		} else { //if needed AddEditWindow type is Edit
			
			
			setTitle(EDIT_WINDOW_TITLE + editTerm.getName()); //change AddEditWindow title
			txtName.setText(editTerm.getName()); //add term name to the txtName from editTerm
			txtTranscription.setText(editTerm.getTranscription()); //add term transcription to the txtTranscription from editTerm
			txtDescription.setText(editTerm.getDescription()); //add term description to the txtDescription from editTerm
			
			this.editTerm = editTerm; //add editTerm to the local variable
			
		}
		
		this.type = type; //add window type information to the local variable
		
	}
	
	/**
	 * <p>Close AddEditWindow and launch MainWindow.<p>
	 * @param updateFrame true if MainWindow JTable needs update or false if it don't need updates.
	 */
	private void closeWindow(boolean updateFrame) {
		dispose(); //close this window
		MainWindow mainWindow = new MainWindow(); //creates object of MainWindow
		mainWindow.frmAgileGlossary.setVisible(true); //displays MainWindow frame
	}
	
	
	/**
	 * <p>Logic of adding or editing button click</p>
	 */
	private void AddEditClick() {
		
		//get name, transcription and description from frame
		String name = txtName.getText();
		String transcription = txtTranscription.getText();
		String description = txtDescription.getText();
										
		if (hasValues(name, transcription, description)) { //if all text fields had values
			
			Term term = getTerm(name, transcription, description); //creates Term object with fields from frame
			
			if (type == MainWindow.WindowType.ADD && !DBHandler.isExist(name)) { //if AddEditWindow in adding mode and new term does not exist in databse
				
				DBHandler.addNewTerm(term); //add new term to the database
								
			} else if (type == MainWindow.WindowType.EDIT) { //if AddEditWindow in editing mode
				 
				DBHandler.updateTerm(term, editTerm.getName()); //edit term in database
					
			} else { //new term is already in database
				
				JOptionPane.showMessageDialog(this, "Can't add term with this name!"); //displays error dialog
				return;
				
			}
			
			closeWindow(true); //closing this frame
		}
		
	}
	
	/**
	 * <p>Creates Term object</p>
	 * @param name          Term name
	 * @param transcription Term transcription
	 * @param description   Term desrciption
	 * @return              Term object
	 */
	private Term getTerm(String name, String transcription, String description) {
		
		Term newTerm = new Term(name.trim(), transcription.trim(), description.trim());
		
		return newTerm;
		
	}
	
	/**
	 * <p>Check that Strings from text fields has values.</p>
	 * @param name          Value from txtName
	 * @param transcription Value from txtTranscription
	 * @param description   Value from txtDescription
	 * @return              true if all parameters has values and false if some of parameters hasn't value
	 */
	private boolean hasValues(String name, String transcription, String description) {
		
		if (name.isEmpty()) return false; //txtName is empty
		
		if (transcription.isEmpty()) return false; //txtTranscription is empty
		
		if (description.isEmpty()) return false; //txtDescription is empty
		
		return true; //all text fields has values
		
	}
	
	/**
	 * <p>Delete editTerm from database</p>
	 */
	private void deleteTerm() {
		
		if (editTerm != null) { //if editTerm is not null
			
			DBHandler.deleteTerm(editTerm.getName()); //delete editTerm from database
			closeWindow(true); //close this frame
			
		}
		
	}
}
