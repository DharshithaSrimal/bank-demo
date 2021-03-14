/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankingsystem.dao;

import com.bankingsystem.model.Transaction;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 
 */
public class TransactionDAO {

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
