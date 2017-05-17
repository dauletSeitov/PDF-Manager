package PDF_Manager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class PdfToImage {
	
	class MinMaxX{
		MinMaxX(int minX, int maxX, int minY, int maxY){
			this.minX = minX;
			this.maxX = maxX;
			this.minY = minY;
			this.maxY = maxY;
			
		}
		int minX;
		int maxX;
		int minY;
		int maxY;
	}
	
	public BufferedImage getImage(String derectoruFile) throws Exception{
		
		File img = new  File(derectoruFile);
		BufferedImage in = null;
		in = ImageIO.read(img);
		
		return in;		
	}
	
	public MinMaxX convert (final BufferedImage in)
	{

		BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		
		Graphics2D g = newImage.createGraphics();
		g.drawImage(in, 0, 0, null);
		g.dispose();
		
		int minX = newImage.getWidth();  
		int maxX = 0;
		
		int minY = newImage.getHeight();  
		int maxY = 0;
		
		for (int x = 0; x < newImage.getWidth(); x++) {
			for (int y = 0; y < newImage.getHeight(); y = y + 10) 
			{				
				Color myColor = new Color(newImage.getRGB(x, y));
			
				if (myColor.getBlue() < 200 || myColor.getRed() < 200 || myColor.getGreen()< 200)
				{
					if(minX > x)
						minX = x;
					
					if(maxX < x)
						maxX = x;
				}			
			}
		}
		//========================================
		
		for (int x = 0; x < newImage.getWidth(); x = x + 20) {
			for (int y = 0; y < newImage.getHeight(); y++) 
			{				
				Color myColor = new Color(newImage.getRGB(x, y));
			
				if (myColor.getBlue() < 200 || myColor.getRed() < 200 || myColor.getGreen()< 200)
				{
					if(minY > y)
						minY = y;
					
					if(maxY < y)
						maxY = y;
				}			
			}
		}
		return new MinMaxX(minX, maxX, minY, maxY);
	}
	
	public BufferedImage paintLines (final BufferedImage in, final MinMaxX my, MainFrame parentFrame ) {
		int inHeight = in.getHeight();
		int inWidth =  in.getWidth();
		BufferedImage bi = new BufferedImage(in.getWidth(), inHeight, BufferedImage.TYPE_BYTE_GRAY);
		
		Graphics2D g = bi.createGraphics();
		g.drawImage(in, 0,0, null);
		g.dispose();
		
		
		for (int y = 0; y < inHeight; y++)
		{
			try {
				
			bi.setRGB(my.minX, y, Color.BLACK.getRGB());
			bi.setRGB(my.minX + 1, y, Color.BLACK.getRGB());
			bi.setRGB(my.minX - 1, y, Color.BLACK.getRGB());
			bi.setRGB(my.minX - 2, y, Color.BLACK.getRGB());
			bi.setRGB(my.minX + 2, y, Color.BLACK.getRGB());
			} catch (Exception e) {
				parentFrame.setTextToLog("Cant print line" + e.toString());
			}
			try {
			
			bi.setRGB(my.maxX, y, Color.BLACK.getRGB());
			bi.setRGB(my.maxX - 1, y, Color.BLACK.getRGB());
			bi.setRGB(my.maxX + 1, y, Color.BLACK.getRGB());
			bi.setRGB(my.maxX - 2, y, Color.BLACK.getRGB());
			bi.setRGB(my.maxX + 2, y, Color.BLACK.getRGB());
			
			} catch (Exception e) {
				parentFrame.setTextToLog("Cant print line" + e.toString());
			}
		}
		//===============================================================
		
		for (int x = 0; x < inWidth; x++)
		{
			try {
				
			bi.setRGB(x, my.minY + 1, Color.BLACK.getRGB());
			bi.setRGB(x, my.minY - 1, Color.BLACK.getRGB());
			bi.setRGB(x, my.minY + 2, Color.BLACK.getRGB());
			bi.setRGB(x, my.minY - 2, Color.BLACK.getRGB());
			bi.setRGB(x, my.minY, Color.BLACK.getRGB());
			} catch (Exception e) {
				parentFrame.setTextToLog("Cant print line" + e.toString());
			}
			try {
			
			bi.setRGB(x, my.maxY + 1, Color.BLACK.getRGB());
			bi.setRGB(x, my.maxY - 1, Color.BLACK.getRGB());
			bi.setRGB(x, my.maxY + 2, Color.BLACK.getRGB());
			bi.setRGB(x, my.maxY - 2, Color.BLACK.getRGB());
			bi.setRGB(x, my.maxY, Color.BLACK.getRGB());
			
			} catch (Exception e) {
				parentFrame.setTextToLog("Cant print line" + e.toString());
			}
		}
		
		return bi;
	}
	
	
}
