package gui.startDialog;

import javax.swing.JButton;

/**
 * Custom Button
 * @author ian.van.nieuwkoop
 *
 */
public class MButton extends JButton{
	private String path = "";
	
	public MButton(String label){
		super(label);
	}
	
	public String getPath(){
		return path;
	}
	
	public void setPath(String path){
		this.path = path;
	}
}
