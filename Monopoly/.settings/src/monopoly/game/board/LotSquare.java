package monopoly.game.board;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import monopoly.MonopolyException;


/**
 * Represents a lot square (square of a "city").
 * 
 * @author Open Universiteit
 */
public class LotSquare extends PropertySquare {
  private static final int RENTLENGTH = 6;
  private static final int MAXBUILDINGS = 5;
  private int housePrice = 0;
  private int[] rents = null;
  private int houses = 0;
  /** the logger. */
  protected static final Logger LOGGER =
			LogManager.getLogger(LotSquare.class.getName());
  
  /**
   * Creates a lot square.
   * 
   * @param name
   *          name of square
   * @param group
   *          group square belongs to
   * @param price
   *          buying price
   * @param housePrice
   *          buying price of house/hotel
   * @param rents
   *          array of rents
   * @throws MonopolyException
   *           when number of specified rents is incorrect
   */
  public LotSquare(String name, Group group, int price, int housePrice,
      int[] rents) throws MonopolyException {
    super(name, group, price);
    this.housePrice = housePrice;
    // check for correct size of rent to prevent failures while playing
    if (rents.length == RENTLENGTH) {
      this.rents = rents;
    } else {
      throw new MonopolyException(super.toString() + ": Error in size rent ("
          + rents.length + "!=" + (RENTLENGTH) + ")");
    }
  }
  
  /**
   * Gets the price of a house for this lot.
   * 
   * @return price of house
   */
  public int housePrice() {
    return housePrice;
  }

  /**
   * Gets the price of a house when selling.
   * 
   * @return price of house when selling
   */
  public int getSellHousePrice() {
    return housePrice / 2;
  }

  /**
   * Computes the rent for this square.
   * 
   * @param rolltotal
   *          current total value of dice. not used.
   * @return rent for square
   */
  public int getRent(int rolltotal) {
    int rent = 0;
    if (!isMortgage()) {
      if (getGroup().isComplete(getOwner())&&houses==0) {
        rent = 2 * rents[0];
      } else {
        rent = rents[houses];
      }
    }
    //LOGGER.info("The Rent a Player has to pay rent: "+rent);
    return rent;
  }
  /**
   * Adds Building to Square.
   */
  public void addBuilding(){
	  if(houses<MAXBUILDINGS){
	  houses++;
	  LOGGER.info(this.getName()+"- building added");
	  }
	  setChanged();
	    notifyObservers();
  }
  /**
   * Removes building from Square.
   */
  public void removeBuilding(){
	  if(houses>0){
		  houses--;
		  LOGGER.info(this.getName()+"- building removed");
	  }
	  setChanged();
	  notifyObservers();
  }
  /**
   * Checks if building can be added.
   * @return true if building can be added else false
   */
  public boolean canAddBuilding(){
	 if(houses >= MAXBUILDINGS||!mortgageCheck()){
		 return false;
		 } 
	 
	 ArrayList<PropertySquare> list = (ArrayList<PropertySquare>)getGroup().getProperties();
	 int min = Integer.MAX_VALUE;
	 int max = 0;
	 int listsize = list.size();
	 if(listsize ==1){
		 return true;
		 }
	 
	 if(listsize>1){
		 houses++; //Temporally increase houses by 1
		 for(PropertySquare ps: list){
			 LotSquare ls = (LotSquare)ps;
			int i = ls.getBuildings();
			if(i> max){
				max = i;
				}
			if(i< min){
				min = i;
				}
		 } 
		 houses--; // reset houses
	 }
	 
	 return (Math.abs(max - min) <=1);

  }
  /**
   * Checks if building can be removed.
   * @return true if building can be removed, else false
   */
  public boolean canRemoveBuilding(){
	  if(houses <= 0){
		  return false;
	  }
	  ArrayList<PropertySquare> list = (ArrayList<PropertySquare>) getGroup().getProperties();
		 int min = Integer.MAX_VALUE;
		 int max = 0;
		 int listsize = list.size();
		 if(listsize == 1){
			 return true;
			 }
		 if(list.size()>1){
			 houses--; //Temporally dencrease houses by 1
			 for(PropertySquare ps: list){
				 LotSquare ls = (LotSquare)ps;
				int i = ls.getBuildings();
				if(i> max){
					max = i;
					}
				if(i< min){
					min = i;
					}
			 } 
			 houses++; // reset houses
		 }
		 return (Math.abs(max - min) <=1);
  }
  
 /**
  * Returns amount of buildings on Square.
  * @return amount of buildings on Square
  */
  public int getBuildings(){
	  return houses;
  }
  /**
   * Method checks is a property can be mortgaged.
   * @return true if possible else false
   */
  public boolean canBeMortgaged(){
	  return !this.isMortgage()&& buildingCheck();
  }
  
  private boolean mortgageCheck(){
	  for(PropertySquare ps: this.getGroup().getProperties()){
		  if(ps.isMortgage()){
			  return false;
			  }
	  }
	  return true;
  }
  private boolean buildingCheck(){
	  for(PropertySquare ps: this.getGroup().getProperties()){
		  if(((LotSquare) ps).getBuildings()>0){
			  return false;
			  }
	  }
	  return true;
  }
 
}
