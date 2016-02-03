package gui2;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import javaGD.BNJavaGD;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class JavaGDDialog{
	private JDialog dialog;
	private MainFrame mainframe;
	
	public JavaGDDialog(MainFrame mf){ 
		this.mainframe = mf;
		dialog = new JDialog (mainframe, "Plot", true);
	    dialog.setResizable (true);
	    dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
	    
		
		JPanel content = new JPanel();
		

		this.dialog.setContentPane(content);
		content.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		content.add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = "" ;
				mainframe.savePlot(name);
				
			} });
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
				
			} });
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = BNJavaGD.getPanel();
		content.add(panel_1, BorderLayout.CENTER);
		this.dialog.setSize (500, 600);
		dialog.setLocationRelativeTo(mainframe);
		this.dialog.setVisible(true);
	}
}
