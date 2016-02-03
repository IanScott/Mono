package monopoly.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.*;
import java.util.List;

import monopoly.game.*;
import monopoly.game.board.*;
import monopoly.game.player.Player;

/**
 * Graphical representation of the monopoly board. It reads the board
 * information from the game and creates a graphical board for it. It can handle
 * boards with different numbers of squares. Defines the colors of the players
 * and the groups.
 * 
 * @author Open Universiteit
 */
public final class MonopolyPanel extends JPanel {

  private static final long serialVersionUID = 315749940776071708L;
  private static final int PREFSIZE = 1000; // preferred size panel
  private static final int BORDERSIZE = 20; // border size
  private JPanel centerPanel = null;

  private static Map<Group, Color> colorMap = null; // mapping from group to
                                                    // color
  private static Color[] playerColors = { Color.RED, Color.GREEN, Color.CYAN,
      Color.MAGENTA, Color.YELLOW, Color.BLUE, Color.ORANGE, Color.PINK };
  private static Color[] groupColors = { new Color(0, 0, 128),
      new Color(160, 160, 255), new Color(128, 0, 255), new Color(255, 160, 0),
      new Color(255, 0, 0), new Color(255, 255, 0), new Color(0, 128, 0),
      new Color(0, 0, 255) };

  /**
   * Creates the graphical representation of the monopoly board.
   * 
   * @param mgame
   *          the monopoly game
   */
  public MonopolyPanel(MonopolyGame mgame) {
    super();
    initialize(mgame);
  }

  // Initializes the board (size, used colors, squares, player gui's)
  private void initialize(MonopolyGame mgame) {
    setPreferredSize(new Dimension(PREFSIZE, PREFSIZE));
    // create cells for locations
    initializeGroupColors(mgame);
    createBoard(mgame);
  }

  // Creates the squares for the board and arranges these on the board.
  // Creates the gui-panels for the players.
  private void createBoard(MonopolyGame mgame) {
    Board board = mgame.getBoard();
    int size = board.getNumberSquares();
    // find dimensions for south/north and west/east
    int cols = (size - 1) / 4 + 2;
    int rows = (size - 2 * cols + 1) / 2 + 2;
    setLayout(new PerimeterLayout(rows, cols, cols - 1, rows - 1));
    for (int i = 0; i < size; i++) {
      add(new SquarePanel(mgame, board.getSquare(i)));
    }

    // add center panel for players
    centerPanel = new JPanel();
    add(centerPanel, PerimeterLayout.CENTER);
    centerPanel.setLayout(new GridLayout(2, 0, BORDERSIZE, BORDERSIZE));
    centerPanel.setBorder(new EmptyBorder(BORDERSIZE, BORDERSIZE, BORDERSIZE,
        BORDERSIZE));
    List<Player> players = mgame.getPlayers();
    int index = 0;
    // add panel for each player
    for (Player p : players) {
      PlayerPanel playerPanel = new PlayerPanel(p, getPlayerColor(index++));
      centerPanel.add(playerPanel);
    }
    centerPanel.setBackground(Color.BLACK);
  }

  // Makes a mapping from group to color
  private void initializeGroupColors(MonopolyGame mgame) {
    colorMap = new HashMap<>();
    int cindex = 0;
    for (int i = 0; i < mgame.getBoard().getNumberSquares(); i++) {
      Square sq = mgame.getBoard().getSquare(i);
      if (sq instanceof LotSquare) {
        Group group = ((LotSquare) sq).getGroup();
        // already known?
        if (!colorMap.containsKey(group)) {
          colorMap.put(group, groupColors[(cindex++) % groupColors.length]);
        }
      }
    }
  }
  
  /**
   * Gets the color of a player.
   * 
   * @param index
   *          index of player
   * @return the color assigned to the player
   */
  public static Color getPlayerColor(int index) {
    return playerColors[index];
  }

  /**
   * Gets the color of a group.
   * 
   * @param group
   *          the group
   * @return the color for the given lot square
   */
  public static Color getGroupColor(Group group) {
    return colorMap.get(group);
  }

}
