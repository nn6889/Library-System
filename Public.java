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

public class Public{

   /** 
       private attributes
   */
   private String email;
   private String organizationName;
   
   /* Default Constructor */
   public Public(){
      email = "";
      organizationName = "";
   }
   
   /* Constructor with parameters 
      @param _email
      @param _organizationName
   */
   public Public (String _email, String _organizationName){
      email = _email;
      organizationName = _organizationName;
   }
   
   
   /* Email mutator */
   public void setEmail (String _email){
      email = _email;
   }
   
   /* Organization Name mutator */
   public void setOrganizationName (String _organizationName){
      organizationName = _organizationName;
   }
   
   
   /* Email accessor */
   public String getEmail(){
      return email;
   }
   
   /* Organization Name accessor */
   public String getOrganizationName(){
      return organizationName;
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
         
         ArrayList<ArrayList<String>> arrl = rd.getData("select * from public where email = ?", stringValues);
         
         email = arrl.get(0).get(3);
         organizationName = arrl.get(0).get(4);
         
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
   
      if (rd.setData("UPDATE public SET email ='" + email + "', organizationName ='" + organizationName + "' WHERE email = ?", stringValues) == true){
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
      if (rd.setData("INSERT INTO public VALUES ('" + email + "', '" + organizationName + "')") == true){
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
      if (rd.setData("DELETE FROM public WHERE email= '" + email + "'") == true){
         return true;
      }else{
         return false;
      }
   }
}