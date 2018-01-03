import java.sql.*;
import java.util.*;

/**
 * Group 1: Carlos Antonio de Oliveira Neto, Anna Wesolowski, Niharika Nakka
 *
 * Course: ISTE 330.01
 *
 * Date: May 18, 2016
 *
 * Description: Student Class
 * 
 */

public class Student {
   
   /** 
      Private attributes 
   */
   private String email;
   private String fName;
   private String lName;
   
   public Student(){
      email = "";
      fName = "";
      lName = "";
   }
   
   /**
      Parameterized constructor
      @param _email
      @param _fName
      @param _lName   
   */
   public Student(String _email, String _fName, String _lName){
      email = _email;
      fName = _fName;
      lName = _lName;
   }
   
   /**
      Email mutator
   */
   public void setEmail(String _email){
      email = _email;
   }  
   
   /**
      First Name mutator
   */
   public void setFirstName(String _fName){
      fName = _fName;
   }
   
   /**
      Last Name mutator
   */
   public void setLastName(String _lName){
      lName = _lName;
   }
   
   /**
      Email accessor
   */
   public String getEmail(){
      return email;
   }
   
   /**
      First Name accessor
   */
   public String getFirstName(){
      return fName;
   }
   
   /**
      Last Name accessor
   */
   public String getLastName(){
      return lName;
   }
   
   /** 
      The fetch method uses the object's id attribute and the Database class' getData method
      to retrieve the databases values for that particular id and update the object's attribute
      values.
      @param id
      @param mysqld The MySQL Database being used
      @return true or false
   **/
   public boolean fetch(String id, ResearchDatabase rd){
      try{
         /* Creates an ArrayList of string values to be used with the sql statement */
         ArrayList<String> stringValues = new ArrayList<String>();
         stringValues.add(id);
         
         /* Creates an ArrayList of an ArrayList of Strings to retrieve the date from the SQL statement and then
         update the Equipment object's attribute values. At the end, it prints the update attribute values */
         
         ArrayList<ArrayList<String>> arrl = rd.getData("select * from student where email = ?", stringValues);
         
         email = arrl.get(0).get(4);
         fName = arrl.get(0).get(5);
         lName = arrl.get(0).get(6);
         
      }catch (IndexOutOfBoundsException ioobe){
         System.out.println("Error: " + ioobe); 
         return false; 
      }
      
      return true;
   }
   
   /**
      The put method updates the databases values according to the given id, using all the object's
      attribute values.
      @param id
      @param mysqld The MySQL Database being used
      @return true or false
   **/
   public boolean put(String id, ResearchDatabase rd){
      /* Creates an ArrayList of string values to be used with the sql statement */
      ArrayList<String> stringValues = new ArrayList<String>();
      stringValues.add(id);
   
      if (rd.setData("UPDATE student SET email ='" + email + "', fName ='" + fName + "', lName ='" + lName + "' WHERE email = ?", stringValues) == true){
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
      if (rd.setData("INSERT INTO student VALUES ('" + email + "', '" + fName + "', '" + lName + "')") == true){
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
      if (rd.setData("DELETE FROM student WHERE email= '" + email + "'") == true){
         return true;
      }else{
         return false;
      }
   }
}