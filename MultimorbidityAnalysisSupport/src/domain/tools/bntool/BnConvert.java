package domain.tools.bntool;

import domain.tools.bntool.Association;
import domain.tools.bntool.Network;
import domain.tools.bntool.Node;

import org.rosuda.JRI.REXP;
//import org.rosuda.JRI.RFactor;
//import org.rosuda.JRI.RVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Class for Converting REXP objects into Java friendly objects.
 * @author ABI team 37
 * @version 0.1
 */
public class BnConvert {

  /**
   * Method for converting a REXP object into a BN Network.
   * @param network REXP object to convert
   * @return a BNNetwork object
   */
  public static Network toBnNetwork(REXP data, REXP network) {
    
    List<Node> nodelist = new ArrayList<Node>(); // List of Nodes
    // Error Check
    if (data == null || network == null) {
      return new Network("null",nodelist);
    }
 
    // Temporary Hashmap for storing nodes
    HashMap<String,Node> nodemap = new HashMap<String,Node>();
    REXP rexp1 = network.asVector().at("nodes"); // Nodes
    @SuppressWarnings("unchecked") // Suppressing Type check
    Vector<String> nodeNames = rexp1.asVector().getNames(); // Node Names
    
    // Step 1 create nodes and put in HashMap
    for (int i = 0; i < nodeNames.size(); i++) {
      Node node = new Node();
      String nodeName = nodeNames.get(i);
      node.setNodeName(nodeName);
      nodemap.put(nodeName, node); // add node to HashMap
      nodelist.add(node); // add node to List
    }
    
    // Step 2 add parents to nodes
    for (int i = 0; i < nodeNames.size(); i++) {
      Node node = nodemap.get(nodeNames.get(i)); // node to edit
      String[] parents = rexp1.asVector().at(i).asVector().at("parents").asStringArray();
      //comment to previous line: array of parentnames
      for (int j = 0; j < parents.length; j++) {
        Node pnode = nodemap.get(parents[j]); // parent node
        node.addParentnode(pnode); // add parent node to node
      }      
    }
    
    // Step 3 add children to nodes
    for (int i = 0; i < nodeNames.size(); i++) {
      Node node = nodemap.get(nodeNames.get(i)); // node to edit
      String[] children = rexp1.asVector().at(i).asVector().at("children").asStringArray();
      //comment to previous line: array of childrennames
      for (int j = 0; j < children.length; j++) {
        Node cnode = nodemap.get(children[j]); // child node
        node.addChildnode(cnode); // add child node to node
      }
    }
    
    // Step 4  get levels
    for (int i = 0; i < nodeNames.size(); i++) {                           
      String rfactor = data.asVector().at(i).asFactor().toString(); // the RFactor as a String
      Node node = nodemap.get(nodeNames.get(i)); // the node to edit
      int end = rfactor.indexOf(')'); // getting level String
      String temp = rfactor.substring(9, end); // getting level String
      String[] temp2 = temp.split(","); // splitting String
      for (int j = 0; j < temp2.length;j++) {
        String str = temp2[j].substring(1, temp2[j].length() - 1);
        node.addNodeLevel(str); // add level to node
      }
    }
    
    // Step 5 get algorithm on BN Network
    String algorithm = network.asVector().at("learning").asVector().at("algo").asString();          

    //Step 6 add Arcs to BN Network
    ArrayList<Association> arcs = new ArrayList<Association>();
    String[] temp = network.asVector().at("arcs").asStringArray();
    for (int i = 0; i < temp.length / 2 ; i++) {
      arcs.add(new Association(nodemap.get(temp[temp.length / 2 + i]), nodemap.get(temp[i])));
    }
    
    // Return BNNetwork
    return new Network(algorithm,nodelist, arcs);         

  }
  
  /**
   * Method for fitting a Baysian Network.
   * @param network a Baysian Network to fit
   * @param fitted an REXP fitted object
   */
  public static void fit(Network network, REXP fitted) {
    if (fitted == null) {
      return;
    }
    ArrayList<Node> nodes = (ArrayList<Node>) network.getNodes();
                  
    // Step 5 add cpts to nodes
    for (int i = 0; i < nodes.size(); i++) {
      Node node = nodes.get(i); // the node to edit
      double[] da;
      da = fitted.asVector().at(nodes.get(i).getNodeName()).asVector().at("prob").asDoubleArray();
      // comment to previous line: the double[] of probablities
      node.setCpt(da);
    }
  }

}
