package monopoly.game.player;

import monopoly.game.MonopolyGame;
import monopoly.game.board.*;
import monopoly.game.board.card.Card;
import monopoly.game.board.card.CardDeck;
import monopoly.game.dice.Cup;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a player of the monopoly game. It contains the general
 * functionality of a player of the monopoly game. Player is an abstract class,
 * so no instances of this class can be made. Players for the game are always
 * subclasses of Player.
 * 
 * @author Open Universiteit
 */
public abstract class Player extends Observable {

  private static final int STARTCASH = 1500;
  private static final int PASSSTARTCASH = 200;
  private static final int MAXDOUBLES = 3; // number of doubles to go to jail
  private static final int MAXJAILTRIES = 3;
  /** the logger. */
  protected static final Logger LOGGER =
			LogManager.getLogger(Player.class.getName());
  private String name;
  private Board board;
  private Cup cup;
  private Square location;
  private int cash;
  private boolean inJail;
  private int countDouble;
  private int nrJailTries;
  private MonopolyGame mGame;
  /** chance of community chest card which has been pulled by player. */
  protected Card activeCard;
  /** Leave the jail without paying cars owned by player. */
  protected ArrayList<Card> cards = new ArrayList<Card>();

  /**
   * Creates a new player. Sets some general information for all types of
   * players, such as start cash and start position on the board.
   * 
   * @param name
   *          name of the player
   * @param board
   *          the game board
   * @param mGame MonopolyGame object
   */
  public Player(String name, Board board, MonopolyGame mGame) {
    this.name = name;
    this.board = board;
    this.setmGame(mGame);
    this.cup = null;
    cash = STARTCASH;
    location = board.getStartSquare(); // start position
    inJail = false;
    countDouble = 0;
  }

  /**
   * Gets player's name.
   * 
   * 
   * @return name of player
   */
  public String getName() {
    return name;
  }

  /**
   * Gets player's amount of cash.
   * 
   * @return amount of cash
   */
  public int getCash() {
    return cash;
  }

  /**
   * Has the player sufficient cash?
   * 
   * @param amount
   *          the amount of cash to investigate.
   * @return true when player has at least the requested amount of cash, false
   *         otherwise
   */
  public boolean canPay(int amount) {
    return cash >= amount;
  }

  /**
   * Gets board on which player plays.
   * 
   * @return game board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Gets player's current location.
   * 
   * @return current location of player
   */
  public Square getLocation() {
    return location;
  }

  /**
   * Gets the cup of the player.
   * 
   * @return cup hold by player
   */
  public Cup getCup() {
    return cup;
  }

  /**
   * Is player bankrupt?
   * 
   * @return true if player is bankrupt, false otherwise
   */
  public boolean isBankrupt() {
    return location == null;
  }

  /**
   * Is player in jail?
   * 
   * @return true if player is in jail, false otherwise
   */
  public boolean isInJail() {
    return inJail;
  }

  /**
   * Gets a list of all properties of player.
   * 
   * @return list of properties
   */
  public List<PropertySquare> getProperties() {
    return board.getProperties(this);
  }

  /**
   * Is player owner of property?
   * 
   * @param psquare
   *          property square
   * @return true if player is owner of property, false otherwise.
   */
  public boolean isOwner(PropertySquare psquare) {
    return psquare.getOwner() == this;
  }

  /**
   * Calculates the total value of the player. The total value includes cash and
   * cost price of properties.
   * 
   * @return the total value of the player
   */
  public int getTotalValue() {
    int value = cash;
    for (PropertySquare psquare : getProperties()) {
      value += psquare.getValue();
    }
    return value;
  }

  /**
   * Starts a turn. Player gets the cup to roll the dice.
   * 
   * @param cup
   *          the cup with dice to roll
   */
  public void takeTurn(Cup cup) {
    if (isBankrupt()) { // do nothing
      return;
    }
    this.cup = cup;
    cup.roll();

    // if in jail, try to leave
    if (isInJail()) {
    	LOGGER.debug(" player " + getName() + " is in jail");
      boolean leftJail = attemptLeavingJail();
      if (!leftJail) { // stay in jail
        return;
      }
    }
    // check for number of doubles thrown
    if (mustGoToJail()) {
      sendToJail();
      return;
    }

    // move to new location
   move(cup.getTotal());
   
  }

  /**
   * Ends the turn.
   */
  public void endTurn() {
    cup = null;
    update();
  }

  /**
   * If player has thrown double faces, take another turn.
   * 
   * @param cup
   *          The cup with dice
   */
  protected void anotherTurn(Cup cup) {
    // double faces: another turn
    if (!isBankrupt() && !isInJail() && cup.equalValues()) {
      takeTurn(cup);
    } else {
      countDouble = 0;
    }
  }

  /**
   * It is player turn?
   * 
   * @return true if player has turn, false otherwise
   */
  public boolean hasTurn() {
    return cup != null;
  }

  /**
   * Moves the player over given amount of steps.
   * 
   * @param nsteps
   *          amount of steps.
   */
  public void move(int nsteps) {
	  move(nsteps, true);
  }
  
  /**
   * Moves the player over given amount of steps.
   * @param nsteps amount of steps 
   * @param forward if true then the player receives money when passing the start square, otherwise not.
   */
  public void move(int nsteps, boolean forward){
    Square newloc = board.getSquare(location, nsteps);
    Square oldloc = location;
    if (forward && board.startPassed(oldloc, newloc)) {
      addCash(PASSSTARTCASH);
    }
    location = newloc;
    oldloc.leftFrom(this);
    newloc.landedOn(this);
    update();
  }
  
  /**
   * Adds money to cash.
   * 
   * @param amount
   *          amount to be added to cash
   */
  public void addCash(int amount) {
    cash += amount;
    LOGGER.info(" player " + this.getName() + " receives Cash: "+amount);
    update();
  }

  /**
   * Reduces cash of player (when he has to pay). If player has not enough cash,
   * properties must be mortgaged or sold.
   * 
   * @param amount
   *          amount to reduce.
   * @return the reduced amount (can be less than amount when player is
   *         bankrupt)
   */
  public int reduceCash(int amount) {
    int reduced = 0;
    // player has enough cash?
    if (canPay(amount)) {
      reduced = amount;
      cash -= amount;
      LOGGER.info(" player " + this.getName() + " Pays Cash: "+amount);
    } else { // try to sell/mortgage properties
      cash -= amount;
      if (attemptSelling()) {
        reduced = amount;
      } else {
        reduced = amount + cash; // cash is negative, cannot be payed
        makeBankrupt();
      }
    }
    update();
    return reduced;
  }

  // Makes player bankrupt.
  private void makeBankrupt() {
    cup = null;
    cash = 0;
    location.leftFrom(this);
    location = null;
    
    // make properties available for others
    for (PropertySquare psquare : getProperties()) {
      psquare.clear();
    }
  }

  /**
   * Attempt to purchase a property.
   * 
   * @param psquare
   *          property
   * @return when purchasing succeeded, otherwise false
   */
  public abstract boolean attemptBuyProperty(PropertySquare psquare);

  /**
   * Attempts to sell stuff (or set mortgage) to get cash.
   * 
   * @return true when selling succeeded (enough cash acquired), false otherwise
   */
  protected abstract boolean attemptSelling();

  // Determine whether player must go to jail.
  private boolean mustGoToJail() {
    if (cup.equalValues()) {
      countDouble++;
      if (countDouble == MAXDOUBLES) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sends player to jail.
   */
  public void sendToJail() {
    inJail = true;
    Square oldloc = location;
    location = board.getJailSquare();
    oldloc.leftFrom(this);
    location.landedOn(this);
    update();
  }

  /**
   * Tries to leave jail.
   * 
   * @return true if leaving jail succeeded, false otherwise
   */
  protected abstract boolean attemptLeavingJail();

  /**
   * Leaves the jail by paying fine.
   * 
   * @return true when leaving jail succeeded (enough cash), false otherwise
   */
  protected boolean leaveJailByPaying() {
    // pay to get out
    JailSquare jsquare = board.getJailSquare();
    reduceCash(jsquare.getBail());
    if (isBankrupt()) {
      return false;
    }
    leaveJail();
    return true;
  }

  /**
   * Leaves the jail by throwing dice.
   * 
   * @return true when leaving jail succeeded (thrown double), false otherwise
   */
  protected boolean leaveJailByDice() {
    // throw dice to leave jail
    if (cup.equalValues()) {
      leaveJail();
      return true;
    }
    nrJailTries++;
    if (nrJailTries == MAXJAILTRIES) {
      // maximum tries reached, 
      if (hasLeaveJailCard()) {
    	  return leaveJailByCard();
      } else {
    	  return leaveJailByPaying();  
      }
    }
    return false;
  }

  // Resets jail-values.
  private void leaveJail() {
    nrJailTries = 0;
    inJail = false;
  }

  // Notifies user interface that player properties are changed.
  private void update() {
    setChanged();
    notifyObservers();
  }


  /**
   * Can property be bought by player? A player can buy property when
   * property is not sold and player has enough cash.
   * 
   * @param propertySquare property
   * @return true when player is allowed to buy property, false otherwise.
   */
  public boolean canBuyProperty(PropertySquare propertySquare) {
    return (propertySquare.getOwner() == null && canPay(propertySquare.getPrice()));
  }

  /**
   * Buys property. A player can only buy a property when cash is
   * sufficient.
   * 
   * @param propertySquare property
   * @return true when buying succeeded, false otherwise
   */
  public boolean buyProperty(PropertySquare propertySquare) {
    int price = propertySquare.getPrice();
    if (canBuyProperty( propertySquare)) {
      // buy
      propertySquare.setOwner(this);
      reduceCash(price);
      return true;
    }
    return false;
  }

  
  /**
   * Can player set mortgage property for given property? 
   * 
   * @param propertySquare
   *          property
   * @return true when property can be mortgaged by player, false otherwise
   */
  public boolean canSetMortgage(PropertySquare propertySquare) {
    return isOwner(propertySquare) && propertySquare.canBeMortgaged();
  }
  
  /**
   * Sets mortgage for given property.
   * 
   * @param propertySquare property
   * @return true when setting mortgage succeeded, false otherwise
   */
  public boolean setMortgage(PropertySquare propertySquare) {
    if (canSetMortgage(propertySquare)) {
      propertySquare.setMortgage(true);
      addCash(propertySquare.getMortgagePrice());
      return true;
    }
    return false;
  }

  /**
   * Can mortgage of property be reset by player?
   * 
   * @param propertySquare property
   * @return true when mortgage can be removed, false otherwise
   */
  public boolean canResetMortgage(PropertySquare propertySquare) {
    return isOwner(propertySquare) && propertySquare.isMortgage() 
        && canPay(propertySquare.getResetMortgagePrice());
  }

  /**
   * Resets mortgage for given property.
   * 
   * @param propertySquare property
   * @return true when resetting mortgage succeeded, false otherwise
   */
  public boolean resetMortgage(PropertySquare propertySquare) {
    if (canResetMortgage(propertySquare)) {
      propertySquare.setMortgage(false);
      reduceCash(propertySquare.getResetMortgagePrice());
      return true;
    }
    return false;
  }

  /**
   * executes the card pulled by the player.
   * @param card card pulled by the player
   */
  public void executeCard(Card card) {
	  card.executeCard(this);
  }

  /**
   * Returns the active card of the player.
   * @return active card of the player.
   */
	public Card getActiveCard() {
		return activeCard;
	}

	/**
	 * Sets the active card for the player.
	 * @param activeCard card that needs to be set as active card.
	 */
	public void setActiveCard(Card activeCard) {
		this.activeCard = activeCard;
	}
	
	/**
	 * Adds a leave the jail card to the cards own by the player.
	 * @param leaveJailCard card that is added
	 */
	public void addLeaveJailCard(Card leaveJailCard) {
		cards.add(leaveJailCard);
	}
	
	/**
	 * Checks if the player has a leave the jail card.
	 * @return true when the player has a leave the jail card.
	 */
	public boolean hasLeaveJailCard(){
		boolean hasCard = (cards.size()>0);
		LOGGER.info(" has jail card is " + hasCard );
		return hasCard;
	}
	
	/**
	 * Leave the jail by using leave the jail card.
	 * @return true when leaving the jail by card succeeds.
	 */
	protected boolean leaveJailByCard(){	
		if (cards.size()>0){
			Card card = cards.remove(0);
			leaveJail();
			CardDeck deck = card.getDeck();
			deck.returnCard(card);
			LOGGER.info("Jail card returned to deck " + deck.getDeckType() );
			return true;		
		}
		return false;
	}
	
	/**
	 * Retrieve the monopoly game object.
	 * @return the monopoly game object.
	 */
	public MonopolyGame getmGame() {
		return mGame;
	}

	/**
	 * Sets the monopoly game object.
	 * @param mGame monopoly game object which is set.
	 */
	public void setmGame(MonopolyGame mGame) {
		this.mGame = mGame;
	}


	/**
	 * Tries to build building.
	 * @param lsq the LotSquare you want to to build on
	 * @return true if a player builds on a square else false
	 */
	public abstract boolean attempToBuyBuilding(LotSquare lsq);
	
	/**
	 * Checks whether a player can build on a property.
	 * @param lsq the LotSquare you want to check if a player can build on
	 * @return true if a player can build on a square else false
	 */
	public boolean canBuyBuilding(LotSquare lsq){
		 return (lsq.getGroup().isComplete(this)&&canPay(lsq.housePrice())&& board.canBuyBuilding(lsq));
	}

	/**
	 * Buys a building for the selected square.
	 * @param lsq the LotSquare you want to build on
	 * @return true if it works else false
	 */
	public boolean buyBuilding(LotSquare lsq){
		  int price = lsq.housePrice();
		    if (canBuyBuilding(lsq)) {
		      // buy
		      board.buyBuilding(lsq);
		      reduceCash(price);
		      return true;
		    }
		    return false;
	}
	
	/**
	 * Checks if player can sell building on Square.
	 * @param lsq LotSquare building is sold
	 * @return true if building can be sold, else false
	 */
	public boolean canSellBuilding(LotSquare lsq){
		  return board.canSellHouse(lsq);
	}
	/**
	 * Sells a building on the square.
	 * @param lsq LotSquare were building is sold
	 * @return true if building is sold, else false
	 */
	public boolean sellBuilding(LotSquare lsq){
		  if(canSellBuilding(lsq)){
			  board.sellHouse(lsq);
			  addCash(lsq.getSellHousePrice());
			  return true;
		  }
		  return false;
	}
  
}