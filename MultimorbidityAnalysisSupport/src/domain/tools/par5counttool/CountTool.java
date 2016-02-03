package domain.tools.par5counttool;

import java.util.HashMap;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import domain.Result;
import domain.tools.Tool;
import domain.tools.ToolArgument;

public class CountTool extends Tool{
	
	public static final String TOOLNAME = "COUNTTOOL";
	private Rengine rengine;
	private static final String COUNT = "Count";
	private static final String VAR1 = "var1";
	private static final String VAR2 = "var2";
	
	public CountTool(Rengine rengine) {
		super(CountTool.TOOLNAME);
		this.rengine = rengine;
	}

	@Override
	public Result execute() {
		return null;
	}

	

	@Override
	public Result execute(ToolArgument toolargument) {
		String[] logicalColumns = ((CountArgument) toolargument).getLogicalColumns();
		String groupColumn = ((CountArgument) toolargument).getGroupColumn();
		if(!checkColumnNames(logicalColumns,groupColumn)){
			return null;
		}
		
		if(logicalColumns.length == 0){
			return null;
		}
		for(int i = 0; i<logicalColumns.length; i++){
			String expr = logicalColumns[i]+"log <- as.logical(MyData$"+logicalColumns[i]+")";
			rengine.eval(expr);
			//System.out.println(expr);
		}
		String expr1 = COUNT + "<-";
		
		for(int i = 0; i<logicalColumns.length; i++){
			if(i == 0){
				expr1= expr1+logicalColumns[i]+"log";
			}else{
			expr1= expr1+" + "+logicalColumns[i]+"log";}
		}
		
		
		rengine.eval(expr1);
		//System.out.println(expr1);
		
		String expr3 ="tdata <- table("+COUNT+")";
		REXP rexp = rengine.eval(expr3);
		
		
		int[] count = rexp.asIntArray();
		
		
		
		
		
		///////////////////////////////////////
		
		
		
		
		String expr5 =  "md <-data.frame(MyData$"+groupColumn+",Count)";
		rengine.eval(expr5);
		//System.out.println(expr5);
		
		String expr6 = "tdf <- table(md)";
		rengine.eval(expr6);
		//System.out.println(expr6);
		
		String[] factors = toolargument.getDataSet().getFactors(groupColumn);
		String[][] values = new String[factors.length][count.length];
		HashMap<String,int[]> groupCount = new HashMap<String,int[]>();
		
		for(int i = 0; i<factors.length; i++){
			int[] temp = new int[count.length];
			for(int j = 0; j<count.length;j++){
				REXP rexp2 = rengine.eval("as.character(tdf['"+factors[i]+ "',"+(j+1) +" ])");
				
				
				values[i][j] = rexp2.asString();
				temp[j] = Integer.parseInt(rexp2.asString());
			}
			groupCount.put(factors[i], temp);
		}
		
		
		//////////// tdf table of Count per Age
		
		
		return new CountResult(count,groupCount);
	}
	
	private boolean checkColumnNames(String[] columns, String groupColumn){
		return true;
	}
	
	public void plot1(){
		rengine.eval("plot(tdata)");
	}
	
	public void plot2(){
		rengine.eval("plot(tdf)");
	}
}
