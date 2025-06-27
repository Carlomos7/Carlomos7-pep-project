package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        // app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postRegisterAccountHandler);
        app.post("/login", this::postLoginAccountHandler);
        
        app.post("/messages", this::postMessageHandler);
        app.get("/messages",this::getAllMessagesHandler);
        app.get("/messages/{message_id}",this::getMessageByIdHandler);
        app.delete("/messages/{message_id}",this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}",this::patchMessageByIdHandler);
        app.get("/accounts/{account_id}/messages",this::getMessagesByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }

    private void postRegisterAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account accountCreated = accountService.register(account);
        if(accountCreated == null){
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(accountCreated));
        }
    }

    private void postLoginAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedInAccount = accountService.login(account.getUsername(),account.getPassword());
        if(loggedInAccount == null){
            ctx.status(401);
        } else {
            ctx.json(mapper.writeValueAsString(loggedInAccount));
        }
    }
    
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message messageCreated = messageService.createMessage(message);
        if(messageCreated == null){
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(messageCreated));
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException{
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException{
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if(message == null){
            ctx.status(200);
            ctx.result("");
        } else {
            ctx.json(message);
        }
    }

    private void deleteMessageByIdHandler(Context ctx)throws JsonProcessingException{
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message found = messageService.getMessageById(messageId);
        if(found == null){
            ctx.status(200);
            ctx.result("");
            return;
        }
        messageService.deleteMessageById(messageId);
        ObjectMapper mapper = new ObjectMapper();
        ctx.status(200).result(mapper.writeValueAsString(found));
    }

    private void patchMessageByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message incoming = mapper.readValue(ctx.body(),Message.class);
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message updated = messageService.updateMessage(messageId,incoming.getMessage_text());
        if(updated == null){
            ctx.status(400);
        } else {
            ctx.json(updated);
        }
    }

    private void getMessagesByAccountIdHandler(Context ctx){
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getMessagesByAccountId(accountId));
    }
}