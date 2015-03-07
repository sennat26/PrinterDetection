package functionality;

import java.util.List;

import javax.swing.SwingWorker;

import I18Nconstants.AllConstants;

import model.ConsumableData;

import components.ConsumableDetailsView;
import components.ScanFrame;

import components.ToolFooter;
/**
 * Swing worker class to populate consumables table
 * @author satish_k 
 */
@SuppressWarnings("unchecked")
public class PopulateWorker extends SwingWorker {
	/**
	 *ipAddress of device for which consumables should be found 
	 */
	private String ipAddress;
	
	/**
	 * Constructor
	 */
    public PopulateWorker(final String ipAddressParam) {
		ipAddress = ipAddressParam;
	}
     /**
      * Fetching the consumables - performed as background task
      * @return returns Consumable List
      */
	protected Object doInBackground() throws Exception {
		ScanFrame.getInstances().Loading(true);
		GetConsumables getConsumables = new GetConsumables();
		return getConsumables.printerConsumables(ipAddress);
		
	}
    /**
     * Loading the consumables in to the table after fetching
     * 
     */
	protected void done() {
		try {
			List<ConsumableData> coList = (List<ConsumableData>) get();
			ScanFrame.getInstances().Loading(false);
			ConsumableDetailsView.getInstance().loadTableDataConsumables(coList);
			if(coList.size()!=0){
			ToolFooter.setStatus(AllConstants.CONSUMABLES_FOUND);
			}else{
				ToolFooter.setStatus(AllConstants.CONSUMABLES_EMPTY);
			}
				
		} catch (Exception e) {

		}

	}
}