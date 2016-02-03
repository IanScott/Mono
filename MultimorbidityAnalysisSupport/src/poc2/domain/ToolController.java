package poc2.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

import poc2.util.DataSetMapper;


public class ToolController extends Observable{
	// Constants
	private static final String RVAR = "MyData"; //Data variable in R
	
	private Rengine rengine;
	private DataSetController dsController;
	private HashMap<String,Tool> tools;
	private Stack<DataSetController> dscStack;
	
	public ToolController(){
		dsController = new DataSetController();
		dscStack = new SizedStack<DataSetController>(5);
		//dsController.addObserver(this);
		setupRengine();
		setupTools();
	}
	
	private void setupRengine(){
		// Initialising the R engine
		String[] dummyArgs = {"--vanilla"};
		
		rengine = new Rengine(dummyArgs,false,null);
		
		// Loading required R libraries
		rengine.eval("library(bnlearn)");
		rengine.eval("library(JavaGD)");
		rengine.eval("library(xlsx)");
		rengine.eval("library(XLConnect)");
		// Setting up JavaGD in R
		rengine.eval("Sys.setenv('JAVAGD_CLASS_NAME'='poc2/domain/BNJavaGD')");
		rengine.eval("JavaGD()");
	}
	
	private void setupTools(){
		tools = new HashMap<String, Tool>();
		Tool bntool = new BNTool("BNTool", rengine, dsController);
		tools.put(bntool.getName(), bntool);
	}
	
	////////////////////////////////////////////////////////////////////
	// ADD OBSERVERS
	public void addDataSetObserver(Observer o){
		this.addObserver(o);
	}
	
		
	///////////////////DataSet Controls  ///////////////////////////////
	
	public String[][] getDataSetValues(){
		return dsController.getDataset().getValues();
	}
	
	public String[] getDataSetColumnnames(){
		return dsController.getDataset().getColumnnames();
	}
	
	public String[] getDatasetFactors(String key){
		return dsController.getDataset().getFactors(key);
	}
	
	
	
	
	// only setup for csv files!
		/**
		 * Method for loading your dataset
		 * @param file the File to load
		 */
	public void loadDataSet(File file, String sep, boolean head){
		String tfilepath = file.getAbsolutePath().replace('\\', '/');										// get filepath as String
		String filepath = this.addSlashes(tfilepath);
			
		//REXP rexp = rengine.eval(RVAR+"<- read.csv(file=\"" + filepath + "\", header=TRUE, sep= \",\")"); 	// get REXP
			
		REXP rexp = rengine.eval(RVAR+"<- read.csv(file=\"" + filepath + "\", header="+(Boolean.toString(head)).toUpperCase()+", sep= \""+sep+"\")"); 	// get REXP
		DataSet tdataset = Converter.toDataSet(rexp);		// Convert rexp to a dataset
		tdataset = addFactors(tdataset);
			
		setDataSetController(tdataset);
		this.setChanged();
		this.notifyObservers();
	}
	
	public void loadDataSet(File file, boolean head){
		String tfilepath = file.getAbsolutePath().replace('\\', '/');										// get filepath as String
		String filepath = this.addSlashes(tfilepath);
		
		
		rengine.eval("wb <- loadWorkbook(\"C:/data.xlsx\")");
		rengine.eval(RVAR + " <- readWorksheet(wb, sheet = 1)"); 
		
		REXP rexp = rengine.eval("MyData");
		//DataSet tdataset = Converter.toDataSet(rexp);		// Convert rexp to a dataset
		//tdataset = addFactors(tdataset);
			
		//dsController.updateDataSet(tdataset);
		
		this.setChanged();
		this.notifyObservers();
	}
	public void loadDataSet(File file, boolean head, String sheet){
		String tfilepath = file.getAbsolutePath().replace('\\', '/');										// get filepath as String
		String filepath = this.addSlashes(tfilepath);
			
		REXP rexp = rengine.eval(RVAR+"<- read.xlsx2(file=\"" + filepath + "\","+sheet+ "\")"); 	// get REXP
		DataSet tdataset = Converter.toDataSet(rexp);		// Convert rexp to a dataset
		tdataset = addFactors(tdataset);
		
		setDataSetController(tdataset);
		this.setChanged();
		this.notifyObservers();
	}
	
	public void exportDataSet(String location, String filename){
		String expr = "write.table("+RVAR+", \""+location+"/"+filename+".csv\", sep=\",\")";
		rengine.eval(expr);
	}
	
	public void saveDataSet(String name){
		DataSetMapper.saveDataSet(dsController.getDataset(), name);
	}
	
	public void loadSDataSet(File file){

    	DataSet tdataset = DataSetMapper.loadDataSet(file);
    	setDataSetController(tdataset);
    	
		
		String[][] values = dsController.getDataset().getValues();
		int rows = values.length;
		//int rows = 5;
		String[] columnnames = dsController.getDataset().getColumnnames();
		int columns = columnnames.length;
		
		//String[] exprs = new String[columns];


		

		for(int i = 0; i< columns; i++){
			StringBuilder expr = new StringBuilder();
			expr.append(columnnames[i]+"<- c(");
			
		
			for(int j = 0; j< rows-1; j++){
			if(j == 0){
				expr.append("'" +values[j][i]+"'");
			}else{ expr.append(",'"+values[j][i]+"'");}
			}
			expr.append(")");
			
			//exprs[i] = expr.toString();
			rengine.eval(expr.toString());
		}
		
		StringBuilder expr = new StringBuilder();
		expr.append("MyData <- data.frame(");
		for(int i =0; i<columns; i++){
			if(i==0){
				expr.append(columnnames[i]);
			}else{expr.append(","+columnnames[i]);}
		}
		expr.append(")");
		rengine.eval(expr.toString());
		
		this.setChanged();
		this.notifyObservers();
	}
	
	private void syncdata(){
		String[][] values = dsController.getDataset().getValues();
		int rows = values.length;
		String[] columnnames = dsController.getDataset().getColumnnames();
		int columns = columnnames.length;
		
		
		for(int i = 0; i< columns; i++){
			StringBuilder expr = new StringBuilder();
			expr.append(columnnames[i]+"<- c(");
			
		
			for(int j = 0; j< rows-1; j++){
			if(j == 0){
				expr.append("'" +values[j][i]+"'");
			}else{ expr.append(",'"+values[j][i]+"'");}
			}
			expr.append(")");
			//exprs[i] = expr.toString();
			rengine.eval(expr.toString());
		}
		
		StringBuilder expr = new StringBuilder();
		expr.append("MyData <- data.frame(");
		for(int i =0; i<columns; i++){
			if(i==0){
				expr.append(columnnames[i]);
			}else{expr.append(","+columnnames[i]);}
		}
		expr.append(")");
		rengine.eval(expr.toString());
	}
	
	public void undo(){
		dsController.undo();
		syncdata();
		this.setChanged();
		this.notifyObservers();
	}
	
	public void redo(){
		dsController.redo();
		syncdata();
		this.setChanged();
		this.notifyObservers();
	}
	private DataSet addFactors(DataSet ds){
		HashMap<String,String[]> factors = new HashMap<String,String[]>();
		//Get levels
		for(String s: ds.getColumnnames()){
			REXP rexp2 = rengine.eval("levels("+RVAR+"$" + s + ")");
			String [] levels = rexp2.asStringArray();
			factors.put(s,levels);
			}
			ds.setFactors(factors);
				
		return ds;
	}
		
		
		
	/**
		 * Getter for the DataSet Attribut
		 * @return a DataSet object
	*/
	public DataSetController getDataSetController(){
			return dsController;
	}
		
	public DataSet getDataSet(){
			return dsController.getDataset();
	}
	
	/////////////////////////////Tool Controles
	
	//public Tool getTool(String key){
		//	return tools.get(key);
	//}
	
	public Result executeTool(String key){
		Result res = tools.get(key).execute();
		dsController.getDataset().addResult(res);
		return res;
	}
	
	public Result executeTool(String key, Object object){
		Result res = tools.get(key).execute(object);
		dsController.getDataset().addResult(res);
		return res;
	}
	
	////////////////////  helper Methods 
	private String addSlashes(String paramString)
		  {
		    if (paramString == null) {
		      return "";
		    }
		    StringBuffer localStringBuffer = new StringBuffer(paramString);
		    for (int i = 0; i < localStringBuffer.length(); i++) {
		      if (localStringBuffer.charAt(i) == '"') {
		        localStringBuffer.insert(i++, '\\');
		      } else if (localStringBuffer.charAt(i) == '\'') {
		        localStringBuffer.insert(i++, '\\');
		      }
		    }
		    return localStringBuffer.toString();
		  }
	
		/**********************************************************************************
		 * 
		 *	Editing / Cleaning DataSet Methods 
		 * 
		********************************************************************************/
		
		/**
		 * Method for cleaning the dataset
		 * Only setup for removing N/A values
		 */
	/*
	public void cleanData(){
			// get REXP
			rengine.eval("MyData[MyData==\"N/A\"]<-NA");
			rengine.eval("MyData <- MyData[complete.cases(MyData), ]");
			REXP rexp = rengine.eval("MyData <- droplevels(MyData)");
			// Convert rexp to a dataset
			DataSet tdataset = Converter.toDataSet(rexp);
					
			// Update DataSet
			tdataset = addFactors(tdataset);
			dsController.updateDataSet(tdataset);	
			
			this.setChanged();
			this.notifyObservers();
		}
		
		*/
	
		public void setNA(String columnname , String na){
			
			String expr = "MyData$"+columnname+ "[is.na(MyData$"+columnname+")]<-"+ "'"+na+"'";
			rengine.eval(expr);
			System.out.println(expr);
			//rengine.eval("MyData[MyData==\"N/A\"]<-NA");
			//rengine.eval("MyData <- MyData[complete.cases(MyData), ]");
			//rengine.eval("MyData <-na.omit(MyData)");
			//rengine.eval("MyData <- droplevels(MyData)");
			
			REXP rexp = rengine.eval("MyData");
			DataSet tdataset = Converter.toDataSet(rexp);
			
			// Update DataSet
			tdataset = addFactors(tdataset);
			dsController.updateDataSet(tdataset);
			
			this.setChanged();
			this.notifyObservers();
		}
		
		
		public void omitNa(){
			//HashMap<String,String[]> factors = new HashMap<String,String[]>();
			rengine.eval("MyData <-na.omit(MyData)");
			rengine.eval("MyData <- droplevels(MyData)");
			REXP rexp = rengine.eval("MyData");
			DataSet tdataset = Converter.toDataSet(rexp);
			
			// Update DataSet
			tdataset = addFactors(tdataset);
			dsController.updateDataSet(tdataset);
			
			this.setChanged();
			this.notifyObservers();
		}
		
		/*
		// Methode wordt niet gebruikt, misschien later
		public void naToMean(){
			rengine.eval("MyData1 <- impute(MyData, median)");
			REXP rexp = rengine.eval("MyData");
			DataSet tdataset = Converter.toDataSet(rexp);
			
			//Get levels
			// Update DataSet
			tdataset = addFactors(tdataset);
			dsController.updateDataSet(tdataset);
			
			this.setChanged();
			this.notifyObservers();
		}
		
		public void naToMedian(){
			//rengine.eval("MyData <- impute(MyData, fun = median)");
			String[] cols = dsController.getDataset().getColumnnames();
			String temp = "";
			int i = 0;
			for(String s: cols){
				REXP rex = rengine.eval("c"+i+  "<- impute(MyData$"+s +")");
				temp = temp +s +"c" +i +",";
			}
			rengine.eval("MyData <-data.frame(" +temp+ ")");
			
			REXP rexp = rengine.eval("MyData");
			DataSet tdataset = Converter.toDataSet(rexp);
			
			// Update DataSet
			tdataset = addFactors(tdataset);
			dsController.updateDataSet(tdataset);
			
			this.setChanged();
			this.notifyObservers();
		}
		*/
		public int maxFactor(){
			return dsController.getDataset().getMaxFactors();
		}
		
		public void editColumnname(String name, String newName){
			String[] cols = getDataSetColumnnames();
			if(newName.isEmpty() || Arrays.asList(cols).contains(newName)){
				return;
			}
			String expr = "colnames("+RVAR+")[colnames("+RVAR+") =='"+name+"'] <-'"+newName+"'";
			System.out.println(expr);
			rengine.eval(expr);
			REXP rexp = rengine.eval("MyData");
			DataSet tdataset = Converter.toDataSet(rexp);
			
			// Update DataSet
			tdataset = addFactors(tdataset);
			dsController.updateDataSet(tdataset);
			//dsController.editColumnname(index, name);
			//syncdata();
			this.setChanged();
			this.notifyObservers();
		}
		
		
		public void deleteColumn(String columnname){
			System.out.println("delete "+ columnname);
			rengine.eval(RVAR+"$"+columnname+"<- NULL");
			REXP rexp = rengine.eval("MyData");
			DataSet tdataset = Converter.toDataSet(rexp);
			
			// Update DataSet
			tdataset = addFactors(tdataset);
			dsController.updateDataSet(tdataset);
			
			this.setChanged();
			this.notifyObservers();
		}
		
		public void renameFactors(String columnname,String[] factors){
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i<factors.length;i++){
				if(i == 0){
					sb.append("'"+factors[i]+ "'");
				}else{sb.append(",'"+factors[i]+"'");}
			}
			rengine.eval(RVAR+"$"+columnname+"<- factor(c("+sb.toString()+ "))");
		
		
		REXP rexp = rengine.eval("MyData");
		DataSet tdataset = Converter.toDataSet(rexp);
		
		// Update DataSet
		tdataset = addFactors(tdataset);
		dsController.updateDataSet(tdataset);
		
		this.setChanged();
		this.notifyObservers();
		}
		public void replaceNas(){
			
		}
		
		public void deleteFactor(String columnname, String factor){
			System.out.println("deleteFActor"+columnname + " " +factor);
			rengine.eval("MyData$"+columnname+"[MyData$"+columnname +"==\"" +factor+ "\"]<-NA");
			//String expr = "MyData$"+columnname +" <- replace(MyData$"+columnname +",MyData$"+columnname+"==" +factor+", NA)";
			//rengine.eval(expr);
			rengine.eval("MyData$"+columnname +" <- droplevels(MyData$"+columnname +")");
			
			REXP rexp = rengine.eval("MyData");
			DataSet tdataset = Converter.toDataSet(rexp);
			
			// Update DataSet
			tdataset = addFactors(tdataset);
			dsController.updateDataSet(tdataset);
			
			this.setChanged();
			this.notifyObservers();
		}
		
		private void setDataSetController(DataSet dataset){
			if(dsController != null){
				dscStack.add(dsController);
			}
			dsController = new DataSetController();
			dsController.updateDataSet(dataset);
		}
}
