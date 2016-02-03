package guiold;


import domain.ToolController;
import guiold.BNFrame;
import guiold.DataviewerFrame;
import guiold.DummyFrame;
import guiold.StartFrame;

public class GuiController {
	private ToolController toolcontroller;
	private StartFrame sf;
	private DataviewerFrame df;
	private BNFrame bf;
	
	public GuiController(ToolController toolcontroller){
		this.toolcontroller = toolcontroller;
		sf = new StartFrame(toolcontroller, this);
		sf.setVisible(true);
	}
	
	public void dataviewerFrame(){
		sf.setVisible(false);
		if(df == null){
			df = new DataviewerFrame(this);
			toolcontroller.addDataSetObserver(df);
		}
		df.setVisible(true);
	}
	
	public void startFrame(){
		if(df != null){
		df.setVisible(false);}
		
		if(bf != null){
			bf.setVisible(false);
			bf.dispose();
		}
		
		sf.recentButtons();
		sf.setVisible(true);
	}
	
	public ToolController getToolController(){
		return toolcontroller;
	}
	
	public void toolFrame(){
		df.setVisible(false);
		sf.setVisible(false);
		//if(bf == null){
			bf = new BNFrame(toolcontroller, this);
		//}
		bf.setVisible(true);
	}
	
	public void dummyFrame(){
		new DummyFrame(sf);
	}
}
