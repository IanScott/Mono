package data;

import domain.tools.bntool.BnResult;
import util.MultimorbidityException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This Class is the Mapper for BnResult. It saves and loads the Result to and from files.
 * @author ABI team 37
 * @version 1.0
 */
public class BnResultMapper {
  /**
   * Saves a BnResult.
   * @param bnresult DataSet to be saved
   * @param file the save location
   * @throws MultimorbidityException propagated to gui
   */
  public void saveBnResult(BnResult bnresult,File file) throws MultimorbidityException {
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(  
      new FileOutputStream(file));
      oos.writeObject(bnresult);
      oos.close();
    } catch (FileNotFoundException e) {
      throw new MultimorbidityException("Error saving File, File not found");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (oos != null) {
          oos.close();
        }
      } catch (IOException e) {
        throw new MultimorbidityException(e.getMessage());
      }
    }
  }
  
  /**
   * Loads a BnResult.
   * @param file File to be loaded
   * @return BnResult from the loaded File
   * @throws MultimorbidityException propagated in gui
   */
  public static BnResult loadBnResult(File file) throws MultimorbidityException {
    FileInputStream fis;
    BnResult bnresult = null;
    try {
      fis = new FileInputStream(file);
      ObjectInputStream oin = new ObjectInputStream(fis);
      bnresult = (BnResult) oin.readObject();
      bnresult.initMapper();
      oin.close();
    } catch (IOException | ClassNotFoundException e) {
      throw new MultimorbidityException("Error loading File");
    }   
    return bnresult;
  }
}
