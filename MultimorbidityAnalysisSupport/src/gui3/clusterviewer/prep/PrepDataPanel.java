package gui3.clusterviewer.prep;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import domain.Column.Type;
import gui3.MainFrame;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class PrepDataPanel extends JPanel {
	
	private MainFrame mainframe;
	private int size;
	private String[] columnnames;
	private String[] factorColumns;
	private String[] numericColumns;
	private String plotType = "B";
	private String clusterColumn;
	private String clusterMethod;
	private String numbClusters;
	

	private String[] levels;
	private JComboBox<String> comboBox;
	private JComboBox<String> comboBox2;
	private JPanel panel;
	private JList left;
	private JList right;
	
	public PrepDataPanel(MainFrame mainframe) {
		this.mainframe = mainframe;
		this.columnnames = mainframe.getDataSetColumnnames();
		this.levels = mainframe.getDatasetFactors(columnnames[0]);
		this.factorColumns = mainframe.getDataSetColumnnames(Type.FACTOR);
		this.numericColumns = mainframe.getDataSetNumColumnnames(Type.NUMERIC);
		this.clusterColumn = mainframe.getClusterColumn();
		this.clusterMethod = mainframe.getClusterMethod();
		this.numbClusters = mainframe.getNumbClusters();
		
		init();
		setup(clusterColumn,levels);
		

	}

	private void init(){
		this.setLayout(null);
		size = 50 +(maxLevel()*28);
		//PAS AAN!!!
		this.setSize(600, 300);
		this.setPreferredSize(new Dimension(600,size));
	}
	
	private int maxLevel() {
		// TODO Auto-generated method stub
		return 5;
	}

	private void setup(final String columnname, String[] levels){
		System.out.println("prep SETUP: " + this.getClusterColumn() + " | " + this.getPlotType() + " | " + this.getNumbClusters() + " | " + this.getClusterMethod());
		this.removeAll();
		this.factorColumns = mainframe.getDataSetColumnnames(Type.FACTOR);
		
		JLabel lblNewLabel = new JLabel("Cluster analysis");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 5, 141, 26);
		add(lblNewLabel);
		JLabel lblNote = new JLabel("Please use the Edit Panel to make modifications as stated in the following steps:");
		lblNote.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNote.setForeground(Color.red);
		lblNote.setBounds(10, 24, 500, 26);
		add(lblNote);
		
		
		String step1 = "Step 1: Choose the Clustering variable name: ";
		JLabel lblNewLabel1 = new JLabel(step1);
		lblNewLabel1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel1.setBounds(10,18,500,100); 
		add(lblNewLabel1);
		
		comboBox = new JComboBox<String>(factorColumns);
		comboBox.setSelectedItem(columnname);
		comboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				comboBoxAction(e);
				
			}});
		comboBox.setBounds(60, 80, 170, 24);
		add(comboBox);

		String step2  = "Step 2: Delete if needed, all numeric variables that will not be used";
		JLabel lblNewLabel2 = new JLabel(step2);
		lblNewLabel2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel2.setBounds(10,75,500,100); 
		add(lblNewLabel2);
		String step2a = "for clustering Purpose. Use the Edit panel";
		JLabel lblNewLabel2a = new JLabel(step2a);
		lblNewLabel2a.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel2a.setBounds(60,95,500,100); 
		add(lblNewLabel2a);

		this.numericColumns = mainframe.getDataSetNumColumnnames(Type.NUMERIC);


		right = new JList();
		left  = new JList(numericColumns);
		left.setVisibleRowCount(3);
		left.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane scrollpane = new JScrollPane(left);
		scrollpane.setPreferredSize(new Dimension(300, 300));
		scrollpane.setBounds(60, 170, 300, 100);
		add(scrollpane);
		right = (JList) left.getSelectedValue();
		
		String step3 = "Step 3: Remove NA values:";
		JLabel lblNewLabel3 = new JLabel(step3);
		lblNewLabel3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel3.setBounds(10,250,500,100); 
		add(lblNewLabel3);
		JLabel lblNote1 = new JLabel("Note: for update please use Edit Panel");
		lblNote1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNote1.setBounds(60, 305, 500, 26);
		add(lblNote1);

		
		JButton btnNewButton_1 = new JButton("Remove NA");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainframe.setNumbClusters("");
				mainframe.omitNa();
			}
		});
		
		btnNewButton_1.setBounds(400, 290, 150, 30);
		add(btnNewButton_1);
		
		String step4 = "Step 4: Help me determine the number of clusters: ";
		JLabel lblNewLabel4 = new JLabel(step4);
		lblNewLabel4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel4.setBounds(10,305,500,100); 
		add(lblNewLabel4);
		
		JButton btnNewButton_2 = new JButton("Calculate");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalculateNumOfClusters(e);
			}
		});
		btnNewButton_2.setBounds(400, 345, 150, 30);
		add(btnNewButton_2);

		String step4a = "Enter the number of clusters: ";
		JLabel lblNewLabel4a = new JLabel(step4a);
		lblNewLabel4a.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel4a.setBounds(60,330,500,100);
		add(lblNewLabel4a);

		final JTextField textField_1 = new JTextField();
		
		textField_1.addFocusListener( new FocusListener() {
		    public void focusGained(FocusEvent e) {
				mainframe.setNumbClusters(textField_1.getText().toString());
			}

			public void focusLost(FocusEvent e) {
				mainframe.setNumbClusters(textField_1.getText().toString());
			}			
		});
		
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainframe.setNumbClusters(textField_1.getText().toString());
			}
		});
		textField_1.setText(mainframe.getNumbClusters());
		textField_1.setBounds(260, 370, 80, 25);
		add(textField_1);

		String step5 = "Step 5: View clustering summary: ";
		JLabel lblNewLabel5 = new JLabel(step5);
		lblNewLabel5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel5.setBounds(10,360,500,100); 
		add(lblNewLabel5);

		JButton btnNewButton_3 = new JButton("Summary");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// R-code determine nr of clusters
			}
		});
		btnNewButton_3.setBounds(400, 400, 150, 30);
		add(btnNewButton_3);

		
		String step6 = "Step 6: Choose clustering method: ";
		JLabel lblNewLabel6 = new JLabel(step6);
		lblNewLabel6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel6.setBounds(10,390,500,100); 
		add(lblNewLabel6);

		String[] comboBoxArray = {"Partitioning method kmeans","Model based Clustering","Hierarchical agglomeration"};
		comboBox2 = new JComboBox<String>(comboBoxArray);
		comboBox2.setSelectedItem(mainframe.getClusterMethod());
		comboBox2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				comboBoxAction2(e);
				
			}});
		comboBox2.setBounds(60, 460, 270, 24);
		add(comboBox2);

		JButton btnNewButton_4 = new JButton("Start Clustering");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartClustering(e);
			}
		});
		btnNewButton_4.setBounds(400, 455, 150, 30);
		add(btnNewButton_4);
		
	}	

	public int getPanelHeight(){
		return size;
	}

	public void setNumbClusters(String numbClusters) {
		
		// TODO Auto-generated method stub
		mainframe.setNumbClusters(numbClusters);
	}

	public String getNumbClusters() {
        return mainframe.getClusterColumn();
	}
	
	public void setClusterMethod(String clusterMethod) {
		mainframe.setClusterMethod(clusterMethod);
	}

	public String getClusterMethod() {
        return mainframe.getClusterMethod();
	}
	
	public void setClusterColumn(String select) {
		// TODO Auto-generated method stub
		mainframe.setClusterColumn(select);
	}

	public String getClusterColumn() {
		// TODO Auto-generated method stub
		return mainframe.getClusterColumn();
	}
	
	public String getPlotType() {
        return this.plotType;
	}

	public void setPlotType(String plotType) {
        this.plotType = plotType;
	}

	private void comboBoxAction(ActionEvent e){
		this.setPlotType("B");
		@SuppressWarnings("unchecked")
		String select =(String) ((JComboBox<String>)e.getSource()).getSelectedItem();
		this.setClusterColumn(select);

		setup(select,mainframe.getDatasetFactors(select));
		size = 50 +(levels.length*28);
		this.getParent().setSize(600, size);
		this.getParent().revalidate();
		mainframe.barChartVert(select);
	}

	private void comboBoxAction2(ActionEvent e){
		@SuppressWarnings("unchecked")
		String method = comboBox2.getSelectedItem().toString();
		mainframe.setClusterMethod(method);
		System.out.println("Prepdata method : " + method);
		
	}

	private void CalculateNumOfClusters(ActionEvent e){
		mainframe.setNumbClusters("");
		this.setPlotType("N");
		if (comboBox.getSelectedItem() != null && comboBox.getSelectedItem() != "") {
			String select = comboBox.getSelectedItem().toString();
			mainframe.setClusterColumn(select);
			setup(select,mainframe.getDatasetFactors(select));
			size = 50 +(levels.length*28);
			this.getParent().setSize(600, size);
			this.getParent().revalidate();
			mainframe.CalculateNumberOfClusters(select);
		}
	}
	
	private void StartClustering(ActionEvent e){
		this.setPlotType("S");
		mainframe.setNumbClusters("");
		System.out.println("Prepdata numb clusters : " + numbClusters);

		if (comboBox.getSelectedItem() != null && comboBox.getSelectedItem() != "") {
			String select =comboBox.getSelectedItem().toString();
			mainframe.setClusterColumn(select);
			setup(select,mainframe.getDatasetFactors(select));
			size = 50 +(levels.length*28);
			this.getParent().setSize(600, size);
			this.getParent().revalidate();
			System.out.println("prep panel: " + this.getClusterColumn() + " | " + this.getPlotType() + " | " + this.getNumbClusters() + " | " + this.getClusterMethod());

			mainframe.StartClusterProcess(this.getClusterColumn(), this.getNumbClusters(), this.getClusterMethod());
		}
	}

	private void StartClusteringSummary(ActionEvent e){
		this.setPlotType("S");
		if (this.getNumbClusters() != "") {
			mainframe.setNumbClusters("");
		}
	}

}

