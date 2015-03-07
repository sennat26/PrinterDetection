package components;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import actions.ExitAction;

/**
 * class to design base panel of UI
 * @author satish_k  
 */
public class ToolFooter extends JPanel {
	/**
	 * serialVersionID
	 */
    private static final long serialVersionUID = 1L;

	/**
	 * Label to show status messages
	 */
	private static JLabel labelStatus;
    /**
     * button to exit UI
     */
	private JButton exitButton;
    /**
     * panel for status label
     */
	private JPanel statusPanel;
    /**
     * panel for exit button
     */
	private JPanel exitpanel;
   
	/**
     * constructor
     */
	public ToolFooter() {

		initializeComponents();

		setProperties();

	}
    /**
     * For initializing the Components and instance variable.
     */
	private void initializeComponents() {

		labelStatus = new JLabel();

		exitButton = new JButton(new ExitAction());

		statusPanel = new JPanel();

		exitpanel = new JPanel();

	}
    /**
     * For setting the component properties
     */
	private void setProperties() {
		
		setLayout(new BorderLayout());

		statusPanel.add(labelStatus);
		statusPanel.setBorder(new EmptyBorder(5, 10, 0, 0));

		exitpanel.add(exitButton);
		exitpanel.setBorder(new EmptyBorder(0, 0, 0, 10));

		add(statusPanel, BorderLayout.WEST);
		add(exitpanel, BorderLayout.EAST);

	}
    /**
     * set status for label in toolfooter
     */
	public static void setStatus(final String status) {

		labelStatus.setText(status);
	}
   /**
    * clear label status
    */
	public static void clearStatus() {
		labelStatus.setText("");
	}
}


