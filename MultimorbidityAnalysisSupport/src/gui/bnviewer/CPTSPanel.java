package gui.bnviewer;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import domain.tools.bntool.Network;
import domain.tools.bntool.Node;
import java.awt.BorderLayout;

/**
 * This Panel displays all the CPT values of all the Nodes in the Network.
 * @author ABI team 37
 * @version 1.0
 */
public class CPTSPanel extends JPanel{
	
	private static final long serialVersionUID = -6055416580201673346L;
	private Network bnnetwork;
	private JScrollPane scrollPane;
	private JPanel content;
	
	/**
	 * Constructor.
	 * @param bnnetwork the network who's node values need to be displayed.
	 */
	public CPTSPanel(Network bnnetwork){
		this.bnnetwork = bnnetwork;
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout(0, 0));
		
		content = new JPanel();
		scrollPane = new JScrollPane(content);
		content.setLayout(null);
		add(scrollPane, BorderLayout.CENTER);
		
		setup();
	}
	
	private void setup(){
		if(bnnetwork != null){
			int y = 0;
			for(Node n: bnnetwork.getNodes()){			
				CPTPanel cptp = new CPTPanel(n);
				//cptp.setBounds(50, y, 1000, cptp.getTSize());
				cptp.setLocation(50, y);
				content.add(cptp);
				y=y+cptp.getTSize();
			}
			this.setPreferredSize(new Dimension(1100,y));
		}
		
	}

}
