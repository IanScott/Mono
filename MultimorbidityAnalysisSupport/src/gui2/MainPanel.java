package gui2;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

import gui2.start.StartPanel;

public class MainPanel extends JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainPanel(MainFrame mainframe){
		
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);
		this.add(new StartPanel(mainframe));
	}
	
	
	
	
}
