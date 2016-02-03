package monopoly.game.board;

import monopoly.SimulationData;
import monopoly.game.player.Player;

/**
 * Represents the "go to jail" square on the board.
 * 
 * @author Open Universiteit
 */
public class GoToJailSquare extends Square {

  /**
   * Creates the go to jail square.
   * 
   * @param name
   *          name of square
   */
  public GoToJailSquare(String name) {
    super(name);
  }

  /**
   * Defines the action when a player lands on this square The player is sent to
   * jail.
   * 
   * @param player
   *          player landing on this square
   */
  public void landedOn(Player player) {
    // show that player landed on this square
    super.landedOn(player);
    // wait for a moment before going to jail
    SimulationData.getInstance().gotojailWait();
    player.sendToJail();
  }

}
