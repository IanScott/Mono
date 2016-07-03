package uat;

import java.io.IOException;
import gui.ErrorDialog;

/**
 * Class used for Acceptance Testing
 * This is the Class with the main method used to start the application
 * in a new Process.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class Main {
	
	private Main(){}
	
	/**
	 * Primary Thread to be started to start new Process.
	 * @param args argument for the main method
	 */
	public static void main(String[] args) {
		try {
			JavaProcess.exec(Class.forName("util.StartApplication"));
		} catch (ClassNotFoundException | IOException | InterruptedException e) {
			new ErrorDialog(null, "Critical ERROR" );
		} 

	}
}
