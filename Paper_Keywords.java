import java.sql.*;
import java.util.*;

/**
 * Group 1: Carlos Antonio de Oliveira Neto, Anna Wesolowski, Niharika Nakka
 *
 * Course: ISTE 330.01
 *
 * Date: May 18, 2016
 *
 * Description: Public class
 * 
*/

public class Paper_Keywords{

   /** 
      private attributes
   */
   private int id;
   private String keyword;
   private ResearchDatabase rd = new ResearchDatabase("jdbc:mysql://127.0.0.1:3307/finalproject", "root", "sip12e12");
   private SqlStatements sqlStatements = new SqlStatements();
   private ArrayList<String> arrayList = new ArrayList<String>();
   
   /* Default Constructor */
   public Paper_Keywords(){
      id = 0;
      keyword = "";
   }
   
   /* Constructor with parameters 
      @param _id
      @param _keyword
   */
   public Paper_Keywords (int _id, String _keyword){
      id = _id;
      keyword = _keyword;
   }
   
   
   /* id mutator */
   public void setID (int _id){
      id = _id;
   }
   
   /* Keyword mutator */
   public void setKeyword (String _keyword){
      keyword = _keyword;
   }
  
   
   /* id accessor */
   public int getID(){
      return id;
   }
   
   /* Keyword accessor */
   public String getKeyword(){
      return keyword;
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
         
      ArrayList<String> value = null;
        
      arrayList.add(Integer.toString(id));
      ArrayList<ArrayList<String>> dataList = rd.getData(sql,value);
         
         if(dataList!=null){
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
  
  /* Method that updates the database values for that particular equipID
   * using all the objects attribute values
  */    
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