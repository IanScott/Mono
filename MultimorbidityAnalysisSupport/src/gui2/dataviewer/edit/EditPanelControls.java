package gui2.dataviewer.edit;

import javax.swing.JPanel;

import gui2.MainFrame;

import java.awt.Dimension;

import javax.swing.JButton;

public class EditPanelControls extends JPanel{
	public EditPanelControls(MainFrame mainframe) {
		setLayout(null);
		this.setSize(new Dimension(600, 53));
		
		JButton btnNewButton = new JButton("Drop Unknown values");
		btnNewButton.setBounds(421, 11, 169, 29);
		add(btnNewButton);
		
		JButton btnReplaceUnknownValues = new JButton("Replace unknown values");
		btnReplaceUnknownValues.setBounds(242, 11, 169, 29);
		add(btnReplaceUnknownValues);
		
		JButton btnCommitNewNames = new JButton("Commit new names");
		btnCommitNewNames.setBounds(10, 11, 169, 29);
		add(btnCommitNewNames);
	}

}
