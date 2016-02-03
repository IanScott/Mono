package gui3.start;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui3.MainFrame;
import gui3.start.DataPreviewerDialog;

public class StartDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7549897441687258783L;
	private JDialog dialog;
	private JPanel content;
	private JButton recentButton1;
	private JButton recentButton2;
	private JButton recentButton3;
	private JButton recentButton4;
	private JButton recentButton5;
	private ArrayList<JButton> sessionbuttons;
	
	private MainFrame mainframe; 
	
	public StartDialog(MainFrame mainframe) {
		this.mainframe = mainframe;
		this.sessionbuttons = new ArrayList<JButton>();
		setupDialog();
		setup();
		init();
		recentButtons();
		dialog.setContentPane(content);
		dialog.setVisible(true);
	}
	
	private void setupDialog(){
		dialog = new JDialog (mainframe, "Start Dialog", true);
        dialog.setSize (800, 515);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (mainframe);
		
	}
	
	private void setup(){
		content = new JPanel();
		content.setPreferredSize(new Dimension(794, 480));
		content.setBackground(Color.WHITE);
		content.setLayout(null);
	}
	
	private void init(){
		JPanel panel = new JPanel();
		content.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 106, 306, 366);
		content.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRecent = new JLabel("RECENT SESSIONS");
		lblRecent.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblRecent.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblRecent, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Open Other Saved Session");
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
		sessionbuttons.add(recentButton1);
		
		recentButton2 = new JButton("Empty");
		recentButton2.setAlignmentX(1.0f);
		recentButton2.setBounds(0, 61, 314, 39);
		panel_3.add(recentButton2);
		sessionbuttons.add(recentButton2);
		
		recentButton3 = new JButton("Empty");
		recentButton3.setBounds(0, 111, 314, 39);
		recentButton3.setAlignmentX(1.0f);
		panel_3.add(recentButton3);
		sessionbuttons.add(recentButton3);
		
		recentButton4 = new JButton("Empty");
		recentButton4.setBounds(0, 161, 314, 39);
		recentButton4.setAlignmentX(1.0f);
		panel_3.add(recentButton4);
		sessionbuttons.add(recentButton4);
		
		recentButton5 = new JButton("Empty");
		recentButton5.setBounds(0, 211, 314, 39);
		recentButton5.setAlignmentX(1.0f);
		panel_3.add(recentButton5);
		sessionbuttons.add(recentButton5);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBounds(10, 11, 306, 84);
		content.add(panel_1);
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
		panel_4.setBounds(326, 11, 458, 461);
		content.add(panel_4);
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
		
		/*
		if(mainframe.getDataSet() != null){
			String name = mainframe.getSessionName(0);
			if(name == null){
				name = "unknown";
			}
			recentButton1.setText("Last Session: "+name);
			recentButton1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					mainframe.newDataviewer();
				}});
		}
		*/
		int size = mainframe.sessionSize();
		//System.out.println("Session size:"+size);
		for(int i = size,j =0; i>0; i--, j++){
			JButton button = sessionbuttons.get(j);
			final int index = i-1;
			String name = mainframe.getSessionName(index);
			if(name == null){
				name = "unknown";
			}
			button.setText(name);
			button.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					//mainframe.loadSession(index);
					//mainframe.newDataviewer();
				}});
		}
		
	}
	
	private void actionImport(){
		JFileChooser fc = new JFileChooser();
		File file = null;
		fc.setDialogTitle("Choose File");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Comma Separated Value", "csv"));
        //fc.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Excel", "xls", "xlsx"));
        fc.setAcceptAllFileFilterUsed(false);
		
		
		int returnVal = fc.showOpenDialog(content);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			String filename = file.toString();
			String ext = filename.substring(filename.lastIndexOf("."),filename.length());
			
			if(ext.equals(".csv")){
				DataPreviewerDialog dp = new DataPreviewerDialog(mainframe,file);
					
				if(dp.getAnswer()){
					mainframe.importDataSet(file,dp.getSeparator(),dp.hasHead());
					dialog.dispose();
				}
			}
			else if(ext.equals(".xlsx")){
				//mainframe.importDataSet(file, true);
			}
		}
		
	}
	
	private void actionLoad(){
		JFileChooser fc = new JFileChooser("TEMP");
		File file = null;
		fc.setDialogTitle("Choose File");
		//fc.setCurrentDirectory(new File("TEMP"));
		int returnVal = fc.showOpenDialog(content);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			
			loadingscreen(file);
			
		}
		
	}
	
	private void loadingscreen(final File file){
		final JDialog frame = new JDialog(mainframe);
	    final JProgressBar progressBar = new JProgressBar();
	    progressBar.setIndeterminate(true);
	    
	    
	    frame.getContentPane().setLayout(new BorderLayout());
	    frame.getContentPane().add(new JLabel("Loading..."), BorderLayout.NORTH);
	    frame.getContentPane().add(progressBar, BorderLayout.CENTER);
	    frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    frame.pack();
	    frame.setLocationRelativeTo(content);
	    frame.setVisible(true);
	    
	    Runnable runnable = new Runnable() {
	        public void run() {
	        	mainframe.loadDataSet(file);
	            frame.setVisible(false);
	            frame.dispose();
	            //mainframe.newDataviewer(); 
	        }
	    };
	    new Thread(runnable).start();
	    dialog.dispose();
	}
	    
	    
	
	
	private void dummy(){
		mainframe.dummyFrame();
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