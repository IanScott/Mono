package gui.bnviewer;

import java.awt.Component;
//import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


/**
 * The MouseListener used by the Nodes.
 * @author ABI team 37
 * @version 1.0
 */
public class NLMListener extends MouseAdapter implements MouseListener, MouseMotionListener{
	//private Point pressPoint;
    //private Point releasePoint;
    //private Component selectedComponent;
    

    @Override
	public void mousePressed(MouseEvent e) {
        hotpotato(e);
        //((NodeLabel)e.getSource()).selected();
    }

    @Override
	public void mouseReleased(MouseEvent e) {
    	hotpotato(e);
    }
        	

    @Override
	public void mouseEntered(MouseEvent e) {
    	hotpotato(e);
    	((NodeLabel)e.getSource()).hover();
	}

	
	@Override
	public void mouseExited(MouseEvent e) {
		hotpotato(e);
		((NodeLabel)e.getSource()).exit();
	}
	
	@Override
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
