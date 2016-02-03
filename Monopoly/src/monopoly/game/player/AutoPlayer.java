package monopoly.game.player;

import monopoly.SimulationData;
import monopoly.game.MonopolyGame;
import monopoly.game.board.*;
import monopoly.game.board.card.Card;
import monopoly.game.board.card.FineOrChanceCard;
import monopoly.game.dice.Cup;

import java.util.*;

/**
 * Represents an automatic player. The automatic player uses very simple rules
 * for it's decisions.
 * 
 * @author Open Universiteit
 */
public class AutoPlayer extends Player {

  private static final int MINCASH = 100; // minimal cash that player keeps
                                          // before buying something

  /**
   * Creates an automatic player. Implementation of a automatic player with a
   * very simple strategy.
   * 
   * @param name
   *          name of player
   * @param board
   *          monopoly game board
   * @param mGame
   *          monopoly model
   */
  public AutoPlayer(String name, Board board, MonopolyGame mGame) {
    super(name, board, mGame);
  }

  /**
   * executes the card pulled by the player.
   * @param card card pulled by the player
   */
  public void executeCard(Card card) {
	  if (card instanceof FineOrChanceCard){
		  ((FineOrChanceCard) card).setAnswer(1);
	  }
	  super.executeCard(card);
  }
  
  /**
   * Starts a turn.
   * 
   * @param cup
   *          the cup with dice
   */
  public void takeTurn(Cup cup) {
    // throw dice
    super.takeTurn(cup);
    // reset mortgage before throwing dice
    attemptResetMortgage();
    // try to buy Buildings
    for(PropertySquare psq: this.getProperties()){ 	
    	if(psq instanceof LotSquare){
    			attempToBuyBuilding((LotSquare)psq);
    	}
    }    
    SimulationData.getInstance().turnWait(); // for animation
    endTurn();
    anotherTurn(cup);
  }

  /**
   * Attempts to buy a property.
   * Keeps a minimum amount of cash.
   * 
   * @param psquare
   *          the property
   * @return true if purchasing property succeeded, false otherwise
   */
  public boolean attemptBuyProperty(PropertySquare psquare) {
    if (canPay(psquare.getPrice() + MINCASH)) {
      return buyProperty(psquare);
    }
    return false;
  }

  /**
   * Attempts to sell stuff or set mortgage. This process is repeated
   * until cash value of player is positive.
   * 
   * @return true when selling succeeded (enough cash acquired), false otherwise
   */
  protected boolean attemptSelling() {
    List<PropertySquare> list = getProperties();
    boolean mortgageset = true;
    boolean sellhouse = true;
    
    while (getCash() < 0 && sellhouse) {
    	sellhouse = false;
    	for(PropertySquare psquare: list) {
    		if(psquare instanceof LotSquare){
    			if(((LotSquare)psquare).getBuildings()>0){
    				if(canSellBuilding((LotSquare)psquare)){
    					if(sellBuilding((LotSquare)psquare)){
    						sellhouse = true;
    						break;
    					}
    				}
    			}
    		}
    	}
    }
    while (getCash() < 0 && mortgageset) {
      mortgageset = false;
      for (PropertySquare psquare : list) {
        if (setMortgage(psquare)) {
          mortgageset = true;
          break;
        }
      }
    }
    return getCash() >= 0;
  }

  // Attempts to reset a mortgage; returns true if succeeded
  private boolean attemptResetMortgage() {
    boolean success = false;
    List<PropertySquare> list = getProperties();
    for (PropertySquare psquare : list) {
      if (canPay(MINCASH + psquare.getResetMortgagePrice())) {
        resetMortgage(psquare);
        success = true;
      }
    }
    return success;
  }

	 /**
	   * Attempts to leave jail. First tries to pay bail, if that does not succeeds
	   * throws dice.
	   * 
	   * @return true if leaving jail succeeded, false otherwise
	   */
	 protected boolean attemptLeavingJail() {
	    JailSquare js = (JailSquare) getLocation(); // is jail
	    if (hasLeaveJailCard()) {
	    	LOGGER.info(" player " + this.getName() + " leaves jail by card");
	    	return leaveJailByCard();
	    }
	    if (canPay(js.getBail())) { // pay to get out
	      return leaveJailByPaying();
	    } else { // throw dice
	      return leaveJailByDice();
	    }
	  }

	@Override
	public boolean attempToBuyBuilding(LotSquare lsq) {
		if (canPay(lsq.housePrice() + MINCASH)) {
		      return buyBuilding(lsq);
		    }
		    return false;
	}



}
