package actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * Base class for all the actions
 * @author satish_k 
 */

public abstract class Actions extends AbstractAction {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor
	 * */
	public Actions(final String name){
		super(name);
	}
    /**
     * method that calls overridden doAction method
     */
	public  void actionPerformed(final ActionEvent event){
		doAction();
	}
	/**
	 * Abstract doAction method which can be overridden to perform required action
	 */
	public abstract void doAction(); 
	
}
