package gui.bnviewer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import domain.tools.bntool.Node;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

/**
 * This panel displays all the CPT tabel of a node.
 * @author ABI team 37
 * @version 1.0
 */
public class CPTPanel extends JPanel{
	
	private static final long serialVersionUID = -2912386960797238362L;
	private JLabel nodeName;
	private BnTable bntable;
	
	/**
	 * Contructor.
	 * @param node the nodes who's cpt table to display
	 */
	public CPTPanel(Node node){
		this.nodeName = new JLabel(node.getNodeName());
		this.nodeName.setFont(new Font("Tahoma", Font.BOLD, 16));
		this.nodeName.setText(node.getNodeName());
		this.setLayout(new BorderLayout(0, 0));
		
		this.nodeName.setSize(200, 50);
		this.add(nodeName, BorderLayout.NORTH);
		this.bntable = new BnTable(node);
		this.add(bntable);
		this.setSize(new Dimension(bntable.getHead().length*150, bntable.getTSize()));
	}
	
	/**
	 * Method for returning the size of the table.
	 * @return the height of the panel.
	 */
	public int getTSize(){
		return bntable.getTSize();
	}
		
	
}
