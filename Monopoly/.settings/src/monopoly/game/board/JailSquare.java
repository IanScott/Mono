package monopoly.game.board;

/**
 * Represents the square for the jail.
 * 
 * @author Open Universiteit
 */
public class JailSquare extends Square {
  private int bail; // bail to pay to leave jail

  /**
   * Creates a jail square.
   * 
   * @param name
   *          name of square
   * @param bail
   *          bail to pay to leave jail
   */
  public JailSquare(String name, int bail) {
    super(name);
    this.bail = bail;
  }

  /**
   * Gets the bail to pay to leave jail.
   * 
   * @return the bail
   */
  public int getBail() {
    return bail;
  }

}
