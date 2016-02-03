package guiold;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import guiold.Colpanel;
import guiold.GuiController;
import guiold.LevPanel;
import guiold.NASPanel;

import java.awt.Color;

public class EditPanel extends JPanel implements Observer{
	private Colpanel panel_1;
	private LevPanel panel_2;
	private NASPanel panel_4;
	private GuiController guicontroller;
	
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public EditPanel(GuiController guicontroller){
		init(guicontroller, null);
	}
	
	public EditPanel(GuiController guicontroller, String columnname) {
		init(guicontroller, columnname);
	}
	

	
	private void init(GuiController guicontroller, String columnname){
		
		
		
		this.guicontroller = guicontroller;
		
		guicontroller.getToolController().addDataSetObserver(this);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panel_1 = new Colpanel(guicontroller);
		add(panel_1);
		panel_2 = new LevPanel(guicontroller, columnname);
		add(panel_2);
		panel_4 = new NASPanel(guicontroller);
		add(panel_4);
		setup(columnname);
	}
	
	private void setup(String columnname){
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		setSize(600, panel_1.getPanelHeight()+panel_2.getPanelHeight()+panel_4.getPanelHeight());
	}
	
	public String getSelectedColumn(){
		return panel_2.getSelectedColumn();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		
	}
	
	

}
