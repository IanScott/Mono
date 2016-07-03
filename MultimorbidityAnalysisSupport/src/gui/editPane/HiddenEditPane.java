package gui.editPane;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

/**
 * This class represents a blanc panel with just vertical text.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class HiddenEditPane extends JPanel{
	private static final long serialVersionUID = -3573059043617892032L;
	private static final int X = 17;
	private static final int WIDTH= 40;
	private static final int HEIGHT = 50;
	private static final Font FONT = new Font("Tahoma", Font.BOLD, 18);
	private static final int Y = 30; 
	
	/**
	 * Constructor.
	 */
	public HiddenEditPane() {
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setLayout(null);
		this.setPreferredSize(new Dimension(65,750));
		int y = 10;
		
		String[] text = {"E","d","i","t","","D","a","t","a","","M","e","n","u"};
		
		for(String s: text){
			JLabel lblNewLabel = new JLabel(s);
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(FONT);
			lblNewLabel.setBounds(X, y, WIDTH, HEIGHT);
			add(lblNewLabel);
			y =y+Y;
		}
	}
	
}
