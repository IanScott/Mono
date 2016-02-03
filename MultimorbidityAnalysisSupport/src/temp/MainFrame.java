package temp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class MainFrame {

	public static void main(String[] args) {
		
		int HORIZSPLIT = JSplitPane.HORIZONTAL_SPLIT;

	    int VERTSPLIT = JSplitPane.VERTICAL_SPLIT;

	    boolean continuousLayout = true;
		  
		    JFrame frame = new JFrame("SplitPaneFrame");
		    
		    JavaGDExample1.main(null);
		    
		    JLabel leftImage = new JLabel(new ImageIcon("a.gif"));
		    Component left = new JScrollPane(leftImage);
		    JLabel rightImage = new JLabel(new ImageIcon("b.gif"));
		    Component right = MyJavaGD1.getGDCanvas();

		    JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		    split.setDividerLocation(600);
		    split.setDividerSize(4);
		    
		    JSplitPane splitPane2 = new JSplitPane(VERTSPLIT, split, rightImage);
		    splitPane2.setOneTouchExpandable(true);
		    splitPane2.setDividerLocation(700);
		    splitPane2.setDividerSize(4);
		    
		    
		    frame.getContentPane().add(splitPane2);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setSize(1240, 1020);
		    frame.setVisible(true);
		    
		    
		    
		    
		    
		  }
		
	

}
