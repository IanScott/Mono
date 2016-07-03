package domain.tools.bntool;

import data.BnResultMapper;
import domain.DataSet;
import domain.DataSetInstance;
import domain.tools.Tool;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import util.MultimorbidityException;

import java.io.File;

/**
 * Class representing a Bayesian network.
 * @author ABI team 37
 * @version 1.0
 */
public class BnTool extends Tool {
  /**
   * Constant Tool Name.
   */
  public static final String TOOLNAME = "BNTOOL";
  /**
   * Available score types.
   */
  public static final String[] SCORE = {"k2","bde","bdes","mbde","aic","bic","loglik",
    "bge","loglik-g","aic-g","bic-g","loglik-cg","aic-cg","bic-cg"};
  
  private static final int HUNDRED = 100;
  // Attributes
  private Network bnnetwork;
  private String rvariable;
  private BnResult bnresult;
  
  /**
   * Constructor of BnTool.
   * @param rengine the REngine to use
   */
  public BnTool(Rengine rengine) {
    super(BnTool.TOOLNAME,rengine);      
  }
  
 /**
  * Method which creates a fitted Bayesian Network.
  * @param algor the algorithm to use.
  * @param columns the names of the columns to use.
  * @throws MultimorbidityException throws this Exception
  */
  public void execute(String algor, String[] columns) throws MultimorbidityException {
    if (columns == null || columns.length < 2) {
      return;
    }
    if (!checkRColumn(columns)) {
      throw new MultimorbidityException("Columns don't exist in R");
    } 
    createTemp(columns);
    learn(algor);
    fitNetwork();
    REXP rexp = eval(RVAR, false);
    DataSetInstance dsi = DataSet.toDataSetInstace("BNTool", rexp);
    this.bnresult = new BnResult(this.bnnetwork, dsi);
    this.setChanged();
    this.notifyObservers();
  }
  
  /**
   * Getter for thet Result object.
   * @return  a BnResult representing the data and Bayesian Network.
   */
  public BnResult getBnResult() {
    return this.bnresult;
  }
  
  /**
   * Method for checking to see if a BnNetwork has been generated.
   * @return  true if there is a BnNetwork loaded, else false.
   */
  public Boolean hasNetwork() {
    if (this.bnnetwork == null) {
      return false;
    }
    return true;
  }
  
  /**
   * Method for checking to see if a BnResult has been generated.
   * @return  true is there is a BnResult loaded, else false.
   */
  public Boolean hasBNResult() {
    if (this.bnresult == null) {
      return false;
    }
    return true;
  }
  
  // PRIVATE METHOD(s)
  /**
   * Method for learning a Bayesian Network.
   * @param algorithm the algorithm to use
   */
  private void learn(String algorithm) {
    REXP network;
    if (algorithm != null) {
      rvariable = "bn." + algorithm; // the variable in R
      network = eval(rvariable + "<-" + algorithm + "(" + RVAR1 + ")" ); 
    } else {
      network = eval(rvariable);
    }
    comment("# delete '#' in the next line to plot network");
    String expr = "#plot(" + rvariable + ")";
    comment(expr);
    REXP data = eval(RVAR1,false); // Retreaving data
    Network bnnwb = BnConvert.toBnNetwork(data, network); // converting to Java BNNetwork
    this.bnnetwork = bnnwb; // setting last created BNNetwork
    if (this.bnresult != null) {
      this.bnresult = new BnResult(bnnetwork,bnresult.getDataSetInstance());
    }
  }
  
  
  /**
   * Method for adding fitted values to a Bayesian Network.
   * @param network  the Bayesian Network
   */
  private void fitNetwork(Network network) {
    String expr = "res <- bn.fit(" + rvariable + "," + RVAR1 + ")";
    REXP fitted = eval(expr);
    // comment to previous line: getting probability tables
    BnConvert.fit(this.bnnetwork, fitted); // fit the selected network
  }
  
  /**
   * Method for adding fitted values to the selected Bayesian Network.
   */
  private void fitNetwork() {
    fitNetwork(this.bnnetwork);
  }
  
  /**
   * Method returns the selected Bayesian Network.
   * @return bayesian network
   */
  public Network getBnNetwork() {
    return bnnetwork;
  }
  
  /**
   * Method checks if the Tool has an active fitted network or not.
   * @return  true if network is fitted, else false.
   */
  public Boolean hasFittedNetwork() {
    if (bnnetwork != null) {
      return bnnetwork.isFitted();
    } else {
      return false;
    }
  }
  
  /**
   * Method calculates the score of the Bayesian Network.
   * @param type  the type of score to calculate, returns zero if score is invalid
   * @return  an int representing the score value
   */
  public double scoreNetwork(String type) {
    String expr = "score(" + rvariable + ", " + RVAR1 + ", type ='" + type + "')";
    REXP rexp = eval(expr);
    double temp = 0;
    if (rexp != null) {
      temp = rexp.asDouble(); 
    }
    return Math.floor(temp * HUNDRED) / HUNDRED;
  }
  
  /**
   * Method for editing an Arc of a Network.
   * @param edit  the type of alteration, only drop, reverse and set allowed
   * @param nodeA  node a
   * @param nodeB  node b
   * @throws MultimorbidityException if action causes invalid state
   */
  public void editArc(String edit, String nodeA, String nodeB) throws MultimorbidityException {
    if (!(edit.equals("drop") || edit.equals("set") || edit.equals("reverse"))) {
      return;
    }
    if (nodeA == null || nodeB == null) {
      return;
    }
    if (nodeA.equals(nodeB)
        || !bnnetwork.containsNode(nodeA) || !bnnetwork.containsNode(nodeB)) {
      return;
    }
    String expr = rvariable + "<-" + edit + ".arc(" + rvariable + ",'" 
        + nodeA + "','" + nodeB + "')";
    REXP test = eval(expr);
    if (test == null) {
      throw new MultimorbidityException("Action causes invalid state");
    }
    learn(null);
    fitNetwork(bnnetwork);
    this.setChanged();
    this.notifyObservers();
  }
  
  /**
   * Method to export a Net file from R.
   * @param path to save file
   */
  public void exportNetwork(String path) {
    String expr = "write.net(" + path + ", res)";
    eval(expr);
  }
  
  /**
   * Method for saving a BnResult.
   * @param file the file to save the result to.
   * @throws MultimorbidityException  a pop-up will be shown in the gui
   */
  public void saveResult(File file) throws MultimorbidityException {
    if (bnresult != null) {
      bnresult.saveBnResult(file);
    }
  }
  
  /**
   * Method for loading a Result.
   * @param file  the location to load.
   * @throws MultimorbidityException  a pop-up will be shown in the gui
   */
  public void loadResult(File file) throws MultimorbidityException {
    this.bnresult = BnResultMapper.loadBnResult(file);
    this.bnnetwork = bnresult.getNetwork();
    this.setChanged();
    this.notifyObservers();
  }
  
}
