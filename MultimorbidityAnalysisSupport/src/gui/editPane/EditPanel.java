package gui.editPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import domain.tools.datatool.DataTool;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This is the Main EditPane. It is devided into 2 seperate panels.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class EditPanel extends JPanel{
	/**
	 * Constant Larger.
	 */
	public static final int LARGER = 1;
	/**
	 * Constant Smaller.
	 */
	public static final int SMALLER = 0;
	private static final long serialVersionUID = 1L;
	private JPanel info;
	private JPanel controls;
	private JScrollPane scrollpane;
	private JButton hideButton;
	private int width;
	private Boolean hidden;
	private JFrame mainframe;
	private JPanel hiddenpanel;
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame
	 * @param datatool the Datatool to use.
	 * @param size a constant representing the size
	 */
	public EditPanel(JFrame mainframe, DataTool datatool,int size){
		this.mainframe = mainframe;
		this.hidden = false;		
		this.setLayout(new BorderLayout(0, 0));	
		
		JPanel toppanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) toppanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		this.controls = new EditPanelControls(mainframe,datatool, size);
		
		
		if(size == LARGER){
			this.width = 600;
			this.setPreferredSize(new Dimension(600,1000));
			this.controls.setPreferredSize(new Dimension(600,55));
			toppanel.setPreferredSize(new Dimension(600, 23));
			this.info = new EditPanelInfo(mainframe, datatool);
		}
		if(size == SMALLER){
			this.width = 315;
			this.setPreferredSize(new Dimension(315,1000));
			this.controls.setPreferredSize(new Dimension(315,55));
			toppanel.setPreferredSize(new Dimension(315, 23));
			this.info = new EditPanelInfoSmall(mainframe, datatool);
		}
				
		this.scrollpane = new JScrollPane(info);		
		this.add(scrollpane, BorderLayout.CENTER);
		this.add(toppanel, BorderLayout.NORTH);
		
		hideButton = new JButton("HIDE");
		hideButton.setPreferredSize(new Dimension(70,17));
		hideButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				hideAction();
				
			} });
		toppanel.add(hideButton);
		
		this.add(controls, BorderLayout.SOUTH);
	}
	
	
	private void hideAction(){
		if(!this.hidden){
			this.setPreferredSize(new Dimension(75,1000));
			this.remove(scrollpane);
			this.remove(controls);
			hiddenpanel = new HiddenEditPane();
			hiddenpanel.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					hideAction();	
				}

				@Override
				public void mousePressed(MouseEvent e) {	
					//not used
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					//not used
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					hiddenpanel.setBorder(new LineBorder(new Color(0, 0, 0), 4));					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					hiddenpanel.setBorder(new LineBorder(new Color(0, 0, 0), 1));					
				} });
			this.add(hiddenpanel, BorderLayout.CENTER);
			mainframe.revalidate();
			mainframe.repaint();
			this.hidden = true;
			hideButton.setText("SHOW");
		}else{
			this.setPreferredSize(new Dimension(width,1000));
			this.remove(hiddenpanel);
			this.add(scrollpane, BorderLayout.CENTER);
			this.add(controls, BorderLayout.SOUTH);
			mainframe.revalidate();
			mainframe.repaint();
			this.hidden = false;
			hideButton.setText("HIDE");
		}		
	}
}
