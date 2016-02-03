package poc2.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import poc2.util.DataSetMapper;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import java.util.Observable;
import java.util.Observer;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;


public class DataviewerFrame extends JFrame implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3561758092955571027L;
	private JTable table;
	private String[] columnnames;
	private String[][] values;
	private JScrollPane scrollPane;
	private JPanel panel_2;
	private EditPanel panel_3;
	private JPanel panel_4;
	private JTabbedPane tabbedPane;
	
	private GuiController guicontroller;
	
	
	public DataviewerFrame(final GuiController guicontroller) {
		
		this.guicontroller = guicontroller;
		
		
		setTitle("MMT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setSize(1050,750);
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//testdata
		table.setModel(new DefaultTableModel(guicontroller.getToolController().getDataSetValues(),guicontroller.getToolController().getDataSetColumnnames()));
		
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		
		
		scrollPane = new JScrollPane(table);
		//scrollPane.setSize(400, 1000);
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		
		TableRowUtilities.addNumberColumn(table, 1, true);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(610, 1000));
		getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.setSize(700, 1000);
		panel.add(tabbedPane, BorderLayout.CENTER);
		
		panel_2 = new JPanel();
		tabbedPane.addTab("General Info", null, panel_2, null);
		panel_2.setPreferredSize(new Dimension(500, 500));
		panel_2.setLayout(new BorderLayout(15, 0));
		
		panel_3 = new EditPanel(guicontroller);
		tabbedPane.addTab("Edit Data", null, panel_3, null);
		
		panel_4 = new JPanel();
		tabbedPane.addTab("Analyse Data", null, panel_4, null);
		
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab(" New tab ", null, panel_6, null);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("undo");
		btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				guicontroller.getToolController().undo();
				
			}});
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("redo");
		btnNewButton_1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				guicontroller.getToolController().redo();
				
			}});
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Export");
		panel_1.add(btnNewButton_2);
		

		JButton btnNewButton_3 = new JButton("Save");
		btnNewButton_3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				savedata();
				
			}});
		panel_1.add(btnNewButton_3);
		
		
		JButton btnNewButton_6 = new JButton("Export");
		btnNewButton_6.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				exportdata();
				
			}});
		panel_1.add(btnNewButton_2);
		
		
		JButton btnNewButton_4 = new JButton("Back");
	
		btnNewButton_4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				guicontroller.startFrame();
				
			}});
		panel_1.add(btnNewButton_4);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.columnnames = guicontroller.getToolController().getDataSetColumnnames();
		this.values = guicontroller.getToolController().getDataSetValues();
		
		DefaultTableModel model = new DefaultTableModel(values,columnnames) ;
		table.setModel(model);
		table.setEnabled(false);
		table.repaint();
		
	}
	
	private void savedata(){
		String name = JOptionPane.showInputDialog(this  ,"Enter file name (no extension):"); 
		guicontroller.getToolController().saveDataSet(name);
		JOptionPane.showMessageDialog(this, "Saving Complete");
	}
	
	private void exportdata(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		 FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("csv files (*.csv)", "csv");
	        // add filters
	        fileChooser.addChoosableFileFilter(csvFilter);
	        fileChooser.setFileFilter(csvFilter);
		int userSelection = fileChooser.showSaveDialog(this);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File file = fileChooser.getSelectedFile();
		    String location = file.getAbsolutePath();
		    String filename = file.getName();
		    System.out.println(location + "   "+filename);
		}
	}
}
