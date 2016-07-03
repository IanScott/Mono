package domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class representing the DataSet.
 * @author ABI team 37
 * @version 1.0
 */
public class DataSetInstance implements Serializable {
  private static final int TEN = 10;
  private static final long serialVersionUID = -4980879919806006795L;
  private String name;
  private HashMap<String,Column> columns;
  private String[] columnindex;
  
  /**
   * Constructor.
   * @param name  the name of the dataSetInstance
   * @param columns  a array of Column to add to the datasetinstance
   */
  public DataSetInstance(String name,Column[] columns) {
    this.name = name;
    this.columns = new HashMap<String, Column>();
    this.columnindex = new String[columns.length];
    for (int i = 0; i < columns.length; i++) {
      columnindex[i] = columns[i].getName();
    }
    for (Column c: columns) {
      this.columns.put(c.getName(), c);
    }
  }
 
  /**
   * Getter for the Columnnames.
   * @return  String[] of Columnnames
   */
  public String[] getColumnnames() {
    return columnindex;
  }
  
  /**
   * Getter for the column Values of a column.
   * @param columnname  the name of the column who's values to return
   * @return  a arrar of columnvalues
   */
  public String[] getColumnValues(String columnname) {
    return columns.get(columnname).getValues();
  }
  
  /**
   * Method returns the maximum amount of row a DataInstance has.
   * @return  a in representing the max rows
   */
  public int getMaxRows() {
    int max = 0;
    for (Column c: columns.values()) {
      int temp = c.getValues().length;
      if (temp > max) {
        max = temp;
      }
    }
    return max;
  }
  
  /**
   * Getter for the factors used by a column.
   * @param columnname  name of the column who's factor to return
   * @return  a array of factors
   */
  public String[] getFactors(String columnname) {
    return columns.get(columnname).getFactors();
  }
  
  /**
   * Returns the name of the DataSet.
   * @return  name of DataSet
   */
  public String getName() {
    if (name == null) {
      return (Math.random() * TEN) * (Math.random() * TEN) + "";
    }
    return name;
  }
  
  /**
   * Method checks is a column is a certain datatype.
   * @param columnName  the column to check
   * @param type  datatype to check
   * @return  boolean true if column and type match, otherwise false
   */
  public Boolean isTypeColumn(String columnName, Column.Type type) {
    Column column = columns.get(columnName);
    if (column != null) {
      if (column.getType() == type) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Setter for a Column.
   * @param columnname  the name of the column who's name to set
   * @param newName  the new Name of the column
   */
  public void setColumnName(String columnname, String newName) {
    columns.get(columnname).setName(newName);
    for (int i = 0; i < this.columnindex.length; i++) {
      if (columnindex[i].equals(columnname)) {
        columnindex[i] = newName;
        Column column = columns.remove(columnname);
        columns.put(newName, column);
        return;
      }
    }
  }
  
  /**
   * Getter for the datatype of a column.
   * @param columnname  name of the column to get the type of
   * @return  a string representing the type of the column
   */
  public String getColumnType(String columnname) {
    Column column = columns.get(columnname);
    if (column != null && column.getType() != null) {
      return column.getType().toString();     
    } else {
      return "";
    }
  }
  
  /**
   * Setter for the classification attribute of a column.
   * @param column  the column to set
   * @param clazz  new classification
   */
  public void setClassification(String column, String clazz) {
    columns.get(column).setClassification(clazz);   
  }
  
  /**
   * Getter for the classification attribute of a column.
   * @param column  the column to get
   * @return  a Column.Enum representing the attribute value
   */
  public Column.Class getClassification(String column) {
    return columns.get(column).getClassification();
  }
  
  /**
   * Setter for the average attribute of a column.
   * @param column the column to set.
   * @param av double the value to set the average attribute to.
   */
  public void setAverage(String column,double av) {
    columns.get(column).setAverage(av);
  }
  
  /**
   * Getter for the average attribute of a column.
   * @param column  the column to get
   * @return  a double representing the attribute value
   */
  public double getAverage(String column) {
    return columns.get(column).getAverage();
  }

  /**
   * Getter for the median attribute of a column.
   * @param column  the column to get
   * @return  a String representing the attribute value
   */
  public String getMedian(String column) {
    return columns.get(column).getMedian();
  }
  
  /**
   * Setter for the median attribute of a column.
   * @param column the column to set
   * @param med the value to set the median attribute to
   */
  public void setMedian(String column,String med) {
    columns.get(column).setMedian(med);
  }
  
  /**
   * Method returns a list of the available types.
   * @return  a String array of datatypes
   */
  public String[] getTypes() {
    return Column.Type.getTypes();
  }
  
  /**
   * Method returns a list of the available Classes.
   * @return  a String array of classification types
   */
  public String[] getClasses() {
    return Column.Class.getClasses();
  }
  
  /**
   * Sets the columntype.
   * @param columnname  the column where the type should be set
   * @param type  type that should be set to the column
   */
  public void setColumnType(String columnname, String type) {
    columns.get(columnname).setType(type);
  }

  @Override
  public DataSetInstance clone() {
    Collection<Column> tempColumns = columns.values();
    Iterator<Column> it = tempColumns.iterator();
    Column[] ccolumns = new Column[tempColumns.size()];
    
    int inr = 0;
    while (it.hasNext()) {
      ccolumns[inr] = it.next();
      inr++;
    }
    DataSetInstance dsi = new DataSetInstance(name,ccolumns);
    dsi.columnindex = this.columnindex.clone();
    return dsi;
  }
  
  /**
   * Getter for the Columns.
   * @return  the columns
   */
  public HashMap<String,Column> getColumns() {
    return columns;
  }
  
  /**
   * Method checks if Columns contain NA's.
   * @param columnname  the name of the column to check
   * @return  a boolean depending on the check
   */
  public Boolean hasNas(String columnname) {
    return columns.get(columnname).hasNas();
  }
  
  /**
   * Setter for the NA attribute of a column.
   * @param columnname  the name of the column
   * @param  nas the boolean values to be set
   */
  public void setNas(String columnname, Boolean nas) {
    columns.get(columnname).setNas(nas);
  }
}
