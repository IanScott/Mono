package monopoly.game.board.card;



/**
 * Represent the deck with chance cards.
 * @author tony
 *
 */
public class ChanceCardDeck extends CardDeck {

	private static ChanceCardDeck cardDeck;
	
	
	private ChanceCardDeck(String name){
		super(name);
		setDeckType(CHANCE_DECK_TYPE);
	}
	
	/**
	 * Retrieves the deck with chance cards.
	 * @param name name of card deck
	 * @return deck with chance cards.
	 */
	public static ChanceCardDeck getCardDeck(String name){
		if (cardDeck == null) {
			cardDeck = new ChanceCardDeck(name);
			LOGGER.info("Card deck is instantiated");
		}
		LOGGER.info("Returning cardDeck object");
		return cardDeck;
		
	}
	
	/**
	 * initial load of the cards in the deck.
	 */
	@Override
	protected void loadCards(){
		
		
		getDeck().add(new AmountCard("De Bank betaalt u 50 dividend", 50, this));
		getDeck().add(new AmountCard("Betaal schoolgeld 150", -150, this));
		getDeck().add(new AmountCard("Boete voor te snel rijden 15", -15, this));
		getDeck().add(new AmountCard("Opgebracht wegens dronkenschap 20 boete", -20, this));
		getDeck().add(new AmountCard("U hebt een kruiswoordpuzzel gewonnen en ontvangt 100", 100, this));
		getDeck().add(new AmountCard("Uw bouwverzekeringvervalt, u ontvangt 150", 150, this));
		
		getDeck().add(new MoveToCard("Reis naar station West", this, 16));
		getDeck().add(new MoveToCard("Ga verder naar Kalverstraat", this, 40));
		getDeck().add(new MoveToCard("Ga verder naar Barteljorisstraat", this, 12));
		getDeck().add(new MoveToCard("Ga verder naar Heerestraat", this, 25));
		getDeck().add(new MoveToCard("Ga verder naar Start", this, 0));
	
		getDeck().add(new MoveToCard("Ga direct naar de gevangenis, ga niet door start", this, MoveToCard.GOTOJAIL, false));
		getDeck().add(new MoveStepsCard("Ga drie plaatsen terug",this,-3));
		getDeck().add(new LeaveJailCard("Verlaat de gevangenis zonder te betalen", this));
		
		getDeck().add(new LotCard("U wordt aangeslagen voor straatgeld. Betaal 40 per huis, 115 per hotel", this, 40, 115));
		getDeck().add(new LotCard("Repareer uw huizen. Betaal 25 per huis, 100 per hotel", this, 25, 100));
		
		
		LOGGER.info("added " + getDeck().size() + " cards to the deck");
		
	}
}
