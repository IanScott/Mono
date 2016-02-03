package gui3.toolviewer;

import javax.swing.JPanel;

import domain.ToolController;
import gui3.MainFrame;

import javax.swing.JLabel;

public class Toolpanel extends JPanel{
	private MainFrame mainframe;
	private ToolController toolcontroller;
	
	public Toolpanel(MainFrame mainframe, ToolController toolcontroller) {
		this.mainframe = mainframe;
		this.toolcontroller = toolcontroller;
		
		
		JLabel lblTest = new JLabel("TEST");
		add(lblTest);
	}

}
