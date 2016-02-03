package domain;

import java.io.Serializable;

/**
 * A Class representing a history of work sessions
 * @author Ian van Nieuwkoop
 * @version 0.1
 */
public class SessionHistory implements Serializable{
	
	private static final long serialVersionUID = 7568247294872428425L;
	private SizedQueue<DataSet> sessions;
	private Boolean editted;
	
	/**
	 * Constructor
	 */
	public SessionHistory(){
		sessions = new SizedQueue<DataSet>(5);
		editted = false;
	}
	
	/**
	 * Methode to add a dataset to the session history.
	 * @param ds the dataSet to add.
	 */
	public void add(DataSet ds){
		if(ds.getDatasetInstance() !=null){
			sessions.add(ds);
			editted = true;
		}
	}
	
	/**
	 * Method deletes the contains of the history.
	 */
	public void reset(){
		sessions = new SizedQueue<DataSet>(5);
		editted = true;
	}
	
	/**
	 * Returns the values of the Editied attribute.
	 * @return a boolean signifying if the object has been changed or not.
	 */
	public Boolean isEditted(){
		return editted;
	}
	
	/**
	 * Getter that returns a DataSet at a index within the Session history.
	 * @param index index of the object to return.
	 * @return a DataSet object.
	 */
	public DataSet get(int index){
		if(index > sessions.limit()-1||index>sessions.size()-1){
			index = sessions.size()-1;
		}
		
		return sessions.get(index);
	}
	
	/**
	 * Method to remove a DataSet from and index.
	 * @param index the index of the object to remove.
	 * @return the DataSet which was removed.
	 */
	public DataSet remove(int index){
		if(index > sessions.limit()-1||index>sessions.size()-1){
			index = sessions.size()-1;
		}
		
		return sessions.remove(index);
	}
	/**
	 * Method that returns the size of the SessionHistory.
	 * @return an int representing the size.
	 */
	public int size(){
		return sessions.size();
	}
	/**
	 * Method signifies that the SessionHistory has been edited.
	 */
	public void saved(){
		editted = false;
	}
}
