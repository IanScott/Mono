package gui.associationviewer.association;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import domain.Column.Type;
import domain.tools.associationtool.AssociationTool;
import domain.tools.datatool.DataTool;
import gui.ErrorDialog;
import gui.InfoDialog;
import gui.ToolViewer;
import gui.associationviewer.AssociationViewer;
import util.MultimorbidityException;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**
 * This is the Control Menu to the association viewer.
 * @author ABI team 37 
 * @version 1.0
 */
public class AssociationMenu extends JPanel implements Observer{
	
	private static final long serialVersionUID = -1453937308240648365L;
	private DataTool datatool;
	private AssociationTool associationtool;
	private JFrame mainframe;
	private JPanel mainpanel;
	
	private JComboBox<String> association1;
	private JComboBox<String> association2;
	private JComboBox<String> compCombobox;
	private JComboBox<String> methodComboBox;
	private JComboBox<String> hypoComboBox;
	
	private JComboBox<String> association1a;
	private JComboBox<String> association2a;
	private JComboBox<String> methodComboBoxa;
	private JTextField conflevel;
	private JTextField conftextField;
	private JButton correlationButton;
	private JButton btnCorrelationTest;
	private JButton oddsButton;
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame to use
	 * @param mainpanel the main JPanel of the viewer
	 * @param datatool the datatool to use
	 * @param associationtool the associationtool to use
	 */
	public AssociationMenu(final JFrame mainframe, JPanel mainpanel, DataTool datatool, AssociationTool associationtool) {
		setLayout(null);
		
		this.mainframe = mainframe;
		this.mainpanel = mainpanel;
		this.associationtool    = associationtool;	
		this.datatool = datatool;
		datatool.addObserver(this);
		
		setup();
	}
	
	private void setup(){
		JLabel lblAssociationAnalysis = new JLabel("Correlation");
		lblAssociationAnalysis.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAssociationAnalysis.setBounds(10, 10, 280, 30);
		add(lblAssociationAnalysis);
		
		JButton infoButton = new JButton("INFO");
		infoButton.setBounds(200, 2, 75, 20);
		add(infoButton);
		infoButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new InfoDialog(mainframe,new File("resources/association.txt"));	
				
			}});
		
		setupStep1();
		setupStep2();
		setupStep3();
		
		JLabel oddslabel = new JLabel("Odds Ratio");
		oddslabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		oddslabel.setBounds(10, 625, 280, 30);
		add(oddslabel);
		
		setupStep1a();
		setupStep2a();
		
		initAssociationComboboxes();
		initAssociationComboboxesa();
	}
	
	private void setupStep1(){
		JPanel step1panel = new JPanel();
		step1panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step1panel.setBounds(10, 46, 280, 173);
		add(step1panel);
		step1panel.setLayout(null);
		
		JLabel lblStep = new JLabel("Step 1.");
		lblStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStep.setBounds(10, 10, 260, 14);
		step1panel.add(lblStep);
		
		JLabel lblNewLabel = new JLabel("Only Integer and Numeric Columns ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 25, 260, 14);
		step1panel.add(lblNewLabel);
		
		JLabel lblCanBeSelected = new JLabel("can be selected.");
		lblCanBeSelected.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCanBeSelected.setBounds(10, 40, 260, 14);
		step1panel.add(lblCanBeSelected);
		
		JLabel lblIfComboboxesAre = new JLabel("If comboboxes are empty use Edit Data Menu");
		lblIfComboboxesAre.setForeground(Color.RED);
		lblIfComboboxesAre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIfComboboxesAre.setBounds(10, 55, 260, 14);
		step1panel.add(lblIfComboboxesAre);
		
		association1 = new JComboBox<String>();
		association1.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
			       if (e.getStateChange() == ItemEvent.SELECTED) {
						association1OnChange();
				   }
			}
		});
		association1.setBounds(10, 95, 260, 20);
		step1panel.add(association1);
		
		association2 = new JComboBox<String>();
		association2.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
			       if (e.getStateChange() == ItemEvent.SELECTED) {
						association2OnChange();
				   }
			}
		});
		association2.setBounds(10, 139, 260, 20);
		step1panel.add(association2);
		
		JLabel lblRelation = new JLabel("Select the first variable:");
		lblRelation.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRelation.setBounds(10, 81, 260, 14);
		step1panel.add(lblRelation);
		
		JLabel relation2label = new JLabel("Select the second variable:");
		relation2label.setFont(new Font("Tahoma", Font.BOLD, 11));
		relation2label.setBounds(10, 125, 260, 14);
		step1panel.add(relation2label);
	}
	
	

	private void initAssociationComboboxes() {	
		String[] columnnames    = datatool.getDataSetNumColumnnames();
		
		association1.removeAllItems();
		for (String s: columnnames){
			association1.addItem(s);
		}
		
		association2.removeAllItems();
		for (String s: columnnames){
			association2.addItem(s);
		}
		
	}
	
	private void setupStep2(){
		JPanel step2panel = new JPanel();
		step2panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step2panel.setBounds(10, 230, 280, 181);
		add(step2panel);
		step2panel.setLayout(null);
		
		String[] useTypes = {"all.obs","complete.obs","pairwise.complete.obs"};	
		compCombobox = new JComboBox<String>(useTypes);
		compCombobox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {			
				comboBoxAction4();			
			}});
		compCombobox.setBounds(10, 60, 260, 20);
		step2panel.add(compCombobox);
		
		String[] methodTypes = {"pearson","kendall","spearman"};	
		methodComboBox = new JComboBox<String>(methodTypes);
		methodComboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {			
				comboBoxAction5();			
			}});
		methodComboBox.setBounds(10, 105, 260, 20);
		step2panel.add(methodComboBox);
		
		JLabel step2label = new JLabel("Step 2.");
		step2label.setFont(new Font("Tahoma", Font.BOLD, 11));
		step2label.setBounds(10, 10, 260, 14);
		step2panel.add(step2label);
		
		JLabel lblSelect = new JLabel("Select completeness");
		lblSelect.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelect.setBounds(10, 46, 260, 14);
		step2panel.add(lblSelect);
		
		JLabel lblSelectMethod = new JLabel("Select method");
		lblSelectMethod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectMethod.setBounds(10, 91, 260, 14);
		step2panel.add(lblSelectMethod);
		
		correlationButton = new JButton("Select 2 variables");
		correlationButton.setEnabled(false);
		correlationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				determineCorrelation();
			}

		});
		correlationButton.setBounds(10, 143, 260, 23);
		step2panel.add(correlationButton);
	}

	private void setupStep3(){
		JPanel step3panel = new JPanel();
		step3panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step3panel.setBounds(10, 422, 280, 181);
		add(step3panel);
		step3panel.setLayout(null);
		
		JLabel step3label = new JLabel("Step 3.");
		step3label.setFont(new Font("Tahoma", Font.BOLD, 11));
		step3label.setBounds(10, 11, 260, 14);
		step3panel.add(step3label);
		
		JLabel lblSelectAlternative = new JLabel("Select alternative");
		lblSelectAlternative.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectAlternative.setBounds(10, 46, 260, 14);
		step3panel.add(lblSelectAlternative);
		
		String[] hypotTypes = {"not equal", "greater","less","two.sided"};
		hypoComboBox = new JComboBox<String>(hypotTypes);
		hypoComboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {	
				comboBoxAction7();
			}});
		hypoComboBox.setBounds(10, 60, 260, 20);
		step3panel.add(hypoComboBox);
		
		JLabel lblInputConfidenceLevel = new JLabel("Input confidence level: (between 0 and 1)");
		lblInputConfidenceLevel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInputConfidenceLevel.setBounds(10, 91, 260, 14);
		step3panel.add(lblInputConfidenceLevel);
		
		conftextField = new JTextField();
		conftextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
		    	if(btnCorrelationTest != null){
		    		checkCorTestButton();
		    	}
				
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(btnCorrelationTest != null){
		    		checkCorTestButton();
		    	}			
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(btnCorrelationTest != null){
		    		checkCorTestButton();
		    	}			
			}
		});

		conftextField.setText("0.95");
		conftextField.setBounds(10, 105, 114, 20);
		step3panel.add(conftextField);
		
		btnCorrelationTest = new JButton("Correlation test");
		btnCorrelationTest.setEnabled(false);
		btnCorrelationTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				determineCorrelationTest(); 
			}
		});
		btnCorrelationTest.setBounds(10, 143, 260, 23);
		step3panel.add(btnCorrelationTest);
	}
	
	private void setupStep1a(){
		JPanel step1panel = new JPanel();
		step1panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step1panel.setBounds(10, 661, 280, 173);
		add(step1panel);
		step1panel.setLayout(null);
		
		JLabel lblStep = new JLabel("Step 1.");
		lblStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStep.setBounds(10, 10, 260, 14);
		step1panel.add(lblStep);
		
		JLabel lblNewLabel = new JLabel("Only Factor Columns ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 25, 260, 14);
		step1panel.add(lblNewLabel);
		
		JLabel lblCanBeSelected = new JLabel("can be selected.");
		lblCanBeSelected.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCanBeSelected.setBounds(10, 40, 260, 14);
		step1panel.add(lblCanBeSelected);
		
		JLabel lblIfComboboxesAre = new JLabel("If comboboxes are empty use Edit Data Menu");
		lblIfComboboxesAre.setForeground(Color.RED);
		lblIfComboboxesAre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIfComboboxesAre.setBounds(10, 55, 260, 14);
		step1panel.add(lblIfComboboxesAre);
		
		association1a = new JComboBox<String>();
		association1a.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
			       if (e.getStateChange() == ItemEvent.SELECTED) {
						association1aOnChange();
				   }
			}
		});
		association1a.setBounds(10, 95, 260, 20);
		step1panel.add(association1a);
		
		association2a = new JComboBox<String>();
		association2a.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
			       if (e.getStateChange() == ItemEvent.SELECTED) {
						association2aOnChange();
				   }
			}
		});
		association2a.setBounds(10, 139, 260, 20);
		step1panel.add(association2a);
		
		JLabel lblRelation = new JLabel("Select first variable:");
		lblRelation.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRelation.setBounds(10, 81, 260, 14);
		step1panel.add(lblRelation);
		
		JLabel relation2label = new JLabel("Select second variable:");
		relation2label.setFont(new Font("Tahoma", Font.BOLD, 11));
		relation2label.setBounds(10, 125, 260, 14);
		step1panel.add(relation2label);
	}
	
	

	private void initAssociationComboboxesa() {	
		String[] columnnames    = datatool.getDataSetColumnnames(Type.FACTOR);
		
		association1a.removeAllItems();
		for (String s: columnnames){
			association1a.addItem(s);
		}
		
		association2a.removeAllItems();
		for (String s: columnnames){
			association2a.addItem(s);
		}
	
	}
	
	private void setupStep2a(){
		JPanel step2panel = new JPanel();
		step2panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step2panel.setBounds(10, 845, 280, 181);
		add(step2panel);
		step2panel.setLayout(null);
		
		
		conflevel = new JTextField();
		conflevel.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
		    	checkOddButton();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
		    	checkOddButton();				
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
		    	checkOddButton();			
			}
		});
		
		conflevel.setBounds(10, 105, 130, 20);
		step2panel.add(conflevel);
		
		String[] methodTypes  = {"cohort.count", "cohort.time", "case.control","cross.sectional"};	
		methodComboBoxa = new JComboBox<String>(methodTypes);
		methodComboBoxa.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {			
				comboBoxAction6();			
			}});
		methodComboBoxa.setBounds(10, 60, 260, 20);
		step2panel.add(methodComboBoxa);
		
		JLabel step2label = new JLabel("Step 2.");
		step2label.setFont(new Font("Tahoma", Font.BOLD, 11));
		step2label.setBounds(10, 10, 260, 14);
		step2panel.add(step2label);
		
		JLabel lblSelect = new JLabel("Select method");
		lblSelect.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelect.setBounds(10, 46, 260, 14);
		step2panel.add(lblSelect);
		
		JLabel lblSelectMethod = new JLabel("Input confidence Level (between 0 and 1)");
		lblSelectMethod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectMethod.setBounds(10, 91, 260, 14);
		step2panel.add(lblSelectMethod);
		
		oddsButton = new JButton("RR / Odds Ratio");
		oddsButton.setEnabled(false);
		oddsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				determineRRandOdd();
 
			}

		});
		oddsButton.setBounds(10, 143, 260, 23);
		step2panel.add(oddsButton);
	}
	
	
	private void association1aOnChange(){
		if ( !((ToolViewer) mainpanel).isActive() ) {
			return;
		}

		associationtool.setPlotType("DS2");
		String column11 = (String) association1a.getSelectedItem();
		associationtool.barChartVert(column11);
		
		checkOddButton();
	}

	private void association2aOnChange(){
		if ( !((ToolViewer) mainpanel).isActive() ) {
			return;
		}

		associationtool.setPlotType("BG2");
		String column22 = (String) association2a.getSelectedItem();
		String column11 = (String) association1a.getSelectedItem();
		associationtool.chartScatter(column11, column22);
		
		checkOddButton();
	}
	
	private void comboBoxAction6(){
		associationtool.setPlotType("B");
	}
	
	private void determineRRandOdd(){
		if ( !((ToolViewer) mainpanel).isActive() ) {
			return;
		}

		associationtool.setPlotType("OD");
		String col11    = (String) association1a.getSelectedItem();
	    String col22    = (String) association2a.getSelectedItem();
		String oddMethodType = (String) methodComboBoxa.getSelectedItem();
		String oddConfLevel  = conflevel.getText();
		
		if (col11 != null  && col22 != null) {
			if (methodComboBoxa.getSelectedItem() != null && !methodComboBoxa.getSelectedItem().toString().isEmpty()) {
				try {
					associationtool.determineRRandOdd(col11, col22, oddConfLevel, oddMethodType );
				} catch (MultimorbidityException e) {
					new ErrorDialog(mainframe,e.getMessage());
				}
			} else {
				String message = "Please select an RR / OR method before proceeding";
			    new ErrorDialog(mainframe, message);		
			}
		} else {
			String message = "RR/ODD: no variables are selected. Please import a data set.";
			new ErrorDialog(mainframe, message);
		}
		
	}
	
	private void association1OnChange(){
		if ( !((AssociationViewer) mainpanel).isActive() ) {
			return;
		}
		
		associationtool.setPlotType("DS");
		String column1 = (String) association1.getSelectedItem();
		try {
			associationtool.startDataSummary(column1);
		} catch (MultimorbidityException e) {
			new ErrorDialog(mainframe, e.getMessage());
		}
		associationtool.barChartVert(column1);
		
		checkCorrelationButton();
		checkCorTestButton();
	}

	private void association2OnChange(){
		if ( !((AssociationViewer) mainpanel).isActive()  ) {
			return;
		}

		associationtool.setPlotType("BG");
		String column2 = (String) association2.getSelectedItem();
		String column1 = (String) association1.getSelectedItem();		
		associationtool.chartScatter(column1, column2);

		checkCorrelationButton();
		checkCorTestButton();
	}
	
	private void comboBoxAction4(){
		associationtool.setPlotType("B");
	}

	private void comboBoxAction5(){
		associationtool.setPlotType("B");
	}
	
	private void comboBoxAction7(){
		associationtool.setPlotType("B");
	}
	
	private void determineCorrelation(){
		if ( !((AssociationViewer) mainpanel).isActive() ) {
			return;
		}

		associationtool.setPlotType("CO");
		String col1    = (String) association1.getSelectedItem();
		String col2    = (String) association2.getSelectedItem();
		String useType = (String) compCombobox.getSelectedItem();
		String metType = (String) methodComboBox.getSelectedItem();
		if (col1 != null  && col2 != null) {
			if (useType != null) {
				if (metType != null) {
					try {
						associationtool.determineCorrelation(col1, col2,useType, metType );
					} catch (MultimorbidityException e) {
						new ErrorDialog(mainframe,e.getMessage());
					}
				} else {
					String message = "Please select a correlation method.";
					new ErrorDialog(mainframe, message);
				}
			} else {
				String message = "Please select the 'use' method for computing covariances in the presence of missing values.";
				new ErrorDialog(mainframe, message);
			}
		} else {
			String message = "CORR: no variables are selected. Please import a data set.";
			new ErrorDialog(mainframe, message);
		}	
	}

	private void determineCorrelationTest(){
		if ( !((AssociationViewer) mainpanel).isActive() ) {
			return;
		}

		associationtool.setPlotType("DT");
   		String col1    		= (String) association1.getSelectedItem();
		String col2    		= (String) association2.getSelectedItem();
		String metType      = (String) methodComboBox.getSelectedItem();
		String alternative  = (String) hypoComboBox.getSelectedItem();
		//String confLevel    = associationtool.getAssocConfLevel();
		String confLevel = conftextField.getText();
		
		if (col1 != null  && col2 != null) {
			if (metType != null) {
				if (alternative != null ) {
					try {
						associationtool.determineCorrelationTest(col1, col2,alternative, confLevel, metType );
					} catch (MultimorbidityException e) {
						new ErrorDialog(mainframe,e.getMessage());
					}
				} else {
					String message = "Please select a alternative Hypothesis.";
					new ErrorDialog(mainframe, message);
				}
			} else {
				String message = "Please select a correlation method to use.";
				new ErrorDialog(mainframe, message);
			}
		} else {
			String message = "CORR: no variables are selected. Please import a data set.";
			new ErrorDialog(mainframe, message);
		}
	}
	
	private void checkCorrelationButton(){
		String a1 = (String) association1.getSelectedItem();
		String a2 = (String) association2.getSelectedItem();
		
		if(a1 == null || a2 == null || a1.isEmpty()||a2.isEmpty()){
			correlationButton.setEnabled(false);
			correlationButton.setText("Select 2 variables");
		}else{
			correlationButton.setEnabled(true);
			correlationButton.setText("Correlation");
		}
	}
	
	private void checkCorTestButton(){
		String conf = conftextField.getText();
		String a1 = (String) association1.getSelectedItem();
		String a2 = (String) association2.getSelectedItem();
		
		if(a1 == null || a2 == null || a1.isEmpty()||a2.isEmpty()){
			btnCorrelationTest.setEnabled(false);
			btnCorrelationTest.setText("Select 2 variables");
		}else if(conf.isEmpty() || conf == null){
			btnCorrelationTest.setEnabled(false);
			btnCorrelationTest.setText("confidence level required");
		}else if(!isNumber(conf)){
			btnCorrelationTest.setEnabled(false);
			btnCorrelationTest.setText("Invalide Input");
		}else{
			btnCorrelationTest.setEnabled(true);
			btnCorrelationTest.setText("Correlation test");
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
	
	private void checkOddButton(){
		String conf = conflevel.getText();
		String a1 = (String) association1a.getSelectedItem();
		String a2 = (String) association2a.getSelectedItem();
		
		if(a1 == null || a2 == null || a1.isEmpty()||a2.isEmpty()){
			oddsButton.setEnabled(false);
			oddsButton.setText("Select 2 variables");
		}else if(conf.isEmpty() || conf == null){
			oddsButton.setEnabled(false);
			oddsButton.setText("confidence level required");
		}else if(!isNumber(conf)){
			oddsButton.setEnabled(false);
			oddsButton.setText("Invalide Input");
		}else{
			oddsButton.setEnabled(true);
			oddsButton.setText("RR/ Odds Ratio");
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		initAssociationComboboxes();
		initAssociationComboboxesa();
		conflevel.setText(conflevel.getText());
	}
}
