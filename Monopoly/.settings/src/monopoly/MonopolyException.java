package monopoly;

/**
 * Checked exception when for errors in the monopoly application.
 * 
 * @author Open Universiteit
 * 
 */
public class MonopolyException extends Exception {

  private static final long serialVersionUID = -7870707962387847338L;

  /**
   * Creates an instance of this class.
   */
  public MonopolyException() {
    super();
  }

  /**
   * Creates an instance of this class.
   * 
   * @param s
   *          error message
   */
  public MonopolyException(String s) {
    super(s);
  }
}
