// AUTHOR: Sebastien Yokoyama
// FILE NAME: AccountDaoImpl.java
/* FILE DESCRIPTION: Class implementation of AccountDAO interface
*/

package DAO.Account;

import Model.Account;
import Utility.ConnectionUtil;

import java.sql.*;
import java.util.List;

import java.util.ArrayList;

public class AccountDaoImpl implements AccountDAO{
    /*
     * Insert Account Method
     * INPUT: 1 Account object
     * OUTPUT: Account object
     * Inserts the given Account to the DB and returns the Account object with the accountId
     * Returns null if unsuccessful
    */
    public Account insertAccount(Account user){
        // Establish connection to DB
        Connection connection = ConnectionUtil.getConnection();

        // SQL Logic
        try{
            String sql = "INSERT INTO accounts (username, password) VALUES (?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            // Check if successful
            if(ps.executeUpdate() > 0){
                return selectAccountByUserAndPass(user.getUsername(), user.getPassword());
            }
        }
        catch(SQLException e){
            System.out.println("Failed to insert Account");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // Return null if unsuccessful
        return null;
    }

    /*
     * Select Account by ID Method
     * INPUT: 1 int
     * OUTPUT: Account object
     * Retrieves the Account with the given ID from the DB and returns it
     * Returns null if unsuccessful
    */
    public Account selectAccountById(int id){
        // Establish connection to DB
        Connection connection = ConnectionUtil.getConnection();

        // SQL Logic
        try{
            String sql = "SELECT * FROM accounts WHERE accountId = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            // Check if ResultSet is empty
            if(rs.next()){
                return extractAccountFromResultSet(rs);
            }
        }
        catch(SQLException e){
            System.out.println("Failed to retrieve Account by ID");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // Return null if unsuccessful
        return null;
    }

    /*
     * Select Account by Username and Password
     * INPUT: 2 String
     * OUTPUT: Account object
     * Retrieves the Account with the given username and password from the DB and returns it
     * Returns null if unsuccessful
    */
    public Account selectAccountByUserAndPass(String username, String password){
        // Establish connection to DB
        Connection connection = ConnectionUtil.getConnection();

        // SQL Logic
        try{
            String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            // Check if ResultSet is empty
            if(rs.next()){
                return extractAccountFromResultSet(rs);
            }
        }
        catch(SQLException e){
            System.out.println("Failed to retrieve Account by username and password");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // Return null if unsuccessful
        return null;
    }

    /*
     * Select All Accounts
     * INPUT: N/A
     * OUTPUT: List of Account objects
     * Retrieves all of the Accounts from the DB and returns it in a List
     * Returns an empty List if unsuccessful, or if no accounts exist
    */
    public List<Account> selectAllAccounts(){
        // Establish connection to DB
        Connection connection = ConnectionUtil.getConnection();

        List<Account> accounts = new ArrayList<>();

        // SQL Logic
        try{
            String sql = "SELECT * FROM accounts";

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            // Loop through ResultSet
            while(rs.next()){
                accounts.add(extractAccountFromResultSet(rs));
            }
        }
        catch(SQLException e){
            System.out.println("Failed to retrieve all Accounts");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return accounts;
    }

    /*
     * Update Account
     * INPUT: 1 Account object
     * OUTPUT: Account object
     * Updates the given Account in the DB with the input data
     * Returns null if unsuccessful
    */
    public Account updateAccount(Account user){
        // Establish connection to DB
        Connection connection = ConnectionUtil.getConnection();

        // SQL Logic
        try{
            String sql = "UPDATE accounts SET username = ?, password = ? WHERE accountId = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getAccountId());

            if(ps.executeUpdate() > 0){
                return selectAccountById(user.getAccountId());
            }
        }
        catch(SQLException e){
            System.out.println("Failed to update Account");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // Return null if unsuccessful
        return null;
    }

    /*
     * Delete Account by ID
     * Input: 1 int
     * OUTPUT: Account object
     * Deletes the Account with the given ID from the DB and returns the now-deleted Account
     * Returns null if unsuccessful
    */
    public Account deleteAccount(int id){
        // Establish connection to DB
        Connection connection = ConnectionUtil.getConnection();

        // SQL Logic
        try{
            // Acquire Account data before deletion
            Account deletedAccount = selectAccountById(id);

            // Check if Account exists
            if(deletedAccount != null){
                String sql = "DELETE FROM accounts WHERE accountId = ?";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, id);

                // Check if successful
                if(ps.executeUpdate() > 0){
                    return deletedAccount;
                }
            }
        }
        catch(SQLException e){
            System.out.println("Failed to delete Account by ID");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // Return null if unsuccessful
        return null;
    }

    /*
     * Extract Account from ResultSet Helper Method
     * INPUT: 1 ResultSet
     * OUTPUT: Account Object
     * Retrieves the first Account from a given ResultSet
    */
    private Account extractAccountFromResultSet(ResultSet rs) throws SQLException{
        Account user = new Account();

        user.setAccountId(rs.getInt("accountId"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));

        return user;
    }
}
