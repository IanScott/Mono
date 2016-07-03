package domain;

import data.AuditMapper;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import util.MultimorbidityException;

import java.io.File;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

/**
 * Class is responsible for the implementation of the Auditing System. 
 * By using the eval method, the way calls to the R engine are audited.
 * All methods are effectly used in the subclasses.
 * @author ABI team 37
 * @version 1.0
 */
public abstract class Audit extends Observable {
  private static Queue<String> auditQueue = new LinkedList<String>();
  private Rengine rengine;
  private AuditMapper auditMapper = new AuditMapper();
  
  /**
   * Basic Constructor.
   */
  public Audit() { 
  }
  
  /**
   * Setter for the R Engine.
   * @param rengine the Rengine object to be used.
   */
  public void setRengine(Rengine rengine) {
    this.rengine = rengine;
  }
  
  /**
  * Getter for the Audit Queue of the tool.
  * @return a Queue of Strings.
  */
  public static Queue<String> getAudit() {
    return Audit.auditQueue;
  }
    
  /**
   * Method used for sending expressions to the R Engine.
   * Expression is logged if bool is TRUE.
   * @param expr the expression to be evaluated.
   * @param bool TRUE if expression has to written to the audit file, else FALSE.
   * @return an REXP object representing the answer of the expression.
   */
  protected REXP eval(String expr, Boolean bool) {
    if (bool) {
      Audit.auditQueue.add(expr);
    }
    if (rengine != null) {
      return rengine.eval(expr);
    } else {
      return null;
    }
  }
  
  /**
   * Method 2 used for sending expressions to the R Engine.
   * Expression is by default automatically logged.
   * @param expr the expression to evaluate.
   * @return an REXP object representing the answer of the expression.
   */
  protected REXP eval(String expr) {
    return this.eval(expr,true);
  }
  
  /**
   * Method used for adding a comment to the audit script.
   * If string doesn't start with '#', it is automaticaly added.
   * @param expr the comment String to add.
   */
  protected void comment(String expr) {
    if (expr.length() > 0) {
      if (expr.startsWith("#")) {
        Audit.auditQueue.add(expr);
      } else {
        String expr1 = "#" + expr;
        Audit.auditQueue.add(expr1);
      }
    }
  }

  
  /**
   * Saves the audit.
   * @param file The file to which the audit should be written
   * @throws MultimorbidityException The thrown message if there is a problem with the export
   */
  public void exportAudit(File file) throws MultimorbidityException {
    auditMapper.exportAudit(file);
  }

}
