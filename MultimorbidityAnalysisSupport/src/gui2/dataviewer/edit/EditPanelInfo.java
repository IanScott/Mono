package gui2.dataviewer.edit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;

import gui2.MainFrame;

import javax.swing.JButton;

public class EditPanelInfo extends JPanel{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	
	public EditPanelInfo(MainFrame mainframe) {
		this.setLayout(null);
		
		//this.setSize(new Dimension(600, 800));
		
		//Title
		JLabel lblNewLabel = new JLabel("Edit Data");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 11, 191, 33);
		add(lblNewLabel);
		
		//Edit Column Name
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, 62, 134, 26);
		add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(154, 64, 313, 26);
		add(textField);
		textField.setColumns(10);
		
		//Edit Columns
		JLabel lblEditColumns = new JLabel("Edit Columns");
		lblEditColumns.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEditColumns.setBounds(10, 130, 191, 33);
		add(lblEditColumns);
		
		//for loop for each column
		String[] columns = mainframe.getDataSetColumnnames();
		int y = 174;
		for(int i = 0; i<columns.length; i++){
			
			JLabel label = new JLabel("Col "+(i+1));
			label.setFont(new Font("Tahoma", Font.BOLD, 15));
			label.setBounds(10, y, 134, 26);
			add(label);
			
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(154, y, 313, 26);
			textField_1.setText(columns[i]);
			add(textField_1);
			
			JButton btnNewButton = new JButton("delete");
			btnNewButton.setBounds(487, y, 89, 26);
			add(btnNewButton);
			y = y + 30;
		}
		
		//Edit Levels
		JLabel lblEditLevels = new JLabel("Edit Levels");
		lblEditLevels.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEditLevels.setBounds(10, y+53, 191, 33);
		add(lblEditLevels);
		
		y=y+100;
		for(int i = 0; i<columns.length; i++){
			String[] factors = mainframe.getDatasetFactors(columns[i]);
			Boolean flag = true;
			for(int j = 0; j<factors.length; j++){
				
				if(flag){
					JLabel lblColumnname = new JLabel(columns[i]);
					lblColumnname.setFont(new Font("Tahoma", Font.BOLD, 15));
					lblColumnname.setBounds(10, y, 200, 26);
					add(lblColumnname);
					flag =false;
				}
				
				textField_2 = new JTextField();
				textField_2.setColumns(10);
				textField_2.setBounds(230, y, 239, 28);
				textField_2.setText(factors[j]);
				add(textField_2);
				
				JButton button = new JButton("delete");
				button.setBounds(487, y, 89, 26);
				add(button);
				y=y+30;
			}
		}
		
		this.setPreferredSize(new Dimension(580, y+30));
	}
	
	
}
