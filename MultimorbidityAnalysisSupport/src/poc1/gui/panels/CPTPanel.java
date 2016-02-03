package poc1.gui.panels;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import poc1.domain.Node;
import poc1.gui.net.BNTable;

public class CPTPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2912386960797238362L;
	private JLabel nodeName;
	//private JTable probTable;
	private BNTable bntable;
	//private JScrollpane = scrollpane = new JScrollPane(table);
	
	public CPTPanel(Node node){
		this.nodeName = new JLabel(node.getNodeName());
		nodeName.setText(node.getNodeName());
		//this.probTable = new JTable(node.getCPT().getTable(),node.getCPT().getLevels());
		this.bntable = new BNTable(node);
		init();
	}
	private void init(){
		this.setSize(200, 200);
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		nodeName.setSize(200, 50);
		this.add(nodeName);
		
		//probTable.setBounds(0,100,200,200);
		//this.add(probTable.getTableHeader());
		//this.add(probTable);
		this.add(bntable);
	}
	
}
