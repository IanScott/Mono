package monopoly.game.board.card;

import java.util.List;

import monopoly.game.player.Player;

/**
 * Class to represent the change or Community Chest cards.
 * This type is used for cards where the player has his birthday
 * @author tony
 *
 */
public class BirthDayCard extends Card {
	
	private static final int BIRTHDAY_PRESENT = 10;

	/**
	 * Constructor for creating a new card.
	 * @param description Description of the card
	 * @param deck type of deck
	 */
	public BirthDayCard (String description, CardDeck deck){
		this.description = description;
		this.deck = deck;
	}

	@Override
	public void executeCard(Player player) {
		List<Player> players = player.getmGame().getPlayers();
		int amount = 0;
		for (Player p:players){
			if(p!=player && !p.isBankrupt()){			
				p.reduceCash(BIRTHDAY_PRESENT);
				LOGGER.debug("10 cash reduced from player " + p.getName() + " because player's "+player.getName()+" birthday ");
				amount = amount + BIRTHDAY_PRESENT;
			}
		}
		player.addCash(amount);	
		LOGGER.info("player " + player.getName() + " is jarig en ontvangt " + amount);
		
		deck.returnCard(this);

	}

}
