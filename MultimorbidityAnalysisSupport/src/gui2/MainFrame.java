package gui2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;
import java.util.Observer;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

import domain.DataSetInstance;
import domain.Result;
import domain.SessionHistory;
import domain.ToolController;
import domain.tools.Tool;
import domain.tools.ToolArgument;
import domain.tools.bntool.BnResult;
import domain.tools.bntool.BnTool;
import domain.tools.par5counttool.CountArgument;
import domain.tools.par5counttool.CountResult;
import domain.tools.par5counttool.CountTool;
import gui2.dataviewer.DataviewerPanel;
import gui2.toolviewer.BNToolPanel;
import gui2.toolviewer.counttoolviewer.CountToolPanel;

public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3845637868692982177L;
	private ToolController toolcontroller;
	private JPanel cardpanel;
	private JPanel mainpanel;
	private JPanel datapanel;
	private JPanel toolpanel;
	private final static String MAINPANEL = "mainpanel";
	private final static String DATAPANEL = "datapanel";
	private final static String TOOLPANEL = "toolpanel";
	
	public MainFrame(ToolController toolcontroller){
		this.toolcontroller = toolcontroller;
		this.init();

		// last statement to make Frame visible
		this.setVisible(true);
	}
	
	public void init(){
		this.setTitle("MMT");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(850, 550));
		//this.setLocationRelativeTo (null);
			
		this.mainpanel = new MainPanel(this);
		this.datapanel = new DataviewerPanel(this);
		cardpanel = new JPanel();
		cardpanel = new JPanel(new CardLayout());
        cardpanel.add(mainpanel, MAINPANEL);
        cardpanel.add(datapanel, DATAPANEL);
        
		this.setContentPane(cardpanel);
	}
	
	
	public void newDataviewer(){
		cardpanel.remove(datapanel);
		this.datapanel = new DataviewerPanel(this);
		cardpanel.add(datapanel, DATAPANEL);
		CardLayout cl = (CardLayout)cardpanel.getLayout();
		cl.show(cardpanel, DATAPANEL);
	}
	
	public void dataviewer(){
		CardLayout cl = (CardLayout)cardpanel.getLayout();
		cl.show(cardpanel, DATAPANEL);
		
	}
	
	public void startFrame(){
		cardpanel.remove(mainpanel);
		this.mainpanel = new MainPanel(this);
		cardpanel.add(mainpanel, MAINPANEL);
		CardLayout cl = (CardLayout)cardpanel.getLayout();
		cl.show(cardpanel, MAINPANEL);
	}
	
	public void bntoolResults(){
		BnResult bnresult = (BnResult) toolcontroller.getResult(BnTool.TOOLNAME);
		System.out.println("mainframe "+ bnresult);
		if(toolpanel != null){
			cardpanel.remove(toolpanel);
		}
		this.toolpanel = new BNToolPanel(this,bnresult);
		cardpanel.add(toolpanel, TOOLPANEL);
		CardLayout cl = (CardLayout)cardpanel.getLayout();
		cl.show(cardpanel, TOOLPANEL);
	}
	
	public void bntoolExcecute(){
		BnResult bnresult = (BnResult) toolcontroller.executeTool(BnTool.TOOLNAME);
		if(toolpanel != null){
			cardpanel.remove(toolpanel);
		}
		this.toolpanel = new BNToolPanel(this,bnresult);
		cardpanel.add(toolpanel, TOOLPANEL);
		CardLayout cl = (CardLayout)cardpanel.getLayout();
		cl.show(cardpanel, TOOLPANEL);
	}
	
	public DataSetInstance getDataSet(){
		return toolcontroller.getDataSet();
	}
	
	public void importDataSet(File file , String sep, Boolean head){
		toolcontroller.importDataSet(file, sep, head);
	}
	
	/*
	public void importDataSet(File file, Boolean bool){ 
		toolcontroller.importDataSet(file, bool);
	}
	*/
	public void loadDataSet(File file){
		toolcontroller.loadDataSet(file);
	}
	
	public void dummyFrame(){
		
	}
	
	public String[][] getDataSetValues(){
		return toolcontroller.getDataSetValues();
	}
	
	public String[] getDataSetColumnnames(){
		return toolcontroller.getDataSetColumnnames();
	}
	
	public void undo(){
		toolcontroller.undo();
	}
	
	public void redo(){
		toolcontroller.redo();
	}
	
	
	
	public void saveDataSet(String name){
		toolcontroller.saveDataSet(name);
	}
	
	public void addObserver(Observer o){
		toolcontroller.addObserver(o);
	}
	
	public String[] getDatasetFactors(String key){
		return toolcontroller.getDatasetFactors(key);
	}
	
	public void omitNa(){
		toolcontroller.omitNa();
	}
	
	public void setNA(String columnname, String na){
		toolcontroller.setNa(columnname, na);
	}
	
	public void editColumnname(String oldname, String newnm){
		toolcontroller.editColumnname(oldname, newnm);
	}
	
	public void deleteColumn(String index){
		toolcontroller.deleteColumn(index);
	}
	public int maxFactor(){
		return toolcontroller.maxFactor();
	}
	
	/*
	public void renameFactors(String columnname,String[] factors){
		toolcontroller.renameFactors(columnname, factors);
	}
	*/
	
	public void renameFactor(String columnname, String oldname, String newname){
		toolcontroller.renameFactor(columnname, oldname, newname);
	}
	
	public void deleteFactor(String columnname, String factor){
		toolcontroller.deleteFactor(columnname, factor);
	}
	
	public String getFrequency(String columnname, String level){
		return toolcontroller.getFrequency(columnname, level);
	}
	
	public String getPercentage(String columnname, String level){
		return toolcontroller.getPercentage(columnname, level);
	}
	public void test(){
		toolcontroller.test();
	}
	
	public void barChart(String columnname){
		toolcontroller.barChart(columnname);
	}
	
	public Result getResult(String tool){
		return toolcontroller.getResult(tool);
	}
	
	public void exportDataSet(String location, String filename){
		toolcontroller.exportDataSet(location, filename);
	}
	
	public CountResult countToolExecute(String[] arg, String arg1){
		return (CountResult) toolcontroller.executeTool(CountTool.TOOLNAME, new CountArgument(toolcontroller.getActiveDataSet(),arg, arg1));
	}
	
	public void countPlot1(){
		toolcontroller.countPlot1();
	}
	
	public void countPlot2(){
		toolcontroller.countPlot2();
	}
	
	public void savePlot(String name){
		toolcontroller.savePlot(name);
	}
	
	public SessionHistory getSessionHistory(){
		return toolcontroller.getSessionHistory();
	}
	
	public int sessionSize(){
		return toolcontroller.sessionSize();
	}
	
	public void loadSession(int index){
		toolcontroller.loadSession(index);
	}
	
	public String getSessionName(int index){
		return toolcontroller.getSessionName(index);
	}
	
	public String getDataSetName(){
		return toolcontroller.getSessionName(0);
	}
}
