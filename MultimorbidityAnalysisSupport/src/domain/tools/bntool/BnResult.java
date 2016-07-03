package domain.tools.bntool;

import data.BnResultMapper;
import domain.DataSetInstance;
import domain.tools.Result;
import util.MultimorbidityException;

import java.io.File;

/**
 * Class representing the BNResult.
 * @author ABI team 37
 * @version 1.0
 */
public class BnResult extends Result {
  
  private static final long serialVersionUID = 2174250638552021936L;
  private Network bnnetwork;
  private transient BnResultMapper bnResultMapper = new BnResultMapper();
  
  /**
   * Constructor.
   * @param bnnetwork the network to add to Result.
   * @param datasetinstance the dataset to add to the Result.
   */
  public BnResult(Network bnnetwork, DataSetInstance datasetinstance) {
    super(BnTool.TOOLNAME, datasetinstance);
    this.bnnetwork = bnnetwork;
  }
  
  /**
   * Method to reset the Mapper attribute after being loaded by the Mapper.
   */
  public void initMapper() {
    this.bnResultMapper = new BnResultMapper();
  }
  
  /**
   * Getter for the network in the Result.
   * @return the network
   */
  public Network getNetwork() {
    return bnnetwork;
  }
  
  /** 
   * Saves the result of bayesian network.
   * @param file The file to which the result should be written
   * @throws MultimorbidityException The thrown message when there is a problem with the action
   */
  public void saveBnResult(File file) throws MultimorbidityException {
    bnResultMapper.saveBnResult(this, file);
  }

}
