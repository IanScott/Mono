package monopoly.game.dice;

/**
 * Represents (a cup with) a number of dice.
 * 
 * @author Open Universiteit
 */
public final class Cup {

  private Die[] dice; // an array of dice

  /**
   * Creates a new cup with a number of dice.
   * 
   * @param number
   *          the number of dice in the cup
   */
  public Cup(int number) {
    dice = new Die[number];
    for (int i = 0; i < number; i++) {
      dice[i] = new Die();
    }
  }

  /**
   * Rolls the dice.
   */
  public void roll() {
    for (int i = 0; i < dice.length; i++) {
      dice[i].roll();
    }
  }

  /**
   * Gets the current total of the dice.
   * 
   * @return the current total of the dice
   */
  public int getTotal() {
    int total = 0;
    for (int i = 0; i < dice.length; i++) {
      total += dice[i].getFaceValue();
    }
    return total;
  }

  /**
   * Indicates whether all dice have the same face value.
   * 
   * @return true when all face values are equal, false otherwise
   */
  public boolean equalValues() {
    for (int i = 1; i < dice.length; i++) {
      if (dice[0].getFaceValue() != dice[i].getFaceValue()) {
        return false;
      }
    }
    return true;
  }

}
