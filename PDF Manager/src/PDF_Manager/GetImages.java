package PDF_Manager;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GetImages {

	public JFrame frame;
	private JTextField textField;
	private pathPDF derectoruFile = null;
	private JButton btnOk;
	private MainFrame ParentFrame;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GetImages window = new GetImages();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}*/

	/**
	 * Create the application.
	 * @param set parent frame object
	 */
	public GetImages(MainFrame ParentFrame) {
		this.ParentFrame = ParentFrame;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Last light 0.1");
		
		JButton btnSelectFile = new JButton("Select file");
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
				fd.setDirectory("C:\\");
				fd.setFile("*.pdf");
				fd.setVisible(true);
				String Directory 	= fd.getDirectory();
				String filename 	= fd.getFile();
				if (filename == null)
					textField.setText("You cancelled the choice");//System.out.println("You cancelled the choice");
				else
				{
					derectoruFile = new pathPDF (Directory, filename);
					textField.setText(Directory + filename);//System.out.println("You chose " + filename);
					 
				}
			
			}
		});
		btnSelectFile.setBounds(10, 11, 89, 23);
		frame.getContentPane().add(btnSelectFile);
		
		textField = new JTextField();
		textField.setBounds(109, 12, 315, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(derectoruFile != null)
				{
					ParentFrame.frame.toFront();
					frame.setVisible(false);
					ParentFrame.pathPDFfolder(derectoruFile);
					ParentFrame.pDFToImage();
					//frame = null;	
				}
				else
					Library.infoBox("Select file", "Select");					
			}
		});
		btnOk.setBounds(335, 228, 89, 23);
		frame.getContentPane().add(btnOk);
	}
}
