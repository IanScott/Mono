package monopoly.game;

import java.io.File;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import monopoly.MonopolyException;
import monopoly.SimulationData;
import monopoly.game.board.Board;
import monopoly.game.dice.Cup;
import monopoly.game.player.*;

/**
 * Monopoly game. Controls the entire game.
 * 
 * @author Open Universiteit.
 */
public class MonopolyGame {
  private static final int NUMBER_OF_DICE = 2; // monopoly is played with 2 dice
  private static final int MAX_ROUNDS = 200;
  private List<Player> players; // players
  private Board board; // game board
  private Cup cup;  // cup with dice
  private boolean transactionFlag = false; // flag to signal that a transaction is ongoing
  /** logger for logging. */
	protected static final Logger LOGGER = LogManager.getLogger(MonopolyGame.class.getName());
  
  /**
   * Creates a new monopoly game. Creates the board and the players of the game.
   * 
   * @throws MonopolyException
   *           when an error occurs during loading game
   */
  public MonopolyGame() throws MonopolyException {
    players = new ArrayList<Player>();
    cup = new Cup(NUMBER_OF_DICE);
  }
  
  /**
   * Initializes the Monopoloy Game. Must be run.
   * @param xml the xml File to be used to load the board. If Null the default Dutch board
   * 			will be used.
   * @throws MonopolyException Exception thrown
   */
  public void initialize(File xml) throws MonopolyException {
    if(xml ==null){
    	board = BoardLoader.loadBoard();
    } else{
    	board =  BoardLoader.loadXml(xml);
    }
    
    players = new ArrayList<Player>();
  }
  
  /**
   * Method adds players to the game.
   * @param name the name of the player to add
   * @param cpu where a player is a computer player or not
   */
  public void addPlayer(String name, Boolean cpu){
	  if(cpu){
		  players.add(new AutoPlayer(name, board, this));
	  }else {
		  players.add(new HumanPlayer(name, board, this));
	  }
  }
  /**
   * Returns the game board used in this game.
   * 
   * @return the game board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Returns a the list of all players participating in the game.
   * 
   * @return the list of all players
   */
  public List<Player> getPlayers() {
    return players;
  }

  /**
   * Returns the number of players participating in the game.
   * 
   * @return number of players
   */
  public int getNumberPlayers() {
    return players.size();
  }

  /**
   * Plays the game. Starts the game loop and runs the game for a number of
   * rounds.
   */
  public void playGame() {
    for (int i = 0; i < MAX_ROUNDS; i++) {
    	int nrplayers = 0;
    	for(Player p: players){
    		if(p.getLocation()!=null){ //check if player is not bankrupt
    			nrplayers++;
    		}
    	}
    	if(nrplayers<2){
    		Player winner = null;
    	
    		for(Player p: players){
        		if(p.getLocation()!=null){ //check which player has won
        			winner = p;
        		}
    		}
    		LOGGER.info(winner.getName()+" HAS WON AND IS THE LAST STANDING");
    		break; // end of game
    		
    	}
      playRound();
    }
    Player richest = players.get(0);
    for(Player p: players){
    	if(p.getCash()> richest.getCash()){
    		richest = p;
    	}
    }
    LOGGER.info("GAME IS FINISHED. Player: "+richest.getName()+" is the RICHEST Player");
  }

  /**
   * Plays one round of the game. All players get one turn.
   */
  private void playRound() {
	  LOGGER.info("NEW ROUND --------------------------------------------------------------- ");
    for (Player player : players) {
      if (!player.isBankrupt()) {
      	// check if there are no ongoing transactions before next player takes turn
  	    while (transactionFlag) {
  		      SimulationData.getInstance().tinyWait();
  		    }
        player.takeTurn(cup);
      }
    }
  }
  	
  /**
   * Sets the transaction flag which indicates that a sell a buy is ongoing.
   * @param transactionFlag value which is set to the transaction flag.
   */
	public void setTransactionFlag(boolean transactionFlag) {
		this.transactionFlag = transactionFlag;
	}

}