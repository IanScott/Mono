package poc2.domain;

import java.util.Observable;

import org.rosuda.JRI.Rengine;

public abstract class Tool extends Observable{
	
	protected String name;
	
	public Tool(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	
	public abstract Result execute();
	
	
	public abstract Result execute(Object object);
}
