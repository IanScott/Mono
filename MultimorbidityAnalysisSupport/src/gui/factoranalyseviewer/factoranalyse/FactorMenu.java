package gui.factoranalyseviewer.factoranalyse;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import domain.tools.datatool.DataTool;
import domain.tools.factoranalysetool.FactoranalyseTool;
import gui.ErrorDialog;
import gui.InfoDialog;
import gui.MListSelectionModel;
import util.MultimorbidityException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 * This is the Control Menu to the factoranalyse viewer.
 * @author ABI team 37 
 * @version 1.0
 */
public class FactorMenu extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private JTextField factorstextField;
	private DataTool datatool;
	private FactoranalyseTool factoranalysetool;
	private JFrame mainframe;
	private JPanel mainpanel;
	private JList<String> numericList;
	private String[] selectedNodes;

	private JComboBox<String> cb_RotationMethod;
	private JComboBox<String> cb_CFAMethod;
	
	private JButton determineButton;
	private JButton efaButton;
	private JButton scoreButton;
	private JButton btnStartCfa;
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame to use
	 * @param mainpanel the main JPanel of the viewer
	 * @param datatool the datatool instance to use
	 * @param factoranalysetool the factoranalysetool instance to use
	 */
	public FactorMenu(JFrame mainframe,JPanel mainpanel,DataTool datatool, FactoranalyseTool factoranalysetool) {
		this.factoranalysetool    = factoranalysetool;	
		this.datatool = datatool;
		this.mainframe = mainframe;
		this.mainpanel = mainpanel;
		datatool.addObserver(this);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(300,700));
		
		setup();
		setToolTips();
	}
	
	private void setup(){
		JLabel lblAssociationAnalysis = new JLabel("Factor Analysis");
		lblAssociationAnalysis.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAssociationAnalysis.setBounds(10, 10, 164, 30);
		add(lblAssociationAnalysis);
		
		JButton infoButton = new JButton("INFO");
		infoButton.setBounds(200, 2, 75, 20);
		add(infoButton);
		infoButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new InfoDialog(mainframe, new File("resources/factor.txt"));			
			}});
		
		setupstep1();
		
		JLabel lblNewLabel_2 = new JLabel("Exploratory Factor Analysis");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(10, 228, 280, 19);
		add(lblNewLabel_2);
		
		setupstep2();
		setupstep3();
		
		JLabel lblConfimoyfactorAnalysis = new JLabel("Confirmatory Factor Analysis");
		lblConfimoyfactorAnalysis.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblConfimoyfactorAnalysis.setBounds(10, 544, 280, 19);
		add(lblConfimoyfactorAnalysis);
		
		setupstep4();
	}
	
	private void setupstep1(){
		JPanel step1panel = new JPanel();
		step1panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step1panel.setBounds(10, 46, 280, 170);
		add(step1panel);
		step1panel.setLayout(null);
		
		JLabel lblStep = new JLabel("Step 1. Setup");
		lblStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStep.setBounds(10, 10, 258, 16);
		step1panel.add(lblStep);
		
		JLabel lblNewLabel = new JLabel("Only numeric Columns are displayed.");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 25, 258, 16);
		step1panel.add(lblNewLabel);
		
		JLabel lblIfListIs = new JLabel("If list is empty use Edit Data Menu");
		lblIfListIs.setForeground(Color.RED);
		lblIfListIs.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIfListIs.setBounds(10, 40, 258, 16);
		step1panel.add(lblIfListIs);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 82, 258, 80);
		step1panel.add(scrollPane);
		
		numericList = new JList<String>();
		numericList.setSelectionModel(new MListSelectionModel());
		numericList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkbutton();
				checkFactors();
			}});
		initList();
		
		scrollPane.setViewportView(numericList);
		
		JLabel lblNewLabel_1 = new JLabel("Select variables (multi select)");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 67, 258, 14);
		step1panel.add(lblNewLabel_1);
	}
	
	private void initList(){
		if(numericList == null){
			return;
		}
		
		DefaultListModel<String> dlm = new DefaultListModel<>();
		numericList.removeAll();
		String [] columnnames = datatool.getDataSetNumColumnnames();
		for(String s: columnnames){
			dlm.addElement(s);
		}
		numericList.setModel(dlm);
	}
	
	private void setupstep2(){
		JPanel step2panel = new JPanel();
		step2panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step2panel.setBounds(10, 250, 280, 70);
		add(step2panel);
		step2panel.setLayout(null);
		
		JLabel lblStep_1 = new JLabel("Step 2.");
		lblStep_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStep_1.setBounds(10, 11, 258, 16);
		step2panel.add(lblStep_1);
		
		determineButton = new JButton("Nothing Selected");
		determineButton.setEnabled(false);
		determineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				determineNumbOfFactors();
			}
		});
		determineButton.setBounds(10, 35, 258, 23);
		step2panel.add(determineButton);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(10, 32, 46, 14);
		step2panel.add(lblNewLabel_3);
		
	}
	
	private void setupstep3(){
		JPanel step3panel = new JPanel();
		step3panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step3panel.setBounds(10, 330, 280, 200);
		add(step3panel);
		step3panel.setLayout(null);
		
		JLabel lblStep_2 = new JLabel("Step 3.");
		lblStep_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStep_2.setBounds(10, 11, 258, 16);
		step3panel.add(lblStep_2);
		
		JLabel lblNewLabel_4 = new JLabel("Enter number of factors");
		lblNewLabel_4.setBounds(10, 38, 258, 14);
		step3panel.add(lblNewLabel_4);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		factorstextField = new JTextField();
		factorstextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
		    	checkFactors();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
		    	checkFactors();				
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
		    	checkFactors();;			
			}
		});
		
		factorstextField.setBounds(10, 52, 114, 20);
		step3panel.add(factorstextField);
		factorstextField.setColumns(10);

		
		JLabel lblNewLabel_5 = new JLabel("Select Rotation method");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(10, 85, 258, 14);
		step3panel.add(lblNewLabel_5);
		
		String[] rotationTypes = {"varimax", "promax"};
		cb_RotationMethod = new JComboBox<String>(rotationTypes);
		
		
		cb_RotationMethod.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				rotationMethodOnChange();
				
			}});
			
		cb_RotationMethod.setBounds(10, 100, 258, 20);
		step3panel.add(cb_RotationMethod);
		
		efaButton = new JButton("Start EFA");
		efaButton.setEnabled(false);
		efaButton.setBounds(10, 140, 258, 23);
		efaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startEFA(); 
			}
		});
		step3panel.add(efaButton);
		
		scoreButton = new JButton("Score");
		scoreButton.setEnabled(false);
		scoreButton.setBounds(10, 170, 258, 23);
		scoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eFASummary(); 
			}
		});
		step3panel.add(scoreButton);
	}

	private void setupstep4(){
		JPanel step4panel = new JPanel();
		step4panel.setLayout(null);
		step4panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step4panel.setBounds(10, 564, 280, 140);
		add(step4panel);
		
		JLabel lblStep_3 = new JLabel("Step 4.");
		lblStep_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStep_3.setBounds(10, 11, 258, 16);
		step4panel.add(lblStep_3);
		
		JLabel lblSelectMethod = new JLabel("Select method");
		lblSelectMethod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectMethod.setBounds(10, 38, 258, 14);
		step4panel.add(lblSelectMethod);
		
		String[] cFAMethods= {"lavaan", "sem"};	
		cb_CFAMethod = new JComboBox<String>(cFAMethods);
		cb_CFAMethod.setEnabled(false);
		cb_CFAMethod.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {			
				cFAMethodOnChange();			
			}});
		cb_CFAMethod.setBounds(10, 52, 258, 20);
		step4panel.add(cb_CFAMethod);
		
		btnStartCfa = new JButton("Nothing Selected");
		btnStartCfa.setEnabled(false);
		btnStartCfa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startCFA(); 
			}
		});
		btnStartCfa.setBounds(10, 83, 258, 23);
		step4panel.add(btnStartCfa);
	}
	
	private void rotationMethodOnChange(){
		if ( !((gui.ToolViewer) mainpanel).isActive() ) {
			return;
		}
		factoranalysetool.setPlotType("DS");
	}

	private void cFAMethodOnChange(){
		if ( !((gui.ToolViewer) mainpanel).isActive() ) {
			return;
		}
		factoranalysetool.setPlotType("DS2");
	}

	private void determineNumbOfFactors(){
		if ( !((gui.ToolViewer) mainpanel).isActive() ) {
			return;
		}

		factoranalysetool.setPlotType("DS");
		if (numericList != null ) {
   		   List<String> temp = numericList.getSelectedValuesList();
   		   selectedNodes = temp.toArray(new String[temp.size()]);
	       factoranalysetool.setSelectedNodes(selectedNodes);
    	   if (selectedNodes.length != 0) {
    		   try {
				factoranalysetool.determineNumbOfFactors(selectedNodes);
				} catch (MultimorbidityException e) {
					new ErrorDialog(mainframe,e.getMessage());
				}
    	   } else {
				String message = "Step1: no variables selected. You may have to import a new dataset";	    		   
				new ErrorDialog(mainframe, message);
    	   }
		} else {
				String message = "There no variables selected. Please import a correct dataset.";
				new ErrorDialog(mainframe, message);
		}
	}

	private void startEFA(){
		if ( !((gui.ToolViewer) mainpanel).isActive() ) {
			return;
		}

		factoranalysetool.setPlotType("EFA");
		
		String numbOfFactors   = factorstextField.getText();
		String efaRotation     = (String)cb_RotationMethod.getSelectedItem();

		if (numericList != null && numericList.getModel().getSize() != 0 ) {
	   		   List<String> temp = numericList.getSelectedValuesList();
	   		   selectedNodes = temp.toArray(new String[temp.size()]);
		       factoranalysetool.setSelectedNodes(selectedNodes);
		       if (selectedNodes.length != 0) {
	    		   if (numbOfFactors != null && !numbOfFactors.isEmpty()) {
	    			   if (efaRotation == null || efaRotation.isEmpty()) {
	    				   String message = "Rotation type is empty. Default will be used: Varimax.";
	    				   new ErrorDialog(mainframe, message);
	    			   } 
   					   try {
   						   factoranalysetool.startEfa(numbOfFactors, efaRotation, selectedNodes);
   					   } catch (MultimorbidityException e) {
   						   new ErrorDialog(mainframe,e.getMessage());
   					   }
    				   cb_RotationMethod.setSelectedItem("varimax");
	    		   } else {
	    			   String message = "Please enter the number of Factors for the EFA.";
	    			   new ErrorDialog(mainframe, message);
	    		   }
	    	   } else {
	    		   String message = "Please select variables that are going to be used for EFA.";
	    		   new ErrorDialog(mainframe, message);
	    	   } 
		} else {
 		   String message = "There are no variables available. Please import a correct dataset.";
 		  new ErrorDialog(mainframe, message);
		}
	}

	private void eFASummary(){
		if ( !((gui.ToolViewer) mainpanel).isActive() ) {
			return;
		}

		factoranalysetool.setPlotType("EFAS");
		String numbOfFactors   = factorstextField.getText();
		String efaRotation     = (String)cb_RotationMethod.getSelectedItem();

		if (numericList != null && numericList.getModel().getSize() != 0 ) {
	   		   List<String> temp = numericList.getSelectedValuesList();
	   		   selectedNodes = temp.toArray(new String[temp.size()]);
		       factoranalysetool.setSelectedNodes(selectedNodes);
	    	   if (selectedNodes.length != 0) {
	    		   if (numbOfFactors != null && numbOfFactors != "") {
	    			   if (efaRotation == null && efaRotation != "") {
	    				   String message = "Rotation type is empty. Default will be used: Varimax.";
	    				   new ErrorDialog(mainframe, message);
	    			   }
    					try {
							factoranalysetool.efaSummary(numbOfFactors, efaRotation, selectedNodes);
    					} catch (MultimorbidityException e) {
    						new ErrorDialog(mainframe,e.getMessage());
    					}
	    				cb_RotationMethod.setSelectedItem("varimax");
	    		   } else {
	    			   String message = "Please enter the number of Factors for the EFA.";
	    			   new ErrorDialog(mainframe, message);
	    		   }
	    	   } else {
	    		   String message = "Please select variables that are going to be used for EFA.";
	    		   new ErrorDialog(mainframe, message);
	    	   } 
		} else {
 		   String message = "There are no variables available. Please import a correct dataset.";
 		  new ErrorDialog(mainframe, message);
		}
	}
	
	private void startCFA(){
		if ( !((gui.ToolViewer) mainpanel).isActive() ) {
			return;
		}

		factoranalysetool.setPlotType("CFA");
		String cfaMethod = (String)cb_CFAMethod.getSelectedItem();
		if (numericList != null && numericList.getModel().getSize() != 0 ) {
	   		   List<String> temp = numericList.getSelectedValuesList();
	   		   selectedNodes = temp.toArray(new String[temp.size()]);
		       factoranalysetool.setSelectedNodes(selectedNodes);
	    	   if (selectedNodes.length != 0) {
	    		   if (cfaMethod == "sem") {
	    			   String message = "Sem is not yet implemented. Default will be used: lavaan.";
	    			   new ErrorDialog(mainframe, message);
	    		   }
    				try {
						factoranalysetool.startCfa(selectedNodes);
    				} catch (MultimorbidityException e) {
    					new ErrorDialog(mainframe,e.getMessage());
    				}
	    		    cb_CFAMethod.setSelectedItem("lavaan");
	    	   } else {
	    		   String message = "Please select variables that are going to be used for CFA.";
	    		   new ErrorDialog(mainframe, message);
	    	   }
		} else {
	 		   String message = "There are no variables available. Please import a correct dataset.";
	 		  new ErrorDialog(mainframe, message);
			}
	}
	
	private void setToolTips(){
		ListModel<String> model = numericList.getModel();
		if(model.getSize() >0){
			numericList.setToolTipText("Select multiple Columns");
		}else{
			numericList.setToolTipText("No Columns visible, use Edit Data Menu to convert Columns");
		}
	}
	
	private void checkbutton(){
		List<String> tlist = numericList.getSelectedValuesList();
		
		if(tlist != null && tlist.size() > 1){		
			determineButton.setEnabled(true);
			determineButton.setText("Determine number of Factors");
			
			btnStartCfa.setEnabled(true);
			btnStartCfa.setText("Start CFA");
			}
		else if(tlist != null && tlist.size() == 1){
			determineButton.setEnabled(false);
			determineButton.setText("Not Enough Selected");
			
			btnStartCfa.setEnabled(false);
			btnStartCfa.setText("Not Enough Selected");
			}
		else{
			determineButton.setEnabled(false);
			determineButton.setText("Nothing Selected");
			
			btnStartCfa.setEnabled(false);
			btnStartCfa.setText("Nothing Selected");
			}
	}
	
	private void checkFactors(){
		String conf = factorstextField.getText();
		List<String> tlist = numericList.getSelectedValuesList();
		
		if(tlist != null && tlist.size() > 1 && conf != null && isNumber(conf)){		
			efaButton.setEnabled(true);
			efaButton.setText("Start EFA");
			
			scoreButton.setEnabled(true);
			scoreButton.setText("Score");
		} 
		else{
			efaButton.setEnabled(false);
			efaButton.setText("Start EFA Disabled");
			
			scoreButton.setEnabled(false);
			scoreButton.setText("Score Disabled");
		}
	}
	
	private Boolean isNumber(String str){  
		try {  
			Double.parseDouble(str);  
		}catch(NumberFormatException nfe){  
			return false;  
		}  
		return true; 
	}
	
	@Override
	public void update(Observable o, Object arg) {
		initList();
		setToolTips();
		checkbutton();
		checkFactors();
	}
}
