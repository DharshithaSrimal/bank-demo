/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankingsystem.dao;

import com.bankingsystem.model.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author DHARSHITHA
 */
public class AccountDAO {
    static final String jdbcDriver = "com.mysql.jdbc.Driver"; //Driver
    //static final String DATABASE_URL1 = "jdbc:oracle:thin:@localhost:1521:orcl", "gebre", "gebre12";
    static final String jdbcURL = "jdbc:mysql://localhost/bank"; //JDBC
    static final String jdbcUsername = "root"; //JDBC
    static final String jdbcPassword = "";
    Connection conn = null; // Manages connion
    Statement statement = null; // Query statement
    PreparedStatement ps; //Prepared statement
    ResultSet rs; //Result set
    
    
    static final String DRIVER = "com.mysql.jdbc.Driver"; //Driver
    //static final String DATABASE_URL1 = "jdbc:oracle:thin:@localhost:1521:orcl", "gebre", "gebre12";
    static final String DATABASE_URL = "jdbc:mysql://localhost/bank"; //JDBC

    private static final String INSERT_ACCOUNT_SQL = "INSERT INTO tblAccount(acct_no, customer_name, sex, branch, initial_balance) VALUES (? , ?, ?, ?, ?)";
    private static final String SELECT_ACCOUNT_BY_ID =  "SELECT * FROM tblAccount WHERE acct_no = ?";
    private static final String SELECT_ALL_ACCOUNTS = "SELECT acct_no, customer_name, deposit, withdraw, balance, date FROM transactions ORDER BY date DESC";
    private static final String DELETE_ACCOUNT_SQL = "delete from users where id = ?;";
    private static final String UPDATE_ACCOUNT_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
	
    protected Connection getConnection(){
	Connection connection = null;
	try{
            Class.forName("jdbcDriver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
	}catch(SQLException e){
            e.printStackTrace();
	}catch(ClassNotFoundException e){
            e.printStackTrace();
	}
	return connection;
    }
    
    //View account by ID
    public Account selectAccount(String selectedAccount) throws ClassNotFoundException {

		Account account = null;	
		try {
                   
                    Class.forName(DRIVER);
                    conn = DriverManager.getConnection(DATABASE_URL, "root", "");
                    statement = conn.createStatement();
                    PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ACCOUNT_BY_ID);
                    preparedStatement.setString(1, selectedAccount);
                    System.out.println(preparedStatement);
                    // Execute the query or update query
                    ResultSet rs = preparedStatement.executeQuery();

                    // Process the ResultSet object.
                    while (rs.next()) {
			String accountNo = rs.getString("acct_no");
			String customerName = rs.getString("customer_name");
			String sex = rs.getString("sex");
			String branch = rs.getString("branch");
                        double initialBalance = rs.getDouble("initial_balance");
			account = new Account(accountNo, customerName, sex, branch, initialBalance);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return account;
	}
    
    //Create account
    public void createAccount(Account account) throws ClassNotFoundException {

		try {
                    Class.forName(DRIVER);
                    conn = DriverManager.getConnection(DATABASE_URL, "root", "");
                    statement = conn.createStatement();
                    PreparedStatement preparedStatement = conn.prepareStatement(INSERT_ACCOUNT_SQL);
                    preparedStatement.setString(1, account.getAcctNo());
                    preparedStatement.setString(2, account.getCustomerName());
                    preparedStatement.setString(3, account.getSex());
                    preparedStatement.setString(4, account.getBranch());
                    preparedStatement.setDouble(5, account.getInitialBalance());
                    System.out.println(preparedStatement);
                    // Execute the query or update query
                    preparedStatement.executeUpdate();

		} catch (SQLException e) {
			printSQLException(e);
		}
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
