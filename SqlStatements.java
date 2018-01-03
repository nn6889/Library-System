import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

/*
 * Group 1: Carlos Antonio de Oliveira Neto, Anna Wesolowski, Niharika Nakka
 *
 * Course: ISTE 330.01
 *
 * Date: May 18, 2016
 *
 * Description: Database Abstraction for Research Db
 * 
 */

public class SqlStatements{
   
   ResearchDatabase rd = new ResearchDatabase("jdbc:mysql://127.0.0.1:3307/finalproject", "root", "sip12e12");
   
   
   public SqlStatements(){
   
   }
   
   public String fetchString(String username, String password){
   
      String fetchStatement = "SELECT email, password FROM faculty WHERE email='"+ username+"' AND password='"+password+ "';";
      
      return fetchStatement;
   }
   
    public String fetchFaculty(String username, String password){
   
      String fetchFaculty = "SELECT * FROM faculty WHERE email='"+ username+"' AND password='"+password+ "';";
      
      return fetchFaculty;
   }
   
   public String updateString(String title, String abstractField, String citation, String id){
      
      String updateStatement = "UPDATE papers SET title ='" + title + "', abstract ='" + abstractField + "', citation ='" + citation + "' WHERE id = "+ id;
      
      return updateStatement;
     
   }
   
   public String insertString(String id, String title, String abstractField, String citation){
     
      String insertStatement = "INSERT INTO papers VALUES ('"+id+"', '" + title + "', '" + abstractField + "', '" + citation + "')";
   
      return insertStatement;
   }
   
   public String deleteString(String id){
   
      String deleteStatement = "DELETE FROM papers WHERE id= " + id;
      
      return deleteStatement;
   }
   
   public String insertKeyword(String id, String keyword){
     
      String insertStatement = "INSERT INTO paper_keywords VALUES ('"+id+"', '" + keyword + "')";
   
      return insertStatement;
   }
   
   public String deleteKeyword(String id){
   
      String deleteStatement = "DELETE FROM paper_keywords WHERE id= " + id;
      
      return deleteStatement;
   }
   
   public String insertAuthorship(String facultyId, String paperId){
	 
  	   String insertStatement = "INSERT INTO authorship VALUES ('"+facultyId+"', '" + paperId + "')";
   
  	   return insertStatement;
   }
   
   public String deleteAuthorship(String paperId){
   
  	   String deleteStatement = "DELETE FROM authorship WHERE paperId = " + paperId;
 	 
      return deleteStatement;
   }
   
   public String selectAuthorship(String facultyId, String paperId){
      String selectStatement = "SELECT * FROM authorship WHERE facultyId=" + facultyId + " AND paperId = " + paperId;
    
      return selectStatement;
   }
   
   public JComboBox getjcombo(JComboBox comboBox){
      
      return comboBox;
   }
   
  public String findPaperKeyword(String paperKeyword){
  
      String valueStatement = "SELECT * FROM papers JOIN paper_keywords USING(id) WHERE keyword= '" + paperKeyword + "'";
      return valueStatement;
  }
  
  public String findTitle(String paperTitle){
  
      String titleStatement = "SELECT * FROM papers JOIN paper_keywords USING(id) WHERE title= '" + paperTitle + "'";
      return titleStatement;
  }
  
  public String findFaculty(String facultyLastName){
      String facultyStatement = "SELECT papers.id, title, citation, abstract, fName, lName FROM papers JOIN authorship JOIN faculty WHERE papers.id=authorship.paperid AND faculty.id = authorship.facultyId AND lName=" + facultyLastName + "'";
      return facultyStatement;
  }
  
  public String fetchPapers(){
   
      String fetchPapers = "SELECT * FROM papers JOIN paper_keywords USING(id)";
      return fetchPapers;
  }

   
}//end of class   