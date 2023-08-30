// AUTHOR: Sebastien Yokoyama
// FILE NAME: AccountDAO.java
/* FILE DESCRIPTION: Interface that defines CRUD behavior for user account data.
*/

package DAO;

import Model.Account;

import java.util.List;

public interface AccountDAO {
    // Create Account
    public Account insertAccount(Account user);

    // Retrieve Account by ID
    public Account selectAccountById(int id);

    // Retrieve Account by username & password
    public Account selectAccountByUserAndPass(String username, String password);

    // Retrieve all Accounts
    public List<Account> selectAllAccounts();

    // Update Account
    public Account updateAccount(Account user);

    // Delete Account by ID
    public Account deleteAccount();
}
