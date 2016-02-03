package gui3.dataviewer;

import javax.swing.JDialog;
import javax.swing.JPanel;

import gui3.MainFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TypeEdit {
	private JDialog dialog;
	private String datatype;
	
	public TypeEdit(final MainFrame mainframe, final JDialog parentdialog, final String columnname){
		dialog = new JDialog (mainframe, "Edit Type", true);
        dialog.setSize (400, 200);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (mainframe);
        dialog.getContentPane().setLayout(null);
        
        String[] datatypes = {"FACTOR","INTEGER","NUMERIC","CHARACTER","LOGICAL"};
        final JComboBox<String> comboBox = new JComboBox<String>(datatypes);
        comboBox.setBounds(10, 87, 227, 35);
        datatype = mainframe.getColumnType(columnname);
        comboBox.setSelectedItem(datatype);
        dialog.getContentPane().add(comboBox);
        
        JButton btnNewButton = new JButton("Cast");
        btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String select = (String)comboBox.getSelectedItem();
				if(!select.equals(datatype)){
					mainframe.castColumn(columnname, select);
					parentdialog.dispose();
					dialog.dispose();
					new EditDialogPanel(mainframe, columnname);
				}
				
			} });
        btnNewButton.setBounds(247, 87, 137, 35);
        dialog.getContentPane().add(btnNewButton);
        
        JLabel lblNewLabel = new JLabel("Data Type");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel.setBounds(10, 59, 94, 26);
        dialog.getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Transform the Data Type of the Column");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1.setBounds(10, 11, 374, 26);
        dialog.getContentPane().add(lblNewLabel_1);
        
        this.dialog.setVisible(true);
	}
}
