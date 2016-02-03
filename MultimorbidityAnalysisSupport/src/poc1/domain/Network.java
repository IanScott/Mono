package poc1.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * This class repressents a Bayesian Network
 * @author Ian van Nieuwkoop, Sliman Ismaili 
 * @version 0.1
 */
public class Network extends Observable{	
	
	// Attributes
	private String algorithm;
	private List<Node> nodes;
	private List<Association> associations;
	private List<Association> arcs;
	
	/**
	 * Constructor
	 * @param algorithm the algorithm used for creating the network
	 * @param nodes the nodes in the network
	 */
	public Network(String algorithm, List<Node> nodes){
		this.algorithm = algorithm;
		this.nodes = nodes;
		this.associations = new ArrayList<Association>();
		setup();
	}
	
	/**
	 * Constructor
	 * @param algorithm the algorithm used for creating the network
	 * @param nodes the nodes in the network
	 */
	public Network(String algorithm, List<Node> nodes, List<Association> arcs){
		this.algorithm = algorithm;
		this.nodes = nodes;
		this.associations = new ArrayList<Association>();
		this.arcs = arcs;
		setup();
	}
	
	/**
	 * Getter for Algorithm used in Network
	 * @return the abreviation of the algroithm used
	 */
	public String getAlgorithm() {
		return algorithm;
	}
	
	/**
	 * Getter for Nodes
	 * @return a list of Nodes
	 */
	public List<Node> getNodes(){
		return nodes;
	}
	
	/**
	 * Getter for Associations
	 * @return list of associations
	 */
	public List<Association> getAssociations() {
        return associations; 
	}
	
	/**
	 * Method for returning Arcs
	 * @return List of Arcs
	 */
	public List<Association> getArcs(){
		return arcs;
	}
	// This method creates the associations in the network
	private void setup(){
		for(Node node: nodes){
			List<Node> parents = node.getParents();
			for(int i =0; i<parents.size(); i++){
				associations.add(new Association(node,parents.get(i)));
			}
		}
	}    
}
