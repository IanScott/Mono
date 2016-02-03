package monopoly.gui;

import javax.swing.JDialog;
import javax.swing.JFrame;


import monopoly.game.MonopolyGame;

/**
 * Create a modal dialog showing the start screen wher the user can enter the players and board.
 * @author tony
 *
 */
public class StartDialog extends JDialog {
	
	private static final long serialVersionUID = 8198157183132459834L;
	private static final int DIALOG_SIZE = 760;

	/**
	 * Shows a modal dialog showing the start screen wher the user can enter the players and board.
	 * @param mf Parent Frame
	 * @param title Title of the board
	 * @param mGame The monopoly game object
	 */
    public StartDialog(JFrame mf,String title,MonopolyGame mGame){
        super(mf,title,true);
        this.setSize(DIALOG_SIZE,DIALOG_SIZE);
        StartPanel startPanel=new StartPanel(mGame, this);   
        this.getContentPane().add(startPanel);
        this.setVisible(true);
    }

}
