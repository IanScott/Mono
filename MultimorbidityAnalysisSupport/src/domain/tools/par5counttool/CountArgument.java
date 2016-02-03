package domain.tools.par5counttool;

import domain.DataSet;
import domain.tools.ToolArgument;

public class CountArgument implements ToolArgument{
	private DataSet dataset;
	private String[] logicalColumns;
	private String groupColumn;
	private String[] trueValues;
	
	
	public CountArgument(DataSet dataset, String[] logcol, String groupcol){
		this.dataset = dataset;
		this.logicalColumns = logcol;
		this.groupColumn = groupcol;
	}
	
	public String[] getLogicalColumns(){
		return this.logicalColumns;
	}
	
	public String getGroupColumn(){
		return this.groupColumn;
	}
	
	@Override
	public DataSet getDataSet() {
		return dataset;
	}
}
