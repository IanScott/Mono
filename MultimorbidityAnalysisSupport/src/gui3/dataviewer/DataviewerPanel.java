package gui3.dataviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import gui3.MainFrame;
import gui3.TableRowUtilities;
import gui3.dataviewer.edit.EditPanel;
import gui3.start.DataPreviewerDialog;
import java.io.File;
import java.util.Observable;
import java.util.Observer;


public class DataviewerPanel extends JPanel implements Observer{
	private static final long serialVersionUID = 3561758092955571027L;
	private JTable table;
	private String[] columnnames;
	private String[][] values;
	private JScrollPane scrollPane;
	private JPanel tablepanel;
	private JButton undoButton;
	private JButton redoButton;
	
	
	private JPanel controlpanel;
	private JPanel editPane;
	private JPanel rightpanel;
	private MainFrame mainframe;
	
	
	public DataviewerPanel(MainFrame mainframe) {		
		this.mainframe = mainframe;
		this.setLayout(new BorderLayout(0, 0));
		this.setupPanels();
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setBackground(Color.WHITE);
		mainframe.addObserver(this);
	}
	
	private void setupPanels(){
		initTable();
		
		tablepanel = new JPanel();
		tablepanel.setLayout(new BorderLayout(0, 0));
		this.add(tablepanel, BorderLayout.CENTER);
		tablepanel.add(scrollPane,BorderLayout.CENTER);
		setupControlPanel();
		tablepanel.add(controlpanel, BorderLayout.NORTH);
		setupEditPane();
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
	
	
	
	private void initTable(){
		this.columnnames = mainframe.getDataSetColumnnames();
		this.values = mainframe.getDataSetValues();
		
		table = new JTable() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = -5751854321180727316L;

			@Override
		    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		        Component comp = super.prepareRenderer(renderer, row, col);
		        Object value = getModel().getValueAt(row, col);
		        if (value.equals("<<UNKNOWN>>")||value.equals("<<NOT A BOOLEAN>>")||value.equals("NaN")) {
		                comp.setBackground(Color.YELLOW);
		             
		        } else {
		            comp.setBackground(Color.white);
		        	}
		        return comp;
		        }
		   };
		
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(values, columnnames));
		scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		TableRowUtilities.addNumberColumn(table, 1, true);
		
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);	
	}
	
	
	private void setupControlPanel(){
		controlpanel = new JPanel();
		controlpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		undoButton = new JButton("undo");
		if(!mainframe.canUndo()){
			undoButton.setEnabled(false);
		}
		undoButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainframe.undo();
				
			}});
		controlpanel.add(undoButton);
		
		redoButton = new JButton("redo");
		if(!mainframe.canRedo()){
			redoButton.setEnabled(false);
		}
		redoButton.addActionListener(new ActionListener(){
					
			@Override
			public void actionPerformed(ActionEvent e) {
				mainframe.redo();
				
			}});
		controlpanel.add(redoButton);
		

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				savedata();
				
			}});
		controlpanel.add(saveButton);
		
		JButton openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				actionLoad();
			}});
		controlpanel.add(openButton);
		
		JButton exportButton = new JButton("Export");
		exportButton.addActionListener(new ActionListener(){

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
		controlpanel.add(exportButton);
		
		JButton btnNewButton_6 = new JButton("Export2");
		btnNewButton_6.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				exportdata();
				
			}});
		controlpanel.add(btnNewButton_6);
		
		JButton importButton = new JButton("Import");
		importButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				actionImport();
			}});
		controlpanel.add(importButton);
		
	}
	
	@Override
	public void update(Observable o, Object arg) {

		this.columnnames = mainframe.getDataSetColumnnames();
		this.values = mainframe.getDataSetValues();
		
		DefaultTableModel model = new DefaultTableModel(values,columnnames) ;
		table.setModel(model);
		table.setEnabled(false);
		//TableRowUtilities.addNumberColumn(table,1,true);
		setupEditPane();
		resetUndoButtons();
		this.validate();
		this.repaint();
	}
	
	private void resetUndoButtons(){
		if(mainframe.canUndo()){
			undoButton.setEnabled(true);
		}else{
			undoButton.setEnabled(false);
		}
		if(mainframe.canRedo()){
			redoButton.setEnabled(true);
		}else{
			redoButton.setEnabled(false);
		}
		
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
	
	private void actionLoad(){
		JFileChooser fc = new JFileChooser("TEMP");
		File file = null;
		fc.setDialogTitle("Choose File");
		//fc.setCurrentDirectory(new File("TEMP"));
		int returnVal = fc.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			
			loadingscreen(file);
			
		}
		
	}
	
	private void loadingscreen(final File file){
		final JDialog frame = new JDialog(mainframe);
	    final JProgressBar progressBar = new JProgressBar();
	    progressBar.setIndeterminate(true);
	    
	    
	    frame.getContentPane().setLayout(new BorderLayout());
	    frame.getContentPane().add(new JLabel("Loading..."), BorderLayout.NORTH);
	    frame.getContentPane().add(progressBar, BorderLayout.CENTER);
	    frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    frame.pack();
	    frame.setLocationRelativeTo(this);
	    frame.setVisible(true);
	    
	    Runnable runnable = new Runnable() {
	        public void run() {
	        	mainframe.loadDataSet(file);
	            frame.setVisible(false);
	            frame.dispose();
	        }
	    };
	    new Thread(runnable).start();
	}
}
