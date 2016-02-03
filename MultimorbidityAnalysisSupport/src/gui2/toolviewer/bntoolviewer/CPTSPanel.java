package gui2.toolviewer.bntoolviewer;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domain.tools.bntool.Network;
import domain.tools.bntool.Node;



public class CPTSPanel extends JPanel implements Observer{
	
	private Network bnnetwork;
	private JScrollPane jscrollpane;
	
	public CPTSPanel(Network bnnetwork){
		this.bnnetwork = bnnetwork;
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		this.setBackground(Color.WHITE);

		init();
		setup();
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
