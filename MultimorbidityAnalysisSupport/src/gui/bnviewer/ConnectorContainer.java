package gui.bnviewer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import domain.tools.bntool.Association;
import domain.tools.bntool.Network;
import domain.tools.bntool.Node;

/**
 * The collateral class contains array of connectors and renders them.
 * The rendering can be called in a different way. E.g. JConnectors cn be just
 * added as usual component. In this case programmer must care about their size,
 * and layout.
 *
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ConnectorContainer extends JPanel {
	private Network network;
    private JConnector[] connectors;
    private HashMap<Node,NodeLabel> nLabels;
    private ArrayList<Node> nodes;
    private ArrayList<Association> arcs;
    
    
    public ConnectorContainer(Network network) {
    	init();
    	this.network = network;
    	calculate();
    }
    
    private void calculate(){
    	this.arcs = (ArrayList<Association>) network.getArcs();
    	this.nodes = (ArrayList<Node>) network.getNodes();
    	this.nLabels = new HashMap<Node, NodeLabel>();
    	
    	for(Node n : nodes){
    		nLabels.put(n,new NodeLabel(n));
    	}
    	
    	this.connectors = new JConnector[arcs.size()];
    	
    	for(int i = 0; i<arcs.size(); i++){
    		NodeLabel nl1 = nLabels.get(arcs.get(i).getChild());
    		NodeLabel nl2 = nLabels.get(arcs.get(i).getParent());
    		JConnector jc = new JConnector(nl2,nl1,2,JConnector.CONNECT_LINE_TYPE_SIMPLE,Color.BLACK);
    		this.connectors[i] = jc;
    	}
    	
    	for(Node n: nodes){
    		this.add(nLabels.get(n));
    	}
    	
 
    	int r = 220;
    	int x = 400;
    	int y = 300;
    	int angles = nodes.size();
    			
    	for(int i=0; i<angles; i++) {
    		double x1 = x - r*Math.cos((i*2*Math.PI)/angles); 
    		double y1 = y + r*Math.sin((i*2*Math.PI)/angles);
    		(nLabels.get(nodes.get(i))).setBounds((int)x1, (int)y1, 50, 50);
    		
    		}
    	
    	addLabels();
    }
    
    private void init(){
    	this.setLayout(null);
    	this.setSize(500, 500);
    	this.setBackground(Color.WHITE);
    	this.addMouseListener(new MListener());
    }
    
    private void addLabels(){
    	for(NodeLabel nl: nLabels.values()){
    		JLabel jl = new JLabel(nl.getNode().getNodeName());
    		jl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    		int labelsize = nl.getNode().getNodeName().length();
    		
    		int x = (int)nl.getBounds().getCenterX() - ((int)(labelsize*7.5+3)/2);
    		int y = (int)nl.getBounds().getCenterY()+30;
    		
    		JPanel nlabel = new Nlabel(jl);
    		nlabel.setBounds(x, y,(int)(labelsize*7.2+3), 20);
    		//nlabel.setBorder(BorderFactory.createLineBorder(Color.black));
    		this.add(nlabel);
    	}
    }
    
    public void setNetwork(Network network){
    	this.network = network;
    	this.removeAll();
    	calculate();
    }
    
    public ConnectorContainer(JConnector[] connectors) {
        this.connectors = connectors;
    }

    public void setConnectors(JConnector[] connectors) {
        this.connectors = connectors;
    }

    public JConnector[] getConnectors() {
        return connectors;
    }

    @Override
	public void paint(Graphics g) {
        super.paint(g);
        if (connectors != null) {
            for (int i = 0; i < connectors.length; i++) {
                if (connectors[i] != null) {
                    connectors[i].paint(g);
                }
            }
        }
    }
    
    private class Nlabel extends JPanel{
    	private volatile int draggedAtX, draggedAtY;
    	public Nlabel(JLabel name){
    		super();
    		this.add(name);
    		this.setBackground(Color.WHITE);
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
    }
}
