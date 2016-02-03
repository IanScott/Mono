package domain.tools.bntool;

import domain.tools.bntool.Association;
import domain.tools.bntool.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
//import java.util.Observable;

/**
 * This class repressents a Bayesian Network
 * @author ABI team 37
 * @version 0.1
 */
public class Network extends Observable implements Serializable{

  /**
	 * 
	 */
	private static final long serialVersionUID = 4897448589919019763L;
// Attributes
  private String algorithm;
  private List<Node> nodes;
  private List<Association> associations;
  private List<Association> arcs;

  /**
   * Constructor 1 of Network object.
   * @param algorithm the algorithm used for creating the network
   * @param nodes the nodes in the network
   */
  public Network(String algorithm, List<Node> nodes) {
    this.algorithm = algorithm;
    this.nodes = nodes;
    this.associations = new ArrayList<Association>();
    setup();
  }
  
  /**
   * Constructor 2 of Network object.
   * @param algorithm the algorithm used for creating the network
   * @param nodes the nodes in the network
   */
  public Network(String algorithm, List<Node> nodes, List<Association> arcs) {
    this.algorithm = algorithm;
    this.nodes = nodes;
    this.associations = new ArrayList<Association>();
    this.arcs = arcs;
    setup();
  }
  
  /**
   * Getter for algorithm used in Network.
   * @return the abreviation of the algroithm used
   */
  public String getAlgorithm() {
    return algorithm;
  }
  
  /**
   * Getter for nodes.
   * @return a list of Nodes
   */
  public List<Node> getNodes() {
    return nodes;
  }

  /**
   * Getter for associations.
   * @return list of associations
   */
  public List<Association> getAssociations() {
    return associations; 
  }
  
  /**
   * Method for returning Arcs.
   * @return List of arcs
   */
  public List<Association> getArcs() {
    return arcs;
    
  }
  
  // PRIVAT METHOD(s)

  /**
   * Creates the associations in the network.
   */
  private void setup() {
    for (Node node: nodes) {
      List<Node> parents = node.getParents();
      for (int i = 0; i < parents.size(); i++) {
        associations.add(new Association(node,parents.get(i)));
      }
    }
  }    
  
}
