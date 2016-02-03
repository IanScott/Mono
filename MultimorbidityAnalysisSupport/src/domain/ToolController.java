package domain;

import domain.Column.Type;
import domain.DataSet;
import domain.Result;
import domain.tools.Tool;
import domain.tools.ToolArgument;
import domain.tools.bntool.BnTool;
import domain.tools.par5counttool.CountArgument;
import domain.tools.par5counttool.CountTool;
import util.MultimorbidityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;

/**
 * Class that represents ToolController.
 * @author ABI team 37
 *
 */
public class ToolController extends Observable {

  // Constants and attributes
  private static final String RVAR = "MyData"; //Data variable in R
  private Rengine rengine;
  private DataSet activeDataset;
  private HashMap<String,Tool> tools;
  private SessionHistory sessions;
  private Logger logger = LogManager.getLogger(this.getClass());  
  
  /**
   * Constructor of ToolController.
   * Creates a new DataSetController
   * Makes a connection with R via a Rengine object
   * Loads the necessary R libraries
   * Creates the setup for the different tools
   */
  public ToolController() {
    logger.info("Constructor ToolController called (without parameters)");
    this.activeDataset = new DataSet();
    this.sessions = new SessionHistory();
    this.connectRengine();
    this.loadRengineLibraries();
    this.setupTools();
  }
  
  /**
   * Constructor of ToolController with session history.
   * @param sessions Sessions to be used
   */
  public ToolController(SessionHistory sessions) {
    this();
    logger.info("Constructor ToolController called (with sessions)");
    this.sessions = sessions;
    logger.debug("Number of sessions for ToolController " + sessions.size());
  }
  
  /**
   * Initializes the rengine object,
   * without reading user file to set environment variables,
   * without reading the site-wide profile at startup,
   * without reading the user's profile at startup,
   * without starting event loop.
   */
  private void connectRengine() {
    logger.info("Private method connectRengine called");
    String[] dummyArgs = {"--vanilla"};
    this.rengine = new Rengine(dummyArgs, false, null);
    logger.debug("Application connected with R: " + isConnectedWithR());
  }
  
  private void loadRengineLibraries() {
    logger.info("Private method loadLibraries called");
    // Loading required R libraries
    rengine.eval("library(bnlearn)");
    rengine.eval("library(JavaGD)");
    rengine.eval("library(reshape2)"); 
    //rengine.eval("library(XLConnect)"); 
    // Setting up JavaGD in R
    rengine.eval("Sys.setenv('JAVAGD_CLASS_NAME'='javaGD/BNJavaGD')");
    rengine.eval("JavaGD()");
    String[] libraries = rengine.eval("(.packages())").asStringArray();
    logger.debug("R libraries bnlearn loaded: " + Arrays.asList(libraries).contains("bnlearn"));
    logger.debug("R libraries bnlearn loaded: " + Arrays.asList(libraries).contains("JavaGD"));
  }

  private void setupTools() {
    logger.info("Private method setupTools called");
    this.tools = new HashMap<String, Tool>();
    //Setup bnTool and add to tools
    Tool bntool = new BnTool(rengine);
    tools.put(BnTool.TOOLNAME, bntool);
    //Setup countTool and add to tools
    Tool counttool = new CountTool(rengine);
    tools.put(CountTool.TOOLNAME, counttool);
    logger.debug("Tools setup for " + tools.values().toString()); //Temporary only String representation of object
  }
  
  /*
   * 
   * Getters
   * 
   */
  
  /**
   * Method for returning the R Engine instance.
   * @return the Rengine object in use.
   
  public Rengine getRengine() {
    return rengine;
  }
  
  /**
   * DELETE!!!!!
   * Test method
   */
  public void test(){
          rengine.eval("plot(5)");
  }
  /**
   * Method that returns the Saved Result of a Tool.
   * @param tool the Tool which's Result must be returned.
   * @return a Result object
   */
  public Result getResult(String tool){
    logger.info("Method getResult called");
    logger.debug("Method getResult for tool " + tool + " returned: " + activeDataset.getResult(tool).toString()); //Temporary only String representation of object
          return activeDataset.getResult(tool);

  }
  
  public HashMap<String,Tool> getTools() {
    logger.info("Method getTools called");
    logger.debug("Method getTools returned: " + tools.toString()); //Temporary only String representation of object
    return tools;
  }
  
  /*
   * 
   * DataSet Controls
   * 
   */
  
  /**
   * Method return the list of values for a column.
   * @param columnname name of the column who's values to return.
   * @return a String array of values.
   */
  public String[] getColumnValues(String columnname){
    logger.info("Method getColumnValues called");
    logger.debug("Method getColumnValues for columnname " + columnname + " returned number of values: " + activeDataset.getColumnValues(columnname).length);
	  return activeDataset.getColumnValues(columnname);
  }
  
  /**
   * Method returns the maximum amount of row a DataInstance has.
   * @return a in representing the max rows.
   */
  public int getMaxRows(){
    logger.info("Method getMaxRows called");
    logger.debug("Method getMaxRows returned: " + activeDataset.getMaxRows());
	  return activeDataset.getMaxRows();
  }
  
  /**
   * Method that returns the active DataSetInstance values.
   * @return a double array of String values.
   
  public String[][] getDataSetValues() {
          if (activeDataset != null){
            logger.info("Dataset values returned"); 
                  return activeDataset.getValues();
          } else {
            logger.info("Dataset values requested from not existing dataset (Dataset null) ");
                  return null;
          }
  }
  
  /**
   * Method that returns a list of the columnnames used by the active DataSetInstance.
   * @return an Array of Strings.
   */
  public String[] getDataSetColumnnames() {
    logger.info("Method getDataSetColumnnames called");
          if(activeDataset != null){
        	  String[] columnnames = activeDataset.getColumnnames();
                  logger.debug("Method getDataSetColumnnames returned: " + arrayOfStringsToString(columnnames)); 
              return columnnames;
          }else{
            logger.debug("Method getDataSetColumnnames (DataSet null) returned: " + null);
              return null;
          }
  }
  
  /**
   * Method returns the name of the active DataSet.
   * @return String name of active DataSet.
   */
  public String getDataSetName(){
    logger.info("Method getDataSetName called");
    logger.debug("Method getDataSetName returned: " + activeDataset.getDataSetName());
	  return activeDataset.getDataSetName();
  }
  
  /**
   * Getter for the active DataSet column names by Type.
   * @param type the type of the columns to return.
   * @return a String array containing the column names.
   */
  public String[] getDataSetColumnnames(Column.Type type) {
    logger.info("Method getDataSetColumnnames called");
      if(activeDataset != null){
    	  String[] columnnames = activeDataset.getColumnnames(type);
          logger.debug("Method getDataSetColumnnames with column type " + type.toString() + " returned: " + arrayOfStringsToString(columnnames)); 
          return columnnames;
      }else{
    	  logger.debug("Method getDataSetColumnnames (DataSet null) with column type " + type.toString() + " returned: " + null); 
          return null;
      }
  }

  /**
   * Getter for the active DataSet column names by Type NUM + INT.
   * @param type the type of the columns to return.
   * @return a String array containing the column names.
   */
  public String[] getDataSetNumColumnnames(Column.Type type) {
    logger.info("Method getDataSetNumColumnnames called");
      if(activeDataset != null){
    	  String[] NumColumnnames = activeDataset.getColumnnames(type);
    	  String[] IntColumnnames = activeDataset.getColumnnames(Type.INTEGER);
		  int aLen = NumColumnnames.length;
    	  int bLen = IntColumnnames.length;

    	  String[] Columnnames = new String[aLen+bLen];

 		  System.arraycopy(NumColumnnames, 0, Columnnames, 0, aLen);
    	  System.arraycopy(IntColumnnames, 0, Columnnames, aLen, bLen);
          logger.info("Dataset columnnames with column type " + type.toString() + "returned"); 
          return Columnnames;
      }else{
    	  logger.info("Dataset columnnames with column type requested from not existing dataset (Dataset null)"); 
          return null;
      }
  }

  /**
   * Getter for the Median values of a column.
   * @param columnname the column who's median values will be returned.
   * @return a String representing the median value.
   */
  public String getColumnMedian(String columnname){
    logger.info("Method getColumnMedian called");
    logger.debug("Method getColumnMedian for " + columnname + " returned " + activeDataset.getMedian(columnname));
	return activeDataset.getMedian(columnname);
  }
  
  /**
   * Getter for the average values of a column.
   * @param columnname the column who's average values will be returned.
   * @return a double representing the average values or a null if not able to calculate.
   */
  public double getColumnAverage(String columnname){
    logger.info("Method getColumnAverage called");
	  logger.debug("Method getColumnAverage " + columnname + " returned" + activeDataset.getAverage(columnname));
	  return activeDataset.getAverage(columnname);
  }
  
  /**
   * Getter for the datatype of a column
   * @param columnname the column who's datatype will be returned.
   * @return a String representing the datatype of the column.
   */
  public String getColumnType(String columnname){
    logger.info("Method getColumnType called");
	  logger.debug("Method getColumnType for " + columnname + " returned " + activeDataset.getColumnType(columnname));
	  return activeDataset.getColumnType(columnname);
  }
  
  /**
   * Getter for the types of the columns
   * @return a String array of the types of the columns.
   */
  public String[] getTypes(){
	  return activeDataset.getTypes();
  }
  
  /**
   * Method returns a list of factors used by a column.
   * @param colunm the name of the column which factors must be returned.
   * @return a list of factors used in the column.
   */
  public String[] getDatasetFactors(String column) {
    logger.info("Method getDatasetFactors called");
    String[] factors = activeDataset.getFactors(column);
    if(factors != null){
      logger.debug("Method getDatasetFactors for " + column + " returned " + arrayOfStringsToString(factors));
        return factors;
    }else {
      logger.debug("Method getDatasetFactors for " + column + " (factors null) returned empty String ");
        return new String[0];
        }  
  }
  
  /**
   * Getter for the DataSet Attribut.
   * @return a DataSet object.
   
  public DataSet getActiveDataSet() {
    logger.info("Active dataset returned");
          return activeDataset;

   }
        
   /**
   * Getter for the active DataSetInstance
   * @return a DataSetInstance object
   
  public DataSetInstance getDataSet() {
         if(activeDataset != null){
           logger.info("Dataset instance of active dataset returned");
           return activeDataset.getDatasetInstance();
           }else { 
             logger.info("Dataset instance of not existing active dataset requested (Dataset null)");
             return null;
             }
        }
        
  /*
   * 
   * Editing / Cleaning DataSet Methods
   * 
   */
  
  /**
   * Method for saving dataset to undoStack
   */
  public void snapshot(){
    logger.info("Method snapshot called");
	  this.activeDataset.snapShot();
	  logger.debug("activeDatasetInstance of undoStack after calling method snapshot " + activeDataset.getDataSetInstanceName());
  }
  
  /**
   * Sets the na values.
   * @param columnname the name of the column where the NA-values will be replaced.
   * @param     na the value by which the NA-values will be substituted.
   * @throws MultimorbidityException 
   */
  public void setNa(String columnname, String na) throws MultimorbidityException {
	  setNa(columnname, na, null); 
  }
  
  /**
   * Sets the na values.
   * @param columnname the name of the column where the NA-values will be replaced.
   * @param     na the value by which the NA-values will be substituted.
   * @throws MultimorbidityException 
   */
  public void setNa(String columnname, String na, String type) throws MultimorbidityException {
    logger.info("method setNa called");

    String expr ="";
    if(type.equals("date")||type.equals("Date")){
    	expr = RVAR+"$" + columnname + "[is.na(MyData$" + columnname + ")]<-" + na ;
    }else{
    	expr = RVAR+"$" + columnname + "[is.na(MyData$" + columnname + ")]<-" + "'" + na + "'";
    } 
    rengine.eval(expr);
    
    loadRData();
    
    logger.debug("Factors after calling setNa for column " + columnname + " set to " + na + " are " + arrayOfStringsToString(activeDataset.getDatasetInstance().getFactors(columnname)));
  }

  /**
   * Omits the na values in the entire DataSet.
   * @throws MultimorbidityException 
   */
  public void omitNa() throws MultimorbidityException {
    logger.info("Method omitNa called");

      rengine.eval(RVAR+ "<-na.omit("+RVAR+")");
      rengine.eval(RVAR+ "<- droplevels("+RVAR+")");   
      loadRData();
      //Debug logging
      ArrayList<String> allFactors = new ArrayList<String>();
      for (String column: activeDataset.getDatasetInstance().getColumnnames()) {
        for (String factor: activeDataset.getDatasetInstance().getFactors(column)) {
          allFactors.add(factor);
        }
      }
      logger.debug("Factors after calling omitNa: " + arrayOfStringsToString((String[]) allFactors.toArray()));

  }

          /**
           * Sets the na values to mean.
           *
          //Method not yet used
          public void naToMean() {
            rengine.eval("MyData1 <- impute(MyData, median)");
            REXP rexp = rengine.eval("MyData");
            DataSetInstance tdataset = Converter.toDataSet(rexp);
            //System.out.println(rexp);
            //Get levels
            // Update DataSet
            tdataset = addFactors(tdataset);
            activeDataset.updateDataSet(tdataset);
          }
                */
          /**
           * Sets the na values to median.
           *
          //Method not yet used
          public void naToMedian() {
            //rengine.eval("MyData <- impute(MyData, fun = median)");
            String[] cols = activeDataset.getDataset().getColumnnames();
            String temp = "";
            int icount = 0;
            for (String s: cols) {
              REXP rex = rengine.eval("c" + icount +  "<- impute(MyData$" + s + ")");
              temp = temp + s + "c" + icount + ",";
              //System.out.println(rex);
            }
            rengine.eval("MyData <-data.frame(" + temp + ")");
            
            REXP rexp = rengine.eval("MyData");
            //System.out.println(rexp);
            DataSetInstance tdataset = Converter.toDataSet(rexp);
            
            // Update DataSet
            tdataset = addFactors(tdataset);
            activeDataset.updateDataSet(tdataset);
          }
          */
  /**
   * DELETE OR EDIT!!!!!!
   * Helper function which returns the maximun amount of Factors a Column has.
   * @return an integer representing maximun occurring amount of factors.
   
  public int maxFactor() {
          return 5;
  }
      
  /**
   * Edits columnname.
   * @param name Old columnname.
   * @param newName New columnname.
   * @return Boolean whether method was completed succesfully.
   * @throws MultimorbidityException 
   */
  public Boolean editColumnname(String name, String newName) throws MultimorbidityException {
    logger.info("Method editColumnname called");
	  if(!name.equals(newName)){
		  if(checkInput(newName)){
			  throw new MultimorbidityException("Name must be Alphanumeric"); 
		  }
		  String[] cols = getDataSetColumnnames();
		  if (newName.isEmpty() || Arrays.asList(cols).contains(newName)) {
			  return false;
		  } 
		  String expr = "colnames(" + RVAR + ")[colnames(" + RVAR + ") =='" + name 
                + "'] <-'" + newName + "'";
		  rengine.eval(expr);
		  
		  this.loadRData();
		  return true;
	  }
	  logger.debug("Columnnames after calling method editColumnname with name " + name + " and newName " + newName + ": " + arrayOfStringsToString(activeDataset.getColumnnames()));
	return null;
  }
  
  /**
   * Private Method for checking if String is valid input
   * @param input
   * @return
   */
  private Boolean checkInput(String input){
	  
	  for(char c: input.toCharArray()){
		  if((Character.isWhitespace(c)) ){
			  return true;
		  }
	  }
	  return false;
  }
  
  /**
   * Deletes a column.
   * @param columnname name of the column to be deleted
   * @return Boolean whether method was completed succesfully.
   * @throws MultimorbidityException 
   */
  public Boolean deleteColumn(String columnname) throws MultimorbidityException{
    logger.info("Method deleteColumn called");
	  String[] cols = getDataSetColumnnames();
      if (columnname == null || !Arrays.asList(cols).contains(columnname)) {
        return false;
      } 
          rengine.eval(RVAR + "$" + columnname + "<- NULL");
          /*
          REXP rexp = rengine.eval(RVAR);
          activeDataset.updateDataSet(rexp);  
          this.setChanged();
          this.notifyObservers();

          */
          loadRData();

          logger.debug("Columnnames after calling method deleteColumnname for column " + columnname + ": " + arrayOfStringsToString(activeDataset.getColumnnames()));
          return true;
  }
          
  /**
   * Method rename a Factor
   * @param columnname the name of the column containing factor
   * @param oldname the original name of the factor.
   * @param newname the new name of the factor.
   * @return Boolean whether method was completed succesfully.
   * @throws MultimorbidityException 
   */
  public Boolean renameFactor(String columnname, String oldname,String newname) throws MultimorbidityException {
    logger.info("Method renameFactor called");
	  if(!oldname.equals(newname)){
		  String[] columnnames = getDataSetColumnnames();
		  if(!Arrays.asList(columnnames).contains(columnname)){
			  return false;  
		  }
		  if(checkInput(newname)){
			  throw new MultimorbidityException("Name must be Alphanumeric"); 
		  }
		 String[] oldfactors = activeDataset.getFactors(columnname);
         if(Arrays.asList(oldfactors).contains(oldname)){
            int index = Arrays.asList(oldfactors).indexOf(oldname);
            oldfactors[index] = newname;
                    
            String expr = "levels("+RVAR+"$"+columnname+")[levels("+RVAR+"$"+columnname+")=='"+oldname+"']<-'"+newname+"'";
            rengine.eval(expr);
            /*
            REXP rexp = rengine.eval(RVAR);
            activeDataset.updateDataSet(rexp);
            this.setChanged();
            this.notifyObservers();
            */
            loadRData();
          }else{
        	  throw new MultimorbidityException("Factor names must be unique");
          }
	  }
	  logger.debug("Factors of columnname " + columnname + " after calling method renameFactors: " + arrayOfStringsToString(activeDataset.getDatasetInstance().getFactors(columnname)));
	  return true;
  }
          
  /**
   * Converts factors to NA-values.
   * @param columnname name of the column in which the factor will be deleted 
   * @param factor the factor to delete.
   * @return Boolean whether method was completed succesfully.
   * @throws MultimorbidityException 
   */
  public Boolean deleteFactor(String columnname, String factor) throws MultimorbidityException {
	  
    logger.info("Method deleteFactor called");
	  String[] factors = activeDataset.getFactors(columnname);
      if(!Arrays.asList(factors).contains(factor)){
    	  return false;
      }  
      
	  rengine.eval(RVAR+"$" + columnname + "["+RVAR+"$" + columnname + "==\"" + factor + "\"]<-NA");
      rengine.eval(RVAR+"$" + columnname + "  <- droplevels("+RVAR+"$" + columnname + ")");
      
      loadRData();
      logger.debug("Factors of columnname " + columnname + " after calling method deleteFactor for " + factor + ": " + arrayOfStringsToString(activeDataset.getDatasetInstance().getFactors(columnname)));
      return true;
  }
          
        
  /*
   * 
   * Data Persistence
   * 
   */
  
  
  // only setup for csv files!
  /**
   * Method for loading a Dataset from a csv file.
   * Reads a csv file in R.
   * @param file the File to load
   * @return Boolean whether method was completed succesfully.
   * @throws MultimorbidityException. 
   */
  public Boolean importDataSet(File file, String sep, boolean head) throws MultimorbidityException { 
    logger.info("Method importDataSet called");
	if(checkInput(file.getName())){
		return false;
	}
	String tfilepath = file.getAbsolutePath().replace('\\', '/'); // get filepath as String
	String filepath = this.addSlashes(tfilepath);  
	  
    REXP rexp = rengine.eval(RVAR + "<- read.csv(file=\"" + filepath + "\", header=" 
        + (Boolean.toString(head)).toUpperCase() + ", sep= \"" + sep + "\")"); // get REXP
 
    String name = file.getName();
    
    activeDataset = new DataSet(rexp, name);
    sessions.add(activeDataset);
    addMetaData();
    
    this.setChanged();
    this.notifyObservers();
    logger.debug("File " + file.getAbsolutePath() + " imported as DataSet with name " + activeDataset.getDataSetInstanceName());
    return true;
  }
  
  private void addMetaData(){
	  String[] columns = activeDataset.getColumnnames();
	  
	  for(String s: columns){
		  switch (activeDataset.getColumnType(s)) {
		  case "character":
		  case "logical":  
			    activeDataset.setMedian(s,median(s));
		        break;
		  case "factor":	  
		        activeDataset.setMedian(s,medianFac(s));
		        break;
		  case "numeric":
		  case "integer": 	  
			    activeDataset.setMedian(s,median(s));
			    activeDataset.setAverage(s, average(s));
			    break;
		}
	  }
  }
  
  //Helper methodes
  private String median(String column){
	  String expr = "names(sort(table("+RVAR+"$"+column+"), decreasing=T)[1])";
	  REXP rexp = rengine.eval(expr);
	  return rexp.asString();
  }
  
  //Helper methodes
  private String medianFac(String column){
	  String expr = "names(sort(summary("+RVAR+"$"+column+"), decreasing=T)[1])";
	  REXP rexp = rengine.eval(expr);
	  return rexp.asString();
  }
  
  //Helper methodes 
  private double average(String column){
	  String expr = "mean("+RVAR+"$"+column+")";
	  REXP rexp = rengine.eval(expr);
	  double average = rexp.asDouble();
	  return average;
  }
  
  //Helper method to check if Slashes are correct
  private String addSlashes(String paramString) {
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
  /**
   * Method 2 to import a DataSet. NOT WORKING
   * Reads a xlsx file in R
   * @param file the file to be loaded
   * @param head true if there is a header, else false
   
  public void importDataSet(File file, boolean head) {
    String tfilepath = file.getAbsolutePath().replace('\\', '/'); // get filepath as String
    String filepath = this.addSlashes(tfilepath);

    rengine.eval("wb <- loadWorkbook(\"C:/data.xlsx\")");
    rengine.eval(RVAR + " <- readWorksheet(wb, sheet = 1)"); 
    
    REXP rexp = rengine.eval("MyData");
    //System.out.println(rexp);
    
    this.setChanged();
    this.notifyObservers();
  }

   *
   * Method 3 to load a DataSet. NOT WORKING
   * Reads a sheet of a xlsx file in R
   * @param file the file to be loaded
   * @param head true is there is a header, else false
   * @param sheet the sheet that should be loaded
   *
  public void importDataSet(File file, boolean head, String sheet) {
    String tfilepath = file.getAbsolutePath().replace('\\', '/'); // get filepath as String
    String filepath = this.addSlashes(tfilepath);
            
    REXP rexp = rengine.eval(RVAR + "<- read.xlsx2(file=\"" 
        + filepath + "\"," + sheet + "\")"); // get REXP
    
    DataSetInstance dataseti = Converter.toDataSet(rexp); // Convert rexp to a dataset
    dataseti = addFactors(dataseti);
            
    setDataSet(dataseti);
    this.setChanged();
    this.notifyObservers();
    //dsController.updateDataSet(tdataset);
  }
*/
  
  /**
   * Needs to be EDITED !!!!!
   * Method 1 to load a DataSet via data layer.
   * @param file the file to be loaded
   */
  public void loadDataSet(File file) { 
    logger.info("Method loadDataSet called");
        this.activeDataset = new DataSet();
        activeDataset.loadDataSet(file);
        this.syncdata();
        this.setChanged();
        this.notifyObservers();
        logger.debug("File " + file.getAbsolutePath() + " loaded as DataSetInstance with the name " + activeDataset.getDataSetInstanceName());
  }
  
  /**
   * Method for exporting a DataSet to a csv file.
   * @param location the location where to save the file.
   * @param filename the name of the file to save.
   */
  public void exportDataSet(String location, String filename) {
    logger.info("Method exportDataset called");
    String location2 = location.replace('\\', '/');
    String expr = "write.table(" + RVAR + ", \"" + location2 + "/" + filename + ".csv\", sep=\",\",row.names = F)";
    rengine.eval(expr);
    logger.debug("File " + filename + " exported as csv to location " + location);
  }
  
  /**
   * Method for saving the datasetInstance.
   * @param name of file.
   */
  public void saveDataSet(String name){
    logger.info("Method saveDataSet called");
    activeDataset.saveDataSet(name);
    logger.debug("Dataset " + name + " saved as after calling method saveDataSet: " + activeDataset.getLastSavedFile().getAbsolutePath());
  }
  
  /**
   * Method used for synching the DataSet in Java with the Data in R
   * Only works with Factorial Data
   */
  private void syncdata() { 
	  
    //String[][] values = activeDataset.getValues();
    //int rows = values.length;
    
    String[] columnnames = activeDataset.getColumnnames();
    int columns = columnnames.length;
    for (int i = 0; i < columns; i++) {
      StringBuilder expr = new StringBuilder();
      String[] values = activeDataset.getColumnValues(columnnames[i]);
      expr.append(columnnames[i] + "<- c(");
      
      String type = activeDataset.getColumnType(columnnames[i]);
      //System.out.println(type);
      for (int j = 0; j < values.length ; j++) {
          if (j == 0) {
            if(values[j] == null||values[j].isEmpty()){
          	  expr.append("NA");
            }else{
            	if(type.equals("factor")||type.equals("character")){
            		expr.append("'" + values[j] + "'");
            	}else if(type.equals("numeric")||type.equals("integer")){
            		expr.append(values[j]);
            	}else if(type.equals("logical")){
            		if(values[j].equals("-2147483648")){
            			expr.append("NA");
            		}else{
            			expr.append(values[j]);
            		}
            	}else if(type.equals("Date")){
            		//System.out.println("check");
            		expr.append(values[j]);
            	}
          	  }
          } else {
          	if(values[j] == null||values[j].isEmpty()){
          		expr.append(",NA");
          	}else{
          		if(type.equals("factor")||type.equals("character")){
            		expr.append(",'" + values[j] + "'");
            	}else if(type.equals("numeric")||type.equals("integer")){
            		expr.append(","+values[j]);
            	}else if(type.equals("logical")){
            		if(values[j].equals("-2147483648")){
            			expr.append("NA");
            		}else{
            			expr.append(","+values[j]);
            		}
            	}else if(type.equals("Date")){
            		//System.out.println("check");
            		expr.append(","+values[j]);
            	}
          	}
          
        }
      }
      expr.append(")");
      rengine.eval(expr.toString());
      
    }
    
    
    StringBuilder expr = new StringBuilder();
    expr.append(RVAR+" <- data.frame(");
    for (int i = 0; i < columns; i++) {
      if (i == 0) {
        expr.append(columnnames[i]);
      } else {
        expr.append("," + columnnames[i]);
      }
    }
    expr.append(")");
    rengine.eval(expr.toString());
    
    for(String s: columnnames){
    	String type = activeDataset.getColumnType(s);
    	String expr1 = "";
    	if(type.equals("Date")){
    		expr1 = RVAR+"$"+s + "<-as.Date("+RVAR+"$"+s+", origin = \"1970-01-01\")";
    	}else{
    		expr1 = RVAR+"$"+s + "<-as."+type+"("+RVAR+"$"+s+")";
    	}
    			
    	
    	rengine.eval(expr1);
    }
  }
  
  
  /*
   * 
   * Undo Functionality
   * 
   */
  
  /**
   * Method to undo the previous save action.
   */
  public void undo() {
    logger.info("Method undo called");
    activeDataset.undo();
    this.syncdata();
    this.setChanged();
    this.notifyObservers();
    logger.debug("Name of dataset instance after calling method undo: " 
        + activeDataset.getDatasetInstance().getName());
  }
  
  /**
   * Method to redo the previous save action.
   */
  public void redo() {
    logger.info("Method redo called");
    activeDataset.redo();
    this.syncdata();
    this.setChanged();
    this.notifyObservers();
    logger.debug("Name of dataset instance after calling method redo: " 
        + activeDataset.getDatasetInstance().getName());
  }
  
  /**
   * Tests if can undo.
   * @return true if can undo, else false
   */
  public Boolean canUndo() {
    logger.info("Method canUndo called");
    Boolean canUndo = activeDataset.canUndo();
    logger.debug("Method canUndo returned: " + canUndo);
    return canUndo;
  }
  
  /**
   * Tests if can redo.
   * @return true if can redo, else false
   */
  public Boolean canRedo() {
    logger.info("Method canRedo called");
    Boolean canRedo = activeDataset.canRedo();
    logger.debug("Method canRedo returned: " + canRedo);
    return activeDataset.canRedo();
  }
  
  /*
   * 
   * Tool Controles
   * 
   */

  /**
   * Method 1 to execute the tool.
   * @param key the tool name
   * @return the Result of the calculation
   */
  public Result executeTool(String key) {
        Tool tool = tools.get(key);
    Result res = tool.execute();
    activeDataset.getDatasetInstance().addResult(res);
    logger.debug("Method executeTool for key " + key + " returned: " + res.toString()); //Temporary only String represenation of object
    return res;
  }

  /**
   * Method 2 to execute the tool.
   * @param key the tool name
   * @param object the source object
   * @return the Result of the calculation
   */
  public Result executeTool(String key, ToolArgument object) {
    Result res = tools.get(key).execute(object);
    if(res !=null){
        activeDataset.getDatasetInstance().addResult(res);}
    logger.debug("Method executeTool for key " + key + " and object " + object.toString() + " returned: " + res.toString()); //Temporary only String represenation of object
    return res;
  }
  
  /**
   * Method whice executes the CountTool.
   * @param arg a list of columns to be grouped
   * @param arg1 the name of the column by which the column will be grouped.
   */
  public void executeCountTool(String[] arg, String arg1){
    logger.info("Method executeCountTool called");
          this.executeTool(CountTool.TOOLNAME, new CountArgument(this.activeDataset, arg, arg1));
          logger.debug("Method executeCountTool for arg " + arrayOfStringsToString(arg) + " and arg1 " + arg1); //Temporary only arguments are shown
  }

  /*
  *
  * Summary functions
  *      
  */
 
 /**
  * Method for getting the Frequency summary about a given variable from the loaded dataset
   * @param columnname
   * @param level
   * @return the frequency of the given columnname and the corresponding level
   */
  public String getFrequency(String columnname, String level) {
        
            // See frequencies.R 
            String request = "as.character(table("+RVAR+"$" + columnname + ")[" + "'" + level + "'])";  

            REXP rexp = rengine.eval(request);
            if(rexp == null){
              logger.info("Frequency request for level " + level + " of column " + columnname + " failed");
                return "";
            }
            String frequency = rexp.asString();
            logger.debug("Method getFrequency for level " + level + " of columnname " + columnname + " returned: " + frequency);
            return frequency;

  }
  
  /**
  * Method for getting the Percentage summary about a given variable from the loaded dataset
   * @param columnname
   * @param level
   * @return the percentage of the given columnname and the corresponding level
   */
  public String getPercentage(String columnname, String level) {
            // See frequencies.R 
            rengine.eval("mytable <- table("+RVAR+"$" + columnname + ")");              
            String request = "as.character(round(prop.table(mytable)[" + "'" + level + "'],4))";  
            
            REXP rexp = rengine.eval(request);
            if(rexp == null){
              logger.debug("Method getPercentage for level " + level + " of column " + columnname + " returned an empty String");
                return "";
            }
            logger.debug("Method getPercentage for level " + level + " of column " + columnname + " returned: " + rexp.asString());
            return rexp.asString();
  }
  
  /**
   * Method for ploting a barcharts horizontally(H) 
   * based on the summary of a specific variable from the loaded dataset 
   * @param columnname from the loaded dataset 
   */
  public void barChartHoriz(String columnname) {
    logger.info("Method barChart called");
         
            // See charts.R 
      rengine.eval("df.freq  <- table(MyData$"  + columnname + ")");                
      rengine.eval("rowcount <- length(MyData$" + columnname + ")");                
      String request = "barplot(df.freq[order(df.freq)], horiz=T,border = NA,xlim = c(0,rowcount),"
                        + "main = '" + columnname + " Frequency/Level'"
                        + ",xlab = '" + columnname + " Type'"
                        + ",las=1)";  

                        rengine.eval(request);  
                        logger.debug("Method barChart for columnname " + columnname + " evalutated request: " + request);
  }

  /**
   * Method for combining multiple plots/graphs  
   * Frequency graphs based on loaded dataset 
   */
  public void combineGraphs() {
         
	  // 4 figures arranged in 2 rows and 2 columns
	  rengine.eval("attach(MyData)");
	  rengine.eval("somePDFPath='C:/temp/java.png'");
	  rengine.eval("png(somePDFPath)");
	  rengine.eval("rpar(mfrow=c(2,2))");
	  rengine.eval("plot(wt,mpg, main='Scatterplot of wt vs. mpg')");
	  rengine.eval("plot(wt,disp, main='Scatterplot of wt vs disp')");
	  rengine.eval("hist(wt, main='Histogram of wt')");
	  rengine.eval("boxplot(wt, main='Boxplot of wt')");  

	  rengine.eval("dev.copy(png,'myfile.png',width=8,height=6,units='in',res=100)");
	  rengine.eval("dev.off()");
	  
  }

  /**
   * Method for ploting a barcharts Vertivally (V) 
   * based on the summary of a specific variable from the loaded dataset 
   * @param columnname from the loaded dataset 
   */
  public void barChartVert(String columnname) {
         
            // See charts.R 
	   
            rengine.eval("df.freq  <- table(MyData$"  + columnname + ")");                
            rengine.eval("rowcount <- length(MyData$" + columnname + ")");                
            String request = "barplot(df.freq[order(df.freq, decreasing = T)] , horiz=F,border = NA,ylim = c(0,rowcount),"
                        + "main  = '" + columnname + " Frequency / Level'"
                        + ",xlab = '" + columnname + " Type'"
                        + ",las=1)";  

                        rengine.eval(request);                                                                          
  }

  /**
   * Method for ploting a barchart for two relations side by side
   * from the loaded dataset 
   * @param columnname1 and columname2 from the loaded dataset 
   */

  public void barChartGrouped(String columnname1, String columnname2) {

      // See charts.R 
	  // create first contingency table 
	   rengine.eval("ctg_table <- table(MyData$" + columnname1 + ", MyData$" + columnname2 + ")"); 
	   
	   String request = "barplot(ctg_table, beside = T ,"
			+ "legend.text=c('Geen " + columnname1 + "', 'Wel " + columnname1 + "'),"
	   		+ "main = 'Relatie tussen " + columnname1 + " en " + columnname1 + "',"
            + "xlab='" + columnname1 + "', las=1, col=c(2,4))";

		rengine.eval(request);	
  }
  
  /**
   * Method for getting the 2-way frequency table based on two given variables
   * @param columnname1 from the loaded dataset 
   * @param columnname2 from the loaded dataset 
   */

  public void get2wayTable(String columnname1, String columnname2) {
      
            // See frequencies.R   
  }
  
  /**
   * Method for getting the 3-way frequency table based on two given variables
   * @param columnname1 from the loaded dataset 
   * @param columnname2 from the loaded dataset 
   * @param columnname3 from the loaded dataset 
   */
  public void get3wayTable(String columnname1, String columnname2, String columnname3) {
      
            // See frequencies.R 
  }
  
  /*
  *
  * 3.6 Association analysis
  *      
  */
 

  
  /*
  *
  * 3.7 clustering
  *      
  */
	public void CalculateNumberOfClusters(String columnname){
        // See charts.R 

		rengine.eval("old.par <- par(mar = c(0, 0, 0, 0))");
		rengine.eval("par(old.par)");
		rengine.eval("plot('')");
        rengine.eval("mydata <- MyData"); 
        String request0 = "mydata$" +columnname  + " = NULL ";        
        String request1 = "wss <- (nrow(mydata)-1)*sum(apply(mydata,2,var))";
        String request2 = "for (i in 2:15) wss[i] <- sum(kmeans(mydata, centers=i)$withinss)";
        String request3 = "plot(1:15, wss, type='b', xlab='Number of Clusters',ylab='Within groups sum of squares')";
        rengine.eval(request0);
        rengine.eval(request1);
        rengine.eval(request2);
        REXP rexp = rengine.eval(request3);
        if(rexp == null){
            logger.info("Calculation of the number of clusters can not be determined");
        }
        //System.out.println(request0);
        //System.out.println(request1);
        //System.out.println(request2);
        //System.out.println(request3);
	}
	
	public void StartClusterProcess(String clusterColumn, String numbClusters, String Method){
		
		CalculateNumberOfClusters(clusterColumn);
		
		rengine.eval("cdata.features <- mydata");
		rengine.eval("results <- kmeans(cdata.features, " + numbClusters + ")");

		System.out.println("Tool: ");
        System.out.println(numbClusters);

		// # find distance matrix between two cars (32x32 matrix)
		rengine.eval("d <- dist(as.matrix(cdata.features))");  
		rengine.eval("d <- dist(cdata.features[-1])");

		// # apply hirarchical clustering 
		rengine.eval("hc <- hclust(d)");
		REXP rexp = rengine.eval("plot(hc)");   
        if(rexp == null){
            logger.info("Calculation of the number of clusters can not be determined");
        }
    }

	public void StartClusterSummary(String clusterColumn, String numbClusters, String Method){
		
		StartClusterProcess(clusterColumn, numbClusters, Method);

		rengine.eval("attributes(results)");
		// #Summary: Size of each cluster from 150 observations
		rengine.eval("results$size");
		
		// # Summary: conversion/mapping of each observation into a cluster value {1,2,3}
		// rownames(cdata) <- cdata$X
		// rownames(cdata) <- cdata$species

		rengine.eval("results$cluster");
		rengine.eval("plot(results$cluster)");
		rengine.eval("plot(results)");

		REXP rexp = rengine.eval("plot(hc)");   
        if(rexp == null){
            logger.info("StartClusterSummary can not be determined");
        }
    }

  /*
   * 
   * Executing Plot Controlls
   * 
   */
  
  /**
   * TEMP methods for activating plot
   */
  public void countPlot1(){
          CountTool ctool = (CountTool)tools.get(CountTool.TOOLNAME);
          ctool.plot1();
  }
  
  /**
   * TEMP methods for activating plot
   */
  public void countPlot2(){
          CountTool ctool = (CountTool)tools.get(CountTool.TOOLNAME);
          ctool.plot2();
  }
  
  /*
   * 
   * Saving Plot function
   * 
   */
  /**
   * Method saves the last plotted graph to a png file.
   * @param name the name of the file.
   */
  public void savePlot(String name){
    logger.info("Method savePlot called");
          rengine.eval("dev.copy(png,'C:/Users/ivann/Desktop/myplot.png')");
          rengine.eval("dev.off()");
          logger.debug("!!!Method savePlot executed but not yet finalized!!!");
  }
  
  /*
   * 
   * Session Controls
   * 
   */
  /**
   * Method for checking the amount of sessions saved.
   * @return the size of the session list.
   */
  public int sessionSize(){
    logger.info("Method sessionSize called");
    logger.debug("Method sessionSize returned: " + sessions.size());
          return sessions.size();

  }
  
  /**
   * Method for getting session name.
   * @param index the index of the session name.
   * @return a String representing the name of the session.
   */
  public String getSessionName(int index){
    logger.info("Method getSessionName called");
          DataSet dataset = sessions.get(index);
          logger.debug("Method getSessionName returned: " + dataset.getDataSetName());
          return dataset.getDataSetName();
  }
  
  /**
   * Method that sets a session in the sessionhistory as the active DataSet.
   * @param index the index of the session to be make active.
   */
  public void loadSession(int index){
    logger.info("Method loadSession called");
          DataSet dataset = sessions.remove(index);
          sessions.add(dataset);
          activeDataset = dataset;
          syncdata();
          this.setChanged();
          this.notifyObservers();
          logger.debug("Name of active dataset after method loadSession with index " + index + " was called: " + activeDataset.getDataSetName());
  }
  
  /**
   * Method that returns the sessionHistory.
   * @return the session history.
   */
  public SessionHistory getSessionHistory(){
    logger.info("Method getSessionHistory called");
    logger.debug("Method getSessionHistory returned: " + sessions.toString() + " with size: " + sessions.size());
          return sessions;
  }
  
  /**
   * Method for casting a Column to a different datatype.
   * @param columnname the name of the column to cast
   * @param type the new datatype of the column
   * @return a boolean whether cast was succesfull
   * @throws MultimorbidityException
   */
  public Boolean castColumn(String columnname, String type) throws MultimorbidityException{
	 
	 // the strings date and Date are excepted
	 if(type.equals("date")){
		 type = "Date";
	 }
	 
	 if(this.activeDataset.getType(columnname).equals(type)){
		 return false;
	 }
	 

    logger.info("Method castColumn called");

	 String[] cols = getDataSetColumnnames();
	 if (columnname.isEmpty() || !Arrays.asList(cols).contains(columnname)) {
	     logger.debug("Method castColumn for column " + columnname + " casted to type " + type + " returned " + false);
		 return false;
	 }
	 String[] types = activeDataset.getColumnTypes();
	 if (type.isEmpty() || !Arrays.asList(types).contains(type)) {
	     logger.debug("Method castColumn for column " + columnname + " casted to type " + type + " returned " + false);
		 return false;
	 }
	 if(type.equals("date")||type.equals("Date")){
		 type = "Date";
		 
	 }
	 String expr = RVAR+"$"+columnname+"<- as."+type+"("+ RVAR+"$"+columnname+")";
	 rengine.eval(expr);
	 
	 loadRData();
	 
	 logger.debug("Method castColumn for column " + columnname + " casted to type " + type + " returned " + true);
	 return true;
  }

  
  /**
   * Method zou moeten werken, maar werkt niet!!!
   * @param columnname
   * @throws MultimorbidityException
   */
  public void convertFactorsToColumns(String columnname) throws MultimorbidityException{
	  /*
	  http://seananderson.ca/2013/10/19/reshape.html
	  MyData<- data.frame(MyData, value=TRUE)
      MyData <- dcast(MyData,patient + datum + leeftijd ~ icpc)
	  */
	  //rengine.eval("install.packages('reshape2')");
	  rengine.eval("library(reshape2)");
	  
	  //rengine.eval("source('C:\\Users\\ivann\\Desktop\\cast.r')");
	  
	  
	  String expr = RVAR+"<- data.frame("+RVAR+", value=TRUE)";
	  rengine.eval(expr);
	  String[] columns = this.activeDataset.getColumnnames();
	  StringBuilder restcolumns1 = new StringBuilder();
	  Boolean flag = false;
	  for(int i =0; i<columns.length;i++){
		  if(!columns[i].equals(columnname)){
			  if(!flag){
				  restcolumns1.append(columns[i]);
				  flag = true;
			  }else{
				  restcolumns1.append(" + "+columns[i]);
			  }
		  }
	  }
	  
	  
	  String timecolumn = "'"+columnname+"'";
	  
	  //String expr2 = RVAR+"<-reshape("+RVAR+", idvar=c("+restcolumns1.toString()+"), timevar="+timecolumn+", direction='wide')";
	  //String expr2 = RVAR+ "<- dcast("+ RVAR+ ","+restcolumns1.toString() +"~"+columnname +", value.var= 'value')";
	  String expr2 = RVAR+ "<- dcast("+ RVAR+ ","+restcolumns1.toString() +"~"+columnname +")";
	  System.out.println(expr2);
	  REXP rexp4 =rengine.eval(expr2);
	  System.out.println(rexp4);
	  /*
	  REXP rexp = rengine.eval(RVAR);
	  activeDataset.updateDataSet(rexp);
	  this.setChanged();
	  this.notifyObservers();
	  */
	  loadRData();
	  /*
	  ArrayList<String> temp = new ArrayList<String>();
	  String[] newColumns = this.activeDataset.getColumnnames();
	  for(String s: newColumns){
		  if(!Arrays.asList(columns).contains(s)){
			  temp.add(s);
		  }
	  }
	  for(String s1: temp){
		  setNa(s1, "FALSE","Date");
	  }
	 */
	  
  }
  
  /**
   * Helper Method
   * @throws MultimorbidityException
   */
  private void loadRData() throws MultimorbidityException{
	  
	  REXP rexp = rengine.eval(RVAR);
      activeDataset.updateDataSet(rexp);  
      //check types
      String[] columns = activeDataset.getColumnnames();
      String[] types = new String[columns.length]; 
      for(int i = 0; i<columns.length; i++){
    	  REXP rexp1 = rengine.eval("class("+RVAR+ "$"+columns[i]+")");
    	  types[i] = rexp1.asString();
      }
      for(int j = 0; j<columns.length;j++){
    	  activeDataset.setColumnType(columns[j], types[j]);
      }
      //check for NA values
      Boolean[] nas = new Boolean[columns.length]; 
      for(int i = 0; i<columns.length; i++){
    	  String expr = "which (is.na("+RVAR+ "$"+columns[i]+"))";
    	  REXP rexp1 = rengine.eval(expr);
    	  if(rexp1.asIntArray().length>0){
    		  nas[i]= true;
    	  }else{
    		  nas[i] = false;
    	  }
      }
      for(int j = 0; j<columns.length;j++){
    	  activeDataset.setNas(columns[j], nas[j]);
      }
      
      addMetaData();
      
      this.setChanged();
      this.notifyObservers();
  }
  
  /**
   * Check whether a Column contains NA's.
   * @param columnname the name of the column to check.
   * @return a boolean result.
   */
  public Boolean hasNa(String columnname){
	  return this.activeDataset.hasNas(columnname);
  }

  
  /**
   * Getter of the Rengine object.
   * Method necessary for test purposes
   * @return the Rengine object
   */
  public Rengine getRengine() {
    return rengine;
  }
  
  /**
   * Confirmation of connection with R.
   * Method necessary for test purposes
   * @return true if connected with R, else false
   */
  public boolean isConnectedWithR() {
    if (getRengine() != null) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Getter of the active DataSet.
   * Method necessary for test purposes
   * @return the active DataSet
   */
  public DataSet getActiveDataSet() {
    return activeDataset;
  }
  
  /**
   * Converts array of Strings to one String.
   * Method for logging purposes
   * @param arrayOfStrings the array to be converted
   * @return the String representation of the array
   */
  private String arrayOfStringsToString(String[] arrayOfStrings) {
    if(arrayOfStrings.length < 1){
    	return "";
    }
	  
	String arrayToString = "{";
    String lastString = arrayOfStrings[arrayOfStrings.length - 1];
    for (String str: arrayOfStrings) {
      if (!lastString.equals(str)) {
        arrayToString.concat(str + ", ");
      } else {
        arrayToString.concat(str + ".");
      }
    }
    arrayToString.concat("}");
    
    return arrayToString;
  }
  
  public void cut(String columnname, int[] bounds, String[] labels) throws MultimorbidityException{
	  String expr = RVAR+"$"+columnname+" <- cut("+ RVAR+ "$"+columnname+", ";
	  expr = expr + "breaks = c(-Inf,";
	  for(int i: bounds){
		  expr = expr+i +",";
	  }
	  expr = expr + "Inf),labels = c(";
	  for(int i = 0; i<labels.length; i++){
		  if(i ==0){
			  expr = expr +"'"+labels[i]+"'"; 
		  }else{
			  expr = expr +",'"+labels[i]+"'"; 
		  }
	  }
      
	  expr = expr+"),right = FALSE)";
      
	  rengine.eval(expr);
	  System.out.println(expr);
	  loadRData();
  }
}