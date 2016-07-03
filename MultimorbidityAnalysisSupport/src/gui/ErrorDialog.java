package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This Class sets up a dialog to display an error message.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class ErrorDialog {
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame.
	 * @param message the String message to display
	 */
	public ErrorDialog(JFrame mainframe, String message){
		JOptionPane.showMessageDialog(new JFrame(), message, "MMT Error",
	            JOptionPane.ERROR_MESSAGE);
	}
}
