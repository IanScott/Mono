package gui.editPane;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

/**
 * A panel that displays the Edit Menu Panel.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class GroupPanel extends JPanel{
	
	private static final long serialVersionUID = 5519589615880778133L;
	private ArrayList<JTextField> bounds;
	private ArrayList<JTextField> labels;
	private JButton saveButton;
	private JButton backButton;
	private final String[] nrbounds= {"1","2","3","4","5","6","7","8","9","10"};
	
	private JTextField bfield1;
	private JTextField bfield2;
	private JTextField bfield3;
	private JTextField bfield4;
	private JTextField bfield5;
	private JTextField bfield6;
	private JTextField bfield7;
	private JTextField bfield8;
	private JTextField bfield9;
	
	private JTextField blabel1;
	private JTextField blabel2;
	private JTextField blabel3;
	private JTextField blabel4;
	private JTextField blabel5;
	private JTextField blabel6;
	private JTextField blabel7;
	private JTextField blabel8;
	private JTextField blabel9;
	private JTextField blabel10;
	
	
	
	;
	/**
	 * Constructor.
	 */
	public GroupPanel() {
		this.bounds = new ArrayList<JTextField>();
		this.labels = new ArrayList<JTextField>();
		
		setLayout(null);
		
		saveButton = new JButton("Save and Continue");
		saveButton.setBounds(337, 243, 153, 33);
		add(saveButton);
		
		backButton = new JButton("Back");
		backButton.setBounds(10, 243, 153, 33);
		add(backButton);
		
		JComboBox<String> comboBox = new JComboBox<String>(nrbounds);
		comboBox.setBounds(10, 61, 79, 20);
		add(comboBox);
		comboBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
			          Object item = e.getItem();
			          hidefields();
			          setBounds((String) item);
			       }
			}
			
		});

		
		JLabel lblNewLabel = new JLabel("Bounds");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(115, 22, 60, 21);
		add(lblNewLabel);
		
		JLabel lblLabels = new JLabel("Labels");
		lblLabels.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLabels.setBounds(185, 22, 98, 21);
		add(lblLabels);
		
		JLabel label_10 = new JLabel("Bounds");
		label_10.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_10.setBounds(322, 22, 60, 21);
		add(label_10);
		
		JLabel label_11 = new JLabel("Labels");
		label_11.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_11.setBounds(392, 22, 98, 21);
		add(label_11);
		
		//Bounds 1 - 5
		bfield1 = new JTextField();
		bfield1.setBounds(115, 71, 60, 21);
		add(bfield1);
		bounds.add(bfield1);
		
		bfield2 = new JTextField();
		bfield2.setBounds(115, 103, 60, 21);
		add(bfield2);
		bounds.add(bfield2);
		
		bfield3 = new JTextField();
		bfield3.setColumns(10);
		bfield3.setBounds(115, 135, 60, 21);
		add(bfield3);
		bounds.add(bfield3);
		
		bfield4 = new JTextField();
		bfield4.setBounds(115, 167, 60, 21);
		add(bfield4);
		bounds.add(bfield4);
		
		bfield5 = new JTextField();
		bfield5.setBounds(115, 199, 60, 21);
		add(bfield5);
		bounds.add(bfield5);
		
		//Labels 1-5
		blabel1 = new JTextField();
		blabel1.setColumns(10);
		blabel1.setBounds(185, 61, 98, 21);
		add(blabel1);
		labels.add(blabel1);
		
		blabel2 = new JTextField();
		blabel2.setColumns(10);
		blabel2.setBounds(185, 93, 98, 21);
		add(blabel2);
		labels.add(blabel2);
		
		blabel3 = new JTextField();
		blabel3.setColumns(10);
		blabel3.setBounds(185, 125, 98, 21);
		add(blabel3);
		labels.add(blabel3);
		
		blabel4 = new JTextField();
		blabel4.setColumns(10);
		blabel4.setBounds(185, 157, 98, 21);
		add(blabel4);
		labels.add(blabel4);
		
		blabel5 = new JTextField();
		blabel5.setColumns(10);
		blabel5.setBounds(185, 190, 98, 21);
		add(blabel5);
		labels.add(blabel5);
		
		JLabel label = new JLabel("1");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(86, 61, 19, 21);
		add(label);
		
		JLabel label_1 = new JLabel("2");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(86, 96, 19, 21);
		add(label_1);
		
		JLabel label_2 = new JLabel("3");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(86, 128, 19, 21);
		add(label_2);
		
		JLabel label_3 = new JLabel("4");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(86, 160, 19, 21);
		add(label_3);
		
		JLabel label_4 = new JLabel("5");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(86, 193, 19, 21);
		add(label_4);
		
		JLabel label_5 = new JLabel("6");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(293, 61, 19, 21);
		add(label_5);
		
		JLabel label_6 = new JLabel("7");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(293, 93, 19, 21);
		add(label_6);
		
		JLabel label_7 = new JLabel("8");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setBounds(293, 125, 19, 21);
		add(label_7);
		
		JLabel label_8 = new JLabel("9");
		label_8.setHorizontalAlignment(SwingConstants.RIGHT);
		label_8.setBounds(293, 157, 19, 21);
		add(label_8);
		
		JLabel label_9 = new JLabel("10");
		label_9.setHorizontalAlignment(SwingConstants.RIGHT);
		label_9.setBounds(293, 190, 19, 21);
		add(label_9);
		
		//bounds 6-10
		bfield6 = new JTextField();
		bfield6.setColumns(10);
		bfield6.setBounds(322, 70, 60, 21);
		add(bfield6);
		
		bfield7 = new JTextField();
		bfield7.setColumns(10);
		bfield7.setBounds(322, 102, 60, 21);
		add(bfield7);
		
		bfield8 = new JTextField();
		bfield8.setColumns(10);
		bfield8.setBounds(322, 134, 60, 21);
		add(bfield8);
		
		bfield9 = new JTextField();
		bfield9.setColumns(10);
		bfield9.setBounds(322, 166, 60, 21);
		add(bfield9);
		
		
		
		// labels 6 -10
		blabel6 = new JTextField();
		blabel6.setColumns(10);
		blabel6.setBounds(392, 61, 98, 21);
		add(blabel6);
		
		blabel7 = new JTextField();
		blabel7.setColumns(10);
		blabel7.setBounds(392, 93, 98, 21);
		add(blabel7);
		
		blabel8 = new JTextField();
		blabel8.setColumns(10);
		blabel8.setBounds(392, 125, 98, 21);
		add(blabel8);
	
		blabel9 = new JTextField();
		blabel9.setColumns(10);
		blabel9.setBounds(392, 157, 98, 21);
		add(blabel9);
	
		blabel10 = new JTextField();
		blabel10.setColumns(10);
		blabel10.setBounds(392, 190, 98, 21);
		add(blabel10);
		
		JLabel lblNewLabel_1 = new JLabel("Number of Groups");
		lblNewLabel_1.setBounds(10, 25, 95, 14);
		add(lblNewLabel_1);
		
		JTextField txtInf = new JTextField();
		txtInf.setEditable(false);
		txtInf.setText("INF");
		txtInf.setBounds(115, 43, 60, 21);
		add(txtInf);
		
		JTextField txtInf_1 = new JTextField();
		txtInf_1.setEditable(false);
		txtInf_1.setText("INF");
		txtInf_1.setColumns(10);
		txtInf_1.setBounds(322, 199, 60, 21);
		add(txtInf_1);
		
		hidefields();
	}
	public int[] getBoundArray(){
		ArrayList<Integer> intarraylist = new ArrayList<Integer>();
		for(JTextField t: bounds){
			String bound = t.getText();
			if(!bound.isEmpty()){
				try{
					int x = Integer.parseInt(t.getText());
					intarraylist.add(x);
				}catch(Exception e){
					break;
				}
			}else{
				break;
			}
		}
		int[] intarray = new int[intarraylist.size()];
		for(int i =0; i<intarraylist.size(); i++){
			intarray[i] = intarraylist.get(i);
		}
		return intarray;
	}
	
	public String[] getLabelArray(){
		ArrayList<String> stringarraylist = new ArrayList<String>();
		for(JTextField t: labels){
			String label = t.getText();
			if(!label.isEmpty()){
				String x = t.getText();
				stringarraylist.add(x);
			}else{
				break;
			}
		}
		
		String[] stringarray = new String[stringarraylist.size()];
		for(int i =0; i<stringarraylist.size(); i++){
			stringarray[i] = stringarraylist.get(i);
		}
		return stringarray;
	}
	
	/**
	 * Method returns the save button.
	 * @return the save button
	 */
	public JButton getSaveButton(){
		return saveButton;
	}
	
	/**
	 * Method returns the back button.
	 * @return the backbutton
	 */
	public JButton getBackButton(){
		return backButton;
	}
	
	private void setBounds(String number){
		switch (number) {
        	case "10":  blabel10.setVisible(true);
        				bfield9.setVisible(true);
        	case "9":   blabel9.setVisible(true);
						bfield8.setVisible(true);
        	case "8":   blabel8.setVisible(true);
						bfield7.setVisible(true); 
        	case "7":   blabel7.setVisible(true);
						bfield6.setVisible(true); 
        	case "6":   blabel6.setVisible(true);
						bfield5.setVisible(true); 
        	case "5":   blabel5.setVisible(true);
						bfield4.setVisible(true); 
        	case "4":   blabel4.setVisible(true);
						bfield3.setVisible(true); 
        	case "3":   blabel3.setVisible(true);
						bfield2.setVisible(true); 
        	case "2":   blabel2.setVisible(true);
						bfield1.setVisible(true); 
        	case "1":   blabel1.setVisible(true);
						break;
	
		}
	}
	
	private void hidefields(){
		bfield1.setVisible(false);
		bfield2.setVisible(false);
		bfield3.setVisible(false);
		bfield4.setVisible(false);
		bfield5.setVisible(false);
		bfield6.setVisible(false);
		bfield7.setVisible(false);
		bfield8.setVisible(false);
		bfield9.setVisible(false);
		
		bfield1.setText("");
		bfield2.setText("");
		bfield3.setText("");
		bfield4.setText("");
		bfield5.setText("");
		bfield6.setText("");
		bfield7.setText("");
		bfield8.setText("");
		bfield9.setText("");
		
		blabel2.setVisible(false);
		blabel3.setVisible(false);
		blabel4.setVisible(false);
		blabel5.setVisible(false);
		blabel6.setVisible(false);
		blabel7.setVisible(false);
		blabel8.setVisible(false);
		blabel9.setVisible(false);
		blabel10.setVisible(false);
		
		blabel2.setText("");
		blabel3.setText("");
		blabel4.setText("");
		blabel5.setText("");
		blabel6.setText("");
		blabel7.setText("");
		blabel8.setText("");
		blabel9.setText("");
		blabel10.setText("");
	}
}
