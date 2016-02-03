package gui3.toolviewer;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;

import domain.Column;
import domain.ToolController;
import gui3.MainFrame;

import javax.swing.JButton;

public class Countviewer extends JPanel{
	private JTable table;
	private JTable table_1;
	private MainFrame mainframe;
	private ToolController toolcontroller;
	
	public Countviewer(MainFrame mainframe, ToolController toolcontroller) {
		this.mainframe = mainframe;
		this.toolcontroller = toolcontroller;
		
		setLayout(new GridLayout(0, 5, 0, 0));
		
		///1 ComboBox 1 PANEL
		JPanel cb1panel = new JPanel();
		add(cb1panel);
		cb1panel.setLayout(new BorderLayout(0, 0));
		String[] columns = mainframe.getDataSetColumnnames();
		JComboBox<String> comboBox = new JComboBox<String>(columns);
		cb1panel.add(comboBox, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Select Group ID (Select One)");
		cb1panel.add(lblNewLabel, BorderLayout.NORTH);
		
		///2 ComboBox 2 PANEL
		JPanel cb2panel = new JPanel();
		add(cb2panel);
		cb2panel.setLayout(new BorderLayout(0, 0));
		String[] logicalColumns = toolcontroller.getDataSetColumnnames(Column.Type.toType("logical"));
		JList<String> comboBox_1 = new JList<String>(logicalColumns);
		cb2panel.add(comboBox_1, BorderLayout.CENTER);
		
		
		JLabel lblNewLabel_1 = new JLabel("Select Disease Columns(Multi Select)");
		cb2panel.add(lblNewLabel_1, BorderLayout.NORTH);
		
		//3 ControllPanel
		JPanel controllpanel = new JPanel();
		add(controllpanel);
		
		JButton btnNewButton = new JButton("Calculate");
		controllpanel.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateAction();	
			}});
		
		//4 Tabel1Panel
		JPanel t1panel = new JPanel();
		add(t1panel);
		t1panel.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		t1panel.add(table, BorderLayout.CENTER);
		
		JLabel lblNewLabel_2 = new JLabel("Total Observations per Disease Count");
		t1panel.add(lblNewLabel_2, BorderLayout.NORTH);
		
		//5 Tabel2Panel
		JPanel t2panel = new JPanel();
		add(t2panel);
		t2panel.setLayout(new BorderLayout(0, 0));
		
		table_1 = new JTable();
		t2panel.add(table_1, BorderLayout.CENTER);
		
		JLabel lblNewLabel_3 = new JLabel("Total Observations per Group and Disease Count");
		t2panel.add(lblNewLabel_3, BorderLayout.NORTH);
	}
	
	private void calculateAction(){
		
	}
}
