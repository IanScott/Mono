package domain;

import java.io.Serializable;

/**
 * A Class representing a history of work sessions. 
 * The history has a maximum size of 5 slots.
 * When new entries are added, the oldest one is removed.
 * @author ABI team 37
 * @version 1.0
 */
public class SessionHistory implements Serializable {
  private static final int MAXSIZE = 5;
  private static final long serialVersionUID = 7568247294872428425L;
  private SizedQueue<String> sessions;
  private Boolean editted;
  
  /**
   * Constructor.
   */
  public SessionHistory() {
    sessions = new SizedQueue<String>(MAXSIZE);
    editted = false;
  }
  
  /**
   * Methode to add a dataset to the session history.
   * @param ds the dataSet to add.
   */
  public void add(String ds) {
    if (ds != null) {
      if (!sessions.contains(ds)) {
        sessions.add(ds);
        editted = true;
      }
    }
  }
  
  /**
   * Method deletes the contains of the history.
   */
  public void reset() {
    sessions = new SizedQueue<String>(MAXSIZE);
    editted = true;
  }
  
  /**
   * Returns the values of the Editied attribute.
   * @return  a boolean signifying if the object has been changed or not
   */
  public Boolean isEditted() {
    return editted;
  }
  
  /**
   * Getter that returns a DataSet at a index within the Session history.
   * @param index  index of the object to return
   * @return  a DataSet object
   */
  public String get(int index) {
    if (index > sessions.limit() - 1 || index > sessions.size() - 1) {
      index = sessions.size() - 1;
    }
    return sessions.get(index);
  }
  
  /**
   * Method to remove a DataSet from and index.
   * @param index  the index of the object to remove
   * @return  the DataSet which was removed
   */
  public String remove(int index) {
    if (index > sessions.limit() - 1 || index > sessions.size() - 1) {
      index = sessions.size() - 1;
    }
    return sessions.remove(index);
  }
  
  /**
   * Method that returns the size of the SessionHistory.
   * @return  an int representing the size
   */
  public int size() {
    return sessions.size();
  }
  
  /**
   * Method signifies that the SessionHistory has been edited.
   */
  public void saved() {
    editted = false;
  }
  
}
