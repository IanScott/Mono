package domain.tools;

import domain.DataSetInstance;

import java.io.Serializable;

/**
 * Abstract class for Result. 
 * This Class is used by a Tool if a calculation needs to be saved. 
 * Or reused by another class.
 * @author ABI team 37
 * @version 1.0
 */
public abstract class Result implements Serializable {
  
  private static final long serialVersionUID = 8835187587234304631L;
  private String tool;
  private DataSetInstance datasetinstance;
  
  /**
   * Constructor as first step for constructor of subclasses.
   * @param tool  the tool for which the subclass should be instantiated
   * @param datasetinstance the dataset to be save with the tool
   */
  public Result(String tool,DataSetInstance datasetinstance) {
    this.tool = tool;
    this.datasetinstance = datasetinstance;
  }
  
  /**
   * Returns tool for which Result is calculated.
   * @return the for which Result is calculated.
   */
  public String getTool() {
    return tool;
  }
  
  /**
   * Getter for the datasetinstance belonging to Result.
   * @return the datasetInstance belonging to Result.
   */
  public DataSetInstance getDataSetInstance() {
    return datasetinstance;
  }
}
