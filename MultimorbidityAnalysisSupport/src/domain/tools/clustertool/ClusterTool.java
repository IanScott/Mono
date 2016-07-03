package domain.tools.clustertool;

import domain.tools.Tool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import util.MultimorbidityException;

/**
 * Class representing the clustering tool.
 * using clustering to cluster variables that have common attributes.
 * @author ABI team 37
 * @version 1.0
 */
public class ClusterTool extends Tool {
  /**
  * Name of the Tool.
  */
  public static final String TOOLNAME = "CLUSTERTOOL";
  private Logger logger = LogManager.getLogger(this.getClass());  
  private String plotType;
  private String[] summarySet;
  private String[] selectedNodes;
  
  /**
   * Constructor.
   * @param rengine  the R Engine instance to use.
   */
  public ClusterTool(Rengine rengine) {
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
   * @param columnname from the loaded data set 
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
   * Method for plotting a scatter chart for two relations side by side
   * given two variables from the loaded data set.
   * @param columnname1  first variable from the loaded data set 
   * @param columnname2  second variable from the loaded data set 
   */
  public void barChartGrouped(String columnname1, String columnname2) {
    if (columnname1 == null || !checkRColumn(columnname1) || columnname2 == null 
        || !checkRColumn(columnname2)) {
      return;
    }  

    eval("MyData <- " + RVAR);
    comment("# create first contingency table");
    String request0 = "ctg_table <- table(MyData$" + columnname1 + ", MyData$" + columnname2 + ")";
    comment("# Create a chart based on two columns, side by side");
    String request = "barplot(ctg_table, beside = T ,"
                 + "legend.text=c('Geen " + columnname1 + "', 'Wel " + columnname1 + "'),"
                 + "main = 'Relatie tussen " + columnname1 + " en " + columnname2 + "',"
                 + "xlab='" + columnname1 + "', las=1, col=c(2,4))";
    eval(request0); 
    eval(request);  
  }
  

  /**
   * Method for plotting a graph that will help to determine the best
   * number of clusters that can be used during clustering. 
   * @param columnname  columnname from the loaded data set used for clustering
   * @param selectedNodes  contains variables based on which clustering will be done
   * @throws MultimorbidityException  a pop-up will be shown in the gui
   */
  public void calculateNumberOfClusters(String columnname, String[] selectedNodes) 
      throws MultimorbidityException {
    if (columnname == null || !checkRColumn(columnname)) {
      throw new MultimorbidityException("invalid columnname");
    } 
    comment("# Initialize the plot area");
    eval("old.par <- par(mar = c(0, 0, 0, 0))");
    eval("par(old.par)");
    eval("plot('')",false);
    
    if (selectedNodes.length < 2) {
      throw new MultimorbidityException("not enough variables selected");
    }
    
    createTemp(selectedNodes);
    eval("mydata <- " + RVAR1); 
    comment("# Help determining the number of clusters using 'k-means' and the 'withinss'");
    String request0 = "mydata$" + columnname  + " = NULL ";        
    String request1 = "wss <- (nrow(mydata)-1)*sum(apply(mydata,2,var))";
    String request2 = "capture.output(for (i in 2:round(nrow(mydata)/2)) wss[i] <- "
        + "sum(kmeans(mydata, centers=i)$withinss))";
    
    eval(request0);
    eval(request1);
    eval(request2,false);
    comment("# Plot the number of cluster on a graph.");
    String request3 = "plot(1:round(nrow(mydata)/2), wss, type='b', xlab='Number of Clusters', "
        + "ylab='Within groups sum of squares')";
    REXP rexp = eval(request3);
    if (rexp == null) {
      logger.info("Calculation of the number of clusters can not be determined");
    }
  }

  /**
    * Method for viewing summary data of the attributes of the 'kmeans' 
    * object based on given number of clusters.  
    * @param clusterColumn  from the loaded data set, based on which clustering will be done. 
    * @param numbClusters  the number of clusters given by the user. 
    * @param attribute  kmeans-object attributes, selected by a user. 
    * @throws MultimorbidityException  a pop-up will be shown in the gui
    */
  public void startClusterSummary(String clusterColumn, String numbClusters, 
      String attribute) throws MultimorbidityException {
    if (clusterColumn == null || !checkRColumn(clusterColumn)) {
      throw new MultimorbidityException("invalid columnname");
    }
    calculateNumberOfClusters(clusterColumn,selectedNodes);
    String request1 = "";
    String request2 = "";
    String request3 = "";
    String request4 = "";
    int value;
    try {
      value = Integer.parseInt(numbClusters);
    } catch (NumberFormatException nfe) {
      throw new MultimorbidityException("Number Clusters must be an integer");
    }
    if (value <= 0) {
      throw new MultimorbidityException("Number of clusters should be higher than 0 "
          + "and must not exceed the number of observations."); 
    }       
    comment("# Extract the clustering result based on the number of clusters.");
    comment("# and retrieve the summary of an attribute from the extracted result.");    
    request1 = "results <- kmeans(mydata, " + numbClusters + ")";
    if (attribute.equals("Cluster assignment")) {
      request2 = "mydata.features <- data.frame(mydata, results$cluster)";
      request3 = "plot(mydata, col = results$cluster)";
      request4 = "capture.output(mydata.features)";

    } else {
      request2 = "resultAttr <- results$"  + attribute;
      request3 = "barplot(resultAttr)";           
      request4 = "capture.output(resultAttr)";
    }
    eval(request1);
    eval(request2);
    eval(request3);
    REXP rexp = eval(request4,false);
    if (rexp == null) {
      logger.info("StartClusterSummary can not be determined");
    } else {
      String[] output = rexp.asStringArray();
      this.setClusterSummary(output);
    }
    this.setChanged();
    this.notifyObservers();
  }

  /**
   * Method for starting the cluster process.    
   * @param clusterColumn  column from the loaded data set, based on which clustering will be done 
   * @param numbClusters  the number of clusters given by the user 
   * @param method  which clustering method will be used for clustering 
   * @param selectedNodes the nodes to be used
   * @throws MultimorbidityException  a pop-up will be shown in the gui
   */
  public void startClusterProcess(String clusterColumn, String numbClusters, String method, 
      String[] selectedNodes) throws MultimorbidityException {
    
    if (clusterColumn == null || !checkRColumn(clusterColumn)) {
      throw new MultimorbidityException("invalid columnname");
    }  
    try {
      Integer.parseInt(numbClusters);
    } catch (NumberFormatException nfe) {
      throw new MultimorbidityException("Number Clusters must be an integer");
    }
    String request = "";
    calculateNumberOfClusters(clusterColumn, selectedNodes) ;
    
    if (method.equals("Hierarchical clustering-kmeans")) {
      comment("# Start Hierarchical clustering-kmeans.");
      String request1 = "";
      String request2 = "";
      String request3 = "";
      comment("# set up the dataframe based on the result from determining the Number of clusters:");
      comment("# find the distance matrix between the variables that will be clustered");
      comment("# and then apply the hierarchical clustering method and plot the clusters");

      request1 = "cdata.features <- mydata";
      request2 = "d <- dist(as.matrix(cdata.features))";              
      request3 = "d <- dist(cdata.features)";
      String request4 = "";
      request4 = "hc <- hclust(d)";
      eval(request1);
      eval(request2);
      eval(request3);
      eval(request4);
      request = "plot(hc)" ;
    }
    
    if (method.equals("Hierarchical agglomeration")) {
      comment("# Start Hierarchical agglomeration");
      int value = Integer.parseInt(numbClusters);
      if (value <= 0) {
        throw new MultimorbidityException("Number of clusters should be higher than 0 "
            + "and must not exceed the number of observations."); 
      }       
      comment("# create distance matrix for each pair of features, using euclidean method");
      comment("# Use library 'hcust' for clustering, method = ward");
      comment("# cut tree into n clusters based on th entered number of clusters");
      comment("# draw dendogram with red borders around the n clusters");
      
      // create distance matrix for each pair of features
      eval("d <- dist(cdata.features, method = 'euclidean')"); 
      eval("d <- dist(as.matrix(cdata.features))"); 
      eval("plot(d)");

      // Use library "hcust" for clustering, method = ward 
      eval("fit <- hclust(d, method='ward.D')"); 
      eval("plot(fit)");

      // cut tree into n clusters
      String req1 = "groups <- cutree(fit, k=" + numbClusters + ")"; 
      eval(req1); 
      
      request = "rect.hclust(fit, k=" + numbClusters + ", border = 'red')";
      //draw dendogram with red borders around the 5 clusters 
    }
    
    
    if (method.equals("Model based Clustering")) {
      comment("# Start Model based Clustering");
      eval("mydataBIC <- mclustBIC(mydata)");
      request = "plot(mydataBIC)";
    }
    
    REXP rexp = eval(request);   
    if (rexp == null) {
      logger.info("Clustering process can not be determined");
    }
  }

  /**
   * Method for viewing the general summary data of the loaded dataset. 
   * The summary contains the mean, median, minimal, max, 1ste and 3th QU.  
   * @param clusterColumn a variable from the loaded data set. 
   */
  public void startDataSummary(String clusterColumn) {
    if (clusterColumn == null || !checkRColumn(clusterColumn)) {
      return;
    }  
    comment("# Plot a chart of the clustering column");
    barChartVert(clusterColumn);
    eval("mydata <- MyData"); 
    comment("# capture the summary - R console");
    String request1 = "capture.output(summary(mydata))";
    REXP rexp = eval(request1);
    if (rexp == null) {
      logger.info("StartDataSummary can not be determined");
    } else {
      String[] output = rexp.asStringArray();
      this.setClusterSummary(output);
    }
    this.setChanged();
    this.notifyObservers();
  }

  /**
   * Setter of the summarySet.
   * @param summarySet  the summary set of the cluster
   */
  public void setClusterSummary(String[] summarySet) {
    this.summarySet = summarySet;
  }

  /**
   * Getter of summarySet.
   * @return  summarySet
   */
  public String[] getClusterSummary() {
    return this.summarySet;
  }

  /**
   * Getter of selectedNodes.
   * @return  selectedNodes
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
   * Setter of the plotType.
   * @param plotType  the plot type
   */
  public void setPlotType(String plotType) {
    this.plotType = plotType;
  }

  /**
   * Getter of plotType.
   * @return plotType
   */
  public String getPlotType() {
    return this.plotType;
  }
}
