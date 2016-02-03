package domain.tools.bntool;

import domain.DataSet;
import domain.Result;
import domain.tools.Tool;
import domain.tools.ToolArgument;
import domain.tools.bntool.BnConvert;
import domain.tools.bntool.BnResult;
import domain.tools.bntool.Network;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
//import java.io.File;

import java.util.ArrayList;
import java.util.List;
//import java.util.Observable;

/**
 * Class representing a Bayesian network.
 * @author ABI team 37
 * @version 0.2
 */
public class BnTool extends Tool{
  
	//namae
	public static final String TOOLNAME = "BNTOOL";
  // Constants
  private static final String RVAR = "MyData"; //Data variable in R
  private static final String[] ALGOR = {"gs", "iamb","hc", "tabu","mmhc"};
  
  // Attributes
  private Network bnnetwork;
  private List<Network> bnnetworks;
  private Rengine rengine;
  //private DataSetController dsController;
  
  /**
   * Constructor of BnTool.
   */
  public BnTool(Rengine ren) {
	  super(BnTool.TOOLNAME);
	  this.bnnetworks = new ArrayList<Network>();
	  this.rengine = ren;       
  }
  
 
  @Override
  public BnResult execute() {
    this.bnnetworks = new ArrayList<Network>();
    for (int i = 0; i < ALGOR.length; i++) {
      learn(ALGOR[i]);
      fitNetwork();
    }
    
    // TODO Auto-generated method stub
    return new BnResult(bnnetworks, this);
  }

  @Override
  public Result execute(ToolArgument object) {
    // TODO Auto-generated method stub
    return null;
  }
  
  // PRIVAT METHOD(s)
  /**
   * Method for learning a Bayesian Network.
   * @param algorithm the algorithm to use
   */
  private void learn(String algorithm) {
    String rvariable = "bn." + algorithm; // the variable in R
    REXP data = rengine.eval(RVAR); // Retreaving data
    REXP network = rengine.eval(rvariable + "<-" + algorithm + "(" + RVAR + ")" );
    // comment to previous line creating Bayesian Network in R
    rengine.eval("plot(" + rvariable + ")"); // plotting Bayesian Network
    Network bnnwb = BnConvert.toBnNetwork(data, network); // converting to Java BNNetwork
    this.bnnetwork = bnnwb; // setting last created BNNetwork
  }
  
  /**
   *  Method for adding fitted values to a Bayesian Network.
   * @param network The Bayesian Network
   */
  private void fitNetwork(Network network) {
    String algorithm = network.getAlgorithm(); // algorithm used
    String rvariable = "bn." + algorithm; // the bn.xx variable in R
    REXP fitted = rengine.eval("res <- bn.fit(" + rvariable + "," + RVAR + ")");
    // comment to previous line: getting probability tables
    BnConvert.fit(this.bnnetwork, fitted); // fit the selected network
    bnnetworks.add(this.bnnetwork); // adding to list of created fitted BNNetworks
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
  private Network getBnNetwork() {
    return bnnetwork;
  }
}
