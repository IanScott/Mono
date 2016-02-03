package domain.tools.par5counttool;

import java.util.HashMap;

import domain.Result;

public class CountResult extends Result{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int[] count;
	private HashMap<String,int[]> groupCount;
	
	public CountResult(int[] count, HashMap<String,int[]> groupCount) {
		super(CountTool.TOOLNAME);
		this.count = count;
		this.groupCount= groupCount;
	}

	public int[] getCount(){
		return count;
	}
	
	public HashMap<String,int[]> getGroupCount(){
		return groupCount;
	}
	
	@Override
	public Object getResults() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
