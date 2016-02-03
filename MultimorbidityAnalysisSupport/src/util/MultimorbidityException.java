package util;

/**
 * Handels the thrown exceptions with a message.
 * @author Team 37
 *
 */
public class MultimorbidityException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public MultimorbidityException() {
    super();
  }
  
  public MultimorbidityException(String message) {
    super(message);
  }
}
