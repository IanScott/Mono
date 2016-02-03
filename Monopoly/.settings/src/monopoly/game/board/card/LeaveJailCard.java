package monopoly.game.board.card;

import monopoly.game.player.Player;


/**
 * Class to represent the change or Community Chest cards.
 * This type is used for cards which give the player of leave the jail card.
 * @author tony
 *
 */
public class LeaveJailCard extends Card {
	
	/**
	 * Constructor for creating a new card.
	 * @param description Description of the card
	 * @param deck type of deck
	 */
	public LeaveJailCard (String description, CardDeck deck){
		this.description = description;
		this.deck = deck;
	}

	
	@Override
	public void executeCard(Player player) {
		player.addLeaveJailCard(this);
	}

}
