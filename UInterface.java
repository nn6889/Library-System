import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Group 1: Carlos Antonio de Oliveira Neto, Anna Wesolowski, Niharika Nakka
 *
 * Course: ISTE 330.01
 *
 * Date: May 18, 2016
 *
 * Description: Interface for Faculty, Student, and Public
 * 
 */

public class UInterface extends JFrame implements ActionListener
{
      /** Attributes */
      private JButton faculty, student, everyone;
      private JPanel welcomePanel;
      private Papers papers = new Papers();
      private Paper_Keywords paper_keywords = new Paper_Keywords();
      ResearchDatabase rd;
      private JFrame frame;
      private JTextField jtfUsername , jtfPassword, jtfId, jtfTitle, jtfAbstract, jtfCitation, jtfKeyword;
      private SqlStatements sqlStatementsClass = new SqlStatements();
      private String fetchResult = null;
      private String updateResult = null;
      private String insertResult = null;
      private String deleteResult = null;
      private String insertKeyword = null;
      private String deleteKeyword = null;
      private String insertAuthorship = null;
      private String deleteAuthorship = null;
      private String selectAuthorship = null;
      private String fetchPapers = null;
      private String findKeywords = null;
      private JTable table;
      private String x = null;
   
      /** Constructor of the class */
      public UInterface(ResearchDatabase _rd) 
      {
         add( new JLabel("Welcome", JLabel.CENTER), BorderLayout.NORTH );
         welcomePanel = new JPanel();
         BoxLayout boxLayout = new BoxLayout(welcomePanel, BoxLayout.Y_AXIS);
         
         faculty = new JButton("Faculty");
         faculty.addActionListener(this);
         
         student = new JButton("Student");
         student.addActionListener(this);
         
         everyone = new JButton("Public");
         everyone.addActionListener(this);
         
         faculty.setAlignmentX(Component.CENTER_ALIGNMENT);
         student.setAlignmentX(Component.CENTER_ALIGNMENT);
         everyone.setAlignmentX(Component.CENTER_ALIGNMENT);

         welcomePanel.setLayout(boxLayout);
         
         welcomePanel.add(faculty);
         welcomePanel.add(student);
         welcomePanel.add(everyone);
         
         add(welcomePanel);
         
         setLocationRelativeTo(null);
         pack();
         setSize(200,150);
         setVisible(true);
         
         /** Database class needed */
         rd = _rd;
         
      }
      
      /** Action Event */
      public void actionPerformed(ActionEvent ae)
      {
       Object choice = ae.getSource();
       
       /** If a person chooses faculty */
       if(choice==faculty){
       
            JFrame jf = new JFrame();
            add( new JLabel("Faculty Login", JLabel.CENTER), BorderLayout.NORTH );
       
            JPanel jpTop = new JPanel(new GridLayout(0,2)); 
                  
            jpTop.add( new JLabel("Username: ", JLabel.RIGHT)); 
            jtfUsername = new JTextField(15); 
            jpTop.add(jtfUsername);
      
            jpTop.add( new JLabel("Password: ", JLabel.RIGHT)); 
            jtfPassword = new JPasswordField(15); 
            jpTop.add(jtfPassword);
      
            jf.add(jpTop, BorderLayout.CENTER);
            
            JPanel jpControls = new JPanel();
      
            JButton jbLogin = new JButton("Login");
  
            jbLogin.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent e){
               
               String username = jtfUsername.getText();
               String password = rd.enctryptPass(jtfPassword.getText());
               
               String fetchResult = sqlStatementsClass.fetchString(username, password);
               String fetchFaculty = sqlStatementsClass.fetchFaculty(username, password);

               Faculty faculty = new Faculty();
               
               /** Validates login */
               if(faculty.fetchLogin(fetchResult, rd) == true)
               {
                  faculty.fetch(fetchFaculty, rd);
                  
                  frame = new JFrame();
                  JPanel main = new JPanel(new BorderLayout());
                  JPanel bottomPanel = new JPanel(new GridLayout(0,2));
                  JPanel topPanel = new JPanel(new FlowLayout());
                  
                  fetchPapers = sqlStatementsClass.fetchPapers();
                   
                  JTable table = new JTable();
                  table.setModel(rd.resultSetToTableModel(fetchPapers));
                  
                  /** Show the papers */
                  table.addMouseListener(new java.awt.event.MouseAdapter(){
                     public void mouseClicked(java.awt.event.MouseEvent evt){
                       int i = table.getSelectedRow();
                       if (i >= 0){
                          jtfId.setText(rd.resultSetToTableModel(fetchPapers).getValueAt(i,0).toString());
                          jtfTitle.setText(rd.resultSetToTableModel(fetchPapers).getValueAt(i,1).toString());
                          jtfAbstract.setText(rd.resultSetToTableModel(fetchPapers).getValueAt(i,2).toString());
                          jtfCitation.setText(rd.resultSetToTableModel(fetchPapers).getValueAt(i,3).toString());
                          jtfKeyword.setText(rd.resultSetToTableModel(fetchPapers).getValueAt(i,4).toString());
                       }
                     }
                   });
                  
                  table.setPreferredScrollableViewportSize(new Dimension(500, 50));
                  table.setFillsViewportHeight(true);
                   
                  JScrollPane scrollPane = new JScrollPane(table);
                  topPanel.add(scrollPane);
                  
                  bottomPanel.add(new JLabel("Id: ", JLabel.RIGHT));
                  jtfId = new JTextField(12);
                  bottomPanel.add(jtfId);
                  
                  bottomPanel.add(new JLabel("Title: ", JLabel.RIGHT));
                  jtfTitle = new JTextField(15);
                  bottomPanel.add(jtfTitle);
                  
                  bottomPanel.add(new JLabel("Abstract: ", JLabel.RIGHT));
                  jtfAbstract = new JTextField(15);
                  bottomPanel.add(jtfAbstract);
                  
                  bottomPanel.add(new JLabel("Citation: ", JLabel.RIGHT));
                  jtfCitation = new JTextField(15);
                  bottomPanel.add(jtfCitation);
                  
                  bottomPanel.add(new JLabel("Keyword: ", JLabel.RIGHT));
                  jtfKeyword = new JTextField(15);
                  bottomPanel.add(jtfKeyword);
                 
                  main.add(bottomPanel, BorderLayout.WEST);
                  main.add(topPanel, BorderLayout.NORTH);
                  
                  JPanel rightPanel = new JPanel();
                  rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
                  
                  /** Updates a selected paper */
                  JButton update = new JButton("Update");
                  update.addActionListener(new ActionListener(){
                     public void actionPerformed(ActionEvent ae){
                     
                        updateResult = sqlStatementsClass.updateString(jtfTitle.getText(), jtfAbstract.getText(), jtfCitation.getText(), jtfId.getText());
                        selectAuthorship = sqlStatementsClass.selectAuthorship(Integer.toString(faculty.getId()), jtfId.getText());
                        
                        System.out.println(papers.fetch(selectAuthorship));
                        
                        if (papers.fetch(selectAuthorship) == true){
                           papers.put(updateResult);
                           table.setModel(rd.resultSetToTableModel(fetchPapers));
                        }else{
                           JOptionPane.showMessageDialog(null, "You are not allowed to do that!");
                        }
                  
                     }
                  });
                  
                  /** Insert a new paper */
                  JButton insert = new JButton("Insert");
                   
                  insert.addActionListener(new ActionListener(){
                     public void actionPerformed(ActionEvent ae){
                     
                     insertKeyword = sqlStatementsClass.insertKeyword(jtfId.getText(), jtfKeyword.getText());
                     insertResult = sqlStatementsClass.insertString(jtfId.getText(), jtfTitle.getText(), jtfAbstract.getText(), jtfCitation.getText());   
                     insertAuthorship = sqlStatementsClass.insertAuthorship(Integer.toString(faculty.getId()), jtfId.getText());
                        if(papers.fetch(insertResult) == false){
                           papers.post(insertResult);
                           papers.post(insertAuthorship);
                        }
                        
                        paper_keywords.post(insertKeyword); 
                        
                        table.setModel(rd.resultSetToTableModel(fetchPapers));     
                     }
                  });

                  /** Deletes a selected paper */
                  JButton delete = new JButton("Delete");
                  delete.addActionListener(new ActionListener(){
                     public void actionPerformed(ActionEvent ae){
                        deleteKeyword = sqlStatementsClass.deleteKeyword(jtfId.getText());
                        deleteResult = sqlStatementsClass.deleteString(jtfId.getText());
                        deleteAuthorship = sqlStatementsClass.deleteAuthorship(jtfId.getText());
                        selectAuthorship = sqlStatementsClass.selectAuthorship(Integer.toString(faculty.getId()), jtfId.getText());
                        
                        if (papers.fetch(selectAuthorship) == true){
                           papers.delete(deleteAuthorship);
                           paper_keywords.delete(deleteKeyword);
                           papers.delete(deleteResult);
                           table.setModel(rd.resultSetToTableModel(fetchPapers));
                        }else{
                           JOptionPane.showMessageDialog(null, "You are not allowed to do that!");
                        }
                        
                        
                     }
                  });
                  
                  /** Ends the program */
                  JButton exit = new JButton("Exit");
                  exit.addActionListener(new ActionListener(){
                     public void actionPerformed(ActionEvent ae){
                        System.exit(0);
                     }
                  });
                  
                  rightPanel.add(update);
                  rightPanel.add(insert);
                  rightPanel.add(delete);
                  rightPanel.add(exit);
                  main.add(rightPanel, BorderLayout.EAST);
                  
                  frame.setLocationRelativeTo(null);
                  frame.add(main);
                  frame.pack();
                  frame.setVisible(true);
                
               /** Login failed */   
               }else{
                  JOptionPane.showMessageDialog (null, "ERROR! \n Incorrect username/password. Try again", "Login Credentials", JOptionPane.INFORMATION_MESSAGE);	 
               }
             }});
            
            /** Ends the program */ 
            JButton jbExit = new JButton("Exit");
            jbExit.addActionListener(new ActionListener(){ 
               public void actionPerformed(ActionEvent e){
                  System.exit(0);
               }
             });
      
            jpControls.add(jbLogin);
            jpControls.add(jbExit);
            
            jf.setLocationRelativeTo(null);
            jf.add(jpControls, BorderLayout.SOUTH);
            jf.pack();
            jf.setVisible(true);
         
         }
         
         
       /** If a person either chooses a student or public */
       if(choice==student||choice==everyone){
       
            JFrame jf = new JFrame();
       
            if(choice==student){
               jf.add(new JLabel("Find paper based on: ", JLabel.CENTER), BorderLayout.NORTH);
            }
            else { 
               jf.add(new JLabel("Find speaker based on: ", JLabel.CENTER), BorderLayout.NORTH);
            }
            
            JPanel panel = new JPanel(new GridLayout(0,2));
            JComboBox optionList = new JComboBox();
            
            ArrayList<String> keywords = rd.getKeywords();
            
            optionList.addItem("-");
            
            for (int i=0; i<keywords.size(); i++){
               optionList.addItem(keywords.get(i));
            }
            
            panel.add( new JLabel("Keyword: ", JLabel.RIGHT)); 
            panel.add(optionList);
            
            panel.add(new JLabel("Title: ", JLabel.RIGHT));
            JTextField titleTextField = new JTextField(15); 
            panel.add( titleTextField);
            
            panel.add(new JLabel("Faculty: ", JLabel.RIGHT));
            JTextField facultyTextField = new JTextField(15); 
            panel.add( facultyTextField);
            
            JPanel jpBottom = new JPanel();
      
            /** Show the options to choose to show papers */
            JButton jbFind = new JButton("Find");
            jbFind.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent e){
               
                  String value = optionList.getSelectedItem().toString();
                  
                  if (!value.equals("-") || !(titleTextField.getText()).equals("") || !(facultyTextField.getText()).equals("")){
                  
                    if (!value.equals("-")){   
                        titleTextField.setEditable(false);
                        facultyTextField.setEditable(false);
                        x = sqlStatementsClass.findPaperKeyword(value);
                     }else if (!titleTextField.getText().equals("")){
                        optionList.setEnabled(false);
                        optionList.setEditable(false);
                        optionList.setSelectedIndex(-1);
                        facultyTextField.setEditable(false);
                        x = sqlStatementsClass.findTitle(titleTextField.getText());
                     }else if (!facultyTextField.getText().equals("")){
                        optionList.setEnabled(false);
                        optionList.setEditable(false);
                        optionList.setSelectedIndex(-1);
                        titleTextField.setEditable(false);
                        x = sqlStatementsClass.findTitle(facultyTextField.getText());
                     }
                     
                     JFrame jFrame = new JFrame();
                     JPanel topPanel = new JPanel(new FlowLayout());
                      
                     JTable table = new JTable();
                      
                     table.setModel(rd.resultSetToTableModel(x));
                      
                     table.setPreferredScrollableViewportSize(new Dimension(500, 50));
                     table.setFillsViewportHeight(true);
                      
                     JScrollPane scrollPane = new JScrollPane(table);
                     topPanel.add(scrollPane);
                      
                     JPanel bottom = new JPanel(new BorderLayout());
                     JButton ok = new JButton("OK");
                     
                     /** Show selected paper(s) */ 
                     ok.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent aae){
                           JFrame j = new JFrame();
                           JPanel jp = new JPanel();
                           
                           int i = table.getSelectedRow();
                           
                           /** If no paper is selected */
                           if (i == -1){
                              JOptionPane.showMessageDialog(null, "Select One Paper", "ALERT!", JOptionPane.INFORMATION_MESSAGE);
                           }else{
                           
                              JTextArea jta = new JTextArea(20,20);
                              
                              jta.setText(rd.resultSetToTableModel(x).getValueAt(i, 2).toString());
                              
                              jta.setEditable(false);
                              
                              jp.add(jta);
                              j.add(jp);
                              j.setLocationRelativeTo(null);
                              j.pack();
                              j.setVisible(true);  
                           }
                        }
                       });
                      
                      bottom.add(ok);
                      jFrame.add(bottom, BorderLayout.SOUTH);
                      
                     jFrame.setLocationRelativeTo(null);
                     jFrame.add(topPanel);
                     jFrame.pack();
                     jFrame.setVisible(true);
                     
                /** If no search option is selected */
                }else{
                  JOptionPane.showMessageDialog(null, "Select one method of search");
                }
               }
             });
            /** Ends the program */ 
            JButton jbExit = new JButton("Exit");
            jbExit.addActionListener(new ActionListener(){ 
               public void actionPerformed(ActionEvent e){
                  System.exit(0);
               }
             });

      
            jpBottom.add(jbFind);
            jpBottom.add(jbExit);
            
            jf.add(jpBottom, BorderLayout.SOUTH);
            
            jf.setLocationRelativeTo(null);
            jf.add(panel);
            jf.pack();
            jf.setVisible(true);
         }
      }
      
      }