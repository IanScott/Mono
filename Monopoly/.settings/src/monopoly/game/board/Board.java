package monopoly.game.board;

import java.util.*;
import monopoly.MonopolyException;
import monopoly.game.Bank;
import monopoly.game.player.Player;

/**
 * Represents the game board. Contains all squares (locations).
 * 
 * @author Open Universiteit
 * Edited by Ian van Nieuwkoop
 */
public class Board {
  // list of all squares
  private List<Square> squares;
  private Bank bank;
  private static final int MAXHOUSES = 4;
  private static final int HOTEL = 5;
  
  /**
   * Creates a board.
   * 
   * @throws MonopolyException
   *           when error occurs during creation of board.
   * @param squares the list of squares to be added to the game
   */
  public Board(List<Square> squares) throws MonopolyException {
    this.squares = squares;
    this.bank = new Bank();
  }

  /**
   * Gets the number of squares on the game board.
   * 
   * @return the number of squares
   */
  public int getNumberSquares() {
    return squares.size();
  }

  /**
   * Gets the square at certain position on the board.
   * 
   * @param index
   *          index of the position
   * @return the square at position index
   */
  public Square getSquare(int index) {
    return squares.get(index);
  }

  /**
   * Gets the square with given name. When more squares have the same name, the
   * first one is returned.
   * 
   * @param name
   *          name of the square
   * @return square with given name, null if name does not exists
   */
  public Square getSquare(String name) {
    for (Square sq : squares) {
      if (sq.getName().equals(name)) {
        return sq;
      }
    }
    return null;
  }

  /**
   * Gets the new square that is reached after moving a distance.
   * 
   * @param from
   *          old location
   * @param distance
   *          distance to move (negative value allowed to move backwards)
   * @return the new location
   */
  public Square getSquare(Square from, int distance) {
    int toIndex = (squares.indexOf(from) + distance) % getNumberSquares();
    if (toIndex < 0) {
      toIndex += squares.size();
    }
    return squares.get(toIndex);
  }

  /**
   * Gets the start square.
   * 
   * @return the start square
   */
  public Square getStartSquare() {
    return squares.get(0);
  }

  /**
   * Gets the square with the jail.
   * 
   * @return the jail square
   */
  public JailSquare getJailSquare() {
    for (Square sq : squares) {
      if (sq instanceof JailSquare) {
        return (JailSquare) sq;
      }
    }
    return null;
  }

  /**
   * Determines whether player passed start position.
   * 
   * @param from
   *          location player left
   * @param to
   *          location player landed on
   * @return true if start position is passed, false otherwise
   */
  public boolean startPassed(Square from, Square to) {
    return squares.indexOf(from) > squares.indexOf(to);
  }

  /**
   * Gets all properties of player.
   * 
   * @param player
   *          player
   * @return all properties of player
   */
  public List<PropertySquare> getProperties(Player player) {
    ArrayList<PropertySquare> list = new ArrayList<PropertySquare>();
    for (Square sq : squares) {
      if (sq.isProperty() && ((PropertySquare) sq).getOwner() == player) {
        list.add((PropertySquare) sq);
      }
    }
    return list;
  }
  
  /**
   * Calculates the number of squares between two squares.
   * @param from square from where is counted
   * @param to square to which is counted
   * @return number of squares between the from and to square
   */
  public int getDeltaSquares(Square from, Square to){
	  int intFrom = squares.indexOf(from);
	  int intTo = squares.indexOf(to);
	  int delta = (intTo - intFrom ) % (squares.size()-1);
	  return delta;
	  
	  
  }

  
  /**
   * Check whether a building can be built on a Square.
   * @param lsq the square to check
   * @return if a building can be placed on a Square or not
   */
  public boolean canBuyBuilding(LotSquare lsq){
	  
	  Boolean b1 = lsq.canAddBuilding();
	  Boolean b2 = false;
	  if(lsq.getBuildings()<MAXHOUSES){
		  b2 = bank.canAddHouse();
	  } 
	  else{
		  b2 = bank.canAddHotel();
		  }
	  return b2&&b1;
  }
  /**
   * Places a building on a square.
   * @param lsq the square to have a building placed on
   */
  public void buyBuilding(LotSquare lsq){
	  if(lsq.getBuildings()<MAXHOUSES){
		  bank.takeHouse();
	  }
	  else{
		  bank.returnHouses(MAXHOUSES);
		  bank.takeHotel();
		  }
	  lsq.addBuilding();
  }
  /**
   * Checks if square can sell a building.
   * @param lsq the square to be checked
   * @return whether a square can sell a building or not
   */
  public boolean canSellHouse(LotSquare lsq){
	  return lsq.canRemoveBuilding();
  }
  
  /**
   * Removes a building from a square.
   * @param lsq the square where a building is removed
   */
  public void sellHouse(LotSquare lsq){
	  if(lsq.getBuildings()==HOTEL){
		  
		  bank.returnHotel();
		  if(bank.getHouses()>MAXHOUSES-1){
			  bank.takeHouses(MAXHOUSES);
			  }
		  else{ /// add method for when not enough houses to sell hotel
		  }
		  
	  }
	  else{
		  bank.returnHouse();
		  }
	  lsq.removeBuilding();
  }
  


  /**
   * Returns the go to Jail Square.
   * @return GoToJailSquare.
   */
  public Square getGoToJailSquare() {
    for (Square sq : squares) {
		if (sq instanceof GoToJailSquare) {
	      return sq;
	    }
	}
	return null;
  }

}