package monopoly.gui;

import javax.swing.*;
import monopoly.game.MonopolyGame;

/**
 * Main window for monopoly user interface.
 * The main window contains only one panel with the game board.
 * 
 * @author Open Universiteit
 * And edited by Ian van Nieuwkoop
 *
 */
public final class MonopolyWindow extends JFrame {
	  private static final long serialVersionUID = -2949747890724398414L;
	  private MonopolyPanel mPanel = null;

	  /**
	   * Creates the frame for the monopoly window.
	   * @param mGame  the game to be show in the window
	   */
	  public MonopolyWindow(MonopolyGame mGame) {
	    super();
	    initialize(mGame);
	    setVisible(true);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	  }
  
	  // initializes the window
	  private void initialize(MonopolyGame mGame) {
	    setTitle("mOnUpoly");
	    
	    new StartDialog(this, "load settings", mGame);
	  
		 mPanel = new MonopolyPanel(mGame);
		   
		 setContentPane(mPanel);
		 pack();

	  }    

}
