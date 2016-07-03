package gui.startDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import domain.ToolController;
import domain.tools.bntool.BnTool;
import domain.tools.datatool.DataTool;
import gui.ErrorDialog;
import gui.datapreviewer.DataPreviewerDialog;
import util.MultimorbidityException;

/**
 * This is the Start Dialog of the Gui. Used for selecting the initial Data to load.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class StartDialog {
	private JDialog dialog;
	private JPanel contentpanel;
	private JButton recentButton1;
	private JButton recentButton2;
	private JButton recentButton3;
	private JButton recentButton4;
	private JButton recentButton5;
	private ArrayList<JButton> sessionbuttons;
	
	private JFrame mainframe;
	private DataTool datatool;
	private ToolController toolcontroller;
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame of the application
	 * @param toolcontroller the toolcontroller used by the application
	 */
	public StartDialog(JFrame mainframe, ToolController toolcontroller) {
		this.mainframe = mainframe;
		this.datatool = toolcontroller.getDataTool();
		this.toolcontroller = toolcontroller;
		this.sessionbuttons = new ArrayList<JButton>();
		
		setupDialog();
		setup();
		init();
		recentButtons();
		dialog.setContentPane(contentpanel);
		dialog.setVisible(true);
	}
	
	private void setupDialog(){
		dialog = new JDialog (mainframe, "Multimorbidity Tool", true);
        dialog.setSize (800, 515);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (mainframe);
	}
	
	private void setup(){
		contentpanel = new JPanel();
		contentpanel.setPreferredSize(new Dimension(794, 480));
		contentpanel.setBackground(Color.WHITE);
		contentpanel.setLayout(null);
	}
	
	private void init(){
		JPanel Recentpanel = new JPanel();
		contentpanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		Recentpanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		Recentpanel.setBackground(Color.LIGHT_GRAY);
		Recentpanel.setBounds(10, 106, 306, 366);
		contentpanel.add(Recentpanel);
		Recentpanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRecent = new JLabel("RECENT SESSIONS");
		lblRecent.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblRecent.setHorizontalAlignment(SwingConstants.CENTER);
		Recentpanel.add(lblRecent, BorderLayout.NORTH);
		
		JButton otherButton = new JButton("Open Other");
		otherButton.setToolTipText("Use to open files other than CSV files");
		otherButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				actionLoad();
			}});
		otherButton.setPreferredSize(new Dimension(314,75));
		otherButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		Recentpanel.add(otherButton, BorderLayout.SOUTH);
		
		JPanel historypanel = new JPanel();
		historypanel.setBackground(Color.WHITE);
		Recentpanel.add(historypanel, BorderLayout.CENTER);
		historypanel.setLayout(null);
		
		recentButton1 = new MButton("Empty");
		recentButton1.setEnabled(false);
		recentButton1.setAlignmentX(1.0f);
		recentButton1.setBounds(0, 11, 302, 39);
		historypanel.add(recentButton1);
		sessionbuttons.add(recentButton1);
		
		recentButton2 = new MButton("Empty");
		recentButton2.setEnabled(false);
		recentButton2.setAlignmentX(1.0f);
		recentButton2.setBounds(0, 61, 302, 39);
		historypanel.add(recentButton2);
		sessionbuttons.add(recentButton2);
		
		recentButton3 = new MButton("Empty");
		recentButton3.setEnabled(false);
		recentButton3.setBounds(0, 111, 302, 39);
		recentButton3.setAlignmentX(1.0f);
		historypanel.add(recentButton3);
		sessionbuttons.add(recentButton3);
		
		recentButton4 = new MButton("Empty");
		recentButton4.setEnabled(false);
		recentButton4.setBounds(0, 161, 302, 39);
		recentButton4.setAlignmentX(1.0f);
		historypanel.add(recentButton4);
		sessionbuttons.add(recentButton4);
		
		recentButton5 = new MButton("Empty");
		recentButton5.setEnabled(false);
		recentButton5.setBounds(0, 211, 302, 39);
		recentButton5.setAlignmentX(1.0f);
		historypanel.add(recentButton5);
		sessionbuttons.add(recentButton5);
		
		JPanel logopanel = new JPanel();
		logopanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		logopanel.setBounds(10, 11, 306, 84);
		contentpanel.add(logopanel);
		logopanel.setBackground(UIManager.getColor("Button.shadow"));
		logopanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblMultimorbidityTool = new JLabel("Multimorbidity Tool");
		lblMultimorbidityTool.setForeground(UIManager.getColor("Button.light"));
		lblMultimorbidityTool.setBackground(Color.BLACK);
		lblMultimorbidityTool.setHorizontalAlignment(SwingConstants.CENTER);
		lblMultimorbidityTool.setFont(new Font("SansSerif", Font.BOLD, 29));
		logopanel.add(lblMultimorbidityTool, BorderLayout.CENTER);
		
		JPanel mainpanel = new JPanel();
		mainpanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		mainpanel.setBackground(Color.WHITE);
		mainpanel.setBounds(326, 11, 458, 461);
		contentpanel.add(mainpanel);
		mainpanel.setLayout(null);
		
		JPanel mainbuttonspanel = new JPanel();
		mainbuttonspanel.setBounds(21, 27, 415, 405);
		mainpanel.add(mainbuttonspanel);
		mainbuttonspanel.setBorder(null);
		mainbuttonspanel.setBackground(Color.WHITE);
		mainbuttonspanel.setLayout(new GridLayout(2, 2, 25, 25));
		
		JButton importButton = new JButton("Import New Data");
		importButton.setToolTipText("Import new CSV files");
		importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionImport();
			}
		});
		
		importButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		mainbuttonspanel.add(importButton);
		
		JButton aboutButton = new JButton("About");
		aboutButton.setToolTipText("Information concerning application");
		aboutButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				aboutAction();
				
			}});
		aboutButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		mainbuttonspanel.add(aboutButton);
		
		JButton exampleButton = new JButton("Example Data");
		exampleButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				actionDummyImport();
				
			}});
		exampleButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		mainbuttonspanel.add(exampleButton);
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("resources/oulogo1.jpg"));
		} catch (IOException e) {
			new ErrorDialog(mainframe,"Can't load resources/oulogo1.jpg");
		}
		JButton openuniversityButton;
		if(img != null){
			ImageIcon icon = new ImageIcon(img);
			openuniversityButton = new JButton(icon);
		}else{
			openuniversityButton = new JButton("OU");
		}
		
		openuniversityButton.setToolTipText("Open Universiteit Home Page");
		openuniversityButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					openWebpage(new URI("https://www.ou.nl/"));
				} catch (URISyntaxException e1) {
					new ErrorDialog(mainframe,e1.getMessage());
				}
				
			}});
		openuniversityButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		mainbuttonspanel.add(openuniversityButton);
	}
	
	private void recentButtons(){
		
		int size = datatool.sessionSize();
		for(int i = size,j =0; i>0; i--, j++){
			final JButton button = sessionbuttons.get(j);
			final int index = i-1;
			String name = datatool.getSession(index);
			if(name == null){
				name = "unknown";
			}
			((MButton)button).setPath(name);
			if(name.length() < 35){
				button.setText(name);
			}else{
				String temp = name.substring(name.length()-35);
				button.setText("..."+temp);
			}
			
			((MButton)button).setPath(name);
			button.setToolTipText("Open "+name);
			button.setEnabled(true);
			button.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					historyAction(((MButton)button).getPath());

				}});
		}
		
	}
	
	private void historyAction(String path){
		int index = path.lastIndexOf('.');
		String ext = path.substring(index);
		File file = new File(path);
		if(ext.equals(".csv")){			
			loadCSV(file);
		}else if(ext.equals(".mmt")||ext.equals(".bn")){
			loadMMTFile(file);
		}
	}
	
	private void actionImport(){
		JFileChooser fc = new JFileChooser();
		File file = null;
		fc.setDialogTitle("Choose File");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Comma Separated Value", "csv"));
        fc.setAcceptAllFileFilterUsed(false);
		
		
		int returnVal = fc.showOpenDialog(contentpanel);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			loadCSV(file);
		}
	}
	
	private void loadCSV(File file){
		if(file.exists() && !file.isDirectory()) { 
			String filename = file.toString();
			String ext = filename.substring(filename.lastIndexOf("."),filename.length());
			
			if(ext.equals(".csv")){
				DataPreviewerDialog dp = new DataPreviewerDialog(mainframe,file);
					
				if(dp.getAnswer()){
					try {
						datatool.importDataSet(file,dp.getSeparator(),dp.hasHead());
					} catch (MultimorbidityException e) {
						new ErrorDialog(mainframe,e.getMessage());
					}
					dialog.dispose();
				}
			}
			else {
				new ErrorDialog(mainframe,"Not a CSV File");
			}
		}else{
			new ErrorDialog(mainframe,"File moved or deleted");
		}
	}
	
	private void actionDummyImport(){
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Choose File");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new FileNameExtensionFilter("Comma Separated Value", "csv"));
        fc.setAcceptAllFileFilterUsed(false);
       
		fc.setCurrentDirectory(new File("./resources"));
		File file;
		int returnVal = fc.showOpenDialog(contentpanel);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			String filename = file.toString();
			String ext = filename.substring(filename.lastIndexOf("."),filename.length());
			
			if(ext.equals(".csv")){
				DataPreviewerDialog dp = new DataPreviewerDialog(mainframe,file);
					
				if(dp.getAnswer()){
					try {
						datatool.importDataSet(file,dp.getSeparator(),dp.hasHead());
					} catch (MultimorbidityException e) {
						new ErrorDialog(mainframe,e.getMessage());
					}
					dialog.dispose();
				}
			} else{
				dialog.dispose();
			}
		}
	}
	
	private void actionLoad(){
		JFileChooser fc = new JFileChooser("TEMP");
		File file = null;
		fc.setDialogTitle("Choose File");
		fc.addChoosableFileFilter(new FileNameExtensionFilter("MultiMorbidity Tool","mmt"));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Bayesian Network","bn"));
		int returnVal = fc.showOpenDialog(contentpanel);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			loadMMTFile(file);
		}
		
	}
	
	private void loadMMTFile(final File file){
		final JDialog frame = new JDialog(mainframe);
		Boolean succes = false;
		
		Runnable thread = new Runnable(){

			@Override
			public void run() {
			    final JProgressBar progressBar = new JProgressBar();
			    progressBar.setIndeterminate(true);
			    frame.getContentPane().setLayout(new BorderLayout());
			    frame.getContentPane().add(new JLabel("Loading..."), BorderLayout.NORTH);
			    frame.getContentPane().add(progressBar, BorderLayout.CENTER);
			    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			    frame.pack();
			    frame.setLocationRelativeTo(contentpanel);
			    frame.setVisible(true);
			} };
			
	    thread.run();
	    
	    String path = file.getAbsolutePath();
	    int index = path.lastIndexOf('.');
	    String ext = path.substring(index);
	    //new ErrorDialog(mainframe, ext);
	    if(ext.equals(".mmt")){
	        try {
				datatool.loadDataSet(file);
				succes = true;
			} catch (MultimorbidityException e) {
				new ErrorDialog(mainframe,e.getMessage());
			}
	    }else if(ext.equals(".bn")){
	       BnTool bntool = ((BnTool)toolcontroller.getTool(BnTool.TOOLNAME));
	       try {
			bntool.loadResult(file);
			succes = true;
		} catch (MultimorbidityException e1) {
			new ErrorDialog(mainframe,e1.getMessage());
		}	      
	       try {
	    	datatool.loadResult(bntool.getBnResult());
	    	succes = true;
	       } catch (MultimorbidityException e) {
	    	   new ErrorDialog(mainframe,e.getMessage());
	       	}
	       
	       
	       }
	    
	    if(succes){
	    	frame.setVisible(false);
		    frame.dispose();
		    dialog.dispose();
	    }
	    
	}
	       
	private void aboutAction(){
		new AboutDialog(mainframe);
	}
	
	public void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	        	new ErrorDialog(mainframe,e.getMessage());
	        }
	    }
	}
}