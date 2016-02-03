package poc2.gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class DummyFrame{
	
	private JDialog dialog;
	
	public DummyFrame(JFrame parent) {
		
		dialog = new JDialog (parent, "Information", true);
        dialog.setSize (600, 410);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
		
        dialog.setLocationRelativeTo (parent);
		dialog.getContentPane().setLayout(null);
		
		JLabel lblOu = new JLabel("OU");
		lblOu.setBackground(Color.WHITE);
		lblOu.setOpaque(true);
		lblOu.setBounds(10, 11, 140, 113);
		dialog.getContentPane().add(lblOu);
		
		JLabel lblHttpwwwounl = new JLabel("http://www.ou.nl");
		lblHttpwwwounl.setHorizontalAlignment(SwingConstants.CENTER);
		lblHttpwwwounl.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblHttpwwwounl.setBackground(Color.WHITE);
		lblHttpwwwounl.setOpaque(true);
		lblHttpwwwounl.setBounds(0, 0, 594, 381);
		dialog.getContentPane().add(lblHttpwwwounl);
		
		
		dialog.setVisible (true);
	}
}
