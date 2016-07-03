package gui.clusterviewer.cluster;

import javaGD.JavaGDPanel;
import util.MultimorbidityException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import domain.tools.clustertool.ClusterTool;
import gui.ErrorDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

/**
 * This is the output/result panel for the cluster analysis.
 * @author ABI team 37 
 * @version 1.0
 */
public class ClusterResultPanel extends JPanel implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClusterTool clustertool;
	private JPanel cplot;
	private JFrame mainframe;

	private String plotType;
	private String numbClusters, clusterAttribute, clusterColumn,clusterMethod;
	private String[] selectedNodes;
	
	private JPanel cContent;
    private JTextArea txtArea;
    
	public ClusterResultPanel(ClusterTool clustertool) {
		
		this.clustertool      = clustertool;
		this.plotType         = clustertool.getPlotType();
		this.selectedNodes    = clustertool.getSelectedNodes();
		
		clustertool.addObserver(this);
		setLayout(new BorderLayout(0, 0));
		
		init();
	  	initJavaGD();
		initSummaryPanel();
	}
	
	private void initSummaryPanel() {
		
		if(cContent == null){
			this.txtArea = new JTextArea(); 
			cContent = new JPanel();
			cContent.setBackground(Color.white);
		    cContent.add(txtArea);
			
			JScrollPane cScroll = new JScrollPane(cContent);
			cScroll.setPreferredSize(new Dimension(500,300));
			add(cScroll, BorderLayout.SOUTH);
		}
		
		String plot = this.plotType;
        if (plot == null) {
        	fillSummary();
        } else {
		  switch (plot) {
	    	case "B" :  clustertool.barChartVert(clusterColumn);
	    				fillSummary();
	    			    break;
	    	case "N" :  try {
							clustertool.calculateNumberOfClusters(clusterColumn, selectedNodes);
	    				} catch (MultimorbidityException e) {
	    					new ErrorDialog(mainframe,e.getMessage());
	    				}
	    				fillSummary();
		    			break;
	    	case "S" :  try {
							clustertool.startClusterProcess(clusterColumn, numbClusters, clusterMethod, selectedNodes);
	    				} catch (MultimorbidityException e) {
	    					new ErrorDialog(mainframe,e.getMessage());
	    				}
	    				fillSummary();
						break;
	    	case "CS":  try {
							clustertool.startClusterSummary(clusterColumn, numbClusters, clusterAttribute);
	    				} catch (MultimorbidityException e) {
	    					new ErrorDialog(mainframe,e.getMessage());
	    				}
	    				fillSummary();
						break;
	    	case "DS" : clustertool.barChartVert(clusterColumn);
						fillSummary();
						break;
	    	default: 	fillSummary();
            			break;
		  } 
        }  
	}


	private void fillSummary(){
		String[] output = clustertool.getClusterSummary();
		txtArea.setText("");
		if (output != null && output.length > 0) {
			for(int i=0;i < output.length;i++) {
			      txtArea.setText( txtArea.getText() + "\n"+ output[i]); 
	 	    }		
		} else {
   			String title = "No summary available";
   			txtArea.setText("");
   		    txtArea.setText( txtArea.getText() + "\n"+ title);   			
   		}
   		initJavaGD();
	}
	
	private void initJavaGD() {
		cplot = JavaGDPanel.getPanel();
		cplot.setPreferredSize(new Dimension(500,500));
		add(cplot, BorderLayout.CENTER);
	}

	private void init() {
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(700,900));
	}
	
	@Override
	public void update(Observable o, Object arg) {	
		if(o instanceof ClusterTool){
			initJavaGD();
			initSummaryPanel();
			this.getParent().revalidate();
			this.getParent().repaint();
		}
	}
}





