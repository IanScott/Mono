package gui3.dataviewer;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;

import gui3.MainFrame;
import util.MultimorbidityException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class EditDialogPanel extends JPanel{

	private JDialog dialog;
	private JPanel content;
	private JPanel controlls;
	private JScrollPane scroll;
	private JButton btnNewButton_1;
	private JButton btnCancel;
	private JButton btnDeleteColumn;
	private JComboBox<String> comboBox;
	private String datatype;
	private HashMap<String,String> columnsToDelete;
	
	private JTextField textField;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;
	private JTextField replaceField;
	private JTextField textField6;
	private HashMap<Integer,JTextField> textFields;
	
	private MainFrame mainframe;
	private String columnname;
	private String[] factors;
	private String type;
	private String median;
	private double average;
	
	JRadioButton option1;
    JRadioButton option2;
    JRadioButton option3; 
	
	public EditDialogPanel(MainFrame mainframe, String columnname) {
		this.mainframe = mainframe;
		this.columnname = columnname;
		this.type = mainframe.getColumnType(columnname);
		this.median = mainframe.getColumnMedian(columnname);
		this.average = mainframe.getColumnAverage(columnname);
		this.textFields = new HashMap<Integer,JTextField>();
		this.columnsToDelete = new HashMap<String,String>();
		this.factors = mainframe.getDatasetFactors(columnname);
		this.setupDialog();
		this.setup();
		this.dialog.setVisible(true);
	}
	
	private void setupDialog(){
		dialog = new JDialog (mainframe, "Edit Column Values", true);
        dialog.setSize (500, 500);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (mainframe);
        JPanel layout = new JPanel();
        content = new JPanel();
        controlls = new JPanel();
        layout.setLayout(new BorderLayout());
        
        content.setLayout(null);
        scroll = new JScrollPane(content);
        layout.add(scroll, BorderLayout.CENTER);
        
        layout.add(controlls, BorderLayout.SOUTH);
		dialog.setContentPane(layout);
	}
	
	private void setup(){
		int y = 10;
		JLabel lblNewLabel = new JLabel("Column");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(10, y, 146, 23);
		content.add(lblNewLabel);
		
		y=y+35;
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, y, 120, 23);
		content.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(151, y, 202, 23);
		textField.setText(columnname);
		content.add(textField);
		
		y=y+28;
		JLabel lblNewLabel_2 = new JLabel("Type");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(10, y, 120, 23);
		content.add(lblNewLabel_2);
		
		String[] tempdatatypes = mainframe.getTypes();
		String[] datatypes = new String [tempdatatypes.length];
		for(int i =0; i<tempdatatypes.length;i++){
			datatypes[i] = tempdatatypes[i].toUpperCase();
		}
		comboBox= new JComboBox<String>(datatypes);
        comboBox.setBounds(151, y, 202, 23);
        datatype = mainframe.getColumnType(columnname);
        comboBox.setSelectedItem(datatype.toUpperCase());
        content.add(comboBox);
		
        //if(datatype.equals("factor")){
        	final JButton btnNewButton3 = new JButton("Convert to Columns");
        	btnNewButton3.setBounds(382, y, 89, 23);
        	content.add(btnNewButton3);
        	btnNewButton3.addActionListener(new ActionListener(){

        		@Override
        		public void actionPerformed(ActionEvent e) {
        			mainframe.convertFactorsToColumns(columnname);
        			dialog.dispose();
        		} 
        	});
       // }
		y=y+28;
		JLabel lblNewLabel_3 = new JLabel("Median");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3.setBounds(10, y, 120, 23);
		content.add(lblNewLabel_3);
		
		textField3 = new JTextField();
		textField3.setBounds(151, y, 202, 23);
		textField3.setText(median);
		textField3.setEditable(false);
		content.add(textField3);
		
		if(type == "numeric"||type =="integer"){
			y=y+28;
			JLabel lblNewLabel_4 = new JLabel("Average");
			lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblNewLabel_4.setBounds(10, y, 120, 23);
			content.add(lblNewLabel_4);
			
			textField4 = new JTextField();
			textField4.setBounds(151, y, 202, 23);
			textField4.setText(average+"");
			textField4.setEditable(false);
			content.add(textField4);
		}
		
		y=y+28;
		JLabel lblNewLabel_5 = new JLabel("Observation");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_5.setBounds(10, y, 120, 23);
		content.add(lblNewLabel_5);
		
		y=y+28;
		JLabel lblNewLabel_6 = new JLabel("Disease");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_6.setBounds(10, y, 120, 23);
		content.add(lblNewLabel_6);
		
		y=y+28;
		JLabel lblNewLabel_7 = new JLabel("Replace");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_7.setBounds(10, y, 120, 23);
		content.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Unknown values");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_8.setBounds(10, y+ 23, 120, 23);
		content.add(lblNewLabel_8);
		
		option1 = new JRadioButton("Median");
        option2 = new JRadioButton("Average");
        option3 = new JRadioButton("Other");
		
        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
		
        content.add(option1);
        content.add(option3);
        option1.setSelected(true);
        replaceField = new JTextField();
		replaceField.setEditable(true);
		content.add(replaceField);
		
		final JButton btnNewButton4 = new JButton("Replace");
    	btnNewButton4.setBounds(382, y, 89, 23);
    	content.add(btnNewButton4);
    	btnNewButton4.addActionListener(new ActionListener(){

    		@Override
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("replace button pressed");
    			replaceAction();
    			dialog.dispose();
    		} 
    	});
		
        if(type.equals("integer")||type.equals("numeric")){
        	content.add(option2);
        	option1.setBounds(151, y    , 70, 23);
        	option2.setBounds(151, y+ 23, 70, 23);
        	option3.setBounds(151, y+ 46, 70, 23);
        	replaceField.setBounds(225, y+46, 130, 23);
        	y= y+46;
        }else{
        	option1.setBounds(151, y    , 70, 23);
        	option3.setBounds(151, y+ 23, 70, 23);
        	replaceField.setBounds(225, y+23, 130, 23);
        	y= y+23;
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
				JLabel lblNewLabel_9 = new JLabel("Level "+(i+1));
				lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 15));
				lblNewLabel_9.setBounds(10, y, 120, 23);
				content.add(lblNewLabel_9);
				
				final JTextField textField_1 = new JTextField();
				textField_1.setBounds(151, y, 202, 23);
				textFields.put(i,textField_1);
				textField_1.setText(factor);
				content.add(textField_1);
				
				
				final JButton btnNewButton = new JButton("Delete");
				btnNewButton.addActionListener(new ActionListener(){
	
					@Override
					public void actionPerformed(ActionEvent e) {
						if(btnNewButton.getText().equals("Delete")){
							//mainframe.deleteFactor(columnname, factor);
							columnsToDelete.put(factor, factor);
							btnNewButton.setText("Deleted");
							textField_1.setEnabled(false);
						}else{
							columnsToDelete.remove(factor);
							btnNewButton.setText("Delete");
							textField_1.setEnabled(true);
						}
						
						
					}
				});
				btnNewButton.setBounds(382, y, 89, 23);
				content.add(btnNewButton);
				y=y+26;
			}
			
		}
		
		
		//y=y+26;
		btnNewButton_1 = new JButton("         Cancel         ");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		btnNewButton_1.setBounds(10, y, 150, 37);
		controlls.add(btnNewButton_1);
		
		//y=y+39;
		btnDeleteColumn = new JButton("Delete Entire Column");
		btnDeleteColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainframe.deleteColumn(columnname);
				dialog.dispose();
			}
		});
		btnDeleteColumn.setBounds(10, y, 150, 37);
		controlls.add(btnDeleteColumn);
		
		
		
		btnCancel = new JButton("       Save & Continue       ");
		btnCancel.setBounds(320, y, 150, 37);
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAction();
				}
			});
		controlls.add(btnCancel);
			
		content.setPreferredSize(new Dimension(470, y));
	}
	
	private void saveAction(){
		//Put DataSet on Undo Stack
		mainframe.snapShot();
		
		//First edit factors
		for(int i = 0; i<factors.length;i++){
			JTextField tf = textFields.get(i);
			String newname = tf.getText();
			String oldname = factors[i];
			if(!newname.equals(oldname)){
				mainframe.renameFactor(columnname, oldname, newname);
			}
		}
		
		//Second delete factors
		for(String s: columnsToDelete.values()){
			mainframe.deleteFactor(columnname, s);
		}
		
		//Thirdly Cast Column
		String select = (String)comboBox.getSelectedItem();
		if(!select.equals(datatype)){
			mainframe.castColumn(columnname, select);
		
		}
		
		//Lastly Change Column Name
		String newname= textField.getText();
	    if(!newname.equals(columnname)){
	    	mainframe.editColumnname(columnname, newname);
	    }
		//Exit dialog
		dialog.dispose();
	}
	
	private void replaceAction(){
		//Put DataSet on Undo Stack
		mainframe.snapShot();
		if(option1.isSelected()){
			mainframe.setNA(this.columnname, this.median);
		}else if(option2.isSelected()){
			mainframe.setNA(this.columnname, this.average+"");
		}else if(option3.isSelected()){
			mainframe.setNA(this.columnname, this.replaceField.getText());
		} 
		//Exit dialog
		dialog.dispose();
	}
}
