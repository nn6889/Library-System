import java.sql.*;
import java.util.*;

/**
 * Group 1: Carlos Antonio de Oliveira Neto, Anna Wesolowski, Niharika Nakka
 *
 * Course: ISTE 330.01
 *
 * Date: May 18, 2016
 *
 * Description: Papers Class
 * 
 */

public class Papers
{
   /**
      private attributes
   */  
   int id;
   String title;
   String abstractText;
   String citation;
   private ResearchDatabase rd = new ResearchDatabase("jdbc:mysql://127.0.0.1:3307/finalproject", "root", "sip12e12");
   private SqlStatements sqlStatements = new SqlStatements();
   private ArrayList<String> arrayList = new ArrayList<String>();
   
   /**
      Default constructor
   */
   public Papers()
   {
   
   }
   
   /**
      Constructor that accepts and sets the ID
   */
   public Papers(int ID)
   {
      id = ID;
   }
   
   /**
      Constructor that accepts and sets all attributes
   */
   public Papers(int ID, String _title, String _abstract, String _citation)
   {
      id = ID;
      title = _title;
      abstractText = _abstract;
      citation = _citation;
   }
   
   /**
      Method that sets the id
   */
   public void setId(int iD)
   {
      id = iD;
   }
   
   /**
      Method that sets the title
   */
    public void setTitle(String _title)
   {
      title = _title;
   }
   
   /**
      Method that sets the abstract
   */
    public void setAbstract(String _abstract)
   {
      abstractText = _abstract;
   }
   
   /**
      Method that sets the citation
   */
   public void setCitation(String _citation)
   {
      citation = _citation;  
   }
   
   
   
   /**
      Method that gets the id
   */
   public int getId()
   {
      return id;
   }
   
   /**
      Method that gets the title
   */
   public String getTitle()
   {
      return title;
   }
   
   /**
      Method that gets the abstract
   */
   public String getAbstract()
   {
      return abstractText;
   }
   
   /**
      Method that gets the citation
   */
   public String getCitation()
   {
      return citation;
   }
   
  
   /** 
      The fetch method uses the object's id attribute and the Database class' getData method
      to retrieve the databases values for that particular id and update the object's attribute
      values.
      @param sql
      @return true or false
   **/
   public boolean fetch(String sql){
     	rd.connect();
      
     	int value = rd.getData(sql);
      
     	if(value>0){
           	rd.closeMySQL();
           	return true;  
     	}
     	else{
           	rd.closeMySQL();
           	return false;
     	}
   }
   
   
   /**
      The post method inserts the object's attribute values into the database as a new record.
      @param sql
      @return true or false
   **/
   public boolean post(String sql){
      try{
            rd.connect();
            rd.setData(sql);
            rd.closeMySQL();
            return true;
       }
         
       catch(Exception s){
            System.out.print("error"+ s);
            rd.closeMySQL();
            return false;
       }
         
    }
  
  /** Method that updates the database values for that particular equipID
   *  using all the objects attribute values
  **/    
  public boolean put(String sql){
      try{
            rd.connect();
            rd.setData(sql);
            rd.closeMySQL();
            return true;
       }
       catch(Exception e){
            System.out.print("error"+e);
            rd.closeMySQL();
            return false;
       }
         
  }
  
  /**
      The delete method removes from the database any data corresponding to the object's equipmentID.
      @param id
      @param mysqld The MySQL Database being used
   **/
  public boolean delete(String sql){
  
      try{
            rd.connect();
            rd.setData(sql);
            rd.closeMySQL();
            return true;
            
       }
       catch(Exception e){
            System.out.print("error"+e);
            rd.closeMySQL();
            return false;
       }
         
   }


      
}  