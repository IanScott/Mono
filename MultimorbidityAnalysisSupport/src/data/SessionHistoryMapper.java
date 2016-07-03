package data;

import domain.SessionHistory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class is a Mapper for the SessionHistory object.
 * The history is writen to the file Config.his in the Temp folder.
 * @author ABI team 37
 * @version 1.0
 */
public class SessionHistoryMapper {
  private static final String FILE = "configuration/Config.his";
  private static final Logger LOGGER = LogManager.getLogger(SessionHistoryMapper.class);
  
  private SessionHistoryMapper(){
          
  }
  
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
      LOGGER.info(FILE + " Not Found");
    } catch (IOException e) {
      LOGGER.info(e.getMessage());
    } finally {
      try {
        if (oos != null) {
          oos.close();
        }
      } catch (IOException e) {
        LOGGER.info(e.getMessage());
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
      LOGGER.info(e.getMessage());
    }
    return sess;
  }
}
