package monopoly.game.board;

import java.util.Observable;

import monopoly.game.player.Player;

/**
 * Represents a square on the board. Abstract class. This class defines default
 * behavior for squares. Subclasses can override this default behavior.
 * 
 * @author Open Universiteit
 */
public abstract class Square extends Observable {
  private String name;

  /**
   * Creates a square with given name.
   * 
   * @param name
   *          name of square
   */
  public Square(String name) {
    this.name = name;
  }

  /**
   * Gets the name of the square.
   * 
   * @return name of square
   */
  public String getName() {
    return name;
  }

  /**
   * Is square a property square?
   * 
   * @return true is this square is a property, false otherwise
   */
  public boolean isProperty() {
    return this instanceof PropertySquare;
  }

  /**
   * Defines the default action when a player lands on this square. Should be
   * overridden for squares that involve real action.
   * 
   * @param player
   *          player landing on this square
   */
  public void landedOn(Player player) {
    // show player on square
    setChanged();
    notifyObservers();
  }

  /**
   * Defines the default action when a player leaves this square.
   * 
   * @param player
   *          player leaving this square
   */
  public void leftFrom(Player player) {
    // stop showing player on square
    setChanged();
    notifyObservers();
  }

  /**
   * Gets information to show on square (all relevant information).
   * 
   * @return information of square
   */
  public String getInfo() {
    return name;
  }

}