package gui3.clusterviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import domain.Column.Type;
import gui3.MainFrame;
import gui3.clusterviewer.prep.PrepConsolePanel;
import gui3.clusterviewer.prep.PrepDataPanel;
import gui3.dataviewer.edit.EditPanel;
import gui3.start.DataPreviewerDialog;

import java.util.Observable;
import java.util.Observer;


public class ClusterviewerPanel extends JPanel implements Observer{
	private static final long serialVersionUID = 3561758092955571027L;
	private JPanel tabPanel;

	private JPanel prepDataPanel;
	private JPanel controlpanel;
	private JPanel editPane;
	private JPanel rightpanel;
	private JPanel leftpanel;
	private MainFrame mainframe;
	private JSplitPane splitPane;
	private String[][] values;
	private String[] columnnames;
	private String[] numericColumns;
	private String[] factorColumns;
	private String clusterColumn; 
	private String clusterMethod; 
	private String numbClusters; 
	
	private final static String PLOTTYPE        = "B";
	private final static String NUMOFCLUSTERS   = "0";
	private final static String METHOD          = "Partitioning method kmeans";

	
	public ClusterviewerPanel(MainFrame mainframe) {		
		this.mainframe = mainframe;
		this.setLayout(new BorderLayout(0, 0));
		this.setupPanels(mainframe, PLOTTYPE, NUMOFCLUSTERS, METHOD);
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setBackground(Color.WHITE);
		mainframe.addObserver(this);
	}
	
	private void setupPanels(MainFrame mainframe, String plotType, String numbClusters, String method){

		//Tab panel CLUSTERING
		tabPanel = new JPanel();
		tabPanel.setLayout(new BorderLayout(0, 0));
		this.add(tabPanel, BorderLayout.CENTER);
		
		//reset and new import (buttons)
		setupControlPanel();
		add(controlpanel, BorderLayout.NORTH);
		
		//Graphs and Clustering results
		//Split plane:
		// - left  : clustering form (PrepDataPanel)
		// - right : Graphs and clustering results
		setupSplitPane(plotType, numbClusters, method);
		
		//Dataset Editing
		setupEditPane();
	}

	
	private void setupSplitPane(String plotType, String numbClusters, String method){
		if(leftpanel != null){
			this.remove(leftpanel);
		}
		leftpanel = new JPanel();
		leftpanel.setPreferredSize(new Dimension(600, 1000));
		leftpanel.setLayout(new BorderLayout(0, 0));
		this.add(leftpanel, BorderLayout.CENTER);
			
		setupEditPane();
		prepDataPanel = new PrepDataPanel(mainframe);
		PrepConsolePanel prepPanel = new PrepConsolePanel(mainframe, plotType, numbClusters, method);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, prepDataPanel, prepPanel);
		splitPane.setContinuousLayout(true);
		splitPane.setForeground(SystemColor.inactiveCaption);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(600);
		splitPane.setSize(700, 1000);
		leftpanel.add(splitPane, BorderLayout.CENTER);
	}

	private void setupEditPane(){
		if(rightpanel != null){
			this.remove(rightpanel);
		}
		rightpanel = new JPanel();
		rightpanel.setPreferredSize(new Dimension(610, 1000));
		rightpanel.setLayout(new BorderLayout(0, 0));
		this.add(rightpanel, BorderLayout.EAST);
		
		editPane = new EditPanel(mainframe);
		editPane.setSize(700, 1000);
		rightpanel.add(editPane, BorderLayout.CENTER);
	}

	private void setupControlPanel(){
		controlpanel = new JPanel();
		controlpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton importButton = new JButton("Reset");
		importButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				actionImport();
			}});
		controlpanel.add(importButton);
		
		JButton importButton1 = new JButton("Import");
		importButton1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				actionImport();
			}});
		controlpanel.add(importButton1);
		
	}
	
	@Override
	public void update(Observable o, Object arg) {

		this.columnnames = mainframe.getDataSetColumnnames();
		this.values = mainframe.getDataSetValues();
		this.numericColumns = mainframe.getDataSetNumColumnnames(Type.NUMERIC);
		this.factorColumns  = mainframe.getDataSetColumnnames(Type.FACTOR);
		this.clusterColumn  = mainframe.getClusterColumn();
		this.numbClusters   = mainframe.getNumbClusters();
		this.clusterMethod  = mainframe.getClusterMethod();
		
		setupSplitPane(mainframe.getPlotType(), this.numbClusters, this.clusterMethod);

		setupEditPane();
		this.validate();
		this.repaint();
	}


	private void actionImport(){
		JFileChooser fc = new JFileChooser();
		File file = null;
		fc.setDialogTitle("Choose File");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Comma Separated Value", "csv"));
        //fc.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Excel", "xls", "xlsx"));
        fc.setAcceptAllFileFilterUsed(false);
		
		
		int returnVal = fc.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			String filename = file.toString();
			String ext = filename.substring(filename.lastIndexOf("."),filename.length());
			
			if(ext.equals(".csv")){
				DataPreviewerDialog dp = new DataPreviewerDialog(mainframe,file);
				
				if(dp.getAnswer()){
					mainframe.importDataSet(file,dp.getSeparator(),dp.hasHead());
				}
			}
			else if(ext.equals(".xlsx")){
				//mainframe.importDataSet(file, true);
			}
		}
		
	}


}
