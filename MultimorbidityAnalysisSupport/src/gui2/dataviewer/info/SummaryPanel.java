package gui2.dataviewer.info;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import gui2.MainFrame;
import javaGD.BNJavaGD;
import java.awt.Color;


public class SummaryPanel extends JPanel{
	private FreqPanel freqpanel;
	private MainFrame mainframe;
	private JPanel plot;
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public SummaryPanel(MainFrame mainframe){
		init(mainframe, null);
	}
	
	public SummaryPanel(MainFrame mainframe, String columnname) {
		init(mainframe, columnname);
	}
	

	
	private void init(MainFrame mainframe, String columnname){
		
		this.mainframe = mainframe;
		//mainframe.test();
		setLayout(null);
		
		freqpanel = new FreqPanel(mainframe, columnname);
		mainframe.barChart(getSelectedColumn());
		freqpanel.setBounds(0, 0, 600, 300);
		add(freqpanel);
		
		
		plot = BNJavaGD.getPanel();
		plot.setBounds(0,300,600,600);
		
		add(plot);
		
		
			
		setup(columnname);
	}
	
	private void setup(String columnname){
		freqpanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
	
		setSize(600, 447);
		//this.setBackground(Color.RED);
	}
	
	public String getSelectedColumn(){
		return freqpanel.getSelectedColumn();
	}
	
	

}

