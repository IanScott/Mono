package gui2.dataviewer.info;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import gui2.MainFrame;


public class FreqPanel extends JPanel {
	
	private MainFrame mainframe;
	private int size;
	private String[]columnnames;
	private String[] levels;
	private JComboBox<String> comboBox;
	private JPanel panel;
	
	
	public FreqPanel(MainFrame mainframe, String columnname) {
		this.mainframe = mainframe;
		this.columnnames = mainframe.getDataSetColumnnames();
		this.levels = mainframe.getDatasetFactors(columnnames[0]);
		
		
		init();
		if(columnname ==null){
			columnname = columnnames[0];
		}
		setup(columnname,levels);
	}
	
	private void init(){
		this.setLayout(null);
		size = 50 +(maxLevel()*28);
		//PAS AAN!!!
		this.setSize(600, 300);
		this.setPreferredSize(new Dimension(600,size));
	}
	
	private void setup(final String columnname, String[] levels){
		this.removeAll();
		this.columnnames = mainframe.getDataSetColumnnames();
		JLabel lblNewLabel = new JLabel("Summary");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 5, 141, 26);
		add(lblNewLabel);
		
		JLabel lblNewLabel5 = new JLabel("Variables");
		lblNewLabel5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel5.setBounds(10, 30, 120, 26);
		add(lblNewLabel5);
		
		JLabel lblNewLabel6 = new JLabel("Levels");
		lblNewLabel6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel6.setBounds(194, 30, 120, 26);
		add(lblNewLabel6);
		
		JLabel lblNewLabel1 = new JLabel("Frequency");
		lblNewLabel1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel1.setBounds(390, 30, 120, 26);
		add(lblNewLabel1);
		
		JLabel lblNewLabel2 = new JLabel("Percentage");
		lblNewLabel2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel2.setBounds(490, 30, 80, 26);
		add(lblNewLabel2);
		
		
		
		comboBox = new JComboBox<String>(columnnames);
		comboBox.setSelectedItem(columnname);
		comboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				comboBoxAction(e);
				
			}});
		comboBox.setBounds(10, 50, 170, 24);
		add(comboBox);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setLocation(184, 50);
		
		createLevels(columnname, levels);
		
		this.add(panel);
		this.revalidate();
		this.repaint();
	}
	
	private void createLevels(String columnname, String[] levels){
		int y = 0;
		if(levels != null){
			for(int i= 0; i<levels.length; i++){
				JPanel panel1 = createLevel(columnname, levels[i], i);
				panel1.setLocation(0, y);
				panel.add(panel1);
				y= y+26;
			}
		}
		panel.setSize(415, y);
		panel.revalidate();
	}
	
	private JPanel createLevel(final String columnname, final String level, final int index){
		JPanel panel = new JPanel();
		
		panel.setSize(450, 26);
		panel.setLayout(null);
		
		final JTextField textField = new JTextField(level);
		textField.setBounds(10, 0, 190, 24);
		panel.add(textField);

		final JTextField textField1 = new JTextField("Frequency");
		textField1.setBounds(206, 0, 80, 24);
        textField1.setText(mainframe.getFrequency(columnname, level));
		panel.add(textField1);

		
		final JTextField textField2 = new JTextField("Percentage");
		textField2.setBounds(306, 0, 80, 24);
		textField2.setText(mainframe.getPercentage(columnname, level));
		panel.add(textField2);

		return panel;
	}
	public int getPanelHeight(){
		return size;
	}
	
	
	public String getSelectedColumn(){
		return (String)comboBox.getSelectedItem();
	}
	
	private void comboBoxAction(ActionEvent e){
		@SuppressWarnings("unchecked")
		String select =(String) ((JComboBox<String>)e.getSource()).getSelectedItem();
		//createLevels(guicontroller.getToolController().getDatasetFactors(select));
		setup(select,mainframe.getDatasetFactors(select));
		size = 50 +(levels.length*28);
		this.getParent().setSize(600, size);
		//this.revalidate();
		this.getParent().revalidate();
		mainframe.barChart(select);
	}
	
	private int maxLevel(){
		return mainframe.maxFactor();
	}
	
	
}
