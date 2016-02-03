package domain;

import domain.Result;

import util.MultimorbidityException;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Class representing the DataSet.
 * @author Ian van Nieuwkoop
 * @version 0.4
 */
public class DataSetInstance implements Serializable {

  private static final long serialVersionUID = -4980879919806006795L;
  private String name;
  private HashMap<String,Column> columns;
  private String[] columnindex;
  private HashMap<String, Result> results;
  private Queue<String> audit;
  //private HashMap<String,String> types;
  
  /**
   * Constructor 1 of DataSet object without parameters.
   */
  public DataSetInstance() {
    this.name = "";
	this.results = new HashMap<String, Result>();
    this.columns = new HashMap<String, Column>();
    this.audit = new LinkedList<String>();
    //this.types = new HashMap<String,String>();
  }
  
  /**
   * Constructor 2 with paramaters.
   * @param name the name of the dataSetInstance.
   * @param col a String[] containing th columnames.
   * @param types a array containing the types of the columns.
   * @param val a 2d array containing the values of the columns.
   * @throws MultimorbidityException
   */
  public DataSetInstance(String name, String[]col, Column.Type[] types, String[][] val) throws MultimorbidityException{
	  this.name = name;
	  if(!allUnique(col)){
		  throw new MultimorbidityException("Column names are not Unique");
	  }
	  
	  this.columns = new HashMap<String, Column>();
	  this.columnindex = col;
	  for(int i = 0; i<col.length;i++){
		  columns.put(col[i],new Column(col[i],types[i],val[i]));
	  }
	  this.results = new HashMap<String, Result>();
	  this.audit = new LinkedList<String>();
  }
  
  /**
   * Constructor 3 with parameters.
   * @param name the name of the dataSetInstance.
   * @param col a String[] containing th columnames.
   * @param types a array containing the types of the columns.
   * @param val a 2d array containing the values of the columns.
   * @param factors a 2d array contiaining the factors of the columns.
   * @throws MultimorbidityException
   */
  public DataSetInstance(String name,String[]col,Column.Type[] types, String[][] val, String[][] factors) throws MultimorbidityException{
	  this.name = name;
	  if(!allUnique(col)){
		  throw new MultimorbidityException("Column names are not Unique");
	  }
	  this.columns = new HashMap<String, Column>();
	  this.columnindex = col;
	  for(int i = 0; i<col.length;i++){
		  columns.put(col[i],new Column(col[i],types[i],val[i],factors[i]));
	  }
	  this.results = new HashMap<String, Result>();
	  this.audit = new LinkedList<String>();
  }
  
  /**
   * Constructor 4
   * @param name the name of the dataSetInstance.
   * @param columns a array of Column to add to the datasetinstance.
   */
  public DataSetInstance(String name,Column[] columns){
	  this.name = name;
	  this.results = new HashMap<String, Result>();
	  this.columns = new HashMap<String, Column>();
	  this.columnindex = new String[columns.length];
	  for(int i = 0; i<columns.length; i++){
		  columnindex[i] = columns[i].getName();
	  }
	  this.audit = new LinkedList<String>();
	  for(Column c: columns){
		  this.columns.put(c.getName(), c);
	  }
  }
  
  private boolean allUnique(String[] strings) {
	    HashSet<String> set = new HashSet<>();
	    for (String s : strings) {
	        if (! set.add(s)) {
	            return false;
	        }
	    }
	    return true;
	}
  
  /**
   * Getter for the Columnnames.
   * @return String[] of Columnnames
   */
  public String[] getColumnnames() {
	  return columnindex;
  }
  
  
  /**
   * Getter for the Values.
   * Values array is transformed to be used by example a JTable
   * @return String[][] containing the values
   
  public String[][] getValues() {
    //return values;
	String[] columnnames = getColumnnames();
	int x = columnnames.length;
	if(x >0){
		int y =columns.get(columnnames[0]).getValues().length;
		if(y >0){
			String[][] values = new String[y][x];
			
			for(int i = 0; i<columns.size(); i++){
				String[] vlist = columns.get(columnnames[i]).getValues();
				for(int j = 0;j<vlist.length; j++){
					values[j][i] = vlist[j];
				}
			}
			return values;
		}
	}
	return null;
  }
  
  /**
   * Getter for the column Values of a column.
   * @param columnname the name of the column who's values to return.
   * @return a arrar of columnvalues.
   */
  public String[] getColumnValues(String columnname){
	  return columns.get(columnname).getValues();
  }
  
  /**
   * Method returns the maximum amount of row a DataInstance has.
   * @return a in representing the max rows.
   */
  public int getMaxRows(){
	  int max = 0;
	  for(Column c: columns.values()){
		  int temp = c.getValues().length;
		  if(temp > max){
			  max = temp;
		  }
	  }
	  return max;
  }
  
  /**
   * Getter for the factors used by a column.
   * @param columnname name of the column who's factor to return.
   * @return a array of factors.
   */
  public String[] getFactors(String columnname) {
    return columns.get(columnname).getFactors();
  }
  
  /**
   * Add Result to DataSet.
   * @param res the Result to be added
   */
  public void addResult(Result res) {
    String toolname = res.getTool();
    
    if (results == null) {
      results = new HashMap<String, Result>();
    }
    
    if (results.get(toolname) != null) {
      results.remove(toolname);
    }
    results.put(toolname, res);
  }
  
  /**
   * Returns the name of the DataSet.
   * @return name of DataSet
   */
  public String getName() {
    if (name == null) {
      return (Math.random()*10) * (Math.random()*10) + "";
    }
    return name;
  }
  
  /**
   * Getter for a Result.
   * @param tool of the Result to return.
   * @return a Result object.
   */
  public Result getResult(String tool){
	  return results.get(tool);
  }
  
  /**
   * Getter for a Queue of R Commands.
   * @return a list of R Commands.
   */
  public Queue<String> getAudit(){
	  return audit;
  }
  
  /**
   * Method to add to the DataSetInstance queue of R Commands.
   * @param entry the line of R Code to add.
   */
  public void addToAudit(String entry){
	  audit.add(entry);
  }
  
  /**
   * Method to return the Result queue of R Code
   * @return a Queue of R Code.
   */
  public Queue<String> getAuditResults(){
	  Queue<String> rq = new LinkedList<String>();
	  Iterator<String> it = audit.iterator();
	  
	  while(it.hasNext()){
		  rq.add(it.next());
	  }
	  
	  for(Result r: results.values()){
		  Queue<String> q = r.getAudit();
		  Iterator<String> it2 = q.iterator();
		  while(it2.hasNext()){
			  rq.add(it2.next());
		  }
	  }
	  
	  return rq;
  }
  
  /**
   * Method checks is a column is a certain datatype.
   * @param columnName the column to check.
   * @param type datatype to check.
   * @return boolean true if column and type match, otherwise false.
   */
  public Boolean isTypeColumn(String columnName, Column.Type type){
	  Column column = columns.get(columnName);
		  if(column != null){
			  if(column.getType() == type){
				  return true;
			  }
		  }
	  return false;
  }
  
  /**
   * Setter for a Column
   * @param columnname the name of the column who's name to set.
   * @param newName the new Name of the column.
   */
  public void setColumnName(String columnname, String newName){
	  columns.get(columnname).setName(newName);
	  for(int i =0; i<this.columnindex.length;i++){
		  if(columnindex[i].equals(columnname)){
			  columnindex[i]=newName;
			  Column column = columns.remove(columnname);
			  columns.put(newName, column);
			  return;
		  }
	  }
  }
  
  /**
   * Getter for the datatype of a column.
   * @param columnname name of the column to get the type of.
   * @return a string representing the type of the column.
   */
  public String getColumnType(String columnname){
	  Column column = columns.get(columnname);
	  if(column !=null){
		  return column.getType().toString();
	  }else return null;
  }
  
  /**
   * Setter for the disease attribute of a column.
   * @param column the column to set.
   * @param bool the valeu to set the disease attribute to.
   */
  public void setDisease(String column, Boolean bool){
	columns.get(column).setDisease(bool);	
  }
  
  /**
   * Getter for the disease attribute of a column.
   * @param column the column to get.
   * @return a boolean representing the attribute value.
   */
  public Boolean getDisease(String column){
	return columns.get(column).getDisease();
  }
  
  /**
   * Setter for the observation attribute of a column.
   * @param column the column to set.
   * @param bool the valeu to set the observation attribute to.
   */
  public void setObservation(String column,Boolean bool){
	  columns.get(column).setObservation(bool);
  }
  
  /**
   * Getter for the observation attribute of a column.
   * @param column the column to get.
   * @return a boolean representing the attribute value.
   */
  public Boolean getObservation(String column){
		return columns.get(column).getObservation();
  }
  
  /**
   * Setter for the average attribute of a column.
   * @param column the column to set.
   * @param double the value to set the average attribute to.
   */
  public void setAverage(String column,double av){
	  columns.get(column).setAverage(av);
  }
  
  /**
   * Getter for the average attribute of a column.
   * @param column the column to get.
   * @return a double representing the attribute value.
   */
  public double getAverage(String column){
		return columns.get(column).getAverage();
  }
	
  /**
   * Getter for the median attribute of a column.
   * @param column the column to get.
   * @return a String representing the attribute value.
   */
  public String getMedian(String column){
		return columns.get(column).getMedian();
  }
  
  /**
   * Setter for the median attribute of a column.
   * @param column the column to set.
   * @param bool the value to set the median attribute to.
   */
  public void setMedian(String column,String med){
	  columns.get(column).setMedian(med);;
  }
  
  /**
   * Method returns a list of the available types.
   * @return a String array of datatypes.
   */
  public String[] getTypes(){
	return Column.Type.getTypes();
  }
  
  public void setColumnType(String columnname, String type){
	  columns.get(columnname).setType(type);
  }
  
  
  @SuppressWarnings("unchecked")
  public DataSetInstance clone(){
	  Collection<Column> tempColumns = columns.values();
	  Iterator<Column> it = tempColumns.iterator();
	  Column[] cColumns = new Column[tempColumns.size()];
	  
	  int i =0;
	  while(it.hasNext()){
		  cColumns[i] = it.next();
		  i++;
	  }
	  DataSetInstance dsi = new DataSetInstance(name,cColumns);
	  
	  dsi.columnindex = this.columnindex.clone();
	  
	  dsi.results = (HashMap<String, Result>) this.results.clone();
	  dsi.audit = this.audit;
	  
	  
	  return dsi;
  }
  
  /**
   * Getter for the Columns.
   * @return the columns.
   */
  public HashMap<String,Column> getColumns(){
	  return columns;
  }
  
  /**
   * Method checks if Columns contain NA's.
   * @param columnname the name of the column to check.
   * @return a boolean depending on the check.
   */
  public Boolean hasNas(String columnname){
	  return columns.get(columnname).hasNas();
  }
  
  /**
   * Setter for the NA attribute of a column.
   * @param columnname the name of the column
   * @param nas the boolean values to be set.
   */
  public void setNas(String columnname, Boolean nas){
	  columns.get(columnname).setNas(nas);
  }
}
