package monopoly.game.board.card;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This represents the card decks.
 * @author tony
 *
 */
public abstract class CardDeck {
	/** deck type number for chance deck. */
	public static final int CHANCE_DECK_TYPE = 0;
	/** deck type number for chance deck. */
	public static final int COM_CHEST_TYPE = 1;
	
	private static final int NUMBER_OF_SHAKES = 100;
	/** logger for logging. */
	protected static final Logger LOGGER = LogManager.getLogger(CardDeck.class.getName());
	/** deck with cards. */	
	private ArrayList<Card> deck = new ArrayList<Card>();
	/** deck type of the deck */
	private int deckType;
	private String name;

	/**
	 * Creates a card desk and shakes the cards.
	 * @param name name of card deck
	 */
	protected CardDeck(String name) {
		this.name = name;
		loadCards();
		shakeCards();
	}
	
	

	/**
	 * initial load of the cards in the deck.
	 */
	protected abstract void loadCards();

	/**
	 * Return the top card of the deck.
	 * @return top card of the deck
	 */
	public Card getTopCard() {
		LOGGER.info("removing top card of list");
		return deck.remove(0);
		
	}

	/**
	 * Put the card on the bottom of the deck.
	 * @param card card which is put back on the deck
	 */
	public void returnCard(Card card) {
		deck.add(card);
		LOGGER.info("adding card " + card.getDescription() + " to bottom of deck");
		LOGGER.debug("last card in arraylist is: " + deck.get(deck.size()-1).getDescription());
	}

	/**
	 * Shakes the deck of card to mix the card order.
	 */
	protected void shakeCards() {
		for(int i = 0; i<NUMBER_OF_SHAKES;i++){
			int max = deck.size();
			int from = (int) (Math.random() * max);
			int to = (int) (Math.random() * max);
			Card fromCard = deck.get(from);
			Card toCard = deck.get(to);
			deck.set(to, fromCard);
			deck.set(from, toCard);
			LOGGER.debug(" card no " + from + " swapped with  card no " + to);
		}
		LOGGER.info("deck has been shaken");
		
		
	}

	/**
	 * retrieves the deck with cards.
	 * @return deck with card
	 */
	public ArrayList<Card> getDeck() {
		return deck;
	}

	/**
	 * returns the type of the deck.
	 * @return type of the deck
	 */
	public int getDeckType() {
		return deckType;
	}

	/**
	 * Set the type of the deck.
	 * @param deckType type of deck
	 */
	public void setDeckType(int deckType) {
		this.deckType = deckType;
	}


	/**
	 * retrieves the name of the card deck.
	 * @return name of card deck
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name of the card deck.
	 * @param name name of card deck
	 */
	public void setName(String name) {
		this.name = name;
	}


}