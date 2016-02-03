package poc2.gui.panels;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import poc2.domain.Network;
import poc2.domain.BNTool;
import poc2.domain.Node;

public class CPTSPanel extends JPanel implements Observer{
	
	private Network bnnetwork;
	private JScrollPane jscrollpane;
	
	public CPTSPanel(Network bnnetwork){
		this.bnnetwork = bnnetwork;
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		this.setBackground(Color.WHITE);
		//this.cpanel = new CPanel();
		//cpanel.setSize(200, 200);
		//cpanel.setBackground(Color.RED);
		//this.jscrollpane = new JScrollPane(cpanel);
		//this.jscrollpane.add(cpanel);
		
		
		
		
		
		
		
		init();
		setup();
		//this.setVisible(true);
	}
	
	private void init(){
		
		if(bnnetwork !=null){
		bnnetwork.addObserver(this);
		}
	}
	
	
	private void setup(){
		this.removeAll();
		if(bnnetwork != null){
			for(Node n: bnnetwork.getNodes()){			
				CPTPanel cptp = new CPTPanel(n);
				this.add(cptp);
				JLabel space = new JLabel();
				space.setSize(50,50);
				this.add(space);
			}
		}
		this.validate();
		this.repaint();
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		setup();
		this.setBackground(Color.RED);
		System.out.println("update");
		
	}
	
	public void setBNNetwork(Network bn){
		this.bnnetwork = bn;
		setup();
	}
}
