package monopoly.game.board;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import monopoly.game.player.Player;

/**
 * Represents a property square. A property square is a square a player can buy
 * (lot, railroad, utility). This class is abstract and contains default
 * behavior for property squares that can be overridden by its subclasses.
 * 
 * @author Open Universiteit.
 * 
 */
public abstract class PropertySquare extends Square {
  private static final double SETMORTGAGE_FRACTION = 0.5;
  private static final double RESETMORTGAGE_FRACTION = 0.6;
  private int price;
  private Group group;
  private Player owner;
  private boolean mortgage;
  /** the logger. */
  protected static final Logger LOGGER =
			LogManager.getLogger(PropertySquare.class.getName());
  private boolean groupcheck = false;
  /**
   * Creates a property square.
   * 
   * @param name
   *          square name
   * @param group
   *          group it belongs to
   * @param price
   *          price to buy property
   */
  public PropertySquare(String name, Group group, int price) {
    super(name);
    this.group = group;
    this.price = price;
    this.group.addProperty(this);
  }

  /**
   * Gets group of property.
   * 
   * @return group of property
   */
  public Group getGroup() {
    return group;
  }

  /**
   * Gets owner of property.
   * 
   * @return player that owns property, null when no player owns property
   */
  public Player getOwner() {
    return owner;
  }

  /**
   * Sets the owner of the property (when property is bought).
   * 
   * @param player
   *          new owner
   */
  public void setOwner(Player player) {
    owner = player;
    // new owner, show new information
    LOGGER.info(this.getName()+" has been bought by: "+player.getName());
    setChanged();
    notifyObservers();
  }

  /**
   * Gets buying price of property.
   * 
   * @return buying price
   */
  public int getPrice() {
    return price;
  }

  /**
   * Gets amount when setting mortgage on property.
   * 
   * @return mortgaged value
   */
  public int getMortgagePrice() {
    return (int) (price * SETMORTGAGE_FRACTION);
  }

  /**
   * Gets amount to pay to reset mortgage on property.
   * 
   * @return amount to pay to reset mortgage
   */
  public int getResetMortgagePrice() {
    return (int) (price * RESETMORTGAGE_FRACTION);
  }

  /**
   * Is property mortgaged?
   * 
   * @return true when property is mortgaged, false otherwise
   */
  public boolean isMortgage() {
    return mortgage;
  }

  /**
   * Sets/resets mortgage status of property.
   * 
   * @param mortgage
   *          indication of mortgage status
   */
  public void setMortgage(boolean mortgage) {
	  LOGGER.info(this.getName()+ " has been mortgaged");
    this.mortgage = mortgage;
    setChanged();
    notifyObservers();
  }

  /**
   * Can property be mortgaged by player?
   * 
   * @return true when property can be mortgaged by player, false otherwise
   */
  public boolean canBeMortgaged() {
    return !isMortgage();
  }
  
  /**
   * Gets the total value this square represents (cost price).
   * 
   * @return total value square represents
   */
  public int getValue() {
    int value = 0;
    if (owner != null) {
      value = getPrice();
    }
    return value;
  }

  /**
   * Clears all property information (owner, mortgage) when property is returned
   * to bank.
   */
  public void clear() {
    owner = null;
    mortgage = false;
    // show new information
    setChanged();
    notifyObservers();
  }

  /**
   * Defines the action when a player lands on this square (buy, pay rent).
   * 
   * @param player
   *          player landing on this square
   */
  public void landedOn(Player player) {
    super.landedOn(player);
    if (owner == null) {
      // attempt to purchase
      player.attemptBuyProperty(this);
    } else if (owner != player) {
      payRent(player);
    }
  }

  // player pays rent for this property to owner
  private void payRent(Player player) {
    if (!isMortgage()) {
      int rent = getRent(player.getCup().getTotal());
      int reducedrent = player.reduceCash(rent);
      owner.addCash(reducedrent);
    }
  }

  /**
   * Gets the current rent to pay when landing on this property.
   * 
   * @param rolltotal
   *          current total of dice
   * @return current rent
   */
  public abstract int getRent(int rolltotal);

  /**
   * Constructs a string containing the information to show on a square.
   * 
   * @return information string
   */
  public String getInfo() {
    String os = "-";
    if (owner != null) {
      os = owner.getName();
    }
    return getName() + "\n" + getPrice() + "\neigenaar: " + os + "\n"
        + getStatus();
  }

  /**
   * Constructs a string containing a text with status information.
   * 
   * @return status information.
   */
  protected String getStatus() {
    if (isMortgage()) {
      return "hypotheek";
    }
    return "";
  }

  /**
   * Creates the string to show in the property-list of a players. Contains the
   * name and the status of the property.
   * 
   * @return a string that shows property name and the status
   */
  public String toString() {
    return String.format("%-24s%s", getName(), getStatus());
  }
  /**
   * Method used to check if the status of the Street has changed since last check.
   * @param player Used to check if this player owns the whole street
   */
  public void checkGroup(Player player){
	  boolean temp = getGroup().isComplete(player);
	  if(temp != groupcheck){
		  setChanged();
		  notifyObservers();
		  groupcheck = temp;
	  }
  }
 
}
