package domain.tools;

import domain.Audit;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

import java.util.Arrays;
import java.util.List;

/**
 * An abstract class that represents a "Tool".
 * A Tool has a connection to the R Engine and performs all the mathematical calculations.
 * @author ABI team 37
 * @version 1.0
 */
public abstract class Tool extends Audit{
  /**
  * Data variable in R.
  */
  protected static final String RVAR = "MyData"; 
  /**
   * Data variable in R.
   */
  protected static final String RVAR1 = "MyData1"; 
  /**
   * Name of the tool.
   */
  private String name;
  
  /**
   * Constructor.
   * @param name  the Name of the Tool to create.
   * @param rengine  the R engine object the tool uses for its calculations.
   */
  public Tool(String name, Rengine rengine) {
    this.name = name;
    this.setRengine(rengine);
  }
  
  /**
   * Getter for the name attribute.
   * @return  a String representing the name of the tool.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Method creates a temporary new variable "MyData1" in the R Engine 
   * which is a near copy of MyData, only containing selected columns. 
   * @param columns  the columns to copy to MyData1.
   */
  protected void createTemp(String[] columns) {
    String temp = "";
    for (int i = 0; i < columns.length; i++) {
      String expr = columns[i] + "<-" + RVAR + "$" + columns[i];
      eval(expr);
    }
    for (int i = 0; i < columns.length; i++) {
      if (i == 0) {
        temp = temp + columns[i]; 
      } else {
        temp = temp + "," + columns[i];
      } 
    }
    String expr = RVAR1 + " <- data.frame(" + temp + ")";
    eval(expr);
  }
  
  /**
   * Method checks if Column is a existing column in R.
   * @param col the column to check
   * @return true if column exists else false
   */
  protected Boolean checkRColumn(String col) {
    REXP rCols = eval("colnames(" + RVAR + ")", false);
    String[] cols = rCols.asStringArray();
    List<String> collist = Arrays.asList(cols);
    
    if (collist.contains(col)) {
      return true;
    }
    return false;   
  }
  
  /**
   * Method checks if Column is a existing column in R.
   * @param columns the columns to check
   * @return true if column exists else false
   */
  protected Boolean checkRColumn(String[] columns) {
    REXP rcols = eval("colnames(" + RVAR + ")", false);
    String[] cols = rcols.asStringArray();
    List<String> collist = Arrays.asList(cols);
    for (String col: columns) {
      if (!collist.contains(col)) {
        return false;
      }
    }
    return true;
  }
}
