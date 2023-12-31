// AUTHOR: Sebastien Yokoyama
// FILE NAME: MessageDAO.java
/* FILE DESCRIPTION: Interface that defines CRUD behavior for user message data.
*/

package DAO.Message;

import Model.Message;

import java.util.List;

public interface MessageDAO {
    // Create Message
    public Message insertMessage(Message msg);

    // Retrieve Message by ID
    public Message selectMessageById(int id);

    // Retrieve Message by Data
    public Message selectMessageByData(Message msg);

    // Retrieve all Messages from User
    public List<Message> selectAllMessagesFromUser(int accountId);

    // Retrieve all Messages
    public List<Message> selectAllMessages();

    // Update Message
    public Message updateMessage(int id, String newText);

    // Delete Message by ID
    public Message deleteMessage(int id);
}
