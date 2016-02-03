package monopoly;

import monopoly.game.*;
import monopoly.gui.*;

/**
 * Start of monopoly game. The main method creates the game (model) and the user
 * interface (view) for a game of monopoly, and starts playing the game.
 * 
 * @author Open Universiteit
 */
public final class PlayMonopoly {
	
	
	/**
	 * Sets a game of monopoly up.
	 * 
	 * @param args
	 *          not used
	 */
	public static void main(String[] args) {
		  
		try {  
		  MonopolyGame model = new MonopolyGame();
		  new MonopolyWindow(model);
		  model.playGame();	
		} catch (MonopolyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Private Constructor
	 * @return 
	 */
	private PlayMonopoly(){
		  
	}

}
