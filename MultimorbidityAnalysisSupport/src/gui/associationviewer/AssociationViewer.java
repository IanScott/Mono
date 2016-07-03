package gui.associationviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import domain.tools.associationtool.AssociationTool;
import domain.tools.datatool.DataTool;
import gui.ToolViewer;
import gui.associationviewer.association.AssociationMenu;
import gui.associationviewer.association.AssociationResultPanel;

/**
 * Main class for the Assocition tool viewer.
 * @author ABI team 37
 * @version 1.0
 */
public class AssociationViewer extends ToolViewer{
	private static final long serialVersionUID = 3561758092955571027L;

	private JPanel associationPreparePanel;
	private JPanel associationResultPanel;
	private DataTool datatool;
	private AssociationTool associationtool;
	private JFrame mainframe;
	private JScrollPane centerpanel;
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame to use
	 * @param datatool the datatool to use
	 * @param associationtool the association tool to use
	 */
	public AssociationViewer(JFrame mainframe,DataTool datatool, AssociationTool associationtool) {		
		this.datatool = datatool;
		this.associationtool = associationtool;
		this.mainframe = mainframe;
		this.setLayout(new BorderLayout(0, 0));
		setup();
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setBackground(Color.WHITE);
		
	}
	
	private void setup(){

		associationPreparePanel = new AssociationMenu(mainframe, this,datatool, associationtool);
		associationPreparePanel.setPreferredSize(new Dimension(300,1100));
		
		
		JScrollPane scroll = new JScrollPane(associationPreparePanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setSize(new Dimension(300,1100));
		this.add(scroll, BorderLayout.WEST);
		
		setupCenterPanel();
	}
	
	private void setupCenterPanel(){
		if(centerpanel == null){
			centerpanel = new JScrollPane(associationResultPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			this.add(centerpanel, BorderLayout.CENTER);
		}else{
			centerpanel.removeAll();
		}
		associationResultPanel = new AssociationResultPanel(datatool, associationtool);
		
		centerpanel.getViewport().add(associationResultPanel);
	}
	
}
