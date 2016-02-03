package poc1.domain;

/**
 * This class represents the Arc and relationships 
 * between the Nodes in the Bayesian Network
 * @author Ian van Nieuwkoop
 * @version 0.1
 * 
 */
public class Association {
	//Attributes
	private Node child;
    private Node parent;
    
    /**
     * Constructor
     * @param child the origin of the association
     * @param parent the destination of the association
     */
    public Association(Node child, Node parent) {
    	this.parent = parent;
    	this.child = child;
	}
    
    /**
     * Getter for Child Node 
     * @return the child node
     */
    public Node getChild(){
    	return child;
    }
    
    /**
     * Getter for Parent Node
     * @return the Parent Node
     */
    public Node getParent(){
    	return parent;
    }
    
    /**
     * Method for converting object to String
     */
    public String toString(){ 
    	return "(" + child + ", " + parent + ")"; 
    }
}
