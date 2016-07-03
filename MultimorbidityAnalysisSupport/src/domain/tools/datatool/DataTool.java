package domain.tools.datatool;


import data.SessionHistoryMapper;
import domain.Column;
import domain.Column.Type;
import domain.DataSet;
import domain.DataSetInstance;
import domain.SessionHistory;
import domain.tools.Result;
import domain.tools.Tool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import util.MultimorbidityException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Class represents the DataTool. 
 * This tool is responsible for all the calculations concerning the dataset.
 * @author ABI team 37
 * @version 1.0
 */
public class DataTool extends Tool {
  
  /**
   * Constant. Name of Tool.
   */
  public static final String TOOLNAME = "DATATOOL";
  private DataSet activeDataset;
  private SessionHistory sessions;
  private Logger logger = LogManager.getLogger(this.getClass()); 

  /**
   * Constructor.
   * @param rengine  the instance of the R Engine the class will use.
   */
  public DataTool(Rengine rengine) {
    super(DataTool.TOOLNAME, rengine);
    loadSessionHistory(); 
  }

  private void loadSessionHistory() {
    this.sessions = SessionHistoryMapper.loadSessionHistory();
    if (this.sessions == null) {
      this.sessions = new SessionHistory();
    }
  }

  /**
  * Getter of the active DataSet.
  * Method necessary for test purposes
  * @return  the active DataSet
  */
  public DataSet getActiveDataSet() {
    return activeDataset;
  }

  /**
  * Getter of a clone of the active DataSet Instance.
  * @return  a clone of the active DataSet Instance
  */
  public DataSetInstance getActiveDataSetInstance() {
    return activeDataset.getDatasetInstance().clone();
  }

  /**
  * Method returns the list of values for a column.
  * @param columnname  name of the column who's values to return.
  * @return  a String array of values.
  */
  public String[] getColumnValues(String columnname) {
    logger.info("Method getColumnValues called");
    logger.debug("Method getColumnValues for columnname " + columnname 
        + " returned number of values: " + activeDataset.getColumnValues(columnname).length);
    return activeDataset.getColumnValues(columnname);
  }

  /**
  * Method returns the maximum amount of row a DataInstance has.
  * @return  a in representing the max rows.
  */
  public int getMaxRows() {
    logger.info("Method getMaxRows called");
    logger.debug("Method getMaxRows returned: " + activeDataset.getMaxRows());
    return activeDataset.getMaxRows();
  }

  /**
  * Method that returns a list of the columnnames used by the active DataSetInstance.
  * @return  an Array of Strings.
  */
  public String[] getDataSetColumnnames() {
    logger.info("Method getDataSetColumnnames called");
    if (activeDataset != null) {
      String[] columnnames = activeDataset.getColumnnames();
      logger.debug("Method getDataSetColumnnames returned: " 
          + arrayOfStringsToString(columnnames)); 
      return columnnames;
    } else {
      logger.debug("Method getDataSetColumnnames (DataSet null) returned: " + null);
      return null;
    }
  }

  /**
  * Getter for the active DataSet column names by Type.
  * @param type  the type of the columns to return.
  * @return  a String array containing the column names.
  */
  public String[] getDataSetColumnnames(Column.Type type) {
    logger.info("Method getDataSetColumnnames called");
    if (activeDataset != null) {
      String[] columnnames = activeDataset.getColumnnames(type);
      logger.debug("Method getDataSetColumnnames with column type " + type.toString() 
          + " returned: " + arrayOfStringsToString(columnnames)); 
      return columnnames;
    } else {
      logger.debug("Method getDataSetColumnnames (DataSet null) with column type " 
          + type.toString() + " returned: " + null); 
      return null;
    }
  }

  /**
  * Method returns the name of the active DataSet.
  * @return  String name of active DataSet.
  */
  public String getDataSetName() {
    logger.info("Method getDataSetName called");
    logger.debug("Method getDataSetName returned: " + activeDataset.getDataSetName());
    return activeDataset.getDataSetName();
  }

  
  /**
   * Getter for the active DataSet column names by Type NUM + INT.
   * @return  a String array containing the column names.
   */
  public String[] getDataSetNumColumnnames() {
    logger.info("Method getDataSetNumColumnnames called");
    if (activeDataset != null) {
      String[] numColumnnames = activeDataset.getColumnnames(Type.NUMERIC);
      String[] intColumnnames = activeDataset.getColumnnames(Type.INTEGER);
      int alength = numColumnnames.length;
      int blength = intColumnnames.length;
      String[] columnnames = new String[alength + blength];
      System.arraycopy(numColumnnames, 0, columnnames, 0, alength);
      System.arraycopy(intColumnnames, 0, columnnames, alength, blength);
      logger.info("Dataset columnnames with column type Numeric and Integers returned"); 
      return columnnames;
    } else {
      logger.info("Dataset columnnames with column type requested from not existing dataset "
          + "(Dataset null)"); 
      return null;
    }
  }

  /**
  * Getter for the Median values of a column.
  * @param columnname  the column who's median values will be returned.
  * @return  a String representing the median value.
  */
  public String getColumnMedian(String columnname) {
    logger.info("Method getColumnMedian called");
    logger.debug("Method getColumnMedian for " + columnname + " returned " 
        + activeDataset.getMedian(columnname));
    return activeDataset.getMedian(columnname);
  }

  /**
  * Getter for the average values of a column.
  * @param columnname  the column who's average values will be returned.
  * @return  a double representing the average values or a null if not able to calculate.
  */
  public double getColumnAverage(String columnname) {
    logger.info("Method getColumnAverage called");
    logger.debug("Method getColumnAverage " + columnname + " returned" 
        + activeDataset.getAverage(columnname));
    return activeDataset.getAverage(columnname);
  }

  /**
  * Getter for the datatype of a column.
  * @param columnname  the column who's datatype will be returned.
  * @return  a String representing the datatype of the column.
  */
  public String getColumnType(String columnname) {
    logger.info("Method getColumnType called");
    logger.debug("Method getColumnType for " + columnname + " returned " 
        + activeDataset.getColumnType(columnname));
    return activeDataset.getColumnType(columnname);
  }

  /**
   * Setter for the classification attribute of the active DataSet.
   * @param column  the column who's attribute to set.
   * @param clazz  a string representation of the Class value to be set.
   */
  public void setClassification(String column, String clazz) {
    String oldclazz = activeDataset.getClassification(column).toString();
    if (oldclazz.equals(clazz)) {
      return;
    }
    activeDataset.setClassification(column,clazz);  
  }

  /**
  * Getter for the classification attribute of the active DataSet.
  * @param column  the column who's attribute to get.
  * @return  a Column.Class value of the attribute.
  */
  public Column.Class getClassification(String column) {
    return activeDataset.getClassification(column);
  }  

  /**
  * Getter for the types of the columns.
  * @return  a String array of the types of the columns.
  */
  public String[] getTypes() {
    return activeDataset.getTypes();
  }
  
  /**
   * Method returns a list of the available Classes.
   * @return  a String array of classification types.
   */
  public String[] getClasses() {
    return activeDataset.getClasses();
  }
        
  /**
  * Method returns a list of factors used by a column.
  * @param column  the name of the column which factors must be returned.
  * @return  a list of factors used in the column.
  */
  public String[] getDatasetFactors(String column) {
    logger.info("Method getDatasetFactors called");
    String[] factors = activeDataset.getFactors(column);
    if (factors != null) {
      logger.debug("Method getDatasetFactors for " + column + " returned " 
          + arrayOfStringsToString(factors));
      return factors;
    } else {
      logger.debug("Method getDatasetFactors for " + column 
            + " (factors null) returned empty String ");
      return new String[0];
    }  
  }

  /**
  * Method for saving dataset to undoStack.
  */
  public void snapshot() {
    logger.info("Method snapshot called");
    this.activeDataset.snapShot();
    logger.debug("activeDatasetInstance of undoStack after calling method snapshot " 
        + activeDataset.getDataSetInstanceName());
  }

  /**
  * Sets the na values.
  * @param columnname  the name of the column where the NA-values will be replaced.
  * @param na  the value by which the NA-values will be substituted.
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  public void setNa(String columnname, String na) throws MultimorbidityException {
    setNa(columnname, na, null); 
  }

  /**
  * Sets the na values.
  * @param columnname  the name of the column where the NA-values will be replaced.
  * @param na  the value by which the NA-values will be substituted.
  * @param type  the type of the Column
  * @throws MultimorbidityException a pop-up message will be shown in the gui
  */
  public void setNa(String columnname, String na, String type) throws MultimorbidityException {
    logger.info("method setNa called");
    if (type == null) {
      type = this.activeDataset.getType(columnname);
    }
    
    String expr = "";
    if (type.equals("date") || type.equals("Date")) {
      expr = RVAR + "$'" + columnname + "'[is.na(" + RVAR + "$'" + columnname + "')]<-" + na ;
    } else {
      expr = RVAR + "$'" + columnname + "'[is.na(" + RVAR + "$'" + columnname + "')]<-" 
          + "'" + na + "'";
    } 
    eval(expr);
    String expr1 = RVAR + "$'" + columnname + "'<- as." + type + "(" + RVAR + "$'" 
        + columnname + "')";
    eval(expr1);
        
    loadRData();
    
    logger.debug("Factors after calling setNa for column " + columnname + " set to " + na + " are " 
        + arrayOfStringsToString(activeDataset.getFactors(columnname)));
  }

  /**
  * Omits the na values in the entire DataSet.
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  public void omitNa() throws MultimorbidityException {
    logger.info("Method omitNa called");
    String expr = RVAR + "<-na.omit(" + RVAR + ")";
    String expr1 = RVAR + "<- droplevels(" + RVAR + ")";
    eval(expr);
    eval(expr1);   
    loadRData();
    //Debug logging
    ArrayList<String> allFactors = new ArrayList<String>();
    for (String column: activeDataset.getColumnnames()) {
      for (String factor: activeDataset.getFactors(column)) {
        allFactors.add(factor);
      }
    }
    /*logger.debug("Factors after calling omitNa: " 
      + arrayOfStringsToString((String[]) allFactors.toArray()));*/
  }
 
  /**
  * Edits columnname.
  * @param name Old columnname.
  * @param newName New columnname.
  * @return Boolean whether method was completed succesfully.
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  public Boolean editColumnname(String name, String newName) throws MultimorbidityException {
    logger.info("Method editColumnname called");
    if (!name.equals(newName)) {
      if (checkInput(newName)) {
        throw new MultimorbidityException("Name must be Alphanumeric"); 
      }
      String[] cols = getDataSetColumnnames();
      if (newName.isEmpty() || Arrays.asList(cols).contains(newName)) {
        return false;
      } 
      String expr = "colnames(" + RVAR + ")[colnames(" + RVAR + ") =='" + name 
          + "'] <-'" + newName + "'";
      eval(expr);
      this.loadRData();
      return true;
    }
    logger.debug("Columnnames after calling method editColumnname with name " + name 
        + " and newName " + newName + ": " 
        + arrayOfStringsToString(activeDataset.getColumnnames()));
    return null;
  }

  /**
  * Private Method for checking if String is valid input.
  * @param input  the String to be used as input
  * @return  true if contains whitespace, else false.
  */
  private Boolean checkInput(String input) {
    if (isNumeric(input)) {
      return true;
    }

    for (char c: input.toCharArray()) {
      if ((Character.isWhitespace(c)) ) {
        return true;
      }
    }
    return false;
  }

  /**
  * Deletes a column.
  * @param columnname  name of the column to be deleted
  * @return  boolean whether method was completed succesfully.
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  public Boolean deleteColumn(String columnname) throws MultimorbidityException {
    logger.info("Method deleteColumn called");
    String[] cols = getDataSetColumnnames();
    if (columnname == null || !Arrays.asList(cols).contains(columnname)) {
      return false;
    } 
    String expr = RVAR + "$'" + columnname + "'<- NULL";
    eval(expr);
    loadRData();
    logger.debug("Columnnames after calling method deleteColumnname for column " + columnname 
        + ": " + arrayOfStringsToString(activeDataset.getColumnnames()));
    return true;
  }

  /**
  * Deletes categorical columns from dataset.
  * @return  boolean whether method was completed successfully.
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  public Boolean deleteCategorical() throws MultimorbidityException {
    logger.info("Method deleteCategorical() is called");
    String[] factors = getDataSetColumnnames(Type.FACTOR);
    if (factors == null) {
      return false;
    } else { 
      for (int i = 0; i < factors.length ; i++) {
        for (String column: activeDataset.getColumnnames()) {
          if (column == factors[i]) {
            deleteColumn(column);
          }
        }
      }
      loadRData();
      return true;        
    }
  }

  /**
  * Deletes Numerical columns from dataset.
  * @return  boolean whether method was completed successfully.
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  public Boolean deleteNumerical() throws MultimorbidityException {
    logger.info("Method deleteColumn based on type, is called");
    for (String column: activeDataset.getColumnnames()) {
      String[] cols = getDataSetColumnnames(Type.FACTOR);
      if (cols == null) {
        return false;
      } else {
        for (int i = 0; i < cols.length; i++) {
          if (column == cols[i]) {
            deleteColumn(column);
          }
        }
        return true;
      }
    }
    loadRData();
    return true; 
  }

  /**
  * Method renames a Factor.
  * @param columnname  the name of the column containing factor
  * @param oldname  the original name of the factor.
  * @param newname  the new name of the factor.
  * @return  boolean whether method was completed succesfully.
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  public Boolean renameFactor(String columnname, String oldname, String newname) 
      throws MultimorbidityException {
    logger.info("Method renameFactor called");
    if (!oldname.equals(newname)) {
      String[] columnnames = getDataSetColumnnames();
      if (!Arrays.asList(columnnames).contains(columnname)) {
        return false;  
      }
      if (checkInput(newname)) {
        throw new MultimorbidityException("Name must be Alphanumeric"); 
      }
      String[] oldfactors = activeDataset.getFactors(columnname);
      if (Arrays.asList(oldfactors).contains(oldname)) {
        int index = Arrays.asList(oldfactors).indexOf(oldname);
        oldfactors[index] = newname;
        String expr = "levels(" + RVAR + "$'" + columnname + "')[levels(" + RVAR + "$'" 
            + columnname + "')=='" + oldname + "']<-'" + newname + "'";
        eval(expr);
        loadRData();
      } else {
        throw new MultimorbidityException("Factor names must be unique");
      }
    }
    logger.debug("Factors of columnname " + columnname + " after calling method renameFactors: " 
        + arrayOfStringsToString(activeDataset.getFactors(columnname)));
    return true;
  }

  /**
   * Converts factors to NA-values.
   * @param columnname  name of the column in which the factor will be deleted 
   * @param factor  the factor to delete.
   * @return  boolean whether method was completed succesfully.
   * @throws MultimorbidityException  a pop-up message will be shown in the gui
   */
  public Boolean deleteFactor(String columnname, String factor) throws MultimorbidityException {
    logger.info("Method deleteFactor called");
    String[] factors = activeDataset.getFactors(columnname);
    if (!Arrays.asList(factors).contains(factor)) {
      return false;
    }  
    eval(RVAR + "$'" + columnname + "'[" + RVAR + "$'" + columnname + "'==\"" + factor + "\"]<-NA");
    eval(RVAR + "$'" + columnname + "'  <- droplevels(" + RVAR + "$'" + columnname + "')");
    loadRData();
    logger.debug("Factors of columnname " + columnname + " after calling method deleteFactor for " 
        + factor + ": " + arrayOfStringsToString(activeDataset.getFactors(columnname)));
    return true;  
  }

  /**
   * Methode delete a list Factor from a column.
   * @param columnname  the name of the column where the factors will be deleted
   * @param factors  the factors to delete
   * @return  true when complete, else false
   * @throws MultimorbidityException  a pop-up message will be shown in the gui
   */
  public Boolean deleteFactor(String columnname, String[] factors) throws MultimorbidityException {
    logger.info("Method deleteFactor called");
    for (String f: factors) {
      String[] tfactors = activeDataset.getFactors(columnname);
      if (Arrays.asList(tfactors).contains(f)) {
        eval(RVAR + "$'" + columnname + "'[" + RVAR + "$'" + columnname + "'==\"" + f + "\"]<-NA");
      }
    }
    eval(RVAR + "$'" + columnname + "'  <- droplevels(" + RVAR + "$'" + columnname + "')");
    loadRData();
    return true;
  }

  /**
  * Method for loading a Dataset from a csv file.
  * Reads a csv file in R.
  * @param file  the File to load
  * @param sep  the separator
  * @param head  if it has a head or no
  * @return  boolean whether method was completed successfully
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  public Boolean importDataSet(File file, String sep, boolean head) 
      throws MultimorbidityException { 
    logger.info("Method importDataSet called");
    String tfilepath = file.getAbsolutePath().replace('\\', '/'); // get filepath as String
    String filepath = this.addSlashes(tfilepath);  
    String expr = RVAR + "<- read.csv(file=\"" + filepath + "\", header=" 
            + (Boolean.toString(head)).toUpperCase() + ", sep= \"" + sep + "\")";
    REXP rexp = eval(expr); // get REXP
    String name = file.getName();
    activeDataset = new DataSet(rexp, name);
    sessions.add(filepath);
    rdatacheck();
    addMetaData();
    this.setChanged();
    this.notifyObservers();
    logger.debug("File " + file.getAbsolutePath() + " imported as DataSet with name " 
        + activeDataset.getDataSetInstanceName());
    return true;
  }
  
  private void addMetaData() {
    String[] columns = activeDataset.getColumnnames();
    for (String s: columns) {
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
        default:
      }
    }
  }

  //Helper methodes
  private String median(String column) {
    String expr = "names(sort(table(" + RVAR + "$'" + column + "'), decreasing=T)[1])";
    REXP rexp = eval(expr, false);
    return rexp.asString();
  }
  
  //Helper methodes
  private String medianFac(String column) {
    String expr = "names(sort(summary(" + RVAR + "$'" + column + "'), decreasing=T)[1])";
    REXP rexp = eval(expr,false);
    return rexp.asString();
  }
  
  //Helper methodes 
  private double average(String column) {
    String expr = "mean(" + RVAR + "$'" + column + "')";
    REXP rexp = eval(expr, false);
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
  * Method 1 to load a DataSet via data layer.
  * @param file  the file to be loaded
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  public void loadDataSet(File file) throws MultimorbidityException { 
    logger.info("Method loadDataSet called");
    activeDataset = new DataSet();
    activeDataset.loadDataSet(file);
    sessions.add(file.getAbsolutePath());
    this.syncdata();
    this.setChanged();
    this.notifyObservers();
    logger.debug("File " + file.getAbsolutePath() + " loaded as DataSetInstance with the name " 
        + activeDataset.getDataSetInstanceName());
  }
  
  /**
  * Method for exporting a DataSet to a csv file.
  * @param location  the location where to save the file.
  * @param filename  the name of the file to save.
  */
  public void exportDataSet(String location, String filename) {
    logger.info("Method exportDataset called");
    String location2 = location.replace('\\', '/');
    String expr = "write.table(" + RVAR + ", \"" + location2 + "/" + filename 
        + ".csv\", sep=\",\",row.names = F)";
    eval(expr);
    logger.debug("File " + filename + " exported as csv to location " + location);
  }
  
  /**
  * Method for saving the datasetInstance.
  * @param file  name of file.
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  public void saveDataSet(File file) throws MultimorbidityException {
    logger.info("Method saveDataSet called");
    activeDataset.saveDataSet(file);
    /*logger.debug("Dataset " + name + " saved as after calling method saveDataSet: " 
      + activeDataset.getLastSavedFile().getAbsolutePath());*/
  }
  
  /**
   * Method used for synching the DataSet in Java with the Data in R.
   * Only works with Factorial Data.
   */
  private void syncdata() {   
    String[] columnnames = activeDataset.getColumnnames();
    int columns = columnnames.length;
    for (int i = 0; i < columns; i++) {
      StringBuilder expr = new StringBuilder();
      String[] values = activeDataset.getColumnValues(columnnames[i]);
      expr.append(columnnames[i] + "<- c(");
      String type = activeDataset.getColumnType(columnnames[i]);
      for (int j = 0; j < values.length ; j++) {
        if (j == 0) {
          if (values[j] == null || values[j].isEmpty()) {
            expr.append("NA");
          } else {
            if (type.equals("factor") || type.equals("character")) {
              expr.append("'" + values[j] + "'");
            } else if (type.equals("numeric") || type.equals("integer")) {
              expr.append(values[j]);
            } else if (type.equals("logical")) {
              if (values[j].equals("-2147483648")) {
                //expr.append("NA");
                expr.append(values[j]);
              } else {
                expr.append(values[j]);
              }
            } else if (type.equals("Date")) {
              expr.append(values[j]);
            }
          }
        } else {
          if (values[j] == null || values[j].isEmpty()) {
            expr.append(",NA");
          } else {
            if (type.equals("factor") || type.equals("character")) {
              expr.append(",'" + values[j] + "'");
            } else if (type.equals("numeric") || type.equals("integer")) {
              expr.append("," + values[j]);
            } else if (type.equals("logical")) {
              if (values[j].equals("-2147483648")) {
                //expr.append("NA");
                expr.append("," + values[j]);
              } else {
                expr.append("," + values[j]);
              }
            } else if (type.equals("Date")) {
              expr.append("," + values[j]);
            }
          }
        }
      }
      expr.append(")");
      eval(expr.toString(),false);
    }
    StringBuilder expr = new StringBuilder();
    expr.append(RVAR + " <- data.frame(");
    for (int i = 0; i < columns; i++) {
      if (i == 0) {
        expr.append(columnnames[i]);
      } else {
        expr.append("," + columnnames[i]);
      }
    }
    expr.append(")");
    eval(expr.toString(),false);
    for (String s: columnnames) {
      String type = activeDataset.getColumnType(s);
      String expr1 = "";
      if (type.equals("Date")) {
        expr1 = RVAR + "$'" + s + "'<-as.Date(" + RVAR + "$'" + s + "', origin = \"1970-01-01\")";
      } else {
        expr1 = RVAR + "$'" + s + "'<-as." + type + "(" + RVAR + "$'" + s + "')";
      }
      eval(expr1,false);
    }
  }
  
  /*
  * Undo Functionality
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
        + activeDataset.getDataSetName());
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
        + activeDataset.getDataSetName());
  }
  
  /**
  * Tests if can undo.
  * @return  true if can undo, else false
  */
  public Boolean canUndo() {
    logger.info("Method canUndo called");
    Boolean canUndo = activeDataset.canUndo();
    logger.debug("Method canUndo returned: " + canUndo);
    return canUndo;
  }
  
  /**
  * Tests if can redo.
  * @return  true if can redo, else false
  */
  public Boolean canRedo() {
    logger.info("Method canRedo called");
    Boolean canRedo = activeDataset.canRedo();
    logger.debug("Method canRedo returned: " + canRedo);
    return activeDataset.canRedo();
  }
  
  /*
   * 
   * Session Controls
   * 
   */
  
  /**
  * Method for checking the amount of sessions saved.
  * @return  the size of the session list.
  */
  public int sessionSize() {
    logger.info("Method sessionSize called");
    logger.debug("Method sessionSize returned: " + sessions.size());
    return sessions.size();
  }
  
  /**
  * Method for getting session name.
  * @param index  the index of the session name.
  * @return  a String representing the name of the session.
  */
  public String getSession(int index) {
    logger.info("Method getSessionName called");
    String path = sessions.get(index);
    logger.debug("Method getSessionName returned: " + path);
    return path;
  }
  
  /**
  * Method that returns the sessionHistory.
  * @return  the session history.
  */
  public SessionHistory getSessionHistory() {
    logger.info("Method getSessionHistory called");
    /*logger.debug("Method getSessionHistory returned: " + sessions.toString() 
      + " with size: " + sessions.size());*/
    return sessions;
  }
  
  /**
  * Method for casting a Column to a different datatype.
  * @param columnname  the name of the column to cast
  * @param type  the new datatype of the column
  * @return  a boolean whether cast was succesfull
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  public Boolean castColumn(String columnname, String type) throws MultimorbidityException {
    // the strings date and Date are excepted
    if (type.equals("date")) {
      type = "Date";
    }
    if (this.activeDataset.getType(columnname).equals(type)) {
      return false;
    }
    logger.info("Method castColumn called");
    String[] cols = getDataSetColumnnames();
    if (columnname.isEmpty() || !Arrays.asList(cols).contains(columnname)) {
      logger.debug("Method castColumn for column " + columnname + " casted to type " 
          + type + " returned " + false);
      return false;
    }
    String[] types = Column.Type.getTypes();
    if (type.isEmpty() || !Arrays.asList(types).contains(type)) {
      logger.debug("Method castColumn for column " + columnname + " casted to type " 
          + type + " returned " + false);
      return false;
    }
    if (type.equals("date") || type.equals("Date")) {
      type = "Date";
    }
    String expr = RVAR + "$'" + columnname + "'<- as." + type + "(" + RVAR + "$'" 
        + columnname + "')";
    eval(expr);
    loadRData();
    logger.debug("Method castColumn for column " + columnname + " casted to type " 
        + type + " returned " + true);
    return true;
  }

  /**
  * Method converts factors into seperate columns.
  * @param columnname  name of the column
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  public void convertFactorsToColumns(String columnname) throws MultimorbidityException {
    
    String expr = RVAR + "<- data.frame(" + RVAR + ", value = TRUE)";
    eval(expr);
    String[] columns = this.activeDataset.getColumnnames();
    StringBuilder restcolumns1 = new StringBuilder();
    Boolean flag = false;
    for (int i = 0; i < columns.length; i++) {
      if (!columns[i].equals(columnname)) {
        if (!flag) {
          restcolumns1.append(columns[i]);
          flag = true;
        } else {
          restcolumns1.append(" + " + columns[i]);
        }
      }
    }
    String expr2 = RVAR + "<- dcast(" + RVAR + "," + restcolumns1.toString() 
        + "~" + columnname + ")";
    eval(expr2);
    //String[] newcolumns = this.activeDataset.getColumnnames();
    REXP rexp = eval("colnames(" + RVAR + ")", false);
    String[] newcolumns = rexp.asStringArray();
    HashSet<String> collection1 = new HashSet<String>(Arrays.asList(columns));
    HashSet<String> collection2 = new HashSet<String>(Arrays.asList(newcolumns));
    collection2.removeAll(collection1);
    for (String c: collection2) {
      String expr3 = RVAR + "$" + "'" + c + "'" + "[is.na(" + RVAR + "$" + "'" 
          + c + "'" + ")]<- FALSE";
      eval(expr3);
    }
    loadRData();
    renameNumericColumns();
  }
  
  private void renameNumericColumns() throws MultimorbidityException {
    String[] columns = this.activeDataset.getColumnnames();
    for (String c: columns) {
      if (isNumeric(c)) {                       
        editColumnname(c,"Num" + c);
      }
    }
  }
  
  private boolean isNumeric(String str) {  
    try {  
      Double.parseDouble(str);  
    } catch (NumberFormatException nfe) {  
      return false;  
    }  
    return true;  
  }
  
  /**
  * Helper method for loading the data from R to the Java Application.
  * @throws MultimorbidityException  a pop-up message will be shown in the gui
  */
  private void loadRData() throws MultimorbidityException {                  
    REXP rexp = eval(RVAR, false);
    activeDataset.updateDataSet(rexp);         
    rdatacheck();
    addMetaData();
    this.setChanged();
    this.notifyObservers();
  }
  
  private void rdatacheck() {
    //check types
    String[] columns = activeDataset.getColumnnames();
    String[] types = new String[columns.length]; 
    for (int i = 0; i < columns.length; i++) {
      REXP rexp1 = eval("class(" + RVAR + "$'" + columns[i] + "')", false);
      types[i] = rexp1.asString();
    }
    for (int j = 0; j < columns.length; j++) {
      activeDataset.setColumnType(columns[j], types[j]);
    }
  
    //check for NA values
    Boolean[] nas = new Boolean[columns.length]; 
    for (int i = 0; i < columns.length; i++) {
      String expr = "which (is.na(" + RVAR + "$'" + columns[i] + "'))";
      REXP rexp1 = eval(expr, false);
      if (rexp1.asIntArray() != null) {
        if (rexp1.asIntArray().length > 0) {
          nas[i] = true;
        } else {
          nas[i] = false;
        }
      }
    }
    for (int j = 0; j < columns.length; j++) {
      activeDataset.setNas(columns[j], nas[j]);
    }
  }
  
  /**
   * Check whether a Column contains NA's.
   * @param columnname  the name of the column to check
   * @return  a boolean result
   */
  public Boolean hasNa(String columnname) {
    return this.activeDataset.hasNas(columnname);
  }
  
  /*
  *
  * Summary functions
  *      
  */
  
  /**
   * Method return the percentage of NA's in the DataSet.
   * @return  a String.
   */
  public String getPecentageNas() {
    String expr = "sum(is.na(" + RVAR + ")) / prod(dim(" + RVAR + "))";
    REXP rexp = eval(expr, false);
    double perc = 0;
    if (rexp != null) {
      perc = rexp.asDouble();
    }
    int temp = (int) (perc * 100000);
    double temp2 = (double)temp / 1000;
    return temp2 + "";
  }

  /**
  * Method for getting the Frequency summary about a given variable from the loaded dataset.
  * @param columnname  name of the column
  * @param level  the level
  * @return  the frequency of the given columnname and the corresponding level
  */
  public String getFrequency(String columnname, String level) {        
    // See frequencies.R 
    String request = "as.character(table(" + RVAR + "$'" + columnname + "')[" 
        + "'" + level + "'])";  
    REXP rexp = eval(request);
    if (rexp == null) {
      logger.info("Frequency request for level " + level + " of column " + columnname + " failed");
      return "";
    }
    String frequency = rexp.asString();
    logger.debug("Method getFrequency for level " + level + " of columnname " 
        + columnname + " returned: " + frequency);
    return frequency;
  }
  
  /**
  * Method for getting the Percentage summary about a given variable from the loaded dataset.
  * @param columnname  name of the column
  * @param level  the level to check
  * @return  the percentage of the given columnname and the corresponding level
  */
  public String getPercentage(String columnname, String level) {
    // See frequencies.R 
    eval("mytable <- table(" + RVAR + "$'" + columnname + "')");               
    String request = "round(prop.table(mytable)[" + "'" + level + "'], 4)";  
    REXP rexp = eval(request);
    if (rexp == null) {
      logger.debug("Method getPercentage for level " + level + " of column " 
          + columnname + " returned an empty String");
      return "";
    }
    logger.debug("Method getPercentage for level " + level + " of column " 
        + columnname + " returned: " + rexp.asString());
    double perc = rexp.asDouble();
    int temp = (int) (perc * 100000);
    double temp2 = (double)temp / 1000;
    return (temp2) + "";
  }
  
  /**
   * Method for ploting a barchart based on the summary of a specific variable
   * from the loaded dataset.
   * @param columnname  columnname from the loaded dataset 
   */
  public void barChart(String columnname) {
    logger.info("Method barChart called");
    // See charts.R 
    eval("df.freq <- table(" + RVAR + "$" + columnname + ")");                
    String request = "barplot(df.freq[order(df.freq)], horiz = T,border = NA,xlim = c(0,30000),"
                + "main = 'Title: " + columnname + " Frequency'"
                + ",xlab = '" + columnname + " Type'"
                + ",ylab = '" + columnname + " Count/perc'"
                + ",las=2)";  
    eval(request);  
    logger.debug("Method barChart for columnname " + columnname 
          + " evalutated request: " + request);
  }
  
  /**
   * Method cuts numeric values into factors.
   * @param columnname  the columnname
   * @param bounds  the bounds of the factors
   * @param labels  the new labels of the factors
   * @throws MultimorbidityException  a pop-up message will be shown in the gui
   */
  public void cut(String columnname, int[] bounds, String[] labels) throws MultimorbidityException {
    String expr = RVAR + "$'" + columnname + "' <- cut(" + RVAR + "$'" + columnname + "', ";
    expr = expr + "breaks = c(-Inf,";
    for (int i: bounds) {
      expr = expr + i + ",";
    }
    expr = expr + "Inf),labels = c(";
    for (int i = 0; i < labels.length; i++) {
      if (i == 0) {
        expr = expr + "'" + labels[i] + "'"; 
      } else {
        expr = expr + ",'" + labels[i] + "'"; 
      }
    }
    expr = expr + "),right = FALSE)";
    eval(expr);
    loadRData();
  }
  
  /**
   * Converts array of Strings to one String.
   * Method for logging purposes
   * @param arrayOfStrings  the array to be converted
   * @return  the String representation of the array
   */
  public static String arrayOfStringsToString(String[] arrayOfStrings) {
    if (arrayOfStrings == null || arrayOfStrings.length < 1) {
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
  
  /**
   * Getter for the number of Columns in a DataSet.
   * @return  the number of Columns
   */
  public int getColumnCount() {
    String expr = "ncol(" + RVAR + ")";
    REXP rexp = eval(expr, false);
    if (rexp == null) {
      return 0;
    }
    int col = rexp.asInt();         
    return col;
  }
  
  /**
   * Getter for the number of Rows in a DataSet.
   * @return  the number of Rows.
   */
  public int getRowCount() {
    String expr = "nrow(" + RVAR + ")";
    REXP rexp = eval(expr, false);
    if (rexp == null) {
      return 0;
    }
    return rexp.asInt();
  }
  
  /**
   * Method for loading the data from a Result, must be run if the data of a result is to be loaded.
   * @param result the Result object who's data to load.
   * @throws MultimorbidityException this Exception is thrown if result is null
   */
  public void loadResult(Result result) throws MultimorbidityException {
    if (result != null) {
      DataSetInstance dsi = result.getDataSetInstance();
      if (dsi != null) {
        this.activeDataset = new DataSet(dsi);
        this.syncdata();
        this.setChanged();
        this.notifyObservers();
      }
    }
  }
  
  /**
   * Checks if a DataSet has been loaded.
   * @return true if activeDataset is not null;
   */
  public Boolean isReady() {
    return (activeDataset != null);
  }
  
}
