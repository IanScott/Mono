package gui.clusterviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import domain.tools.clustertool.ClusterTool;
import domain.tools.datatool.DataTool;
import gui.ToolViewer;
import gui.clusterviewer.cluster.ClusterMenu;
import gui.clusterviewer.cluster.ClusterResultPanel;


/**
 * The mainpanel of the Cluster viewer.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class ClusterViewer extends ToolViewer{
	private static final long serialVersionUID = 3561758092955571027L;

	private JPanel preparePanel;
	private JPanel resultPanel;
	private DataTool datatool;
	private ClusterTool clusteranalysetool;
	private JFrame mainframe;
	private JScrollPane centerpanel;
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame
	 * @param datatool the datatool instance used
	 * @param clusteranalysetool the clusteranalystool instace to use
	 */
	public ClusterViewer(JFrame mainframe,DataTool datatool, ClusterTool clusteranalysetool) {		
		this.datatool = datatool;
		this.clusteranalysetool = clusteranalysetool;
		this.mainframe = mainframe;
		this.setLayout(new BorderLayout(0, 0));
		setup();
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setBackground(Color.WHITE);
		
	}
	
	private void setup(){

		preparePanel = new ClusterMenu(mainframe, this, datatool, clusteranalysetool);
		preparePanel.setPreferredSize(new Dimension(300,900));
		
		
		JScrollPane scroll = new JScrollPane(preparePanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setSize(new Dimension(300,900));
		this.add(scroll, BorderLayout.WEST);
		
		setupCenterPanel();
	}
	
	private void setupCenterPanel(){
		if(centerpanel == null){
			centerpanel = new JScrollPane(resultPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			this.add(centerpanel, BorderLayout.CENTER);
		}else{
			centerpanel.removeAll();
		}
		resultPanel = new ClusterResultPanel(clusteranalysetool);
		
		centerpanel.getViewport().add(resultPanel);
	}
	
}

