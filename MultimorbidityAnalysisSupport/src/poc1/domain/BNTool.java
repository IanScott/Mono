package poc1.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

/**
 * System Class
 * @author Ian van Nieuwkoop
 * @version 0.1
 */
public class BNTool extends Observable{
	// Constants
	private static final String RVAR = "MyData"; //Data variable in R
	
	// Attributes
	private Network bnnetwork;
	private List<Network> bnnetworks;
	private Rengine rengine;
	private DataSet dataset;
	
	/*
	 * Constructor
	 */
	public BNTool(){
		this.bnnetworks = new ArrayList<Network>();
		this.dataset = new DataSet();
		setup();	
	}
	
	private void setup(){
		// Initialising the R engine
		String[] dummyArgs = {"--vanilla"};
		rengine = new Rengine(dummyArgs, false, null);
		
		// Loading required R libraries
		rengine.eval("library(bnlearn)");
		rengine.eval("library(JavaGD)");
		
		// Setting up JavaGD in R
		rengine.eval("Sys.setenv('JAVAGD_CLASS_NAME'='org/ou/domain/BNJavaGD')");
		rengine.eval("JavaGD()");
	}
	
	// only setup for csv files!
	/**
	 * Method for loading your dataset
	 * @param file the File to load
	 */
	public void loadDataSet(File file){
		String tfilepath = file.getAbsolutePath().replace('\\', '/');										// get filepath as String
		String filepath = this.addSlashes(tfilepath);
		REXP rexp = rengine.eval(RVAR+"<- read.csv(file=\"" + filepath + "\", header=TRUE, sep=\",\")"); 	// get REXP
		DataSet tdataset = BNConvert.toDataSet(rexp); 														// Convert rexp to a dataset
		dataset.setAll(tdataset.getColumnnames(), tdataset.getValues());										// Update DataSet
	}
	
	/**
	 * Method for learning a Bayesian Network
	 * @param algorithm the algorithm to use
	 */
	public void learn(String algorithm){
		String rVariable = "bn."+algorithm; 											// the variable in R
		REXP data = rengine.eval(RVAR);													// Retreaving data
		REXP network = rengine.eval(rVariable + "<-" + algorithm + "(" + RVAR + ")" ); 	// creating Bayesian Network in R
		rengine.eval("plot(" + rVariable + ")");										// plotting Bayesian Network
		Network bnnwb = BNConvert.toBNNetwork(data, network);							// converting to Java BNNetwork
		this.bnnetwork = bnnwb;															// setting last created BNNetwork
		//Notify Observing objects
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 *  Method for adding fitted values to a Bayesian Network
	 * @param network The Bayesian Network
	 */
	public void fitNetwork(Network network){
		String algorithm = network.getAlgorithm();										// algorithm used
		String rVariable = "bn."+algorithm; 											// the bn.xx variable in R
		REXP fitted = rengine.eval("res <- bn.fit(" + rVariable + "," + RVAR + ")");	// getting probability tables
		BNConvert.fit(this.bnnetwork, fitted);											// fit the selected network
		bnnetworks.add(this.bnnetwork);													// adding to list of created fitted BNNetworks
		//Notify Observing objects
		this.setChanged();;
		this.notifyObservers();
	}
	
	/**
	 * Method for adding fitted values to the selected Bayesian Network
	 */
	public void fitNetwork(){
		fitNetwork(this.bnnetwork);
	}
	
	/**
	 * Method for cleaning the dataset
	 * Only setup for removing N/A values
	 */
	public void cleanData(){
		// get REXP
		rengine.eval("MyData[MyData==\"N/A\"]<-NA");
		rengine.eval("MyData <- MyData[complete.cases(MyData), ]");
		REXP rexp = rengine.eval("MyData <- droplevels(MyData)");
		// Convert rexp to a dataset
		DataSet tdataset = BNConvert.toDataSet(rexp);
				
		// Update DataSet
		dataset.setAll(tdataset.getColumnnames(), tdataset.getValues());	
	}
	
	/**
	 * Getter for the DataSet Attribut
	 * @return a DataSet object
	 */
	public DataSet getDataSet(){
		return dataset;
	}
	
	/**
	 * Method returns the selected Bayesian Network
	 * @return
	 */
	public Network getBNNetwork(){
		return bnnetwork;
	}
	
	
	// Method not used yet
	private String addSlashes(String paramString)
	  {
	    if (paramString == null) {
	      return "";
	    }
	    StringBuffer localStringBuffer = new StringBuffer(paramString);
	    for (int i = 0; i < localStringBuffer.length(); i++) {
	      if (localStringBuffer.charAt(i) == '"') {
	        localStringBuffer.insert(i++, '\\');
	      } else if (localStringBuffer.charAt(i) == '\'') {
	        localStringBuffer.insert(i++, '\\');
	      }
	    }
	    return localStringBuffer.toString();
	  }
	
}
