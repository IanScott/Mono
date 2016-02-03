package poc1.gui.net;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import poc1.gui.net.NodeLabel;

public class NLMListener extends MouseAdapter implements MouseListener, MouseMotionListener{
	Point pressPoint;
    Point releasePoint;
    Component selectedComponent;
    

    public void mousePressed(MouseEvent e) {
        hotpotato(e);
        //((NodeLabel)e.getSource()).selected();
    }

    public void mouseReleased(MouseEvent e) {
    	hotpotato(e);
    }
        	

    public void mouseEntered(MouseEvent e) {
    	hotpotato(e);
    	((NodeLabel)e.getSource()).hover();
	}

	
	public void mouseExited(MouseEvent e) {
		hotpotato(e);
		((NodeLabel)e.getSource()).exit();
	}
	
	public void mouseMoved(MouseEvent e){
		hotpotato(e);
	}
    
	private void hotpotato(MouseEvent e){
		MouseEvent mouseEvent = new MouseEvent(
                (Component) e.getSource(), 
                e.getID(), 
                e.getWhen(), 
                e.getModifiers(), 
                e.getX(), 
                e.getY(), 
                e.getXOnScreen(), 
                e.getYOnScreen(), 
                e.getClickCount(), 
                e.isPopupTrigger(), 
                e.getButton()
                );
        ((NodeLabel)e.getSource()).getParent().dispatchEvent(mouseEvent);
	}
}
