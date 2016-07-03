package gui.clusterviewer.cluster;

import javax.swing.JPanel;

import domain.Column.Type;
import domain.tools.clustertool.ClusterTool;
import domain.tools.datatool.DataTool;
import gui.ErrorDialog;
import gui.InfoDialog;
import gui.MListSelectionModel;
import util.MultimorbidityException;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * This is the Control Menu to the cluster viewer.
 * @author ABI team 37 
 * @version 1.0
 */
public class ClusterMenu extends JPanel implements Observer{
	
	private static final long serialVersionUID = -3728448413924661916L;
	private DataTool datatool;
	private ClusterTool clustertool;
	private JFrame mainframe;
	private JPanel mainpanel;
	
	private String[] onlyNumericColumns;
	private JList<String> numericList;
	private String[] selectedNodes;

	private JComboBox<String> cb_clusterColumn;
	private JComboBox<String> cb_clusterMethods;
	private JComboBox<String> cb_clusterAttributes;
	
	private JButton determineButton;
	private JButton clusterSumButton;
	private JButton btnStartClustering;
	
	private JTextField clustertextField;
	
	public ClusterMenu(JFrame mainframe, JPanel mainpanel,DataTool datatool, ClusterTool clustertool) {
		this.mainframe = mainframe;
		this.mainpanel = mainpanel;
		this.clustertool = clustertool;	
		this.datatool = datatool;
		
		clustertool.addObserver(this);
		datatool.addObserver(this);
		
		setLayout(null);
		setup();
		setupstep1();
		setupstep2();
		setupstep3();
		setupstep4();
		setupstep5();
		
		initCombobox();
		initListbox();
		checkbutton();
	}
	
	private void setup(){
		JLabel mainLabel = new JLabel("Cluster Analysis");
		mainLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		mainLabel.setBounds(10, 10, 164, 30);
		add(mainLabel);
		
		JButton infoButton = new JButton("INFO");
		infoButton.setBounds(200, 2, 75, 20);
		add(infoButton);	
		infoButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new InfoDialog(mainframe, new File("resources/cluster.txt"));
			}});
	}
	
	private void setupstep1(){
		JPanel step1panel = new JPanel();
		step1panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step1panel.setBounds(10, 46, 280, 125);
		add(step1panel);
		step1panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Step 1.");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 10, 69, 14);
		step1panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Only Factor type columns selectable.");
		lblNewLabel_1.setBounds(10, 25, 260, 14);
		step1panel.add(lblNewLabel_1);
		
		JLabel lblIfNoColumns = new JLabel("If no columns are visible, convert columns");
		lblIfNoColumns.setForeground(Color.RED);
		lblIfNoColumns.setBounds(10, 40, 260, 14);
		step1panel.add(lblIfNoColumns);
		
		JLabel lblToFactorType = new JLabel("to factor type by using the Edit Data Menu.");
		lblToFactorType.setForeground(Color.RED);
		lblToFactorType.setBounds(10, 55, 260, 14);
		step1panel.add(lblToFactorType);
		
		JLabel lblNewLabel_2 = new JLabel("Select Clustering Column");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 80, 260, 14);
		step1panel.add(lblNewLabel_2);
		
		cb_clusterColumn = new JComboBox<String>();
		cb_clusterColumn.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
			       if (e.getStateChange() == ItemEvent.SELECTED) {
			    	   clusterColumnOnChange();
				   }
			}
		});
		cb_clusterColumn.setBounds(10, 94, 260, 20);
		step1panel.add(cb_clusterColumn);
	}
	
	private void setupstep2(){
		JPanel step2panel = new JPanel();
		step2panel.setLayout(null);
		step2panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step2panel.setBounds(10, 182, 280, 186);
		add(step2panel);
		
		JLabel lblStep = new JLabel("Step 2.");
		lblStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStep.setBounds(10, 10, 69, 14);
		step2panel.add(lblStep);
		
		JLabel lblOnlyNumericintegerType = new JLabel("Only Numeric/Integer type columns selectable.");
		lblOnlyNumericintegerType.setBounds(10, 25, 260, 14);
		step2panel.add(lblOnlyNumericintegerType);
		
		JLabel label_2 = new JLabel("If no columns are visible, convert columns");
		label_2.setForeground(Color.RED);
		label_2.setBounds(10, 40, 260, 14);
		step2panel.add(label_2);
		
		JLabel label_3 = new JLabel("to factor type by using the Edit Data Menu.");
		label_3.setForeground(Color.RED);
		label_3.setBounds(10, 55, 260, 14);
		step2panel.add(label_3);
		
		JLabel lblSelectColumns = new JLabel("Select Columns to Cluster");
		lblSelectColumns.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectColumns.setBounds(10, 80, 260, 14);
		step2panel.add(lblSelectColumns);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 94, 260, 80);
		step2panel.add(scrollPane);
		
		numericList = new JList<String>();
		numericList.setSelectionModel(new MListSelectionModel());
		numericList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkbutton();;
			}});
		
		scrollPane.setViewportView(numericList);
	}
	
	private void setupstep3(){
		JPanel step3panel = new JPanel();
		step3panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step3panel.setBounds(10, 379, 280, 80);
		add(step3panel);
		step3panel.setLayout(null);
		
		JLabel lblStep_1 = new JLabel("Step 3.");
		lblStep_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStep_1.setBounds(10, 11, 258, 16);
		step3panel.add(lblStep_1);
		
		determineButton = new JButton("Determine number of Clusters");
		determineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculateNumOfClusters();
			}
		});
		determineButton.setBounds(10, 37, 258, 23);
		step3panel.add(determineButton);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(10, 32, 46, 14);
		step3panel.add(lblNewLabel_3);
		
	}
	
	private void setupstep4(){
		
		JPanel step4panel = new JPanel();
		step4panel.setLayout(null);
		step4panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step4panel.setBounds(10, 470, 280, 158);
		add(step4panel);

		JLabel lblStep_3 = new JLabel("Step 4.");
		lblStep_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStep_3.setBounds(10, 11, 258, 16);
		step4panel.add(lblStep_3);

		JLabel lblNewLabel_4 = new JLabel("Enter number of clusters");
		lblNewLabel_4.setBounds(10, 30, 258, 14);
		step4panel.add(lblNewLabel_4);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
	
		clustertextField = new JTextField();
		clustertextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				checkbutton();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				checkbutton();
				
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				checkbutton();
				
			}
		});

		clustertextField.setBounds(10, 45, 114, 20);
		step4panel.add(clustertextField);

		
		JLabel lblSelectMethod = new JLabel("Select summary method");
		lblSelectMethod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectMethod.setBounds(10, 75, 258, 14);
		step4panel.add(lblSelectMethod);
			
		String[] clusterAttributes = {"cluster","size","withinss", "centers", "Cluster assignment"};
		cb_clusterAttributes = new JComboBox<String>(clusterAttributes);
		cb_clusterAttributes.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {				
				clusterAttributeOnChange();			
			}});
		cb_clusterAttributes.setBounds(10, 90, 258, 20);
		step4panel.add(cb_clusterAttributes);
		
		clusterSumButton = new JButton("View Clustering Summary");
		clusterSumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startClusteringSummary(); 
			}
		});
		clusterSumButton.setBounds(10, 120, 258, 23);
		step4panel.add(clusterSumButton);	
	}
	
	private void setupstep5(){
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 639, 280, 119);
		add(panel);
		
		JLabel lblStep_2 = new JLabel("Step 5.");
		lblStep_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStep_2.setBounds(10, 11, 258, 16);
		panel.add(lblStep_2);
		
		JLabel lblSelectClusteringMethod = new JLabel("Select clustering method");
		lblSelectClusteringMethod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectClusteringMethod.setBounds(10, 38, 258, 14);
		panel.add(lblSelectClusteringMethod);
		
		String[] ClusterMethods = {"Hierarchical clustering-kmeans","Hierarchical agglomeration"};
		cb_clusterMethods = new JComboBox<String>(ClusterMethods);
		cb_clusterMethods.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {				
				clusterMethodOnChange();			
			}});
		cb_clusterMethods.setBounds(10, 52, 258, 20);
		panel.add(cb_clusterMethods);
		
		btnStartClustering = new JButton("Start Clustering");
		btnStartClustering.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				startClustering();
			}});
		btnStartClustering.setBounds(10, 83, 258, 23);
		panel.add(btnStartClustering);
	}
	
	private void initListbox() {	
		DefaultListModel<String> dlm = new DefaultListModel<>();
		numericList.removeAll();
		this.onlyNumericColumns = datatool.getDataSetNumColumnnames();
		for(String s: onlyNumericColumns){
			dlm.addElement(s);
		}
		numericList.setModel(dlm);	
	}
	
	private void initCombobox() {
		cb_clusterColumn.removeAllItems();
		String[] onlyFactorColumns  = datatool.getDataSetColumnnames(Type.FACTOR);
		for (String s: onlyFactorColumns){
			cb_clusterColumn.addItem(s);
		}

	}
	
	private void clusterColumnOnChange(){		
        if ( !((gui.ToolViewer) mainpanel).isActive() ) {
        	return;
        }
		clustertool.setPlotType("DS");
		String clusterColumn = (String) cb_clusterColumn.getSelectedItem();
 		if (clusterColumn != null && clusterColumn != "") {
		   if (onlyNumericColumns !=null && onlyNumericColumns.length != 0) {
			   clustertool.startDataSummary(clusterColumn);
		   } else {
				String message = "Step2: no numeric variables for clustering. Please import a dataset";
				new ErrorDialog(mainframe, message);
		   }
			   
		} else {
				String message = "Step 1: please choose a cluster column or import a correct dataset";
				new ErrorDialog(mainframe, message);
		}
 		checkbutton();
	}
	
	private void clusterMethodOnChange(){
		checkbutton();
	}

	private void clusterAttributeOnChange(){
		clustertool.setPlotType("CS");
		checkbutton();
	}

	private void calculateNumOfClusters(){
		if ( !((gui.ToolViewer) mainpanel).isActive() ) {
        	return;
        }

		clustertool.setPlotType("N");
		if (cb_clusterColumn.getSelectedItem() != null) {
			if (onlyNumericColumns !=null && onlyNumericColumns.length != 0 ) {
      		   List<String> temp = numericList.getSelectedValuesList();
	    	   selectedNodes = temp.toArray(new String[temp.size()]);
 	    	   clustertool.setSelectedNodes(selectedNodes);
	    	   if (selectedNodes.length != 0) {
	    		   String select = (String) cb_clusterColumn.getSelectedItem();
    			   try {
    				   clustertool.calculateNumberOfClusters(select, selectedNodes);
    			   } catch (MultimorbidityException e) {
    				   new ErrorDialog(mainframe,e.getMessage());
    			   }
	    	   } else {
					String message = "Step2: no variables selected or you may have to import a dataset";	    		   
					new ErrorDialog(mainframe, message);
	    	   }
			} else {
				String message = "Step2: no variables available. Please import a dataset";
				new ErrorDialog(mainframe, message);
			}
		}else {
				String message = "Step 1: please choose a cluster column or you may have to import a dataset.";
				new ErrorDialog(mainframe, message);							
		}
	}
	   
	private void startClusteringSummary(){
		if ( !((gui.ToolViewer) mainpanel).isActive() ) {
        	return;
        }

		String clusterColumn     = (String)cb_clusterColumn.getSelectedItem();
		String numbOfClusters    = clustertextField.getText();
		String clusterAttributes = (String)cb_clusterAttributes.getSelectedItem();
		
		clustertool.setPlotType("CS");
		if (clusterColumn != null && !clusterColumn.isEmpty()) {
          if (onlyNumericColumns !=null && onlyNumericColumns.length != 0) {
       	    if (selectedNodes.length != 0) {   
       		   List<String> temp = numericList.getSelectedValuesList();
 	    	   selectedNodes = temp.toArray(new String[temp.size()]);
 	    	   clustertool.setSelectedNodes(selectedNodes);
        	  if (numbOfClusters != null && !numbOfClusters.isEmpty()) {
        		  if (clusterAttributes != null && !clusterColumn.isEmpty()) {
      	  			  try {
      	  				  clustertool.startClusterSummary(clusterColumn, numbOfClusters, clusterAttributes);
      	  			  } catch (MultimorbidityException e) {
      	  				  new ErrorDialog(mainframe,e.getMessage());
      	  			  }
        		  } else {
        			  String message = "Please choose a summary attribute from the list - step 5";
        			  new ErrorDialog(mainframe, message);
					
        		  }
        	  } else {
        		  String message = "Please enter a number of clusters - step 5";
        		  new ErrorDialog(mainframe, message);
        	  }
        	
       	    } else {
    			String message = "Step2: no variables selected or you may have to import a dataset";	    		   
    			new ErrorDialog(mainframe, message);
       	    }
        	  
          } else {
       		String message = "Step2: no numeric variables for clustering. Please import a dataset";
       		new ErrorDialog(mainframe, message);
          }
        	  
		} else {
			String message = "Step 1: please choose a cluster column or import a correct dataset";
			new ErrorDialog(mainframe, message);
		}
	}

	
	private void startClustering(){
		if ( !((gui.ToolViewer) mainpanel).isActive() ) {
        	return;
        }

		String clusterColumn     = (String)cb_clusterColumn.getSelectedItem();
		String numbOfClusters    = clustertextField.getText();
		String clusterMethod 	 = (String)cb_clusterMethods.getSelectedItem();

		clustertool.setPlotType("S");
		if (clusterColumn != null && !clusterColumn.isEmpty()) {
	      if (onlyNumericColumns !=null && onlyNumericColumns.length != 0) { 
 	   	    if (selectedNodes.length != 0) {
       		   List<String> temp = numericList.getSelectedValuesList();
 	    	   selectedNodes = temp.toArray(new String[temp.size()]);
 	    	   clustertool.setSelectedNodes(selectedNodes);
     		  if (clusterMethod != null && !clusterMethod.isEmpty()) {
				 if (clusterMethod == "Hierarchical agglomeration") {
	        	    if (numbOfClusters != null && !numbOfClusters.isEmpty()) {
			  			try {
							clustertool.startClusterProcess(clusterColumn, numbOfClusters, clusterMethod, selectedNodes);
						} catch (MultimorbidityException e) {
							new ErrorDialog(mainframe,e.getMessage());
						}
				    } else {
						String message = "Please enter the number of clusters to be used with Hierarchical agglomeration";
					    JOptionPane.showMessageDialog(mainframe, message, "MMT - Clustering",
					            JOptionPane.ERROR_MESSAGE);
   				    }
				 } else {
	  				 try {
	  					 clustertool.startClusterProcess(clusterColumn, numbOfClusters, clusterMethod, selectedNodes);
	  				 } catch (MultimorbidityException e) {
	  					 new ErrorDialog(mainframe,e.getMessage());
	  				 }
				 }	
			  } else {
				String message = "Please enter a clustering method from the list - step 6";
				new ErrorDialog(mainframe, message);
			  }
 	   	    } else {
    			String message = "Step2: no variables selected or you may have to import a dataset";	    		   
    			new ErrorDialog(mainframe, message);
 	   	    }
	      } else {
	       		String message = "Step2: no numeric variables for clustering. Please import a dataset";
	       		new ErrorDialog(mainframe, message);
	      }
		} else {
			String message = "Step 1: please choose a cluster column or import a correct dataset";
			new ErrorDialog(mainframe, message);
		}
	}
	
	private void checkbutton(){
		List<String> tlist = numericList.getSelectedValuesList();
		String cluster = (String)cb_clusterColumn.getSelectedItem();
		
		if(cluster ==null || cluster.isEmpty()){
			determineButton.setEnabled(false);
			clusterSumButton.setEnabled(false);
			btnStartClustering.setEnabled(false);
			
			determineButton.setText("Select Cluster Column");
			clusterSumButton.setText("Select Cluster Column");
			btnStartClustering.setText("Select Cluster Column");
		}else{
			if(tlist == null || tlist.size() < 1){	
				determineButton.setEnabled(false);
				clusterSumButton.setEnabled(false);
				btnStartClustering.setEnabled(false);
				
				determineButton.setText("Select Columns to Cluster");
				clusterSumButton.setText("Select Columns to Cluster");
				btnStartClustering.setText("Select Columns to Cluster");
			
			}else{
				determineButton.setEnabled(true);
				determineButton.setText("Determine number of Clusters");
				
				String numclus = clustertextField.getText();
				if(numclus == null || numclus.isEmpty()){
					clusterSumButton.setEnabled(false);
					btnStartClustering.setEnabled(false);
					
					clusterSumButton.setText("Set number of Clusters");
					btnStartClustering.setText("Set number of Clusters");	
				}else{
					String summary = (String)cb_clusterAttributes.getSelectedItem();
					String method = (String)cb_clusterMethods.getSelectedItem();
					if(method == null || method.isEmpty()){
						btnStartClustering.setEnabled(false);
						btnStartClustering.setText("Select clustering method");
					}else{
						btnStartClustering.setEnabled(true);
						btnStartClustering.setText("Start Clustering");
					}
					
					if(summary == null || summary.isEmpty()){
						clusterSumButton.setEnabled(false);
						clusterSumButton.setText("Select summary Method");
					}else{
						clusterSumButton.setEnabled(true);
						clusterSumButton.setText("View Clustering Summary");
					}
				}
			}
		}
	}	
		
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof DataTool){
			initCombobox();
			initListbox();
			checkbutton();
		}
	}		
}
