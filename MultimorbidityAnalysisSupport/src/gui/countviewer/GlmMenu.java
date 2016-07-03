package gui.countviewer;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import domain.Column;
import domain.tools.counttool.CountTool;
import domain.tools.datatool.DataTool;
import gui.ErrorDialog;
import gui.InfoDialog;
import gui.MListSelectionModel;
import util.MultimorbidityException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
/**
 * The menu panel to the glm viewer panel.
 * @author ABI team 37
 * @version 1.0
 */
public class GlmMenu extends JPanel implements Observer{
        private JFrame mainframe;
        private DataTool datatool;
        private CountTool counttool;
        private String[] columns;
        
        private JButton calcButton;
        private JComboBox<String> columnsComboBox;
        private JList<String> columnList;
        
        /**
         * Constructor.
         * @param mainframe the main JFrame to use
         * @param datatool the datatool instance to use
         * @param counttool the counttool instance to use
         */
        public GlmMenu(final JFrame mainframe, DataTool datatool, CountTool counttool) {
                this.setLayout(null);
                this.setPreferredSize(new Dimension(300,700));
                this.setSize(new Dimension(300,700));
                
                this.mainframe = mainframe;
                this.datatool = datatool;
                this.counttool = counttool;
                
                datatool.addObserver(this);
                
                JLabel mainLabel = new JLabel("GLM Analysis");
                mainLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
                mainLabel.setBounds(10, 11, 280, 24);
                add(mainLabel);
                
                JButton infoButton = new JButton("INFO");
                infoButton.setBounds(200, 2, 75, 20);
                add(infoButton);
                infoButton.addActionListener(new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                                new InfoDialog(mainframe,new File("resources/glm.txt"));        
                        }});
                
                setupStep1();
                setupStep2();
                setupStep3();
                
                updateComboboxes();
                checkbutton();
                setToolTips();
        }
        


        private void setupStep2(){
                JPanel step2panel = new JPanel();
                step2panel.setBorder(new LineBorder(new Color(0, 0, 0)));
                step2panel.setBounds(10, 222, 280, 210);
                add(step2panel);
                step2panel.setLayout(null);
                
                JLabel lblNewLabel_1 = new JLabel("Step 2.");
                lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
                lblNewLabel_1.setBounds(10, 10, 260, 14);
                step2panel.add(lblNewLabel_1);
                
                JLabel lblNewLabel_2 = new JLabel("Select the Other Dependencies(factor) Columns.");
                lblNewLabel_2.setBounds(10, 25, 260, 14);
                step2panel.add(lblNewLabel_2);
                
                JLabel lblIfNoColumns = new JLabel("If no columns are visible, convert columns");
                lblIfNoColumns.setForeground(Color.RED);
                lblIfNoColumns.setBounds(10, 40, 260, 14);
                step2panel.add(lblIfNoColumns);
                
                JLabel lblToLogicalType = new JLabel("to logical type by using the Edit Data Menu.");
                lblToLogicalType.setForeground(Color.RED);
                lblToLogicalType.setBounds(10, 55, 260, 14);
                step2panel.add(lblToLogicalType);
                
                JLabel lblNewLabel_3 = new JLabel("Select Other Factor Columns");
                lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
                lblNewLabel_3.setBounds(10, 88, 260, 14);
                step2panel.add(lblNewLabel_3);
                
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setBounds(10, 105, 260, 93);
                step2panel.add(scrollPane);
                
                columnList = new JList<String>();
                columnList.setSelectionModel(new MListSelectionModel());
                scrollPane.setViewportView(columnList);
                columnList.setToolTipText("No Columns visible, use Edit Data Menu to convert Columns");
                columnList.addListSelectionListener(new ListSelectionListener(){

                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                                checkbutton();  
                        }});
        }
        
        private void setupStep1(){
                JPanel step1panel = new JPanel();
                step1panel.setBorder(new LineBorder(new Color(0, 0, 0)));
                step1panel.setBounds(10, 46, 280, 165);
                add(step1panel);
                step1panel.setLayout(null);
                
                JLabel lblStep = new JLabel("Step 1.");
                lblStep.setFont(new Font("Tahoma", Font.BOLD, 11));
                lblStep.setBounds(10, 10, 260, 14);
                step1panel.add(lblStep);
                
                JLabel lblSelectTheFactor = new JLabel("Select a Dependant(factor) Column.");
                lblSelectTheFactor.setBounds(10, 25, 260, 14);
                step1panel.add(lblSelectTheFactor);
                
                JLabel lblTheCountedValues = new JLabel("Only Factor Columns are displayed.");
                lblTheCountedValues.setBounds(10, 40, 260, 14);
                step1panel.add(lblTheCountedValues);
                
                JLabel label_2 = new JLabel("If no columns are visible, convert columns");
                label_2.setForeground(Color.RED);
                label_2.setBounds(10, 55, 260, 14);
                step1panel.add(label_2);
                
                JLabel lblToFactorType = new JLabel("to factor type by using the Edit Data Menu");
                lblToFactorType.setForeground(Color.RED);
                lblToFactorType.setBounds(10, 70, 260, 14);
                step1panel.add(lblToFactorType);
                
                columnsComboBox = new JComboBox<String>();
                columnsComboBox.setBounds(10, 135, 260, 20);
                step1panel.add(columnsComboBox);
                columnsComboBox.addActionListener(new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                                setupList();
                                
                        }});
                
                JLabel lblNewLabel_4 = new JLabel("Select Dependant Column");
                lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
                lblNewLabel_4.setBounds(10, 117, 260, 14);
                step1panel.add(lblNewLabel_4);
        }       
        
        private void setupStep3(){
                JPanel step2panel = new JPanel();
                step2panel.setBorder(new LineBorder(new Color(0, 0, 0)));
                step2panel.setBounds(10, 443, 280, 75);
                add(step2panel);
                step2panel.setLayout(null);
                
                JLabel lblStep_1 = new JLabel("Step 3.");
                lblStep_1.setFont(new Font("Tahoma", Font.BOLD, 11));
                lblStep_1.setBounds(10, 11, 260, 14);
                step2panel.add(lblStep_1);
                
                calcButton = new JButton("Nothing Selected");
                calcButton.setEnabled(false);
                calcButton.setBounds(10, 36, 260, 23);
                step2panel.add(calcButton);
                calcButton.addActionListener(new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                                calculateAction();
                                
                        }});
        }
        
        private void calculateAction(){
                final List<String> tothers = columnList.getSelectedValuesList();
                final String dependent = (String)columnsComboBox.getSelectedItem();
                
                if(tothers == null || tothers.size() <1 || dependent == null){
                        new ErrorDialog(mainframe, "No or not enough columns selected. Select more columns. If no columns are visable use the Edit Menu to convert columns to suitable type.");
                        return;
                }
                
                
                final JDialog frame = new JDialog(mainframe);
            final JProgressBar progressBar = new JProgressBar();
            progressBar.setIndeterminate(true);
            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().add(new JLabel("Loading..."), BorderLayout.NORTH);
            frame.getContentPane().add(progressBar, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(mainframe);
            frame.setVisible(true);
            
            Runnable runnable = new Runnable() {
                public void run() {
                                String[] others = tothers.toArray(new String[tothers.size()]);
                                try {
									counttool.executeGLM(dependent,others);
								} catch (MultimorbidityException e) {
									new ErrorDialog(mainframe,e.getMessage());
								} 
                                frame.setVisible(false);
                    frame.dispose();
                }
            };
            new Thread(runnable).start();   
        }
        
        private void updateComboboxes(){
                this.columns = datatool.getDataSetColumnnames(Column.Type.toType("factor"));
                
                
                columnsComboBox.removeAllItems();
                for(String s: columns){
                        columnsComboBox.addItem(s);
                }       

                setupList();    
        }
        
        private void setupList(){
                String dependent = (String)columnsComboBox.getSelectedItem();
                String[] tcolumns = this.columns.clone();
                if(dependent != null && tcolumns != null){
                        for (int i = 0; i < tcolumns.length; i++)
                        {
                            if (tcolumns[i] != null && tcolumns[i].equals(dependent))
                            {
                                tcolumns[i] = null;
                                break;
                            }
                        }
                        DefaultListModel<String> dlm = new DefaultListModel<>();
                        for(String s: tcolumns){
                                if(s != null){
                                        dlm.addElement(s);
                                }       
                        }
                        columnList.setModel(dlm);
                }
        }
        
        private void checkbutton(){
                List<String> list = columnList.getSelectedValuesList();
                
                if(list != null && list.size() > 0){            
                        calcButton.setEnabled(true);
                        calcButton.setText("Calculate");
                        }else{
                                calcButton.setEnabled(false);
                                calcButton.setText("Nothing Selected");
                        }
        }
        
        private void setToolTips(){
                ListModel<String> model = columnList.getModel();
                if(model.getSize() >0){
                        columnList.setToolTipText("Select multiple Columns");
                }else{
                        columnList.setToolTipText("No Columns visible, use Edit Data Menu to convert Columns");
                }
        }
        
        @Override
        public void update(Observable o, Object arg) {
                updateComboboxes();
                checkbutton();
                setToolTips();
        }
}

