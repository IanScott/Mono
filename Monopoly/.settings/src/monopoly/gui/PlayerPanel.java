package monopoly.gui;

import monopoly.game.board.*;
import monopoly.game.player.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Panel for player interaction. This panel shows all information of a player.
 * It contains of a list of its properties and widgets to interact with the
 * game.
 * 
 * @author Open Universiteit
 */
public class PlayerPanel extends JPanel implements Observer {

  private static final long serialVersionUID = -3080592767020994444L;
  private Player player = null;
  private Color color = null;
  private JLabel cashLabel = null;
  private JLabel locLabel = null;
  private JLabel diceLabel = null;
  private JTextArea messageArea = null;
  private JButton purchaseButton = null;
  private JButton endTurnButton = null;
  private DefaultListModel<PropertySquare> propModel = null;
  private JList<PropertySquare> propList = null;

  /**
   * Creates a player panel.
   * 
   * @param player
   *          the player
   * @param color
   *          the color assigned to the player
   */
  public PlayerPanel(Player player, Color color) {
    super();
    this.player = player;
    this.color = color;
    initialize();
  }

  // Initializes the panel for a player.
  private void initialize() {
    player.addObserver(this);
    setLayout(new BorderLayout());
    
    JPanel northPanel = makeNorthPanel();
    add(northPanel, BorderLayout.NORTH);
    
    JPanel centerPanel = makeCenterPanel();
    add(centerPanel, BorderLayout.CENTER);
    
    JPanel southPanel = makeSouthPanel();
    add(southPanel, BorderLayout.SOUTH);

    displayInfo();
  }
  

  private JPanel makeNorthPanel() {
    JPanel northPanel = new JPanel();
    northPanel.setLayout(new BorderLayout());
    
    JPanel gridPanel = new JPanel();
    northPanel.add(gridPanel, BorderLayout.NORTH);
    gridPanel.setLayout(new GridLayout(0, 1, 0, 3));
    
    JLabel header = new JLabel(player.getName());
    header.setFont(new Font("Dialog", Font.BOLD, 14));
    header.setOpaque(true);
    header.setBackground(color);
    Color fg = new Color(255 - color.getRed(), 255 - color.getGreen(),
        255 - color.getBlue());
    header.setForeground(fg);
    gridPanel.add(header);
    
    cashLabel = new JLabel();
    gridPanel.add(cashLabel);

    locLabel = new JLabel();
    gridPanel.add(locLabel);

    diceLabel = new JLabel();
    gridPanel.add(diceLabel);
   
    messageArea = new JTextArea();
    messageArea.setForeground(Color.RED);
    messageArea.setOpaque(false);
    messageArea.setLineWrap(true);
    messageArea.setWrapStyleWord(true);
    messageArea.setEditable(false);
    northPanel.add(messageArea, BorderLayout.CENTER);
    
    return northPanel;
  }
  
  private JPanel makeCenterPanel() {
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BorderLayout());
    propModel = new DefaultListModel<PropertySquare>();
    propList = new JList<PropertySquare>(propModel);
    propList.setFont(new Font(Font.MONOSPACED, Font.BOLD, 11));
    centerPanel.add(new JScrollPane(propList), BorderLayout.CENTER);
    return centerPanel;
  }
  
  private JPanel makeSouthPanel() {
    JPanel southPanel = new JPanel();

    if (player instanceof HumanPlayer) {
      purchaseButton = new JButton("Koop");
      purchaseButton.setEnabled(false);
      purchaseButton.addActionListener(new PurchaseButtonListener());
      southPanel.add(purchaseButton);

      endTurnButton = new JButton("Klaar");
      endTurnButton.setEnabled(false);
      endTurnButton.addActionListener(new EndTurnButtonListener());
      southPanel.add(endTurnButton);
      propList.addMouseListener(new PropertyListener());
    }

    return southPanel;
  }


  
  // Displays the most current information of the player.
  private void displayInfo() {
    messageArea.setText("");
    locLabel.setText("");
    diceLabel.setText("");
    propModel.removeAllElements();
    if (player.isBankrupt()) {
      cashLabel.setText("BANKROET");
      propList.setEnabled(false);
    } else {
      cashLabel.setText("Cash: " + player.getCash());
      locLabel.setText("Location: " + player.getLocation().getName());
      if (player.hasTurn()) {
        String same = "";
        if (player.getCup().equalValues()) {
          same = " (double)";
        }
        diceLabel.setText("Dobbelstenen: " + player.getCup().getTotal() + same);
      }
      // refill property list
      for (PropertySquare psq : player.getProperties()) {
        propModel.addElement(psq);
      }
    }
  }

  @Override
  public void update(Observable o, Object arg) {
    // repaint player gui for current situation. Enable/disable widgets.
    final Object interaction = arg;
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        if (interaction == HumanPlayer.JAIL_INTERACTION) {
          showJailOptions();
        } else if (interaction == HumanPlayer.CARD_INTERACTION){
        	showCardDescription();
        } else if (interaction == HumanPlayer.CARD_CHOICE_INTERACTION){
        	showChoiceCard();
        } else {
        	if(player instanceof HumanPlayer) {
        		purchaseButton.setEnabled(false);
        		}
          displayInfo();
          if (!player.isBankrupt() && player instanceof HumanPlayer) {
            // notification to enable/disable human action
            endTurnButton.setEnabled(player.hasTurn());
            if (player.getLocation().isProperty()) {
              purchaseButton.setEnabled(player.hasTurn()
                  && player.canBuyProperty((PropertySquare) (player.getLocation())));
            }
          }
          if (interaction == HumanPlayer.SELL_MESSAGE) {
            messageArea.setText("Verkoop bezittingen");
            endTurnButton.setEnabled(false);
          }
          repaint();
        }
      }
    });
  }

//Shows the buttons for the options of the FineOrChance Card.
  private int showChoiceCard() {
	  String title = player.getActiveCard().getDeck().getName();
	  String message = player.getActiveCard().getDescription();
	  Object[] options = { "Betaal", "Kies kanskaart" };
	  int n = -1;
	  while (n == -1) {
	      n = JOptionPane.showOptionDialog(null, message, title,
	          JOptionPane.YES_NO_CANCEL_OPTION,
	          JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	  }
	  ((HumanPlayer) player).setAnswer(n);
	  return n;
}

/**
   * Optionpane to show pulled card.
   */
  private void showCardDescription() {
	  String title = player.getActiveCard().getDeck().getName();
	  JOptionPane.showMessageDialog(null, player.getActiveCard().getDescription(), title, JOptionPane.PLAIN_MESSAGE);
	    
	   ((HumanPlayer) player).setAnswer(1);	
}

// Shows the buttons for the options to leave jail.
  private int showJailOptions() {
	  Object[] options = { "Betaal boete", "Dobbel" };
	if (player.hasLeaveJailCard()){
		options = new Object[]{ "Betaal boete", "Dobbel","Verlaat de gevangenis kaart" };
	} 
    int n = -1;
    while (n == -1) {
      n = JOptionPane.showOptionDialog(null, "Verlaat de gevangenis",
          player.getName(), JOptionPane.YES_NO_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }
    ((HumanPlayer) player).setAnswer(n);
    return n;
  }

  // Listener class for buying properties.
  private class PurchaseButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      // player buys property
      player.buyProperty((PropertySquare) (player.getLocation()));
      purchaseButton.setEnabled(false);
    }
  }

  // Listener class for "end turn" button
  private class EndTurnButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      // player ends turn
      player.endTurn();
    }
  }

  // Listener class for operations on properties.
  // Can be extended for buying houses/hotels.
  private class PropertyListener extends MouseAdapter {
    private static final String SETMORTGAGE = "Hypotheek";
    private static final String RESETMORTGAGE = "Betaal hypotheek";
    private static final String BUYBUILDING = "Koop gebouw";
    private static final String SELLBUILDING = "Verkoop gebouw";
    private static final String CANCEL = "Annuleer";

    @Override
    public void mousePressed(MouseEvent e) {
    	// set transactionflag to avoid that other players continue to throw
    	player.getmGame().setTransactionFlag(true);
      int index = propList.locationToIndex(e.getPoint());
      if (index != -1 && propList.getCellBounds(index, index).contains(e.getPoint())) {
        PropertySquare psq = propList.getSelectedValue();
        String action = showPropertyActions(psq);
        if (SETMORTGAGE.equals(action)) {
          player.setMortgage(psq);
        } else if (RESETMORTGAGE.equals(action)) {
          player.resetMortgage(psq);
        } else if (BUYBUILDING.equals(action)) {
            player.buyBuilding((LotSquare)psq);
          } else if (SELLBUILDING.equals(action)) {
              player.sellBuilding((LotSquare)psq);
          } 
      }
      // reset transaction flag
      player.getmGame().setTransactionFlag(false);
    }

    // enable the buttons for the allowed operations
    private String showPropertyActions(PropertySquare psq) {
      ArrayList<String> options = new ArrayList<String>();
      if (player.canSetMortgage(psq)) {
        options.add(SETMORTGAGE);
      }
      if (player.canResetMortgage(psq)) {
        options.add(RESETMORTGAGE);
      }
      if (psq instanceof LotSquare && player.canBuyBuilding((LotSquare)psq)) {
          options.add(BUYBUILDING);
        }
      if (psq instanceof LotSquare && player.canSellBuilding((LotSquare)psq)) {
          options.add(SELLBUILDING);
        }
      options.add(CANCEL);
      int n = -1;
      while (n == -1) {
        n = JOptionPane.showOptionDialog(null, "Actie voor " + psq.getName(),
            player.getName(), JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options.toArray(), "");
      }
      return options.get(n);
    }
  }
}
