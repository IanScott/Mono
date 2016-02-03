package guiold.panels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import domain.DataSetInstance;

public class DataSetPanel extends JFrame implements Observer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2095847816359777410L;
	
	private String[] columnnames ={"empty"};
	private String[][] values = {{"test"},{"test"}};
	private JTable table;
	private JScrollPane scrollpane;
	
	public DataSetPanel(DataSetInstance dataset){
		//setup Observer pattern
		//dataset.addObserver(this);
	
		this.columnnames =dataset.getColumnnames();
		this.values = dataset.getValues();
		table = new JTable(values, columnnames);
		scrollpane = new JScrollPane(table);
		
		//testcode
		test();
	}
	
	private void init(){
		
	}

	@Override
	public void update(Observable o, Object arg) {
		//this.columnnames =((DataSet)o).getColumnnames();
		//this.values = ((DataSet)o).getValues();
		
		DefaultTableModel model = new DefaultTableModel(values,columnnames);
		table.setModel(model);
		table.repaint();
		scrollpane.repaint();
	}
	
	
	private void test(){
		
		
		this.getContentPane().add(scrollpane);
		
		this.setVisible(true);
		
	}
}
