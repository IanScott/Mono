package monopoly.game.board.card;

import monopoly.game.board.Board;
import monopoly.game.board.PropertySquare;
import monopoly.game.board.Square;
import monopoly.game.player.Player;

/**
 * Class to represent the change or Community Chest cards.
 * This type is used for cards which move the position of the player on the board to a fixed position
 * @author tony
 *
 */
public class MoveToCard extends Card {
	private static final int START_SQUARE = 0;
	private static final int DEFAULT_NO_OF_SQUARES = 40;
	/** constant to indicate the GoToJail square. */
	public static final int GOTOJAIL = -1;
	// The square number in the default board used for determining alternative square in case of other configurations
	private int squareNumber; 
	private boolean forward = true; // direction of the move
	
	
	/**
	 * Constructor for creating a new card.
	 * @param description Description of the card
	 * @param deck type of deck
	 * @param squareNumber The square number in the default board used for determining alternative square in case of other configurations, Start = No 1
	 */
	public MoveToCard (String description, CardDeck deck, int squareNumber){
		this(description, deck, squareNumber, true);		
	}
	
	/**
	 * Constructor for creating a new card.
	 * @param description Description of the card
	 * @param deck type of deck
	 * @param squareNumber The square number in the default board used for determining alternative square in case of other configurations, Start = No 1
	 * @param forward direction of the move
	 */
	public MoveToCard (String description, CardDeck deck, int squareNumber, boolean forward){
		this.description = description;
		this.deck = deck;
		this.squareNumber = squareNumber;
		this.forward = forward;
	}
	
	/**
	 * Moves the player to the target location.
	 */
	@Override
	public void executeCard(Player player) {
		Board board = player.getBoard();
		Square current = player.getLocation();
		Square destination = null;
		destination = getSquare(board);
		
		int nsteps = board.getDeltaSquares(current, destination);
		LOGGER.debug(" No of steps to move is : " + nsteps);
		player.move(nsteps, forward); // move forward

		LOGGER.info("Player has been moved " + nsteps + " number of steps");
		
		deck.returnCard(this);
		
	}

	/**
	 * Determine the alternative square when the location can not be found.
	 * Updates the description
	 * @param board
	 * @return
	 */
	private Square getSquare(Board board){
		// check for start square
		if (squareNumber == START_SQUARE){
			return board.getSquare(0);
		}
		
		// check for jail square 
		if (squareNumber == GOTOJAIL){
			return board.getGoToJailSquare();
		}
		
		// Property squares
		int defSquareNumber = (board.getNumberSquares()) * (squareNumber-1) / (DEFAULT_NO_OF_SQUARES);
		Square defSquare = board.getSquare(defSquareNumber);
		
		// check if square is a property square
		while (!(defSquare instanceof PropertySquare)){
			defSquareNumber = (defSquareNumber+1) % (board.getNumberSquares()) ;
			defSquare = board.getSquare(defSquareNumber);
		}
		
		// check if card description needs updated
		if (defSquareNumber != squareNumber -1){
			description = "Ga verder naar " + defSquare.getName();
		}
		
		LOGGER.debug("square is " + defSquare.getName() + " idNo " + defSquareNumber + " sqNo " + squareNumber);
		return defSquare;
	}

}
