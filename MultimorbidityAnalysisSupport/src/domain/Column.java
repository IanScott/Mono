package domain;

/**
 * A Class representing a column of a Data Set
 * @author Ian van Nieuwkoop
 * @version 0.1
 */
public class Column {
	private Type type;
	private String name;
	private String[] values;
	private String[] factors;
	private Boolean disease;
	private Boolean observation;
	private double average;
	private String median;
	private Boolean nas;
	
	/**
	 * Constructor 1.
	 * @param name the name of the column.
	 * @param type the datatype of the column.
	 * @param values a list of the values the column contains.
	 * @param factors a list of the factors used by column.
	 */
	public Column(String name, Column.Type type, String[] values, String[] factors){
		this.name = name;
		this.type = type;
		this.values = values;
		this.nas = false;
		if(type.equals(Type.FACTOR)){
			this.factors = factors;
		}
	}
	
	/**
	 * Constructor 2.
	 * @param name the name of the column.
	 * @param type the datatype of the column.
	 * @param values a list of the values the column contains.
	 */
	public Column(String name, Column.Type type, String[] values){
		this( name,  type, values, null);
	}
	
	/**
	 * Getter for the column name.
	 * @return a String representation of the column name.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Setter for the column name
	 * @param name the name of the column
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Getter for the column datatype.
	 * @return a Type Enum representing the column type.
	 */
	public Type getType(){
		return type;
	}
	
	/**
	 * Getter for the column values.
	 * @return a String array containing the column values.
	 */
	public String[] getValues(){
		if(values == null){
			return new String[0];
		}
		return values;
	}
	
	/**
	 * Getter for the column factors.
	 * @return a String array of the factors used in the column.
	 */
	public String[] getFactors(){
		return factors;
	}
	
	/**
	 * Setter for the disease boolean.
	 * @param bool disease true or false?
	 */
	public void setDisease(Boolean bool){
		this.disease = bool;
	}
	
	/**
	 * Getter for the disease attribute.
	 * @return a boolean whether column is a disease or not.
	 */
	public Boolean getDisease(){
		return this.disease;
	}
	
	/**
	 * Setter for the observation boolean.
	 * @param bool observation true or false?
	 */
	public void setObservation(Boolean bool){
		this.observation = bool;
	}
	
	/**
	 * Getter for the observation attribute.
	 * @return a boolean whether column is a observation or not.
	 */
	public Boolean getObservation(){
		return this.observation;
	}
	
	/**
	 * Setter for the average attribute.
	 * @param av a double representing the average value.
	 */
	public void setAverage(double av){
		this.average = av;
	}
	
	/**
	 * Getter for the average attribute.
	 * @return a double representing the average value.
	 */
	public double getAverage(){
		return this.average;
	}
	
	/**
	 * Setter for the median value.
	 * @param med a String representing the median value.
	 */
	public void setMedian(String med){
		this.median = med;
	}
	
	/**
	 * Getter for the median value.
	 * @return a String representing the median value.
	 */
	public String getMedian(){
		return this.median;
	}
	
	/**
	 * Setter for the type attribute
	 * @param stringtype a String representing the datatype of the column.
	 */
	public void setType(String stringtype){
		Type type = Type.toType(stringtype);
		this.type = type;
	}
	
	/**
	 * Method returns a Clone of the column.
	 * @return return a Column object which is a clone.
	 */
	public Column clone(){
		Column ccolumn= new Column(name, type, values.clone(), factors.clone());
		ccolumn.disease = this.disease;
		ccolumn.observation = this.observation;
		ccolumn.average = this.average;
		ccolumn.median = this.median;
		
		return ccolumn;
	}
	
	/**
	 * Setter for the Contains NA's attribute
	 * @param nas a boolean, whether column has NA's or not.
	 */
	public void setNas(Boolean nas){
		if(nas != null){
			this.nas = nas;
		}
	}
	
	/**
	 * A Check to see if Column contains NA values.
	 * @return a boolean whether column contains NA values.
	 */
	public Boolean hasNas(){
		return nas;
	}
	
	/**
	 * An Enum of the Types a Column Class can have.
	 * @author Ian van Nieuwkoop
	 * @version 0.1
	 */
	public enum Type{
		CHARACTER("character"), 
		LOGICAL("logical"), 
		NUMERIC("numeric"), 
		INTEGER("integer"), 
		FACTOR("factor"),
		DATE("Date");
		
		private final String name;
		
		/**
		 * Private Constructor
		 * @param n String representation of enum.
		 */
		private Type(String n){
			this.name = n;
		}
		
		/**
		 * Overrided ToString method.
		 */
		public String toString(){
			return this.name;
		}
		
		/**
		 * Method returns a list of the available types.
		 * @return a String array of datatypes.
		 */
		public static String[] getTypes(){
			String[] types = {"character","logical","numeric","integer","factor","Date"};
			return types;
		}
		
		/**
		 * Method for converting String representations to Column DataTypes
		 * @param string the String to convert
		 * @return a Type object.
		 */
		public static Type toType(String string){
			for(Type t: Column.Type.values()){
				if(string.equals(t.toString()))
				return t;
			}
			return null;
		}
	}
}
