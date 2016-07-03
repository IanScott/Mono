package gui.countviewer;

import gui.ToolViewer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import domain.tools.counttool.CountTool;
import domain.tools.datatool.DataTool;

/**
 * The main Glm Viewer panel.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class GlmViewer extends ToolViewer implements Observer{
        private static final long serialVersionUID = -1438425577008122188L;
        private JFrame mainframe;
        private DataTool datatool;
        private CountTool counttool;
        private JTable glmTable;

        
        /**
         * Constructor.
         * @param mainframe the main JFrame to use
         * @param datatool the datatool instance to use
         * @param counttool the counttool instance to use
         */
        public GlmViewer(JFrame mainframe, DataTool datatool, CountTool counttool) {
                this.mainframe = mainframe;
                this.datatool = datatool;
                this.counttool = counttool;
                this.setLayout(new BorderLayout(0, 0)); 
                
                setup();
        }
        
        private void setup(){
                
                JPanel menu = new GlmMenu(mainframe, datatool, counttool);
                JScrollPane scroll = new JScrollPane(menu,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                add(scroll, BorderLayout.WEST);          
                
                JPanel content = new GlmDisplayPanel(mainframe, counttool);
                content.setPreferredSize(new Dimension(700,700));
                add(content, BorderLayout.CENTER);
        }
        
        
        @Override
        public void update(Observable o, Object arg) {
                if(o instanceof CountTool && (Integer)arg == 2){
                        String[][] cvalues = counttool.getGLMTable();
                        String[] chead = counttool.getGLMHead();
                        
                        glmTable.getTableHeader().setReorderingAllowed(false);
                        glmTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        glmTable.setModel(new DefaultTableModel(cvalues, chead));
                }
        }
}
