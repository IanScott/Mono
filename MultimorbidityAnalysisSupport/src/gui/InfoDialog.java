package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * This is an About Panel used for displaying information.
 * @author ABI team 37
 * @version 1.0
 */
public class InfoDialog{
	private JTextArea text;
	private JDialog dialog;
	private JFrame mainframe;
	private JPanel content;
	private File file;
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame
	 * @param file to load containing text
	 */
	public InfoDialog(JFrame mainframe, File file) {
		this.mainframe = mainframe;
		this.file = file;
		this.content = new JPanel();
		
		setupContent();
		setupDialog();
		dialog.setVisible(true);
	}
	
	/**
	 * Constructor 2.
	 * @param mainframe the main JFrame
	 */
	public InfoDialog(JFrame mainframe) {
		this(mainframe, null);
	}
	
	private void setupContent(){
		content.setLayout(null);
		content.setSize(650,350);
		
		text = new JTextArea();
		text.setEditable(false);
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		
		if(file == null){
			file = new File("resources/text.txt");
		}
		
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
		
		String line = in.readLine();
		while(line != null){
		  text.append(line + "\n");
		  line = in.readLine();
		}
		} catch (IOException e) {
			new ErrorDialog(mainframe, e.getMessage());
		}
		
		text.setBackground(SystemColor.text);
		text.setFont(new Font("Serif", Font.PLAIN, 14));
		
		JScrollPane scrollpane = new JScrollPane(text);
		scrollpane.setBounds(10, 10, 625, 350);
		text.setCaretPosition(0);
		content.add(scrollpane);
		
		
		
	}
	
	private void setupDialog(){
		dialog = new JDialog (mainframe, "INFORMATION", true);
        dialog.setSize (650, 400);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (mainframe);
		dialog.setContentPane(content);
	}
}
