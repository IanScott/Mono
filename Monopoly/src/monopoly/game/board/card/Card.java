package monopoly.game.board.card;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import monopoly.game.player.Player;

/**
 * Class to represent the change or Community Chest cards. 
 * @author tony
 *
 */
public abstract class Card {
	/** logger. */
	protected static final Logger LOGGER = LogManager.getLogger(Card.class.getName());
	
	/** text on card. */
	protected String description = null;
	
	/** deck of cards. */
	protected CardDeck deck;
		

	/**
	 * Executes the description of the card for player who has drawn the card.
	 * @param player player who has drawn the card
	 */
	public abstract void executeCard(Player player);
	
	/**
	 * Returns the card description.
	 * @return Description of the card
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns the deck of the card.
	 * @return deck of the card.
	 */
	public CardDeck getDeck() {
		return deck;
		
	}
}
