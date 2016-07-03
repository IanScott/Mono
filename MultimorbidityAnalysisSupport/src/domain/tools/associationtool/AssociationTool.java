package domain.tools.associationtool;

import domain.tools.Tool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import util.MultimorbidityException;

/**
 * Class used to analyse the relation between two variables
 * using correlation and relative risk / Odds Ratio methods.
 * @author ABI team 37 
 * @version 1.0
 */
public class AssociationTool extends Tool {

  /**
   * Constant name of the Tool.
   */
  public static final String TOOLNAME = "ASSOCIATIONTOOL";
  private Logger logger = LogManager.getLogger(this.getClass());  
  private String plotType;
  private String[] summarySet;
  
  /**
   * Constructor of the AssociationTool.
   * @param rengine  the R Engine instance to use.
   */
  public AssociationTool(Rengine rengine) {
          super(TOOLNAME, rengine);
  }
  
  /**
   * Method for plotting a bar chart horizontally(H) 
   * given a variable from the loaded data set. 
   * @param columnname  the columname from the loaded data set 
   */
  public void barChartHoriz(String columnname) {
    logger.info("Method barChart called");
    if (columnname == null || !checkRColumn(columnname)) {
      return;
    }
 
    comment("# Create a frequency table");
    eval("df.freq  <- table(" + RVAR + "$"  + columnname + ")");                
    comment("# Determine the rowcount of the dataset");
    eval("rowcount <- length(" + RVAR + "$" + columnname + ")");                
    comment("# Plot the graph based on frequency table and the rowcount");
    String request = "barplot(df.freq[order(df.freq)], horiz=T,border = NA,xlim = c(0,rowcount),"
                        + "main = '" + columnname + " Frequency/Level'"
                        + ",xlab = '" + columnname + " Type'"
                        + ",las=1)";  

    eval(request);  
    logger.debug("Method barChart for columnname " 
        + columnname + " evalutated request: " + request);
    this.setChanged();
    this.notifyObservers();
  }

  /**
   * Method for plotting a bar chart vertically(V)
   * given a variable from the loaded data set. 
   * @param columnname  the columnname from the loaded data set
   */
  public void barChartVert(String columnname) {
    if (columnname == null || !checkRColumn(columnname)) {
      return;
    }
    
    comment("# Create a frequency table");
    eval("df.freq  <- table(" + RVAR + "$"  + columnname + ")");                
    comment("# Determine the rowcount");
    eval("rowcount <- length(" + RVAR + "$" + columnname + ")");                
    comment("# Plot the graph based on frequency table and the rowcount");
    String request = "barplot(df.freq[order(df.freq, decreasing = T)] , "
                       + "horiz=F,border = NA,ylim = c(0,rowcount),"
                       + "main  = '" + columnname + " Frequency / Level'"
                       + ",xlab = '" + columnname + " Type'"
                       + ",las=1)";  

    eval(request);    
    this.setChanged();
    this.notifyObservers();
  }

  /**
   * Method for plotting a scatter chart for two relations side by side
   * given two variables from the loaded data set.
   * @param columnname1  first variable from the loaded data set 
   * @param columnname2  second variable from the loaded data set 
   */
  public void chartScatter(String columnname1, String columnname2) {
    if (columnname1 == null || !checkRColumn(columnname1) || columnname2 == null 
        || !checkRColumn(columnname2)) {
      return;
    }  
    comment("# Create a chart scatter based on two columns");
    eval("cdata <- " + RVAR);
    eval("plot(cdata$" + columnname1 + ", cdata$" + columnname2 
        + ", las=1, col = c('red', 'blue'))");
    this.setChanged();
    this.notifyObservers();
  }

  /**
   * Method for viewing summary data of the loaded data set. 
   * This summary contains the mean, median,    
   * minimal, max, 1ste and 3th QU.  
   * @param assocColumn  the column from the loaded data set based 
   *     on which the summary will be retrieved
   * @throws MultimorbidityException if columns are invalid
   */
  public void startDataSummary(String assocColumn) throws MultimorbidityException {
    if (assocColumn == null || !checkRColumn(assocColumn)) {
      throw new MultimorbidityException("invalid input");
    }  
    comment("# View the association column graphically");
    barChartVert(assocColumn);
    eval("mydata <- " + RVAR + ""); 
    comment("# Retrieve the summary of the dataset");
    String request1 = "capture.output(summary(mydata))";
    REXP rexp = eval(request1,false);
    if (rexp == null) {
      logger.info("StartDataSummary for column " + assocColumn + " can not be determined");
    } else {
      String[] output = rexp.asStringArray();
      this.setAssociationSummary(output);
    }
    this.setChanged();
    this.notifyObservers();
  }

  /**
   * Method for capturing output and or result given by R after execution. 
   * The output is a list of strings. 
   * @param query  the R query or statement, based on which the summary will be done. 
   * @return the list of strings. Each string contains one (console) output line.  
   */
  @SuppressWarnings("unused")
  private String[] captureOutput(String query) {
    comment("# Capturing R output (console) using 'query' as R statement");
    REXP rexp = eval("capture.output(" + query + ")",false);
    String[] output = rexp.asStringArray();
    return output;
  }

  /**
   * Method for determining the correlation between two variables. 
   * @param assocColumn1  first  variable 
   * @param assocColumn2  second variable 
   * @param assocUseType  use-method for computing covariances in the presence of missing values
   * @param assocMethodType  correlation method used to determine the correlation
   * @throws MultimorbidityException  a pop-up will be shown in the gui
   */
  public void determineCorrelation(String assocColumn1, String assocColumn2, 
      String assocUseType, String assocMethodType) throws MultimorbidityException {
    if (assocColumn1 == null || !checkRColumn(assocColumn1)) {
      throw new MultimorbidityException("invalid input Column1");
    }
    if (assocColumn2 == null || !checkRColumn(assocColumn2)) {
      throw new MultimorbidityException("invalid input Column2");
    }
    String request = "";    
    if (assocColumn1 != null && assocColumn2 != null && !assocColumn1.isEmpty() 
        && !assocColumn2.isEmpty()) {
      if (assocColumn1.equals(assocColumn2)) {
        throw new MultimorbidityException("selected variables should be different");
      }
      eval("cdata <- " + RVAR + "");
      comment("# Plot a chart of two variables, side by side");
      eval("plot(cdata$" + assocColumn1 + ", cdata$" + assocColumn2 + ", col = c('red', 'blue'))");
      comment("# Determine the correlation between two variables");      
      request = "cor(cdata[ , '" + assocColumn1 + "'],  cdata[ , '" +  assocColumn2 + "']";
      if (assocUseType != null && !assocUseType.isEmpty()) {
        request = request + ", use='" + assocUseType + "'";
      }
      if (assocMethodType != null && !assocMethodType.isEmpty()) {
        request = request + ", method='" + assocMethodType + "'";
      }
      request = request + ")";
      comment("# capture the R console output");
      REXP rexp = eval("capture.output(" + request + ")",false);   

      if (rexp == null) {
        logger.info("determineCorrelation process can not be determined");
      } else {
        String[] output = rexp.asStringArray();
        this.setAssociationSummary(output);
      }               
    } else {
      throw new MultimorbidityException("Both variables should be filled."); 
    }
    this.setChanged();
    this.notifyObservers();
  }

  /**
   * Method for determining the correlation-test between two variables. 
   * @param assocColumn1  first  variable 
   * @param assocColumn2  second variable 
   * @param alternative  alternative hypothesis 
   * @param confLevel  confidential level, default 0.95 
   * @param assocMethodType  correlation method to determine the correlation-test
   * @throws MultimorbidityException  a pop-up will be shown in the gui
   */
  public void determineCorrelationTest(String assocColumn1, String assocColumn2, 
      String alternative, String confLevel, String assocMethodType) throws MultimorbidityException {
    if (assocColumn1 == null || !checkRColumn(assocColumn1)) {
      throw new MultimorbidityException("invalid input Column1");
    }
    if (assocColumn2 == null || !checkRColumn(assocColumn2)) {
      throw new MultimorbidityException("invalid input Column2");
    }
    String request = "";
    if (assocColumn1 != null && assocColumn2 != null && !assocColumn1.isEmpty() 
        && !assocColumn2.isEmpty()) {
      if (assocColumn1.equals(assocColumn2)) {
        throw new MultimorbidityException("selected variables should be different");
      }
      eval("cdata <- " + RVAR + "");
      comment("# Plot a scatter chart of two variables side by side");
      eval("plot(cdata$" + assocColumn1 + ", cdata$" + assocColumn2 + ", main='Scatterplot', "
          + "las=1, col = c('red', 'blue'))");
      comment("# Perform a correlation test between two variables");
      comment("# using a method, alternative Hypothesis and a confidential level (default 0.95)");
      request = "cor.test(cdata[ , '" + assocColumn1 + "'],  cdata[ , '" +  assocColumn2 + "']";
      if (assocMethodType != null && !assocMethodType.isEmpty()) {
        request = request + ", method='" + assocMethodType + "'";
        if ("spearman".equals(assocMethodType)) {
          request = request + ", exact = F";
        }
      }
      if (alternative != null && !alternative.isEmpty()) {
        if (!("not equal").equals(alternative)) {
          request = request + ", alt='" + alternative + "'";
        }
      }
      if (confLevel != null && !confLevel.isEmpty()) {
        double value;
        try {
          value = Double.parseDouble(confLevel);
        } catch (NumberFormatException nfe) {
          throw new MultimorbidityException("Confidential level should be a numeric value "
            + "between 0 and 1");
        }
        if (value >= 0  && value <= 1) {
          request = request + ", conf.level=" + confLevel;
        } else {
          throw new MultimorbidityException("Confidential level should be between 0 and 1"); 
        }       
      } else {
        request = request + ", conf.level=0.95";
      }
     
      request = request + ")";
      REXP rexp = eval("capture.output(" + request + ")",false);   
      if (rexp == null) {
        logger.info("DetermineCorrelationTest can not be determined");
      } else {
        String[] output = rexp.asStringArray();
        this.setAssociationSummary(output);
      }               
    }
    this.setChanged();
    this.notifyObservers();
  }

  /**
   * Method for determining the Relative risk (RR) and the odd Ration (OR) between two variables. 
   * @param assocColumn1  first  variable 
   * @param assocColumn2  second variable 
   * @param oddConfLevel  confidential level, default 0.95 
   * @param oddMethodType  Package/method used to calculate the RR and OR 
   * @throws MultimorbidityException  a pop-up will be shown in the gui
   */
  public void determineRRandOdd(String assocColumn1, String assocColumn2, String oddConfLevel, 
      String oddMethodType) throws MultimorbidityException {
    if (assocColumn1 == null || !checkRColumn(assocColumn1)) {
      throw new MultimorbidityException("invalid input Column1");
    }
    if (assocColumn2 == null || !checkRColumn(assocColumn2)) {
      throw new MultimorbidityException("invalid input Column2");
    }
    String request = "";
    if (assocColumn1 != null && assocColumn2 != null && !assocColumn1.isEmpty() 
        && !assocColumn2.isEmpty()) {
      if (assocColumn1.equals(assocColumn2)) {
        throw new MultimorbidityException("selected variables should be different");
      }

      eval("cdata <- " + RVAR + "");
      comment("# Create a table for the two selected variables");
      eval("cdata.tab <- table(cdata$" + assocColumn1 + ", cdata$" + assocColumn2 + ")");
      comment("# Plot a chart of two variables side by side");
      eval("barplot(cdata.tab, beside=T, legend=T, col = c('red', 'blue'), las=2)");
      if (oddMethodType != null && !oddMethodType.isEmpty()) {
        comment("# Perform the Relative Risk and the Odds Ratio of the two selected variables");
        comment("# using a confidential level (default 0.95)");
        request = "epi.2by2(cdata.tab, method='" +  oddMethodType  + "',outcome = 'as.columns'";
        if (oddConfLevel != null && !oddConfLevel.isEmpty()) {
          double value = Double.parseDouble(oddConfLevel);
          if (value >= 0  && value <= 1) {
            request = request + ", conf.level=" + oddConfLevel;
          } else {
            throw new MultimorbidityException("Confidential level should be between 0 and 1"); 
          }       
        } else {
          request = request + ", conf.level=0.95";
        }
      }
      request = request + ")";
      REXP rexp = eval("capture.output(" + request + ")",false);   
      if (rexp == null) {
        logger.info("DetermineRRandOdd can not be determined");
      } else {
        String[] output = rexp.asStringArray();
        this.setAssociationSummary(output);
      }               
    }
    this.setChanged();
    this.notifyObservers();
  }
  
  /**
   * Setter for the summarySet of the loaded data set.
   * @param summarySet  the summary of a data set as a list of strings.
   */
  public void setAssociationSummary(String[] summarySet) {
    this.summarySet = summarySet;
  }
  
  /**
  * Getter of the summarySet.
  * @return the summarySet as a list of strings.
  */
  public String[] getAssociationSummary() {
    return this.summarySet;
  }
  
  /**
   * Setter of the plot type.
   * @param plotType  the plot type that should be set
   */
  public void setPlotType(String plotType) {
    this.plotType = plotType;
  }
          
  /**
  * Getter of plot type.
  * @return plotType
  */
  public String getPlotType() {
    return this.plotType;
  }

}
