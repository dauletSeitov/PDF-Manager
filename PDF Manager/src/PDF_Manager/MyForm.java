package PDF_Manager;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import PDF_Manager.PdfToImage.MinMaxX;
//import PDF_Manager.blah.pathPDF;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MyForm extends MainFrame implements WindowListener {

	public JFrame frame;
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnOk;
	private JTextField textField;
	private JButton button;
	private JButton button_1;
	private JButton btnNewButton_3;
	//Frame
	
	private MinMaxX linesXY;
	private BufferedImage myImage;
	private pathPDF derectoruFile;
	private MainFrame parentFrame;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private JButton btnNewButton_6;
	private int pdfPages;
	private JTextField textField_1;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyForm window = new MyForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 * @param set path for the folder
	 * @param set parent object 
	 * @param set count of pages
	 */
	public MyForm(pathPDF derectoruFile, MainFrame ParentFrame, int pages) {
		this.derectoruFile 	= derectoruFile;
		this.parentFrame 	= ParentFrame;
		this.pdfPages = pages;
		initialize();
		actions();
		loadImage();
	}
	
	

	/**
	 * Load the image to label
	 */
	private void loadImage(){
		if (derectoruFile != null)
		{
			PdfToImage image = new PdfToImage();
			try {
				if (derectoruFile.file.lastIndexOf(".pdf") > 0 ) throw new Exception();
				myImage = image.getImage(derectoruFile.directory + derectoruFile.file);	
				parentFrame.setTextToLog("Image loaded from " + derectoruFile.directory + derectoruFile.file);
			} catch (Exception e) {
				try {
					myImage = image.getImage(derectoruFile.directory + "9.jpg");
					parentFrame.setTextToLog("Image loaded from " + derectoruFile.directory + "9.jpg");	
				} catch (Exception e1) {
					try {
						myImage = image.getImage(derectoruFile.directory + "0.jpg");
						parentFrame.setTextToLog("Image loaded from " + derectoruFile.directory + "0.jpg");
					} catch (Exception e2) {
						parentFrame.setTextToLog("Can't load image");
						return;	
					}
					
				}
			}
			
			linesXY = image.convert(myImage);
			BufferedImage myLocal = image.paintLines(myImage, linesXY, parentFrame);
			lblNewLabel.setIcon(new ImageIcon(Library.resize(myLocal, 380 , 490)));
		}
	}
	
	/**
	 * frame elements action 	
	 */
	private void actions() {
						
		//==================================================================
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (myImage != null)
				{
					linesXY.minX = linesXY.minX - 10;
					PdfToImage image = new PdfToImage();
					BufferedImage myLocal = image.paintLines(myImage, linesXY, parentFrame);
					lblNewLabel.setIcon(new ImageIcon(Library.resize(myLocal, 380 , 490)));
				}
			}
		});
		
		//====================================================================
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (myImage != null)
				{
					linesXY.minX = linesXY.minX + 10;

					PdfToImage image = new PdfToImage();
					BufferedImage myLocal = image.paintLines(myImage, linesXY, parentFrame);
					lblNewLabel.setIcon(new ImageIcon(Library.resize(myLocal, 380 , 490)));
				}
			}
		});
		//===================================================
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
				fd.setDirectory("C:\\");
				fd.setFile("*.jpg");
				fd.setVisible(true);
				String Directory 	= fd.getDirectory();
				String filename 	= fd.getFile();
				if (filename == null)
					textField.setText("You cancelled the choice");//System.out.println("You cancelled the choice");
				else
				{
					derectoruFile = new pathPDF(Directory, filename);
					textField.setText(Directory + filename);//System.out.println("You chose " + filename);
					loadImage();
					 
				}
			}
		});
		
		//==========================================================
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myImage != null)
				{
					linesXY.maxX = linesXY.maxX + 10;

					PdfToImage image = new PdfToImage();
					BufferedImage myLocal = image.paintLines(myImage, linesXY, parentFrame);
					lblNewLabel.setIcon(new ImageIcon(Library.resize(myLocal, 380 , 490)));
				}
			}
		});
		//============================================================
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myImage != null)
				{
					linesXY.maxX = linesXY.maxX - 10;
					PdfToImage image = new PdfToImage();
					BufferedImage myLocal = image.paintLines(myImage, linesXY, parentFrame);
					lblNewLabel.setIcon(new ImageIcon(Library.resize(myLocal, 380 , 490)));
				}
			}
		});
		//========================================================
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(linesXY != null)
					{
					try {
						pdfPages = Integer.parseInt(textField_1.getText());
					} catch (Exception e) {	}
					
					
					if (pdfPages > 0)//System.out.println(linesXY.maxX + linesXY.minX);
					{	
						parentFrame.frame.toFront();
						frame.setVisible(false);
						parentFrame.pathPDFfolder(derectoruFile);
						parentFrame.setMinMaxX(linesXY, pdfPages);
					}
					else
						Library.infoBox("Write count of pages", "Count of pages");
					}
				else
					Library.infoBox("Borders not defined", "min max X Y");
			}
		});
	//========================================================================
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (myImage != null)
				{
					linesXY.minY = linesXY.minY - 10;
					PdfToImage image = new PdfToImage();
					BufferedImage myLocal = image.paintLines(myImage, linesXY, parentFrame);
					lblNewLabel.setIcon(new ImageIcon(Library.resize(myLocal, 380 , 490)));
				}
			}
		});
	//========================================================================
		
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myImage != null)
				{
					linesXY.minY = linesXY.minY + 10;
					PdfToImage image = new PdfToImage();
					BufferedImage myLocal = image.paintLines(myImage, linesXY, parentFrame);
					lblNewLabel.setIcon(new ImageIcon(Library.resize(myLocal, 380 , 490)));
				}
			}
		});
	//==========================================================================
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myImage != null)
				{
					linesXY.maxY = linesXY.maxY + 10;
					PdfToImage image = new PdfToImage();
					BufferedImage myLocal = image.paintLines(myImage, linesXY, parentFrame);
					lblNewLabel.setIcon(new ImageIcon(Library.resize(myLocal, 380 , 490)));
				}
			}
		});
	//============================================================================
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myImage != null)
				{
					linesXY.maxY = linesXY.maxY - 10;
					PdfToImage image = new PdfToImage();
					BufferedImage myLocal = image.paintLines(myImage, linesXY, parentFrame);
					lblNewLabel.setIcon(new ImageIcon(Library.resize(myLocal, 380 , 490)));
				}
			}
		});
	//==========================================================================
		textField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				try {
					pdfPages = Integer.parseInt(textField_1.getText());
				} catch (Exception e) {
					textField_1.setText(String.valueOf(pdfPages));
				}
				
			}
		});
	//=============================================================================	
	}

	/**
	 * Initialize the contents of the frame.
	 *  
	 */
	private void initialize() {
		//my vars

		//frame vars
		frame = new JFrame();
		frame.setTitle("Last light 0.1");
		frame.setBounds(100, 100, 824, 560);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnNewButton = new JButton("<-");
		btnNewButton.setBounds(410, 44, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("->");
		btnNewButton_1.setBounds(509, 44, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Select file");
		btnNewButton_2.setBounds(410, 10, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
	
		btnOk = new JButton("Ok");
		btnOk.setBounds(711, 488, 89, 23);
		frame.getContentPane().add(btnOk);
		
		textField =  new JTextField();
		textField.setText("Select file");
		textField.setBounds(509, 11, 291, 20);
		frame.getContentPane().add(textField);
						
		lblNewLabel = new JLabel();
		lblNewLabel.setBounds(10, 10, 390, 500);
		frame.getContentPane().add(lblNewLabel);
		
		button = new JButton("<-");
		button.setBounds(608, 44, 89, 23);
		frame.getContentPane().add(button);
		
		button_1 = new JButton("->");
		button_1.setBounds(711, 44, 89, 23);
		frame.getContentPane().add(button_1);
		
		btnNewButton_3 = new JButton("<html>&#9650</html>");
		btnNewButton_3.setBounds(410, 78, 33, 89);
		frame.getContentPane().add(btnNewButton_3);
		
		btnNewButton_4 = new JButton("<html>&#9660</html>");
		btnNewButton_4.setBounds(410, 178, 33, 89);
		frame.getContentPane().add(btnNewButton_4);
		
		btnNewButton_5 = new JButton("<html>&#9660</html>");
		btnNewButton_5.setBounds(410, 422, 33, 89);
		frame.getContentPane().add(btnNewButton_5);
		
		btnNewButton_6 = new JButton("<html>&#9650</html>");
		btnNewButton_6.setBounds(410, 322, 33, 89);
		frame.getContentPane().add(btnNewButton_6);
		
		textField_1 = new JTextField();
		textField_1.setBounds(615, 489, 86, 20);
		textField_1.setText(String.valueOf(this.pdfPages)); // ?
		textField_1.setColumns(10);
		frame.getContentPane().add(textField_1);
				
		JLabel lblCountOfPages = new JLabel("Count of pages");
		lblCountOfPages.setBounds(509, 492, 89, 14);
		frame.getContentPane().add(lblCountOfPages);
	}



	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowClosing(WindowEvent arg0) {
	//
		
		//this.frame = null;
		
	}



	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
