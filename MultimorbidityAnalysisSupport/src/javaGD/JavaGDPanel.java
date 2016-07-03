package javaGD;

import org.rosuda.javaGD.GDInterface;
import org.rosuda.javaGD.JGDPanel;

/**
 * Class for creating a Panel containing the plotted image from R.
 * @author ABI team 37
 * @version 1.0
 */
public class JavaGDPanel extends GDInterface{
	
	//Attribute
	private static JGDPanel d;
	
	/**
	 * Method for creating a new plot.
	 */
	@Override
	public void gdOpen(double w, double h) {
		 c = new JGDPanel(w, h);
		 d = (JGDPanel) c;
    }
    
	/**
	 * Getter for JGDPanel, the panel where R plots are painted on.
	 * @return a JGDPanel
	 */
	public static JGDPanel getPanel(){
		return d;
	}
	
}
