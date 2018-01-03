import java.sql.*;
import java.util.*;

/**
 * Group 1: Carlos Antonio de Oliveira Neto, Anna Wesolowski, Niharika Nakka
 *
 * Course: ISTE 330.01
 *
 * Date: May 18, 2016
 *
 * Description: Faculty Class
 * 
 */

public class Faculty
{
   /**
      private attributes
   */  
   private int id;
   private String fName;
   private String lName;
   private String password;
   private String email;
   
   /**
      Default constructor
   */
   public Faculty()
   {
   
   }
   
   /**
      Constructor that accepts and sets the ID
   */
   public Faculty(int ID)
   {
      id = ID;
   }
   
   /**
      Constructor that accepts and sets all attributes
   */
   public Faculty(int ID, String firstName, String lastName, String pswrd, String mail)
   {
      id = ID;
      fName = firstName;
      lName = lastName;
      password = pswrd;
      email = mail;
   }
   
   /**
      Method that sets the id
   */
   public void setId(int iD)
   {
      id = iD;
   }
   
   /**
      Method that sets the firstName
   */
    public void setfName(String firstName)
   {
      fName = firstName;
   }
   
   /**
      Method that sets the lastName
   */
    public void setlName(String lastName)
   {
      lName = lastName;
   }
   
   /**
      Method that sets the password
   */
   public void setPassword(String pswrd)
   {
      password = pswrd;
   }
   
   /**
      Method that sets the email
   */
   public void setEmail(String mail)
   {
      email = mail;
   }
   
   /**
      Method that gets the id
   */
   public int getId()
   {
      return id;
   }
   
   /**
      Method that gets the firstName
   */
   public String getfName()
   {
      return fName;
   }
   
   /**
      Method that gets the lastName
   */
   public String getlName()
   {
      return lName;
   }
   
   /**
      Method that gets the password
   */
   public String getPassword()
   {
      return password;
   }
   
   /**
      Method that gets the email
   */
   public String getEmail()
   {
      return email;
   }
   
   /** 
      The fetch method uses the object's id attribute and the Database class' getData method
      to retrieve the databases values for that particular id and update the object's attribute
      values.
      @param sqlStatement
      @param rd The MySQL Database being used
      @return true or false
   **/
   public boolean fetch(String sqlStatement, ResearchDatabase rd){
   
      try{
        
         ArrayList<ArrayList<String>> dataList = rd.getData2(sqlStatement);
         
         id = Integer.parseInt(dataList.get(0).get(0));
         fName = dataList.get(0).get(1);
         lName = dataList.get(0).get(2);
         password = dataList.get(0).get(3);
         email = dataList.get(0).get(4);
         
      }catch (IndexOutOfBoundsException ioobe){
         System.out.println("Error: " + ioobe); 
         return false; 
      }
      
      return true;
   }
   
   /** 
      The fetch method uses the object's id attribute and the Database class' getData method
      to retrieve the databases values for that particular id and update the object's attribute
      values.
      @param id
      @param mysqld The MySQL Database being used
      @return true or false
   **/
   public boolean fetchLogin(String sqlStatement, ResearchDatabase rd){
      
      int test;
   
      try{
          
         test = rd.getData(sqlStatement);
         
      }catch (IndexOutOfBoundsException ioobe){
         System.out.println("Error: " + ioobe); 
         return false; 
      }
      
      if (test>0){
         return true;
      }else{
         return false;
      }
   }
   
   /**
      The put method updates the databases values according to the given id, using all the object's
      attribute values.
      @param id
      @param mysqld The MySQL Database being used
      @return true or false
   **/
   public boolean put(int id, ResearchDatabase rd){
      /* Creates an ArrayList of string values to be used with the sql statement */
      ArrayList<String> stringValues = new ArrayList<String>();
      stringValues.add(Integer.toString(id));
   
      if (rd.setData("UPDATE faculty SET id =" + id + ", fName ='" + fName + "', lName ='" + lName + "', password ='" + password + "', email ='" + email + "' WHERE email = ?", stringValues) == true){
         return true;
      }else{
         return false;
      }
   }
   
   /**
      The post method inserts the object's attribute values into the database as a new record.
      @param mysqld The MySQL Database being used
      @return true or false
   **/
   public boolean post(ResearchDatabase rd){
      if (rd.setData("INSERT INTO student VALUES (" + id + ", '" + fName + "', '" + lName + "', '" + password + "', '" + email + "')") == true){
         return true;
      }else{
         return false;
      }
   }
   
   /**
      The delete method removes from the database any data corresponding to the object's equipmentID.
      @param id
      @param mysqld The MySQL Database being used
   **/
   public boolean delete(String id, ResearchDatabase rd){
      if (rd.setData("DELETE FROM faculty WHERE id= " + id) == true){
         return true;
      }else{
         return false;
      }
   }
   
   
}  