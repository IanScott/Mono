package gui.bnviewer;

import java.awt.Component;
//import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
//import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
//import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 * The MouseListener used by the ConnectorContainer Panel.
 * @author ABI team 37
 * @version 1.0
 */
public class MListener extends MouseAdapter implements MouseListener, MouseMotionListener{
	private Point pressPoint;
    private Point releasePoint;
    private Component selectedComponent;
    
    /*
    private Window dragWindow = new JWindow() {
	private static final long serialVersionUID = -397567771100456021L;

	@Override
	public void paint(Graphics g) {
           super.paint(g);
           selectedComponent.paint(g);
        }
    };
    */
    
    @Override
	public void mouseDragged(MouseEvent e) {
        /*
    	Point dragPoint = e.getPoint();
        int xDiff = pressPoint.x - dragPoint.x;
        int yDiff = pressPoint.y - dragPoint.y;

        Rectangle b = e.getComponent().getBounds();
        Point p = b.getLocation();
        SwingUtilities.convertPointToScreen(p, e.getComponent().getParent());
        p.x -= xDiff;
        p.y -= yDiff;
        if (e.isPopupTrigger()) {
            doPop(e);
        }

        dragWindow.setLocation(p);
        */
    }


    @Override
	public void mousePressed(MouseEvent e) {
        
    	if(e.getSource() instanceof NodeLabel){
    		NodeLabel child = (NodeLabel) e.getSource();
    		JPanel parent =(JPanel)child.getParent();
    		boolean selected= child.isSelected();
    		Component[] comps = parent.getComponents();
    				for(Component c: comps){
    					if(c instanceof NodeLabel){
    						((NodeLabel)c).deselect();
    					}
    				}
    		if(!selected){		
    			child.select();}
    		else{
    			child.deselect();
    		}
    	}
    	
    	pressPoint = e.getPoint();
        selectedComponent = e.getComponent().getComponentAt((int)pressPoint.getX(), (int)pressPoint.getY());
        Rectangle b = selectedComponent.getBounds();
        Point p = b.getLocation();
        SwingUtilities.convertPointToScreen(p, selectedComponent.getParent());
        //if (e.isPopupTrigger()) {
         //   doPop(e);
        //}

        //dragWindow.setBounds(b);
        //dragWindow.setLocation(p);
        //dragWindow.setVisible(true);
    }

    @Override
	public void mouseReleased(MouseEvent e) {
        releasePoint = e.getPoint();
        //dragWindow.setVisible(false);
        
        if(selectedComponent != null){
	        int xDiff = pressPoint.x - releasePoint.x;
	        int yDiff = pressPoint.y - releasePoint.y;
	
	        Rectangle b = selectedComponent.getBounds();
	        Point p = b.getLocation();
	        SwingUtilities.convertPointToScreen(p, selectedComponent.getParent());
	        p.x -= xDiff;
	        p.y -= yDiff;
	
	        SwingUtilities.convertPointFromScreen(p, selectedComponent.getParent());
	        if (p.x <= 0) {
	            p.x = 1;
	        }
	        if (p.x > selectedComponent.getParent().getWidth() - b.width) {
	            p.x = selectedComponent.getParent().getWidth() - b.width;
	        }
	        if (p.y <= 0) {
	            p.y = 1;
	        }
	        if (p.y > selectedComponent.getParent().getHeight() - b.height) {
	            p.y = selectedComponent.getParent().getHeight() - b.height;
	        }
	        //if (e.isPopupTrigger()) {
	         //   doPop(e);
	        //}
	
	        selectedComponent.setLocation(p);
	        selectedComponent.repaint();
	        selectedComponent.getParent().repaint();
        }
        
    }
	
    @Override
	public void mouseEntered(MouseEvent e) {
    	
	}

	
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e){
		Component component = e.getComponent().getComponentAt((int)pressPoint.getX(), (int)pressPoint.getY());
		if(component != null && component instanceof NodeLabel){
			((NodeLabel)component).hover();
		}
	}
    
}
