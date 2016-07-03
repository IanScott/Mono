package gui.associationviewer.association;

import javaGD.JavaGDPanel;
import util.MultimorbidityException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import domain.tools.associationtool.AssociationTool;
import domain.tools.datatool.DataTool;
import gui.ErrorDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import java.awt.BorderLayout;

/**
 * This is the output/result panel for the association analysis.
 * @author ABI team 37 
 * @version 1.0
 */
public class AssociationResultPanel extends JPanel implements Observer{
	private static final long serialVersionUID = 1L;
	private AssociationTool associationtool;
	private JPanel cplot;
	private JFrame mainframe;

	private String plotType;
	private String column1, column2,column11, column22 , useType, metType, alternative, confLevel, oddConfLevel, oddMethodType;
	
	private JPanel cContent;
    private JTextArea txtArea;
	
    /**
     * Constructor.
     * @param datatool the datatool instance to use
     * @param associationtool the asscoiationtool instance to use
     */
	public AssociationResultPanel(DataTool datatool, AssociationTool associationtool) {
		
		this.associationtool   	= associationtool;
		this.plotType       	= associationtool.getPlotType();
			
		associationtool.addObserver(this);
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
			//cScroll.setBounds(30, 400, 610, height);
			cScroll.setPreferredSize(new Dimension(500,300));
			add(cScroll, BorderLayout.SOUTH);
		}
		
		String plot = this.plotType;
        if (plot == null) {
        	fillSummary(title);
        } else {
		  switch (plot) {
	    	case "DC" : associationtool.barChartVert(column1);
	    				break;
	    	case "BG2": associationtool.chartScatter(column11,column22);
		    			break;
	    	case "DS" : try {
				          associationtool.startDataSummary(column1);
			            } catch (MultimorbidityException e1) {
				          new ErrorDialog(mainframe, e1.getMessage());
			            }
	    				title = "Data summary of the whole dataset: \n" ;
	    				fillSummary(title);
		    			break;
	    	case "DS2": try {
				          associationtool.startDataSummary(column11);
			            } catch (MultimorbidityException e1) {
				          new ErrorDialog(mainframe, e1.getMessage());
			            }
	    				title = "Data summary of the whole dataset: \n" ;
	    				fillSummary(title);
		    			break;
	    	case "CO" : try {
							associationtool.determineCorrelation(column1, column2,useType, metType);
	    				} catch (MultimorbidityException e) {
	    					new ErrorDialog(mainframe,e.getMessage());
	    				}
	    				title = "Correlation between " + column1  + " and " + column2 +  " is equal: \n" ;
	    				fillSummary(title);
		    			break;
	    	case "DT" : try {
							associationtool.determineCorrelationTest(column1, column2,alternative, confLevel,metType);
	    				} catch (MultimorbidityException e) {
	    					new ErrorDialog(mainframe,e.getMessage());
	    				}
	    				title = "Correlation test between " + column1  + " and " + column2 + ": \n" ;
	    				fillSummary(title);
	    				break;
	    	case "OD" : try {
							associationtool.determineRRandOdd(column11, column22,oddConfLevel,oddMethodType);
	    				} catch (MultimorbidityException e) {
	    					new ErrorDialog(mainframe,e.getMessage());
	    				}
	    				title = "Relative risk and Odd Ratio: "+  column11  + " and " + column22 + ": \n" ;
	    				fillSummary(title);
						break;
	    	default: 	fillSummary(title);
	    				break;
		  	} 
        }  
	}
    	
   	private void fillSummary(String title){
	  	String[] output = associationtool.getAssociationSummary();
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
			cplot.setPreferredSize(new Dimension(500,350));
			add(cplot, BorderLayout.CENTER);
	}

	private void init() {
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(700,900));
	}
	
	@Override
	public void update(Observable o, Object arg) {	
		if(o instanceof AssociationTool){
			initJavaGD();
			initSummaryPanel();
			initJavaGD();
			this.getParent().revalidate();
			this.getParent().repaint();
		}
	}

	
}





