package poc2.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;

/**
 * Class representing the DataSet
 * @author Ian van Nieuwkoop
 * @version 0.2
 */
public class DataSet extends Observable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4980879919806006795L;
	
	//Non persistent attributes
	//private final static int rVarNr = 0;
	//private DataSet oldDataSet;
	//private String rVar;
	
	// Attributes
	private String name;
	private String[] columnnames;
	private String[][] values;
	
	private HashMap<String,String[]> factors;
	private HashMap<Tool, Result> results;
	
	private String[] lists;
	/**
	 * Constructor
	 */
	public DataSet(){
		this.results = new HashMap<Tool, Result>();
		this.columnnames = new String[0];
		this.values = new String[0][0];
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Constructor
	 * @param col String[] of columnnames
	 * @param val String[][] of values
	 */
	public DataSet(String nm, String[] col, String[][] val, HashMap<String,String[]> fac, HashMap<Tool, Result> res){
		this.name = nm;
		this.columnnames = col;
		this.values = val;
		this.factors = fac;
		this.results = res;
	}
	
	public DataSet(String[] col, String[][] val){
		this.results = new HashMap<Tool, Result>();
		this.columnnames = col;
		this.values = val;
	}
	
	/**
	 * Getter for the Columnnames
	 * @return String[] of Columnnames
	 */
	public String[] getColumnnames() {
		return columnnames;
	}
	
	/**
	 * Getter for the Values
	 * @return String[][] containing the values
	 */
	public String[][] getValues() {
		return values;
	}
	
	/**
	 * Setter for the Columnnames
	 * @param columnnames a String[] containing the new columnnames
	 */
	public void setColumnnames(String[] columnnames) {
		this.columnnames = columnnames;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Setter for the Values
	 * @param values a String[][] containing the new values
	 */
	public void setValues(String[][] values) {
		this.values = values;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Setter for the Columnnames and Values
	 * @param col a String[] containing the new columnnames
	 * @param val a String[][] containing the new values
	 */
	public void setAll(String[] col, String[][] val){
		this.columnnames = col;
		this.values = val;
		this.setChanged();
		this.notifyObservers();
	}
	
	//new
	public void setAll(String[] col, String[][] val, HashMap<String,String[]> factors){
		this.columnnames = col;
		this.values = val;
		this.factors = factors;
		this.setChanged();
		this.notifyObservers();
	}
	
	public String[] getFactors(String key){
		return factors.get(key);
	}
	
	public int getMaxFactors(){
		int max = 0;
		Iterator<String[]> iter = factors.values().iterator();
		while(iter.hasNext()){
			String[] s = iter.next();
			if(s.length>max){
				max = s.length;
			}
		}
		return max;
	}
	
	public void setFactors(HashMap<String,String[]> factors){
		this.factors = factors;
	}
	
	public void addResult(Result res){
		Tool tool = res.getTool();
		if(results == null){
			results = new HashMap<Tool, Result>();
		}
		
		if(results.get(tool) != null){
			results.remove(res.getTool());
		}
		results.put(res.getTool(), res);
	}
	public String getName(){
		if(name == null){
			return Math.random()*Math.random()+"";
		}
		return name;
	}
	
	public DataSet clone(){
		return new DataSet(name,Arrays.copyOf(columnnames,columnnames.length), values, factors, results);
	}
	
	public String toString(){
		String res = "";
		for(String s: columnnames){
			res = res+","+s;
		}
		return res;
	}
}
