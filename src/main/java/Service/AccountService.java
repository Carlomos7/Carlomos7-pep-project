package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account register(Account account){
        String username = account.getUsername();
        String password = account.getPassword();

        if(username == null || username.isBlank()){
            return null;
        }
        if (password == null || password.length() < 4){
            return null;
        }
        boolean duplicateUsername = accountDAO.getAccountByUsername(username) != null;
        if(duplicateUsername){
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    public Account login(String username, String password){
        return accountDAO.getAccountByUsernameAndPassword(username, password);
    }
}