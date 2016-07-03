package domain;

import data.DataSetMapper;
import domain.Column.Type;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RFactor;
import org.rosuda.JRI.RVector;
import util.MultimorbidityException;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represents a DataSet. 
 * This class is also responsible for the undo functionality.
 * The actual data is saved in a DataSetInstance.
 * @author ABI 37
 * @version 1.0
 */
public class DataSet implements Serializable {
  
  private static final long serialVersionUID = -1558181455145977463L;
  private static final int MAXSIZE = 7;
  private static final int VECTOR = 16;
  private static final int FACTOR = 127;
  private static final int LOGICAL = 37;
  private static final int INTARRAY = 32;
  private static final int STRINGARRAY = 34;
  private static final int DOUBLEARRAY = 33;
  private DataSetInstance activeDatasetInstance;
  private transient Stack<DataSetInstance> undoStack;
  private transient Stack<DataSetInstance> redoStack;
  private transient DataSetMapper dsMapper;
  
  /**
   * Contructor, creates an DataSet without a datasetInstance.
   */
  public DataSet() {
    this.reset();
  }
  
  /**
   * Constructor 1, creates a DataSet with a dataSetInstance.
   * @param rdata  an REXP object representing a R dataframe
   * @param name  the name of the DataSet
   * @throws MultimorbidityException  pop-up in the gui
   */
  public DataSet(REXP rdata,String name) throws MultimorbidityException {
    this();
    DataSetInstance tdataset = toDataSetInstace(name,rdata);
    this.activeDatasetInstance = tdataset;
  }
  
  /**
   * Constructor 2, creates a DataSet.
   * @param data  the data of the dataset instance
   * @throws MultimorbidityException  pop-up in the gui
   */
  public DataSet(DataSetInstance data) throws MultimorbidityException {
    this();
    this.activeDatasetInstance = data;
    this.updateDataSet(data);
  }
  
  /**
   * Method resets the undo-, redoStack and dsMapper.
   */
  public void reset() {
    this.undoStack = new SizedStack<DataSetInstance>(MAXSIZE);
    this.redoStack = new SizedStack<DataSetInstance>(MAXSIZE);
    this.dsMapper = new DataSetMapper();
  }
  
  /**
   * Getter of DataSet object.
   * @return  the dataset instance
   */
  public DataSetInstance getDatasetInstance() {
    return activeDatasetInstance;
  }
 
  /**
  * Getter of the name of DataSetInstance.
  * Method used for logging purposes
  * @return  the name of the active DataSetInstance
  */
  public String getDataSetInstanceName() {
    return activeDatasetInstance.getName();
  }
  
  /**
   * Updates the DataSet.
   * @param ds  DataSetInstance to be updated
   */
  private void updateDataSet(DataSetInstance ds) {
    if (this.activeDatasetInstance != null) {
      undoStack.push(activeDatasetInstance);
    }
    this.activeDatasetInstance = ds;
  }
  
  
  /**
   * Method that loads a REXP representing the Data Set in R into the Memory of the Application.
   * @param rexp  a REXP object reprenting the Data Set
   * @throws MultimorbidityException  pop-up in the gui
   */
  public void updateDataSet(REXP rexp) throws MultimorbidityException {
    String name = activeDatasetInstance.getName();
    String[] columns = activeDatasetInstance.getColumnnames();
    HashMap<String,String> medians = new HashMap<String,String>();
    HashMap<String,Double> averages = new HashMap<String,Double>();
    /*Add extra Attribute to be transferred 
      from old to new datasetInstance Observation disease etc..*/
    for (int i = 0; i < columns.length; i++) {
      medians.put(columns[i], activeDatasetInstance.getMedian(columns[i]));
      averages.put(columns[i], activeDatasetInstance.getAverage(columns[i]));
    }
    DataSetInstance tdataset = toDataSetInstace(name, rexp);
    String[] tempcolumns = tdataset.getColumnnames();
    //Add attributes to new dataset which needed to be saved.
    for (int i = 0; i < tempcolumns.length; i++) {
      if (medians.containsKey(tempcolumns[i]) && averages.containsKey(tempcolumns[i])) {
        String median = medians.get(tempcolumns[i]);
        double average = averages.get(tempcolumns[i]);
        tdataset.setMedian(tempcolumns[i],median);
        tdataset.setAverage(tempcolumns[i],average);
      }
    }
    this.activeDatasetInstance = tdataset;
  }
  
  /**
   * Adds a datasetInstance to the undoStack.
   */
  public void snapShot() {
    if (this.activeDatasetInstance != null) {
      undoStack.push(activeDatasetInstance.clone());
    }
  }
  
  /**
   * Method checks if an Undo operation is possible.
   * @return  a Boolean based on if an undo is possible
   */
  public boolean canUndo() {
    return (!undoStack.isEmpty());
  }
  
  /**
   * Method checks if a Redo operation is possible.
   * @return  a Boolean based on if a redo is possible
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
   * Method returns the values of the Column.
   * @param columnname  name of the colum who's values will be returned
   * @return  a String array of column values
   */
  public String[] getColumnValues(String columnname) {
    if (activeDatasetInstance == null) {
      return new String[0];
    }
    return activeDatasetInstance.getColumnValues(columnname);
  }
  
  /**
   * Method returns the maximum amount of row a DataInstance has.
   * @return  a in representing the max rows
   */
  public int getMaxRows() {
    if (activeDatasetInstance != null) {
      return activeDatasetInstance.getMaxRows();
    } else {
      return 0;
    }
  }
  
  /**
   * Getter for the column names.
   * @return  a String array containing all the column names
   */
  public String[] getColumnnames() {
    if (activeDatasetInstance == null) {
      return new String[0];
    }
    return activeDatasetInstance.getColumnnames();
  }
  
  /**
   * Getter for the column names of a single type.
   * @param type  the type of columns to return
   * @return  a String array containing all the column names of a certain type
   */
  public String[] getColumnnames(Column.Type type) {
    String[] columnnames = getColumnnames();
    ArrayList<String> temp = new ArrayList<String>();
    for (String s: columnnames) {
      if (activeDatasetInstance.isTypeColumn(s, type)) {
        temp.add(s);
      }
    }
    return temp.toArray(new String[temp.size()]);
  }
  
  /**
   * Getter for the type of a column.
   * @param columnname  name of the column who's type will be returned
   * @return  a String representing the column's datatype
   */
  public String getColumnType(String columnname) {
    return activeDatasetInstance.getColumnType(columnname);
  }
  
  /**
   * Getter for the factors of a column.
   * @param columnname the name of the column who's factors to return
   * @return a String array of the factors in the column
   */
  public String[] getFactors(String columnname) {
    if (activeDatasetInstance == null) {
      return new String[1];
    }
    return activeDatasetInstance.getFactors(columnname);
  }
  
  private static String[] getFactors(REXP rexp) {
    String temp = rexp.toString();
    Pattern pattern = Pattern.compile("\"(.*?)\"");
    Matcher matcher = pattern.matcher(temp);
    ArrayList<String> levels1 = new ArrayList<String>();
    while (matcher.find()) {
      String level = matcher.group(0);
      String temp1 = level.replace('"', ' ').trim();
      levels1.add(temp1);
    }
    String[] levels = levels1.toArray(new String[levels1.size()]);
    return levels;
  }
  
  /**
   * Getter for the active Data Sets name.
   * @return a String representing the active dataset's name
   */
  public String getDataSetName() {
    String name = "";
    if (activeDatasetInstance == null) {
      return name;
    }
    return activeDatasetInstance.getName();
  }
  
  /**
   * Method transforms a REXP object representing a DataFrame into a DataSetInstance object.
   * @param name the name the instance will have
   * @param data the REXP object to be transformed
   * @return an DataSetInstance object representing the REXP dataframe
   * @throws MultimorbidityException  pop-up in the gui
   */
  @SuppressWarnings("unchecked")
  public static DataSetInstance toDataSetInstace(String name,REXP data) 
      throws MultimorbidityException {
    Column[] columns = {}; 
    Vector<String> vnames;
    if (data != null && data.getType() == VECTOR) {
      RVector rvec = data.asVector();
      columns = new Column[rvec.size()];
      vnames = (rvec.getNames());
      for (int j = 0; j < rvec.size(); j++) {
        REXP rexp2 = rvec.at(j);
        String colname = vnames.get(j);
        Type type = null;
        String[] values = {};
        String[] factors = {};
        if (rexp2.getType() == FACTOR) {
          RFactor rfac = rexp2.asFactor();
          factors = getFactors(rexp2);
          values = new String[rfac.size()];
          type = Type.FACTOR;
          for (int i = 0; i < rfac.size(); i++) {
            values[i] = rfac.at(i);
          }  
        } else if (rexp2.getType() == LOGICAL) {
          int[] intarray = rexp2.asIntArray();
          values = new String[intarray.length];
          type = Type.LOGICAL;
          for (int i = 0; i < intarray.length; i++) {
            values[i] = intarray[i] + "";
          }
        } else if (rexp2.getType() == INTARRAY) {
          int[] intarray = rexp2.asIntArray();
          values = new String[intarray.length];
          type = Type.INTEGER;
          for (int i = 0; i < intarray.length; i++) {
            values[i] = intarray[i] + "";
          }
        } else if (rexp2.getType() == STRINGARRAY) {
          String[] stringArray = rexp2.asStringArray();
          values = new String[stringArray.length];
          type = Type.CHARACTER;
          for (int i = 0; i < stringArray.length; i++) {
            values[i] = stringArray[i];
          }
        } else if (rexp2.getType() == DOUBLEARRAY) {
          double[] darray = rexp2.asDoubleArray();
          values = new String[darray.length];
          type = Type.NUMERIC;
          for (int i = 0; i < darray.length; i++) {
            values[i] = darray[i] + "";
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
   * @param file  the name of the File
   * @throws MultimorbidityException  pop-up in the gui
   */
  public void saveDataSet(File file) throws MultimorbidityException {
    dsMapper.saveDataSet(activeDatasetInstance, file);
  }
  
  /**
   * Method that loads a dataset from a file.
   * @param file  the name of the file to load
   * @throws MultimorbidityException  pop-up in the gui
   */
  public void loadDataSet(File file) throws MultimorbidityException {
    DataSetInstance tdataset = dsMapper.loadDataSet(file);
    this.updateDataSet(tdataset);  
  }
  
  /**
   * Setter for a Column.
   * @param columnname  the name of the column who's name to set
   * @param newName  the new Name of the column
   */
  public void setColumnName(String columnname, String newName) {
    this.activeDatasetInstance.setColumnName(columnname, newName);
  }
  
  /**
   * Setter for the classification attribute of the active DataSet.
   * @param column the column who's attribute to set
   * @param clazz a string representation of the Class value to be set
   */
  public void setClassification(String column, String clazz) {
    activeDatasetInstance.setClassification(column,clazz);  
  }
  
  /**
   * Getter for the classification attribute of the active DataSet.
   * @param column  the column who's attribute to get
   * @return a  Column.Class value of the attribute
   */
  public Column.Class getClassification(String column) {
    return activeDatasetInstance.getClassification(column);
  }
  
  /**
   * Setter for the average attribute of the active DataSet.
   * @param column  the column who's attribute to set
   * @param av  the value to be set
   */
  public void setAverage(String column,double av) {
    activeDatasetInstance.setAverage(column, av);
  }
  
  /**
   * Getter for the average attribute of the active DataSet.
   * @param column the column who's attribute to get
   * @return  the double value of the attribute
   */
  public double getAverage(String column) {
    return activeDatasetInstance.getAverage(column);
  }
  
  /**
   * Setter for the median attribute of the active DataSet.
   * @param column  the column who's attribute to set
   * @param med  String value to be set
   */
  public void setMedian(String column,String med) {
    activeDatasetInstance.setMedian(column,med);
  }
  
  /**
   * Getter for the median attribute of the active DataSet.
   * @param column  the column who's attribute to get
   * @return  the String value of the attribute
   */
  public String getMedian(String column) {
    return activeDatasetInstance.getMedian(column);
  }
  
  /**
   * Getter for the type of the column.
   * @param columnname  the column who's data type will be returned
   * @return  a String representing the column type
   */
  public String getType(String columnname) {
    return activeDatasetInstance.getColumnType(columnname);
  }
  
  /**
   * Method returns a list of the available types.
   * @return  a String array of datatypes
   */
  public String[] getColumnTypes() {
    return this.activeDatasetInstance.getTypes();
  }
  
  /**
   * Getter for the last saved filed.
   * Method used for logging purposes.
   * @return  the file that was saved on disk
   */
  public File getLastSavedFile() {
    return dsMapper.getFile();
  }
  
  /**
   * Getter for the types of the columns.
   * @return  a String array containing all the types of the columns
   */
  public String[] getTypes() {
    return activeDatasetInstance.getTypes();
  }
  
  /**
   * Method returns a list of the available Classes.
   * @return  a String array of classification types
   */
  public String[] getClasses() {
    return activeDatasetInstance.getClasses();
  }
  
  /**
   * Setter fot the type of a single Column.
   * @param columnname  the name of the column to set
   * @param type  a string representing the new type
   */
  public void setColumnType(String columnname, String type) {
    activeDatasetInstance.setColumnType(columnname, type);
  }
  
  /**
   * Check to see whether Column contains NA's.
   * @param columnname  the name of the column to check
   * @return  a boolean whether column contains NA's
   */
  public Boolean hasNas(String columnname) {
    return this.activeDatasetInstance.hasNas(columnname);
  }
  
  /**
   * Setter for the NA's attribute of a column.
   * @param columnname  the name of the column to set
   * @param nas  the boolean value to set.
   */
  public void setNas(String columnname, Boolean nas) {
    this.activeDatasetInstance.setNas(columnname, nas);
  }
}
