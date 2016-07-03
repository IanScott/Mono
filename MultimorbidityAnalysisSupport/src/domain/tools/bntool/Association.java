package domain.tools.bntool;

import java.io.Serializable;

/**
 * This class represents the Arc and relationships,
 * between the Nodes in the Bayesian Network.
 * @author ABI team 37
 * @version 1.0
 */
public class Association implements Serializable {
  
  private static final long serialVersionUID = 7891359635654905244L;
  
  //Attributes
  private Node child;
  private Node parent;

  /**
   * Constructor of Association object.
   * @param child  the origin of the association
   * @param parent  the destination of the association
   */
  public Association(Node child, Node parent) {
    this.parent = parent;
    this.child = child;
  }

  /**
   * Getter for Child Node.
   * @return the child node
   */
  public Node getChild() {
    return child;
  }

  /**
   * Getter for Parent Node.
   * @return the Parent Node
   */
  public Node getParent() {
    return parent;
  }

  /**
   * Method for converting object to String.
   */
  @Override
  public String toString() { 
    return "(" + child + ", " + parent + ")"; 
  }

}
