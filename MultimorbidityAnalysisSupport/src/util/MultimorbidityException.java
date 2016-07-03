package util;

/**
 * Handles the thrown exceptions with a message.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class MultimorbidityException extends Exception {

  private static final long serialVersionUID = 1L;
  
  /**
   * Constructor 1.
   */
  public MultimorbidityException() {
    super();
  }
  
  /**
   * Constructor 2 with parameters.
   * @param message the String message to be printed as error message
   */
  public MultimorbidityException(String message) {
    super(message);
  }
}
