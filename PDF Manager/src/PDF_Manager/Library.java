package PDF_Manager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

//import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.stdDSA;

public class  Library {

	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	
	/**
	 * 
	 * @param set image
	 * @return resized image
	 */
	public static BufferedImage resize (BufferedImage in, int x, int y){
		
		BufferedImage out = new BufferedImage(/*380, 490*/x, y, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = out.createGraphics();
		g.drawImage(in, 0,0, x, y/*380, 490*/, null);
		g.dispose();
		
		return out;
	}
}

class pathPDF {
	pathPDF(String directory, String file){
		this.directory = directory;
		this.file = file;
	}
	String directory;
	String file;
}