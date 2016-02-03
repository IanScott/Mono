package poc2.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

/**
 * System Class
 * @author Ian van Nieuwkoop
 * @version 0.2
 */
public class BNTool extends Tool{
	// Constants
	private static final String RVAR = "MyData"; //Data variable in R
	private static final String[] ALGOR = {"gs", "iamb","hc", "tabu","mmhc"};
	
	
	// Attributes
	private Network bnnetwork;
	private List<Network> bnnetworks;
	private Rengine rengine;
	//private DataSetController dsController;
	
	/*
	 * Constructor
	 */
	public BNTool(String name, Rengine r, DataSetController d){
		super(name);
		this.name = "BNTool";
		this.bnnetworks = new ArrayList<Network>();
		//this.dsController = d;
		this.rengine = r;	
	}
	
	
	/**
	 * Method for learning a Bayesian Network
	 * @param algorithm the algorithm to use
	 */
	private void learn(String algorithm){
		String rVariable = "bn."+algorithm; 											// the variable in R
		REXP data = rengine.eval(RVAR);													// Retreaving data
		REXP network = rengine.eval(rVariable + "<-" + algorithm + "(" + RVAR + ")" ); 	// creating Bayesian Network in R
		rengine.eval("plot(" + rVariable + ")");										// plotting Bayesian Network
		Network bnnwb = BNConvert.toBNNetwork(data, network);							// converting to Java BNNetwork
		this.bnnetwork = bnnwb;															// setting last created BNNetwork
		//Notify Observing objects
		//dsController.updateDataSet(ds);
		//this.notifyObservers();
	}
	
	/**
	 *  Method for adding fitted values to a Bayesian Network
	 * @param network The Bayesian Network
	 */
	private void fitNetwork(Network network){
		String algorithm = network.getAlgorithm();										// algorithm used
		String rVariable = "bn."+algorithm; 											// the bn.xx variable in R
		REXP fitted = rengine.eval("res <- bn.fit(" + rVariable + "," + RVAR + ")");	// getting probability tables
		BNConvert.fit(this.bnnetwork, fitted);											// fit the selected network
		bnnetworks.add(this.bnnetwork);													// adding to list of created fitted BNNetworks
		//Notify Observing objects
		//this.setChanged();;
		//this.notifyObservers();
	}
	
	/**
	 * Method for adding fitted values to the selected Bayesian Network
	 */
	private void fitNetwork(){
		fitNetwork(this.bnnetwork);
	}
	
	
	
	/**
	 * Method returns the selected Bayesian Network
	 * @return
	 */
	private Network getBNNetwork(){
		return bnnetwork;
	}





	@Override
	public BNResult execute() {
		this.bnnetworks = new ArrayList<Network>();
		for(int i = 0; i <ALGOR.length; i++){
			learn(ALGOR[i]);
			fitNetwork();
		}
		
		
		// TODO Auto-generated method stub
		return new BNResult(bnnetworks, this);
	}



	@Override
	public Result execute(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
