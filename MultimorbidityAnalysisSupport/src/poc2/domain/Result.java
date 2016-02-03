package poc2.domain;

public abstract class Result {
	
	private Tool tool;
	
	public Result(Tool tool){
		this.tool = tool;
	}
	
	public abstract Object getResults();
	
	public Tool getTool(){
		return tool;
	}
}
