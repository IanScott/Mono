package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import domain.ToolController;
import domain.tools.associationtool.AssociationTool;
import domain.tools.bntool.BnTool;
import domain.tools.clustertool.ClusterTool;
import domain.tools.counttool.CountTool;
import domain.tools.datatool.DataTool;
import domain.tools.factoranalysetool.FactoranalyseTool;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import gui.associationviewer.AssociationViewer;
import gui.bnviewer.BnViewer;
import gui.clusterviewer.ClusterViewer;
import gui.countviewer.CountViewer;
import gui.countviewer.GlmViewer;
import gui.datapreviewer.DataPreviewerDialog;
import gui.editPane.EditPanel;
import gui.factoranalyseviewer.FactoranalyseViewer;
import gui.startDialog.StartDialog;
import gui.tableviewer.TableViewer;
import util.MultimorbidityException;

/**
 * This class is the Main JFrame of the application. This Class is responsible for initializing all the 
 * Panels used by the application.
 * @author ABI team 37
 * @version 1.0
 */
public class MainFrame extends JFrame implements Observer{
	private static final long serialVersionUID = 1L;
	private static final int MINWIDTH = 550;
	private static final int MINHEIGHT = 850;
	private static final int MINSIZE = 1800;
	
	private ToolController toolcontroller;
	private DataTool datatool;	
	private JButton undoButton;
	private JButton redoButton;	
	private int screenwidth;
	private JPanel topMenuPanel;
	private JPanel editPanel;
	private JTabbedPane toolTabbedPane;	
	private TableViewer tablepanel;
	private CountViewer countpanel;
	private GlmViewer glmpanel;
	private BnViewer bnpanel;
	private AssociationViewer associationpanel;
	private ClusterViewer clusterpanel;
	private FactoranalyseViewer factoranalysepanel;
	
	/**
	 * Constructor.
	 * @param toolcontroller the toolcontroller to use
	 */
	public MainFrame(ToolController toolcontroller){
		this.toolcontroller = toolcontroller;
		this.datatool = toolcontroller.getDataTool();
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenwidth = gd.getDisplayMode().getWidth();		
		init();
		start();	
	}	
	
	private void init(){
		this.setTitle("Multimorbidity Tool");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(MINHEIGHT, MINWIDTH));
	}
	
	private void start(){
		datatool.addObserver(this);
		new StartDialog(this, toolcontroller);
		if(datatool.isReady()){
			this.setupDataPanel();
		}else {
			System.gc();
			System.exit(0);
		}
	}
	
	private void setupDataPanel(){
		setupTopMenu();	
		setupEditPanel();
		setupTabbedPane();
		this.setVisible(true);
	}
	
	
	private void setupTopMenu(){
		//setup Top Menu Panel with undo redo save load export and import buttons.
		topMenuPanel = new JPanel();
		topMenuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.getContentPane().add(topMenuPanel, BorderLayout.NORTH);
		
		//Setup undo Button
		undoButton = new JButton("undo");
		if(!datatool.canUndo()){
			undoButton.setEnabled(false);
		}
		undoButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				undoAction();
			}
		});
		topMenuPanel.add(undoButton);
		
		//Setup redo Button
		redoButton = new JButton("redo");
		if(!datatool.canRedo()){
			redoButton.setEnabled(false);
		}
		redoButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				redoAction();
			}
		});
		topMenuPanel.add(redoButton);
		
		//Setup save Button
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAction();
			}
		});
		topMenuPanel.add(saveButton);
		
		//Setup open Button
		JButton openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				openAction();
			}
		});
		topMenuPanel.add(openButton);
		
		//Setup Export Button
		JButton exportButton = new JButton("Export");
		exportButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				exportAction();
			}
		});
		topMenuPanel.add(exportButton);
		
		//Setup Import Button
		JButton importButton = new JButton("Import");
		importButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				importAction();
			}
		});
		topMenuPanel.add(importButton);
		
		//Setup Audit Export Button
		JButton auditButton = new JButton("Audit Export");
		auditButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				auditAction();
			}
		});
		topMenuPanel.add(auditButton);
	}
	
	private void setupEditPanel(){
		if(editPanel != null ){
			getContentPane().remove(editPanel);
		}
		
		if(screenwidth > MINSIZE){
			editPanel = new EditPanel(this, datatool, EditPanel.LARGER);
		}else{
			editPanel = new EditPanel(this, datatool, EditPanel.SMALLER);
		}
		
		getContentPane().add(editPanel, BorderLayout.EAST);
	}
	
	
	private void setupTabbedPane(){
		toolTabbedPane = new JTabbedPane(SwingConstants.TOP);
		getContentPane().add(toolTabbedPane, BorderLayout.CENTER);
		
		tablepanel = new TableViewer(this,datatool);
		countpanel = new CountViewer(this, toolcontroller.getDataTool(), (CountTool)toolcontroller.getTool(CountTool.TOOLNAME));
		glmpanel = new GlmViewer(this, toolcontroller.getDataTool(), (CountTool)toolcontroller.getTool(CountTool.TOOLNAME));
		
		if(screenwidth > MINSIZE){
			bnpanel = new BnViewer(this,toolcontroller.getDataTool(),(BnTool)toolcontroller.getTool(BnTool.TOOLNAME));
		}else{
			bnpanel = new BnViewer(this,toolcontroller.getDataTool(),(BnTool)toolcontroller.getTool(BnTool.TOOLNAME), BnViewer.SMALLER);
		}
		
		associationpanel = new AssociationViewer(this,toolcontroller.getDataTool(), (AssociationTool)toolcontroller.getTool(AssociationTool.TOOLNAME));
		factoranalysepanel = new FactoranalyseViewer(this,toolcontroller.getDataTool(), (FactoranalyseTool)toolcontroller.getTool(FactoranalyseTool.TOOLNAME));
		clusterpanel = new ClusterViewer(this,toolcontroller.getDataTool(),(ClusterTool)toolcontroller.getTool(ClusterTool.TOOLNAME));
		
		toolTabbedPane.add("Table View", tablepanel);
		toolTabbedPane.add("Count View", countpanel);
		toolTabbedPane.add("GLM View", glmpanel);
		toolTabbedPane.add("Bayesians Network", bnpanel);
		toolTabbedPane.add("Association view", associationpanel);
		toolTabbedPane.add("Factor analysis view", factoranalysepanel);
		toolTabbedPane.add("Cluster view", clusterpanel);
		
		toolTabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane j = (JTabbedPane) e.getSource();
				
				int i = j.getTabCount();
				for(int k=i-1; k >= 0; k--) {

					if(gui.ToolViewer.class.isInstance(j.getComponentAt(k))){ 
					ToolViewer tpanel = (ToolViewer) j.getComponentAt(k);
					 tpanel.inactive();
					}
				}
				if (j.getSelectedComponent() instanceof ToolViewer) {
					ToolViewer selected = (ToolViewer) j.getSelectedComponent();
					selected.active();
				}
			}
		});
	}
	
	//TopMenuPanel Actions
	
	private void undoAction(){
		datatool.undo();
	}
	
	private void redoAction(){
		datatool.redo();
	}
	
	//Not Finished
	private void saveAction(){
		
		JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("MultimorbidityTool","mmt");
		fc.setFileFilter(filter);
		
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = new File(fc.getSelectedFile()+".mmt");
            try {
				datatool.saveDataSet(file);
			} catch (MultimorbidityException e) {
				new ErrorDialog(this, e.getMessage());
			}
		}
	}
	
	private void openAction(){
		JFileChooser fc = new JFileChooser("TEMP");
		fc.addChoosableFileFilter(new FileNameExtensionFilter("MultimorbidityTool","mmt"));
				
		File file = null;
		fc.setDialogTitle("Choose File");
		int returnVal = fc.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			openFile(file);
		}
	}
	
	private void openFile(final File file){
		final JDialog frame = new JDialog(this);
		frame.setLocationRelativeTo(this);
	    
	    Runnable runnable = new Runnable() {
	        @Override
			public void run() {
	        	final JProgressBar progressBar = new JProgressBar();
	    	    progressBar.setIndeterminate(true);

	    	    frame.getContentPane().setLayout(new BorderLayout());
	    	    frame.getContentPane().add(new JLabel("Loading..."), BorderLayout.NORTH);
	    	    frame.getContentPane().add(progressBar, BorderLayout.CENTER);
	    	    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	    	    frame.pack();
	    	    frame.setVisible(true);
	        }
	    };
	    new Thread(runnable).start();
	    try {
			datatool.loadDataSet(file);
		} catch (MultimorbidityException e) {
			new ErrorDialog(this, e.getMessage());
		}
        frame.setVisible(false);
        frame.dispose();
	}
	
	private void exportAction(){
		JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Comma Separated Value","csv");
		fc.setFileFilter(filter);
		
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String location= file.getParent();
            String filename= file.getName();		
            datatool.exportDataSet(location, filename);
		}
	}
	
	private void importAction(){
		JFileChooser fc = new JFileChooser();
		File file = null;
		fc.setDialogTitle("Choose File");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Comma Separated Value", "csv"));
        fc.setAcceptAllFileFilterUsed(false);
		
		int returnVal = fc.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
			String filename = file.toString();
			String ext = filename.substring(filename.lastIndexOf("."),filename.length());
			
			if(ext.equals(".csv")){
				DataPreviewerDialog dp = new DataPreviewerDialog(this,file);
					
				if(dp.getAnswer()){
					try {
						datatool.importDataSet(file,dp.getSeparator(),dp.hasHead());
					} catch (MultimorbidityException e) {
						new ErrorDialog(this, e.getMessage());
					}
				}
			}
		}
	}
	
	private void auditAction(){
		JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("R script file","r");
		fc.setFileFilter(filter);
		
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
              toolcontroller.exportAudit(file);
    		} catch (MultimorbidityException e) {
    			new ErrorDialog(this, e.getMessage());
    		}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		setupEditPanel();
		resetUndoButtons();
		this.validate();
		this.repaint();
	}
	
	private void resetUndoButtons(){
		if(undoButton !=null && redoButton != null){
			if(datatool.canUndo()){
				undoButton.setEnabled(true);
			}else{
				undoButton.setEnabled(false);
			}
			if(datatool.canRedo()){
				redoButton.setEnabled(true);
			}else{
				redoButton.setEnabled(false);
			}
		}
	}
}
