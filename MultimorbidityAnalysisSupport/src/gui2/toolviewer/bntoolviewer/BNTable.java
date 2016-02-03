package gui2.toolviewer.bntoolviewer;

import java.util.List;
import java.util.Stack;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import domain.tools.bntool.Node;



public class BNTable extends JPanel{
	
	private JTable righttable;
	private JTable lefttable;
	
	public BNTable(Node node){
		if(node.getCpt() == null){
			return;}
		
		this.righttable = new JTable(node.getCpt().getTable(),node.getCpt().getLevels());
		this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		
		
		List<Node> parents = node.getParents();
		String[] parentnames = new String[parents.size()];
		for(int i =0; i<parents.size();i++){
			parentnames[i] = parents.get(i).getNodeName();
		}
		
		if(parents.size() == 0){
			String[] empty = {};
			String[][] empty1 = new String[node.getNodeLevels().size()][1];
			this.lefttable = new JTable(empty1,empty);
		}
			
		if(parents.size()>0){	
			Stack<Node> stack = new Stack<Node>();
			
			int i = 0;
			
			while(i < parents.size()){
				stack.push(parents.get(i));
				i++;
			}
			
			int total = 1;
			
			for(Node n: parents){
				total = total*n.getCpt().getLevels().length;
			}
			
			String[][] values = new String[total][parents.size()];
			
			int ratio = 1;
			int count = 0;
			int parentnr = parents.size()-1;
			Node lastnode = null;
			
			while(!stack.isEmpty()){
				Node n = stack.pop();
	
				if(lastnode != null){
				 ratio= ratio*lastnode.getNodeLevels().size();}
				int next = -1;
				
				while(count <total){
					if(count%ratio==0){next++;}
					values[count][parentnr] = n.getNodeLevels().get(next%n.getNodeLevels().size());
					count++;
				}
				count = 0;
				parentnr--;
				lastnode = n;
			}
				
			this.lefttable = new JTable(values, parentnames);
			
			//this.add(lefttable);
			//this.add(righttable);
			//JFrame f = new JFrame();
			//JScrollPane scroll = new JScrollPane(lefttable);
			//f.getContentPane().add(this);
			//f.setSize(200, 200);
			//f.setVisible(true);
		}
		JScrollPane scroll1 = new JScrollPane(lefttable);
		JScrollPane scroll2 = new JScrollPane(righttable);
		
		this.add(scroll1);
		this.add(scroll2);
	}
	
}
