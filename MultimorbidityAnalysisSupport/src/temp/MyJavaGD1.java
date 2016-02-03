package temp;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.rosuda.javaGD.GDCanvas;
import org.rosuda.javaGD.GDInterface;
import org.rosuda.javaGD.JGDPanel;
import org.rosuda.javaGD.JavaGD;
/**
* This is a minimal reimplementation of the GDInterface. When the device is opened,
* it just creates a new JFrame, adds a new GDCanvas to it (R will plot to this GDCanvas)
* and tells the program to exit when it is closed.
*/
public class MyJavaGD1 extends GDInterface {
	
	
	public static JFrame f;
	
	public static JGDPanel d;
	
	public static JPanel getGDCanvas(){
		if(d != null){
			return d;
		}
		else {
			return null;
		}
	}
	
	public void gdOpen(double w, double h) {
			//f = new JFrame("JavaGD");
			c = new JGDPanel(w, h);
			d = (JGDPanel) c;
			//d.setSize(250,250);
			//d.add((GDCanvas) c);
			//f.add((GDCanvas) c);
			//f.pack();
			//f.setVisible(true);
			//f.setTitle("Naked R plot");
			//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}