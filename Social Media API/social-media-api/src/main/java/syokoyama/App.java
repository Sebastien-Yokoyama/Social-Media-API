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
            String sql = "SELECT * FROM accounts";
        
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("Acc ID: " + Integer.toString(rs.getInt("accountId")));
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
