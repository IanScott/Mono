package data;

import domain.Audit;
import util.MultimorbidityException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Queue;

/**
 * This class is used to save the Audit to a File.
 * @author ABI team 37
 * @version 1.0
 */
public class AuditMapper {
  /**
   * Saves the audits.
   * @param file location to be exported
   * @throws MultimorbidityException propagated to gui
   */
  public void exportAudit(File file) throws MultimorbidityException {
    ObjectOutputStream oos = null;
    try {
      File file1 = new File(file.getAbsolutePath() + ".r");
      FileOutputStream fos = new FileOutputStream(file1);
   
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
      
      Queue<String> audit = Audit.getAudit();
      
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      Calendar cal = Calendar.getInstance();
      
      bw.write("# Date/Time " + dateFormat.format(cal.getTime()));
      bw.newLine();
      
      while (!audit.isEmpty()) {
        String expr = audit.poll();
        bw.write(expr);
        bw.newLine();
      }
      
      bw.close();
    } catch (IOException e) {
      throw new MultimorbidityException("Failed to save Audit");
    } finally {
      try {
        if (oos != null) {
          oos.close();
        }
      } catch (IOException e) {
        throw new MultimorbidityException("Failed to save Audit");
      }
    }
  }
}
