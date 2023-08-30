// AUTHOR: Sebastien Yokoyama
// FILE NAME: ConnectionUtil.java
/* FILE DESCRIPTION: Class that connects to database via JDBC
*/

package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static String url = "jdbc:sqlite:D:\\Programs\\SQLite\\Databases\\SocialMediaApi\\SocialMediaDB.db";
    private static Connection connection = null;

    public static Connection getConnection(){
        if(connection == null){
            try{
                connection = DriverManager.getConnection(url);
            }
            catch(SQLException e){
                System.out.println("Database failed to connect!");
                e.printStackTrace();   
            }
        }

        return connection;
    }
}
