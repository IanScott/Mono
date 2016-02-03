package monopoly.game.board.card;


import monopoly.game.player.Player;

/**
 * Class to represent the change or Community Chest cards.
 * This type is used for cards which adds or reduce the cash of the player with a fixed amount
 * @author tony
 *
 */
public class AmountCard extends Card {
	private int amount = 0;
	/**
	 * Constructor for creating a new card.
	 * @param description Description of the card
	 * @param amount The fixed amount which is added to the player's cash
	 * @param deck type of deck
	 */
	public AmountCard (String description, int amount, CardDeck deck){
		this.description = description;
		this.amount = amount;
		this.deck = deck;
		
	}

	/**
	 * Reduces the cash of player with a fixed amount equal to the variable amount.
	 * @param player player which has drawn the card
	 */
	@Override
	public void executeCard(Player player) {
		if (amount<0){
			player.reduceCash(-amount);
		} else {
			player.addCash(amount);
		}
		LOGGER.info("Amount of player " + player.getName() + " changed with " + amount);
		
		
		deck.returnCard(this);
		LOGGER.info("card: " + getDescription() + " has been returned to deck");
	}


}
