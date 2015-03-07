package components;



import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Trailblazerpopupmenu extends PopupMenu{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ScanFrame frame;
	
	

	public Trailblazerpopupmenu(ScanFrame trailblazerFrame) {
		// TODO Auto-generated constructor stub
		frame=trailblazerFrame;		
		System.out.println("menu constructor");
		MenuItem exit = new MenuItem("Exit");		
		MenuItem logout = new MenuItem("Logout");		
		final MenuItem open = new MenuItem("Open");
		
		exit.addActionListener(new ActionListener() {				
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		open.addActionListener(new ActionListener() {				
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(true);
				
			}
		});
		
		add(open);
		addSeparator();
		add(exit);
	}
}
