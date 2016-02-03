package gui3.dataviewer.table;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import gui3.MainFrame;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditDataDialog extends JPanel{
	private JTextField textField;
	private JDialog dialog;
	private JScrollPane scroll;
	
	public EditDataDialog(final MainFrame mainframe) {
		dialog = new JDialog (mainframe, "Edit Data Set", true);
        dialog.setSize (412, 245);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (mainframe);
		
		JPanel content = new JPanel();
		
		content.setLayout(null);
		
		scroll = new JScrollPane(content);
		dialog.getContentPane().add(scroll);
		
		JLabel lblEditData = new JLabel("Edit Data");
		lblEditData.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEditData.setBounds(10, 11, 107, 25);
		content.add(lblEditData);
		
		JLabel lblNewLabel = new JLabel("Data Set Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 58, 141, 25);
		content.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(161, 60, 229, 25);
		content.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Replace Unknown Values");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(10, 103, 172, 37);
		content.add(btnNewButton);
		
		JButton btnDropUnknownValues = new JButton("Drop Unknown Values");
		btnDropUnknownValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainframe.snapShot();
				mainframe.omitNa();
			}
		});
		btnDropUnknownValues.setBounds(10, 151, 172, 37);
		content.add(btnDropUnknownValues);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		btnBack.setBounds(218, 151, 172, 37);
		content.add(btnBack);
		
		dialog.setVisible(true);
	}
}
