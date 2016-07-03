package gui.editPane;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import domain.tools.datatool.DataTool;
import java.awt.BorderLayout;
import java.awt.Color;

/**
 * Panel for the summary.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class SummaryPanel extends JPanel{
	private static final long serialVersionUID = 4672873287665431894L;
	private FreqPanel freqpanel;
	private JFrame mainframe;
	private DataTool datatool;
	private String columnname;
	private JDialog dialog;
	private JPanel contentPanel;
	
	/**
	 * Contructor
	 * @param mainframe the main JFrame, used by dialogframes.
	 * @param datatool the DataTool instance to use.
	 * @param columnname the name of the column to display.
	 */
	public SummaryPanel(JFrame mainframe,DataTool datatool, String columnname) {
		this.columnname = columnname;
		this.datatool = datatool;
		this.mainframe = mainframe;
		init();
	}
	
	private void init(){
		contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		freqpanel = new FreqPanel(datatool, columnname);
		datatool.barChart(columnname);
		
		JScrollPane scroll = new JScrollPane(freqpanel);
		scroll.setBounds(0, 0, 450, 200);
		contentPanel.add(scroll);
		freqpanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		contentPanel.setSize(450, 200);
		setupDialog();
		dialog.setVisible(true);
	}
	
	private void setupDialog(){
		dialog = new JDialog (mainframe, "Summary Info", true);
        dialog.setSize (450, 250);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo (mainframe);
        JPanel layout = new JPanel();
        
       
        layout.setLayout(new BorderLayout());
        
        contentPanel.setLayout(null);
        JScrollPane scroll = new JScrollPane(contentPanel);
        layout.add(scroll, BorderLayout.CENTER);
        
		dialog.setContentPane(layout);
	}
	
	
}

