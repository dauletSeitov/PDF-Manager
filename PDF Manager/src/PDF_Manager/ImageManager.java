package PDF_Manager;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JProgressBar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import PDF_Manager.PdfToImage.MinMaxX;
//import PDF_Manager.blah.pathPDF;

public class ImageManager {
	
	private  	MinMaxX		myMinMaxXY;
	private 	pathPDF 	myPath;
	private		int			pDFPages;
	
	ImageManager (pathPDF myPath, MinMaxX myMinMaxXY, int pDFPages){
		this.myPath = myPath;
		this.myMinMaxXY = myMinMaxXY;
		this.pDFPages = pDFPages; 
	}
	
public  void start(MainFrame parentFrame) {
	
	//==============================================================
		if (!(myMinMaxXY != null))//if is null
			{
			Library.infoBox("Borders not defined", "min max X Y");
			return;
			}
		if (!(myPath != null))//if is null
			{
			Library.infoBox("Not specified directory", "select directory");
			return;
			}
			
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++
		new Thread(new Runnable() { 
			BufferedImage in = null;
			PDDocument doc = new PDDocument();
			PDPageContentStream contents;
			PDImageXObject pdImage;
			JProgressBar my = parentFrame.getProsesBar();
	    	public void run() {
	    		for (int index = 0; index < pDFPages; index++) 
	    		{
	    			parentFrame.indicatror(index, pDFPages, my);
	    			parentFrame.setTextToLog("____________________________begin");
	    			parentFrame.setTextToLog("page " + index);
	    			File img = new  File(myPath.directory + index +".jpg");	
	    			try {
	    				in = ImageIO.read(img);
	    			} 
	    			catch (Exception e) {
	    				parentFrame.setTextToLog("ERROR " + e.toString() + " skip page " + index);
	       				continue;
	    			}
	    			
	    			try {
	    				in = in.getSubimage(myMinMaxXY.minX, myMinMaxXY.minY, myMinMaxXY.maxX - myMinMaxXY.minX, myMinMaxXY.maxY - myMinMaxXY.minY);	
	    			} catch (Exception e) {
	    				parentFrame.setTextToLog("ERROR " + e.toString() + " skip page " + index);
	    				continue;
	    			
	    			}
	    			
	    			//in = resize(in,612, 792);
	    			in = Library.resize(in, in.getWidth(), (int)(1.4142 * in.getWidth()));
	    			
	    			PDPage page = new PDPage(new PDRectangle(in.getWidth() , in.getHeight()));		
	    			doc.addPage(page);
	    			try {
	    				pdImage = LosslessFactory.createFromImage(doc, in);

	    				contents = new PDPageContentStream(doc, page);
	    				contents.drawImage(pdImage, 0,0);
	    				contents.close();	
	    			} 
	    			catch (IOException e) {
	    				parentFrame.setTextToLog("ERROR " + e.toString() + " skip page " + index);
	    				continue;
	    			}
	    			parentFrame.setTextToLog("____________________________end");
	    		}
	    			
	    		parentFrame.setTextToLog("Create pdf end");
	    		try {
	    			doc.save(myPath.directory + "new_readdy_book" + new Date().toString().replace(':','_') + ".pdf");
	    			doc.close();
	    		} catch (IOException e) {
	    			parentFrame.setTextToLog("ERROR " + e.toString());
	    		}
	    	}
	    }).start();	

		//++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		
		
		
		
		
			
}

	/*public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	} */ 
}
