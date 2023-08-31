

package DAO.Message;

import Model.Message;
import Utility.ConnectionUtil;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class MessageDaoImpl implements MessageDAO {
        /*
         * Insert Message Method
         * INPUT: 1 Message object
         * OUTPUT: Message object
         * Inserts the given Message to the DB and returns the Message object with the messageId
         * Returns null if unsuccessful
        */
        public Message insertMessage(Message msg){
            // Establish connection to DB
            Connection connection = ConnectionUtil.getConnection();

            // SQL Logic
            try{
                String sql = "INSERT INTO messages (postedBy, messageText, timePostedEpoch) VALUES (?, ?, ?)";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, msg.getPostedBy());
                ps.setString(2, msg.getMessageText());
                ps.setLong(3, msg.getTimePostedEpoch());

                // Check if successful
                if(ps.executeUpdate() > 0){
                    return selectMessageByData(msg);
                }
            }
            catch(SQLException e){
                System.out.println("Failed to insert Message");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            // Return null if unsuccessful
            return null;
        }

        /*
         * Select Message by ID Method
         * INPUT: 1 int
         * OUTPUT: Message object
         * Retrieves the Message with the given ID from the DB and returns it
         * Returns null if unsuccessful
        */
        public Message selectMessageById(int id){
            // Establish connection to DB
            Connection connection = ConnectionUtil.getConnection();

            // SQL Logic
            try{
                String sql = "SELECT * FROM messages WHERE messageId = ?";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                // Check if ResultSet is empty
                if(rs.next()){
                    return extractMessageFromResultSet(rs);
                }
            }
            catch(SQLException e){
                System.out.println("Failed to retrieve Message by ID");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            // Return null if unsuccessful
            return null;
        }

        /*
         * Select Message by Data Method
         * INPUT: 1 Message object
         * OUTPUT: Message object
         * Retrieves the Message with the given data from the DB and returns it
         * Returns null if unsuccessful
        */
        public Message selectMessageByData(Message msg){
            // Establish connection to DB
            Connection connection = ConnectionUtil.getConnection();

            // SQL Logic
            try{
                String sql = "SELECT * FROM messages WHERE postedBy = ? AND messageText = ? AND timePostedEpoch = ?";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, msg.getPostedBy());
                ps.setString(2, msg.getMessageText());
                ps.setLong(3, msg.getTimePostedEpoch());

                ResultSet rs = ps.executeQuery();

                // Check if ResultSet is empty
                if(rs.next()){
                    return extractMessageFromResultSet(rs);
                }
            }
            catch(SQLException e){
                System.out.println("Failed to retrieve Message by Data");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            // Return null if unsuccessful
            return null;
        }
    
        /*
         * Select all Messages from User Method
         * INPUT: 1 int
         * OUTPUT: List of Message objects
         * Retrieves all Messages sent from a user from the DB and returns it in a List
         * Returns an empty List if unsuccessful, or if no Messages exist
        */
        public List<Message> selectAllMessagesFromUser(int accountId){
            // Establish connection to DB
            Connection connection = ConnectionUtil.getConnection();

            List<Message> messages = new ArrayList<>();

            // SQL Logic
            try{
                String sql = "SELECT * FROM messages WHERE postedBy = ?";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, accountId);

                ResultSet rs = ps.executeQuery();

                // Loop through ResultSet
                while(rs.next()){
                    messages.add(extractMessageFromResultSet(rs));
                }
            }
            catch(SQLException e){
                System.out.println("Failed to retrieve all Messages from user");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            return messages;
        }
    
        /*
         * Select all Messages Method
         * INPUT: N/A
         * OUTPUT: List of Message objects
         * Retrieves all Messages from the DB and returns it in a List
         * Returns an empty List if unsuccessful, or if no Messages exist
        */
        public List<Message> selectAllMessages(){
            // Establish connection to DB
            Connection connection = ConnectionUtil.getConnection();

            List<Message> messages = new ArrayList<>();

            // SQL Logic
            try{
                String sql = "SELECT * FROM messages";

                PreparedStatement ps = connection.prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                // Loop through ResultSet
                while(rs.next()){
                    messages.add(extractMessageFromResultSet(rs));
                }
            }
            catch(SQLException e){
                System.out.println("Failed to retrieve all Messages");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            return messages;
        }
    
        /*
         * Update Message Method
         * INPUT: 1 Message object
         * OUTPUT: Message object
         * Updates the given Message in the DB with the input data
         * Returns null if unsuccessful
        */
        public Message updateMessage(Message msg){
            // Establish connection to DB
            Connection connection = ConnectionUtil.getConnection();

            // SQL Logic
            try{
                String sql = "UPDATE messages SET messageText = ? WHERE messageId = ?";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, msg.getMessageText());
                ps.setInt(2, msg.getPostedBy());

                // Check if successful
                if(ps.executeUpdate() > 0){
                    return selectMessageById(msg.getMessageId());
                }
            }
            catch(SQLException e){
                System.out.println("Failed to update Message");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            // Return null if unsuccessful
            return null;
        }
    
        /*
         * Delete Message Method
         * INPUT: 1 int
         * OUTPUT: Message object
         * Deletes the Message with the given ID from the DB and returns the now-deleted Message
         * Returns null if unsuccessful
        */
        public Message deleteMessage(int id){
            // Establish connection to DB
            Connection connection = ConnectionUtil.getConnection();

            // SQL Logic
            try{
                // Acquire Message data before deletion
                Message deletedMsg = selectMessageById(id);

                // Check if message exists
                if(deletedMsg != null){
                    String sql = "DELETE FROM messages WHERE messageId = ?";

                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setInt(1, id);

                    // Check if successful
                    if(ps.executeUpdate() > 0){
                        return deletedMsg;
                    }
                }
            }
            catch(SQLException e){
                System.out.println("Failed to delete Message");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            // Return null if unsuccessful
            return null;
        }

        /*
         * Extract Message from ResultSet Helper Method
         * INPUT: 1 ResultSet object
         * OUTPUT: Message object
         * Retrieves a Message from a given ResultSet
        */
        private Message extractMessageFromResultSet(ResultSet rs) throws SQLException{
            Message msg = new Message();

            msg.setMessageId(rs.getInt("messageId"));
            msg.setPostedBy(rs.getInt("postedBy"));
            msg.setMessageText(rs.getString("messageText"));
            msg.setTimePostedEpoch(rs.getLong("timePostedEpoch"));

            return msg;
        }
}
