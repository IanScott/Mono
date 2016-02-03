package monopoly.game.board;

import monopoly.game.player.Player;

/**
 * Represents the extra tax square.
 * 
 * @author Open Universiteit
 */
public class ExtraTaxSquare extends Square {
  private int tax;

  /**
   * Creates an extra tax square.
   * 
   * @param name
   *          name of square
   * @param tax
   *          tax to be paid
   */
  public ExtraTaxSquare(String name, int tax) {
    super(name);
    this.tax = tax;
  }

  /**
   * Defines the action when a player lands on this square The player has to pay
   * extra tax.
   * 
   * @param player
   *          player landing on this square
   */
  public void landedOn(Player player) {
    super.landedOn(player);
    // pay tax
    player.reduceCash(tax);
  }

}
