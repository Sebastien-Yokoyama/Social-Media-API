// AUTHOR: Sebastien Yokoyama
// FILE NAME: SocialMediaService.java
/*
 * FILE DESCRIPTION: Class that implements the CRUD logic for data
*/

package Service;

import java.util.List;

import DAO.Account.AccountDaoImpl;
import DAO.Message.MessageDaoImpl;
import Model.Account;
import Model.Message;

public class SocialMediaService {
    // DAO Implementations
    private AccountDaoImpl accountDao;
    private MessageDaoImpl messageDao;

    // Constructors
    // No-arg constructor
    public SocialMediaService(){
        this.accountDao = new AccountDaoImpl();
        this.messageDao = new MessageDaoImpl();
    }

    // Parameterized constructor
    public SocialMediaService(AccountDaoImpl accountDao, MessageDaoImpl messageDao){
        this.accountDao = accountDao;
        this.messageDao = messageDao;
    }

    // Methods
    /*
     * User Register Method
     * INPUT: 1 Account object
     * OUTPUT: Account object
     * If username or password is blank, then return null
     * Otherwise, insert Account and return Account object
    */
    public Account userRegister(Account user){
        // Ensure username and password are valid
        if(user.getUsername().isBlank() || user.getPassword().isBlank()){
            return null;
        }

        return accountDao.insertAccount(user);
    }

    /*
     * User Login Method
     * INPUT: 1 Account object
     * OUTPUT: Account object
     * If username or password is blank, then return null
     * Otherwise, return Account that is being logged into
    */
    public Account userLogin(Account user){
        // Ensure username and password are valid
        if(user.getUsername().isBlank() || user.getPassword().isBlank()){
            return null;
        }

        return accountDao.selectAccountByUserAndPass(user.getUsername(), user.getPassword());
    }

    /*
     * User Update Method
     * INPUT: 1 Account object
     * OUTPUT: Account object
     * Updates the account with the given accountId and returns the updated Account
     * Returns null if unsuccessful
    */
    public Account userUpdate(Account user){
        // Ensure username and password are valid
        if(user.getUsername().isBlank() || user.getPassword().isBlank()){
            return null;
        }

        return accountDao.updateAccount(user);
    }

    /*
     * User Delete Method
     * INPUT: 1 int
     * OUTPUT: Account object
     * Deletes the account with the given accountId and returns it
     * Returns null if unsuccessful
    */
    public Account userDelete(int id){
        return accountDao.deleteAccount(id);
    }

    /*
     * Message Create Method
     * INPUT: 1 Message object
     * OUTPUT: Message object
     * If message text is empty, return null
     * Otherwise, insert Message and return it
    */
    public Message messageCreate(Message msg){
        // Ensure messageText is valid
        if(msg.getMessageText().isBlank()){
            return null;
        }

        return messageDao.insertMessage(msg);
    }

    /*
     * Message Retrieve by ID Method
     * INPUT: 1 int
     * OUTPUT: Message object
     * Retrieves the message with the given messageId and returns it
     * Returns null if unsuccessful
    */
    public Message messageRetrieveById(int id){
        return messageDao.selectMessageById(id);
    }

    /*
     * Message Retrieve All from User Method
     * INPUT: 1 int
     * OUTPUT: List of Message objects
     * Retrieves all of the messages from a given accountId
     * Returns an empty List if unsuccessful
    */
    public List<Message> messageRetrieveAllFromUser(int id){
        return messageDao.selectAllMessagesFromUser(id);
    }

    /*
     * Message Retrieve All Method
     * INPUT: N/A
     * OUTPUT: List of Message objects
     * Retrieves all messages
     * Returns an empty List if unsuccessful
    */
    public List<Message> messageRetrieveAll(){
        return messageDao.selectAllMessages();
    }

    /*
     * Message Update Method
     * INPUT: 1 Message object
     * OUTPUT: Message object
     * Updates the message with the given messageId and returns it
     * Returns null if unsuccessful
    */
    public Message messageUpdate(int id, String newText){
        return messageDao.updateMessage(id, newText);
    }

    /*
     * Message Delete by ID Method
     * INPUT: 1 int
     * OUTPUT: Message object
     * Deletes the message with the given messageId and returns it
     * Returns null if unsuccessful
    */
    public Message messageDelete(int id){
        return messageDao.deleteMessage(id);
    }
}
