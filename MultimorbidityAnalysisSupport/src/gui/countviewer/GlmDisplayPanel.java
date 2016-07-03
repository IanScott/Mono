package gui.countviewer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import domain.tools.counttool.CountTool;
import javaGD.JavaGDPanel;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * The displaypanel of the Glm viewer panel.
 * @author ABI team 37
 * @version 1.0
 */
public class GlmDisplayPanel extends JPanel implements Observer{
	private static final long serialVersionUID = -1438425577008122188L;
	private JFrame mainframe;
	private CountTool counttool;
	private JTable glmTable;	
	private JPanel rightpanel;
	private JPanel cContent;
	private JTextArea txtArea;
	
	/**
	 * Constructor.
	 * @param mainframe the main JFrame to use
	 * @param counttool the counttool instance to use.
	 */
	public GlmDisplayPanel(JFrame mainframe, CountTool counttool) {
		this.mainframe = mainframe;
		this.counttool = counttool;
		counttool.addObserver(this);
		this.setBackground(Color.WHITE);		
		setLayout(new BorderLayout(0, 0));
		
		setupTabelPanel();	
		rightpanel = JavaGDPanel.getPanel();	
		this.add(rightpanel, BorderLayout.CENTER);
		initSummaryPanel();
	}
	
	private void initSummaryPanel() {
		
		if(cContent == null){
			this.txtArea = new JTextArea(); 
			cContent = new JPanel();
			cContent.setBackground(Color.white);
		    cContent.add(txtArea);
			
			JScrollPane cScroll = new JScrollPane(cContent);
			cScroll.setPreferredSize(new Dimension(500,300));

			add(cScroll, BorderLayout.SOUTH);
		}
	}
	
	private void fillSummary(){
		String[] output = counttool.getSummary();

		txtArea.setText("");
		if (output != null && output.length > 0) {
			for(int i=0;i < output.length;i++) {
			      txtArea.setText( txtArea.getText() + "\n"+ output[i]); 
	 	    }		
		} else {
   			String title = "No summary available";
   			txtArea.setText("");
   		    txtArea.setText( txtArea.getText() + "\n"+ title);   			
   		}
	}
	
	private void setupTabelPanel(){
		JPanel leftpanel = new JPanel();
		leftpanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		leftpanel.setLayout(new GridLayout(1, 2, 0, 0));	
			
		JPanel panel = new JPanel();
		leftpanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		//Empty Panels
		panel.add(new JPanel(), BorderLayout.WEST);
		panel.add(new JPanel(), BorderLayout.EAST);
		
		JLabel glmLabel = new JLabel("Generalized Linear Models (GLM)");
		glmLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		glmLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(glmLabel, BorderLayout.NORTH);
		
		glmTable = new JTable();
		glmTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		JScrollPane scrollPane_2 = new JScrollPane(glmTable);
		scrollPane_2.getViewport().setBackground(Color.WHITE);
		panel.add(scrollPane_2, BorderLayout.CENTER);

		this.add(leftpanel, BorderLayout.WEST);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof CountTool && (Integer)arg == 2){
			String[][] cvalues = counttool.getGLMTable();
			DecimalFormat f = new DecimalFormat("#0.000");
            for(int i = 0; i< cvalues.length ; i++ ){
            	for(int j = 0; j<cvalues[0].length;j++){
            		try{
            		double temp = Double.parseDouble(cvalues[i][j]);
            		cvalues[i][j] = f.format(temp);
            		}catch(Exception e){} //If not a number do nothing
            	}
            }
			String[] chead = counttool.getGLMHead();
			
			glmTable.getTableHeader().setReorderingAllowed(false);
			glmTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			glmTable.setModel(new DefaultTableModel(cvalues, chead));
			
			fillSummary();
			if(rightpanel != null){
				this.remove(rightpanel);
				rightpanel = JavaGDPanel.getPanel();
				this.add(rightpanel, BorderLayout.CENTER);
				mainframe.revalidate();
				mainframe.repaint();
			}
		}
	}
}
