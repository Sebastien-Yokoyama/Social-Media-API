// AUTHOR: Sebastien Yokoyama
// FILE NAME: SocialMediaController.java
/*
 * FILE DESCRIPTION: Class that implements the business layer for the data
*/

package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.security.RouteRole;
import io.javalin.http.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Set;

import Model.Account;
import Model.Message;
import Service.SocialMediaService;

public class SocialMediaController {
    SocialMediaService socialMediaService;

    // Constructors
    // No-arg constructor
    public SocialMediaController(){
        this.socialMediaService = new SocialMediaService();
    }

    // Parameterized Constructor
    public SocialMediaController(SocialMediaService socialMediaService){
        this.socialMediaService = socialMediaService;
    }

    /*
     * Start API Method
     * INPUT: N/A
     * OUTPUT: Javalin object
     * Creates a Javalin object that defines the controller behavior and returns it
     * Endpoints will be grouped by roles
    */
    public Javalin startAPI(){
        Javalin app = Javalin.create();

        app.post("/register", this::postUserHandler);
        app.get("/login", this::getLoginUserHandler);
        app.patch("/accounts/{accountId}", this::patchUpdateUserHandler);
        app.delete("/accounts", this::deleteUserHandler);
        app.delete("/accounts/{accountId}", this::deleteUserByIdHandler);

        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{messageId}", this::getMessageByIdHandler);
        app.get("/accounts/{accountId}/messages", this::getAllMessagesFromUserHandler);
        app.patch("/messages/{messageId}", this::patchUpdateMessageHandler);
        app.delete("/messages", this::deleteMessageHandler);
        app.delete("/messages/{messageId}", this::deleteUserByIdHandler);

        return app;
    }

    // Handlers
    /*
     * POST User Handler Method
     * INPUT: 1 Context object
     * OUTPUT: N/A
     * Creates an Account for user registration
     * If successful, return response body with JSON representation of Account object
     * Otherwise, return client error
    */
    private void postUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();

        // Read Request Body
        Account user = om.readValue(ctx.body(), Account.class);

        // Attempt POST
        Account createdUser = socialMediaService.userRegister(user);

        // Check if unsuccessful
        if(createdUser == null){
            // Client error
            ctx.status(400);
        }
        else{
            // Return response body
            ctx.json(om.writeValueAsString(createdUser));
            ctx.status(200);
        }
    }

    /*
     * GET Login User Handler Method
     * INPUT: 1 Context object
     * OUTPUT: N/A
     * Retrieves a user's Account for user login
     * If successful, returns response body with JSON representation of Account object
     * Otherwise, return unauthorized error
    */
    private void getLoginUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();

        // Read Request Body
        Account user = om.readValue(ctx.body(), Account.class);

        // Attempt GET
        Account loginUser = socialMediaService.userLogin(user);

        // Check if unsuccessful
        if(loginUser == null){
            // Unauthorized error
            ctx.status(401);
        }
        else{
            // Return response body
            ctx.json(om.writeValueAsString(loginUser));
            ctx.status(200);
        }
    }

    /*
     * PATCH Update User Handler Method
     * INPUT: 1 Context object
     * OUTPUT: N/A
     * Retrieves a user's Account after updating it
     * If successful, returns response body with JSON representation of Account object
     * Otherwise, return client error
    */
    private void patchUpdateUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();

        // Read Request Body
        Account user = om.readValue(ctx.body(), Account.class);

        // Attempt PATCH
        Account updatedUser = socialMediaService.userUpdate(user);

        // Check if unsuccessful
        if(updatedUser == null){
            // Client error
            ctx.status(400);
        }
        else{
            // Return response body
            ctx.json(om.writeValueAsString(updatedUser));
            ctx.status(200);
        }
    }

    /*
     * DELETE User Handler Method
     * INPUT: 1 Context object
     * OUTPUT: N/A
     * Retrieves a user's Account after deleting it
     * If successful, returns response body with JSON representation of Account object
     * Otherwise, return client error
    */
    private void deleteUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();

        // Read Request Body
        Account user = om.readValue(ctx.body(), Account.class);

        // Attempt DELETE
        Account deletedUser = socialMediaService.userDelete(user.getAccountId());

        // Check if unsuccessful
        if(deletedUser == null){
            // Client error
            ctx.status(400);
        }
        else{
            // Return response body
            ctx.json(om.writeValueAsString(deletedUser));
            ctx.status(200);
        }
    }

    /*
     * DELETE User By ID Handler Method
     * INPUT: 1 Context object
     * OUTPUT: N/A
     * Retrieves a user's Account after deleting it
     * If successful, returns response body with JSON representation of Account object
     * Otherwise, return unauthorized error
    */
    private void deleteUserByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();

        // Attempt DELETE
        Account deletedUser = socialMediaService.userDelete(Integer.parseInt(ctx.pathParam("accountId")));

        // Check if unsuccessful
        if(deletedUser == null){
            // Unauthorized error
            ctx.status(401);
        }
        else{
            // Return response body
            ctx.json(om.writeValueAsString(deletedUser));
            ctx.status(200);
        }
    }

    /*
     * POST Message Handler Method
     * INPUT: 1 Context object
     * OUTPUT: N/A
     * Retrieves a Message after creating it
     * If successful, returns response body with JSON representation of Message object
     * Otherwise, return client error
    */
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();

        // Read request body
        Message msg = om.readValue(ctx.body(), Message.class);

        // Attempt POST
        Message addedMsg = socialMediaService.messageCreate(msg);

        // Check if unsuccessful
        if(addedMsg == null){
            // Client error
            ctx.status(400);
        }
        else{
            // Return response body
            ctx.json(om.writeValueAsString(addedMsg));
            ctx.status(200);
        }
    }

    /*
     * GET Message By ID Handler Method
     * INPUT: 1 Context object
     * OUTPUT: N/A
     * Retrieves a Message by the ID from the request body
     * If successful, returns JSON representation of Message object
     * Otherwise, returns emtpy JSON body
     * Always returns HTTP Status Code 200
    */
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();

        // Attempt GET
        Message msg = socialMediaService.messageRetrieveById(Integer.parseInt(ctx.pathParam("messageId")));

        // Check if unsuccessful
        if(msg == null){
            ctx.json("");
        }
        else{
            // Return response body
            ctx.json(om.writeValueAsString(msg));
        }

        ctx.status(200);
    }

    /*
     * GET All Messages From User Handler Method
     * INPUT: 1 Context object
     * OUTPUT: N/A
     * Retrieves all Messages from the accountId from the request body
     * If successful, returns JSON representation of Message object List
     * Otherwise, returns empty JSON body
     * Always returns HTTP Status Code 200
    */
    private void getAllMessagesFromUserHandler(Context ctx) {
        // Return response body
        ctx.json(socialMediaService.messageRetrieveAllFromUser(Integer.parseInt(ctx.pathParam("accountId"))));
        ctx.status(200);
    }

    /*
     * GET All Messages Handler Method
     * INPUT: 1 Context object
     * OUTPUT: N/A
     * Retrieves all Messages
     * If successful, returns JSON representation of Message object List
     * Otherwise, returns empty JSON body
     * Always returns HTTP Status Code 200
    */
    private void getAllMessagesHandler(Context ctx) {
        // Return response body
        ctx.json(socialMediaService.messageRetrieveAll());
        ctx.status(200);
    }

    /*
     * PATCH Update Message Handler Method
     * INPUT: 1 Context object
     * OUTPUT: N/A
     * Retrieves a Message after updating it
     * If successful, returns response body with JSON representation of Message object
     * Otherwise, returns client error
    */
    private void patchUpdateMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();

        // Read request body
        Message msg = om.readValue(ctx.body(), Message.class);

        // Attempt PATCH
        Message updatedMsg = socialMediaService.messageUpdate(Integer.parseInt(ctx.pathParam("messageId")), msg.getMessageText());

        // Check if unsuccessful
        if(updatedMsg == null){
            // Client error
            ctx.status(400);
        }
        else{
            // Return response body
            ctx.json(om.writeValueAsString(updatedMsg));
            ctx.status(200);
        }
    }

    /*
     * DELETE Message Handler Method
     * INPUT: 1 Context object
     * OUTPUT: N/A
     * Retrieves a Message after deleting it
     * If successful, returns response body with JSON representation of Message object
     * Otherwise, returns empty JSON body
     * Always returns HTTP Status Code 200
    */
    private void deleteMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();

        // Attempt DELETE
        Message deletedMsg = socialMediaService.messageDelete(Integer.parseInt(ctx.pathParam("messageId")));

        // Check if unsuccessful
        if(deletedMsg == null){
            ctx.json("");
        }
        else{
            // Return response body
            ctx.json(om.writeValueAsString(deletedMsg));
        }

        ctx.status(200);
    }
}
