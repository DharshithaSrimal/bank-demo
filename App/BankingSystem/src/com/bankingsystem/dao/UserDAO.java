/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankingsystem.dao;

import static com.bankingsystem.dao.AccountDAO.DRIVER;
import com.bankingsystem.model.Account;
import com.bankingsystem.model.CommonAccount;
import com.bankingsystem.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author DHARSHITHA
 */
public class UserDAO {

    Connection conn = null; // Manages connion
    Statement statement = null; // Query statement
    PreparedStatement ps; //Prepared statement
    ResultSet rs; //Result set

    static final String DRIVER = "com.mysql.jdbc.Driver"; //Driver
    private static final String SELECT_ACCOUNT_BY_USERNAME = "SELECT * FROM user WHERE username = ? AND password = ?";
    static final String DATABASE_URL = "jdbc:mysql://localhost/bank"; //JDBC
    private static final String INSERT_USER_SQL = "INSERT INTO user(username, password, role) VALUES (? , ?, ?)";
    private static final String SELECT_USER_BY_USERNAME = "SELECT * FROM user WHERE username = ?";
     private static final String DELETE_USER_SQL = "delete from user where username = ?;";
   

    public String checkPassword(String username, String password) throws ClassNotFoundException, SQLException {
        String role = "";
        User user = null;

        Class.forName(DRIVER);
        conn = DriverManager.getConnection(DATABASE_URL, "root", "");
        statement = conn.createStatement();
        PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ACCOUNT_BY_USERNAME);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        System.out.println(preparedStatement);
        // Execute the query or update query
        ResultSet rs = preparedStatement.executeQuery();

        // Process the ResultSet object.
        while (rs.next()) {
            role = rs.getString("role");
        }

        return role;
    }

     //Create account
    public void createUser(User user) throws ClassNotFoundException {

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_USER_SQL);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, "Cashier");
           
            System.out.println(preparedStatement);
            // Execute the query or update query
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    
    //View account by ID
    public User selectAccount(String username) throws ClassNotFoundException {

        User user = null;
        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_USER_BY_USERNAME);
            preparedStatement.setString(1, username);
            System.out.println(preparedStatement);
            // Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Process the ResultSet object.
            while (rs.next()) {
                String un = rs.getString("username");
                String pw = rs.getString("password");
                String role = rs.getString("role");
                user = new User(username, pw, role);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }
    
     //Delete account
    public boolean deleteAccount(String username) throws ClassNotFoundException {
        boolean rowDeleted = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER_SQL);
            preparedStatement.setString(1, username);

            System.out.println(preparedStatement);
            // Execute the query or update query
            rowDeleted = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }
    
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
