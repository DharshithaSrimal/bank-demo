/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankingsystem.dao;

import static com.bankingsystem.dao.AccountDAO.DRIVER;
import com.bankingsystem.model.Account;
import com.bankingsystem.model.Transaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author DHARSHITHA
 */
public class TransactionDAO {

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
    private static final String SELECT_TRANSACTION_BY_ID = "SELECT * FROM transactions WHERE acct_no = ? ORDER BY date DESC";
    private static final String SELECT_ALL_TRANSACTIONS = "SELECT * FROM transactions ORDER BY date DESC";
    private static final String DELETE_ACCOUNT_SQL = "delete from users where id = ?;";
    private static final String UPDATE_ACCOUNT_SQL = "UPDATE  tblAccount SET initial_balance = ? WHERE acct_no = ?";
    private static final String DEPOSIT_MONEY_SQL = "INSERT INTO transactions (acct_no, customer_name, deposit,withdraw, balance, date) VALUES(?, ?, ?, ?, ?, CURDATE())";
    
    public List<Transaction> selectTransactionsById(String selectedAccount) throws ClassNotFoundException {

        ArrayList<Transaction> transactions;
        transactions = new ArrayList<Transaction>();
        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_TRANSACTION_BY_ID);
            preparedStatement.setString(1, selectedAccount);
            System.out.println(preparedStatement);
            // Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Process the ResultSet object.
            while (rs.next()) {
                String accountNo = rs.getString("acct_no");
                String customerName = rs.getString("customer_name");
                double deposit = rs.getDouble("deposit");
                double withdraw = rs.getDouble("withdraw");
                double balance = rs.getDouble("balance");
                Date date = rs.getDate("date");
                transactions.add(new Transaction(accountNo, customerName, deposit, withdraw, balance, date));
               
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return transactions;
    }
    
    public List<Transaction> viewAllTransactions() throws ClassNotFoundException {

        ArrayList<Transaction> transactions;
        transactions = new ArrayList<Transaction>();
        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_TRANSACTIONS);
            System.out.println(preparedStatement);
            // Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Process the ResultSet object.
            while (rs.next()) {
                String accountNo = rs.getString("acct_no");
                String customerName = rs.getString("customer_name");
                double deposit = rs.getDouble("deposit");
                double withdraw = rs.getDouble("withdraw");
                double balance = rs.getDouble("balance");
                Date date = rs.getDate("date");
                transactions.add(new Transaction(accountNo, customerName, deposit, withdraw, balance, date));
               
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return transactions;
    }
   
    public void makeTransaction(Transaction transaction) throws ClassNotFoundException{
    try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();
            //Insert transaction data
            PreparedStatement preparedStatement = conn.prepareStatement(DEPOSIT_MONEY_SQL);
            preparedStatement.setString(1, transaction.getAcctNo());
            preparedStatement.setString(2, transaction.getCustomerName());
            preparedStatement.setDouble(3, transaction.getDeposit());
            preparedStatement.setDouble(4, transaction.getWithdraw());
            preparedStatement.setDouble(5, transaction.getBalance());
            //preparedStatement.setDate(6, (java.sql.Date) transaction.getDate());
            System.out.println(preparedStatement);

            // Execute the query or update query
            preparedStatement.executeUpdate();          

        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    
    public void updateAccount(String acctNo, double balance) throws ClassNotFoundException{
     try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();
       
            //Update account balance
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_ACCOUNT_SQL);
            preparedStatement.setString(1, acctNo);
            preparedStatement.setDouble(2, balance);
            
            //Execute the query or update query
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
