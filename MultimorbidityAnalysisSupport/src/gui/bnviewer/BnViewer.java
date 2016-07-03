package gui.bnviewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import domain.tools.bntool.BnTool;
import domain.tools.bntool.Network;
import domain.tools.datatool.DataTool;
import gui.ToolViewer;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * The mainpanel of the BnTool viewer.
 * @author ABI team 37
 * @version 1.0
 */
public class BnViewer extends ToolViewer implements Observer{
	/**
	 * Larger constant.
	 */
	public static final int LARGER = 1;
	/**
	 * Smaller constant.
	 */
	public static final int SMALLER = 0;
	private static final long serialVersionUID = 6694577431007850611L;
	private CPTSPanel cptspanel = null;
	private ConnectorContainer cc = null;
	private JTabbedPane tabbedPane;
	private JLabel algorLabel = null;
	private BnTool bntool;
	private DataTool datatool;
	private JFrame mainframe;
	private int size;
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame
	 * @param datatool the DataTool to use
	 * @param bntool the BnTool to use
	 * @param size an int representing the size
	 */
	public BnViewer(JFrame mainframe,DataTool datatool, BnTool bntool, int size){
		super();
		this.mainframe=mainframe;
		this.bntool= bntool;
		this.datatool = datatool;
		this.bntool.addObserver(this);
		this.size = size;
		init();	
	}
	/**
	 * Constructor 2.
	 * @param mainframe the main JFrame
	 * @param datatool the DataTool to use
	 * @param bntool the BnTool to use
	 */
	public BnViewer(JFrame mainframe,DataTool datatool, BnTool bntool){
		this(mainframe,datatool, bntool, 1);
	}
	
	private void init(){
		this.setLayout(new BorderLayout(0, 0));	
		
		bottompanel();
		sidepanel();
		mainpanel();
	}

	private void bottompanel(){
		JPanel toppanel = new JPanel();
		this.add(toppanel, BorderLayout.SOUTH);
		toppanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel txtAlgorLabel = new JLabel("Algorithm: ");
		txtAlgorLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		toppanel.add(txtAlgorLabel);
		
		algorLabel = new JLabel("");
		algorLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		toppanel.add(algorLabel);
	}
	
	private void sidepanel(){
		JPanel sidemenupanel = null;
		if(size == 1){
			sidemenupanel = new SideMenu(mainframe,datatool, bntool);
			sidemenupanel.setPreferredSize(new Dimension(300,900));
		}else if(size == 0){
			sidemenupanel = new JPanel(); 
			JTabbedPane tp = new SideMenuSmall(mainframe,datatool, bntool);
			sidemenupanel.setLayout(new BorderLayout(0, 0));
			sidemenupanel.add(tp, BorderLayout.CENTER);
			sidemenupanel.setPreferredSize(new Dimension(300,650));
		}
		
		JScrollPane scroll = new JScrollPane(sidemenupanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setSize(new Dimension(300,1000));
		add(scroll, BorderLayout.WEST);
		
	}
	
	private void mainpanel(){
		if(tabbedPane == null){
			tabbedPane = new JTabbedPane(SwingConstants.TOP);
			add(tabbedPane, BorderLayout.CENTER);
		}else{
			tabbedPane.removeAll();
		}
		
		Network bnnetwork = bntool.getBnNetwork();
		
		if(bnnetwork !=null){
			algorLabel.setText(bnnetwork.getAlgorithm());
			
			this.cc = new ConnectorContainer(bnnetwork);
			cc.setPreferredSize(new Dimension(800,800));
			JScrollPane panel_1 = new JScrollPane(cc);
			tabbedPane.addTab("Graph", null, panel_1, null);
			
			if(bnnetwork.isFitted()){
				this.cptspanel = new CPTSPanel(bnnetwork);
				JScrollPane panel_2 = new JScrollPane(cptspanel);
				tabbedPane.addTab("CPTS Table", null, panel_2, null);
			}							
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		mainpanel();
		this.revalidate();
		this.repaint();
	}
}
