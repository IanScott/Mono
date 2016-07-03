package gui.startDialog;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import gui.ErrorDialog;

/**
 * This is an About Panel used for displaying information.
 * @author ABI team 37
 * @version 1.0
 */
public class AboutDialog{
	private JTextArea welcometxt;
	private JDialog dialog;
	private JFrame mainframe;
	private JPanel content;
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame
	 */
	public AboutDialog(JFrame mainframe) {
		this.mainframe = mainframe;
		this.content = new JPanel();
		
		setupContent();
		setupDialog();
		dialog.setVisible(true);
	}
	
	private void setupContent(){
		content.setLayout(null);
		content.setSize(650,350);
		
		welcometxt = new JTextArea();
		welcometxt.setEditable(false);
		welcometxt.setWrapStyleWord(true);
		welcometxt.setLineWrap(true);
		
		File file = new File("resources/text.txt");
		
		
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
		
		String line = in.readLine();
		while(line != null){
		  welcometxt.append(line + "\n");
		  line = in.readLine();
		}
		} catch (IOException e) {
			welcometxt.setText("Error loading text file.");
		}
		
		welcometxt.setBackground(SystemColor.text);
		welcometxt.setFont(new Font("Serif", Font.PLAIN, 14));
		JScrollPane scrollpane = new JScrollPane(welcometxt);
		scrollpane.setBounds(37, 64, 395, 280);
		welcometxt.setCaretPosition(0);
		content.add(scrollpane);
		
		
		JLabel lblNewLabel = new JLabel("Multi Morbidity Tool");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(37, 11, 213, 30);
		content.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("OU LOGO");
		lblNewLabel_1.setBounds(467, 69, 146, 146);
		content.add(lblNewLabel_1);
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("resources/oulogo1.jpg"));
		} catch (IOException e) {
			new ErrorDialog(mainframe,"Can't load resources/oulogo1.jpg");
		}
		ImageIcon icon = new ImageIcon(img);
		lblNewLabel_1.setIcon(icon);
		
	}
	
	private void setupDialog(){
		dialog = new JDialog (mainframe, "About", true);
		dialog.setTitle("About Multi Morbidity Tool");
        dialog.setSize (650, 400);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (mainframe);
		dialog.setContentPane(content);
	}
}
