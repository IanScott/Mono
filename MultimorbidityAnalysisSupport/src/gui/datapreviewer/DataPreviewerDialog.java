package gui.datapreviewer;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import data.Previewer;
import gui.ErrorDialog;
import gui.TableRowUtilities;
import util.MultimorbidityException;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A Dialog to Preview a CSV file.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class DataPreviewerDialog {	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField otherTextField;
	private JTable table;
	private File file;
    private JDialog dialog;
    private boolean answer;
	private String separator = ",";
	private JCheckBox seperatorCheckBox;
	private JRadioButton commaRadioButton;
	private JRadioButton semicolonRadioButton;
	private JRadioButton tabRadioButton;
	private JRadioButton otherRadioButton;
	private JPanel contentPanel;
	private JPanel controllPanel;
	private JFrame parent;
	
	/**
	 * Constructor.
	 * @param parent the Frame parent of the dialog
	 * @param file the file to preview
	 */
	public DataPreviewerDialog(JFrame parent, File file) {
		this.file = file;
		this.parent = parent;
		dialog = new JDialog (parent, "Information", true);
        dialog.setSize (600, 410);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (parent);
		dialog.getContentPane().setLayout(new BorderLayout(0, 0));
		
		setupPanels();
		setupButtons();
		setupLabels();
		
		otherTextField = new JTextField();
		otherTextField.setBounds(436, 135, 95, 20);
		controllPanel.add(otherTextField);
		otherTextField.setColumns(10);
		
		setupTable();
		
		dialog.setVisible (true);
	}
	
	private void setupTable(){
		DefaultTableModel model = null;
		try {
			model = Previewer.preview(file, ",", seperatorCheckBox.isSelected());
		} catch (MultimorbidityException e) {
			new ErrorDialog(parent,e.getMessage());
		}
		if(model != null){
			table = new JTable();
			table.setModel(model);
			JScrollPane scrollPane = new JScrollPane(table);
			TableRowUtilities.addNumberColumn(table, 1, false);
			dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);
		}
	}
	
	private void setupLabels(){
		JLabel seperatorLabel = new JLabel("Separator");
		seperatorLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		seperatorLabel.setBounds(291, 50, 80, 30);
		controllPanel.add(seperatorLabel);
		
		
		JLabel headerLabel = new JLabel("Header");
		headerLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		headerLabel.setBounds(114, 50, 57, 30);
		controllPanel.add(headerLabel);
		
		JLabel lblPreviewParameters = new JLabel("Preview parameters");
		lblPreviewParameters.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblPreviewParameters.setBounds(190, 11, 196, 28);
		controllPanel.add(lblPreviewParameters);
	}
	
	private void setupPanels(){
		contentPanel = new JPanel();
		dialog.getContentPane().add(contentPanel, BorderLayout.SOUTH);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		controllPanel = new JPanel();
		controllPanel.setPreferredSize(new Dimension(600, 160));
		dialog.getContentPane().add(controllPanel, BorderLayout.NORTH);
		controllPanel.setLayout(null);
	}
	
	private void setupButtons(){	
		seperatorCheckBox = new JCheckBox("");
		seperatorCheckBox.setSelected(true);
		seperatorCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
		seperatorCheckBox.setFont(new Font("SansSerif", Font.BOLD, 20));
		seperatorCheckBox.setBounds(177, 50, 30, 30);
		controllPanel.add(seperatorCheckBox);
		seperatorCheckBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				preview();	
			}
		});
		
		commaRadioButton = new JRadioButton("Comma");
		commaRadioButton.setSelected(true);
		buttonGroup.add(commaRadioButton);
		commaRadioButton.setBounds(372, 56, 109, 23);
		controllPanel.add(commaRadioButton);
		commaRadioButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				preview();	
			}
		});
		
		semicolonRadioButton = new JRadioButton("Semicolon");
		buttonGroup.add(semicolonRadioButton);
		semicolonRadioButton.setBounds(372, 82, 109, 23);
		controllPanel.add(semicolonRadioButton);
		semicolonRadioButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				preview();	
			}
		});
		
		tabRadioButton = new JRadioButton("Tab");
		buttonGroup.add(tabRadioButton);
		tabRadioButton.setBounds(372, 108, 109, 23);
		controllPanel.add(tabRadioButton);
		tabRadioButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				preview();	
			}
		});
		
		otherRadioButton = new JRadioButton("Other");
		buttonGroup.add(otherRadioButton);
		otherRadioButton.setBounds(372, 134, 60, 23);
		controllPanel.add(otherRadioButton);
		otherRadioButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				preview();	
			}
		});
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				answer = true;
				dialog.dispose();
			}
		});
		contentPanel.add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				answer = false;
				dialog.dispose();
			}
			
		});
		contentPanel.add(cancelButton);
		
	}
	
	
	
	private void preview(){
		//get radiobutton value
		if(commaRadioButton.isSelected()){separator = ",";}
		if(semicolonRadioButton.isSelected()){separator = ";";}
		if(tabRadioButton.isSelected()){separator = "\t";}
		if(otherRadioButton.isSelected()){separator = otherTextField.getText();}
		
		DefaultTableModel model = null;
		try {
			model = Previewer.preview(file, separator, seperatorCheckBox.isSelected());
		} catch (MultimorbidityException e) {
			new ErrorDialog(parent,e.getMessage());
		}
		if(model != null){
			table.setModel(model);
			table.repaint();
		}	
	}
	
	/**
	 * Method returns the answers the user choose.
	 * @return int value
	 */
	public boolean getAnswer(){
		return answer;
	}
	/**
	 * Method checks if user selected head option.
	 * @return true if head was selected.
	 */
	public boolean hasHead(){
		return seperatorCheckBox.isSelected();
	}
	/**
	 * Method return the separator the user selected.
	 * @return the string value of the separator.
	 */
	public String getSeparator(){
		return separator;
	}
	
	
}
