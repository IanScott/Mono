package monopoly.game.board.card;

import monopoly.game.player.Player;

/**
 * Class to represent the change or Community Chest cards.
 * This type is used for cards where the player has the option to pay a fine of pull a chance card
 * @author tony
 *
 */
public class FineOrChanceCard extends Card {
	private static final int FINE_AMOUNT = 10;
	private int answer;
	private CardDeck changeCardDeck = ChanceCardDeck.getCardDeck(null);
	
	/**
	 * Constructor for creating a new card.
	 * @param description Description of the card
	 * @param deck type of deck
	 */
	public FineOrChanceCard (String description, CardDeck deck){
		this.description = description;
		this.deck = deck;
	}

	@Override
	public void executeCard(Player player) {
		deck.returnCard(this);
		if (answer == 0){
			player.reduceCash(FINE_AMOUNT);
			LOGGER.info("player " + player.getName() + " heeft 10 boete betaald");
		} else {
		  Card card = changeCardDeck.getTopCard();
		  LOGGER.info("player " + player.getName() + " has pulled chance card: " + card.getDescription());
		  // to do laat description van de kaart aan de speler zien. Moet voor execute voor al cash niet toereikend is
		  player.executeCard(card);
		  LOGGER.info("player " + player.getName() + " has executed card: " + card.getDescription());
		}
		
	}

	/**
	 * Returns the answer of the player on the question fine of chance card.
	 * @return answer of the player on the question fine of chance card
	 */
	public int getAnswer() {
		return answer;
	}
	
	
	/**
	 * Sets the answer of the player on the question fine of chance card.
	 * @param answer the answer of the player on the question fine of chance card.
	 */
	public void setAnswer(int answer) {
		this.answer = answer;
	}

}
