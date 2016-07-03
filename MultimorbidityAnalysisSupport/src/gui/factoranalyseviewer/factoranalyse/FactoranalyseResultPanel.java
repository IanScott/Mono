package gui.factoranalyseviewer.factoranalyse;

import javaGD.JavaGDPanel;
import util.MultimorbidityException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import domain.tools.datatool.DataTool;
import domain.tools.factoranalysetool.FactoranalyseTool;
import gui.ErrorDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import java.awt.BorderLayout;

/**
 * This is the output/result panel for the factor analysis.
 * @author ABI team 37 
 * @version 1.0
 */
public class FactoranalyseResultPanel extends JPanel implements Observer{
	private static final long serialVersionUID = 1L;
	private FactoranalyseTool factoranalysetool;
	private JPanel cplot;
	private JFrame mainframe;

	private String plotType;
	private String[] numericList ;  
	private String[] selectedNodes;
	private String numbOfFactors, rotationType;
	
	private JPanel cContent;
    private JTextArea txtArea;
    
    /**
     * Constructor.
     * @param datatool the datatool instance to use
     * @param factoranalysetool the factoranalysetool to use
     */
	public FactoranalyseResultPanel(DataTool datatool, FactoranalyseTool factoranalysetool) {
		
		this.factoranalysetool  = factoranalysetool;	
		this.plotType       	= factoranalysetool.getPlotType();
		this.numericList        = datatool.getDataSetColumnnames();
		this.selectedNodes    	= factoranalysetool.getSelectedNodes();
		
		factoranalysetool.addObserver(this);
		setLayout(new BorderLayout(0, 0));
		
		init();
	  	initJavaGD();
		initSummaryPanel();
	}
	
	private void initSummaryPanel() {
		
		String title = "";
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
        	fillSummary(title);
        } else {
        	if (plotType == "B") {
				try {
					factoranalysetool.startDataSummary("");
				} catch (MultimorbidityException e) {
					new ErrorDialog(mainframe, e.getMessage());
				}
        		title = "Data summary of the whole dataset: \n" ;
        		fillSummary(title);
        	}
        	if (plotType == "DS") {
        		try {
					factoranalysetool.determineNumbOfFactors(this.selectedNodes);
				} catch (MultimorbidityException e) {
					new ErrorDialog(mainframe,e.getMessage());
				}
        		title = "Look for which factor, the Proportion of Variance has no significance: \n" ;
        		fillSummary(title);
        	}
        	if (plotType == "EFA") {
        		try {
					factoranalysetool.startEfa(numbOfFactors,rotationType,this.selectedNodes);
				} catch (MultimorbidityException e) {
					new ErrorDialog(mainframe,e.getMessage());
				}
        		title = "Exploratory Factor Analysis (EFA): \n" ;
        		fillSummary(title);
        	}
        	if (plotType == "EFAS") {
        		try {
					factoranalysetool.efaSummary(numbOfFactors, rotationType, this.selectedNodes);
				} catch (MultimorbidityException e) {
					new ErrorDialog(mainframe,e.getMessage());
				}
        		title = "Exploratory Factor Analysis (EFA): Scores\n" ;
        		fillSummary(title);
        	}
        	if (plotType == "CFA") {
        		try {
					factoranalysetool.startCfa(numericList);
				} catch (MultimorbidityException e) {
					new ErrorDialog(mainframe,e.getMessage());
				}
        		title = "Confirmatory Factor Analysis (CFA): \n" ;
        		fillSummary(title);
        	}
        }
	}
	
   	private void fillSummary(String title){

		String[] output = factoranalysetool.getSummary();
		txtArea.setText("");
	    txtArea.setText( txtArea.getText() + "\n"+ title);
   		if (output != null && output.length > 0) {
   			for(int i=0;i < output.length;i++) {
   			      txtArea.setText( txtArea.getText() + "\n"+ output[i]); 
   	 	    }		
   		} else {
   			title = "No summary available";
   			txtArea.setText("");
   		    txtArea.setText( txtArea.getText() + "\n"+ title);   			
   		}
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
		initJavaGD();
		initSummaryPanel();
		this.getParent().revalidate();
		this.getParent().repaint();
	}
}
