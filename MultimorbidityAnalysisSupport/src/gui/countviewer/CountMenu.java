package gui.countviewer;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import domain.Column;
import domain.tools.counttool.CountTool;
import domain.tools.datatool.DataTool;
import gui.ErrorDialog;
import gui.InfoDialog;
import gui.MListSelectionModel;
import util.MultimorbidityException;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

/**
 * Class represents the Menu of the CountToolviewer.
 * @author ABI team 37
 * @version 1.0
 */
public class CountMenu extends JPanel implements Observer{
	private static final long serialVersionUID = -7465574892426449751L;
	private JFrame mainframe;
	private DataTool datatool;
	private CountTool counttool;
	private String[] columns;
	private String[] logicalColumns;
	
	private JButton calcButton;
	private JComboBox<String> countColumnsComboBox;
	private JList<String> logicalColumnList;
	
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private static final int LEFT = 10;
	
	/**
	 * Constructor for the class.
	 * @param mainframe the main JFrame of the application
	 * @param datatool the dataTool instance to use
	 * @param counttool the countTool instance to use
	 */
	public CountMenu(final JFrame mainframe, DataTool datatool, CountTool counttool) {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setSize(new Dimension(WIDTH,HEIGHT));
		
		this.mainframe = mainframe;
		this.datatool = datatool;
		this.counttool = counttool;
		
		datatool.addObserver(this);
		
		JLabel mainLabel = new JLabel("Count Analysis");
		mainLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		mainLabel.setBounds(LEFT, 11, 280, 24);
		add(mainLabel);
		
		JButton infoButton = new JButton("INFO");
		infoButton.setBounds(200, 2, 75, 20);
		add(infoButton);
		infoButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new InfoDialog(mainframe, new File("resources/count.txt"));	
			}});
		
		setupStep1();
		setupStep2();
		setupStep3();
		
		updateComboboxes();
		checkbutton();
		setToolTips();
	}
	


	private void setupStep1(){
		JPanel step1panel = new JPanel();
		step1panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step1panel.setBounds(LEFT, 46, 280, 210);
		add(step1panel);
		step1panel.setLayout(null);
		
		JLabel step1label = new JLabel("Step 1.");
		step1label.setFont(new Font("Tahoma", Font.BOLD, 11));
		step1label.setBounds(LEFT, 10, 260, 14);
		step1panel.add(step1label);
		
		JLabel step2label = new JLabel("Select the logical Columns to count.");
		step2label.setBounds(LEFT, 25, 260, 14);
		step1panel.add(step2label);
		
		JLabel lblIfNoColumns = new JLabel("If no columns are visible, convert columns");
		lblIfNoColumns.setForeground(Color.RED);
		lblIfNoColumns.setBounds(LEFT, 40, 260, 14);
		step1panel.add(lblIfNoColumns);
		
		JLabel lblToLogicalType = new JLabel("to logical type by using the Edit Data Menu.");
		lblToLogicalType.setForeground(Color.RED);
		lblToLogicalType.setBounds(LEFT, 55, 260, 14);
		step1panel.add(lblToLogicalType);
		
		JLabel selectlabel = new JLabel("Select Logical Columns");
		selectlabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		selectlabel.setBounds(LEFT, 88, 260, 14);
		step1panel.add(selectlabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(LEFT, 105, 260, 93);
		step1panel.add(scrollPane);
		
		logicalColumnList = new JList<String>();
		logicalColumnList.setSelectionModel(new MListSelectionModel());
		scrollPane.setViewportView(logicalColumnList);
		logicalColumnList.setToolTipText("No Columns visible, use Edit Data Menu to convert Columns");
		logicalColumnList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkbutton();	
			}});
	}
	
	private void setupStep2(){
		JPanel step2panel = new JPanel();
		step2panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step2panel.setBounds(LEFT, 267, 280, 165);
		add(step2panel);
		step2panel.setLayout(null);
		
		JLabel lblStep = new JLabel("Step 2. Optional");
		lblStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStep.setBounds(LEFT, 10, 260, 14);
		step2panel.add(lblStep);
		
		JLabel lblSelectTheFactor = new JLabel("Select the factor Column to group ");
		lblSelectTheFactor.setBounds(LEFT, 25, 260, 14);
		step2panel.add(lblSelectTheFactor);
		
		JLabel lblTheCountedValues = new JLabel("the counted values.");
		lblTheCountedValues.setBounds(LEFT, 40, 260, 14);
		step2panel.add(lblTheCountedValues);
		
		JLabel visiblelabel = new JLabel("If no columns are visible, convert columns");
		visiblelabel.setForeground(Color.RED);
		visiblelabel.setBounds(LEFT, 55, 260, 14);
		step2panel.add(visiblelabel);
		
		JLabel lblToFactorType = new JLabel("to factor type by using the Edit Data Menu");
		lblToFactorType.setForeground(Color.RED);
		lblToFactorType.setBounds(LEFT, 70, 260, 14);
		step2panel.add(lblToFactorType);
		
		countColumnsComboBox = new JComboBox<String>();
		countColumnsComboBox.setBounds(LEFT, 135, 260, 20);
		step2panel.add(countColumnsComboBox);
		
		JLabel grouplabel = new JLabel("Select Group by Column");
		grouplabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		grouplabel.setBounds(LEFT, 117, 260, 14);
		step2panel.add(grouplabel);
	}	
	
	private void setupStep3(){
		JPanel step2panel = new JPanel();
		step2panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step2panel.setBounds(LEFT, 443, 280, 79);
		add(step2panel);
		step2panel.setLayout(null);
		
		JLabel step3label = new JLabel("Step 3.");
		step3label.setFont(new Font("Tahoma", Font.BOLD, 11));
		step3label.setBounds(LEFT, 11, 260, 14);
		step2panel.add(step3label);
		
		calcButton = new JButton("Nothing Selected");
		calcButton.setEnabled(false);
		calcButton.setBounds(LEFT, 36, 260, 23);
		step2panel.add(calcButton);
		calcButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				calculateAction();
				
			}});
	}
	
	private void calculateAction(){
		String groupColumn = (String)countColumnsComboBox.getSelectedItem();
		List<String> tdiseases = logicalColumnList.getSelectedValuesList();
		String[] diseases = tdiseases.toArray(new String[tdiseases.size()]);
		
		if(diseases !=null && diseases.length >0){
			try {
				counttool.executeCount(groupColumn, diseases );
			} catch (MultimorbidityException e) {
				new ErrorDialog(mainframe, e.getMessage());
			}	
		}
	}
	
	private void updateComboboxes(){
		this.columns = datatool.getDataSetColumnnames(Column.Type.toType("factor"));
		this.logicalColumns = datatool.getDataSetColumnnames(Column.Type.toType("logical"));
		
		DefaultListModel<String> dlm = new DefaultListModel<>();
		for(String s: logicalColumns){
			dlm.addElement(s);
		}
		logicalColumnList.setModel(dlm);
		
		countColumnsComboBox.removeAllItems();
		for(String s: columns){
			countColumnsComboBox.addItem(s);
		}	
	}
	
	private void checkbutton(){
		List<String> list = logicalColumnList.getSelectedValuesList();
		
		if(list != null && list.size() > 0){		
			calcButton.setEnabled(true);
			calcButton.setText("Calculate");
			}else{
				calcButton.setEnabled(false);
				calcButton.setText("Nothing Selected");
			}
	}
	
	private void setToolTips(){
		ListModel<String> model = logicalColumnList.getModel();
		if(model.getSize() >0){
			logicalColumnList.setToolTipText("Select multiple Columns");
		}else{
			logicalColumnList.setToolTipText("No Columns visible, use Edit Data Menu to convert Columns");
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updateComboboxes();
		checkbutton();
		setToolTips();
	}
}
