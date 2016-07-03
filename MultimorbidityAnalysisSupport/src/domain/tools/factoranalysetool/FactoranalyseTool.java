package domain.tools.factoranalysetool;

import domain.tools.Tool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import util.MultimorbidityException;

/**
 * Class used to perform the factor analysis
 * using Explanatory and Confirmatory factor analysis.
 * @author ABI team 37
 * @version 1.0
 */
public class FactoranalyseTool extends Tool {

  /**
   * Constant name of the Tool.
   */
  public static final String TOOLNAME = "FACTORTOOL";
  private Logger logger = LogManager.getLogger(this.getClass());  
  private String plotType;
  private String[] summarySet;
  private String[] selectedNodes;

  /**
   * Constructor.
   * @param rengine  the R Engine instance to use.
   */
  public FactoranalyseTool(Rengine rengine) {
    super(TOOLNAME, rengine);
  }

  /**
   * Method for plotting a bar chart horizontally(H) 
   * given a variable from the loaded data set. 
   * @param columnname  columnname from the loaded data set 
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
    logger.debug("Method barChart for columnname " + columnname 
        + " evalutated request: " + request);
  }

  /**
   * Method for plotting a bar chart vertically(V) 
   * given a variable from the loaded data set. 
   * @param columnname  columnname from the loaded data set 
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
  }
  
  /**
   * Method for plotting a bar chart for two relations side by side
   * from the loaded data set.
   * @param columnname1  first column from the loaded dataset
   * @param columnname2  second column from the loaded dataset
   */
  public void barChartGrouped(String columnname1, String columnname2) {
    if (columnname1 == null || !checkRColumn(columnname1) || columnname2 == null 
        || !checkRColumn(columnname2)) {
      return;
    } 
    // create first contingency table 
    comment("# create first contingency table");
    eval("ctg_table <- table(" + RVAR + "$" + columnname1 + ", " + RVAR + "$" + columnname2 + ")"); 
    comment("# Create a chart based on two columns, side by side");
    String request = "barplot(ctg_table, beside = T ,"
                 + "legend.text=c('Geen " + columnname1 + "', 'Wel " + columnname1 + "'),"
                 + "main = 'Relatie tussen " + columnname1 + " en " + columnname1 + "',"
                 + "xlab='" + columnname1 + "', las=1, col=c(2,4))";
    eval(request);  
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
    eval("plot(cdata$" + columnname1 + ", cdata$" + columnname2 + ", col = c('red', 'blue'))");
  }
  
  /**
   * Method for viewing summary data of the loaded data set. 
   * This summary contains the mean, median,    
   * minimal, max, 1ste and 3th QU.  
   * @param column  the column from the loaded data set based on which the summary will be retrieved
   * @throws MultimorbidityException when columns are invalid 
   */
  public void startDataSummary(String column) throws MultimorbidityException {
    if (column == null || !checkRColumn(column)) {
      throw new MultimorbidityException("invalid columnname");
    }
    comment("# Plot a chart of the selected column");
    barChartVert(column);
    eval("mydata <- " + RVAR,false); 
    comment("# capture the summary - R console");
    String request1 = "capture.output(summary(mydata),)";
    REXP rexp = eval(request1,false);
    if (rexp == null) {
      logger.info("StartDataSummary can not be determined");
    } else {
      String[] output = rexp.asStringArray();
      this.setSummary(output);
    }
  }

  /**
   * Method for determining the number of feasible factors that can be used for FA. 
   * @param selectedNodes  only the selected nodes by a user will be used for FA  
   * @throws MultimorbidityException  pop-up in the gui
   */
  public void determineNumbOfFactors(String[] selectedNodes) throws MultimorbidityException {
    if (selectedNodes == null) {
      return;
    }
    if (selectedNodes.length <= 2) {
      throw new MultimorbidityException("not enough variables selected");
    }
    createTemp(selectedNodes);
    eval("cdata <- MyData1");
    comment("# Help determine the number of factors by performing a principal components analysis ");
    comment("# on the given numeric data matrix and returns the results as an object of class princomp");
    comment("# and then viewing the corresponding plot.");    
    eval("cdata.pca <- princomp(cdata)");
    eval("plot(cdata.pca)");
    String request = "";   
    request = "summary(cdata.pca)";
    REXP rexp = eval("capture.output(" + request + ")",false);   

    if (rexp == null) {
      logger.info("DetermineNumbOfFactors process can not be ran");
    } else {
      String[] output = rexp.asStringArray();
      this.setSummary(output);
    }       
    this.setChanged();
    this.notifyObservers();
  }
  
  /**
   * Method for performing the Explanatory Factor analysis EFA. 
   * @param numbOfFactors  the number of factors that should examined for EFA  
   * @param efaRotation  the rotation method used for EFA  
   * @param selectedNodes  only the selected nodes by a user will used for FA  
   * @throws MultimorbidityException  pop-up in the gui
   */
  public void startEfa(String numbOfFactors, String efaRotation, String[] selectedNodes) 
      throws MultimorbidityException {
    if (numbOfFactors == null || selectedNodes == null ) {
      return;
    }
    int numfactors;
    try {
      numfactors = Integer.parseInt(numbOfFactors);
    } catch (NumberFormatException nfe) {
      throw new MultimorbidityException("Number Factors must be an integer");
    }  
    String request = "";
    
    if (numbOfFactors != null && !numbOfFactors.isEmpty()) {
      if (numfactors <= 0) {
        throw new MultimorbidityException("Number of factors should be higher than 0 "
            + "and must not exceed the number of selected variables.");
      }         
      this.determineNumbOfFactors(selectedNodes);
      if (selectedNodes.length - numfactors <=  numfactors) {
        throw new MultimorbidityException("Not enough variables selected "
            + "or the numbers of factors is much higher than expected.");
      }
      comment("# Start the EFA - explanatory factor analysis");
      comment("# using a given number of factors and a rotation method");
      if (efaRotation == null) {
        eval("cdata.fa1 <- factanal(cdata, factors=" +  numbOfFactors + ")");
      } else {
        eval("cdata.fa1 <- factanal(cdata, factors=" +  numbOfFactors + ", rotation ='" 
            +  efaRotation + "')");                                   
      }
      request = "cdata.fa1";
      REXP rexp = eval("capture.output(" + request + ")",false);   
      if (rexp == null) {
        logger.info("EFA can not be started");
      } else {
        String[] output = rexp.asStringArray();
        this.setSummary(output);
      }
      this.setChanged();
      this.notifyObservers();
    } else {
      logger.info("DetermineCorrelationTest can not be determined");
    }
  }

  /**
   * Method for retrieving the summary after execution of the EFA. 
   * @param numbOfFactors  the number of factors that should examined for EFA  
   * @param efaRotation  the rotation method used for EFA  
   * @param selectedNodes  only the selected nodes by a user will used for FA  
   * @throws MultimorbidityException  pop-up in the gui
   */
  public void efaSummary(String numbOfFactors, String efaRotation, String[] selectedNodes) 
      throws MultimorbidityException {
    if (selectedNodes == null) {
      return;
    }
    String request = "";
    if (numbOfFactors != null && !numbOfFactors.isEmpty()) {
      this.determineNumbOfFactors(selectedNodes);
      comment("# Retrieve the summary info prior conducting an EFA");
      comment("# using a given number of factors and a rotation method");
      if (efaRotation == null) {
        eval("cdata.fa1 <- factanal(cdata, factors=" +  numbOfFactors + ", scores='regression')");
      } else {
        eval("cdata.fa1 <- factanal(cdata, factors=" +  numbOfFactors + ", rotation='" 
            +  efaRotation + "', scores='regression')");                                      
      }
      request = "cdata.fa1$score";
      REXP rexp = eval("capture.output(" + request + ")",false);   
      if (rexp == null) {
        logger.info("EFA score can not be determined");
      } else {
        String[] output = rexp.asStringArray();
        this.setSummary(output);
      }   
      this.setChanged();
      this.notifyObservers();
    } else {
      logger.info("EFA scores can not be determined");
    }
  }

  /**
   * Method for conducting the Confirmatory Factor analysis : CFA. 
   * @param numericList  only the selected nodes by a user will used for FA  
   * @throws MultimorbidityException  pop-up in the gui
   */
  public void startCfa(String[] numericList) throws MultimorbidityException {
    if (numericList == null) {
      return;
    }
    // merge all numeric columnames by adding a "+" between them.
    String elemFactor = "";
    for (int i = 0; i < numericList.length; i++) {
      elemFactor = elemFactor + numericList[i];       
      if (i + 1 != numericList.length) {
        elemFactor = elemFactor + "+";                                  
      }
    }

    eval("cdata <- " + RVAR);
    comment("# Start the CFA - confirmatory factor analysis");
    comment("# using a model containing the latent variable which is built from the selected variables");
    eval("model1 <- 'LATENTVARIABLE =~ " + elemFactor + "'");
    comment("# fit the model using the dataset");
    eval("data.fit <- cfa(model1, data = cdata)");
    comment("# Retrieve the summary on the the fitted Model");    
    String request = "summary(data.fit, standardized=T, fit.measures=T)";
    REXP rexp = eval("capture.output(" + request + ")",false);   
    if (rexp == null) {
      logger.info("CFA can not be started");
    } else {
      String[] output = rexp.asStringArray();
      this.setSummary(output);
    }   
    this.setChanged();
    this.notifyObservers();
  }

  /**
   * Getter of selectedNodes.
   * @return selectedNodes
   */
  public String[] getSelectedNodes() {
    return this.selectedNodes;
  }

  /**
   * Setter of the selectedNodes selected by a user.
   * @param selectedNodes  the selected nodes
   */
  public void setSelectedNodes(String[] selectedNodes) {
    this.selectedNodes = selectedNodes;
  }
  
  /**
   * Setter for the summarySet of the loaded dataset.
   * @param summarySet  the summary of a dataset as a list of strings.
   */
  public void setSummary(String[] summarySet) {
    this.summarySet = summarySet;
  }

  /**
  * Getter of the summarySet.
  * @return  the summarySet as a list of strings.
  */
  public String[] getSummary() {
    return this.summarySet;
  }

  /**
   * Setter of the plot type.
   * @param plotType  the plot type
   */
  public void setPlotType(String plotType) {
    this.plotType = plotType;
  }

  /**
  * Getter of plot type.
  * @return  plotType
  */
  public String getPlotType() {
    return this.plotType;
  }

}

