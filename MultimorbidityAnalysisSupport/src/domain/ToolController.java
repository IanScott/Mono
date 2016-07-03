package domain;

import domain.tools.Tool;
import domain.tools.associationtool.AssociationTool;
import domain.tools.bntool.BnTool;
import domain.tools.clustertool.ClusterTool;
import domain.tools.counttool.CountTool;
import domain.tools.datatool.DataTool;
import domain.tools.factoranalysetool.FactoranalyseTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rosuda.JRI.Rengine;
import util.MultimorbidityException;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Class that represents ToolController. 
 * This Class is responsible for the initialization 
 * of the R Engine instance and the Tools used by the by the application.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class ToolController extends Audit {

  // Attributes
  private Rengine rengine;
  private DataTool datatool;
  private HashMap<String,Tool> tools;
  private Logger logger = LogManager.getLogger(this.getClass());  
  
  /**
   * Constructor of ToolController.
   * Creates a new DataSetController
   * Makes a connection with R via a Rengine object
   * Loads the necessary R libraries
   * Creates the setup for the different tools
   */
  public ToolController() {
    logger.info("Constructor ToolController called (without parameters)");
    this.connectRengine();
    this.loadRengineLibraries();
    this.setupDataTool();
    this.setupTools();
  }
  
  /**
   * Initializes the rengine object,
   * without reading user file to set environment variables,
   * without reading the site-wide profile at startup,
   * without reading the user's profile at startup,
   * without starting event loop.
   */
  private void connectRengine() {
    logger.info("Private method connectRengine called");
    String[] dummyArgs = {"--vanilla"};
    this.rengine = new Rengine(dummyArgs, false, null);
    setRengine(this.rengine);
    logger.debug("Application connected with R: " + isConnectedWithR());
  }
  
  private void loadRengineLibraries() {
    logger.info("Private method loadLibraries called");
    // Loading required R libraries
    comment("#################### MultiMorbidity Tool       ########################");
    comment("#################### Loading Libraries Started ########################");
    comment("# Don't forget to use install.packages() if needed, "
        + "remove '#' on the following lines if needed.");
    comment("#install.packages('bnlearn')");
    comment("#install.packages('reshapes')");
    comment("#install.packages('lavaan')");
    comment("#install.packages('epiR')");

    eval("library(bnlearn)"); //Bayesian Network
    eval("library(JavaGD)",false); // Plot in Java
    eval("library(reshape2)"); // edit main data
    eval("library(lavaan)"); // Factor analysis - CFA
    eval("library(epiR)"); // Odd Ration and RR
    // Setting up JavaGD in R
    eval("Sys.setenv('JAVAGD_CLASS_NAME'='javaGD/JavaGDPanel')", false);
    eval("JavaGD()", false);
    //String[] libraries = eval("(.packages())", false).asStringArray();
    comment("#################### Loading Libraries Finished ########################");
    //Checking if all libraries have loaded
    checkLoadedLibraries();
  }
  
  private void checkLoadedLibraries() {
    String[] libraries = eval("(.packages())", false).asStringArray();
    comment("#################### Loading Libraries Finished ########################");
    //Checking if all libraries have loaded
    logger.debug("R libraries bnlearn loaded: " + Arrays.asList(libraries).contains("bnlearn"));
    logger.debug("R libraries JavaGD loaded: " + Arrays.asList(libraries).contains("JavaGD"));
    logger.debug("R libraries reshape2 loaded: " + Arrays.asList(libraries).contains("reshape2"));
    logger.debug("R libraries epiR loaded: " + Arrays.asList(libraries).contains("epiR"));
    logger.debug("R libraries lavaan loaded: " + Arrays.asList(libraries).contains("lavaan"));
    
    Boolean flag = false;
    String error = "The following libraries have failed to load:/n";
    if (!Arrays.asList(libraries).contains("bnlearn")) {
      flag = true;
      error = error + "bnlearn\n";
    }
    if (!Arrays.asList(libraries).contains("JavaGD")) {
      flag = true;
      error = error + "JavaGD\n";
    }
    if (!Arrays.asList(libraries).contains("reshape2")) {
      flag = true;
      error = error + "reshape2\n";
    }
    if (!Arrays.asList(libraries).contains("epiR")) {
      flag = true;
      error = error + "epiR\n";
    }
    if (flag) {
      logger.error(error);
    }
    
  }
  
  private void setupDataTool() {
    this.datatool = new DataTool(rengine);
    this.tools = new HashMap<String, Tool>();
    tools.put(DataTool.TOOLNAME, datatool);
  }
  
  private void setupTools() {
    logger.info("Private method setupTools called");
    
    //Setup bnTool and add to tools
    Tool bntool = new BnTool(rengine);
    tools.put(BnTool.TOOLNAME, bntool);
    
    //Setup countTool and add to tools
    Tool counttool = new CountTool(rengine);
    tools.put(CountTool.TOOLNAME, counttool);
    
    //Setup clusterTool and add to tools
    Tool clustertool = new ClusterTool(rengine);
    tools.put(ClusterTool.TOOLNAME, clustertool);
    logger.debug("Tools setup for " + tools.values().toString()); 
    //previous line: Temporary only String representation of object

    //Setup associationTool and add to tools
    Tool associationtool = new AssociationTool(rengine);
    tools.put(AssociationTool.TOOLNAME, associationtool);
    logger.debug("Tools setup for " + tools.values().toString()); 
    //previous line: Temporary only String representation of object

    //Setup factoranalyseTool and add to tools
    Tool factoranalysetool = new FactoranalyseTool(rengine);
    tools.put(FactoranalyseTool.TOOLNAME, factoranalysetool);
    logger.debug("Tools setup for " + tools.values().toString()); 
    //previous line: Temporary only String representation of object
  }
  
  /*
   * Getters
   */
  
  /**
   * Getter for getting the DataTool instance.
   * Object used for editing the main data.
   * @return  the active datatool instance
   */
  public DataTool getDataTool() {
    return this.datatool;
  }
  
  /**
   * Getter for a Tool object.
   * @param name  the name of the Tool to return
   * @return  a Tool object
   */
  public Tool getTool(String name) {
    return tools.get(name);
  }
  
  /**
   * Getter of the Rengine object.
   * Method necessary for test purposes
   * @return  the Rengine object
   */
  public Rengine getRengine() {
    return rengine;
  }
  
  /**
   * Confirmation of connection with R.
   * Method necessary for test purposes
   * @return  true if connected with R, else false
   */
  public boolean isConnectedWithR() {
    if (getRengine() == null) {
      return false;
    }
    return true;
  }
  
  /**
   * Used for test purposes.
   * Getter for getting all the Tools
   * @return  a Map of all the available tools.
   */
  public HashMap<String,Tool> getTools() {
    logger.info("Method getTools called");
    logger.debug("Method getTools returned: " + tools.toString()); 
    //previous line: Temporary only String representation of object
    return tools;
  }
  
  /**
   * Converts array of Strings to one String.
   * Method for logging purposes
   * @param arrayOfStrings the array to be converted
   * @return the String representation of the array
   */
  public static String arrayOfStringsToString(String[] arrayOfStrings) {
    if (arrayOfStrings.length < 1) {
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
 
}