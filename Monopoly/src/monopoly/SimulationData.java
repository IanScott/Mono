package monopoly;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * This class defines data used for the simulation process (speed of simulation
 * etc.). For example, to be able to see what happens, it is convenient to
 * interrupt the simulation after each turn, so one can see the result of the
 * last actions. The properties of the data can be defined in the
 * <code>simulation.props</code> file.
 * 
 * @author Open Universiteit
 */
public final class SimulationData {
  private static final String SIMULATION_PROPS_FILE = "simulation.props";
  private static final String NO_WAIT = "0";
  private static final String TINY_WAIT = "10";
  // instance for singleton
  private static SimulationData instance;
  // attributes for simulation settings
  private int turnWait;
  private int gotojailWait;
  private int tinyWait;

  /**
   * Wait after a user turn.
   */
  public void turnWait() {
    try {
      Thread.sleep(turnWait);
    } catch (InterruptedException e) {
    }
  }

  /**
   * Wait before a user is moved to jail.
   */
  public void gotojailWait() {
    try {
      Thread.sleep(gotojailWait);
    } catch (InterruptedException e) {
    }
  }

  /**
   * Wait for a tiny moment. Used when waiting for answer of player.
   */
  public void tinyWait() {
    try {
      Thread.sleep(tinyWait);
    } catch (InterruptedException e) {
    }
  }

  /**
   * Get the instance of the simulation properties. Only one instance exists
   * (singleton). All properties are read from file and stored in this instance.
   * 
   * @return the instance of the simulation properties.
   */
  public static synchronized SimulationData getInstance() {
    if (instance == null) {
      instance = new SimulationData();
    }
    return instance;
  }

  /**
   * Constructs the simulation properties. Reads the properties from file and
   * stores them in attributes.
   */
  private SimulationData() {
    Properties simprops = new Properties();
    FileInputStream in = null;
    try {
      in = new FileInputStream(SIMULATION_PROPS_FILE);
      simprops.load(in);
    } catch (FileNotFoundException e) {
      System.out.println("Cannot read properties, using defaults instead");
    } catch (IOException e) {
      System.out
          .println("Error while reading simulation properties, using defaults instead");
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {}
      }
    }
    init(simprops);
  }

  /**
   * Reads the properties into attributes.
   * 
   * @param simprops
   *          the list with properties.
   */
  private void init(Properties simprops) {
    turnWait = Integer.parseInt(simprops.getProperty("TURN_WAIT", NO_WAIT));
    gotojailWait = Integer.parseInt(simprops.getProperty("GOTOJAIL_WAIT", NO_WAIT));
    tinyWait = Integer.parseInt(simprops.getProperty("TINY_WAIT", TINY_WAIT));
  }

}
