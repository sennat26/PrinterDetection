package components;

import java.awt.GridLayout;

import javax.swing.JPanel;
 /**
  * class to design center panel of UI
  * @author satish_k 
  * */
public class ToolCenter extends JPanel {
    /**
     * Serial version Id.
     */
	 
	private static final long serialVersionUID = 1L;
    /**
     * Contains device details view for the application
     */
	private DeviceDetailsView deviceDetailsView;
    /**
     * Contains consumable view for the application
     */ 
	private ConsumableDetailsView consumableDetailsView;

    /**
     * Constructor
     */
	public ToolCenter() {

		initializeComponents();

		setProperties();

	}
    
	/**
     * method that sets properties for the components
     */
	private void setProperties() {

		setLayout(new GridLayout(2, 1));
		add(deviceDetailsView);
		add(consumableDetailsView);

	}
	/**
     * method that initializes components for the components
     */
	private void initializeComponents() {

		consumableDetailsView = ConsumableDetailsView.getInstance();
		deviceDetailsView = new DeviceDetailsView();

	}

}
