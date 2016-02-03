package monopoly.game.board;

import monopoly.game.board.card.Card;
import monopoly.game.board.card.CardDeck;
import monopoly.game.board.card.ChanceCardDeck;
import monopoly.game.board.card.ComChestDeck;
import monopoly.game.player.Player;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents the chance square.
 * @author tony
 *
 */
public class ChanceSquare extends Square {
	private static final Logger LOGGER =
			LogManager.getLogger(ChanceSquare.class.getName());
	private CardDeck deck;

	/**
	 * Instantiates the ChanceSquare.
	 * @param name name of chanceSquare
	 * @param deckType deck type number of chanceSquare
	 */
	public ChanceSquare(String name, int deckType) {
		super(name);
		if (deckType == CardDeck.CHANCE_DECK_TYPE){
			deck = ChanceCardDeck.getCardDeck(name);
		} else {
			deck = ComChestDeck.getCardDeck(name);
		}
		LOGGER.info("ChanceSquare succesfully created");
	}
	
	  /**
	   * pick the top card of the deck.
	   * executes the card 
	   * 
	   * @param player
	   *          player landing on this square
	   */
	  public void landedOn(Player player) {
		  super.landedOn(player);
		  
		  Card card = deck.getTopCard();
		  LOGGER.info("player " + player.getName() + " has pulled card: " + card.getDescription());
		  // to do laat description van de kaart aan de speler zien. Moet voor execute voor al cash niet toereikend is
		  player.executeCard(card);
		  LOGGER.info("player " + player.getName() + " has executed card: " + card.getDescription());
		  

	  }

}
