package gui.bnviewer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;

import domain.tools.bntool.Node;

/**
 * This Panel is used to display individual nodes on the ConnectorContainer.
 * @author ABI team 37
 * @version 1.0
 */
public class NodeLabel extends JPanel {
    
	private static final long serialVersionUID = 5708333422632992932L;
	private Node node;
	private boolean selected =false;
	private boolean hover = false;
	private volatile int draggedAtX, draggedAtY;
	
	/**
	 * Constructor.
	 * @param node the node who's lable must be displayed
	 */
    public NodeLabel(Node node) {
        super();
    	this.node = node;
    	this.addMouseListener(new NLMListener());
    	this.setOpaque(false);
    	
    	addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                draggedAtX = e.getX();
                draggedAtY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                setLocation(e.getX() - draggedAtX + getLocation().x,
                        e.getY() - draggedAtY + getLocation().y);
            }
        });
    	
    }
    
    /**
     * Getter for the Node.
     * @return the node
     */
    public Node getNode(){
    	return node;
    }
    
    /**
     * Method for making nodes selected.
     */
    public void select(){
    	if(selected){
    		selected = false;
    	}else{
    		selected = true;}
    	repaint();
    }
    
    /**
     * Check is node is selected.
     * @return true or false
     */
    public boolean isSelected(){
    	return selected;
    }
    
    /**
     * Method deselects the Node.
     */
    public void deselect(){
    	selected = false;
    	repaint();
    }
    
    /**
     * Set the hover attribute.
     */
    public void hover(){
    	hover = true;
    	repaint();
    	
    }
    
    /**
     *Deselects the hover attribute.
     */
    public void exit(){
    	hover = false;
    	repaint();
    }
    
    @Override
	public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        //g2.setPaint(Color.WHITE);
        //g2.fillRect(0, 0, 50, 50);
        
        if(hover){
        	g2.setStroke(new BasicStroke(3));
    	}
        if(!hover){
        	g2.setStroke(new BasicStroke(2));
        }
        g2.setPaint(Color.BLACK);
        
        if(selected){
        	g2.fillOval(1, 1, 48, 48);
        }
                	  
        g2.drawOval(1, 1, 48, 48);
        
    }
    
    
}

