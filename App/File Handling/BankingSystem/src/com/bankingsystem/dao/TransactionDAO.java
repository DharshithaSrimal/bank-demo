/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankingsystem.dao;

import static com.bankingsystem.dao.AccountDAO.DRIVER;
import com.bankingsystem.model.Account;
import com.bankingsystem.model.Transaction;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 
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

        Transaction transaction = null;
        try {
            String nameNumberString;

            String acctNo;
            String customerName;
            double deposit;
            double withdraw;
            double balance;
            Date date;

            // Using file pointer creating the file.
            File file = new File("Transactions.txt");

            if (!file.exists()) {
                // Create a new file if not exists.
                file.createNewFile();
            }

            // Opening file in reading and write mode.
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;

            // Traversing the file
            // getFilePointer() give the current offset
            // value from start of the file.
            while (raf.getFilePointer() < raf.length()) {

                // reading line from the file.
                nameNumberString = raf.readLine();

                // splitting the string to get name and
                // number
                String[] lineSplit = nameNumberString.split("!");

                // separating name and number.                
                acctNo = lineSplit[0];
                customerName = lineSplit[1];
                deposit = Double.parseDouble(lineSplit[2]);
                withdraw = Double.parseDouble(lineSplit[3]);
                balance = Double.parseDouble(lineSplit[4]);

                //date = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(lineSplit[5]);    
                if (selectedAccount.equals(acctNo)) {
                    transactions.add(new Transaction(acctNo, customerName, deposit, withdraw, balance));
                    System.out.println(transactions);
                    return transactions;
                }
            }

        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (NumberFormatException nef) {
            System.out.println(nef);
        }
        transactions = null;
        return transactions;
    }

    public List<Transaction> viewAllTransactions() throws ClassNotFoundException, ParseException {

        ArrayList<Transaction> transactions;
        transactions = new ArrayList<Transaction>();

        Transaction transaction = null;
        try {
            String nameNumberString;

            String acctNo;
            String customerName;
            double deposit;
            double withdraw;
            double balance;
            Date date;

            // Using file pointer creating the file.
            File file = new File("Transactions.txt");

            if (!file.exists()) {
                // Create a new file if not exists.
                file.createNewFile();
            }

            // Opening file in reading and write mode.
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;

            // Traversing the file
            // getFilePointer() give the current offset
            // value from start of the file.
            while (raf.getFilePointer() < raf.length()) {

                // reading line from the file.
                nameNumberString = raf.readLine();

                // splitting the string to get name and
                // number
                String[] lineSplit = nameNumberString.split("!");

                // separating name and number.                
                acctNo = lineSplit[0];
                customerName = lineSplit[1];
                deposit = Double.parseDouble(lineSplit[2]);
                withdraw = Double.parseDouble(lineSplit[3]);
                balance = Double.parseDouble(lineSplit[4]);

                //date = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(lineSplit[5]);    
                transactions.add(new Transaction(acctNo, customerName, deposit, withdraw, balance));
                System.out.println(transactions);

            }

        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (NumberFormatException nef) {
            System.out.println(nef);
        }

        return transactions;
    }

    public void makeTransaction(Transaction transaction) throws ClassNotFoundException, IOException {
        try {

            String[] data = null;
            String nameNumberString;

            String acctNo = transaction.getAcctNo();
            String customerName = transaction.getCustomerName();
            double deposit = transaction.getDeposit();
            double withdraw = transaction.getWithdraw();
            double balance = transaction.getBalance();
            Date date = new Date();

            File file = new File("Transactions.txt");

            if (!file.exists()) {
                // Create a new file if not exists.
                file.createNewFile();
            }
            long fileLength = file.length();
            // Opening file in reading and write mode.
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(fileLength);
            System.out.println("In");
            // Enter the if block when a record
            // is not already present in the file.
            nameNumberString = acctNo + "!" + customerName + "!" + deposit + "!" + withdraw + "!" + balance + "!" + balance + "!" + date;

            // writeBytes function to write a string
            // as a sequence of bytes.
            raf.writeBytes(nameNumberString);

            // To insert the next record in new line.
            raf.writeBytes(System.lineSeparator());

            // Print the message
            System.out.println(" Acount added. ");

            // Closing the resources.
            raf.close();

        } catch (IOException ioe) {

            System.out.println(ioe);
        } catch (NumberFormatException nef) {

            System.out.println(nef);
        }
    }

    public void updateAccount(String acctNo, double balance) throws ClassNotFoundException {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();

            //Update account balance
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_ACCOUNT_SQL);
            preparedStatement.setDouble(1, balance);
            preparedStatement.setString(2, acctNo);
            System.out.println(preparedStatement);
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
