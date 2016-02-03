package poc1.gui;

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
import poc1.domain.BNJavaGD;
import poc1.domain.BNTool;
import poc1.gui.panels.CPTSPanel;
import poc1.gui.panels.ConnectorContainer;
import poc1.gui.panels.DataSetPanel;

public class BNFrame extends JFrame implements Observer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6694577431007850611L;
	//private JGDBufferedPanel jgdpanel = null;
	private CPTSPanel cptspanel = null;
	private JSplitPane splitpane = null;
	private BNTool bntool = null;
	
	
	public BNFrame(BNTool bntool){
		super();
		this.bntool = bntool;
		init();
		
		this.pack();
		this.editSize();
		this.setVisible(true);
		this.setTitle("BNTOOL");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void init(){
		bntool.addObserver(this);
		
		this.cptspanel = new CPTSPanel(null);
		this.splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
		
		splitpane.setLeftComponent(BNJavaGD.getPanel());
		splitpane.setRightComponent(cptspanel);
		
		this.getContentPane().add(splitpane);
		this.setJMenuBar(getMenubar());
	}
	
	private void editSize(){
		this.setSize(1500, 1000);
	}
	
	private JMenuBar getMenubar(){
		// MenuBar
		JMenuBar menubar = new JMenuBar();
		
		// Menu
		JMenu jmData = new JMenu("Data");
		JMenu jmView = new JMenu("View");
		JMenu jmMain = new JMenu("Main");
		JMenu jmFile = new JMenu("File");
		JMenu jmHelp = new JMenu("Help");
		
		// Add Menus to Menubar
		menubar.add(jmData);
		menubar.add(jmView);
		menubar.add(jmMain);
		menubar.add(jmFile);
		menubar.add(jmHelp);
		
		
		// MenuItems -> Menu Data
		JMenuItem jmi = new JMenuItem("Import Data");
		JMenuItem jmi2 = new JMenuItem("Clean Data");
		JMenuItem jmi3 = new JMenuItem("Run Grow-Shrink Algorithm");
		JMenuItem jmi4 = new JMenuItem("Run IAMB Algorithm");
		JMenuItem jmi5 = new JMenuItem("Run HC Algorithm");
		JMenuItem jmi6 = new JMenuItem("Run TABU Algorithm");
		JMenuItem jmi7 = new JMenuItem("Run MMHC Algorithm");
		
		// MenuItems -> Menu View
		JMenuItem jmi8 = new JMenuItem("View DataSet");
		JMenuItem jmi9 = new JMenuItem("View Editor");
		
		// Add MenuItems to Menu Data
		jmData.add(jmi);
		jmData.add(jmi2);
		jmData.add(jmi3);
		jmData.add(jmi4);
		jmData.add(jmi5);
		jmData.add(jmi6);
		jmData.add(jmi7);
		
		// Add MenuItems to Menu View
		jmView.add(jmi8);
		jmView.add(jmi9);
		
		//MenuItems Actionlisteners
		jmi.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				actionImport();
				
			}
		});
		
		jmi3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bntool.learn("gs");
				bntool.fitNetwork();
			}
		});
		
		jmi2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bntool.cleanData();
			}
		});
		jmi4.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bntool.learn("iamb");
				bntool.fitNetwork();
			}
		});
		jmi5.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				bntool.learn("hc");
				bntool.fitNetwork();
			}
		});
		
		jmi6.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				bntool.learn("tabu");
				bntool.fitNetwork();
				
			}
		});
		
		jmi7.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				bntool.learn("mmhc");
				bntool.fitNetwork();
			}
		});
		
		jmi8.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new DataSetPanel(bntool.getDataSet());
			}
		});
		
		jmi9.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("test");
				JFrame jf = new JFrame();
				jf.setSize(600, 600);
				jf.setContentPane(new ConnectorContainer(bntool.getBNNetwork()));
				jf.setVisible(true);
				
				System.out.println("test");
			}
		});
		
		return menubar;
	}
	
	
	private void actionImport(){
		JFileChooser fc = new JFileChooser();
		File file = null;
		fc.setDialogTitle("Choose File");
		
		int returnVal = fc.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			new DataSetPanel(bntool.getDataSet());
			bntool.loadDataSet(file);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("update");
		cptspanel.setBNNetwork(bntool.getBNNetwork());
		splitpane.repaint();
		this.repaint();
	}
	
	
	
}
