package poc2.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComboBox;
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

public class NASPanel extends JPanel implements Observer{
	private int size;
	private GuiController guicontroller;
	
	public NASPanel(GuiController guicontroller) {
		guicontroller.getToolController().addDataSetObserver(this);
		this.guicontroller = guicontroller;
		setLayout(null);
		size = 180;
		//setSize(600,size);
		this.setPreferredSize(new Dimension(600,size));
		setup();
		
	}
	
	private void setup(){
		removeAll();
		JLabel lblNewLabel = new JLabel("Edit Unknown Values");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 19, 227, 14);
		add(lblNewLabel);
		
		final JComboBox<String> comboBox = new JComboBox<String>(guicontroller.getToolController().getDataSetColumnnames());
		comboBox.setBounds(197, 69, 170, 24);
		add(comboBox);
		
		final JComboBox<String> comboBox_1 = new JComboBox<String>(guicontroller.getToolController().getDatasetFactors(guicontroller.getToolController().getDataSetColumnnames()[0]));
		comboBox_1.setBounds(377, 70, 176, 24);
		add(comboBox_1);
		
		comboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String item = (String)comboBox.getSelectedItem();
				String[] factors = guicontroller.getToolController().getDatasetFactors(item);
				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(factors);
				comboBox_1.setModel(model);
				
			}});
		
		JButton btnNewButton = new JButton("Drop Rows with N\\A's");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dropnas();
			}
		});
		btnNewButton.setBounds(10, 126, 170, 24);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Replace NA's in");
		btnNewButton_1.setBounds(7, 70, 176, 24);
		btnNewButton_1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String temp = (String)comboBox.getSelectedItem();
				String temp2 = (String)comboBox_1.getSelectedItem();
				replaceNas(temp, temp2);
				
			}});
		add(btnNewButton_1);
		
		JLabel lblReplaceWith = new JLabel("With");
		lblReplaceWith.setHorizontalAlignment(SwingConstants.LEFT);
		lblReplaceWith.setBounds(377, 44, 115, 14);
		add(lblReplaceWith);
		
		JLabel lblColumn = new JLabel("Column");
		lblColumn.setBounds(197, 44, 107, 14);
		add(lblColumn);
		
		revalidate();
	}
	
	public int getPanelHeight(){
		return size;
	}
	
	private void dropnas(){
		guicontroller.getToolController().omitNa();
	}
	
	private void replaceNas(String columnname, String na){
		guicontroller.getToolController().setNA(columnname, na);
	}
	@Override
	public void update(Observable o, Object arg) {
		setup();
		
	}
}
