package poc1.gui.net;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import poc1.domain.Node;

public class NodeLabel extends JPanel {
    
	private static final long serialVersionUID = 5708333422632992932L;
	private Node node;
	private boolean selected =false;
	private boolean hover = false;
    
    public NodeLabel(Node node) {
        super();
    	this.node = node;
    	this.addMouseListener(new NLMListener());
    	this.setOpaque(false);
    	
    }
    
    public Node getNode(){
    	return node;
    }
    
    public void select(){
    	if(selected){
    		selected = false;
    	}else{
    		selected = true;}
    	repaint();
    }
    public boolean isSelected(){
    	return selected;
    }
    public void deselect(){
    	selected = false;
    	repaint();
    }
    
    public void hover(){
    	hover = true;
    	repaint();
    	
    }
    
    public void exit(){
    	hover = false;
    	repaint();
    }
    
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

