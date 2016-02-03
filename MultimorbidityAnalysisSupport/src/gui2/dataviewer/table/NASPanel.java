package gui2.dataviewer.table;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import gui2.MainFrame;

public class NASPanel extends JPanel{
	private int size;
	private MainFrame mainframe;
	private JDialog dialog;
	private JPanel content;
	
	public NASPanel(MainFrame mainframe) {
		this.mainframe= mainframe;
		
		dialog = new JDialog (mainframe, "Edit Data Set", true);
        dialog.setSize (600, 300);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (mainframe);
		
		content = new JPanel();
		
		content.setLayout(null);
		
		this.setPreferredSize(new Dimension(600,200));
		setup();
		dialog.setContentPane(content);
		dialog.setVisible(true);
	}
	
	private void setup(){
		
		JLabel lblEditData = new JLabel("Edit Data");
		lblEditData.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEditData.setBounds(10, 11, 107, 25);
		content.add(lblEditData);
		
		JLabel lblNewLabel = new JLabel("Data Set Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 58, 141, 25);
		content.add(lblNewLabel);
		
		JTextField textField = new JTextField();
		textField.setBounds(161, 60, 229, 25);
		content.add(textField);
		textField.setText(mainframe.getDataSetName());
		
		JLabel lblNewLabel3 = new JLabel("Edit Unknown Values");
		lblNewLabel3.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel3.setBounds(10, 121, 227, 14);
		content.add(lblNewLabel3);
		
		final JComboBox<String> comboBox = new JComboBox<String>(mainframe.getDataSetColumnnames());
		//JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 171, 170, 24);
		content.add(comboBox);
		
		final JComboBox<String> comboBox_1 = new JComboBox<String>(mainframe.getDatasetFactors(mainframe.getDataSetColumnnames()[0]));
		//JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(190, 171, 176, 24);
		content.add(comboBox_1);
		
		comboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String item = (String)comboBox.getSelectedItem();
				String[] factors = mainframe.getDatasetFactors(item);
				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(factors);
				comboBox_1.setModel(model);
				
			}});
		
		JButton btnNewButton = new JButton("Drop Rows with N\\A's");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dropnas();
			}
		});
		btnNewButton.setBounds(383, 217, 176, 24);
		content.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Replace NA's in");
		btnNewButton_1.setBounds(383, 171, 176, 24);
		btnNewButton_1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String temp = (String)comboBox.getSelectedItem();
				String temp2 = (String)comboBox_1.getSelectedItem();
				replaceNas(temp, temp2);
				
			}});
		content.add(btnNewButton_1);
		
		JLabel lblReplaceWith = new JLabel("With");
		lblReplaceWith.setHorizontalAlignment(SwingConstants.LEFT);
		lblReplaceWith.setBounds(190, 146, 115, 14);
		content.add(lblReplaceWith);
		
		JLabel lblColumn = new JLabel("Column");
		lblColumn.setBounds(10, 146, 107, 14);
		content.add(lblColumn);
		
	}
	
	public int getPanelHeight(){
		return size;
	}
	
	private void dropnas(){
		mainframe.omitNa();
	}
	
	private void replaceNas(String columnname, String na){
		mainframe.setNA(columnname, na);
	}
	
}
