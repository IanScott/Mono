package domain;

import java.io.Serializable;
import java.util.Queue;

import domain.tools.Tool;


/**
 * Abstract class for Result.
 * @author ABI team 37
 *
 */
public abstract class Result implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8835187587234304631L;
	private String tool;
	private Queue<String> audit;
  
  /**
   * Constructor as first step for constructor of subclasses.
   * @param tool the tool for which the subclass should be instantiated
   */
  public Result(String tool) {
    this.tool = tool;
  }
  
  /**
   * Method to be overridden by subclasses to get a Result.
   * @return the object with the results depending on the tool
   */
  public abstract Object getResults();
  
  /**
   * Returns tool for which Result is calculated.
   * @return the for which Result is calculated.
   */
  public String getTool() {
    return tool;
  }
  
  /**
   * Getter for a Queue containing R Code.
   * @return a Queue containing R Code.
   */
  public Queue<String> getAudit(){
	  return audit;
  }
  
  /**
   * Method to add to the Audit Queue.
   * @param entry the code to add to the Audit Queue.
   */
  public void addToAudit(String entry){
	  audit.add(entry);
  }
}
