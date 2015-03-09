package actions;

import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowEvent;

  /**
   * Action class for button exit
   * @author senthilnathan_c  
   */

public class ExitAction extends Actions {

    /**
     * Serial version Id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor setting button name
     */
    public ExitAction() {
    	super("Exit");
		
	}
	/**
     * Exit action method to exit application
     */
    @SuppressWarnings("static-access")
	public void doAction() {
  
    	Frame[] activeframes = Frame.getFrames();
    	
    	for (int i = 0; i < activeframes.length; i++) {
    		System.out.println(activeframes[i].getName());
    		Window[] windowInstArray = activeframes[i].getWindows();
            for (int j = 0; j < windowInstArray.length; j++) {
                final Window windowInst = windowInstArray[j];
                windowInst.dispatchEvent(new WindowEvent(windowInst, WindowEvent.WINDOW_CLOSING));
           }
		}
    	
    }
}
