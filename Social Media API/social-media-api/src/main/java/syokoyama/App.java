// AUTHOR: Sebastien Yokoyama
// FILE NAME: App.java
/* FILE DESCRIPTION: Contains the main program
*/

package syokoyama;

import Model.*;
import Utility.ConnectionUtil;

import java.sql.*;

public class App 
{
    public static void main( String[] args )
    {
        Connection connection = ConnectionUtil.getConnection();

        try{
            // String sql = "SELECT * FROM accounts";
            String sql = "SELECT * FROM messages";
        
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            // Account account = new Account(rs.getInt("accountId"),
            //     rs.getString("username"), rs.getString("password"));
                
            // System.out.println(account.toString());

            Message message = new Message(
                rs.getInt("messageId"), rs.getInt("postedBy"),
                rs.getString("messageText"), rs.getLong("timePostedEpoch")
            );

            System.out.println(message.toString());
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
