package monopoly.game.board;

import monopoly.game.player.Player;

/**
 * Represents an income tax square.
 * 
 * @author Open Universiteit
 */
public class IncomeTaxSquare extends Square {
  private int percentage; // percentage tax
  private int maxtax; // maximum tax
  private static final int HUNDRED = 100; // maximum tax

  /**
   * Creates an income tax square.
   * 
   * @param name
   *          name of square
   * @param percentage
   *          tax percentage
   * @param maxtax
   *          maximum tax
   */
  public IncomeTaxSquare(String name, int percentage, int maxtax) {
    super(name);
    this.percentage = percentage;
    this.maxtax = maxtax;
  }

  /**
   * Defines the action when a player lands on this square. The player pays
   * income tax.
   * 
   * @param player
   *          player landing on this square
   */
  public void landedOn(Player player) {
    super.landedOn(player);
    // calculate tax
    int tax = percentage * player.getTotalValue() / HUNDRED;
    if (tax > maxtax) {
      tax = maxtax;
    }
    player.reduceCash(tax);
  }

}
