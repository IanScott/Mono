package gui3.clusterviewer.prep;

import javaGD.BNJavaGD;

import javax.swing.JPanel;

import domain.Column.Type;

import java.awt.Dimension;
import gui3.MainFrame;

public class PrepSummaryPanel extends JPanel {
	private MainFrame mainframe;
	private JPanel plot;
	private JPanel plot1;
	
	private String[][] values;
	private String[] columnnames;
	private String[] numericColumns;
	private String[] factorColumns;
	
	public PrepSummaryPanel(MainFrame mainframe) {
		this.mainframe = mainframe;
		this.columnnames = mainframe.getDataSetColumnnames();
		this.factorColumns = mainframe.getDataSetColumnnames(Type.FACTOR);
		this.numericColumns = mainframe.getDataSetColumnnames(Type.NUMERIC);

		
		init();
		
		mainframe.barChartHoriz(columnnames[0]);
		plot = BNJavaGD.getPanel();
		plot.setBounds(30,350,600,300);
		add(plot);

	}
	
	private void init(){
		this.setLayout(null);
		this.setSize(600, 300);
		this.setPreferredSize(new Dimension(600,1000));
	}
	
}





