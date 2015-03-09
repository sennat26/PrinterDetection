package actions;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import components.ConsumableDetailsView;
import components.ToolFooter;
import functionality.PopulateWorker;

/**
 * Action class to get consumables
 * @author senthilnathan_c 
 */
public class GetConsumablesAction extends Actions {
    /**
     * Serial version Id.
     */
	private static final long serialVersionUID = 1L;
    /**
     * GetConsumablesAction Constructor.
     * @param name -  Button Name.
     */
	public GetConsumablesAction(final String name) {
		super(name);
		
	}

	/**
	 * Calls PopulateWorker to fetch the device consumable information for selected ipAddress 
	 *
	 */
	public void doAction() {
		ToolFooter.setStatus("searching...");
		String IPaddress =ConsumableDetailsView.getInstance().getipText();
		ExecutorService executor = Executors.newCachedThreadPool();
		PopulateWorker consumableDetailsWorker = new PopulateWorker(IPaddress);
		executor.execute(consumableDetailsWorker);
	}
	

}
