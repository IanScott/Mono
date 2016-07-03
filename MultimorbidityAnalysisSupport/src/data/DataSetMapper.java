package data;

import domain.DataSetInstance;
import util.MultimorbidityException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class is the Mapper for the DataSet class. 
 * This Class saves and loads the class objects to and from a File.
 * @author ABI team 37
 * @version 1.0
 */
public class DataSetMapper {
  private File file = null;
  
  /**
   * Saves a DataSet.
   * @param dataset DataSet to be saved
   * @param file save location
   * @throws MultimorbidityException propagated in gui
   */
  public void saveDataSet(DataSetInstance dataset,File file) throws MultimorbidityException {
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(  
          new FileOutputStream(file));
      oos.writeObject(dataset);
      oos.close();
    } catch (FileNotFoundException e) {
      throw new MultimorbidityException("Error loading File, File not found");
    } catch (IOException e) {
      throw new MultimorbidityException("Error loading File");
    } finally {
      try {
        if (oos != null) {
          oos.close();
        }
      } catch (IOException e) {
        throw new MultimorbidityException("Error loading File");
      }
    }
  }

  /**
   * Loads a DataSet.
   * @param file File to be loaded
   * @return DataSet from the loaded File
   * @throws MultimorbidityException propagated in gui
   */
  public DataSetInstance loadDataSet(File file) throws MultimorbidityException {
    FileInputStream fis;
    DataSetInstance datasetinstance = null;
    try {
      fis = new FileInputStream(file);
      ObjectInputStream oin = new ObjectInputStream(fis);
      datasetinstance = (DataSetInstance) oin.readObject();
      oin.close();
    } catch (IOException | ClassNotFoundException e) {
      throw new MultimorbidityException("Error saving File");
    }   
    return datasetinstance;
  }
  
  /**
   * Getter for the last save/load location.
   * @return the location as a File
   */
  public File getFile() {
    return file;
  }
}
