package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;


public class AccountDAO {
    public Account insertAccount(Account account){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                return new Account(id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsername(String username){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsernameAndPassword(String username, String password){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int id){
                Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
