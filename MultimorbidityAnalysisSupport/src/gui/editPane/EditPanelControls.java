package gui.editPane;

import javax.swing.JPanel;
import domain.tools.datatool.DataTool;
import gui.ErrorDialog;
import util.MultimorbidityException;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * This Panel contains the standard menu of the EditPanel.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class EditPanelControls extends JPanel implements Observer {
	/**
	 * Constant Larger.
	 */
	public static final int LARGER = 1;
	/**
	 * Constant Smaller.
	 */
	public static final int SMALLER = 0;
	private static final long serialVersionUID = 5370630528787311637L;
	private DataTool datatool;
	private JButton dropButton;
	private JFrame mainframe;
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame
	 * @param datatool the DataTool to use
	 * @param size an int representing the size
	 */
	public EditPanelControls(JFrame mainframe, DataTool datatool, int size) {
		this.datatool = datatool;
		this.mainframe = mainframe;
		setLayout(null);
		this.setSize(new Dimension(600, 53));
		
		JButton dropButton = new JButton("Drop Unknown values");
		dropButton.setBounds(10, 11, 169, 29);
		dropButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dropAction();
			} 
		});
		add(dropButton);
		dropButton.setToolTipText("All rows with an Unknown value will be dropped");
		if(size == LARGER){
			JLabel dropLabel = new JLabel("All rows with an Unknown value will be dropped");
			dropLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
			dropLabel.setBounds(189, 11, 388, 29);
			add(dropLabel);
		}
	}
	
	private void dropAction(){
		String[] columns = datatool.getDataSetColumnnames();
		Boolean flag = false;
		for(String c: columns){
			Boolean hasNa = datatool.hasNa(c);
			if(hasNa){
				flag = true;
				break;
			}
		}
		if(!flag){
			return;
		}
		datatool.snapshot();
		try {
			datatool.omitNa();
		} catch (MultimorbidityException e) {
			new ErrorDialog(mainframe, e.getMessage());
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String[] columns = datatool.getDataSetColumnnames();
		Boolean flag = false;
		for(String c: columns){
			Boolean hasNa = datatool.hasNa(c);
			if(hasNa){
				flag = true;
				break;
			}
		}
		if(flag){
			this.dropButton.setEnabled(true);
		}else{
			this.dropButton.setEnabled(false);
		}		
	}

}
