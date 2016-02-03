package monopoly.game.dice;

/**
 * Represents a die that can be rolled. The faces of the die have values of 1 to
 * 6.
 * 
 * @author Open Universiteit
 */
public class Die {
  private static final int MAX = 6;
  private int faceValue; // value of visible face

  /**
   * Creates a die. Rolls it to get a valid face value.
   */
  public Die() {
    roll();
  }

  /**
   * Rolls the die.
   */
  public void roll() {
    faceValue = (int) (Math.random() * MAX) + 1;
  }

  /**
   * Returns the value of the visible face (value: 1..6).
   * 
   * @return value of the visible face
   */
  public int getFaceValue() {
    return faceValue;
  }
}
