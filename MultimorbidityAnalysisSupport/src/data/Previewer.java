package data;

import util.MultimorbidityException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

/**
 * This Class reads a CSV file and returns a 10 row preview JTabel.
 * @author ABI team 37
 * @version 1.0
 */
public class Previewer {
  private static final int MAX = 10;
  /**
   * private constructor.
   */
  private Previewer(){
          
  }
  
  /**
   * Method returns a Tabel preview of a csv file.
   * @param file the file to preview
   * @param sep the separator
   * @param head whether the file has a head
   * @return a TabelModel
   * @throws MultimorbidityException propagated to gui
   */
  public static DefaultTableModel preview(File file, String sep, Boolean head) throws MultimorbidityException {
    BufferedReader br = null;
    String line;
    
    String[] header = {};
    String[][] values = new String[1][1];
    int i = 0;
    
    try {
      br = new BufferedReader(new FileReader(file));
      boolean firstline = true;
      
      if (head) {
        line = br.readLine();
        if (line != null) {
          header = line.split(sep);
          if (firstline) {
            values = new String[MAX][header.length];
            firstline = false;
          }
        }
      }
      
      line = br.readLine();
      while (line != null && i < MAX) {
        if (firstline) {
          values = new String[MAX][line.split(sep).length];
          firstline = false;
          
          int j = values[0].length;
          String[] empty = new String[j];
          for (int u = 0; u < j; u++) {
            empty[u] = "";
          }
          header = empty;
        }
        String[] val = line.split(sep);
        values[i] = val;
        line = br.readLine();
        i++;
      }

    } catch (FileNotFoundException e) {
      throw new MultimorbidityException("File Missing");
    } catch (IOException e) {
      throw new MultimorbidityException("File Corrupt");
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          throw new MultimorbidityException("Failed closing DataStream");
        }
      }
    }

    if (i < MAX) {
      if (values[0] != null) {
        int j = values[0].length;
        String[] empty = new String[j];
        for(int u = 0; u < j; u++){
          empty[u] = "";
        }
        while ( i < MAX) {
          values[i] = empty;
          i++;
        }
      }
    }

    return new DefaultTableModel(values,header);            
  }
}
