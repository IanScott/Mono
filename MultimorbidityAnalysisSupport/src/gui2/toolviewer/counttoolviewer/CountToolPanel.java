package gui2.toolviewer.counttoolviewer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domain.tools.par5counttool.CountResult;
import gui2.JavaGDDialog;
import gui2.MainFrame;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.awt.BorderLayout;
import javax.swing.JButton;

public class CountToolPanel extends JPanel{
	private JScrollPane scrollpane;
	
	public CountToolPanel(final MainFrame mainframe, CountResult cres) {
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		JButton btnNewButton_1 = new JButton("Calculate");
		btnNewButton_1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				CountToolStartDialog cd = new CountToolStartDialog(mainframe);
				
				String[] arg = cd.getLogicalColumns();
				String arg1 = cd.getGroupby();
				Container panel =((JButton)e.getSource()).getParent().getParent();
				panel.remove(scrollpane);
				
				CountResult cres = mainframe.countToolExecute(arg, arg1);
				JPanel centerpanel = getCenterPanel(cres);
				scrollpane = new JScrollPane(centerpanel);
				panel.add(scrollpane);
				panel.revalidate();
			} });
		panel.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Plot1");
		btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainframe.countPlot1();
				new JavaGDDialog(mainframe);
				
			}});
		panel.add(btnNewButton);
		
		JButton btnNewButton2 = new JButton("Plot2");
		btnNewButton2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainframe.countPlot2();
				new JavaGDDialog(mainframe);
				
			}});
		panel.add(btnNewButton2);
		
		
		
		JPanel centerpanel;
		if(cres != null){
		centerpanel = getCenterPanel(cres);}
		else{centerpanel = new JPanel();
		centerpanel.setBackground(Color.WHITE);}
		scrollpane = new JScrollPane(centerpanel);
		add(scrollpane,BorderLayout.CENTER);
		
		}
		
		
		
		
	

	private JPanel getCenterPanel(CountResult cres){
		JPanel centerpanel = new JPanel();
		
		centerpanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Count Analysis");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 11, 174, 34);
		centerpanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Total Observations per Disease Count");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(59, 56, 310, 21);
		centerpanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Disease Count");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(59, 88, 150, 25);
		lblNewLabel_2.setBorder(BorderFactory.createLineBorder(Color.black));
		centerpanel.add(lblNewLabel_2);
		
		JLabel lblCount = new JLabel("Total Observations");
		lblCount.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCount.setBorder(BorderFactory.createLineBorder(Color.black));
		lblCount.setBounds(219, 88, 150, 25);
		centerpanel.add(lblCount);
		
		JLabel lblPercentage = new JLabel("Percentage");
		lblPercentage.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPercentage.setBorder(BorderFactory.createLineBorder(Color.black));
		lblPercentage.setBounds(379, 88, 150, 25);
		centerpanel.add(lblPercentage);
		
		int[] count = cres.getCount();
		int total=0;
		for(int i: count){
			total = total+i;
		}
		System.out.println("total " +total);
		int y = 124;
		for(int i =0; i<count.length; i++, y=y+27){
			JLabel label_2 = new JLabel(i+"");
			label_2.setFont(new Font("Tahoma", Font.BOLD, 15));
			label_2.setBorder(BorderFactory.createLineBorder(Color.black));
			label_2.setBounds(59, y, 150, 25);
			centerpanel.add(label_2);
			
			JLabel label_3 = new JLabel(count[i]+"");
			label_3.setFont(new Font("Tahoma", Font.BOLD, 15));
			label_3.setBorder(BorderFactory.createLineBorder(Color.black));
			label_3.setBounds(219, y, 150, 25);
			centerpanel.add(label_3);
			
			double perc = (double)count[i]/total;
			System.out.println(count[i]);
			System.out.println(total);
			int perc2 = (int) (perc*100);
			System.out.println(perc2);
			JLabel label_4 = new JLabel(perc2+"");
			label_4.setFont(new Font("Tahoma", Font.BOLD, 15));
			label_4.setBorder(BorderFactory.createLineBorder(Color.black));
			label_4.setBounds(379, y, 150, 25);
			centerpanel.add(label_4);
		}
		
		y = y+50;
		
		JLabel lblTotalObservationsPer = new JLabel("Total Observations per Group and Disease Count");
		lblTotalObservationsPer.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTotalObservationsPer.setBounds(59, y, 401, 21);
		centerpanel.add(lblTotalObservationsPer);
		
		y=y+36;
		
		JLabel lblGroupVariables = new JLabel("Variables");
		lblGroupVariables.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGroupVariables.setBorder(BorderFactory.createLineBorder(Color.black));
		lblGroupVariables.setBounds(59, y, 150, 25);
		centerpanel.add(lblGroupVariables);
		
		JLabel label_15 = new JLabel("Disease Count");
		label_15.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_15.setBorder(BorderFactory.createLineBorder(Color.black));
		label_15.setBounds(219, y, 150, 25);
		centerpanel.add(label_15);
		
		JLabel lblTotalObservations = new JLabel("Total Observations");
		lblTotalObservations.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTotalObservations.setBorder(BorderFactory.createLineBorder(Color.black));
		lblTotalObservations.setBounds(379, y, 150, 25);
		centerpanel.add(lblTotalObservations);
		
		y=y+27;
		
		HashMap<String,int[]> groupcount = cres.getGroupCount();
		Set<String> keys = groupcount.keySet();
		Iterator<String> it = keys.iterator();
		
		while(it.hasNext()){
			String key = it.next();
			 int[] values = groupcount.get(key);
			 
			 for(int i =0; i<values.length;i++,y=y+27){
				 JLabel lblOld = new JLabel(key);
					lblOld.setFont(new Font("Tahoma", Font.BOLD, 15));
					lblOld.setBorder(BorderFactory.createLineBorder(Color.black));
					lblOld.setBounds(59, y, 150, 25);
					centerpanel.add(lblOld);
					
					JLabel label_1 = new JLabel(i+"");
					label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
					label_1.setBorder(BorderFactory.createLineBorder(Color.black));
					label_1.setBounds(219, y, 150, 25);
					centerpanel.add(label_1);
					
					JLabel label_5 = new JLabel(values[i]+"");
					label_5.setFont(new Font("Tahoma", Font.BOLD, 15));
					label_5.setBorder(BorderFactory.createLineBorder(Color.black));
					label_5.setBounds(379, y, 150, 25);
					centerpanel.add(label_5);
			 }
		}
		centerpanel.setPreferredSize(new Dimension(550, y));
		return centerpanel;
	}
	
	
	
	private static final long serialVersionUID = 2819714456508952340L;
}
