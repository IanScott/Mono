package monopoly.game.board.card;

import java.util.List;
import monopoly.game.board.PropertySquare;
import monopoly.game.board.LotSquare;
import monopoly.game.player.Player;

/**
 * Class to represent the change or Community Chest cards.
 * This type is used for cards player has to pay a fixed amount per house and / or hotel
 * @author tony
 *
 */
public class LotCard extends Card {
	private static final int AANTAL_HOTEL = 5;
	private int housePrice;
	private int hotelPrice;

	
	/**
	 * Constructor for creating a new card.
	 * @param description Description of the card
	 * @param deck type of deck
	 * @param housePrice price which have to be paid for a hotel
	 * @param hotelPrice price which have to be paid for a house
	 */
	public LotCard(String description, CardDeck deck, int housePrice, int hotelPrice) {
		this.description = description;
		this.deck = deck;
		this.housePrice = housePrice;
		this.hotelPrice = hotelPrice;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeCard(Player player) {
		
		int numberHouses = 0;
		int numberHotels = 0;
		List<PropertySquare> properties = player.getProperties();
		for(PropertySquare psq: properties){
			if (psq instanceof LotSquare) {
				LotSquare lotSquare = (LotSquare)psq;
				int houses = lotSquare.getBuildings(); // to do gethouses methode 
				if (houses == AANTAL_HOTEL){
					numberHotels++;
				} else {
					numberHouses = numberHouses+ houses;
				}
			}

		}
		LOGGER.info("player " + player.getName() + " has " + numberHouses + " houses and " + numberHotels + " hotels");
		
		int amount = housePrice * numberHouses + hotelPrice * numberHotels;
		player.reduceCash(amount);
		LOGGER.info("cash of player " + player.getName() + " reduced with " + amount);
		
		deck.returnCard(this);
		
	}

}
