package poc2.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class LevPanel extends JPanel implements Observer{
	
	private GuiController guicontroller;
	private int size;
	private String[]columnnames;
	private String[] levels;
	private JComboBox<String> comboBox;
	private JPanel panel;
	private String lastColumnname;
	
	public LevPanel(GuiController guicontroller, String columnname) {
		this.guicontroller = guicontroller;
		this.columnnames = guicontroller.getToolController().getDataSetColumnnames();
		this.levels = guicontroller.getToolController().getDatasetFactors(columnnames[0]);
		guicontroller.getToolController().addDataSetObserver(this);
		
		init();
		if(columnname ==null){
			columnname = columnnames[0];
		}
		setup(columnname,levels);
	}
	
	private void init(){
		this.setLayout(null);
		size = 50 +(maxLevel()*28);
		//this.setSize(600, size);
		this.setPreferredSize(new Dimension(600,size));
	}
	
	private void setup(final String columnname, String[] levels){
		this.removeAll();
		this.columnnames = guicontroller.getToolController().getDataSetColumnnames();
		JLabel lblNewLabel = new JLabel("Edit Levels");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 5, 141, 26);
		add(lblNewLabel);
		
		JLabel lblNewLabel5 = new JLabel("Columns");
		lblNewLabel5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel5.setBounds(10, 30, 120, 26);
		add(lblNewLabel5);
		
		JLabel lblNewLabel6 = new JLabel("Levels");
		lblNewLabel6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel6.setBounds(194, 30, 120, 26);
		add(lblNewLabel6);
		
		JLabel lblNewLabel1 = new JLabel("commit");
		lblNewLabel1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel1.setBounds(388, 30, 120, 26);
		add(lblNewLabel1);
		
		JLabel lblNewLabel2 = new JLabel("delete");
		lblNewLabel2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel2.setBounds(437, 30, 60, 26);
		add(lblNewLabel2);
		
		JLabel lblNewLabel3 = new JLabel("Merge");
		lblNewLabel3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel3.setBounds(530, 30, 80, 26);
		add(lblNewLabel3);
		
		
		comboBox = new JComboBox<String>(columnnames);
		comboBox.setSelectedItem(columnname);
		comboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				lastColumnname = columnname;
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
		for(int i= 0; i<levels.length; i++){
			JPanel panel1 = createLevel(columnname, levels[i], i);
			panel1.setLocation(0, y);
			panel.add(panel1);
			y= y+26;
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
		
		JButton delete = new JButton("S");
		delete.setFont(new Font("Tahoma", Font.BOLD, 11));
		delete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String nlevel = textField.getText();
				saveAction(columnname,index, level, nlevel);
				
			}});
		delete.setBounds(206, 0, 40, 25);
		panel.add(delete);
		
		JButton na = new JButton("-");
		na.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				naAction(columnname, level, index);
				
			}});
		//na.setFont(new Font("Tahoma", Font.BOLD, 8));
		na.setBounds(250, 0, 40, 25);
		panel.add(na);
		
		JButton merge = new JButton("+");
		//merge.setFont(new Font("Tahoma", Font.BOLD, 10));
		merge.setBounds(355, 0, 40, 25);
		panel.add(merge);
		
		JCheckBox checkBox = new JCheckBox("");
		checkBox.setBounds(325, 4, 26, 23);
		panel.add(checkBox);
		
		
		return panel;
	}
	public int getPanelHeight(){
		return size;
	}
	
	
	
	@Override
	public void update(Observable o, Object arg) {
		String columnname = getSelectedColumn();
		String[] colums = guicontroller.getToolController().getDataSetColumnnames();
		
		if(Arrays.asList(colums).contains(columnname)){
		
		setup(getSelectedColumn(), guicontroller.getToolController().getDatasetFactors(getSelectedColumn()));}
		else{
			setup(colums[0], guicontroller.getToolController().getDatasetFactors(colums[0]));
		}
		
	}
	
	public String getSelectedColumn(){
		return (String)comboBox.getSelectedItem();
	}
	
	private void comboBoxAction(ActionEvent e){
		@SuppressWarnings("unchecked")
		String select =(String) ((JComboBox<String>)e.getSource()).getSelectedItem();
		//createLevels(guicontroller.getToolController().getDatasetFactors(select));
		setup(select,guicontroller.getToolController().getDatasetFactors(select));
		size = 50 +(levels.length*28);
		this.getParent().setSize(600, size);
		//this.revalidate();
		this.getParent().revalidate();
	}
	
	private int maxLevel(){
		return guicontroller.getToolController().maxFactor();
	}
	
	private void saveAction(String columnname, int index,String level, String newlevel){
		String[] factors = guicontroller.getToolController().getDatasetFactors(columnname);
		factors[index] = newlevel; 
		guicontroller.getToolController().renameFactors(columnname, factors);
		//setup(columnname,guicontroller.getToolController().getDatasetFactors(columnname));
	}
	
	private void naAction(String columnname, String level, int index){
		guicontroller.getToolController().deleteFactor(columnname, level);
		//setup(columnname,guicontroller.getToolController().getDatasetFactors(columnname));
		
	}
}
