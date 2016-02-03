package monopoly.game.board;

/**
 * Represents a utility square.
 * 
 * @author Open Universiteit
 */
public class UtilitySquare extends PropertySquare {
  private static final String GROUPNAME = "Utility";
  private static final Group UTGROUP = new Group(GROUPNAME);
  private final int baserent;
  private final int grouprent;

  /**
   * Creates a utility square.
   * 
   * @param name
   *          name of square
   * @param price
   *          buying price
   * @param baserent
   *          base rent
   * @param grouprent
   *          rent for complete group
   */
  public UtilitySquare(String name, int price, int baserent, int grouprent) {
    super(name, UTGROUP, price);
    this.baserent = baserent;
    this.grouprent = grouprent;
  }

  /**
   * Computes the rent for this square.
   * 
   * @param rolltotal
   *          result of last roll with dice
   * @return rent for square
   */
  public int getRent(int rolltotal) {
    if (isMortgage()) {
      return 0;
    }
    // total of dice determines rent
    if (getGroup().isComplete(getOwner())) {
      return rolltotal * grouprent;
    }
    return rolltotal * baserent;
  }

}
