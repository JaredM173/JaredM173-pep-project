package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    //update message by id 
    public Message updateMessageById(int id, String revisedMessage){
        Connection connection = ConnectionUtil.getConnection();
        Message updatedMessage = null; 

    try {
        // First, select the message to return it later
        String selectSql = "SELECT * FROM message WHERE message_id = ?";
        try (PreparedStatement selectPs = connection.prepareStatement(selectSql)) {
            selectPs.setInt(1, id);
            ResultSet rs = selectPs.executeQuery();

            
            if (rs.next()) {
                updatedMessage = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        } // End of first try-with-resources block for SELECT
       
        // If the message exists, update it
        if (updatedMessage != null && updatedMessage.getMessage_text()!="" &&updatedMessage.getMessage_text().length()<=255) {
            String updateSql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            try (PreparedStatement updatePs = connection.prepareStatement(updateSql)) {
                updatePs.setString(1, revisedMessage);
                updatePs.setInt(2, id);
                int rowsUpdated = updatePs.executeUpdate();
                
            } // End of second try-with-resources block for Update
        }

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    return updatedMessage; 
        
    }
    // delete message by id
    public Message deleteMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message deletedMessage = null; 

    try {
        // First, select the message to return it later
        String selectSql = "SELECT * FROM message WHERE message_id = ?";
        try (PreparedStatement selectPs = connection.prepareStatement(selectSql)) {
            selectPs.setInt(1, id);
            ResultSet rs = selectPs.executeQuery();

            
            if (rs.next()) {
                deletedMessage = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        } // End of first try-with-resources block for SELECT

        // If the message exists, delete it
        if (deletedMessage != null) {
            String deleteSql = "DELETE FROM message WHERE message_id = ?";
            try (PreparedStatement deletePs = connection.prepareStatement(deleteSql)) {
                deletePs.setInt(1, id);
                deletePs.executeUpdate();
            } // End of second try-with-resources block for DELETE
        }

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    return deletedMessage; 
        
}

    //get message by id
    public Message getAllMessagesById(int id){
        Connection connection = ConnectionUtil.getConnection();
        
        
        try{
            String sql = "SELECT * FROM message WHERE message_id=?; ";
            PreparedStatement PS = connection.prepareStatement(sql);
           
            PS.setInt(1, id);
            ResultSet rs = PS.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                                              rs.getInt("posted_by"),
                                              rs.getString("message_text"),
                                              rs.getLong("time_posted_epoch"));
                
                return message;                              //messages.add(message);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }return null;
    }

    //get all messages
    public List<Message> getAllMessages(){  
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql ="SELECT * FROM message";
            PreparedStatement PS = connection.prepareStatement(sql);
            ResultSet rs = PS.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                                              rs.getInt("posted_by"),
                                              rs.getString("message_text"),
                                              rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    //insert a message in to message table 
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement PS = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            PS.setInt(1, message.getPosted_by());
            PS.setString(2, message.getMessage_text());
            PS.setLong(3, message.getTime_posted_epoch());
            PS.executeUpdate();
            ResultSet pkeyResultSet = PS.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id= (int) pkeyResultSet.getInt(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(),message.getTime_posted_epoch());

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
