// AUTHOR: Sebastien Yokoyama
// FILE NAME: Message.java
/* FILE DESCRIPTION: Class that represents a Message
*/

package Model;

public class Message {
    // Variables
    private int messageId;
    private int postedBy;   // Represents accountId of poster
    private String messageText;
    private long timePostedEpoch;   // Time when message was posted in num. secs since Jan. 1, 1978


    // Constructors
    // No-arg Constructor
    public Message(){ }

    /*
     * Parameterized Constructor
     * INPUT: 1 int, 1 String, 1 long
    */
    public Message(int postedBy, String messageText, long timePostedEpoch){
        this.postedBy = postedBy;
        this.messageText = messageText;
        this.timePostedEpoch = timePostedEpoch;
    }

    /*
     * Parameterized Constructor
     * INPUT: 2 int, 1 String, 1 long
    */
    public Message(int messageId, int postedBy, String messageText, long timePostedEpoch){
        this.messageId = messageId;
        this.postedBy = postedBy;
        this.messageText = messageText;
        this.timePostedEpoch = timePostedEpoch;
    }


    // Methods
    // messageId Getter Method
    public int getMessageId(){ return this.messageId; }
    // messageId Setter Method
    public void setMessageId(int messageId){ this.messageId = messageId; }

    // postedBy Getter Method
    public int getPostedBy(){ return this.postedBy; }
    // postedBy Setter Method
    public void setPostedBy(int postedBy){ this.postedBy = postedBy; }

    // messageText Getter Method
    public String getMessageText(){ return this.messageText; }
    // messageText Setter Method
    public void setMessageText(String messageText){ this.messageText = messageText; }

    // timePostedEpoch Getter Method
    public long getTimePostedEpoch(){ return this.timePostedEpoch; }
    // timePostedEpoch Setter Method
    public void setTimePostedEpoch(long timePostedEpoch){ this.timePostedEpoch = timePostedEpoch; }
}
