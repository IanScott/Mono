package domain;

import data.DataSetMapper;
import domain.Column.Type;
import domain.DataSetInstance;
import domain.Result;
import domain.SizedStack;
import util.MultimorbidityException;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RFactor;
import org.rosuda.JRI.RVector;

/**
 * Class that represents a DataSet
 * The actuel data is saved in a DataSetInstance.
 * @author Ian van Nieuwkoop
 * @version 0.5
 */
public class DataSet implements Serializable{
  
  private static final long serialVersionUID = -1558181455145977463L;
  private DataSetInstance activeDatasetInstance;
  private Stack<DataSetInstance> undoStack;
  private Stack<DataSetInstance> redoStack;
  private DataSetMapper dsMapper;
  
  /**
   * Contructor, creates an DataSet without a datasetInstance.
   */
  public DataSet(){
	  this.undoStack = new SizedStack<DataSetInstance>(7);
	  this.redoStack = new SizedStack<DataSetInstance>(7);
	  this.dsMapper = new DataSetMapper();
  }
  
  /**
   * Constructor, creates a DataSet with a dataSetInstance.
   * @param rdata an REXP object representing a R dataframe.
   * @param factors a list of factors to be added as metadata to the dataSetInstance.
   * @param name the name of the DataSet.
   * @throws MultimorbidityException 
   */
  public DataSet(REXP rdata,String name) throws MultimorbidityException{
	  this();
	  DataSetInstance tdataset = toDataSetInstace(name,rdata);
	  this.activeDatasetInstance = tdataset;
  }
  
  /**
   * Getter of DataSet object.
   * @return DataSet
   */
 public DataSetInstance getDatasetInstance() {
    return activeDatasetInstance;
  }
 
 /**
  * Getter of the name of DataSetInstance.
  * Method used for logging purposes
  * @return the name of the active DataSetInstance
  */
 public String getDataSetInstanceName() {
   return activeDatasetInstance.getName();
 }
  
  /**
   * Updates the DataSet.
   * @param ds DataSetInstance to be updated
   */
  private void updateDataSet(DataSetInstance ds) {
    if (this.activeDatasetInstance != null) {
      undoStack.push(activeDatasetInstance);
   }
    this.activeDatasetInstance = ds;
  }
  
  
  /**
   * Method that loads a REXP representing the Data Set in R into the Memory of the Application.
   * @param rexp a REXP object reprenting the Data Set.
   * @param type
   * @throws MultimorbidityException 
   */
  public void updateDataSet(REXP rexp) throws MultimorbidityException {
	  String name = activeDatasetInstance.getName();
	  String columns[] = activeDatasetInstance.getColumnnames();
	  //HashMap<String,String> types = new HashMap<String,String>();
	  HashMap<String,String> medians = new HashMap<String,String>();
	  HashMap<String,Double> averages= new HashMap<String,Double>();
	  //Add extra Attribute to be transferred from old to new datasetInstance Observation disease etc..
	  for(int i = 0; i<columns.length; i++){
		//types.put(columns[i], activeDatasetInstance.getColumnType(columns[i]));
		medians.put(columns[i], activeDatasetInstance.getMedian(columns[i]));
		averages.put(columns[i], activeDatasetInstance.getAverage(columns[i]));
	  }
	  	  
	  DataSetInstance tdataset = toDataSetInstace(name, rexp);
	  String[] tempcolumns = tdataset.getColumnnames();
	  //Add attributes to new dataset which needed to be saved.
	  for(int i = 0; i<tempcolumns.length; i++){
			
			if(medians.containsKey(tempcolumns[i]) && averages.containsKey(tempcolumns[i])){
			    String median = medians.get(tempcolumns[i]);
			    double average = averages.get(tempcolumns[i]);
			    
			    tdataset.setMedian(tempcolumns[i],median);
			    tdataset.setAverage(tempcolumns[i],average);
			}
		  }
	  
	  this.activeDatasetInstance = tdataset;
	}
  
  /**
   * Adds a datasetInstance to the undoStack
   * @param datasetinstance the instance to add to the undostack
   */
  public void snapShot(){
	  if (this.activeDatasetInstance != null) {
	      undoStack.push(activeDatasetInstance.clone());
	  }
  }
  
  /**
   * Method adds a Result to the Resultlist of the active DataSet.
   * @param res the Result to add.
   */
  public void addResult(Result res) {
    activeDatasetInstance.addResult(res);
  }
  
  /**
   * Method checks if an Undo operation is possible.
   * @return a Boolean based on if an undo is possible.
   */
  public boolean canUndo() {
    return (!undoStack.isEmpty());
  }
  
  /**
   * Method checks if a Redo operation is possible.
   * @return a Boolean based on if a redo is possible.
   */
  public boolean canRedo() {
    return (!redoStack.isEmpty());
  }
  
  /**
   * Undoing of previous action.
   */
  public void undo() {
    if (canUndo()) {
      redoStack.push(this.activeDatasetInstance.clone());
      DataSetInstance undo = undoStack.pop();
      this.activeDatasetInstance = undo;
    }      
  }
  
  /**
   * Redoing of previous action.
   */
  public void redo() {
    if (canRedo()) {
      undoStack.push(this.activeDatasetInstance.clone());
      this.activeDatasetInstance = redoStack.pop();
    }
  }
  
  /**
   * Method returns a 2D String Array containing the values of the active DataSet.
   * @return a 2D String Array of values.
   
  public String[][] getValues(){
	  if(activeDatasetInstance ==null){
		  return new String[1][1];}
	  return activeDatasetInstance.getValues();
  }
  /**
   * Method returns the values of the Column.
   * @param columnname name of the colum who's values will be returned.
   * @return a String array of column values.
   */
  public String[] getColumnValues(String columnname){
	  if(activeDatasetInstance ==null){
		  return new String[0];}
	  return activeDatasetInstance.getColumnValues(columnname);
  }
  
  /**
   * Method returns the maximum amount of row a DataInstance has.
   * @return a in representing the max rows.
   */
  public int getMaxRows(){
	  return activeDatasetInstance.getMaxRows();
  }
  
  /**
   * Getter for the column names.
   * @return a String array containing all the column names.
   */
  public String[] getColumnnames(){
	  if(activeDatasetInstance ==null){
		  return new String[0];}
	  return activeDatasetInstance.getColumnnames();
  }
  
  /**
   * Getter for the column names of a single type.
   * @param type the type of columns to return
   * @return a String array containing all the column names of a certain type.
   */
  public String[] getColumnnames(Column.Type type){
	  String[] columnnames = getColumnnames();
	  ArrayList<String> temp = new ArrayList<String>();
	  for(String s: columnnames){
		  if(activeDatasetInstance.isTypeColumn(s, type)){
			  temp.add(s);
		  }
	  }
	  return temp.toArray(new String[temp.size()]);
  }
  
  /**
   * Getter for the type of a column.
   * @param columnname name of the column who's type will be returned.
   * @return a String representing the column's datatype.
   */
  public String getColumnType(String columnname){
	  return activeDatasetInstance.getColumnType(columnname);
  }
  
  /**
   * Getter for the factors of a column.
   * @param columnname the name of the column who's factors to return.
   * @return a String array of the factors in the column.
   */
  public String[] getFactors(String columnname){
	  if(activeDatasetInstance ==null){
		  return new String[1];}
	  return activeDatasetInstance.getFactors(columnname);
  }
 
  /**
   * Getter for a Tool Result.
   * @param tool the name of the tool who's Result will be returned.
   * @return a Result.
   */
  public Result getResult(String tool){
	  return activeDatasetInstance.getResult(tool);
  }
  
  /**
   * Getter for the active Data Sets name.
   * @return a String representing the active dataset's name.
   */
  public String getDataSetName(){
	  String name = "";
	  if(activeDatasetInstance == null){
		  return name;
	  }
	  
	  return activeDatasetInstance.getName();
  }
  
  private  DataSetInstance toDataSetInstace(String name,REXP data) throws MultimorbidityException {
	  Column[] columns = {}; 
	  Vector<String> vnames;
	  
	  if (data.getType() == 16) {
	    RVector rvec = data.asVector();
	    
	    columns = new Column[rvec.size()];
	    vnames = ((Vector<String>)rvec.getNames());

	    for (int j = 0; j < rvec.size(); j++) {
	      REXP rexp2 = rvec.at(j);
	      String colname = vnames.get(j);
	      Type type = null;
	      String[] values = {};
	      String[] factors = {};
	      if (rexp2.getType() == 127) {
	    	  RFactor rfac = rexp2.asFactor();
	    	  factors = getFactors(rexp2);
	    	  values = new String[rfac.size()];
	    	  type = Type.FACTOR;
	    	 
	    	  for (int i = 0; i < rfac.size(); i++) {
	          values[i] = rfac.at(i);
	    	  }  
	      }
	      else if(rexp2.getType() == 37){
	      	int[] intarray = rexp2.asIntArray();
	      	values = new String[intarray.length];
	      	type = Type.LOGICAL;
	      	for (int i = 0; i < intarray.length; i++) {
	              values[i] = intarray[i]+"";
	      	}
	      }	
	      else if(rexp2.getType() == 32){
	      	int[] intarray = rexp2.asIntArray();
	      	values = new String[intarray.length];
	      	type = Type.INTEGER;
	      	for (int i = 0; i < intarray.length; i++) {
	              values[i] = intarray[i]+"";
	      	}
	      }	  
	      else if(rexp2.getType() == 34){
	      	String[] stringArray = rexp2.asStringArray();
	      	values = new String[stringArray.length];
	      	type = Type.CHARACTER;
	      	for (int i = 0; i < stringArray.length; i++) {
	              values[i] = stringArray[i];
	      	}
	      }	   
	      else if(rexp2.getType() == 33){
	      	double[] dArray = rexp2.asDoubleArray();
	      	values = new String[dArray.length];
	      	type = Type.NUMERIC;
	      	for (int i = 0; i < dArray.length; i++) {
	      		values[i] = dArray[i]+"";
	      	}
	      } else {
	    	  throw new MultimorbidityException("Conversion Error");
	      }
	      columns[j] = new Column(colname,type,values,factors);
	    }      
	  }
	  return new DataSetInstance(name,columns);
  }
  
  
  /**
   * Method that saves the active Data Set to a File.
   * @param name the name of the File.
   */
  public void saveDataSet(String name){
	  dsMapper.saveDataSet(activeDatasetInstance, name);
  }
  
  /**
   * Method that loads a dataset from a file.
   * @param file the name of the file to load.
   */
  public void loadDataSet(File file){
	  DataSetInstance tdataset = dsMapper.loadDataSet(file);
	  this.updateDataSet(tdataset);
	  
  }
  
  private String[] getFactors(REXP rexp){
	  String temp = rexp.toString();
	  Pattern pattern = Pattern.compile("\"(.*?)\"");
	  Matcher matcher = pattern.matcher(temp);
	  ArrayList<String> levels1 = new ArrayList<String>();
	  while (matcher.find())
	  {
		  String level = matcher.group(0);
		  String temp1 = level.replace('"', ' ').trim();
	      levels1.add(temp1);
	  }
	  String[] levels = levels1.toArray(new String[levels1.size()]);
	  return levels;
  }
  
  /**
   * Setter for a Column
   * @param columnname the name of the column who's name to set.
   * @param newName the new Name of the column.
   */
  public void setColumnName(String columnname, String newName){
	  this.activeDatasetInstance.setColumnName(columnname, newName);;
  }
  
  
  /**
   * Setter for the disease attribute of the active DataSet
   * @param column the column who's attribute to set.
   * @param bool the value to be set.
   */
  public void setDisease(String column, Boolean bool){
	activeDatasetInstance.setDisease(column,bool);	
  }
  
  /**
   * Getter for the disease attribute of the active DataSet
   * @param column the column who's attribute to get.
   * @return the boolean value of the attribute.
   */
  public Boolean getDisease(String column){
	  return activeDatasetInstance.getDisease(column);
  }
  
  /**
   * Setter for the observation attribute of the active DataSet
   * @param column the column who's attribute to set.
   * @param bool the value to be set.
   */
  public void setObservation(String column,Boolean bool){
	  activeDatasetInstance.setObservation(column, bool);
  }
  
  /**
   * Getter for the observation attribute of the active DataSet
   * @param column the column who's attribute to get.
   * @return the boolean value of the attribute.
   */
  public Boolean getObservation(String column){
	return activeDatasetInstance.getObservation(column);
  }
  
  /**
   * Setter for the average attribute of the active DataSet
   * @param column the column who's attribute to set.
   * @param double the value to be set.
   */
  public void setAverage(String column,double av){
	  activeDatasetInstance.setAverage(column,av);
  }
  
  /**
   * Getter for the average attribute of the active DataSet
   * @param column the column who's attribute to get.
   * @return the double value of the attribute.
   */
  public double getAverage(String column){
	return activeDatasetInstance.getAverage(column);
  }
  
  /**
   * Setter for the median attribute of the active DataSet
   * @param column the column who's attribute to set.
   * @param String the value to be set.
   */
  public void setMedian(String column,String med){
	  activeDatasetInstance.setMedian(column,med);
  }
  
  /**
   * Getter for the median attribute of the active DataSet
   * @param column the column who's attribute to get.
   * @return the String value of the attribute.
   */
  public String getMedian(String column){
	  return activeDatasetInstance.getMedian(column);
  }
  
  /**
   * Getter for the type of the column
   * @param columnname the column who's data type will be returned.
   * @return a String representing the column type.
   */
  public String getType(String columnname){
	  return activeDatasetInstance.getColumnType(columnname);
  }
  
  /**
   * Method returns a list of the available types.
   * @return a String array of datatypes.
   */
  public String[] getColumnTypes(){
	  return this.activeDatasetInstance.getTypes();
  }
  
  /**
   * Getter for the last saved filed.
   * Method used for logging purposes
   * @return the file that was saved on disk
   */
  public File getLastSavedFile() {
    return dsMapper.getFile();
  }
  
  /**
   * Getter for the types of the columns
   * @return a String array containing all the types of the columns.
   */
  public String[] getTypes(){
	  return activeDatasetInstance.getTypes();
  }
  
  /**
   * Setter fot the type of a single Column.
   * @param columnname the name of the column to set.
   * @param type a string representing the new type.
   */
  public void setColumnType(String columnname, String type){
	  activeDatasetInstance.setColumnType(columnname, type);
  }
  
  /**
   * Check to see whether Column contains NA's.
   * @param columnname the name of the column to check.
   * @return a boolean whether column contains NA's.
   */
  public Boolean hasNas(String columnname){
	  return this.activeDatasetInstance.hasNas(columnname);
  }
  
  /**
   * Setter for the NA's attribute of a column
   * @param columnname the name of the column to set.
   * @param nas the boolean value to set.
   */
  public void setNas(String columnname, Boolean nas){
	  this.activeDatasetInstance.setNas(columnname, nas);
  }

}
