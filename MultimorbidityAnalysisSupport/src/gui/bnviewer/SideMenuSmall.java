package gui.bnviewer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import domain.Column;
import domain.tools.bntool.BnTool;
import domain.tools.datatool.DataTool;
import gui.ErrorDialog;
import gui.MListSelectionModel;
import util.MultimorbidityException;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import java.awt.Component;

/**
 * This is a test smaller control panel for the Bnviewer Panel.
 * @author ABI team 37
 * @version 1.0
 */
public class SideMenuSmall extends JTabbedPane implements Observer{
	private static final long serialVersionUID = -2433859252360380870L;
	private JFrame mainframe;
	private DataTool datatool;
	private BnTool bntool;
	private JTextField scoreTextField;
	private JComboBox<Item> algorcb;
	private JComboBox<String> scorecb;
	private JComboBox<String> nodesa;
	private JComboBox<String> nodesb;	
	private JList<String> list;	
	private String[] selectedNodes;
	
	private JPanel setup;
	private JPanel edit;
	
	private JPanel step1panel;
	private JPanel step2panel;
	private JPanel step3panel;
	private JPanel step4panel;
	private JPanel step5panel;
	
	/**
	 * Constructor.
	 * @param mainframe the Main JFrame 
	 * @param datatool the Datatool used
	 * @param bntool the BnTool used
	 */
	public SideMenuSmall(JFrame mainframe,DataTool datatool, BnTool bntool) {
		this.datatool = datatool;
		this.bntool = bntool;
		this.mainframe = mainframe;
		this.datatool.addObserver(this);
		this.bntool.addObserver(this);
		
		setup();
		setupComboBoxes();
		setupList();
		setupTextField();
	}
	
	private void setupList(){
		String[] fcolumns = null;
		if(datatool !=null){
			fcolumns = datatool.getDataSetColumnnames(Column.Type.FACTOR);
		}
		
		ArrayList<String> temp = new ArrayList<String>();
		for(int i = 0; i<fcolumns.length;i++){
			if(!datatool.hasNa(fcolumns[i])){
				temp.add(fcolumns[i]);
			}
		}
		
		String[] nfcolumns = temp.toArray(new String[temp.size()]);
		
		if(list == null){
			list = new JList<String>();
			list.setSelectionModel(new MListSelectionModel());
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(list);
			scrollPane.setPreferredSize(new Dimension(150,300));
			scrollPane.setBounds(10, 115, 260, 80);
			step1panel.add(scrollPane);
		}
		if(fcolumns != null){
			DefaultListModel<String> dlm = new DefaultListModel<>();
			for(String s: nfcolumns){
				dlm.addElement(s);
			}
			list.setModel(dlm);
		}
	}
	
	private void setupTextField(){
		
		if(scoreTextField == null){
			scoreTextField = new JTextField();
			scoreTextField.setBounds(10, 90, 260, 20);
			scoreTextField.setEditable(false);
			step3panel.add(scoreTextField);
		}	
	}
	
	private void setupComboBoxes(){
		String[] fcolumns = this.selectedNodes;
		
		if(algorcb == null){
			
			
			algorcb = new JComboBox<Item>();
			algorcb.addItem(new Item("gs", "Grow-Shrink (gs)"));
			algorcb.addItem(new Item("iamb", "Incremental Association (iamb)"));
			algorcb.addItem(new Item("fast.iamb", "Fast Incremental Association (fast.iamb)"));
			algorcb.addItem(new Item("inter.iamb", "Interleaved Incremental Association (inter.iamb)"));
			algorcb.addItem(new Item("hc", "Hill-Climbing (hc)"));
			algorcb.addItem(new Item("tabu", "Tabu Search (tabu)"));
			algorcb.addItem(new Item("mmhc", "Max-Min Hill-Climbing (mmhc)"));
			algorcb.addItem(new Item("rsmax2", "Restricted Maximization (rsmax2)"));
			algorcb.addItem(new Item("mmpc", "Max-Min Parents and Children (mmpc)"));
			algorcb.addItem(new Item("si.hiton.pc", "Hiton Parents and Children (si.hiton.pc)"));
			algorcb.addItem(new Item("chow.liu", "Chow-Liu (chow.liu)"));
			algorcb.addItem(new Item("aracne", "ARACNE (aracne)"));
			
			
			algorcb.setBounds(10, 40, 260, 26);
			step2panel.add(algorcb);
		}
		
		if(scorecb == null){
			scorecb = new JComboBox<String>(BnTool.SCORE);
			scorecb.setBounds(10, 40, 260, 23);
			step3panel.add(scorecb);
		}
		
		if(nodesa == null){
			nodesa = new JComboBox<String>();
			nodesa.setBounds(10, 60, 260, 23);
			step4panel.add(nodesa);
		}
		
		if(nodesb == null){
			nodesb = new JComboBox<String>();
			nodesb.setBounds(10, 100, 260, 23);
			step4panel.add(nodesb);
		}
		
		if(fcolumns != null){
			nodesa.removeAllItems();
			nodesb.removeAllItems();
			for(String s: fcolumns){
				nodesa.addItem(s);
				nodesb.addItem(s);
			}
		}

	}
	
	private void setupStep1panel(){
		step1panel = new JPanel();
		step1panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step1panel.setBounds(10, 45, 280, 205);
		setup.add(step1panel);
		step1panel.setLayout(null);
		
		JLabel step1Label = new JLabel("Step 1.");
		step1Label.setFont(new Font("Tahoma", Font.BOLD, 11));
		step1Label.setBounds(10, 10, 260, 14);
		step1Label.setHorizontalAlignment(SwingConstants.LEFT);
		step1Label.setMaximumSize(new Dimension(Integer.MAX_VALUE, step1Label.getMinimumSize().height));
		step1panel.add(step1Label);
		
		JLabel lblOnlyFactors = new JLabel("Only factors &");
		lblOnlyFactors.setMaximumSize(new Dimension(2147483647, 14));
		lblOnlyFactors.setHorizontalAlignment(SwingConstants.LEFT);
		lblOnlyFactors.setBounds(10, 25, 260, 14);
		step1panel.add(lblOnlyFactors);
		
		JLabel knownLabel = new JLabel("columns with known values");
		knownLabel.setBounds(10, 40, 260, 14);
		knownLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, knownLabel.getMinimumSize().height));
		step1panel.add(knownLabel);
		
		JLabel selectLabel = new JLabel("Select Columns (Min. 2)");
		selectLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		selectLabel.setBounds(10, 100, 260, 14);
		selectLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, selectLabel.getMinimumSize().height));
		step1panel.add(selectLabel);
	}
	
	private void setupStep2panel(){
		step2panel = new JPanel();
		step2panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step2panel.setBounds(10, 261, 280, 102);
		setup.add(step2panel);
		step2panel.setLayout(null);
		
		JLabel step2Label = new JLabel("Step 2.");
		step2Label.setFont(new Font("Tahoma", Font.BOLD, 11));
		step2Label.setBounds(10, 10, 165, 14);
		step2panel.add(step2Label);
		
		JLabel algorithmLabel = new JLabel("Choose Algorithm");
		algorithmLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		algorithmLabel.setBounds(10, 25, 260, 14);
		step2panel.add(algorithmLabel);;
		
		JButton runButton = new JButton("Run");
		runButton.setBounds(10, 70, 260, 23);
		step2panel.add(runButton);
		runButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, runButton.getMinimumSize().height));
		runButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		runButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				runAction();
				
			}
		});
	}
	
	private void setupStep3panel(){
		step3panel = new JPanel();
		step3panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step3panel.setBounds(10, 374, 280, 122);
		setup.add(step3panel);
		step3panel.setLayout(null);
		
		JLabel step3Label = new JLabel("Step 3.");
		step3Label.setFont(new Font("Tahoma", Font.BOLD, 11));
		step3Label.setBounds(10, 10, 155, 14);
		step3Label.setHorizontalAlignment(SwingConstants.LEFT);
		step3Label.setVerticalAlignment(SwingConstants.TOP);
		step3Label.setMaximumSize(new Dimension(Integer.MAX_VALUE, step3Label.getMinimumSize().height));
		step3panel.add(step3Label);
		
		JButton calculateButton = new JButton("Calculate");
		calculateButton.setBounds(10, 65, 260, 23);
		calculateButton.setVerticalAlignment(SwingConstants.TOP);
		calculateButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, calculateButton.getMinimumSize().height));
		step3panel.add(calculateButton);
		calculateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateAction();	
			} 
		});
		
		JLabel lblChooseScoreType = new JLabel("Choose Score type");
		lblChooseScoreType.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblChooseScoreType.setBounds(10, 25, 155, 14);
		lblChooseScoreType.setHorizontalAlignment(SwingConstants.LEFT);
		lblChooseScoreType.setVerticalAlignment(SwingConstants.TOP);
		step3panel.add(lblChooseScoreType);
	}
	
	private void setupStep4panel(){
		step4panel = new JPanel();
		step4panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step4panel.setBounds(10, 45, 280, 208);
		edit.add(step4panel);
		step4panel.setLayout(null);
		
		JLabel lblStepEdit = new JLabel("Step 4.");
		lblStepEdit.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStepEdit.setBounds(10, 10, 155, 14);
		step4panel.add(lblStepEdit);
		
		JLabel lblNodeA = new JLabel("Node A");
		lblNodeA.setBounds(10, 44, 155, 14);
		step4panel.add(lblNodeA);
		
		JLabel lblNodeB = new JLabel("Node B");
		lblNodeB.setBounds(10, 85, 155, 14);
		step4panel.add(lblNodeB);
		
		JButton setButton = new JButton("Set");
		setButton.setBounds(10, 129, 260, 23);
		step4panel.add(setButton);	
		
		JButton dropButton = new JButton("Drop");
		dropButton.setBounds(10, 152, 260, 23);
		step4panel.add(dropButton);
		
		JButton reverseButton = new JButton("Reverse");
		reverseButton.setBounds(10, 175, 260, 23);
		step4panel.add(reverseButton);
		
		JLabel lblEditArcs = new JLabel("Edit Arcs");
		lblEditArcs.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEditArcs.setBounds(10, 25, 155, 14);
		step4panel.add(lblEditArcs);
		
		reverseButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				reverseAction();
			}
		});
		dropButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dropAction();
			}
		});
		setButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setAction();		
			} 
		});
	}
	
	private void setupStep5panel(){
		step5panel = new JPanel();
		step5panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step5panel.setBounds(10, 260, 280, 115);
		edit.add(step5panel);
		step5panel.setLayout(null);
		
		JLabel lblOtherOptions = new JLabel("Other Options");
		lblOtherOptions.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOtherOptions.setBounds(10, 11, 155, 14);
		step5panel.add(lblOtherOptions);
		
		JButton btnSaveNetwork = new JButton("Save Network");
		btnSaveNetwork.setBounds(10, 36, 260, 23);
		step5panel.add(btnSaveNetwork);
		
		JButton btnLoadNetwork = new JButton("Load Network");
		btnLoadNetwork.setBounds(10, 59, 260, 23);
		step5panel.add(btnLoadNetwork);
		
		JButton btnExportNetwork = new JButton("Export Network");
		btnExportNetwork.setBounds(10, 82, 260, 23);
		step5panel.add(btnExportNetwork);
		btnExportNetwork.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				exportNetworkAction();
			}
		});
		btnLoadNetwork.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadNetworkAction();
			}
		});
		btnSaveNetwork.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				saveNetworkAction();
			} 
		});
		
	}
	
	private void setup(){
		//setLayout(null);
		JLabel mainlabel = new JLabel("Bayesian Network Analysis");
		mainlabel.setBounds(10, 8, 280, 26);
		mainlabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel mainlabel2 = new JLabel("Bayesian Network Analysis");
		mainlabel2.setBounds(10, 8, 280, 26);
		mainlabel2.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		this.setTabPlacement(BOTTOM);
		
		setup = new JPanel();
		edit = new JPanel();
		
		setup.setLayout(null);
		edit.setLayout(null);
		
		setup.add(mainlabel);
		edit.add(mainlabel2);
		
		this.addTab("Setup", setup);
		this.addTab("Edit", edit);
		
		setupStep1panel();
		setupStep2panel();
		setupStep3panel();
		setupStep4panel();
		setupStep5panel();
	}
	
	private void loadNetworkAction(){
		JFileChooser fc = new JFileChooser();
		File file = null;
		fc.setDialogTitle("Choose Network File");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new FileNameExtensionFilter("Bayesian Network", "bn"));
        fc.setAcceptAllFileFilterUsed(false);
		
		int returnVal = fc.showOpenDialog(mainframe);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			try {
				bntool.loadResult(file);
			} catch (MultimorbidityException e1) {
				new ErrorDialog(mainframe, e1.getMessage());
			}
			try {
				datatool.loadResult(bntool.getBnResult());
			} catch (MultimorbidityException e) {
				new ErrorDialog(mainframe, e.getMessage());
			}
		}
	}
	
	private void saveNetworkAction(){
		JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Bayesian Network","bn");
		fc.setFileFilter(filter);
		
		int returnVal = fc.showSaveDialog(mainframe);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = new File(fc.getSelectedFile()+".bn");
            try {
				bntool.saveResult(file);
			} catch (MultimorbidityException e) {
				new ErrorDialog(mainframe, e.getMessage());
			}
		}
	}
	
	private void exportNetworkAction(){
		JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Fitted Network","fnet");
		fc.setFileFilter(filter);
		
		int returnVal = fc.showSaveDialog(mainframe);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            bntool.exportNetwork(file.getAbsolutePath());
		}
	}
	
	private void runAction(){
		String algor = ((Item)algorcb.getSelectedItem()).getKey();
		List<String> temp = list.getSelectedValuesList();
		if(temp == null|| temp.size()<2){
			new ErrorDialog(mainframe, "No or not enough columns selected. Select more columns. If no columns are visable use the Edit Menu to convert columns to suitable type.");
			return;
		}
		
		selectedNodes = temp.toArray(new String[temp.size()]);

		try {
			bntool.execute(algor, selectedNodes);
		} catch (MultimorbidityException e) {
			new ErrorDialog(mainframe, e.getMessage());
		}
	}
	
	private void calculateAction(){
		double score = bntool.scoreNetwork((String)scorecb.getSelectedItem());
		scoreTextField.setText(score+"");
	}
	
	private void setAction(){	
		try {
			bntool.editArc("set",(String)nodesa.getSelectedItem(),(String)nodesb.getSelectedItem());
		} catch (MultimorbidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void dropAction(){
		try {
			bntool.editArc("drop",(String)nodesa.getSelectedItem(),(String)nodesb.getSelectedItem());
		} catch (MultimorbidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void reverseAction(){
		try {
			bntool.editArc("reverse",(String)nodesa.getSelectedItem(),(String)nodesb.getSelectedItem());
		} catch (MultimorbidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {		
		setupComboBoxes();
		if(o instanceof DataTool){
			setupList();
		}
	}
	
	/**
	 * Private Inner class used by Combobox
	 * @author ian.van.nieuwkoop
	 *
	 */
	class Item{
		private String key;
		private String value;
		
		public Item(String key, String value){
			this.key = key;
			this.value = value;
		}
		
		public String getKey(){
			return key;
		}
		public String getValue(){
			return value;
		}
		
		public String toString(){
			return value;
		}
	}
}
