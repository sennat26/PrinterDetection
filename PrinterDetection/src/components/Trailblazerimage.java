package components;


import java.awt.Image;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class Trailblazerimage {

	private Image image;
	
	public Trailblazerimage() {
		// TODO Auto-generated constructor stub
	}
	
	Image getImg() {
		try{
		ImageIcon imageicon = createImageIcon("trail.jpg");

        image=new BufferedImage(imageicon.getIconWidth(),imageicon.getIconHeight(),

                BufferedImage.TYPE_4BYTE_ABGR);

        imageicon.paintIcon(new Panel(), image.getGraphics(), 0, 0);
		}catch(Exception e){
			//Trailblazermain.file.delete();
		}
        return image;
       

    }
	
	

	private ImageIcon createImageIcon(String image) {
		// TODO Auto-generated method stub
		if (image != null) {
			return new ImageIcon(image);
		} else {
			System.err.println("Couldn't find file: " + image);
			return null;
		}
		
	}

	
}
