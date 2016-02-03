package monopoly.game;
/**
 * Class is responsible the management of the houses and hotels.
 * @author Ian van Nieuwkoop
 *
 */
public class Bank {
	private static final int MAXHOUSES = 32;
	private static final int MAXHOTELS = 12;
	private int houses = MAXHOUSES; 
	private int hotels = MAXHOTELS;
	
	/**
	 * Constructor.
	 */
	public Bank(){}
	/**
	 * Method returns the available houses.
	 * @return the available houses
	 */
	public int getHouses(){
		return houses;
	}
	
	/**
	 * Method checks if a player can add a house.
	 * @return if a player can add a house or not
	 */
	public boolean canAddHouse(){
		return (houses>=1);
	}
	/**
	 * Method check if a player can add a hotel.
	 * @return if a player can add a hotel or not
	 */
	public boolean canAddHotel(){
		return (hotels>=1);
	}
	/**
	 * Returns a house.
	 */
	public void returnHouse(){
		houses++;
	}
	/**
	 * Returns multiple houses.
	 * @param hs the amount of houses to return
	 */
	public void returnHouses(int hs){
		houses= houses +hs;
	}
	/**
	 * Returns a hotel.
	 */
	public void returnHotel(){
		hotels++;
	}
	/**
	 * Takes a house.
	 */
	public void takeHouse(){
		houses--;
	}
	/**
	 * Takes multiple houses.
	 * @param hs the amount of houses to return
	 */
	public void takeHouses(int hs){
		houses= houses-hs;
	}
	/**
	 * Takes a hotel.
	 */
	public void takeHotel(){
		hotels--;
	}
}
