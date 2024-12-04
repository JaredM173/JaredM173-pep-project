package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Service.MessageService;
import Service.AccountService;
import Model.Account;
import Model.Message;

import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::NewAccHandler);
        app.post("/login", this::login);
        app.post("/messages", this::NewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getAllMessagesByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        //app.start(8080);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void updateMessageByIdHandler(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        String revisedMessage = ctx.body();
        Message message = messageService.updateMessageById(id, revisedMessage);
        if (message == null){
            ctx.status(400);
        }else{
            ctx.json(message);
        }
    }

    private void deleteMessageByIdHandler(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deletMessageById(id);
        if (message==null){
            ctx.status(200);
        }else{
            ctx.json(message);
        }
    }

    private void getAllMessagesByIdHandler(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getAllMessagesById(id);
        if(message==null){
            ctx.status(200);
        }else{
            ctx.json(message);
        }
        
    }

    private void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void NewMessageHandler(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }

    private void NewAccHandler(Context ctx) throws JsonProcessingException { 
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }

    private void login(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account validUser = accountService.Login(account);
        if(validUser!=null){
            ctx.json(mapper.writeValueAsString(validUser));
        }else{
            ctx.status(401);
        }
    }


}