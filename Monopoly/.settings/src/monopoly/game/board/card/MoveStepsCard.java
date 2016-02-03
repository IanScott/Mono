package monopoly.game.board.card;

import monopoly.game.player.Player;

/**
 * Class to represent the change or Community Chest cards.
 * This type is used for cards which move the position of the player on the board with a fixed of steps
 * @author tony
 *
 */
public class MoveStepsCard extends Card {
	private int nSteps;
	
	/**
	 * Constructor for creating a new card.
	 * @param description Description of the card
	 * @param deck type of deck
	 * @param nSteps number of steps which the player has to move
	 */
	public MoveStepsCard (String description, CardDeck deck, int nSteps){
		this.description = description;
		this.deck = deck;
		this.nSteps = nSteps;
	} 
	
	
	

	@Override
	public void executeCard(Player player) {
		// if nSteps > 0 then direction is forwards otherwise backwards
		boolean forward = (nSteps > 0);
		player.move(nSteps, forward); // move forward

		LOGGER.info("Player has been moved " + nSteps + " number of steps");
		
		deck.returnCard(this);

	}

}
