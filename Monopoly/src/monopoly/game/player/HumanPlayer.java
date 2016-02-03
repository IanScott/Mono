package monopoly.game.player;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import monopoly.SimulationData;
import monopoly.game.MonopolyGame;
import monopoly.game.board.Board;
import monopoly.game.board.LotSquare;
import monopoly.game.board.PropertySquare;
import monopoly.game.board.card.Card;
import monopoly.game.board.card.FineOrChanceCard;
import monopoly.game.dice.Cup;

/**
 * Represents a human player. A human player is a player that is controlled by
 * the user interface.
 * 
 * @author Open Universiteit
 * 
 */
public class HumanPlayer extends Player {

  /** Object to notify user interface for jail interaction. */
  public static final Object JAIL_INTERACTION = new Object();
  /** Object to notify user interface for selling interaction. */
  public static final Object SELL_MESSAGE = new Object();
  /** Object to notify user interface for confirming card. */
  public static final Object CARD_INTERACTION = new Object();
  /** Object to notify user interface for choice of card. */
  public static final Object CARD_CHOICE_INTERACTION = new Object();
  
  private final Lock lock = new ReentrantLock();
  private final Condition confirmCardCondition = lock.newCondition();
  private volatile int answer = -1;


  /**
   * Creates a human player.
   * 
   * @param name
   *          name of player
   * @param board
   *          monopoly game board
   * @param mGame MonopolyGame object
   */
  public HumanPlayer(String name, Board board, MonopolyGame mGame) {
    super(name, board, mGame);
  }

  /**
   * Starts a turn.
   * 
   * @param cup
   *          the cup with dice
   */
  public void takeTurn(Cup cup) {
    super.takeTurn(cup);
    setChanged();
    notifyObservers();
    // wait until player is finished
    while (hasTurn()) {
      SimulationData.getInstance().tinyWait();
    }
    anotherTurn(cup);
  }

  /**
   * Attempts to buy property. This method notifies user interface to start
   * interaction for buying property.
   * 
   * @param psquare
   *          not used
   * @return true
   */
  public boolean attemptBuyProperty(PropertySquare psquare) {
    setChanged();
    notifyObservers();
    return true;
  }

  /**
   * Attempts to leave jail. This method notifies user interface to start jail
   * interaction.
   * 
   * @return true when player left jail, false otherwise
   */
  protected boolean attemptLeavingJail() {
	    answer = -1;
	    setChanged();
	    notifyObservers(JAIL_INTERACTION);
	    // wait for an answer
	    while (answer == -1) {
	      SimulationData.getInstance().tinyWait();
	    }
	    boolean leftJail = false;
	    LOGGER.debug(" leave jail answer is " + answer);
	    switch (answer){
	    case 0:	leftJail = leaveJailByPaying();
	    		break;
	    case 1: leftJail = leaveJailByDice();
	    		break;
	    case 2: leftJail = leaveJailByCard();
	    		break;
	    default: leftJail = false;
	    }
	    setChanged();
	    notifyObservers();
	    return leftJail;
  }

  /**
   * Sets the answer of an interaction with the user.
   * 
   * @param answer
   *          user answer
   */
  public void setAnswer(int answer) {
	    this.answer = answer;
	    lock.lock();
	    confirmCardCondition.signal();
	    LOGGER.debug("confirm card condition is signalled");
	    lock.unlock();
  }

  /**
   * Attempts to sell stuff or set mortgage. This method notifies
   * user interface to start selling/mortgage interaction. This is repeated
   * until cash value of player is positive.
   * 
   * @return true when selling succeeded (enough cash acquired), false otherwise
   */
  protected boolean attemptSelling() {
    while (getCash() < 0 && hasSomethingToSell()) {
      int cash = getCash();
      setChanged();
      notifyObservers(SELL_MESSAGE);
      while (cash == getCash()) {
        // wait until something is sold
        SimulationData.getInstance().tinyWait();
      }
    }
    return getCash() >= 0;
  }

  // has player stuff to sell or set mortgage?
  private boolean hasSomethingToSell() {
    for (PropertySquare psquare : getProperties()) {
      if (!psquare.isMortgage()) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * executes the card pulled by the player.
   * @param card card pulled by the player
   */
  public void executeCard(Card card) {
	  	activeCard = card;
	    answer = -1;
	    setChanged();
		  if (card instanceof FineOrChanceCard){
			  notifyObservers(CARD_CHOICE_INTERACTION);  
		  } else {
			   notifyObservers(CARD_INTERACTION);
		  }
	    // wait for an answer
	    lock.lock();
	    try {
		    while (answer == -1) {
		    	LOGGER.debug("going to be locked for confirm card condition");
		    	confirmCardCondition.await();
		    }
	    } catch(Exception e){
	    	LOGGER.fatal("Unexpected error at confirm card condition");
	    } finally {
	    	lock.unlock();
	    }
	  
	  if (card instanceof FineOrChanceCard){
		  ((FineOrChanceCard) card).setAnswer(answer);
	  }
	  super.executeCard(card);
	  activeCard = null;
	  LOGGER.info("player " + this.getName() + " has excuted card: " + card.getDescription());
  }

@Override
public boolean attempToBuyBuilding(LotSquare lsq) {
	setChanged();
    notifyObservers();
    return true;
}


}
