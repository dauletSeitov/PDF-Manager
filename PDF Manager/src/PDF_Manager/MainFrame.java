package PDF_Manager;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import PDF_Manager.PdfToImage.MinMaxX;
import java.awt.TextArea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JToggleButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

public class MainFrame {

	//frame
	public JFrame frame;
	private JProgressBar progressBar;
	private JProgressBar progressBar_1;
	private JButton btnNewButton;
	//private JTextField textField;	
	private JButton btnNewButton_1;
	//private JButton btnNewButton_2;
	private TextArea textArea;
	private JSlider slider;
	
	//my vars 
	private MinMaxX	myMinMaxX;
	private pathPDF NamePDF;
	private static MainFrame window;
	private static GetImages windowFirst;
	private		int			pDFPages;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
		setTextToLog("App runed"); //set begin date
		action();
	}

	public void setTextToLog(String s) {
		textArea.append(new Date().toString() + " " + s +"\n");
	}
	/**
	 * on action elements of frame.
	 */
	private void action() {
		//===================================================================
		
		//===================================================================
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				progressBar_1.setValue(0);
			
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
								MyForm windowChild = new MyForm(NamePDF, window, pDFPages);
								windowChild.frame.setVisible(true);
	
							} catch (Exception e) {
								setTextToLog("ERROR " + e.toString());
							}
					}
				});
			}
		});
		//=====================================================================
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				progressBar.setValue(0);
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							windowFirst = new GetImages(window);
							windowFirst.frame.setVisible(true);
						} catch (Exception e) {
							setTextToLog("ERROR " + e.toString());						}
					}
				});
			}
		});
		//=======================================================================
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 578, 493);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Last light 0.1");
		
		btnNewButton = new JButton("Create PDF");
		btnNewButton.setBounds(449, 396, 105, 23);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("Parse PDF");
		
		btnNewButton_1.setBounds(334, 396, 105, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(334, 430, 105, 14);
		frame.getContentPane().add(progressBar);
		
		progressBar_1 = new JProgressBar();
		progressBar_1.setStringPainted(true);
		progressBar_1.setBounds(449, 430, 105, 14);
		frame.getContentPane().add(progressBar_1);
		
		textArea = new TextArea();
		textArea.setBounds(10, 10, 544, 380);
		frame.getContentPane().add(textArea);
		
		JLabel lblNewLabel = new JLabel("Lounge entertainment");
		lblNewLabel.setFont(new Font("Simplified Arabic", Font.ITALIC, 15));
		lblNewLabel.setBounds(10, 423, 149, 23);
		frame.getContentPane().add(lblNewLabel);
		
		slider = new JSlider();
		slider.setValue(250);
		slider.setMinimum(50);
		slider.setMaximum(300);
		slider.setValue(150);
		slider.setBounds(163, 400, 161, 44);
		slider.setMajorTickSpacing(50);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		frame.getContentPane().add(slider);
		
		JLabel lblDpi = new JLabel("DPI");
		lblDpi.setFont(new Font("Simplified Arabic", Font.ITALIC, 15));
		lblDpi.setBounds(124, 395, 35, 23);
		frame.getContentPane().add(lblDpi);
		//textField_1.setColumns(10);
		//textField_1.
	}
	
	/**
	 * 
	 * @param set path to folder
	 */
	public void pathPDFfolder(pathPDF s){
		this.NamePDF = s;
		
		setTextToLog("new work path " + s.directory + s.file);
	}

	
	/**
	 * function converts pdf to images
	 */
	public void pDFToImage(){
		
		setTextToLog("Converting Pdf to images...");
		
		PDDocument document;
		try {
			document = PDDocument.load(new File(NamePDF.directory + NamePDF.file));
			PDFRenderer pdfRenderer = new PDFRenderer(document);
		
			
			int pages = document.getNumberOfPages();
			//=================================================
		    new Thread(new Runnable() {
		    	BufferedImage bim;
		    	int dpi =  slider.getValue();
		    	public void runSecond(){

 		
		    		for (int page = 0; page < pages; ++page) 
		    		{
		    			//int page =21;

		    			setTextToLog("__________________________________begin");
		    			
						try {
							bim = pdfRenderer.renderImageWithDPI(page, dpi, ImageType.RGB); /*100 dpi*/
							
							setTextToLog("Original size X = " + bim.getWidth() + " " + "Y = " + bim.getHeight());
							
							bim = Library.resize(bim, 876 , 1538);
							
							setTextToLog("Resized size X = " + bim.getWidth() + " " + "Y = " + bim.getHeight());
						} catch (Exception e) {
							setTextToLog("ERROR " + e.toString() + "skipet image " + page );
							continue;
						}
						setTextToLog("Page " + String.valueOf(page));
						try {
							ImageIOUtil.writeImage(bim, NamePDF.directory + page + ".jpg", 100);
						} catch (Exception e) {
							setTextToLog("ERROR " + e.toString() + "skipet image " + page );
							continue;
						}
						indicatror(page, pages, progressBar);	
						setTextToLog("__________________________________end");	    			
		    		}
		    		try {
						document.close();
					} catch (Exception e) {
						setTextToLog("ERROR " + e.toString());
					}

		    		setTextToLog("PDF pages " + pages);
		    		setTextToLog("Converting Pdf to images end");

		    	}
		    	
		    	public void run() {
		    	
		    		runSecond();
		    	}
		    }).start();	
						
			//=======================================
		    pDFPages = pages;
			
		} 
		catch (IOException e) {
			setTextToLog("ERROR " + e.toString());
		}
			
		
	}
	
	/**
	 * 
	 * @param set actualy value
	 * @param sen maximum value
	 * @param set which of bars
	 */
	public void indicatror(int index, int max, JProgressBar bar){
		
		bar.setMaximum(max);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bar.setValue(bar.getValue() + 1);
				} catch (Exception e) {
					setTextToLog("ERROR " + e.toString());
				}
			}
		});
	}
	
	public JProgressBar getProsesBar(){
		return progressBar_1;
	}
	/**
	 * 
	 * @param setn min max x and y
	 * @param set path to pdf 
	 * @param set count of pages
	 */
	public void setMinMaxX (MinMaxX myMinMaxX, int CoutnOfPages){

		setTextToLog("Begin create pdf...");
		this.myMinMaxX = myMinMaxX;
		this.pDFPages = CoutnOfPages;
		
		setTextToLog("New size of pictures min X = " + myMinMaxX.minX + " max X = " + myMinMaxX.maxX 
				+ " min y = " + myMinMaxX.minY + " max y = " + myMinMaxX.maxY);
		setTextToLog("PDF pages " + CoutnOfPages);
		ImageManager myImage = new ImageManager(NamePDF, myMinMaxX, pDFPages);
		myImage.start(this);
	}
}
