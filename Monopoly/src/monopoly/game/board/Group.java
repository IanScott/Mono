package monopoly.game.board;

import java.util.*;

import monopoly.game.player.Player;

/**
 * Specification of a group of properties (e.g. city).
 * 
 * @author Open Universiteit
 * 
 */
public class Group {
  private String name; // name of group
  private List<PropertySquare> properties; // properties within group

  /**
   * Creates a group.
   * 
   * @param name
   *          name of the group
   */
  public Group(String name) {
    this.name = name;
    properties = new ArrayList<>();
  }

  /**
   * Adds property to the group.
   * 
   * @param psq
   *          property to be added to group
   */
  public void addProperty(PropertySquare psq) {
    properties.add(psq);
  }

  /**
   * Gets the name of the group.
   * 
   * @return name of the group
   */
  public String getName() {
    return name;
  }

  /**
   * Gets all properties of the group.
   * 
   * @return all properties of the group
   */
  public List<PropertySquare> getProperties() {
    return properties;
  }

  /**
   * Gets the number of properties in group owned by given player.
   * 
   * @param player
   *          the player
   * @return number of properties owned
   */
  public int getNumberOwned(Player player) {
	if(player == null){
		return 1;
	}  
    int count = 0;
    for (PropertySquare psq : properties) {
      if (psq.getOwner() == player) {
        count++;
      }
    }
    return count;
  }

  /**
   * Investigates whether group is completely owned by player.
   * 
   * @param player
   *          the player
   * @return true is group is completely owned, false otherwise
   */
  public boolean isComplete(Player player) {
	if(player == null){
		return false;
	}
	else {
		return getNumberOwned(player) == properties.size();
		}
  }

}
