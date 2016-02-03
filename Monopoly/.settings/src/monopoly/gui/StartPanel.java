package monopoly.gui;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;
import java.io.File;
import javax.swing.JTextArea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import monopoly.MonopolyException;
import monopoly.game.MonopolyGame;


/**
 * This class is used to setup the Monopoly game
 * @author Ian van Nieuwkoop
 *
 */
public class StartPanel extends JPanel{
	private static final long serialVersionUID = 315749940776071708L;
	private MonopolyGame mGame = null; 				//	Monopoly Game
	private StartDialog startDialog = null;
	private JPanel panel = null; 					// 	Main Panel
	private JPanel configBoard = null;				// 	Sub Panel to group buttons
	private File xml = null;						//	Selected Board xml file
	/** logger for logging. */
	protected static final Logger LOGGER = LogManager.getLogger(StartPanel.class.getName());
	
	//TextFields
	private JTextField player_1 = null;
	private JTextField player_2 = null;
	private JTextField player_3 = null;
	private JTextField player_4 = null;
	private JTextField player_5 = null;
	private JTextField player_6 = null;
	private JTextField player_7 = null;
	private JTextField player_8 = null;
	
	//Checkboxes
	private JCheckBox checkBox_1 = null;
	private JCheckBox checkBox_2 = null;
	private JCheckBox checkBox_3 = null;
	private JCheckBox checkBox_4 = null;
	private JCheckBox checkBox_5 = null;
	private JCheckBox checkBox_6 = null;
	private JCheckBox checkBox_7 = null;
	private JCheckBox checkBox_8 = null;
	
	private JTextArea infoArea = null; 	//InfoArea
	private JLabel selectedFile = null; //Selected File
	/**
	   * Constructor.
	   * 
	   * @param mGame
	   *          the monopoly model
	   * @param startDialog the setup dialog         
	   */
	  public StartPanel(MonopolyGame mGame, StartDialog startDialog) {
	    super();
	    this.mGame = mGame;
	    this.startDialog = startDialog;
	    this.setLayout(null);
	    panel = new JPanel();
	    configBoard = new JPanel();
	    init();    
	  }
	  
	  private void init(){
		setPanel();
		setButtons();
		setLabels();
		setTextFields();
		setCheckboxes();
	  }
	  /**
	   * Method returns the selected Board XML File.
	   * @return the XML File containing a board to load.
	   */
	  public File getBoard(){
		  return xml;
	  }
	  /**
	   * Method returns Player name.
	   * @return list of player names
	   */
	  public List<String> getPlayers(){
		  ArrayList<String> players = new ArrayList<String>();
		  
		  players.add(player_1.getText());
		  players.add(player_2.getText());
		  players.add(player_3.getText());
		  players.add(player_4.getText());
		  players.add(player_5.getText());
		  players.add(player_6.getText());
		  players.add(player_7.getText());
		  players.add(player_8.getText());
		  
		  return players;
	  }
	  /**
	   * Method return a list of booleans whether a player is a computer or not.
	   * @return a list of booleans
	   */
	  public List<Boolean> getComputerPlayers(){
		  ArrayList<Boolean> cpus = new ArrayList<Boolean>();
		  cpus.add(checkBox_1.isSelected());
		  cpus.add(checkBox_2.isSelected());
		  cpus.add(checkBox_3.isSelected());
		  cpus.add(checkBox_4.isSelected());
		  cpus.add(checkBox_5.isSelected());
		  cpus.add(checkBox_6.isSelected());
		  cpus.add(checkBox_7.isSelected());
		  cpus.add(checkBox_8.isSelected());
		  return cpus;
	  }
	  private void setLabels(){
		  JLabel label = new JLabel("Player 1");
		    label.setBounds(20, 60, 150, 20);
		    panel.add(label);
		    
		    JLabel label_1 = new JLabel("Player 2");
		    label_1.setBounds(20, 100, 150, 20);
		    panel.add(label_1);
		    
		    JLabel label_2 = new JLabel("Player 3");
		    label_2.setBounds(20, 140, 150, 20);
		    panel.add(label_2);
		    
		    JLabel label_3 = new JLabel("Player 4");
		    label_3.setBounds(20, 180, 150, 20);
		    panel.add(label_3);
		    
		    JLabel label_4 = new JLabel("Player 5");
		    label_4.setBounds(20, 220, 150, 20);
		    panel.add(label_4);
		    
		    JLabel label_5 = new JLabel("Player 6");
		    label_5.setBounds(20, 260, 150, 20);
		    panel.add(label_5);
		    
		    JLabel label_6 = new JLabel("Player 7");
		    label_6.setBounds(20, 300, 150, 20);
		    panel.add(label_6);
		    
		    JLabel label_7 = new JLabel("Player 8");
		    label_7.setBounds(20, 340, 150, 20);
		    panel.add(label_7);
		    
		    JLabel playerNames = new JLabel("Player Names");
		    playerNames.setBounds(100, 35, 113, 14);
		    panel.add(playerNames);
		    
		    JLabel computer = new JLabel("Computer?");
		    computer.setBounds(345, 35, 75, 14);
		    panel.add(computer);
		    
		    JLabel minplayers = new JLabel("Stel minimaal twee spelers in.");
		    minplayers.setBounds(20, 388, 367, 14);
		    panel.add(minplayers);
		    
		    selectedFile = new JLabel("Default is het Standaard Nederlandse Bord");
		    selectedFile.setOpaque(true);
		    selectedFile.setBackground(Color.WHITE); 
		    selectedFile.setBounds(10, 72, 445, 31);
		    selectedFile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		    configBoard.add(selectedFile);
	  }
	  
	  private void setCheckboxes(){
		  	checkBox_1 = new JCheckBox();
		    checkBox_1.setBounds(366, 60, 21, 23);
		    panel.add(checkBox_1);
		    
		    checkBox_2 = new JCheckBox();
		    checkBox_2.setBounds(366, 99, 21, 23);
		    panel.add(checkBox_2);
		    
		    checkBox_3 = new JCheckBox();
		    checkBox_3.setBounds(366, 139, 21, 23);
		    panel.add(checkBox_3);
		    
		    checkBox_4 = new JCheckBox();
		    checkBox_4.setBounds(366, 179, 21, 23);
		    panel.add(checkBox_4);
		    
		    checkBox_5 = new JCheckBox();
		    checkBox_5.setBounds(366, 219, 21, 23);
		    panel.add(checkBox_5);
		    
		    checkBox_6 = new JCheckBox();
		    checkBox_6.setBounds(366, 259, 21, 23);
		    panel.add(checkBox_6);
		    
		    checkBox_7 = new JCheckBox();
		    checkBox_7.setBounds(366, 299, 21, 23);
		    panel.add(checkBox_7);
		    
		    checkBox_8 = new JCheckBox();
		    checkBox_8.setBounds(366, 339, 21, 23);
		    panel.add(checkBox_8);
	  }
	  
	  private void setTextFields(){
		  player_1 = new JTextField();
		    player_1.setBounds(100, 60, 222, 20);
		    panel.add(player_1);
		    
		    player_2 = new JTextField();
		    player_2.setBounds(100, 100, 222, 20);
		    panel.add(player_2);
		    
		    player_3 = new JTextField();
		    player_3.setBounds(100, 140, 222, 20);
		    panel.add(player_3);
		    
		    player_4 = new JTextField();
		    player_4.setBounds(100, 180, 222, 20);
		    panel.add(player_4);
		    
		    player_5 = new JTextField();
		    player_5.setBounds(100, 220, 222, 20);
		    panel.add(player_5);
		    
		    player_6 = new JTextField();
		    player_6.setBounds(100, 260, 222, 20);
		    panel.add(player_6);
		    
		    player_7 = new JTextField();
		    player_7.setBounds(100, 300, 222, 20);
		    panel.add(player_7);
		    
		    player_8 = new JTextField();
		    player_8.setBounds(100, 340, 222, 20);
		    panel.add(player_8);
		    
		    infoArea = new JTextArea();
		    infoArea.setBounds(482, 158, 238, 292);
		    infoArea.setLineWrap(true);
		    infoArea.setForeground(Color.RED);
		    infoArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		    infoArea.setEditable(false);
		    add(infoArea);
	  }
	  
	  private void setPanel(){
		    panel.setBorder(new TitledBorder(null, "Selecteer Spelers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		    panel.setBounds(26, 37, 430, 413);
		    panel.setBackground(Color.LIGHT_GRAY);
		    add(panel);
		    panel.setLayout(null);
		    
		    configBoard.setBounds(26, 461, 694, 113);
		    configBoard.setBackground(Color.LIGHT_GRAY);
		    configBoard.setBorder(new TitledBorder(null, "Configureer Bord", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		    add(configBoard);
		    configBoard.setLayout(null);
	  }
	  
	  private void setButtons(){
		  JButton startbutton = new JButton("Start Game");
		    startbutton.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent arg0) {
		    		try{
		    			loadGame();
		    			startDialog.setVisible(false);
		    		} catch(MonopolyException e){
		    			infoArea.removeAll();
		    			infoArea.setText(e.getMessage());
		    		} catch (Exception e){
		    			LOGGER.error(e.toString());
		    		}
		    	}
		    });
		    startbutton.setBounds(515, 47, 177, 92);
		    this.add(startbutton);
		    
		    JButton loadbutton = new JButton("Laad Bord Bestand");
		    loadbutton.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent arg0) {
		    		JFileChooser fc=new JFileChooser();
		    		  int returnVal=fc.showOpenDialog(new JFrame());
		    		  if (returnVal == JFileChooser.APPROVE_OPTION) {
		    		    selectedFile.setText(fc.getSelectedFile().getAbsolutePath());
		    		    xml= fc.getSelectedFile();
		    		  }
		    	}
		    });
		    loadbutton.setBounds(495, 72, 177, 31);
		    configBoard.add(loadbutton);
		    
		    JButton dfltbutton = new JButton("Nederlands Bord");
		    dfltbutton.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent arg0) {
		    		xml=null;
		    		selectedFile.setText("Default Bord");
		    	}
		    });
		    dfltbutton.setBounds(495, 21, 177, 31);
		    configBoard.add(dfltbutton);
	  }
	  
	  public void loadGame() throws MonopolyException {
			 int count = 0;
			 ArrayList<String> plys = (ArrayList<String>) getPlayers();
			 ArrayList<Boolean> cpus =  (ArrayList<Boolean>) getComputerPlayers();
			 mGame.initialize(getBoard());
			
			 
			 for( int i = 0; i<plys.size(); i++){
				 if(!plys.get(i).isEmpty()){
				 mGame.addPlayer(plys.get(i), cpus.get(i));
				 count++;}
			 }
			 if(count==0){
				 mGame.addPlayer("Ian", false);
				 mGame.addPlayer("Ton", false);
			 }
			 if(count==1){
				 mGame.addPlayer("Ton", false);
			 }
			 
	  }
}
