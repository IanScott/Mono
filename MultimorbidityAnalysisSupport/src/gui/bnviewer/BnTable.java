package gui.bnviewer;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Stack;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

import domain.tools.bntool.Node;

/**
 * Class represents a double JTable, which is used to display CPT values.
 * @author ABI team 37
 * @version 1.0
 */
public class BnTable extends JPanel{
        private static final long serialVersionUID = -6497529833781767985L;
        private String[][] rlvalues;
        private String[] rlhead;
        private String[] lhead = {};
        private String[] rhead = {};
        private String[][] lvalues = {{}};
        private String[][] rvalues = {{}};
        
        private JTable maintable;
        
        /**
         * Constructor.
         * @param node the node to display
         */
        public BnTable(Node node){
                if(node.getCpt() == null){
                        return;}
                
                this.rvalues = node.getCpt().getTable();              
                      
                DecimalFormat df = new DecimalFormat("#0.000");
                DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
                sym.setDecimalSeparator('.');
                df.setDecimalFormatSymbols(sym);                        
                
                for(int i = 0; i< this.rvalues.length ; i++ ){
                	for(int j = 0; j<this.rvalues[0].length;j++){
                		double temp = Double.parseDouble(rvalues[i][j]);
                		
                		rvalues[i][j] = df.format(temp);
                	}
                }
                
                this.rhead = node.getCpt().getLevels();         
                                
                //this.righttable = new JTable(node.getCpt().getTable(),node.getCpt().getLevels());
                this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
                
                
                List<Node> parents = node.getParents();
                String[] parentnames = new String[parents.size()];
                for(int i =0; i<parents.size();i++){
                        parentnames[i] = parents.get(i).getNodeName();
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
                        lvalues = values;
                }
                
                lhead = parentnames;
                rlvalues = new String[Math.max(rvalues.length, lvalues.length)][lhead.length+rhead.length];
                
                for(int j = 0; j<lvalues.length;j++){
                        for(int k = 0; k<lvalues[j].length;k++){
                                rlvalues[j][k] = lvalues[j][k];
                        }
                }
                for(int l = 0; l<rvalues.length; l++){
                        for(int m =0; m<rvalues[l].length; m++){
                                
                                int temp =0;
                                if(lvalues.length >0){
                                temp= m+lvalues[l].length;}
                                
                                rlvalues[l][temp] = rvalues[l][m];
                        }
                }
                rlhead = new String[rhead.length+lhead.length];
                
                for(int n= 0; n<lhead.length;n++){
                        rlhead[n] = lhead[n];
                }
                for(int p= 0; p<rhead.length;p++){
                        rlhead[p+lhead.length] = rhead[p];
                }

                maintable = new JTable(rlvalues,rlhead) {
        			private static final long serialVersionUID = -5751854321180727316L;

        			@Override
        		    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        		        Component comp = super.prepareRenderer(renderer, row, col);
        		        Boolean flag = true;
        		        Object value = getModel().getValueAt(row, col);
        		        try {        		  
        		          Double.parseDouble((String)value);  
        		        } catch(NumberFormatException nfe) {  
        		        	flag = false;  
        		        }  
        		        
        		                 		    	        
        		        if (!flag) {
        		             comp.setBackground(Color.LIGHT_GRAY);     		           
        		        }else{
        		        	comp.setBackground(Color.WHITE);
        		        }
        		        if(isCellSelected(row, col)){           
        		            comp.setBackground(Color.white);
        		            comp.setForeground(Color.BLACK);
        		                         
        		        }
        		        return comp;
        		    }
        			
        		};
                
        		maintable.getTableHeader().setReorderingAllowed(false);
        		maintable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        		maintable.setCellSelectionEnabled(true);
        		maintable.setColumnSelectionAllowed(true);
                JScrollPane scroll1 = new JScrollPane(maintable);
                
                this.add(scroll1);
        }
        
        /**
         * Method returns the size the table need to be.
         * @return the size in pixels
         */
        public int getTSize(){
                int temp = 0;
                if(rlvalues != null){
                        temp = rlvalues.length*16;
                }               
                return 58+temp;
        }
        
        /**
         * Method return bntable head.
         * @return the head.
         */
        public String[] getHead(){
        	return rlhead;
        }
}
