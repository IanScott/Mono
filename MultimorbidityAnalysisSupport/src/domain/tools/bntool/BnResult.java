package domain.tools.bntool;

import domain.Result;
import domain.tools.bntool.BnTool;
import domain.tools.bntool.Network;

import java.util.List;

public class BnResult extends Result{
  
  private List<Network> bnnetworks;
  
  public BnResult(List<Network> bnnetworks, BnTool bntool) {
    super(BnTool.TOOLNAME);
    this.bnnetworks = bnnetworks;
  }
  
  @Override
  public List<Network> getResults() {
    return bnnetworks;
  }

}
