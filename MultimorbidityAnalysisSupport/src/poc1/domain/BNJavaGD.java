package poc1.domain;

import org.rosuda.javaGD.GDInterface;
import org.rosuda.javaGD.JGDPanel;

/**
 * Class for creating a Panel containing the plotted image from R
 * @author Ian van Nieuwkoop
 * @version 0.1
 */
public class BNJavaGD extends GDInterface{
	
	//Attribute
	private static JGDPanel d;
	
	/**
	 * Method for creating a new plot
	 */
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
