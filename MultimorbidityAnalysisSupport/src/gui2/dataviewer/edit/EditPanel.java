package gui2.dataviewer.edit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui2.MainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class EditPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EditPanelInfo info;
	private EditPanelControls controls;
	private JScrollPane scrollpane;
	private JPanel test;
	
	public EditPanel(MainFrame mainframe){
		//this.setSize(600,800);
		this.setLayout(new BorderLayout(0, 0));
		
		this.info = new EditPanelInfo(mainframe);
		this.test = new JPanel();
		test.setPreferredSize(new Dimension(550,3000));
		test.setBackground(Color.BLUE);
		this.scrollpane = new JScrollPane(info);
		
		//this.scrollpane.setViewportView(test);
		this.add(scrollpane, BorderLayout.CENTER);
		
		this.controls = new EditPanelControls(mainframe);
		this.controls.setPreferredSize(new Dimension(600,55));
		this.add(controls, BorderLayout.SOUTH);
	}
	
	
}
