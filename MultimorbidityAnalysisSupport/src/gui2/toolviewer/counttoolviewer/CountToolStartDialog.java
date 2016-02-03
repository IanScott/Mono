package gui2.toolviewer.counttoolviewer;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import gui2.MainFrame;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

public class CountToolStartDialog{
	
	private JDialog dialog;
	private MainFrame mainframe;
	private ButtonGroup buttongroup;
	private ArrayList<JCheckBox> checkboxes;
	private String arg1;
	private String[] arg;
	
	
	public CountToolStartDialog(MainFrame mainframe) {
		this.mainframe = mainframe;
		this.buttongroup = new ButtonGroup();
		this.checkboxes = new ArrayList<JCheckBox>();
		
		dialog = new JDialog (mainframe, "Select", true);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
        
		
		JPanel content = setupPanel();
		JScrollPane scrollpane = new JScrollPane(content);
		scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.dialog.setContentPane(scrollpane);
		this.dialog.setSize (500, 300);
		dialog.setLocationRelativeTo(mainframe);
		this.dialog.setVisible(true);
		
	}
	private JPanel setupPanel(){
		final String[] columns = mainframe.getDataSetColumnnames();
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		
		JLabel lblColums = new JLabel("Column Names");
		lblColums.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblColums.setBounds(10, 11, 131, 14);
		panel_1.add(lblColums);
		
		JLabel lblNewLabel_1 = new JLabel("Selected");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(193, 11, 75, 14);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("True Value");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(285, 11, 131, 14);
		panel_1.add(lblNewLabel_2);
		
		buttongroup = new ButtonGroup();
		int y = 36;
		for(int i =0; i<columns.length;i++, y=y+36 ){
			String[] factors = mainframe.getDatasetFactors(columns[i]);
			
			JLabel lblNewLabel = new JLabel(columns[i]);
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel.setBounds(10, y, 181, 20);
			panel_1.add(lblNewLabel);
			
			JRadioButton jrb= new JRadioButton();
			jrb.setBounds(200, y, 26, 23);
			jrb.setActionCommand(columns[i]);
			buttongroup.add(jrb);
			panel_1.add(jrb);
			
			if(buttongroup.getButtonCount()==1){
				jrb.setSelected(true);;
			}
			
			JCheckBox chckbxNewCheckBox = new JCheckBox("");
			chckbxNewCheckBox.setBounds(235, y, 26, 23);
			checkboxes.add(chckbxNewCheckBox);
			panel_1.add(chckbxNewCheckBox);
			
			
			JComboBox<String> comboBox = new JComboBox<String>();
			comboBox.setBounds(285, y, 131, 20);
			for(int j = 0; j<factors.length; j++){
				comboBox.addItem(factors[j]);
			}
			
			
			panel_1.add(comboBox);
		
		}
		
		
		panel_1.setPreferredSize(new Dimension(500,columns.length*50+15));
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.setBounds(10, y, 220, 40);
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
			
		});
		panel_1.add(btnNewButton);
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.setBounds(260, y, 220, 40);
		btnContinue.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				arg1 = buttongroup.getSelection().getActionCommand();
				System.out.println(arg1);
				int temp = 0;
				
				for(int i = 0; i<checkboxes.size();i++){
					if(checkboxes.get(i).isSelected()){
						temp++;
					}
				}
				arg = new String[temp];
				temp=0;
				for(int i = 0; i<checkboxes.size();i++){
					if(checkboxes.get(i).isSelected()){
						arg[temp] = columns[i];
						temp++;
						System.out.println(columns[i]);
					}
					
				}
				
				
				
				dialog.dispose();
			}});
		panel_1.add(btnContinue);
		return panel_1;
	}
	
	public String getGroupby(){
		return arg1;
	}
	
	public String[] getLogicalColumns(){
		return arg;
	}
}
