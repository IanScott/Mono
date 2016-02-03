package monopoly.game.board.card;

/**
 * Represent the deck with community chest cards.
 * @author tony
 *
 */
public class ComChestDeck extends CardDeck {

	
	private static ComChestDeck cardDeck;
	
	private ComChestDeck(String name){
		super(name);
		setDeckType(COM_CHEST_TYPE);
	}
	
	/**
	 * Retrieves the deck with chance cards.
	 * @param name name of card deck
	 * @return deck with chance cards.
	 */
	public static ComChestDeck getCardDeck(String name){
		if (cardDeck == null) {
			cardDeck = new ComChestDeck(name);
			LOGGER.info("Community Chest Card deck is instantiated");
		}
		LOGGER.info("Returning communicaty chest cardDeck object");
		return cardDeck;
		
	}

	/**
	 * initial load of the cards in the deck.
	 */
	@Override
	protected void loadCards() {
		
		getDeck().add(new AmountCard("Een vergissing van de bank in uw voordeel, u ontvangt 200", 200, this));
		getDeck().add(new AmountCard("Betaal uw dokters rekening 50", -50, this));
		getDeck().add(new AmountCard("Restitutie inkomstenbelasting, u ontvangt 20", 20, this));
		getDeck().add(new AmountCard("U ontvangt rente van 7% preferente aandelen", 25, this));
		getDeck().add(new AmountCard("U heeft de tweede prijs in een schoonheidswedstrijd gewonnen en ontvangt 10", 10, this));
		getDeck().add(new AmountCard("Door verkoop van effecten ontvangt u 50", 50, this));
		getDeck().add(new AmountCard("U erft 100", 100, this));
		getDeck().add(new AmountCard("Lijfrente vervalt, u ontvangt 100", 100, this));
		getDeck().add(new AmountCard("Betaal het hospitaal 100", -100, this));
		getDeck().add(new AmountCard("Betaal uw verzekeringspremie", -50, this));
		
		getDeck().add(new MoveToCard("Ga verder naar Start", this, 0));
		getDeck().add(new MoveToCard("Ga terug naar Dorpstraat", this, 2, false));
		
		getDeck().add(new MoveToCard("Ga direct naar de gevangenis, ga niet door start", this, MoveToCard.GOTOJAIL, false));
		getDeck().add(new BirthDayCard("U bent jarig en ontvangt van iedere speler 10", this));
		getDeck().add(new LeaveJailCard("Verlaat de gevangenis zonder te betalen", this));
		getDeck().add(new FineOrChanceCard("Betaal 10 boete of neem een kanskaart", this));
		
		LOGGER.info("added " + getDeck().size() + " cards to the deck");
	}

}
