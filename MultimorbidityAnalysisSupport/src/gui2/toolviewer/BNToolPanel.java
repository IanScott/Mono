package gui2.toolviewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import domain.tools.bntool.BnResult;
import domain.tools.bntool.Network;
import domain.ToolController;
import gui2.MainFrame;
import gui2.toolviewer.bntoolviewer.CPTSPanel;
import gui2.toolviewer.bntoolviewer.ConnectorContainer;

public class BNToolPanel extends JPanel{
	
	private static final long serialVersionUID = 6694577431007850611L;
	private CPTSPanel cptspanel = null;
	private ConnectorContainer cc = null;
	private JLabel lblNewLabel_1 = null;
	private BnResult result;
	private int index = 0;
	private MainFrame mainframe;
	
	public BNToolPanel(MainFrame mainframe, BnResult res){
		super();
		this.mainframe = mainframe;
		this.result = res;
		System.out.println("BNTPanel "+res);
		init();	
	}
	
	private void init(){
		//bntool.addObserver(this);
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		this.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblNewLabel.setText("Algorithm: ");
		panel_2.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 40));
		ArrayList<Network> ln = (ArrayList<Network>) result.getResults();
		lblNewLabel_1.setText(ln.get(0).getAlgorithm());
		panel_2.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		this.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		cc = new ConnectorContainer(result.getResults().get(index));
		cc.setPreferredSize(new Dimension(800,500));
		panel_1.add(cc, BorderLayout.WEST);
		this.cptspanel = new CPTSPanel(result.getResults().get(index));
		panel_1.add(cptspanel);
		
		JPanel panel = new JPanel();
		this.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton btnNewButton_2 = new JButton("Home");
		btnNewButton_2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainframe.startFrame();
				
			}});
		panel.add(btnNewButton_2);
		
		JButton btnNewButton = new JButton("Run Algorithms");
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Edit Dataset");
		btnNewButton_1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainframe.dataviewer();
			}});
		panel.add(btnNewButton_1);
		
		//bntool.learn("gs");
		//bntool.fitNetwork();
		
		JButton btnNewButton_3 = new JButton("LAST NETWORK");
		btnNewButton_3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				lastNetwork();	
			}});
		this.add(btnNewButton_3, BorderLayout.WEST);
		
		JButton btnNewButton_4 = new JButton("NEXT NETWORK");
		btnNewButton_4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				nextNetwork();
			}
		});
		this.add(btnNewButton_4, BorderLayout.EAST);
	}
	
	private void editSize(){
		this.setSize(497, 284);
	}
	
	
	public void nextNetwork(){
		if(index != result.getResults().size()-1){
			index++;
			}
		else{index = 0;}
		
		cc.setNetwork(result.getResults().get(index));
		cptspanel.setBNNetwork(result.getResults().get(index));
		lblNewLabel_1.setText(result.getResults().get(index).getAlgorithm());
		cc.repaint();
		this.repaint();
	}
	
	public void lastNetwork(){
		if(index != 0){
			index--;
			}
		else{index = result.getResults().size()-1;}
		
		cc.setNetwork(result.getResults().get(index));
		cptspanel.setBNNetwork(result.getResults().get(index));
		lblNewLabel_1.setText(result.getResults().get(index).getAlgorithm());
		cc.repaint();
		this.repaint();
	}
}
