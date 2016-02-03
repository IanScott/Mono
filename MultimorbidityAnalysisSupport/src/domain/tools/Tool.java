package domain.tools;

import domain.Result;
import org.rosuda.JRI.Rengine;

import java.util.Observable;


public abstract class Tool extends Observable{
  
	private String name;
	
  public Tool(String name) {
    this.name = name;
  }
  
  public String getName(){
	  return name;
  }
  
  public abstract Result execute();
  
  public abstract Result execute(ToolArgument toolargument);
}
