package poc2.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.javaGD.JGDBufferedPanel;
import org.rosuda.javaGD.JGDPanel;
import poc2.domain.BNJavaGD;
import poc2.domain.BNResult;
import poc2.domain.BNTool;
import poc2.domain.DataSet;
import poc2.domain.Network;
import poc2.domain.ToolController;
import poc2.gui.panels.CPTSPanel;
import poc2.gui.panels.ConnectorContainer;
import poc2.gui.panels.DataSetPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Font;

public class BNFrame extends JFrame implements Observer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6694577431007850611L;
	//private JGDBufferedPanel jgdpanel = null;
	private CPTSPanel cptspanel = null;
	private ConnectorContainer cc = null;
	private JLabel lblNewLabel_1 = null;
	//private JSplitPane splitpane = null;
	//private BNTool bntool = null;
	//private DataSet dataset = null;
	private GuiController guicontroller;
	//private ToolController toolcontroller;
	private BNResult result;
	//private List<Network> networks;
	private int index = 0;
	
	public BNFrame(ToolController toolcontroller, GuiController guicontroller){
		super();
		//this.bntool = (BNTool)toolcontroller.getTool("BNTool");
		//this.dataset = dataset;
		//this.toolcontroller = toolcontroller;
		this.guicontroller = guicontroller;
		this.result = (BNResult)toolcontroller.executeTool("BNTool");
		toolcontroller.addDataSetObserver(this);
		init();
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.editSize();
		this.setTitle("BNTOOL");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void init(){
		//bntool.addObserver(this);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblNewLabel.setText("Algorithm: ");
		panel_2.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblNewLabel_1.setText(result.getResults().get(0).getAlgorithm());
		panel_2.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		cc = new ConnectorContainer(result.getResults().get(index));
		cc.setPreferredSize(new Dimension(800,500));
		panel_1.add(cc, BorderLayout.WEST);
		this.cptspanel = new CPTSPanel(result.getResults().get(index));
		panel_1.add(cptspanel);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton btnNewButton_2 = new JButton("Home");
		btnNewButton_2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				guicontroller.startFrame();
				
			}});
		panel.add(btnNewButton_2);
		
		JButton btnNewButton = new JButton("Run Algorithms");
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Edit Dataset");
		btnNewButton_1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				guicontroller.dataviewerFrame();
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
		getContentPane().add(btnNewButton_3, BorderLayout.WEST);
		
		JButton btnNewButton_4 = new JButton("NEXT NETWORK");
		btnNewButton_4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				nextNetwork();
			}
		});
		getContentPane().add(btnNewButton_4, BorderLayout.EAST);
	}
	
	private void editSize(){
		this.setSize(497, 284);
	}
	
	

	@Override
	public void update(Observable o, Object arg) {
		//System.out.println("update");
		cptspanel.setBNNetwork(result.getResults().get(index));
		cc.setNetwork(result.getResults().get(index));
		cc.repaint();
		this.repaint();
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
