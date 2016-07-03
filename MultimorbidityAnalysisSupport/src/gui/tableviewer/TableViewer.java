package gui.tableviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import domain.tools.datatool.DataTool;
import gui.ErrorDialog;
import gui.TableRowUtilities;

/**
 * The mainpanel of the tableviewer panel.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class TableViewer extends JPanel implements Observer{
	private static final long serialVersionUID = -496139605137334030L;
	private JScrollPane scrollPane;
	private JTable table;
	private DataTool datatool;
	private String[] columnnames;
	private String[][] values;
	private JFrame mainframe;
	/**
	 * Constructor.
	 * @param mainframe the main JFrame to use
	 * @param datatool to be used
	 */
	public TableViewer(JFrame mainframe, DataTool datatool){
		this.mainframe = mainframe;
		this.datatool = datatool;
		this.setLayout(new BorderLayout(0, 0));
		datatool.addObserver(this);
		initTable();
	}
	
	private void initTable(){
		this.removeAll();
		this.columnnames = datatool.getDataSetColumnnames();
		this.values = this.convertDataSetValues();
		
		table = new JTable() {
			private static final long serialVersionUID = -5751854321180727316L;

			@Override
		    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		        Component comp = super.prepareRenderer(renderer, row, col);
		        
		        Object value = getModel().getValueAt(row, col);
		        if (value.equals("<<UNKNOWN>>")||value.equals("<<NOT A BOOLEAN>>")||value.equals("NaN")) {
		                comp.setBackground(Color.YELLOW);
		             
		        } else {
		            comp.setBackground(Color.white);
		        	}
		        if(isCellSelected(row, col)){           
		            comp.setBackground(Color.white);
		            comp.setForeground(Color.BLACK);
		                         
		        }
		        return comp;
		    }
			
		};
		
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(values, columnnames));
		scrollPane = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane, BorderLayout.CENTER);
		TableRowUtilities.addNumberColumn(table, 1, true);
		
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
	}
	
	private String[][] convertDataSetValues(){
		String[] columns = datatool.getDataSetColumnnames();
		String[][] values = new String[datatool.getMaxRows()][columns.length];
		
		
		for(int i = 0; i<columns.length; i++){
			String type = datatool.getColumnType(columns[i]);
			if(type == null){
				type = "unknown";
			}
			String[] vlist= datatool.getColumnValues(columns[i]);
			
			for(int j = 0;j<vlist.length; j++){	
				if(vlist[j] == null){
					values[j][i] = "<<UNKNOWN>>";
				}else if(type.equals("logical")&&vlist[j].equals("1")){
					values[j][i] = "TRUE";
				}else if(type.equals("logical")&&vlist[j].equals("0")){
					values[j][i] = "FALSE";
				}else if(type.equals("logical")&&vlist[j].equals("-2147483648")){
					values[j][i] = "<<NOT A BOOLEAN>>";
				}else if(type.equals("Date")){
					values[j][i] = this.convertRDate(vlist[j]);
				}
				else { values[j][i] = vlist[j];} 
			}		
		}		
		return values;
	}
	
	private String convertRDate(String temp){
		int x = (int)Double.parseDouble(temp);
		String dt = "1970-01-01";  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dt));
		} catch (ParseException e) {
			new ErrorDialog(mainframe, e.getMessage());
		}
		c.add(Calendar.DATE, x);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		
		return dt;
	}

	@Override
	public void update(Observable o, Object arg) {
		initTable();
	}
}
