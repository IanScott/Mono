package poc1.domain;

import java.util.Observable;

/**
 * Class representing the DataSet
 * @author Ian van Nieuwkoop
 * @version 0.1
 */
public class DataSet extends Observable{
	// Attributes
	String[] columnnames;
	String[][] values;
	
	/**
	 * Constructor
	 */
	public DataSet(){
		this.columnnames = new String[0];
		this.values = new String[0][0];
	}
	
	/**
	 * Constructor
	 * @param col String[] of columnnames
	 * @param val String[][] of values
	 */
	public DataSet(String[] col, String[][] val){
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
	
}
