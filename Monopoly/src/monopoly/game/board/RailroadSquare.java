package monopoly.game.board;

import monopoly.game.player.Player;

/**
 * Represents a railroad square.
 * 
 * @author Open Universiteit
 */
public class RailroadSquare extends PropertySquare {
  private static final String GROUPNAME = "Railroad";
  private static final Group RRGROUP = new Group(GROUPNAME);
  private final int baserent;

  /**
   * Creates a railroad square.
   * 
   * @param name
   *          name of square
   * @param price
   *          buying price
   * @param baserent
   *          base rent
   */
  public RailroadSquare(String name, int price, int baserent) {
    super(name, RRGROUP, price);
    this.baserent = baserent;
  }

  /**
   * Computes the rent for this square.
   * 
   * @param rolltotal
   *          current total value of dice. not used.
   * @return rent for square
   */
  public int getRent(int rolltotal) {
    if (isMortgage()) {
      return 0;
    }
    int railroads = getGroup().getNumberOwned(getOwner());
    int pow = (int) Math.pow(2, railroads - 1);
    return pow * baserent;
  }
  
  /**
   * Method not used.
   * @param player not used.
   */
  public void checkGroup(Player player){
  }
  /**
   * Overrides super setOwner and adds some code.
   * When Railroads a bought the other railroadSquares are updated also.
   * This so Tooltip is up to date.
   * @param player the new owner of the Square
   */
  public void setOwner(Player player) {
	  super.setOwner(player);
	    for ( PropertySquare ps: getGroup().getProperties()){
	    	ps.hasChanged();
	    	ps.notifyObservers();
	    }
	  }
}
