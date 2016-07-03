package gui.countviewer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import domain.tools.counttool.CountTool;
import domain.tools.datatool.DataTool;
import gui.ToolViewer;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;

/**
 * Class responsible for displaying the CountTool controlls and data.
 * @author ABI team 37
 * @version 1.0
 */
public class CountViewer extends ToolViewer implements Observer{
        private static final long serialVersionUID = -1438425577008122188L;
        private JTable diseaseCountTable;
        private JTable diseaseGroupedTable;
        private JFrame mainframe;
        private DataTool datatool;
        private CountTool counttool;
        
        /**
         * Constructor.
         * @param mainframe the main JFrame
         * @param datatool the datatool instance to use
         * @param counttool the counttool instance to use
         */
        public CountViewer(JFrame mainframe, DataTool datatool, CountTool counttool) {
                this.mainframe = mainframe;
                this.datatool = datatool;
                this.counttool = counttool;
                this.setLayout(new BorderLayout(0, 0)); 
                
                counttool.addObserver(this);
                setup();
        }
        
        private void setup(){
                setupTabelPanel();
                JPanel menu = new CountMenu(mainframe,datatool,counttool);
                JScrollPane scroll = new JScrollPane(menu,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                add(scroll, BorderLayout.WEST);
        }
        
        
        private void setupTabelPanel(){
                JPanel tabelspanel = new JPanel();
                add(tabelspanel, BorderLayout.CENTER);
                tabelspanel.setBorder(new LineBorder(new Color(0, 0, 0)));
                tabelspanel.setLayout(new GridLayout(1, 2, 0, 0));
                
                //4 Tabel1Panel
                JPanel t1panel = new JPanel();
                tabelspanel.add(t1panel);
                t1panel.setLayout(new BorderLayout(0, 0));
                
                JLabel t1Label = new JLabel("Total Observations per Disease Count");
                t1Label.setFont(new Font("Tahoma", Font.BOLD, 15));
                t1Label.setHorizontalAlignment(JLabel.CENTER);
                t1panel.add(t1Label, BorderLayout.NORTH);
                
                //Empty Panels
                t1panel.add(new JPanel(), BorderLayout.WEST);
                t1panel.add(new JPanel(), BorderLayout.EAST);
                
                diseaseCountTable = new JTable();
                diseaseCountTable.setBorder(new LineBorder(new Color(0, 0, 0)));
                //t1panel.add(diseaseCountTable, BorderLayout.CENTER);
                JScrollPane scrollPane = new JScrollPane(diseaseCountTable);
                scrollPane.getViewport().setBackground(Color.WHITE);
                t1panel.add(scrollPane, BorderLayout.CENTER);
                
                //5 Tabel2Panel
                JPanel t2panel = new JPanel();
                tabelspanel.add(t2panel);
                t2panel.setLayout(new BorderLayout(0, 0));
                
                //Empty Panels
                t2panel.add(new JPanel(), BorderLayout.WEST);
                t2panel.add(new JPanel(), BorderLayout.EAST);
                
                diseaseGroupedTable = new JTable();
                diseaseGroupedTable.setBorder(new LineBorder(new Color(0, 0, 0)));
                //t2panel.add(diseaseGroupedTable, BorderLayout.CENTER);
                
                JLabel t2Label = new JLabel("Total Observations per Group and Disease Count");
                t2Label.setFont(new Font("Tahoma", Font.BOLD, 15));
                t2Label.setHorizontalAlignment(JLabel.CENTER);
                t2panel.add(t2Label, BorderLayout.NORTH);
                
                JScrollPane scrollPane_1 = new JScrollPane(diseaseGroupedTable);
                scrollPane_1.getViewport().setBackground(Color.WHITE);
                t2panel.add(scrollPane_1, BorderLayout.CENTER);
        }
        
        @Override
        public void update(Observable o, Object arg) {  
                if(o instanceof CountTool && (Integer)arg == 1){
                        String[][] cvalues = counttool.getCountTableValues();
                        String[] chead = counttool.getCountTableHead();
                        
                        diseaseCountTable.getTableHeader().setReorderingAllowed(false);
                        diseaseCountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        diseaseCountTable.setModel(new DefaultTableModel(cvalues, chead));                              
                        diseaseCountTable.setCellSelectionEnabled(true);
                        diseaseCountTable.setColumnSelectionAllowed(true);      
                        
                        String[][] gvalues = counttool.getGroupTableValues();
                        String[] ghead = counttool.getGroupTableHead();
                        
                        diseaseGroupedTable.getTableHeader().setReorderingAllowed(false);
                        diseaseGroupedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        diseaseGroupedTable.setModel(new DefaultTableModel(gvalues, ghead));
                        
                        diseaseGroupedTable.setCellSelectionEnabled(true);
                        diseaseGroupedTable.setColumnSelectionAllowed(true);
                }
        }
}