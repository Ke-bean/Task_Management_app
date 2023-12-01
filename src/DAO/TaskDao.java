/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import java.sql.*;
import MODEL.Task;
import java.util.*;

/**
 *
 * @author Kebean
 */
public class TaskDao {
    String url = "jdbc:postgresql://localhost:5432/TaskManagementSystem";
    String username = "postgres";
    String password = "kebean";
    public String createTask(Task taskObj){
        try{
            Connection Con = DriverManager.getConnection(url, username, password);
            String query  = "insert into tasks values(?, ?, ?, ?)";
            PreparedStatement stat = Con.prepareStatement(query);
            stat.setInt(1, taskObj.getId());
            stat.setString(2, taskObj.getTask());
            stat.setString(3, taskObj.getCategory());
            stat.setDate(4, taskObj.getDate());
            int affectedRows = stat.executeUpdate();
            if(affectedRows > 0){
                return "Task Created Successfully";
            }else{
                return "Fatal Error: Oops! Task failed to be created";
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    public String updateTask(Task taskObj){
        try{
            Connection Con = DriverManager.getConnection(url, username, password);
            String query = "update tasks set task = ?, category = ?, date = ? where taskId = ?";
            PreparedStatement stat = Con.prepareStatement(query);
            stat.setString(1, taskObj.getTask());
            stat.setString(2, taskObj.getCategory());
            stat.setDate(3, taskObj.getDate());
            stat.setInt(4, taskObj.getId());
            int affectedRows = stat.executeUpdate();
            Con.close();
            if(affectedRows > 0){
                return "Task Updated Successfully";
            }else{
                return "Fatal Error: Task not updated";
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    public String deleteTask(Task taskObj){
        try{
            Connection Con = DriverManager.getConnection(url, username, password);
            String query = "delete from tasks where taskid = ?";
            PreparedStatement stat = Con.prepareStatement(query);
            stat.setInt(1, taskObj.getId());
            int affectedRows = stat.executeUpdate();
            Con.close();
            if(affectedRows > 0){
                return "Task Deleted Successfully";
            }else{
                return "Fatal Error: Task can't be deletd";
            }
        }catch(Exception ex){
         ex.printStackTrace();
        }
        return null;
    }
    public Task searchTask(Task taskObj){
        try{
            Connection Con = DriverManager.getConnection(url, username, password);
            String query = "select * from tasks where taskid=?";
            PreparedStatement stat = Con.prepareStatement(query);
            stat.setInt(1, taskObj.getId());
            ResultSet task = stat.executeQuery();
            Task taskObj2 = new Task();
            boolean flag = false;
            if(task.next()){
                taskObj2.setId(task.getInt("taskid"));
                taskObj2.setTask(task.getString("task"));
                taskObj2.setCategory(task.getString("category"));
                taskObj2.setDate(task.getDate("date"));
                flag = true;
            }
            Con.close();
            if(flag){
                return taskObj2;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    public List<Task> RetrieveAllTasks(){
        try{
            Connection Con = DriverManager.getConnection(url, username, password);
            String query = "Select * from tasks";
            PreparedStatement stat = Con.prepareStatement(query);
            ResultSet task = stat.executeQuery();
            List<Task> taskObj = new ArrayList<>();
            while(task.next()){
                Task allTasks = new Task();
                allTasks.setId(task.getInt("taskid"));
                allTasks.setTask(task.getString("task"));
                allTasks.setCategory(task.getString("category"));
                allTasks.setDate(task.getDate("date"));
                Con.close();
                taskObj.add(allTasks);
            }
            return taskObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
