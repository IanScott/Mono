package poc2.domain;

import java.util.List;

public class BNResult extends Result{
	
	private List<Network> bnnetworks;
	
	public BNResult(List<Network> bnnetworks, BNTool bntool){
		super(bntool);
		this.bnnetworks = bnnetworks;
	}
	
	@Override
	public List<Network> getResults() {
		return bnnetworks;
	}

}
