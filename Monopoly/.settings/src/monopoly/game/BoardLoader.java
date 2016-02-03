package monopoly.game;

import monopoly.MonopolyException;
import monopoly.game.board.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * This class is responsible for the creation of the board.
 */
public class BoardLoader {
	
	/**
	 * private constructor to prevent instantiation.
	 */
	private BoardLoader(){	
	}
	
	
	/**
	   * Loads the default Dutch Monopoly board.
	   * 
	   * @throws MonopolyException
	   *           when error occurs in defining the board
	   * @return a board object
	   */
	  public static Board loadBoard() throws MonopolyException {
		 ArrayList<Square> squares = new ArrayList<Square>(); 
		 Group[] groups = { new Group("ONS DORP"), new Group("ARNHEM"),
			      new Group("HAARLEM"), new Group("UTRECHT"), new Group("GRONINGEN"),
			      new Group("'S GRAVENHAGE"), new Group("ROTTERDAM"),
			      new Group("AMSTERDAM") };

			  int[][] rents = { { 2, 10, 30, 90, 160, 250 }, // ons dorp
			      { 4, 20, 60, 180, 320, 450 }, // brink
			      { 6, 30, 90, 270, 400, 550 }, // steenstraat + ketelstraat
			      { 8, 40, 100, 300, 450, 600 }, // velperplein
			      { 10, 50, 150, 450, 625, 750 }, // barteljorisstraat + zijlweg
			      { 12, 60, 180, 500, 700, 900 }, // houtstraat
			      { 14, 70, 200, 550, 750, 950 }, // neude + biltstraat
			      { 16, 80, 220, 600, 800, 1000 }, // vreeburg
			      { 18, 90, 250, 700, 875, 1050 }, // a-kerkhof + groote markt
			      { 20, 100, 300, 750, 925, 1100 }, // heerestraat
			      { 22, 110, 330, 800, 975, 1150 }, // spui + plein
			      { 24, 120, 360, 850, 1025, 1200 }, // lange pooten
			      { 26, 130, 390, 900, 1100, 1275 }, // hofplein + blaak
			      { 28, 150, 450, 1000, 1200, 1400 }, // coolsingel
			      { 35, 175, 500, 1100, 1300, 1500 }, // leidschestraat
			      { 50, 200, 600, 1400, 1700, 2000 } // kalverstraat
			  };
		 
		 
		 
	    squares.add(new RegularSquare("START"));
	    squares.add(new LotSquare("DORPSTRAAT", groups[0], 60, 50, rents[0]));
	    squares.add(new ChanceSquare("ALGEMEEN FONDS", 1));
	    squares.add(new LotSquare("BRINK", groups[0], 60, 50, rents[1]));
	    squares.add(new IncomeTaxSquare("INKOMSTENBELASTING", 10, 200));
	    squares.add(new RailroadSquare("STATION ZUID", 200, 25));
	    squares.add(new LotSquare("STEENSTRAAT", groups[1], 100, 50, rents[2]));
	    squares.add(new ChanceSquare("KANS", 0));
	    squares.add(new LotSquare("KETELSTRAAT", groups[1], 100, 50, rents[2]));
	    squares.add(new LotSquare("VELPERPLEIN", groups[1], 120, 50, rents[3]));
	    squares.add(new JailSquare("GEVANGENIS", 50));
	    squares.add(new LotSquare("BARTELJORISSTRAAT", groups[2], 140, 100,
	        rents[4]));
	    squares.add(new UtilitySquare("ELECTRICITEITSBEDRIJF", 150, 4, 10));
	    squares.add(new LotSquare("ZIJLWEG", groups[2], 140, 100, rents[4]));
	    squares.add(new LotSquare("HOUTSTRAAT", groups[2], 160, 100, rents[5]));
	    squares.add(new RailroadSquare("STATION WEST", 200, 25));
	    squares.add(new LotSquare("NEUDE", groups[3], 180, 100, rents[6]));
	    squares.add(new ChanceSquare("ALGEMEEN FONDS", 1));
	    squares.add(new LotSquare("BILTSTRAAT", groups[3], 180, 100, rents[6]));
	    squares.add(new LotSquare("VREEBURG", groups[3], 200, 100, rents[7]));
	    squares.add(new RegularSquare("VRIJ PARKEREN"));
	    squares.add(new LotSquare("A-KERKHOF", groups[4], 220, 150, rents[8]));
	    squares.add(new ChanceSquare("KANS", 1));
	    squares.add(new LotSquare("GROOTE MARKT", groups[4], 220, 150, rents[8]));
	    squares.add(new LotSquare("HEERESTRAAT", groups[4], 240, 150, rents[9]));
	    squares.add(new RailroadSquare("STATION NOORD", 200, 25));
	    squares.add(new LotSquare("SPUI", groups[5], 260, 150, rents[10]));
	    squares.add(new LotSquare("PLEIN", groups[5], 260, 150, rents[10]));
	    squares.add(new UtilitySquare("WATERLEIDINGBEDRIJF", 150, 4, 10));
	    squares.add(new LotSquare("LANGE POOTEN", groups[5], 280, 150, rents[11]));
	    squares.add(new GoToJailSquare("NAAR DE GEVANGENIS"));
	    squares.add(new LotSquare("HOFPLEIN", groups[6], 300, 200, rents[12]));
	    squares.add(new LotSquare("BLAAK", groups[6], 300, 200, rents[12]));
	    squares.add(new ChanceSquare("ALGEMENE FONDS", 1));
	    squares.add(new LotSquare("COOLSINGEL", groups[6], 320, 200, rents[13]));
	    squares.add(new RailroadSquare("STATION OOST", 200, 25));
	    squares.add(new ChanceSquare("KANS", 0));
	    squares
	        .add(new LotSquare("LEIDSCHESTRAAT", groups[7], 350, 200, rents[14]));
	    squares.add(new ExtraTaxSquare("EXTRA BELASTING", 100));
	    squares.add(new LotSquare("KALVERSTRAAT", groups[7], 400, 200, rents[15]));
	    
	    
	    return new Board(squares);
	  }
	  
	  /**
	   * Method creates a board based on a xml File.
	   * @param xmlfile the file to load.
	   * @return a board object
	   * @throws Method throws a MonopolyException
	   */
	  public static Board loadXml(File xmlfile) throws MonopolyException{
		//get the factory
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			ArrayList<Group> groups = new ArrayList<Group>();
			int[][] rents;
			ArrayList<Square> squares = new ArrayList<Square>();
			
			squares.add(new RegularSquare("START"));
			
			Document dom = null;
			try {

				//Using factory get an instance of document builder
				DocumentBuilder db = dbf.newDocumentBuilder();

				//parse using builder to get DOM representation of the XML file
				dom = db.parse(xmlfile);


			}catch(ParserConfigurationException pce) {
				
				throw new MonopolyException("XML Error:"+pce.getMessage());
			}catch(SAXException se) {
				
				throw new MonopolyException("XML Error:"+se.getMessage());
			}catch(IOException ioe) {
				
				throw new MonopolyException("XML Error:"+ioe.getMessage());
			}
			
			//get the root element
			Element docEle = dom.getDocumentElement();

			//add groups to list
			NodeList nl = docEle.getElementsByTagName("Group");
			if(nl != null && nl.getLength() > 0) {
				for(int i = 0 ; i < nl.getLength();i++) {

					//get the group element
					Element el = (Element)nl.item(i);
					//get the group object
					Group e = getGroup(el);
					//add it to list
					groups.add(e);
				}
			}
			//add rents to list
			NodeList nlrent = docEle.getElementsByTagName("Rent");
			rents = new int[nlrent.getLength()][];
			if(nlrent != null && nlrent.getLength() > 0) {
				for(int i = 0 ; i < nlrent.getLength()&&(nlrent.item(i) != null);i++) {

					//get the rent element
					Element el = (Element)nlrent.item(i);
					//get the rent object
					int[] e = getRent(el);
					
					//add it to list
					if(e != null){
					rents[i]=e;}
				}
			}
			//add squares to list
			NodeList nlsq = docEle.getElementsByTagName("Square");
			//check that board has a minimal length of 8
			if(nlsq.getLength()<7){
				throw new MonopolyException("Het speelbord moet minimaal 8 velden bevatten");
			}
			if(nlsq != null && nlsq.getLength() > 0) {
				for(int i = 0 ; i < nlsq.getLength();i++) {

					//get the square element
					Element el = (Element)nlsq.item(i);
					//get the square object
					Square e = getSquare(el, groups, rents);
					//add it to list
					squares.add(e);
				}
			}
			
			Board board = null;
			
			//check that the board has at least one go to Jail Square and a exactly one Jail Square
			checkJails(squares);
			board = new Board(squares);
			
			return board;
	  }
	  
	  private static void checkJails(ArrayList<Square> squares) throws MonopolyException{
		  int jails = 0;
			int gtjs = 0;
			for(Square sq: squares){
				if(sq instanceof JailSquare){
					jails++;
				}
				if(sq instanceof GoToJailSquare){
					gtjs++;
				}
			}
			
			if(jails!=1){
				throw new MonopolyException("Er moet precies 1 Gevangenis zijn");
			}
			if(gtjs < 1){
				throw new MonopolyException("Er moet minsten 1 Gaan naar Gevangenis veld zijn");
			}
	  }

	  private static Group getGroup(Element el){
		  String groupName = el.getFirstChild().toString();
		  return new Group(groupName);
	  }
	  
	  private static int[] getRent(Element el) throws MonopolyException{
		  ArrayList<Integer> list = new ArrayList<Integer>();
		  String s = getTextValue(el, "List");
		  int[] intarray = null;
		  if(s != null){
			  String[] s2 = s.split(",");
				  for(String s3: s2) {
					  list.add(Integer.parseInt(s3.trim()));
				  } 
		  	  }
		 
			  intarray = new int[list.size()];
		 
			  for(int i=0; i<list.size(); i++){
				  intarray[i] = list.get(i);
			  }
			  return intarray;
		  }
		 	
		  
	  private static Square getSquare(Element el, ArrayList<Group> groups, int[][] rents) throws MonopolyException{
		  Square sq = null;
		  String type = getTextValue(el, "Type");
		  
		  switch(type) {
		  	case "ExtraTaxSquare":	sq = new ExtraTaxSquare(getTextValue(el, "Name"),getIntValue(el, "Tax"));
		  		break;
		  	case "GoToJailSquare": 	sq = new GoToJailSquare(getTextValue(el, "Name"));
		  		break;
		  	case "IncomeTaxSquare": 	sq = new IncomeTaxSquare(getTextValue(el, "Name"),getIntValue(el, "Percent"),getIntValue(el, "Max"));
		  		break;
		  	case "JailSquare":		sq = new JailSquare(getTextValue(el, "Name"),getIntValue(el, "Bail"));
		  		break;
		  	case "LotSquare":		int[] rent = rents[getIntValue(el, "Rentlist")];
		  							sq = new LotSquare(getTextValue(el, "Name"),groups.get(getIntValue(el, "Group")),getIntValue(el, "Price"),getIntValue(el, "Houseprice"),rent);
				break;
		  	case "RailroadSquare":	sq = new RailroadSquare(getTextValue(el, "Name"),getIntValue(el, "Price"),getIntValue(el, "Baserent"));
		  		break;
		  	case "UtilitySquare":   sq = new UtilitySquare(getTextValue(el, "Name"), getIntValue(el, "Price"),getIntValue(el, "Base"),getIntValue(el, "Groupbase"));
		  		break;
		  	case "ChanceSquare":	sq= new ChanceSquare(getTextValue(el, "Name"),getIntValue(el, "Decktype"));
		  		break;
		  	default : 				sq = new RegularSquare(getTextValue(el, "Name"));
		  
		  
		  }
		  return sq;
	  }
	  
	  /**
		 * I take a xml element and the tag name, look for the tag and get.
		 * the text content
		 * i.e for <employee><name>John</name></employee> xml snippet if
		 * the Element points to employee node and tagName is 'name' I will return John
		 */
	  private static String getTextValue(Element ele, String tagName) throws MonopolyException {
			String textVal = null;
			NodeList nl = ele.getElementsByTagName(tagName);
			if(nl != null && nl.getLength() > 0) {
				Element el = (Element)nl.item(0);
				textVal = el.getFirstChild().getNodeValue();
			}
			if(textVal == null){
				throw new MonopolyException("Error:"+tagName+  "  - String field doesn't contain a value");
			}
			return textVal;
		}
		
		/**
		 * Calls getTextValue and returns a int value.
		 * @throws MonopolyException 
		 */
		private static int getIntValue(Element ele, String tagName) throws MonopolyException{
			//in production application you would catch the exception
			int i = 0;
			try{
				i = Integer.parseInt(getTextValue(ele,tagName));
			} catch(Exception e){
				throw new MonopolyException("Error: "+tagName+ "- Number field doesn't contain an integer");
			}
			
			return i;
		}
}
