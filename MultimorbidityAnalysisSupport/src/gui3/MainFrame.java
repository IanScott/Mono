package gui3;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

import domain.Column;
import domain.Result;
import domain.SessionHistory;
import domain.ToolController;
import gui3.clusterviewer.ClusterviewerPanel;
import gui3.dataviewer.DataviewerPanel;
import gui3.start.StartDialog;
import gui3.toolviewer.Countviewer;
import gui3.toolviewer.Toolpanel;
import util.MultimorbidityException;

public class MainFrame extends JFrame implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3845637868692982177L;
	private ToolController toolcontroller;
	private JTabbedPane tabpanel;
	private JPanel datapanel;
	private JPanel toolpanel;

	private JPanel clusterpanel;
	private String plotType;
	private String numbClusters;
	private String clusterMethod;
	private String clusterColumn;

	private JPanel countpanel;
	private final static String DATAPANEL = " Datapanel  ";
	private final static String TOOLPANEL = " Toolpanel  ";
	private final static String COUNTPANEL = " Countpanel ";
	private final static String CLUSTPANEL = " Clusterpanel ";
	
	public MainFrame(ToolController toolcontroller){
		this.toolcontroller = toolcontroller;
		this.init();
		toolcontroller.addObserver(this);
		new StartDialog(this);
		this.setupDataPanel();

		this.setupClusterPanel();
		this.setupToolPanel();

		this.setVisible(true);
	}
	
	private void init(){
		this.setTitle("MMT");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(850, 550));

		tabpanel = new JTabbedPane();
		tabpanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabpanel.setBackground(Color.WHITE);
        Font myFont1 = new Font("Serif",Font.PLAIN, 18);
        tabpanel.setFont(myFont1);
		this.setContentPane(tabpanel);
	}
	
	private void setupDataPanel(){
		if(this.datapanel == null){
			this.datapanel = new DataviewerPanel(this);
			tabpanel.add(datapanel, DATAPANEL);
		}
	}
	
	private void setupClusterPanel(){
		this.clusterpanel = new ClusterviewerPanel(this);
		tabpanel.add(clusterpanel, CLUSTPANEL);
	}

	private void setupToolPanel(){
		this.toolpanel = new Toolpanel(this, toolcontroller);
		tabpanel.add(toolpanel, TOOLPANEL);
		
	}
	private void setupCountPanel(){
		this.countpanel = new Countviewer(this, toolcontroller);
		tabpanel.add(countpanel, COUNTPANEL);
	}

	public void importDataSet(File file , String sep, Boolean head){
		try {
			toolcontroller.importDataSet(file, sep, head);
		} catch (MultimorbidityException e) {
			e.printStackTrace();
		}
	}
	
	public void loadDataSet(File file){
		toolcontroller.loadDataSet(file);
	}
	
	public void dummyFrame(){
		
	}
	
	public String[][] getDataSetValues(){
		//String[][] values = toolcontroller.getDataSetValues();
		String[] columns = toolcontroller.getDataSetColumnnames();
		String[][] values = new String[toolcontroller.getMaxRows()][columns.length];
		
		
		for(int i = 0; i<columns.length; i++){
			String type = toolcontroller.getColumnType(columns[i]);
	
			String[] vlist= toolcontroller.getColumnValues(columns[i]);
			
			for(int j = 0;j<vlist.length; j++){
				
				
				if(vlist[j] == null){
					values[j][i] = "<<UNKNOWN>>";
				}else if(type.equals("logical")&&vlist[j].equals("1")){
					values[j][i] = "TRUE";
				}else if(type.equals("logical")&&vlist[j].equals("0")){
					values[j][i] = "FALSE";
				}else if(type.equals("logical")&&vlist[j].equals("-2147483648")){
					values[j][i] = "<<NOT A BOOLEAN>>";
				}else if(type.equals("Date")){
					values[j][i] = this.convertRDate(vlist[j]);
				}
				
				
				else { values[j][i] = vlist[j];} 
			}
					
		}		
		return values;
	}

	
	public String[] getDataSetColumnnames(){
		return toolcontroller.getDataSetColumnnames();
	}
	
	public String[] getDataSetColumnnames(Column.Type type) {
		return toolcontroller.getDataSetColumnnames(type);
	}

	public String[] getDataSetNumColumnnames(Column.Type type) {
		return toolcontroller.getDataSetNumColumnnames(type);
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
		try {
			toolcontroller.omitNa();
		} catch (MultimorbidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setNA(String columnname, String na){
		try {
			toolcontroller.setNa(columnname, na, "");
		} catch (MultimorbidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void editColumnname(String oldname, String newnm){
		try {
			toolcontroller.editColumnname(oldname, newnm);
		} catch (MultimorbidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteColumn(String index){
		try {
			toolcontroller.deleteColumn(index);
		} catch (MultimorbidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//public int maxFactor(){
	//	return toolcontroller.maxFactor();
	//}
	
	
	public void renameFactor(String columnname, String oldname, String newname){
		try {
			toolcontroller.renameFactor(columnname, oldname, newname);
		} catch (MultimorbidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteFactor(String columnname, String factor){
		try {
			toolcontroller.deleteFactor(columnname, factor);
		} catch (MultimorbidityException e) {
			e.printStackTrace();
		}
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
	
	public void barChartVert(String columnname){
		toolcontroller.barChartVert(columnname);
	}

	public void barChartHoriz(String columnname){
		toolcontroller.barChartHoriz(columnname);
	}

	public void combineGraphs(){
		toolcontroller.combineGraphs();	
	}
	
	public Result getResult(String tool){
		return toolcontroller.getResult(tool);
	}
	
	public void exportDataSet(String location, String filename){
		toolcontroller.exportDataSet(location, filename);
	}
	
	//public CountResult countToolExecute(String[] arg, String arg1){
	//	return (CountResult) toolcontroller.executeTool(CountTool.TOOLNAME, new CountArgument(toolcontroller.getActiveDataSet(),arg, arg1));
	//}
	
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
		return toolcontroller.getDataSetName();
	}
	public String getColumnType(String columnname){
		return toolcontroller.getColumnType(columnname);
	}
	
	public String getColumnMedian(String columnname){
		return toolcontroller.getColumnMedian(columnname);
	}
	
	public double getColumnAverage(String columnname){
		return toolcontroller.getColumnAverage(columnname);
	}
	
	public Boolean canUndo(){
		return toolcontroller.canUndo();
	}
	
	public Boolean canRedo(){
		return toolcontroller.canRedo();
	}
	
	public void castColumn(String columnname, String type){
		try {
			toolcontroller.castColumn(columnname, type.toLowerCase());
		} catch (MultimorbidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void snapShot(){
		toolcontroller.snapshot();
	}

	public void setPlotType(String plotType){
		this.plotType = plotType;
	}

	public String getPlotType(){
		return this.plotType;
	}

	public void setNumbClusters(String numbClusters){
		this.numbClusters = numbClusters;
	}

	public String getNumbClusters(){
		return this.numbClusters;
	}

	public void setClusterMethod(String clusterMethod){
		this.clusterMethod = clusterMethod;
	}

	public String getClusterMethod(){
		return this.clusterMethod;
	}

	public void setClusterColumn(String clusterColumn){
		this.clusterColumn = clusterColumn;
	}

	public String getClusterColumn(){
		return this.clusterColumn;
	}

	public void CalculateNumberOfClusters(String columnname){
		// TODO Auto-generated method stub
		this.setClusterColumn(columnname);
		toolcontroller.CalculateNumberOfClusters(columnname);
	}

	public void StartClusterProcess(String columnname, String numbClusters, String method){
		// TODO Auto-generated method stub
		this.setClusterColumn(columnname);
		this.setNumbClusters(numbClusters);		
		this.setClusterMethod(method);		
		toolcontroller.StartClusterProcess(columnname, numbClusters, method);
	}
	
	public String[] getTypes(){
		return toolcontroller.getTypes();
	}
	
	public String convertRDate(String temp){
		int x = (int)Double.parseDouble(temp);
		String dt = "1970-01-01";  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DATE, x);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		
		return dt;
	}
	public void convertFactorsToColumns(String columnname) {
		try {
			toolcontroller.convertFactorsToColumns(columnname);
		} catch (MultimorbidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean hasNa(String columnname){
		return toolcontroller.hasNa(columnname);
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		
		while(tabpanel.getComponentCount() > 1){
			tabpanel.removeTabAt(1);
		}
		
		if(this.datapanel == null){
			setupDataPanel();
		}
		this.toolpanel = new Toolpanel(this, toolcontroller);
		tabpanel.addTab(TOOLPANEL,toolpanel);
		
		this.countpanel = new Countviewer(this, toolcontroller);
		tabpanel.addTab(COUNTPANEL, countpanel);
		
		
		
	}
}
