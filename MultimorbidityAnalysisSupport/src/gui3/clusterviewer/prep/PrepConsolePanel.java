package gui3.clusterviewer.prep;

import javaGD.BNJavaGD;

import javax.swing.JPanel;

import domain.Column.Type;

import java.awt.Dimension;
import gui3.MainFrame;

public class PrepConsolePanel extends JPanel {
	private MainFrame mainframe;
	private JPanel plot;
	private JPanel plot1;
	
	private String[][] values;
	private String[] columnnames;
	private String[] numericColumns;
	private String[] factorColumns;
	private String numbClusters;
	private String clusterColumn;
	private String clusterMethod;
	
	public PrepConsolePanel(MainFrame mainframe, String plotType, String numbClusters, String clusterMethod) {
		
		this.mainframe      = mainframe;
		this.columnnames    = mainframe.getDataSetColumnnames();
		this.values         = mainframe.getDataSetValues();
		this.numericColumns = mainframe.getDataSetNumColumnnames(Type.NUMERIC);
		this.factorColumns  = mainframe.getDataSetColumnnames(Type.FACTOR);
		this.clusterMethod  = mainframe.getClusterMethod();
		this.numbClusters   = mainframe.getNumbClusters();
		this.clusterColumn  = mainframe.getClusterColumn();
		
		init();
		
		if (plotType == "C") {
			mainframe.combineGraphs();
		}
		if (plotType == "B") {
			mainframe.barChartVert(this.clusterColumn);
		}
		if (plotType == "N") {
			mainframe.CalculateNumberOfClusters(this.clusterColumn);
		}
		if (plotType == "S") {
			mainframe.StartClusterProcess(this.clusterColumn, this.numbClusters, this.clusterMethod);
		}

		plot = BNJavaGD.getPanel();
		plot.setBounds(30,30,600,300);
		add(plot);

	}
	
	private void init(){
		this.setLayout(null);
		this.setSize(600, 300);
		this.setPreferredSize(new Dimension(600,1000));
	}
	
}





