package monopoly.gui;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import monopoly.game.*;
import monopoly.game.board.*;
import monopoly.game.player.Player;

/**
 * Panel for a square on the board. It uses the Observer-Observable pattern to
 * show the changes during the game.
 * 
 * @author Open Universiteit
 */
public class SquarePanel extends JPanel implements Observer {

  private static final long serialVersionUID = 2102123616608506581L;
  private MonopolyGame mgame;
  private Square square;
  private JPanel cgPanel = null; // color group panel
  private JTextArea infoArea = null; // text information
  private JPanel playersPanel = null; // the panel to show the players
  private JPanel[] players = null;
  private JPanel[] buildings = null;
  private static final Font FONT = new Font("SansSerif", Font.PLAIN, 9);
  private static final LineBorder JAILBORDER = new LineBorder(Color.BLACK, 2);
  private static final LineBorder HOUSEBORDER = new LineBorder(Color.BLACK, 1);
  private static final Border TOPBOTTOMBORDER = BorderFactory.createMatteBorder(2,0,2,0, Color.BLACK);
  private static final Border LEFTBORDER = BorderFactory.createMatteBorder(2,2,2,0, Color.BLACK);
  private static final Border RIGHTBORDER = BorderFactory.createMatteBorder(2,0,2,2, Color.BLACK);
  
  /**
   * Creates a panel for a square on the board.
   * 
   * @param mgame
   *          the game that is played
   * @param square
   *          the square to be shown
   */
  public SquarePanel(MonopolyGame mgame, Square square) {
    super();
    this.mgame = mgame;
    this.square = square;
    square.addObserver(this);

    setBorder(new BevelBorder(BevelBorder.LOWERED));
    setLayout(new BorderLayout());
    //Set Tooltip
    setTooltip();
    // NORTH: color group (empty if not needed)
    cgPanel = new JPanel();
    //cgPanel.setPreferredSize(new Dimension(10, 10));
    add(cgPanel, BorderLayout.NORTH);
    cgPanel.setLayout(new GridLayout(1, 1));

    if (square instanceof LotSquare) {
      cgPanel.setBackground(MonopolyPanel.getGroupColor(((LotSquare) square).getGroup()));
    }
    
    //Buildings
    buildings = new JPanel[5];
    for (int i = 0; i < 4; i++) {
      buildings[i] = new JPanel();
      buildings[i].setBackground(Color.GREEN);
      buildings[i].setPreferredSize(new Dimension(10, 15));
      buildings[i].setOpaque(false);
      cgPanel.add(buildings[i]);
    }
    	buildings[4] = new JPanel();
    	buildings[4].setBackground(Color.RED);
        buildings[4].setPreferredSize(new Dimension(50, 10));
        buildings[4].setOpaque(false);
        cgPanel.add(buildings[4]);
    	
    	displayBuildings();
    // CENTER: info
    infoArea = new JTextArea();
    infoArea.setFont(FONT);
    add(infoArea, BorderLayout.CENTER);
    infoArea.setEditable(false);
    displayInfo();
    infoArea.setToolTipText(tooltipText());
    
    // SOUTH: players
    playersPanel = new JPanel();
    playersPanel.setPreferredSize(new Dimension(10, 20));
    add(playersPanel, BorderLayout.SOUTH);
    playersPanel.setLayout(new GridLayout(0, 8, 2, 2));
    int numberPlayers = mgame.getNumberPlayers();
    players = new JPanel[numberPlayers];
    for (int i = 0; i < numberPlayers; i++) {
      players[i] = new JPanel();
      players[i].setBackground(MonopolyPanel.getPlayerColor(i));
      players[i].setPreferredSize(new Dimension(10, 20));
      players[i].setOpaque(false);
      playersPanel.add(players[i]);
    }
    displayPlayers();

  }

  @Override
  public void update(Observable o, Object arg) {
    // repaint the square with most current information of square.
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
    	updateToolTips();
    	updateAllToolTips();
        displayInfo();
        displayPlayers();
        displayBuildings();
        repaint();
      }
    });
  }
  private void updateAllToolTips(){
	  if(square instanceof PropertySquare){
		  
    	  for(PropertySquare ps: ((PropertySquare)square).getGroup().getProperties()){
    		  ps.checkGroup(ps.getOwner());
    	  }
	  }  
  }
  private void updateToolTips(){
	setToolTipText(tooltipText());
  	infoArea.setToolTipText(tooltipText());
  }
  // Displays the most current information of the square.
  private void displayInfo() {
    infoArea.setText(square.getInfo());
  }
  
  //display buildings
  private void displayBuildings(){
		  if( square instanceof LotSquare){
			  resetStreet();
			  int b = ((LotSquare) square).getBuildings();
			  switch(b){
			  case 0: 
				  		break;
			  case 4: 	buildings[3].setOpaque(true);
			  			buildings[3].setBorder(HOUSEBORDER);
			  case 3: 	buildings[2].setOpaque(true);
			  			buildings[2].setBorder(HOUSEBORDER);
			  case 2: 	buildings[1].setOpaque(true);
			  			buildings[1].setBorder(HOUSEBORDER);
			  case 1: 	buildings[0].setOpaque(true);
			  			buildings[0].setBorder(HOUSEBORDER);
			  			break;
			  case 5: 	buildings[3].setOpaque(true);
			  			buildings[3].setBackground(Color.RED);
			  			buildings[3].setBorder(RIGHTBORDER);
			  			buildings[2].setOpaque(true);
			  			buildings[2].setBackground(Color.RED);
			  			buildings[2].setBorder(TOPBOTTOMBORDER);
			  			buildings[1].setOpaque(true);
			  			buildings[1].setBackground(Color.RED);
			  			buildings[1].setBorder(LEFTBORDER);
			  default: 
			  		break;
			  }
		  }
		
  }
  
  private void resetStreet(){
	  for(int i =0; i<4; i++){
		  buildings[i].setOpaque(false);
		  buildings[i].setBackground(Color.GREEN);
		  buildings[i].setBorder(null);
		  }
  }
  // Shows all players on this square.
  private void displayPlayers() {
    // show players on square
    int index = 0;
    for (Player p : mgame.getPlayers()) {
      if (p.getLocation() == square) {
        players[index].setOpaque(true);
        if (p.isInJail()) {
          players[index].setBorder(JAILBORDER);
        } else {
          players[index].setBorder(null);
        }
        index++;
      } else {
        players[index].setOpaque(false);
        players[index].setBorder(null);
        index++;
      }
    }
  }
  private String tooltipText(){
	  
	  if(square instanceof LotSquare){
		  return "House Price: "+((LotSquare)square).housePrice()+
				  "  Rent: "+((LotSquare)square).getRent(1);
		  }
	  else if(square instanceof UtilitySquare){
		  return "Rent (x dice): "+((UtilitySquare)square).getRent(1);
		  }
	  else if(square instanceof RailroadSquare){
		  return "Rent: "+((RailroadSquare)square).getRent(1);
		  }
	  else if(square instanceof PropertySquare){
		  return " Rent: "+((PropertySquare)square).getRent(1);
		  }
	  
	  else {
		  return square.getName();
	  }
				  
  }
  private void setTooltip(){  
	  this.setToolTipText(tooltipText());
  }
}
