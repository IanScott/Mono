package gui.editPane;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import domain.tools.datatool.DataTool;

/**
 * A panel used to display Frequency values.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class FreqPanel extends JPanel {
	private static final long serialVersionUID = 8381607430868878102L;
	private DataTool datatool;
	private int size;
	private String[]columnnames;
	private String[] levels;
	private JPanel containerPanel;
	
	/**
	 * Constructor.
	 * @param datatool the datatool instance to use.
	 * @param columnname the columnname to use.
	 */
	public FreqPanel(DataTool datatool, String columnname) {
		this.datatool = datatool;
		this.levels = datatool.getDatasetFactors(columnname);
		

		init();
		if(columnname ==null){
			columnname = columnnames[0];
		}
		setup(columnname,levels);
	}
	
	private void init(){
		this.setLayout(null);
		size = 50 +(levels.length*28);
		this.setPreferredSize(new Dimension(430,size));
	}
	
	private void setup(final String columnname, String[] levels){
		this.removeAll();
		JLabel summaryLabel = new JLabel("Summary");
		summaryLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		summaryLabel.setBounds(10, 5, 141, 26);
		add(summaryLabel);
		
		JLabel columnNameLabel = new JLabel(columnname);
		columnNameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		columnNameLabel.setBounds(20, 30, 250, 26);
		add(columnNameLabel);
		
		JLabel factorsLabel = new JLabel("Factors");
		factorsLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		factorsLabel.setBounds(20, 55, 120, 26);
		add(factorsLabel);
		
		JLabel freqLabel = new JLabel("Frequency");
		freqLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		freqLabel.setBounds(216, 55, 120, 26);
		add(freqLabel);
		
		JLabel percLabel = new JLabel("Percentage");
		percLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		percLabel.setBounds(316, 55, 80, 26);
		add(percLabel);
		
		containerPanel = new JPanel();
		containerPanel.setLayout(null);
		containerPanel.setLocation(10, 75);
		
		createLevels(columnname, levels);
		
		this.add(containerPanel);
	}
	
	private void createLevels(String columnname, String[] levels){
		int y = 0;
		if(levels != null){
			for(int i= 0; i<levels.length; i++){
				JPanel levelPanel = createLevel(columnname, levels[i], i);
				levelPanel.setLocation(0, y);
				containerPanel.add(levelPanel);
				y= y+26;
			}
		}
		containerPanel.setSize(415, y);
	}
	
	private JPanel createLevel(final String columnname, final String level, final int index){
		JPanel tempPanel = new JPanel();
		
		tempPanel.setSize(450, 26);
		tempPanel.setLayout(null);
		
		final JTextField levelTextField = new JTextField(level);
		levelTextField.setBounds(10, 0, 190, 24);
		levelTextField.setEditable(false);
		tempPanel.add(levelTextField);

		final JTextField freqTextField = new JTextField("Frequency");
		freqTextField.setBounds(206, 0, 80, 24);
        freqTextField.setText(datatool.getFrequency(columnname, level));
        freqTextField.setEditable(false);
		tempPanel.add(freqTextField);

		
		final JTextField percTextField = new JTextField("Percentage");
		percTextField.setBounds(306, 0, 80, 24);
		percTextField.setText(datatool.getPercentage(columnname, level));
		percTextField.setEditable(false);
		tempPanel.add(percTextField);

		return tempPanel;
	}
	public int getPanelHeight(){
		return size;
	}
	
}
