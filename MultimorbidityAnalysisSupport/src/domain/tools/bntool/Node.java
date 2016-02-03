package domain.tools.bntool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a Node in a Bayesian network
 * @author ABI team 37
 * @version 0.1
 */
public class Node implements Serializable{

  /**
	 * 
	 */
	private static final long serialVersionUID = -3711415459592325494L;
//Attributes
  private String name;
  private List<String> levels;
  private Cpt cpt;
  private List<Node> children;
  private List<Node> parents;
  
  /**
   * Constructor 1 of Node object without parameter(s).
   */
  public Node() {
    this.levels = new ArrayList<String>();
    this.children = new ArrayList<Node>();
    this.parents = new ArrayList<Node>();
  }
  /**
   * Constructor 2 of Node object with String parameter "name".
   * @param name Name of the Node
   */
  public Node(String name) {
    this.levels = new ArrayList<String>();
    this.children = new ArrayList<Node>();
    this.parents = new ArrayList<Node>();
    this.name = name;
  }
  
  /**
   * Getter Name of the node.
   * @return name of node
   */
  public String getNodeName() {
    return name;
  }
  
  /**
   * Method adds parent to node.
   * @param pn Parent Node
   */
  public void addParentnode(Node pn) {
    parents.add(pn);
  }
  
  /**
   * Method adds a child to the node.
   * @param cn Child Node
   */
  public void addChildnode(Node cn) {
    children.add(cn);
  }
  
  /**
   * Getter for Parent Nodes.
   * @return List of Parent Nodes
   */
  public List<Node> getParents() {
    return parents;
  }
  
  /**
   * Getter for Children Nodes.
   * @return list of Children Nodes
   */
  public List<Node> getChildren() {
    return children;
  }
  
  /**
   * Getter for Node Levels.
   * @return list of Levels 
   */
  public List<String> getNodeLevels() {
    return levels;
  }       
  
  /**
   * Setter for Node Name.
   * @param name Name of Node
   */
  public void setNodeName(String name) {
    this.name = name;
  }
  
  /**
   * Method adds a Node Level to the Node.
   * @param level the Level name to add
   */
  public void addNodeLevel(String level) { 
    levels.add(level);
  }
  
  /**
   * Method returns a representation of the Nodes Cpt.
   */
  public Cpt getCpt() {
    return cpt;
  }
  
  /**
   * Method sets the nodes Cpt Tables.
   * @param cpt the cpt table to be used
   */
  public void setCpt(double[] cpt) { 
    String[] nlevels = new String[levels.size()];
    for (int i = 0; i < levels.size(); i++) {
      nlevels[i] = levels.get(i);
    }
    this.cpt = new Cpt(nlevels, cpt);
  }
  
  /**
   * Method returns a String representation of the object.
   *  @return a String representation of the object
   */
  public String toString() {
    return this.name;
  }
}
