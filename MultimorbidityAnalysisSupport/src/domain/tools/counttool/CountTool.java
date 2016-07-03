package domain.tools.counttool;

import domain.tools.Tool;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;
import util.MultimorbidityException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

/**
 * This is a basic Tool which count diseases and groups them.
 * This class can also be used to do a GLM analyses.
 * @author ABI team 37
 * @version 1.0
 */
public class CountTool extends Tool {
  
  /**
   * Constant, Name of Tool.
   */
  public static final String TOOLNAME = "COUNTTOOL";
  private static final String COUNT = "Count";
  private static final int THREE = 3;
  private static final int FOUR = 4;
  private String[] levels;
  private int[] count;
  private HashMap<String,int[]> groupCount;
  private String[][] glmtable;
  private String[] glmhead;
  private Logger logger = LogManager.getLogger(this.getClass());  
  private String[] summary;

  /**
   * Constructor.
   * @param rengine  the Rengine to use
   */
  public CountTool(Rengine rengine) {
          super(CountTool.TOOLNAME,rengine);
  }

  /**
   * This Method calculates the Count values
   * This Method must run before: 
   * getCountTableHead, getCountTableValues, getGroupTableHead, getGroupTableValues.   
   * @param groupColumn  the column to group by
   * @param diseases  the Columns to count
   * @throws MultimorbidityException if Selected columns don't exist in R
   */
  public void executeCount(String groupColumn, String[] diseases) throws MultimorbidityException {        
    logger.info("Method execute called");       
    if (diseases == null || diseases.length < 1) {
      return;
    }
    if (!checkRColumn(groupColumn) || !checkRColumn(diseases)) {
      throw new MultimorbidityException("Columns don't exist in R");
    }  
    createTemp(diseases);
    eval(RVAR1 + "[is.na(" + RVAR1 + ")]<- FALSE");
    String expr1 = COUNT + "<-";
    for (int i = 0; i < diseases.length; i++) {
      if (i == 0) {
        expr1 = expr1 + RVAR1 + "$" + diseases[i];
      } else {
        expr1 = expr1 + " + " + RVAR1 + "$" + diseases[i];
      }
    }
    eval(expr1);
    String expr3 = "tdata <- data.frame(table(" + COUNT + "))";
    eval(expr3);
    REXP rexp = eval("levels(tdata$Count)", false);
    levels = rexp.asStringArray();
    REXP rexp1 = eval("tdata$Freq");
    count = rexp1.asIntArray();   
    /////Part 2
    if (groupColumn == null) {
      groupCount = new HashMap<String,int[]>();
      return;
    }
    String expr5 = "md <- data.frame(" + RVAR + "$" + groupColumn + ", Count)";
    eval(expr5);
    String expr6 = "tdf <- table(md)";
    eval(expr6);
    REXP temp1 = eval("levels(" + RVAR + "$" + groupColumn + ")", false);
    String[] factors = temp1.asStringArray();
    String[][] values = new String[factors.length][count.length];
    groupCount = new HashMap<String,int[]>();
    for (int i = 0; i < factors.length; i++) {
      int[] temp = new int[count.length];
      for (int j = 0; j < count.length; j++) {
        REXP rexp2 = eval("as.character(tdf['" + factors[i] + "'," + (j + 1) + " ])",false);
        values[i][j] = rexp2.asString();
        temp[j] = Integer.parseInt(rexp2.asString());
      }
      groupCount.put(factors[i], temp);
    }
    this.setChanged();
    this.notifyObservers(1);
  }

  /**
   * Method returns the Count Table Head.
   * @return String[]  containing count table head values.
   */
  public String[] getCountTableHead() {
    String[] thead = {"Disease Count","Total"};
    return thead;
  }
  
  /**
   * Method return the Count Table Values.
   * @return String[][]  containing count table values.
   */
  public String[][] getCountTableValues() {
    if (count == null) {
      return null;
    }
    String[][] values = new String[count.length][2];
    
    for (int i = 0; i < count.length; i++) {
      values[i][0] = levels[i];
      values[i][1] = count[i] + "";
    }
    
    return values;
  }

  /**
   * Method return the Group Table Head.
   * @return String[] containing Group table head values.
   */
  public String[] getGroupTableHead() {
    if (count == null) {
      return null;
    }
    String[] ghead = new String[count.length + 1];
    ghead[0] = "Factors";
    for (int i = 1; i < ghead.length; i++) {
      ghead[i] = i - 1 + "";
    }
    return ghead;
  }

  /**
   * Method return the Group Table Values.
   * @return String[][]  containing Group table values.
   */
  public String[][] getGroupTableValues() {
    if (count == null) {
      return null;
    }
    if (groupCount == null) {
      groupCount = new HashMap<String,int[]>();
    }
    Set<String> keys = groupCount.keySet();
    String[] factors = keys.toArray(new String[keys.size()]);
    Arrays.sort(factors);
    
    String[][] values = new String[factors.length][count.length + 1];
    for (int i = 0; i < factors.length; i++) {
      int[] intarray = groupCount.get(factors[i]);
      values[i][0] = factors[i];
      for (int j = 1; j < intarray.length + 1; j++) {
        values[i][j] = intarray[j - 1] + "";
      }
    }
    return values;
  }

  /**
   * This Method calculates the Generalized Linear Models values.
   * This Method must run before: 
   * getGLMHead, getGLMTable.
   * @param dependent  the column to analyzes how dependent it is on the other columns
   * @param others  the columns to analyze with the dependent column
   * @throws MultimorbidityException if columns don't exist in R
   */
  public void executeGLM(String dependent, String[] others) throws MultimorbidityException {
    logger.info("Method execute called");   
    if (others == null || others.length < 1) {
      return;
    }
    if (!checkRColumn(dependent) || !checkRColumn(others)) {
      throw new MultimorbidityException("Columns don't exist in R");
    } 
    String objects = "~";
    for (int i = 0; i < others.length; i++) {
      if (i == 0) {
        objects = objects + others[i];
      } else {
        objects = objects + " + " + others[i];
      }

    }
    String expr = "temp <- glm(" + dependent + objects + ", data = " 
        + RVAR + ", family = 'binomial')";
    
    eval(expr);   
    String expr1 = "temp2 <-exp(cbind(OR=coef(temp), confint(temp)))";
    REXP rexp = eval(expr1);
    String expr2 = "attributes(temp$coefficients)";
    REXP rexp2 = eval(expr2,false);
    RVector rvec = rexp2.asVector();
    @SuppressWarnings("unchecked")
    Vector<String> names = rvec.getNames();
    REXP rexp3 = rvec.at(names.get(0));
    
    String[] attr = rexp3.asStringArray();
    double[] list = rexp.asDoubleArray();
    String[][] table = new String[attr.length][FOUR];
    
    int len = attr.length;
    for (int i = 0; i < len; i++) {      
      table[i][0] = attr[i];
      table[i][1] = list[i] + "";
      table[i][2] = list[i + len] + "";
      table[i][THREE] = list[i + len + len] + ""; 
    }
    
    String[] temp = {"", "OR","2,5%", "97,5%"};
    glmhead = temp;
    this.glmtable = table;
    summary();   
    this.setChanged();
    this.notifyObservers(2);
  }
 
  private void summary() {
    REXP rexp4 = eval("capture.output(temp)",false);    
    this.summary = rexp4.asStringArray();    
    eval("plot(temp2)",false);
  }
  
  
  /**
   * Method returns the recorded summary.
   * @return String[]  containing summary
   */
  public String[] getSummary() {
    if (this.summary != null) {
      return this.summary;
    } else {
      return new String[0];
    }
  }

  /**
   * Method return the table head values.
   * @return String[]  head values
   */
  public String[] getGLMHead() {
    return glmhead;
  }

  /**
   * Method return the table values.
   * @return String[]  table values
   */
  public String[][] getGLMTable() {
    return this.glmtable;
  }

  /**
   * Method used to plot the count data.
   */
  
  public void plotGLM() {
    eval("plot(temp2)");
    this.setChanged();
    this.notifyObservers(2);
  }

  /**
   * Method used to plot the count data.
   */
  public void plot1() {
    eval("plot(tdata)");
  }

  /**
   * Method used to plot the group by data.
   */
  public void plot2() {
    eval("plot(tdf)");
  }
  
}

