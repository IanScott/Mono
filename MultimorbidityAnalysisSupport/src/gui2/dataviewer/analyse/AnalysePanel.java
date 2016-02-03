package gui2.dataviewer.analyse;

import javax.swing.JPanel;
import domain.tools.par5counttool.CountArgument;
import domain.tools.par5counttool.CountTool;
import gui2.MainFrame;
import gui2.toolviewer.counttoolviewer.CountToolStartDialog;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AnalysePanel extends JPanel{
	public AnalysePanel(final MainFrame mainframe) {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Analyse Data");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 11, 192, 25);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("View/ Edit prior Results");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, 68, 192, 25);
		add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Bayesian Network Result");
		btnNewButton.setBounds(10, 95, 561, 42);
		btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("show Results");
				mainframe.bntoolResults();
				
			}});
		add(btnNewButton);
		
		JButton btnDummyResult = new JButton("Dummy Result");
		btnDummyResult.setBounds(10, 148, 561, 42);
		add(btnDummyResult);
		
		JButton btnDummyResult_1 = new JButton("Dummy2 Result");
		btnDummyResult_1.setBounds(10, 201, 561, 42);
		add(btnDummyResult_1);
		
		JButton btnDummyResult_2 = new JButton("Dummy3 Result");
		btnDummyResult_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDummyResult_2.setBounds(10, 254, 561, 42);
		add(btnDummyResult_2);
		
		JButton btnDummyresult = new JButton("Dummy 4Result");
		btnDummyresult.setBounds(10, 307, 561, 42);
		add(btnDummyresult);
		
		JLabel lblCreateANew = new JLabel("Create a new Results");
		lblCreateANew.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCreateANew.setBounds(10, 392, 192, 25);
		add(lblCreateANew);
		
		JButton btnNewBayesianNetwork = new JButton("New Bayesian Network");
		btnNewBayesianNetwork.setBounds(10, 428, 561, 42);
		btnNewBayesianNetwork.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainframe.bntoolExcecute();
				
			}});
		add(btnNewBayesianNetwork);
		
		JButton btnNewDummy = new JButton("New Count Tool");
		btnNewDummy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewDummy.setBounds(10, 481, 561, 42);
		btnNewDummy.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new CountToolStartDialog(mainframe);
			}});
		add(btnNewDummy);
		
		JButton btnNewDummy_1 = new JButton("New Dummy 2");
		btnNewDummy_1.setBounds(10, 534, 561, 42);
		add(btnNewDummy_1);
		
		JButton btnNewDummy_2 = new JButton("New Dummy 3");
		btnNewDummy_2.setBounds(10, 587, 561, 42);
		add(btnNewDummy_2);
		
		JButton btnNewDummy_3 = new JButton("New Dummy 4");
		btnNewDummy_3.setBounds(10, 640, 561, 42);
		add(btnNewDummy_3);
	}
}
