package gui2.dataviewer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import gui2.MainFrame;

import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class EditDialogPanel extends JPanel{
	private JTextField textField;
	private HashMap<Integer,JTextField> textFields;
	private JDialog dialog;
	private JPanel content;
	private JScrollPane scroll;
	private JButton btnNewButton_1;
	private JButton btnCancel;
	private JButton btnDeleteColumn;
	private String[] factors;
	
	public EditDialogPanel(final MainFrame mainframe, final String columnname) {
		dialog = new JDialog (mainframe, "Edit Column Values", true);
        dialog.setSize (500, 500);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (mainframe);
		
        content = new JPanel();
        scroll = new JScrollPane(content);
		dialog.getContentPane().add(scroll);
		
		textFields = new HashMap<Integer,JTextField>();
		factors = mainframe.getDatasetFactors(columnname);
		
		content.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Edit Column");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 11, 146, 23);
		content.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(151, 45, 202, 23);
		content.add(textField);
		textField.setText(columnname);
		
		
		JLabel lblNewLabel_1 = new JLabel("Column name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, 45, 120, 23);
		
		content.add(lblNewLabel_1);
		
		JLabel lblEditLevels = new JLabel("Edit Levels");
		lblEditLevels.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEditLevels.setBounds(10, 88, 146, 23);
		content.add(lblEditLevels);
		
		btnNewButton_1 = new JButton("Save Edited Names");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				for(int i = 0; i<factors.length;i++){
					JTextField tf = textFields.get(i);
					String newname = tf.getText();
					String oldname = factors[i];
					
					mainframe.renameFactor(columnname, oldname, newname);
				}
				mainframe.editColumnname(columnname, textField.getText());
				dialog.dispose();
			}
		});
		btnNewButton_1.setBounds(10, 373, 202, 37);
		content.add(btnNewButton_1);
		
		btnCancel = new JButton("Continue");
		btnCancel.setBounds(334, 421, 148, 37);
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		content.add(btnCancel);
		
		btnDeleteColumn = new JButton("Delete Entire Column");
		btnDeleteColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainframe.deleteColumn(columnname);
				dialog.dispose();
			}
		});
		btnDeleteColumn.setBounds(10, 421, 202, 37);
		content.add(btnDeleteColumn);
		
		for(int i =0; i<factors.length; i++){
			final String factor = factors[i];
			JLabel lblNewLabel_2 = new JLabel("Level "+(i+1));
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblNewLabel_2.setBounds(10, 124+(i*30), 120, 23);
			content.add(lblNewLabel_2);
			
			JTextField textField_1 = new JTextField();
			textField_1.setBounds(151, 124+(i*30), 202, 23);
			textFields.put(i,textField_1);
			
			textField_1.setText(factor);
			content.add(textField_1);
			textField_1.setColumns(10);
			
			final JButton btnNewButton = new JButton("delete");
			btnNewButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					mainframe.deleteFactor(columnname, factor);
					btnNewButton.setEnabled(false);
					btnNewButton.setText("Deleted");
					
				}});
			btnNewButton.setBounds(382, 124+(i*30), 89, 23);
			content.add(btnNewButton);
		
		}
		
		dialog.setVisible(true);
	}
}
