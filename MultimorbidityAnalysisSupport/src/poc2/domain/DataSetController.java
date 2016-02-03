package poc2.domain;

import java.io.Serializable;
import java.util.Observable;
import java.util.Stack;

public class DataSetController extends Observable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5623734565511821115L;
	private DataSet dataset;
	private Stack<DataSet> undoStack;
	private Stack<DataSet> redoStack;
	
	public DataSetController(){
		this.undoStack = new SizedStack<DataSet>(7);
		this.redoStack = new SizedStack<DataSet>(7);
	}
	
	public DataSet getDataset(){
		if(dataset != null){
		return dataset.clone();}
		return null;
	}
	
	public void updateDataSet(DataSet ds){
		if(this.dataset != null){
		undoStack.push(dataset.clone());}
		this.dataset = ds;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void addResult(Result res){
		dataset.addResult(res);
	}

	public boolean canUndo(){
		return(!undoStack.isEmpty());
	}
	
	public boolean canRedo(){
		return(!redoStack.isEmpty());
	}
	
	public void undo(){
		
		if(canUndo()){
			redoStack.push(this.dataset);
			DataSet undo = undoStack.pop();
			this.dataset = undo;
			this.setChanged();
			this.notifyObservers();
		}
		
	}
	
	public void redo(){
		if(canRedo()){
			undoStack.push(this.dataset);
			this.dataset = redoStack.pop();
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	public void editColumnname(int index, String name){
		DataSet tdataset = dataset.clone();
		String[] columnnames = tdataset.getColumnnames();
		columnnames[index] = name;
		tdataset.setColumnnames(columnnames);
		this.updateDataSet(tdataset);
	}
	/*
	public void deleteColumn(int index){
		DataSet tdataset = dataset.clone();
		String[] columnnames = tdataset.getColumnnames();
		String[][] values = tdataset.getValues();
		
		String[] newcol = new String[columnnames.length-1];
		String[][] newval = new String[values.length][values[0].length-1];
		
		boolean flag = false;
		for(int i =0; i<columnnames.length; i++){
			if(i==index){
				flag = true;
			}else{
				if(!flag){
					newcol[i] = columnnames[i]; 
				}else{
					newcol[i-1]= columnnames[i];
				}
			}	
		}
		
		for(int i =0; i<values.length; i++){
			boolean flag1 = false;
			for(int j =0; j<values[0].length;j++){
				if(j==index){
					flag1 = true;
				}
				else{
					if(!flag1){
						newval[i][j] = values[i][j];}
					else{
						newval[i][j-1] = values[i][j];
					}
				}	
			}
		}
		
		
		tdataset.setAll(newcol, newval);;
		this.updateDataSet(tdataset);
	}
	
	*/
	
}



