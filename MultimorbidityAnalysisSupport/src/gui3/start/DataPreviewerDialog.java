package gui3.start;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import gui3.TableRowUtilities;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataPreviewerDialog {
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JTable table;
	private File file;
	
	
    private JDialog dialog;
    private boolean answer;
	private String separator = ",";
    
	private JCheckBox chckbxNewCheckBox;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JRadioButton rdbtnNewRadioButton_3;
	
	
	public DataPreviewerDialog(JFrame parent, File file) {
		this.file = file;
		dialog = new JDialog (parent, "Information", true);
        dialog.setSize (600, 410);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (parent);
        
		
		
		//setModal(true);
		//this.setSize(600,410);
		dialog.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		dialog.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnNewButton_1 = new JButton("OK");
		btnNewButton_1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				answer = true;
				dialog.dispose();
			}
		});
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Cancel");
		btnNewButton_2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				answer = false;
				dialog.dispose();
			}
			
		});
		panel_1.add(btnNewButton_2);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(600, 160));
		dialog.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Separator");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblNewLabel_1.setBounds(322, 50, 80, 30);
		panel.add(lblNewLabel_1);
		
		chckbxNewCheckBox = new JCheckBox("");
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxNewCheckBox.setFont(new Font("SansSerif", Font.BOLD, 20));
		chckbxNewCheckBox.setBounds(274, 50, 30, 30);
		panel.add(chckbxNewCheckBox);
		
		rdbtnNewRadioButton = new JRadioButton("Comma");
		rdbtnNewRadioButton.setSelected(true);
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(403, 56, 109, 23);
		panel.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("Semicolon");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(403, 82, 109, 23);
		panel.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Tab");
		buttonGroup.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.setBounds(403, 108, 109, 23);
		panel.add(rdbtnNewRadioButton_2);
		
		JLabel lblNewLabel = new JLabel("Header");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblNewLabel.setBounds(211, 50, 57, 30);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(457, 135, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		rdbtnNewRadioButton_3 = new JRadioButton("Other");
		buttonGroup.add(rdbtnNewRadioButton_3);
		rdbtnNewRadioButton_3.setBounds(403, 134, 53, 23);
		panel.add(rdbtnNewRadioButton_3);
		
		JLabel lblPreviewParameters = new JLabel("Preview parameters");
		lblPreviewParameters.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblPreviewParameters.setBounds(211, 11, 255, 28);
		panel.add(lblPreviewParameters);
		
		JButton btnNewButton = new JButton("Preview");
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnNewButton.setBounds(10, 50, 159, 103);
		btnNewButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				preview();
			}
		});
		panel.add(btnNewButton);
		
		
		//get radiobutton value
		
		DefaultTableModel model= Previewer.preview(file, ",", chckbxNewCheckBox.isSelected());
		table = new JTable();
		table.setModel(model);
		JScrollPane scrollPane = new JScrollPane(table);
		TableRowUtilities.addNumberColumn(table, 1, false);
		dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		dialog.setVisible (true);
	}
	
	private void preview(){
		//get radiobutton value
		if(rdbtnNewRadioButton.isSelected()){separator = ",";}
		if(rdbtnNewRadioButton_1.isSelected()){separator = ";";}
		if(rdbtnNewRadioButton_2.isSelected()){separator = "\t";}
		if(rdbtnNewRadioButton_3.isSelected()){separator = textField.getText();}
		
		DefaultTableModel model = Previewer.preview(file, separator, chckbxNewCheckBox.isSelected());
		table.setModel(model);
		table.repaint();
	}
	
	public boolean getAnswer(){
		return answer;
	}
	public boolean hasHead(){
		return chckbxNewCheckBox.isSelected();
	}
	public String getSeparator(){
		return separator;
	}
	
	
}
