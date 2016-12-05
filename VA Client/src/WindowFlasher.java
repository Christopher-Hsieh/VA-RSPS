import java.awt.*;
import java.awt.event.*;

public class WindowFlasher {

	private Dialog dialog;
	private Window window;
	
	/**
	 * Creates the window flash object
	 * 
	 * @param frame
	 */
	public WindowFlasher(ClientFrame frame) {
		window = frame;
		dialog = new Dialog(window);
		dialog.setUndecorated(true);
		dialog.setSize(0, 0);
		dialog.setModal(false);
		dialog.addWindowFocusListener(new WindowAdapter() {
			
			@Override
			public void windowGainedFocus(WindowEvent event) {
				window.requestFocus();
				dialog.setVisible(false);
				super.windowGainedFocus(event);
			}
		});
		window.addWindowFocusListener(new WindowAdapter() {
			
			@Override
			public void windowGainedFocus(WindowEvent event) {
				dialog.setVisible(false);
				super.windowGainedFocus(event);
			}
		});
	}
	
	/**
	 * Flashes the windows task-bar icon, If the windows not focused
	 */
	public void flashWindow() {
		if (!window.isFocused()) {
			if (dialog.isVisible()) {
				dialog.setVisible(false);
			}
            dialog.setLocation(0, 0);
            dialog.setLocationRelativeTo(window);
            dialog.setVisible(true);
		}
	}
}