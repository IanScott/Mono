package domain.tools.bntool;

import java.io.Serializable;

/**
 * Class which represents the Probability tables 
 * of a Node in a Bayesian Network.
 * @author ABI team 37
 * @version 0.1
 */
public class Cpt implements Serializable{

  /**
	 * 
	 */
	private static final long serialVersionUID = -7666591824526683920L;
// Attributes
  private double[] values;
  private String[] levels;
  private String[][] table;

  /**
   * Constructor of Cpt object.
   * @param levels an array of possible levels
   * @param values an array of all the probability values
   */
  public Cpt(String[] levels, double[] values) {
    this.values = values;
    this.levels = levels;
    table = new String[values.length / levels.length][levels.length];
    
    int foo = 0;
    for (int i = 0; i < table.length; i++) {
      for (int j = 0; j < table[0].length; j++) {
        table[i][j] = values[foo] + "";
        foo++;
      }
    }
  }
  
  /**
   * Getter for the values.
   * @return a double array containing the values
   */
  public double[] getValues() {
    return values;
  }
  
  /**
   * Getter for the levels.
   * @return a String array containing the levels
   */
  public String[] getLevels() {
    return levels;
  }
  
  /**
   * Getter for the table.
   * @return a 2d String array which represents the table of probabilities
   */
  public String[][] getTable() {
    return table;
  }
}
