package guiold;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import guiold.GuiController;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class Colpanel extends JPanel implements Observer{
	private GuiController guicontroller;
	private JPanel colpanel;
	private int size;
	
	public Colpanel(GuiController guicontroller) {
		this.guicontroller = guicontroller;
		init();
		setup();
	}
	
	private void init(){
		
		this.setLayout(null);
		guicontroller.getToolController().addDataSetObserver(this);
	}
	
	private void setup(){
		this.removeAll();
		String[] colnames = guicontroller.getToolController().getDataSetColumnnames();
		size = 52+(30*colnames.length);
		//this.setSize(600,size);
		this.setPreferredSize(new Dimension(600,size));
		
		JLabel lblNewLabel = new JLabel("Edit Columns");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 11, 143, 28);
		add(lblNewLabel);
		
		JLabel lblNewLabel_5 = new JLabel("Columns");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(10, 46, 50, 14);
		add(lblNewLabel_5);
		
		JLabel lblNewLabel_1 = new JLabel("commit");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(388, 46, 50, 14);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("delete");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(438, 46, 50, 14);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("numeric");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(525, 46, 132, 14);
		add(lblNewLabel_2);
		
		addPanels(colnames);
		this.revalidate();
		this.repaint();
	}
	
	private void addPanels(String[] colnames){
		for(int i = 0; i<colnames.length; i++){
			JPanel panel = createPanel(colnames[i]);
			panel.setLocation(0, (60+(26*i)));
			this.add(panel);
		}
	}
	
	private JPanel createPanel(final String col){
		JPanel panel_3 = new JPanel();
		panel_3.setLocation(1, 40);
		panel_3.setSize(580, 26);
		panel_3.setOpaque(false);
		
		panel_3.setLayout(null);
		
		final JTextField textField_1 = new JTextField();
		textField_1.setBounds(10, 1, 375, 24);
		textField_1.setText(col);
		panel_3.add(textField_1);
		
		JButton commit = new JButton("S");
		commit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				commitAction(col, textField_1);
			}
		});
		commit.setFont(new Font("Tahoma", Font.BOLD, 11));
		commit.setBounds(390, 0, 40, 25);
		panel_3.add(commit);
		
		JButton delete = new JButton("-");
		delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteAction(col);
				
				//setup();
				
			}
		});
		delete.setFont(new Font("Tahoma", Font.BOLD, 11));
		delete.setBounds(435, 0, 40, 25);
		panel_3.add(delete);
		
		
		JCheckBox radioButton_1 = new JCheckBox();
		radioButton_1.setBounds(535, 0, 30, 30);
		panel_3.add(radioButton_1);
		
		
		return panel_3;
	}
	
	public int getPanelHeight(){
		return size;
	}

	@Override
	public void update(Observable o, Object arg) {
		setup();
	}
	
	private void commitAction(String col, JTextField tf){
		guicontroller.getToolController().editColumnname(col, tf.getText());
	}
	
	private void deleteAction(String index){
		guicontroller.getToolController().deleteColumn(index);
	}
}
