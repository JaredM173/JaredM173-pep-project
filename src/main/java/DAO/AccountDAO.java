package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    //insert an Account to Account table 
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account(username, password) VALUES (?,?);";
            PreparedStatement PS = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            PS.setString(1, account.getUsername());
            PS.setString(2, account.getPassword());
            PS.executeUpdate();
            ResultSet pkeyResultSet = PS.getGeneratedKeys();
            if (pkeyResultSet.next()){
               int generated_account_id = (int) pkeyResultSet.getLong(1);
               return new Account(generated_account_id, account.getUsername(), account.getPassword());  
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return account;
    }

    // get account by account username
    public Account getAccountByUsername(String Username){
        Connection connection = ConnectionUtil.getConnection();
        
        try{
            String sql = "SELECT * FROM account WHERE username=?";
            PreparedStatement PS = connection.prepareStatement(sql);

            PS.setString(1, Username);
            ResultSet rs = PS.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                                rs.getString("username"), 
                                rs.getString("password"));
                            return account;
        }}catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }
    // valid login
    public List<Account> login(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try{
            String sql = "SELECT username, password FROM account WHERE username=? AND password=?; ";
            PreparedStatement PS = connection.prepareStatement(sql);

            PS.setString(1, username);
            PS.setString(2, password);
            ResultSet rs = PS.executeQuery();
            while (rs.next()){
                Account account = new Account(rs.getString("username"),
                                rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;

    }
}
