package gui2.dataviewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import gui2.MainFrame;
import gui2.TableRowUtilities;
import gui2.dataviewer.analyse.AnalysePanel;
import gui2.dataviewer.edit.EditPanel;
import gui2.dataviewer.info.SummaryPanel;
import gui2.dataviewer.table.EditDataDialog;
import gui2.dataviewer.table.NASPanel;
import gui2.toolviewer.counttoolviewer.CountToolPanel;
import java.io.File;
import java.util.Observable;
import java.util.Observer;


public class DataviewerPanel extends JPanel implements Observer{
	private static final long serialVersionUID = 3561758092955571027L;
	private JTable table;
	private String[] columnnames;
	private String[][] values;
	private JScrollPane scrollPane;
	private JPanel generalPanel;
	private JPanel editpanel;
	private JPanel analysepanel;
	private JPanel auditpanel;
	private JPanel countpanel;
	private JPanel controlpanel;
	private JTabbedPane tabbedPane;
	private JPanel rightpanel;
	private MainFrame mainframe;
	
	
	public DataviewerPanel(MainFrame mainframe) {		
		this.mainframe = mainframe;
		this.setLayout(new BorderLayout(0, 0));
		this.setupPanels();
		mainframe.addObserver(this);
	}
	
	public void setupPanels(){
		initTable();
		
		JPanel leftpanel = new JPanel();
		leftpanel.setLayout(new BorderLayout(0, 0));
		this.add(leftpanel, BorderLayout.CENTER);
		leftpanel.add(scrollPane,BorderLayout.CENTER);
		JPanel edit = new JPanel();
		edit.setLayout(new BorderLayout(0, 0));
		JButton editbutton = new JButton("Edit Data");
		editbutton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//new EditDataDialog(mainframe);
				new NASPanel(mainframe);
				
			} });
		edit.add(editbutton, BorderLayout.CENTER);
		leftpanel.add(edit, BorderLayout.NORTH);
		//this.add(scrollPane,BorderLayout.CENTER);
		
		setupTabbedPane();
			
		setupControlPanel();
	}
	
	private void setupTabbedPane(){
		rightpanel = new JPanel();
		rightpanel.setPreferredSize(new Dimension(610, 1000));
		rightpanel.setLayout(new BorderLayout(0, 0));
		this.add(rightpanel, BorderLayout.EAST);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setSize(700, 1000);
		rightpanel.add(tabbedPane, BorderLayout.CENTER);
		
		fillTabbedPane();
	}
	
	private void fillTabbedPane(){
		tabbedPane.removeAll();
		if(mainframe.getDataSet() != null){
		generalPanel = new SummaryPanel(mainframe);
		tabbedPane.addTab("General Info", null, generalPanel, null);
		generalPanel.setPreferredSize(new Dimension(500, 500));
		generalPanel.setLayout(new BorderLayout(15, 0));
		tabbedPane.addTab("General Info", null, generalPanel, null);
		}
		editpanel = new EditPanel(mainframe);
		tabbedPane.addTab("Edit Data", null, editpanel, null);
		
		countpanel = new CountToolPanel(mainframe, null);
		tabbedPane.addTab("Count Analyse", null, countpanel, null);
		
		analysepanel = new AnalysePanel(mainframe);
		tabbedPane.addTab("Analyse Data", null, analysepanel, null);
		
		auditpanel = new JPanel();
		tabbedPane.addTab("Audit Data", null, auditpanel, null);
	}
	
	private void initTable(){
		this.columnnames = mainframe.getDataSetColumnnames();
		this.values = mainframe.getDataSetValues();
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(values, columnnames));
		scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		TableRowUtilities.addNumberColumn(table, 1, true);
		
		
		setupTable();
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		
		
		
	}
	
	private void setupTable(){
		JTableHeader header = table.getTableHeader();
		int length;
		if(columnnames != null){
			length = columnnames.length;
		}else{
			length = 0;
		}
		header.setLayout(new GridLayout(1,length));
		header.removeAll();
		for(int i =0; i<length; i++){
			final String colname = columnnames[i];
			JButton button = new JButton(colname);
			header.add(button);
			button.setMinimumSize(new Dimension(0,0));
			button.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					new EditDialogPanel(mainframe, colname);
		
				}});
			
		}
		
		
		
		
		table.repaint();
		
	}
	
	private void setupControlPanel(){
		controlpanel = new JPanel();
		
		JButton undoButton = new JButton("undo");
		undoButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainframe.undo();
				
			}});
		controlpanel.add(undoButton);
		
		JButton btnNewButton_1 = new JButton("redo");
		btnNewButton_1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainframe.redo();
				
			}});
		controlpanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Export");
		btnNewButton_2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(DataviewerPanel.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
				
				String location= file.getParent();
				String filename= file.getName();		
				mainframe.exportDataSet(location, filename);
				}
			}});
		controlpanel.add(btnNewButton_2);
		

		JButton btnNewButton_3 = new JButton("Save");
		btnNewButton_3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				savedata();
				
			}});
		controlpanel.add(btnNewButton_3);
		
		
		JButton btnNewButton_6 = new JButton("Export");
		btnNewButton_6.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				exportdata();
				
			}});
		controlpanel.add(btnNewButton_2);
		
		
		JButton btnNewButton_4 = new JButton("Back");
	
		btnNewButton_4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainframe.startFrame();
				
			}});
		controlpanel.add(btnNewButton_4);
		rightpanel.add(controlpanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void update(Observable o, Object arg) {

		this.columnnames = mainframe.getDataSetColumnnames();
		this.values = mainframe.getDataSetValues();
		
		DefaultTableModel model = new DefaultTableModel(values,columnnames) ;
		table.setModel(model);
		table.setEnabled(false);
		
		setupTable();
		
		int index = tabbedPane.getSelectedIndex();
		fillTabbedPane();
		tabbedPane.setSelectedIndex(index);
	}
	

	
	private void savedata(){
		String name = JOptionPane.showInputDialog(this  ,"Enter file name (no extension):"); 
		mainframe.saveDataSet(name);
		JOptionPane.showMessageDialog(mainframe, "Saving Complete");
	}
	
	private void exportdata(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		 FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("csv files (*.csv)", "csv");
	        // add filters
	        fileChooser.addChoosableFileFilter(csvFilter);
	        fileChooser.setFileFilter(csvFilter);
		int userSelection = fileChooser.showSaveDialog(mainframe);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File file = fileChooser.getSelectedFile();
		    String location = file.getAbsolutePath();
		    String filename = file.getName();
		}
	}
}
