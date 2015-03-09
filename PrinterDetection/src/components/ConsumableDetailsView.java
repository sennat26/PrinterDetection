package components;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import java.util.List;

import javax.swing.*;

import javax.swing.border.EmptyBorder;

import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.bcel.internal.Constants;

import model.ConsumableData;

import I18Nconstants.AllConstants;
import actions.GetConsumablesAction;


/**
 * Class to display consumable details in Application
 * @author senthilnathan_c
 */
public class ConsumableDetailsView extends JPanel {
	/**
     * Serial version Id.
     */
	private static final long serialVersionUID = 1L;
	
	/**
	 * default table model for consumables table
	 */

	private DefaultTableModel defaultTableModelConsumables;
	
    /**
     * Label in upperpanel
     */
	private JLabel ipLabel;
    /**
     * Button in upperpanel
     */
	private JButton getConsumables;
    /**
     * text field in upper panel
     * 
     */
	private JTextField ipTextField;
    /**
     * pane; containing a label,textfield and button
     */
	private JPanel upperPanel;
    /**
     * Custom table model for consumables table
     */
	private DeviceTableModel consumablesCustomTableModel;
	
	/**
	 * consumables table
	 */

	private JTable consumablesTable;
     /**
      * scroll panel for consumables table
      */	
 
	private JScrollPane scrollpane;
	
	/**
	 * an array for filling column names
	 */

	private final String[] COLUMN_NAME = { AllConstants.NAME, AllConstants.TYPE, AllConstants.PART_NO, AllConstants.UNITS,
			AllConstants.MAX_CAPACITY, AllConstants.CURRENT_LEVEL, AllConstants.PERCENTAGE, AllConstants.CLASS };
     /**
      * label name string
      */
	private final String LABEL_NAME = AllConstants.ENTER_IP;
    /**
     * button name string
     */
	private final String BUTTON_NAME = AllConstants.GET_CONSUMABLES;
	/**
	 * List of consumables obtained from the device
	 */
	private static ConsumableDetailsView consumableDetailsView;
	
	/**
	 * constructor that calls initialization of components,set properties of components and fill table methods 
	 */

	private ConsumableDetailsView() {

		initializeComponents();

		setProperties();

		fillTableTitle();

	}

	/**
	 * initialize all the components used in the panel
	 */
   
	private void initializeComponents() {

		defaultTableModelConsumables = new DefaultTableModel();

		ipLabel = new JLabel(LABEL_NAME);

		getConsumables = new JButton(new GetConsumablesAction(BUTTON_NAME));

		ipTextField = new JTextField(20);

		upperPanel = new JPanel();

		consumablesTable = new JTable(defaultTableModelConsumables);

		scrollpane = new JScrollPane(consumablesTable);
		
		consumablesTable.getTableHeader().setReorderingAllowed(false);
	} 
	
	/**
	 * set properties for all the components in the panel
	 */

	private void setProperties() {

		upperPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		upperPanel.add(ipLabel);
		upperPanel.add(ipTextField);
		upperPanel.add(getConsumables);
		upperPanel.setBorder(new EmptyBorder(0, 10, 0, 0));

		setLayout(new BorderLayout());
		add(upperPanel, BorderLayout.NORTH);
		add(scrollpane, BorderLayout.CENTER);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		
	}

	/**
	 * fill the table title from the array value
	*/

	private void fillTableTitle() {

		for (int i = 0; i < COLUMN_NAME.length; i++) {
			defaultTableModelConsumables.addColumn(COLUMN_NAME[i]);
		}

	}
     /**
      * returns the text entered in text field
      * @return ip entered in text field
      */
	public String getipText() {
		return ipTextField.getText();
	}
	/**
	 * Creating Object for ConsumableDetailsView
	 * @return consumableDetailsView - object of ConsumableDetailsView
	 */
	public static ConsumableDetailsView getInstance() {
		if (consumableDetailsView == null) {
			consumableDetailsView = new ConsumableDetailsView();
		}
		return consumableDetailsView;
	}
	/**
	 * populate consumables list in to the table
	 */

	public void loadTableDataConsumables(List<ConsumableData> conList) {

		int rows = conList.size();
		int rowcount = 0;
		int cellvalue = 0;

		if (conList != null) {

			consumablesCustomTableModel = new DeviceTableModel(rows, COLUMN_NAME.length);
			consumablesTable.setModel(consumablesCustomTableModel);
			consumablesCustomTableModel.fireTableDataChanged();
			for (int i = 0; i < conList.size(); i++) {
				ConsumableData consumableData = conList.get(i);

            	consumablesTable.getModel().setValueAt(
						consumableData.getName(), cellvalue++, 0);

				consumablesTable.getModel().setValueAt(
						consumableData.getType(), cellvalue - 1, 1);
				consumablesTable.getModel().setValueAt(
						consumableData.getPartNumber(), cellvalue - 1, 2);
				consumablesTable.getModel().setValueAt(
						consumableData.getUnits(), cellvalue - 1, 3);
				consumablesTable.getModel().setValueAt(
						consumableData.getMaxCapacity(), cellvalue - 1, 4);
				consumablesTable.getModel().setValueAt(
						consumableData.getCurrentLevel(), cellvalue - 1, 5);
				consumablesTable.getModel().setValueAt(
						consumableData.getPercentageValue(), cellvalue - 1, 6);
				consumablesTable.getModel().setValueAt(
						consumableData.getClassValue(), cellvalue - 1, 7);

				rowcount++;

			}
		}

	} 
	
	/**
	 * Class that creates custom table model for consumables table
	 */

	private class DeviceTableModel extends DefaultTableModel {

		/** Serial version ID. */
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
			return COLUMN_NAME.length;
		}

		/**
		 * Get column name.
		 * 
		 * @param col - column number
		 *          
		 */
		public String getColumnName(final int col) {
			return COLUMN_NAME[col];
		}

		/**
		 * Get cell edit status.
		 * 
		 * @param row
		 *            - row number
		 * @param col
		 *            - column number
		 */
		public boolean isCellEditable(final int row, final int col) {
			return false;
		}
	}

}
