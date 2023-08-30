// AUTHOR: Sebastien Yokoyama
// FILE NAME: Account.java
/* FILE DESCRIPTION: Class that represents an Account
*/

package Model;

public class Account {
    // Variables
    private int accountId;
    private String username;
    private String password;
    

    // Constructors
    // No-arg Constructor
    public Account(){ }

    /*
     * Parameterized Constructor
     * INPUT: 2 Strings
    */
    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    /*
     * Parameterized Constructor
     * INPUT: 1 int, 2 Strings
    */
    public Account(int accountId, String username, String password){
        this.accountId = accountId;
        this.username = username;
        this.password = password;
    }


    // Methods
    // account_id Getter Method
    public int getAccountId(){ return this.accountId; }
    // account_id Setter Method
    public void setAccountId(int accountId){ this.accountId = accountId; }
    
    // username Getter Method
    public String getUsername(){ return this.username; }
    // username Setter Method
    public void setUsername(String username){ this.username = username; }

    // password Getter Method
    public String getPassword(){ return this.password; }
    // password Setter Method
    public void setPassword(String password){ this.password = password; }
}
