package data;

import domain.DataSetInstance;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataSetMapper {
  File file = null;
  
  /**
   * Saves a DataSet.
   * @param dataset DataSet to be saved
   */
  public void saveDataSet(DataSetInstance dataset, String name) {
    ObjectOutputStream oos = null;
    try {
      file = new File("TEMP/"+name+".ser");
      oos = new ObjectOutputStream(  
      new FileOutputStream(file));
      oos.writeObject(dataset);
      oos.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
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
   * Loads a DataSet.
   * @param file File to be loaded
   * @return DataSet from the loaded File
   */
  public DataSetInstance loadDataSet(File file) {
    FileInputStream fis;
    DataSetInstance dataset = null;
    try {
      fis = new FileInputStream(file);
      ObjectInputStream oin = new ObjectInputStream(fis);
      dataset = (DataSetInstance) oin.readObject();
      oin.close();
    } catch (IOException | ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return dataset;
  }
  
  public File getFile() {
    return file;
  }
  
}
