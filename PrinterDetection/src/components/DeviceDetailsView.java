package components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.DeviceData;

import org.snmp4j.PDU;
import org.snmp4j.smi.VariableBinding;

import I18Nconstants.AllConstants;

import functionality.Devices;
import functionality.PopulateWorker;

/** 
 * 
 * DeviceDetails class to display printer details in application
 * @author satish_k & senthilnathan_c
 */
public class DeviceDetailsView extends JPanel {
	 /**
     * Serial version Id.
     */
	private static final long serialVersionUID = 1L;
	/**
	 * default table model for Devices table
	 */
	private DefaultTableModel devicesDefaultTableModel;
	/**
	 * list of OID values taken from PDU
	 */
	private ArrayList<String> oidValue;
	/**
	 * label in combined panel
	 */
	private JLabel discoverDevices;
	/**
	 * label in combined panel
	 */
	private static JLabel DISCOVER_SUBNET;
	/**
	 * An array of column names for devices table
     */
	private String[] colname = { AllConstants.SL_NUMBER, AllConstants.MANUFACTURER, AllConstants.MODEL,
			AllConstants.IPADDRESS };
    /**
     * a panel containing 2 labels
     */
	private JPanel labelPanel;
    /**
     * Jtable to display devices found
     */
	private JTable deviceTable;
	/**
	 * Combobox to select language for the application
	 * */
	private JComboBox selectLanguageBox;
    /**
     * custom table model for devices
     */
	private DeviceTableModel deviceTableModel;
    /**
     * scroll pane for devices table
     */
	private JScrollPane tableScrollpane;
    /**
     * map with inetAddress and PDU values
     * */
	private Map<InetAddress, PDU> deviceList;
    /**
     * Executor service for swing worker
     */
	private ExecutorService executor;
	/**
	 * Combobox Content
	 * */
	private final String[] comboBoxContent = {"Select your language","English","French"};
	
	/**
	 * Panel for Combobox
	 * */
	private JPanel comboboxPanel;
	/**
	 * panel containing comboboxPanel and labelPanel
	 * */
	private JPanel DeviceViewTopPanel;
	/**
	 * constructor invokes the method to initialize,set properties for components 
	 * and execute the FindDeviceWorker
	 */

	public DeviceDetailsView() {

		initializeComponents(); // method initializes components

		setProperties(); // sets properties for components

		fillTableTitle();

		executor = Executors.newCachedThreadPool();
		

		executor.execute(new FindDeviceWorker());	
		
	}
	/**
	 * For initializing the components and variables
	 */ 
	private void initializeComponents() {

		devicesDefaultTableModel = new DefaultTableModel();

		discoverDevices = new JLabel(AllConstants.DISCOVERED_PRINTERS);

		DISCOVER_SUBNET = new JLabel(AllConstants.DISCOVERED_SUBNETS);

		labelPanel = new JPanel();
		
		DeviceViewTopPanel= new JPanel();

		deviceTable = new JTable(devicesDefaultTableModel);

		tableScrollpane = new JScrollPane(deviceTable);
		
		deviceTable.getTableHeader().setReorderingAllowed(false);
		
		selectLanguageBox= new JComboBox(comboBoxContent);
		
		comboboxPanel= new JPanel();

		printerTableClick();

	}
	/**
	 * set properties for all the components in the panel
	 */
	private void setProperties() {


		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		labelPanel.add(discoverDevices);
		labelPanel.add(DISCOVER_SUBNET);
		/*labelPanel.add(selectLanguageBox);*/
		
		/*comboboxPanel.add(selectLanguageBox);*/
		
		DeviceViewTopPanel.setLayout(new BorderLayout());
		DeviceViewTopPanel.add(labelPanel,BorderLayout.WEST);
		/*DeviceViewTopPanel.add(comboboxPanel,BorderLayout.EAST);*/

		setLayout(new BorderLayout());
		add(DeviceViewTopPanel, BorderLayout.NORTH);
		add(tableScrollpane, BorderLayout.CENTER);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}
	
	/**
	 * populating the device information in device table 
	 */
	private void loadTableData(final Map<InetAddress, PDU> deviceList) {
		int serialnumber = 1;
		int rowcount = 0;
		int rows = deviceList.size();
        DeviceData deviceData = new DeviceData(); 
		if (deviceList != null) {

			deviceTableModel = new DeviceTableModel(rows, colname.length);
			deviceTable.setModel(deviceTableModel);
			deviceTableModel.fireTableDataChanged();
			if(deviceList.size()==0){
			ToolFooter.setStatus(AllConstants.NO_DEV_FOUND);
			}
			
			for (InetAddress a : deviceList.keySet()) {
                
                //getting PDU response for every inet address
                PDU pdu = deviceList.get(a);
				ArrayList<String> oidValueList = this.getvar(pdu);
				oidValueList.add(a.getHostAddress());
				
				if(oidValueList.get(1).contains(" ")){
				String str[]=oidValueList.get(1).split(" ");
				oidValueList.add(str[0]);
				deviceData.setManufacturer(oidValueList.get(4));
				}else{
					deviceData.setManufacturer(oidValueList.get(1));
				}
				
				deviceData.setModel(oidValueList.get(2));
				deviceData.setIpAddress(oidValueList.get(3));
		
				deviceTable.getModel().setValueAt(serialnumber, rowcount++, 0);
				deviceTable.getModel().setValueAt(deviceData.getManufacturer(), rowcount - 1, 1);
				deviceTable.getModel().setValueAt(deviceData.getModel(), rowcount - 1, 2);
				deviceTable.getModel().setValueAt(deviceData.getIpAddress(), rowcount - 1, 3);
				serialnumber++;

			}

		}

	} 
	
	/**
	 * Action method when user clicks on devices row to get consumables
	 */

	private void printerTableClick() {
          
		deviceTable.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				tblPrintersMouseClicked(evt);
			}

		});
	}
	
	/**
	 * this method is used to extract the ipAddress of the device from selected row in device table and 
	 * execute consumableDetailsWorker.
	 */

	private void tblPrintersMouseClicked(MouseEvent evt){
		ToolFooter.setStatus("searching...");
		String strIPaddress = (String) deviceTable.getModel().getValueAt(
				deviceTable.getSelectedRow(),3);
		
		try {

			ExecutorService executor = Executors.newCachedThreadPool();
			PopulateWorker consumableDetailsWorker = new PopulateWorker(
					strIPaddress);
			executor.execute(consumableDetailsWorker);

			
		} catch (Exception e) {
			ToolFooter.setStatus("Unexpected Exception");
		}
	}
	
    /**
     * fill the title for devices table
     * */
	private void fillTableTitle() {

		for (int i = 0; i < colname.length; i++) {
			devicesDefaultTableModel.addColumn(colname[i]);
		}
	}
	
     /**
      * set status for subnets label
      */
	public static void setStatus(final String status) {
		DISCOVER_SUBNET.setText(status);
	}
	
      /**
       * clear status for label
       */
	public static void clearStatus() {
		DISCOVER_SUBNET.setText("");
	}
	
    /**
     * custom model for device table 
     */
	private class DeviceTableModel extends DefaultTableModel {

		/** 
		 * Serial version ID. 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @param rows
		 *            - row number
		 * @param cols
		 *            - column number
		 */
		public DeviceTableModel(final int rows, final int cols) {
			super(rows, cols);
		}

		/**
		 * Get column count.
		 * 
		 * @return int - column count
		 */
		public int getColumnCount() {
			return colname.length;
		}

		/**
		 * Get column name.
		 * 
		 * @param col
		 *            - column number
		 * @return String - column name
		 */
		public String getColumnName(final int col) {
			return colname[col];
		}

		/**
		 * Get cell edit status.
		 * 
		 * @param row
		 *            - row number
		 * @param col
		 *            - column number
		 * @return boolean - status
		 */
		public boolean isCellEditable(final int row, final int col) {
			return true;
		}
		
	}
	
	/**
	 * this method extracts the value alone from the PDU sent.
	 * @return ret - array list containing OID values.
	 * */

	private ArrayList<String> getvar(PDU pdu) {
		// TODO Auto-generated method stub
		oidValue = new ArrayList<String>();
		for (Object vb : pdu.getVariableBindings()) {
			oidValue.add(((VariableBinding) vb).getVariable().toString());

		}
		return oidValue;

	}
	
	/**
	 * FindDeviceWorker will be used for finding the devices with in the network and It will populate 
	 * the device details in the table.
	 */

	@SuppressWarnings("unchecked")
	private class FindDeviceWorker extends SwingWorker {
		/**
		 * Devices details are fetched as background task.
		 * @return deviceList
		 */
		protected Object doInBackground() throws Exception {
			ScanFrame.getInstances().Loading(true);
			deviceList = new Devices().deviceList();
			return deviceList;
		}
        /**
         * Device table is populated from the obtained response
         */
		protected void done() {
			ScanFrame.getInstances().Loading(false);
			ToolFooter.setStatus(AllConstants.DEVICE_FOUND);
			DeviceDetailsView.setStatus(AllConstants.DISCOVERED_SUBNETS);
			loadTableData(deviceList);

		}

	}

}
