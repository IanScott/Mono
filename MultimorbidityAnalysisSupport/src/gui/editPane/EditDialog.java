package gui.editPane;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import domain.tools.datatool.DataTool;
import gui.ErrorDialog;
import util.MultimorbidityException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;

/**
 * This is the dialog panel which is used to edit Column data.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class EditDialog extends JPanel{
	private static final long serialVersionUID = -1712134504052523383L;
	private JDialog dialog;
	private JPanel content;
	private JPanel controlls;
	private JScrollPane scroll;
	private JButton cancelButton;
	private JButton saveButton;
	private JButton convertButton;
	private JButton groupButton;
	private JComboBox<String> comboBox;
	private JComboBox<String> classComboBox;
	private HashMap<String,String> columnsToDelete;
	private JTextField columnNameTextField;
	private JTextField medianTextField;
	private JTextField averageTextField;
	private JTextField replaceField;
	private HashMap<Integer,JTextField> textFields;
	private JFrame mainframe;
	private DataTool datatool;
	private String columnname;
	private String[] factors;
	private String type;
	private String classification;
	private String median;
	private double average;
	private JRadioButton option;
	private JRadioButton option1;
	private JRadioButton option2;
	private JRadioButton option3; 
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame
	 * @param datatool the datatool to use
	 * @param columnname the name of the column who's data is to be shown and edited.
	 */
	public EditDialog(JFrame mainframe, DataTool datatool, String columnname) {
		this.mainframe = mainframe;
		this.datatool = datatool;
		this.columnname = columnname;
		this.type = datatool.getColumnType(columnname);
		if(type == null){
			type = "unknown";
		}
		this.classification = (datatool.getClassification(columnname)).toString();
		this.median = datatool.getColumnMedian(columnname);
		this.average = datatool.getColumnAverage(columnname);
		this.textFields = new HashMap<Integer,JTextField>();
		this.columnsToDelete = new HashMap<String,String>();
		this.factors = datatool.getDatasetFactors(columnname);
		
		this.setupDialog();
		this.setup();
		this.dialog.setVisible(true);
	}
	
	private void setupDialog(){
		dialog = new JDialog (mainframe, "Edit Column Values", true);
        dialog.setSize (500, 500);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (mainframe);
        JPanel layout = new JPanel();
        content = new JPanel();
        controlls = new JPanel();
        controlls.setLayout(null);
        controlls.setPreferredSize(new Dimension(500,30));
        layout.setLayout(new BorderLayout());
        
        content.setLayout(null);
        scroll = new JScrollPane(content);
        layout.add(scroll, BorderLayout.CENTER);
        
        layout.add(controlls, BorderLayout.SOUTH);
		dialog.setContentPane(layout);
	}
	
	private void setup(){
		int y = 10;
		JLabel columnLabel = new JLabel("Column");
		columnLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		columnLabel.setBounds(10, y, 146, 23);
		content.add(columnLabel);
		
		y=y+35;
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		nameLabel.setBounds(10, y, 120, 23);
		content.add(nameLabel);
		
		columnNameTextField = new JTextField();
		columnNameTextField.setBounds(151, y, 202, 23);
		columnNameTextField.setText(columnname);
		content.add(columnNameTextField);
		
		y=y+28;
		JLabel typeLabel = new JLabel("Type");
		typeLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		typeLabel.setBounds(10, y, 120, 23);
		content.add(typeLabel);
		
		String[] datatypes = datatool.getTypes();
		comboBox= new JComboBox<String>(datatypes);
        comboBox.setBounds(151, y, 202, 23);
        if(type != null){
        	comboBox.setSelectedItem(type);
        }
        content.add(comboBox);
		
        y=y+28;
		JLabel classLabel = new JLabel("Classification");
		classLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		classLabel.setBounds(10, y, 120, 23);
		content.add(classLabel);
		
		String[] classifications = datatool.getClasses();
		classComboBox= new JComboBox<String>(classifications);
        classComboBox.setBounds(151, y, 202, 23);
        if(classification != null){
        	classComboBox.setSelectedItem(classification);
        }
        content.add(classComboBox);
        
		y=y+28;
		JLabel medianLabel = new JLabel("Median");
		medianLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		medianLabel.setBounds(10, y, 120, 23);
		content.add(medianLabel);
		
		medianTextField = new JTextField();
		medianTextField.setBounds(151, y, 202, 23);
		medianTextField.setText(median);
		medianTextField.setEditable(false);
		content.add(medianTextField);
		
		if(type == "numeric"||type =="integer"){
			y=y+28;
			JLabel averageLabel = new JLabel("Average");
			averageLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			averageLabel.setBounds(10, y, 120, 23);
			content.add(averageLabel);
			
			averageTextField = new JTextField();
			averageTextField.setBounds(151, y, 202, 23);
			averageTextField.setText(average+"");
			averageTextField.setEditable(false);
			content.add(averageTextField);
		}
		
		y=y+28;
		JLabel replaceLabel = new JLabel("Replace");
		replaceLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		replaceLabel.setBounds(10, y, 120, 23);
		content.add(replaceLabel);
		
		JLabel unknownLabel = new JLabel("Unknown values");
		unknownLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		unknownLabel.setBounds(10, y+ 23, 120, 23);
		content.add(unknownLabel);
		
		option = new JRadioButton("No Action");
		option1 = new JRadioButton("Median");
        option2 = new JRadioButton("Average");
        option3 = new JRadioButton("Other");
		
        
        ButtonGroup group = new ButtonGroup();
        group.add(option);
        group.add(option1);
        group.add(option2);
        group.add(option3);
		
        content.add(option);
        content.add(option1);
        content.add(option3);
        option.setSelected(true);
        replaceField = new JTextField();
		replaceField.setEditable(true);
		content.add(replaceField);
		
		option.setBounds(151,  y    , 100, 23);
    	option1.setBounds(151, y +23    , 90, 23);
        if(type.equals("integer")||type.equals("numeric")){
        	content.add(option2);
        	option2.setBounds(151, y+ 46, 90, 23);
        	option3.setBounds(151, y+ 69, 70, 23);
        	replaceField.setBounds(225, y+69, 130, 23);
        	y= y+69;
        }else{
        	option3.setBounds(151, y+ 46, 70, 23);
        	replaceField.setBounds(225, y+46, 130, 23);
        	y= y+46;
        }
        
        
		if(type.equals("factor")){
			y=y+40;
			JLabel lblEditLevels = new JLabel("Levels");
			lblEditLevels.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblEditLevels.setBounds(10, y, 146, 23);
			content.add(lblEditLevels);
			y=y+28;
			
			for(int i =0; i<factors.length; i++){
				final String factor = factors[i];
				JLabel levelLabel = new JLabel("Level "+(i+1));
				levelLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
				levelLabel.setBounds(10, y, 120, 23);
				content.add(levelLabel);
				
				final JTextField factorTextField = new JTextField();
				factorTextField.setBounds(151, y, 202, 23);
				textFields.put(i,factorTextField);
				factorTextField.setText(factor);
				content.add(factorTextField);
				
				
				final JButton deleteButton = new JButton("Delete");
				deleteButton.addActionListener(new ActionListener(){
	
					@Override
					public void actionPerformed(ActionEvent e) {
						if(deleteButton.getText().equals("Delete")){
							columnsToDelete.put(factor, factor);
							deleteButton.setText("Deleted");
							factorTextField.setEnabled(false);
						}else{
							columnsToDelete.remove(factor);
							deleteButton.setText("Delete");
							factorTextField.setEnabled(true);
						}
						
						
					}
				});
				deleteButton.setBounds(382, y, 89, 23);
				content.add(deleteButton);
				y=y+26;
			}
			
		}
		
		
		//y=y+26;
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		cancelButton.setBounds(2, 2, 100, 26);
		controlls.add(cancelButton);
		
		//y=y+39;
		convertButton = new JButton("Convert to Columns");
		convertButton.setToolTipText("Convert Factors to Columns");
		convertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				convertAction();
			}
		});
		convertButton.setBounds(104, 2, 150, 26);
		controlls.add(convertButton);
		
		groupButton = new JButton("Group");
		groupButton.setToolTipText("Group distinct values into factors");
		if(!type.equals("integer")){
			groupButton.setEnabled(false);
		}
		groupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				groupby();
			}
		});
		
		groupButton.setBounds(256, 2, 100, 26);
		controlls.add(groupButton);
		
		
		saveButton = new JButton("Save & Continue");

		saveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAction();
				}
			});
		saveButton.setBounds(358, 2, 133, 26);
		controlls.add(saveButton);
			
		content.setPreferredSize(new Dimension(470, y));
	}
	
	private void saveAction(){
		//Put DataSet on Undo Stack
		datatool.snapshot();
		
		//First edit factors
		for(int i = 0; i<factors.length;i++){
			JTextField tempTF = textFields.get(i);
			String newname = tempTF.getText();
			String oldname = factors[i];
			if(!newname.equals(oldname)){
				try {
					datatool.renameFactor(columnname, oldname, newname);
				} catch (MultimorbidityException e) {
					new ErrorDialog(mainframe,e.getMessage());
				}
			}
		}
		
		//Second delete factors
		String [] delete = columnsToDelete.values().toArray(new String[columnsToDelete.values().size()]);
		try {
			datatool.deleteFactor(columnname, delete);
		} catch (MultimorbidityException e) {
			new ErrorDialog(mainframe,e.getMessage());
		}
		
		// Fourly Replace NA's
		replaceAction();
		
		//Thirdly Cast Column
		String select = (String)comboBox.getSelectedItem();
		if(!select.equals(type)){
			try {
				datatool.castColumn(columnname, select);
			} catch (MultimorbidityException e) {
				new ErrorDialog(mainframe,e.getMessage());
			}
		}
		//Change classification
		String clazz = (String)classComboBox.getSelectedItem();
		datatool.setClassification(columnname, clazz);
		
		//Lastly Change Column Name, Always Last
		String newname= columnNameTextField.getText();
	    if(!newname.equals(columnname)){
	    	try {
				datatool.editColumnname(columnname, newname);
			} catch (MultimorbidityException e) {
				new ErrorDialog(mainframe,e.getMessage());
			}
	    }
		//Exit dialog
		dialog.dispose();
	}
	
	private void replaceAction(){
		//Put DataSet on Undo Stack
		if(option.isSelected()){
			return;
		}else if(option1.isSelected()){
			try {
				datatool.setNa(this.columnname, this.median);
			} catch (MultimorbidityException e) {
				new ErrorDialog(mainframe,e.getMessage());
			}
		}else if(option2.isSelected()){
			try {
				datatool.setNa(this.columnname, this.average+"");
			} catch (MultimorbidityException e) {
				new ErrorDialog(mainframe,e.getMessage());
			}
		}else if(option3.isSelected()){
			try {
				datatool.setNa(this.columnname, this.replaceField.getText());
			} catch (MultimorbidityException e) {
				new ErrorDialog(mainframe,e.getMessage());
			}
		} 
	}
	
	private void groupby(){
		datatool.snapshot();
		dialog.dispose();
		final GroupPanel grouppanel = new GroupPanel();
		final JDialog groupdialog = new JDialog(mainframe);
		groupdialog.setContentPane(grouppanel);
		groupdialog.setSize (500, 320);
        groupdialog.setResizable (false);
        groupdialog.setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);
        groupdialog.setLocationRelativeTo (this);
		groupdialog.setVisible(true);
		grouppanel.getBackButton().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				groupdialog.dispose();
			} });
		grouppanel.getSaveButton().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] bounds = grouppanel.getBoundArray();
				String[] labels = grouppanel.getLabelArray();
				groupdialog.dispose();
				if(bounds.length != labels.length -1){	
					return;
				}
				try {
					datatool.cut(columnname,bounds, labels);
				} catch (MultimorbidityException e1) {
					new ErrorDialog(mainframe,e1.getMessage());
				}
			} });
	}
	
	
	private void convertAction(){
		String[] factors = datatool.getDatasetFactors(columnname);
		int count = factors.length;
		if(count >10){
			new ErrorDialog(mainframe,"Too many Factor. Max 9");
		}
		final JDialog frame = new JDialog(mainframe);
		
		Runnable thread = new Runnable(){

			@Override
			public void run() {
			    final JProgressBar progressBar = new JProgressBar();
			    progressBar.setIndeterminate(true);
			    frame.getContentPane().setLayout(new BorderLayout());
			    frame.getContentPane().add(new JLabel("Loading..."), BorderLayout.NORTH);
			    frame.getContentPane().add(progressBar, BorderLayout.CENTER);
			    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			    frame.pack();
			    frame.setLocationRelativeTo(content);
			    frame.setVisible(true);
			} };
			
	    thread.run();
		
		datatool.snapshot();
		try {
			datatool.convertFactorsToColumns(columnname);
		} catch (MultimorbidityException e1) {
			new ErrorDialog(mainframe, e1.getMessage());
		}
		frame.dispose();
		dialog.dispose();

	}
}
