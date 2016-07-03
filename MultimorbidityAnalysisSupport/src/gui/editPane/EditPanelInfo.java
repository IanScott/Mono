package gui.editPane;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import domain.tools.datatool.DataTool;
import gui.ErrorDialog;
import util.MultimorbidityException;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The main component to the EditPanel, this is the panel where the main displays and controls are found.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class EditPanelInfo extends JPanel{
	private static final long serialVersionUID = 320669192660886194L;
	private JTextField nameTextField;
	private JTextField colCountTextField;
	private JTextField rowCountTextField;
	private JTextField columnNameField;
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame of the application
	 * @param datatool the datatool instance to use.
	 */
	public EditPanelInfo(final JFrame mainframe, final DataTool datatool) {
		
		this.setLayout(null);
		
		int y = 1;
		
		//Title
		JLabel titleLabel = new JLabel("Edit Data Menu");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		titleLabel.setBounds(10, y, 191, 33);
		add(titleLabel);
		
		//Edit Column Name
		y=y+50;
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		nameLabel.setBounds(10, y, 134, 26);
		add(nameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(90, y, 290, 26);
		nameTextField.setEditable(false);
		nameTextField.setText(datatool.getDataSetName());
		add(nameTextField);
		y=y+30;
		
		JLabel cols = new JLabel("Columns");
		cols.setFont(new Font("Tahoma", Font.BOLD, 15));
		cols.setBounds(10, y, 134, 26);
		add(cols);
		
		colCountTextField = new JTextField();
		colCountTextField.setBounds(90, y, 290, 26);
		colCountTextField.setText(datatool.getColumnCount()+"");
		colCountTextField.setEditable(false);
		add(colCountTextField);
		
		y=y+30;
		JLabel rows = new JLabel("Rows");
		rows.setFont(new Font("Tahoma", Font.BOLD, 15));
		rows.setBounds(10, y, 134, 26);
		add(rows);
		
		rowCountTextField = new JTextField();
		rowCountTextField.setBounds(90, y, 290, 26);
		rowCountTextField.setText(datatool.getRowCount()+"");
		rowCountTextField.setEditable(false);
		add(rowCountTextField);
		
		y=y+30;
		JLabel percentageLabel = new JLabel("Percentage");
		percentageLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		percentageLabel.setBounds(10, y, 134, 20);
		add(percentageLabel);
		
		JLabel unknownLabel = new JLabel("Unknown");
		unknownLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		unknownLabel.setBounds(10, y+13, 134, 20);
		add(unknownLabel);
		
		JTextField percentageTextField = new JTextField();
		percentageTextField.setBounds(90, y, 50, 26);
		percentageTextField.setText(datatool.getPecentageNas());
		percentageTextField.setEditable(false);
		add(percentageTextField);
		
		
		//Edit Columns
		y=y+35;
		JLabel editColumnsLabel = new JLabel("Columns");
		editColumnsLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		editColumnsLabel.setBounds(10, y, 191, 33);
		add(editColumnsLabel);
		
		JLabel colnameLabel = new JLabel("Name");
		colnameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		colnameLabel.setBounds(90, y, 191, 33);
		add(colnameLabel);
		
		JLabel nasLabel = new JLabel("NA's");
		nasLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		nasLabel.setBounds(276, y, 191, 33);
		add(nasLabel);
		
		JLabel factorLabel = new JLabel("Type");
		factorLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		factorLabel.setBounds(320, y, 191, 33);
		add(factorLabel);
		
		//for loop for each column
		final String[] columns = datatool.getDataSetColumnnames();
		y = y+26;
		for(int i = 0; i<columns.length; i++){
			final int index = i;
			JLabel columnLabel = new JLabel("Nr. "+(i+1));
			columnLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			columnLabel.setBounds(10, y, 80, 26);
			add(columnLabel);
			
			columnNameField = new JTextField();
			columnNameField.setBounds(90, y, 180, 26);
			columnNameField.setText(columns[i]);
			columnNameField.setEditable(false);
			add(columnNameField);
			
			Boolean nasBool = datatool.hasNa(columns[i]);
			JTextField naLabel = new JTextField(nasBool.toString());
			naLabel.setEditable(false);
			naLabel.setHorizontalAlignment(SwingConstants.CENTER);
			naLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
			naLabel.setBounds(275, y, 30, 26);
			if(nasBool){
				naLabel.setForeground(Color.RED);
			}
			add(naLabel);
			
			JTextField typelabel = new JTextField(datatool.getColumnType(columns[i]));
			typelabel.setEditable(false);
			typelabel.setHorizontalAlignment(SwingConstants.CENTER);
			typelabel.setFont(new Font("Tahoma", Font.BOLD, 10));
			typelabel.setBounds(310, y, 60, 26);
			add(typelabel);
			
			final JButton editButton = new JButton("edit");
			editButton.setBounds(380, y, 65, 26);
			editButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					new EditDialog(mainframe,datatool,columns[index]);
					
				} });
			add(editButton);
			
			final JButton infoButton = new JButton("info");
			infoButton.setBounds(450, y, 65, 26);
			add(infoButton);
			if(datatool.getColumnType(columns[i]) != "factor" ){
				infoButton.setEnabled(false);
			}
			infoButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					new SummaryPanel(mainframe,datatool, columns[index]);
					
				}});
			
			
			final JButton deleteButton = new JButton("del");
			deleteButton.setToolTipText("delete column");
			deleteButton.setBounds(520, y, 65, 26);
			deleteButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					datatool.snapshot();
					try {
						datatool.deleteColumn(columns[index]);
					} catch (MultimorbidityException e1) {
						new ErrorDialog(mainframe, e1.getMessage());
					}
				} });
			add(deleteButton);
			y = y + 30;
		}
		this.setPreferredSize(new Dimension(580, y+30));
	}	
}
