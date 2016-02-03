package guiold;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import domain.ToolController;
import guiold.DataPreviewerDialog;
import guiold.GuiController;
import guiold.panels.DataSetPanel;

import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.UIManager;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
	

public class StartFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7549897441687258783L;
	private ToolController toolcontroller;
	private GuiController guicontroller;
	
	private JButton recentButton1; 
	 
	
	public StartFrame(ToolController toolcontroller, GuiController guicontroller) {
		this.toolcontroller = toolcontroller;
		this.guicontroller = guicontroller;
		
		setup();
		init();
		recentButtons();
	}
	
	private void setup(){
		this.setResizable(false);
		this.setSize(800,500);
		this.setTitle("MMT");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
	}
	
	private void init(){
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 95, 306, 366);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRecent = new JLabel("RECENT");
		lblRecent.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblRecent.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblRecent, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Open Other");
		btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				actionLoad();
			}});
		btnNewButton.setPreferredSize(new Dimension(314,75));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel.add(btnNewButton, BorderLayout.SOUTH);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(null);
		
		recentButton1 = new JButton("Empty");
		recentButton1.setAlignmentX(1.0f);
		recentButton1.setBounds(0, 11, 314, 39);
		panel_3.add(recentButton1);
		
		JButton button = new JButton("Empty");
		button.setAlignmentX(1.0f);
		button.setBounds(0, 61, 314, 39);
		panel_3.add(button);
		
		JButton btnNewButton_1_1 = new JButton("Empty");
		btnNewButton_1_1.setBounds(0, 111, 314, 39);
		btnNewButton_1_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_3.add(btnNewButton_1_1);
		
		JButton btnNewButton_2 = new JButton("Empty");
		btnNewButton_2.setBounds(0, 161, 314, 39);
		panel_3.add(btnNewButton_2);
		
		JButton btnNewButton_4 = new JButton("Empty");
		btnNewButton_4.setBounds(0, 211, 314, 39);
		panel_3.add(btnNewButton_4);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBounds(10, 0, 306, 84);
		getContentPane().add(panel_1);
		panel_1.setBackground(UIManager.getColor("Button.shadow"));
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblMultimorbidityTool = new JLabel("Multimorbidity Tool");
		lblMultimorbidityTool.setForeground(UIManager.getColor("Button.light"));
		lblMultimorbidityTool.setBackground(Color.BLACK);
		lblMultimorbidityTool.setHorizontalAlignment(SwingConstants.CENTER);
		lblMultimorbidityTool.setFont(new Font("SansSerif", Font.BOLD, 29));
		panel_1.add(lblMultimorbidityTool, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(326, 0, 458, 461);
		getContentPane().add(panel_4);
		panel_4.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(21, 27, 415, 405);
		panel_4.add(panel_2);
		panel_2.setBorder(null);
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(new GridLayout(2, 2, 25, 25));
		
		JButton btnNewButton_3 = new JButton("Import New Data");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionImport();
			}
		});
		
		btnNewButton_3.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel_2.add(btnNewButton_3);
		
		JButton btnNewButton_6 = new JButton("Help");
		btnNewButton_6.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dummy();
				
			}});
		btnNewButton_6.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel_2.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("Example Data");
		btnNewButton_7.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dummy();
				
			}});
		btnNewButton_7.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel_2.add(btnNewButton_7);
		
		JButton btnNewButton_5 = new JButton("OU");
		btnNewButton_5.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					openWebpage(new URI("https://www.ou.nl/"));
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}});
		btnNewButton_5.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel_2.add(btnNewButton_5);
	}
	
	public void recentButtons(){
		if(toolcontroller.getDataSet() != null){
			recentButton1.setText("Loaded Dataset");
			recentButton1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					guicontroller.dataviewerFrame();
				}});
		}
	}
	
	private void actionImport(){
		JFileChooser fc = new JFileChooser();
		File file = null;
		fc.setDialogTitle("Choose File");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Comma Separated Value", "csv"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Excel", "xls", "xlsx"));
        fc.setAcceptAllFileFilterUsed(false);
		
		
		int returnVal = fc.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			String filename = file.toString();
			String ext = filename.substring(filename.lastIndexOf("."),filename.length());
			
			if(ext.equals(".csv")){
				DataPreviewerDialog dp = new DataPreviewerDialog(this,file);
					
				if(dp.getAnswer()){
					toolcontroller.importDataSet(file,dp.getSeparator(),dp.hasHead());
					guicontroller.dataviewerFrame();
				}
			}
			else if(ext.equals(".xlsx")){
				
				toolcontroller.importDataSet(file, true);
				//guicontroller.dataviewerFrame();
			}
		}
	}
	
	private void actionLoad(){
		JFileChooser fc = new JFileChooser("TEMP");
		File file = null;
		fc.setDialogTitle("Choose File");
		//fc.setCurrentDirectory(new File("TEMP"));
		int returnVal = fc.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			
			loadingscreen(file);
			
		}
	}
	
	private void loadingscreen(final File file){
		final JDialog frame = new JDialog(this);
	    final JProgressBar progressBar = new JProgressBar();
	    progressBar.setIndeterminate(true);
	    
	    
	    frame.getContentPane().setLayout(new BorderLayout());
	    frame.getContentPane().add(new JLabel("Loading..."), BorderLayout.NORTH);
	    frame.getContentPane().add(progressBar, BorderLayout.CENTER);
	    frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    frame.pack();
	    frame.setLocationRelativeTo(this);
	    frame.setVisible(true);
	    
	    Runnable runnable = new Runnable() {
	        public void run() {
	        	toolcontroller.loadDataSet(file);
	            frame.setVisible(false);
	            frame.dispose();
	            guicontroller.dataviewerFrame(); 
	        }
	    };
	    new Thread(runnable).start();
	    
	}
	    
	    
	
	
	private void dummy(){
		guicontroller.dummyFrame();
	}
	
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
}
