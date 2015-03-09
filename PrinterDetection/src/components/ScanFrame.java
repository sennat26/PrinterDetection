package components;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;


/**
 * This Class will be used for loading the Application
 * @author senthilnathan_c 
 */
public class ScanFrame extends JFrame {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
    /**
     * Footer panel for application.
     */
	private ToolFooter toolFooter;
    /**
     * Header Panel for application.
     */
	private ToolHeader toolHeader;
    /**
     * Center panel for application.
     */
	private ToolCenter toolCenter;
    /**
     * Base panel contains Header,Center,Footer panel.
     */
	private JPanel basePanel;
	
	private JWindow window;
	
	private static ScanFrame scanFrame;
	
	private Trailblazerimage image=new Trailblazerimage();
	
	private TrayIcon icon;
	
	public ScanFrame() {
		super("ScanTool");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

	public void initializeFrame() {
		initializeComponents();
		setProperties();
		// this.pack();
		setVisible(true);
		
	}
  /**
   * set properties for all the components
   */
	private void setProperties() {

		setIconImage(image.getImg());
		setLocation(330, 190);
		setResizable(false);
		setName("ScanTool");
		this.setSize(700, 650);
	}
      
	/**
	 * initialize all the components for base frame
	 */
	
	private void initializeComponents() {
		icon = new TrayIcon(image.getImg(),"Trailblazer", new Trailblazerpopupmenu(this));
		icon.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	               setVisible(true);
	           }
	        });
		
		if(SystemTray.isSupported()){
			try {
				SystemTray.getSystemTray().add(icon);
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				System.out.println("Icon cannot be added to System tray");
			}
		}
		
		toolFooter = new ToolFooter();
		toolHeader = new ToolHeader();
		toolCenter = new ToolCenter();
		basePanel = new JPanel();
        window = new JWindow();
		this.basePanel.setLayout(new BorderLayout());
		this.basePanel.add(toolHeader, BorderLayout.NORTH);
		this.basePanel.add(toolCenter, BorderLayout.CENTER);
		this.basePanel.add(toolFooter, BorderLayout.SOUTH);

		this.getContentPane().add(this.basePanel);
		this.setTitle("ScanTool");
		
	}
	 public void Loading(Boolean value) {
		
		 this.setEnabled(!value);
		
		    JPanel panel1 = new JPanel();
		    ImageIcon pic = new ImageIcon("hope2.gif");
		    //AWTUtilities.setWindowOpacity(this, .7f);
            panel1.add(new JLabel(pic));
		    window.add(panel1);
		    window.pack();
		    window.setVisible(value);
		    window.setLocationRelativeTo(this);   
		 
		  }
		public static ScanFrame getInstances() {
			if (ScanFrame.scanFrame == null) {
				ScanFrame.scanFrame = new ScanFrame();
			}
			return scanFrame;
		}


		 
}
	
	

