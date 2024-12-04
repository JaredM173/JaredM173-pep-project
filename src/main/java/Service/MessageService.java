package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;
//import Model.Account;

//import java.util.ArrayList;
import java.util.List;


public class MessageService {
   private MessageDAO messageDAO;
   private AccountDAO accountDAO;
   
   // no args constructor for creating new account service 
   public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
   }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    //update message by Id
    public Message updateMessageById(int id, String revisedMessage){
        return messageDAO.updateMessageById(id, revisedMessage);
    }
    // delete message by Id
    public Message deletMessageById(int id){
        return messageDAO.deleteMessageById(id);
    }
    // retrive messages from specific users
    public Message getAllMessagesById(int id){
        
        return messageDAO.getAllMessagesById(id);
    }
    //retrive all messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
    // new messages 
    public Message addMessage(Message message){
       
        if(accountDAO.getAccountByID(message.getMessage_id())!= null 
        ||message.getMessage_text().length()>255 || message.getMessage_text()=="" ){
            return null;
        }
        return messageDAO.insertMessage(message);
    }

}
