package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    //no args construtor for creating a new AccountService with a new AccountDAO 
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    // login 
    public List<Account> Login(String username, String password){
        return accountDAO.login(username, password);
    }
    // using AccountDAO to persist an account 
    public Account addAccount(Account account){
        if( account.getPassword().length()<4 || account.getUsername()==""|| accountDAO.getAccountByUsername(account.getUsername())!= null){
            return null;
        }
        
        return accountDAO.insertAccount(account);
    }
    
}
