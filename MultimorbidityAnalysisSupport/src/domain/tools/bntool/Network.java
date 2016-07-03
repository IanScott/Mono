package domain.tools.bntool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;


/**
 * This class represents a Bayesian Network.
 * @author ABI team 37
 * @version 1.0
 */
public class Network extends Observable implements Serializable{

  private static final long serialVersionUID = 4897448589919019763L;
  private String algorithm;
  private List<Node> nodes;
  private List<Association> associations;
  private List<Association> arcs;
  private Boolean fitted;
  
  /**
   * Constructor 1 of Network object.
   * @param algorithm  the algorithm used for creating the network
   * @param nodes  the nodes in the network
   */
  public Network(String algorithm, List<Node> nodes) {
    this.algorithm = algorithm;
    this.nodes = nodes;
    this.associations = new ArrayList<Association>();
    this.fitted = false;
    setup();
  }
  
  /**
   * Constructor 2 of Network object.
   * @param algorithm  the algorithm used for creating the network
   * @param nodes  the nodes in the network
   * @param arcs  the arcs in the network
   */
  public Network(String algorithm, List<Node> nodes, List<Association> arcs) {
    this.algorithm = algorithm;
    this.nodes = nodes;
    this.associations = new ArrayList<Association>();
    this.arcs = arcs;
    this.fitted = false;
    setup();
  }
  
  /**
   * Getter for algorithm used in Network.
   * @return  the abbreviation of the algorithm used
   */
  public String getAlgorithm() {
    return algorithm;
  }
  
  /**
   * Getter for nodes.
   * @return  a list of Nodes
   */
  public List<Node> getNodes() {
    return nodes;
  }

  /**
   * Getter for associations.
   * @return  list of associations
   */
  public List<Association> getAssociations() {
    return associations; 
  }
  
  /**
   * Method for returning Arcs.
   * @return  list of arcs
   */
  public List<Association> getArcs() {
    return arcs;
    
  }
  
  /**
   * Method checks if the Node is contained in the network.
   * @param nodeName  the name of the node to check
   * @return  true if network contains node, else false.
   */
  public Boolean containsNode(String nodeName) {
    Set<String> setNodes = new HashSet<String>();
    for (Node n: nodes) {
      setNodes.add(n.getNodeName());
    }
    if (setNodes.contains(nodeName)) {
      return true;
    }
    return false;
  }
  
  /**
   * Sets the fitted flag to true.
   */
  public void setFitted() {
    this.fitted = true;
  }
  
  /**
   * Method returns whether network was succesfully fitted or not.
   * @return  true if fitted, else false.
   */
  public Boolean isFitted() {
    return this.fitted;
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
