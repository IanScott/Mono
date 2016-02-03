package gui3.dataviewer.edit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JTextField;

import gui3.MainFrame;
import gui3.dataviewer.EditDialogPanel;

import javax.swing.JButton;

public class EditPanelInfo extends JPanel{
	private JTextField textField;
	private JTextField columnNameField;
	
	
	
	public EditPanelInfo(final MainFrame mainframe) {
		
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
		textField.setBounds(90, 64, 290, 26);
		textField.setText(mainframe.getDataSetName());
		add(textField);
		textField.setColumns(10);
		
		//Edit Columns
		JLabel lblEditColumns = new JLabel("Columns");
		lblEditColumns.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEditColumns.setBounds(10, 130, 191, 33);
		add(lblEditColumns);
		
		//for loop for each column
		final String[] columns = mainframe.getDataSetColumnnames();
		int y = 165;
		for(int i = 0; i<columns.length; i++){
			final int index = i;
			JLabel columnLabel = new JLabel("Col "+(i+1));
			columnLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			columnLabel.setBounds(10, y, 80, 26);
			add(columnLabel);
			
			columnNameField = new JTextField();
			columnNameField.setBounds(90, y, 210, 26);
			columnNameField.setText(columns[i]);
			columnNameField.setEditable(false);
			add(columnNameField);
			
			Boolean nas = mainframe.hasNa(columns[i]);
			JTextField nalabel = new JTextField(nas.toString());
			nalabel.setEditable(false);
			nalabel.setFont(new Font("Tahoma", Font.BOLD, 10));
			nalabel.setBounds(310, y, 30, 26);
			if(nas){
				nalabel.setForeground(Color.RED);
			}
			add(nalabel);
			
			JTextField typelabel = new JTextField(mainframe.getColumnType(columns[i]));
			typelabel.setEditable(false);
			typelabel.setFont(new Font("Tahoma", Font.BOLD, 10));
			typelabel.setBounds(345, y, 60, 26);
			add(typelabel);
			
			final JButton editButton = new JButton("edit");
			editButton.setBounds(410, y, 85, 26);
			editButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					new EditDialogPanel(mainframe, columns[index]);
					
				} });
			add(editButton);
			
			final JButton deleteButton = new JButton("delete");
			deleteButton.setBounds(505, y, 85, 26);
			deleteButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					mainframe.snapShot();
					mainframe.deleteColumn(columns[index]);
				} });
			add(deleteButton);
			y = y + 30;
		}

		this.setPreferredSize(new Dimension(580, y+30));
	}
	
	
}
