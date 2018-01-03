import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;  
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Group 1: Carlos Antonio de Oliveira Neto, Anna Wesolowski, Niharika Nakka
 *
 * Course: ISTE 330.01
 *
 * Date: May 18, 2016
 *
 * Description: Database Abstraction for Research Db
 * 
 */
 
public class ResearchDatabase {
   //attributes
   String uri;
   String user;
   String password;
   Connection conn = null;
   
   /* 
      Constructor that sets database, user, and password
      @param _uri
      @param _user
      @param _password
   */
   public ResearchDatabase(String _uri, String _user, String _password){
      uri = _uri;
      user = _user;
      password = _password;
   }
   
   /*
      Connect method that opens database
   */
   public boolean connect(){
      try{
         conn = DriverManager.getConnection(uri, user, password);
      }
      catch(SQLException sqle){
         System.out.println("Could not connect to db: " + uri);
      }
      return true;
   }
   
    /*
      Close method that closes database
   */
   public boolean closeMySQL(){
      try{
         conn.close();
      }
      catch(SQLException sqle){
         System.out.println("Could not close db");
         return false;  
      }
      return true;
   }
   
   /** 
      getData accepts a SQL string. Then this method returns a 2-d
      ArrayList. This will be used for doing "SELECT" sql statements.
      @param sqlStrign The sql select statement
   **/
   public ArrayList<ArrayList<String>> getData (String sqlString, ArrayList<String> stringValues){
      /* Creates a Statement object using the prepare method */
      PreparedStatement stmnt = prepare(sqlString, stringValues);
      
      ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
      
      /* Try to connect to the database */
      if(connect() == true){
         System.out.println("Connected");
      }
      else{
         System.out.println("Not connected");
      }
      
      try{
         /* Try to execute successfully the select statement. */
         ResultSet rs = null;
         if (stmnt == null){
            Statement stmnt2 = conn.createStatement();
            rs = stmnt2.executeQuery(sqlString);
         }else{
            rs = stmnt.executeQuery();
         } 
        
         /* Use Metadata do determine the number of fields requested */
         ResultSetMetaData rsmd = rs.getMetaData();
         int numFields = rsmd.getColumnCount();
         
         /* While there are rows... */
         while(rs.next()){
            /* Creates a temporary 1-d ArrayList of Strings to catch the attribute values */
            ArrayList<String> partialResult = new ArrayList<String>();
            
            /* the first row to be added contains the column names. */
            for (int j=1; j<=numFields; j++){
              partialResult.add(rsmd.getColumnLabel(j));
            }
            
            partialResult.add("\n");
            
            /* Add the attribute values to the 1-d ArrayList */
            for (int i=1; i<=numFields; i++){
               partialResult.add(rs.getString(i));
            }
            
            /* Add the 1-d ArrayList of Strings to the 2-d ArrayList */
            result.add(partialResult);
         }
      }catch(SQLException sqle){
         System.out.println("Error5: " + sqle);
         return null;
      }
      
      /* Try to close the database */
      if(closeMySQL() == true){
         System.out.println("Connection closed");
      }
      else{
         System.out.println("Connection is not closed");
      }
      
      /* Returns the final 2-d ArrayList */
      return result;

   }
   
   /** 
      getData accepts a SQL string. Then this method returns a 2-d
      ArrayList. This will be used for doing "SELECT" sql statements.
      @param sqlStrign The sql select statement
   **/
   public ArrayList<ArrayList<String>> getData2 (String sqlString){
      ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
      
      /* Try to connect to the database */
      if(connect() == true){
         System.out.println("Connected");
      }
      else{
         System.out.println("Not connected");
      }
      
      try{
         /* Try to execute successfully the select statement. */
         ResultSet rs = null;
         Statement stmnt = conn.createStatement();
         rs = stmnt.executeQuery(sqlString); 
        
         /* Use Metadata do determine the number of fields requested */
         ResultSetMetaData rsmd = rs.getMetaData();
         int numFields = rsmd.getColumnCount();
         
         /* While there are rows... */
         while(rs.next()){
            /* Creates a temporary 1-d ArrayList of Strings to catch the attribute values */
            ArrayList<String> partialResult = new ArrayList<String>();
            
            /* Add the attribute values to the 1-d ArrayList */
            for (int i=1; i<=numFields; i++){
               partialResult.add(rs.getString(i));
            }
            
            /* Add the 1-d ArrayList of Strings to the 2-d ArrayList */
            result.add(partialResult);
         }
      }catch(SQLException sqle){
         System.out.println("Error2: " + sqle);
         return null;
      }
      
      /* Try to close the database */
      if(closeMySQL() == true){
         System.out.println("Connection closed");
      }
      else{
         System.out.println("Connection is not closed");
      }
      
      /* Returns the final 2-d ArrayList */
      return result;

   }
   
   /** 
      getData accepts a SQL string.
      @param sqlString The sql select statement
   **/
   public int getData (String sqlString){
   
      int counter = 0;
      
      /* Try to connect to the database */
      if(connect() == true){
         System.out.println("Connected");
      }
      else{
         System.out.println("Not connected");
      }
      
      try{
         /* Try to execute successfully the select statement. */
         Statement stmnt = conn.createStatement();
         ResultSet rs = stmnt.executeQuery(sqlString);
         
         /*if (rs.last()){
            counter = rs.getRow();
            rs.beforeFirst();
         }*/
         
         while (rs.next()){
            counter++;
         }
         
      }catch(SQLException sqle){
         System.out.println("Error3: " + sqle);
         return 0;
      }
      
      /* Try to close the database */
      if(closeMySQL() == true){
         System.out.println("Connection closed");
      }
      else{
         System.out.println("Connection is not closed");
      }
      
      return counter;

   }
   
   public TableModel resultSetToTableModel(String sqlString) {
        try {
            connect();
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(sqlString);

            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector rows = new Vector();

            while (rs.next()) {
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

   
   public ArrayList<String> getKeywords(){
      ArrayList<String> keywords = new ArrayList<String>();
      String SQL = "SELECT keyword from paper_keywords";
      
      try{
         connect();
         Statement stmnt = conn.createStatement();
         ResultSet rs = stmnt.executeQuery(SQL);
         
         while(rs.next()){
            keywords.add(rs.getString(1));
         }
      }catch (SQLException sqle){
         System.out.println("Error: " + sqle);
      }
      
      return keywords;
   }
   
   public String enctryptPass (String password){
      
      String encryptedPassword = null;
      
      /* Try to connect to the database */
      if(connect() == true){
         System.out.println("Connected");
      }
      else{
         System.out.println("Not connected");
      }
      
      try{
         Statement stmnt = conn.createStatement();
         ResultSet rs = stmnt.executeQuery("SELECT MD5('"+password+"')");
         
         while (rs.next()){
            encryptedPassword = rs.getString(1);
         }
      }catch (SQLException sqle){
         System.out.println("Error4: " + sqle);
         return null;
      }
      
      /* Try to close the database */
      if(closeMySQL() == true){
         System.out.println("Connection closed");
      }
      else{
         System.out.println("Connection is not closed");
      }
      
      return encryptedPassword;
   }
      
   /*
      setData method that accepts an SQL String
      and returns true
      @param sql       The sql string
      @param stringValues To be used by the prepare method
      @return true or false
   */   
   public boolean setData(String sql, ArrayList<String> stringValues){
      // connect to database
      if(connect() == true){
         System.out.println("Connected");
      }
      else{
         System.out.println("Not connected");
      }
      
      try{
         /* Try to execute the statement */
         PreparedStatement stmnt = prepare (sql, stringValues);
         boolean rc = stmnt.execute();
         
      }catch(SQLException sqle){
         /* If fails, returns false */
         System.out.println("Error: " + sqle);
         return false;
      }     
      
      // close database
      if(closeMySQL() == true){
         System.out.println("Connection closed");
      }
      else{
         System.out.println("Connection is not closed");
      }
      return true;
   }     
   
   /** 
      setData accepts an SQL string and returns a boolean if the operation was performed or not. This will be
      used for doing "UPDATE", "DELETE", and "INSERT" operations.
      @param sqlString The sql update, delete or insert statement
      @return true or false
   **/  
   public boolean setData(String sqlString){
   
      // connect to database
      if(connect() == true){
         System.out.println("Connected");
      }
      else{
         System.out.println("Not connected");
      }
      
      try{
         /* Try to execute the statement */
         Statement stmnt = conn.createStatement();
         boolean rc = stmnt.execute(sqlString);
      }catch(SQLException sqle){
         /* If fails, returns false */
         System.out.println("Error: " + sqle);
         return false;
      }
      
      // close database
      if(closeMySQL() == true){
         System.out.println("Connection closed");
      }
      else{
         System.out.println("Connection is not closed");
      }
      
      /* If the operation was performed, returns true */
      return true;
   }
   
   /**
      This method accepts a SQL string and an ArrayList of string values, prepares it,
      binds the values, and returns a prepared statement.
      @param sqlString The sql statement
      @param stringValues To be used with the statement
   **/
   public PreparedStatement prepare (String sqlString, ArrayList<String> stringValues){
      connect();
      
      PreparedStatement ps = null;
      //ResultSet rs = null;
      
      try{
         ps = conn.prepareStatement(sqlString);
         
         //System.out.println("Prepared statement before bind variables set: \n\t" + ps.toString());
         
         for (int i=0; i<stringValues.size(); i++){
            ps.setString(i+1, stringValues.get(i));
         }
         
         //System.out.println("Prepared statement after bind variables set: \n\t" + ps.toString());

         //rs = ps.executeQuery();
         //ps.execute();
      }catch (SQLException sqle){
         System.out.println("Exception: " + sqle);
      }
      
      return ps;
   }
   
} // End ResearchDatabase