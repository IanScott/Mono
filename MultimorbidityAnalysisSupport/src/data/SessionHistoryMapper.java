package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import domain.SessionHistory;



public class SessionHistoryMapper {
	private static final String FILE ="TEMP/Config.his";
	/**
	   * Saves a SessionHistory.
	   * @param sessionHistory sessionHistory to be saved
	   */
	  public static void saveSessionHistory(SessionHistory sessionHistory) {
	    ObjectOutputStream oos = null;
	    try {
	      oos = new ObjectOutputStream(  
	      new FileOutputStream(new File(FILE)));
	      oos.writeObject(sessionHistory);
	      oos.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("File Missing");
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      try {
	        if (oos != null) {
	          oos.close();
	        }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	  }
	  
	  /**
	   * Loads a SessionHistory.
	   * @return SessionHistory from the loaded File
	   */
	  public static SessionHistory loadSessionHistory() {
	    FileInputStream fis;
	    SessionHistory sess = null;
	    try {
	      fis = new FileInputStream(FILE);
	      ObjectInputStream oin = new ObjectInputStream(fis);
	      sess = (SessionHistory) oin.readObject();
	      oin.close();
	    } catch (IOException | ClassNotFoundException e) {
	
	      System.out.println("Failed to Open File");
	    }
	    return sess;
	  }
}
