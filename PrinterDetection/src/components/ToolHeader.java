package components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 * This class creates the header panel for the application
 * @author senthilnathan_c
 */
public class ToolHeader extends JPanel {

	private static final long serialVersionUID = 1L;
    /**
     * image panel for image
     */
	private ImagePanel imgPanel;

	/**
	 * ToolHeader Constructor
	 */
	public ToolHeader() {

		initializeComponents();

		setProperties();

	}
	/**
     * method that sets properties for the components
     */

	private void setProperties() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		add(imgPanel);

	}
	/**
     * method that initializes components for the components
     */

	private void initializeComponents() {
		imgPanel = new ImagePanel(new ImageIcon("header.jpg").getImage());

	}
}
/**
 * class to load image in header panel
 */
  class ImagePanel extends JPanel {
	/**
	 * serialVersionID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * img - Image
	 */
	private Image img;
     /**
      * Constructor
      */
	ImagePanel(Image image) {
		this.img = image;
		Dimension size = new Dimension(image.getWidth(null), image
				.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);

	}
    /**
     * 
     */
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
