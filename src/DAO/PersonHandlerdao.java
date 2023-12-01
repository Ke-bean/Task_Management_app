/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import java.sql.*;
import java.util.*;
import MODEL.Person;
import MODEL.Task;

/**
 *
 * @author Kebean
 */
public class PersonHandlerdao {
      String url = "jdbc:postgresql://localhost:5432/TaskManagementSystem";
      String username = "postgres";
      String password = "kebean";
      //first of all, I want to create all tables that I will need just using queries
      //=================================================================================
//    public String createUserTable(){
//        // Table 1: user Table
//        //---------------------
////      try{
////          Connection Con  = DriverManager.getConnection(url, username, password);
////          String query = "create table users(id varchar(20) NOT NULL , firstname varchar(30) NOT NULL, lastname varchar(30) NOT NULL, email varchar(30) NOT NULL, password varchar(30) NOT NULL, primary key(id))";
////          PreparedStatement stat = Con.prepareStatement(query);
////          boolean feedBack = stat.execute();
////          if(!feedBack){
////              return "Table created Successfully";
////          }else{
////              return "Fatal Error! Table not created";
////          }
////      }catch(Exception ex){
////          ex.printStackTrace();
////      }
////      return null;
////    
////           Table 2: task table
//           //----------------------
//           try{
//               Connection Con = DriverManager.getConnection(url, username, password);
      
      
                 //=================================================================================================================================================
                  // will need to check how to use foreign keys && Relationships effectively with JDBC API...(so as to select task based on the logged in user)...
                //==================================================================================================================================================
      
//               String query = "create table tasks(taskId int, id varchar(20) REFERENCES users(id) ON DELETE CASCADE,task varchar(100), category varchar(20), date DATE,primary key(taskId))";
//               PreparedStatement stat = Con.prepareStatement(query);
//               boolean feedBack = stat.execute();
//               if(!feedBack){
//                   return "Table created Successfully";
//               }else{
//                   return "Fatal Error: Table not created";
//               }
//           }catch(Exception ex){
//               ex.printStackTrace();
//           }
//           return null;
//        
//    }
      public String registerPerson(Person obj){
          try{
              Connection Con = DriverManager.getConnection(url, username, password);
              String query = "insert into users values (?, ?, ?, ?, ?)";
              PreparedStatement stat = Con.prepareStatement(query);
              stat.setString(1, obj.getId());
              stat.setString(2, obj.getFirstname());
              stat.setString(3, obj.getLastname());
              stat.setString(4, obj.getEmail());
              stat.setString(5, obj.getPassword());
              int rowsAffected = stat.executeUpdate();
              Con.close();
              if(rowsAffected > 0){
                  return " "+obj.getFirstname()+ " "+obj.getLastname()+ " Registered Successfully";
              }else{
                  return "Fatal Error! Person Registration failed";
              }
          }catch(Exception ex){
              ex.printStackTrace();
          }
          return null;
      }
      public Person loginPerson(Person obj){
          try{
              Connection Con = DriverManager.getConnection(url, username, password);
              String query = "select * from users where email = ? and password = ?";
              PreparedStatement stat = Con.prepareStatement(query);
              stat.setString(1, obj.getEmail());
              stat.setString(2, obj.getPassword());
              ResultSet rs = stat.executeQuery();
              Person newPerson = new Person();
             boolean indicator = false;
             if(rs.next()){
                indicator = true;
             }
             SessionId.Id = rs.getString("id");
             newPerson.setId(SessionId.Id);
             Con.close();
             if(indicator){
                 return newPerson;
             }
          }catch(Exception ex){
              ex.printStackTrace();
          }
          return null;
      }
      public Person HandleSession(Person obj){
          try{
              Connection Con = DriverManager.getConnection(url, username, password);
              String query = "select firstname, lastname from users where id=?";
              PreparedStatement stat = Con.prepareStatement(query);
              stat.setString(1, SessionId.Id);
              ResultSet rs = stat.executeQuery();
              Person newPerson = new Person();
              boolean indicator = false;
              if(rs.next()){
                  newPerson.setFirstname(rs.getString("firstname"));
                  newPerson.setLastname(rs.getString("lastname"));
                  indicator = true;
              }
              Con.close();
              if(indicator){
                  return newPerson;
              }
          }catch(Exception ex){
              ex.printStackTrace();
            }
        return null;
    }
}
