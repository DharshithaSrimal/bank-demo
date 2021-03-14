/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankingsystem.dao;

import com.bankingsystem.model.Account;
import com.bankingsystem.model.CommonAccount;
import java.sql.SQLException;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.NumberFormatException;

/**
 *
 * @author 
 */
public class AccountDAO {

    //View account by ID
    public Account selectAccount(String selectedAccount) throws ClassNotFoundException, IOException {

        Account account = null;
        try {
            String nameNumberString;

            String accountNo;
            String cusName;
            String gender;
            String branch;
            String accType;
            double balance;
            // Using file pointer creating the file.
            File file = new File("Accounts.txt");

            if (!file.exists()) {
                // Create a new file if not exists.
                file.createNewFile();
            }

            // Opening file in reading and write mode.
            RandomAccessFile raf
                    = new RandomAccessFile(file, "rw");
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
                accountNo = lineSplit[0];
                cusName = lineSplit[1];
                gender = lineSplit[2];
                branch = lineSplit[3];
                accType = lineSplit[4];
                balance = Double.parseDouble(lineSplit[5]);

                if (selectedAccount.equals(accountNo)) {
                    account = new CommonAccount(accountNo, cusName, gender, branch, accType, balance);
                    return account;
                }
            }
            account = null;
            return account;
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (NumberFormatException nef) {
            System.out.println(nef);
        }
        return account;
    }

    //View aall accounts
    public ArrayList<Account> viewAllAccounts() throws ClassNotFoundException {

        ArrayList<Account> accounts;
        accounts = new ArrayList<Account>();
    
        Account account = null;
        try {
            String nameNumberString;

            String accountNo;
            String cusName;
            String gender;
            String branch;
            String accType;
            double balance;
            // Using file pointer creating the file.
            File file = new File("Accounts.txt");

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
                accountNo = lineSplit[0];
                cusName = lineSplit[1];
                gender = lineSplit[2];
                branch = lineSplit[3];
                accType = lineSplit[4];
                balance = Double.parseDouble(lineSplit[5]);

                accounts.add(new CommonAccount(accountNo, cusName, gender, branch, accType, balance));
            }
    
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (NumberFormatException nef) {
            System.out.println(nef);
        }
        return accounts;
    }

    //Create account
    public void createAccount(Account account) throws ClassNotFoundException {

        try {
            String[] data = null;
            String nameNumberString;

            String accountNo = account.getAcctNo();
            String cusName = account.getCustomerName();
            String gender = account.getSex();
            String branch = account.getBranch();
            String accType = account.getAccountType();
            double balance = account.getInitialBalance();

            // Using file pointer creating the file.
            File file = new File("Accounts.txt");

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
            nameNumberString = accountNo + "!" + cusName + "!" + gender + "!" + branch + "!" + accType + "!" + balance;

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

    //Update account
    public void updateAccount(Account account) throws ClassNotFoundException, IOException {
        try {
            String accountNo = account.getAcctNo();
            String cusName = account.getCustomerName();
            String gender = account.getSex();
            String branch = account.getBranch();
            String accType = account.getAccountType();
            double balance = account.getInitialBalance();
            String nameNumberString;

            int index = 0;
            int indexEnd = 0;

            File file = new File("Accounts.txt");
            if (!file.exists()) {
                // Create a new file if not exists.
                file.createNewFile();
            }

            // Opening file in reading and write mode.
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;

            // Checking whether the account already exists.
            // getFilePointer() give the current offset
            // value from start of the file.
            while (raf.getFilePointer() < raf.length()) {

                // reading line from the file.
                nameNumberString = raf.readLine();

                // splitting the string to get Account Number
                String[] lineSplit = nameNumberString.split("!");

                // separating name and number.
                //accountNo = lineSplit[0];
                // if condition to find existence of record.
                if (accountNo.equals(account.getAcctNo())) {
                    System.out.println("found truee");
                    found = true;
                    break;
                }
            }
            if (found == true) {
                // Creating a temporary file
                // with file pointer as tmpFile.
                File tmpFile = new File("Temp.txt");

                // Opening this temporary file
                // in ReadWrite Mode
                RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");

                // Set file pointer to start
                raf.seek(0);

                // Traversing the friendsContact.txt file
                while (raf.getFilePointer() < raf.length()) {

                    // Reading the contact from the file
                    nameNumberString = raf.readLine();
                    System.out.println(nameNumberString);
                    index = nameNumberString.indexOf('!');
                    System.out.println("index:" + index);
                    accountNo = nameNumberString.substring(0, 5);

                    // Check if the fetched contact
                    // is the one to be updated
                    if (accountNo.equals(account.getAcctNo())) {
                        System.out.println(accountNo);
                        indexEnd = nameNumberString.indexOf('!', index + 1);
                        System.out.println(indexEnd);
                        System.out.println(cusName);
                        // Update the number of this contact
                        nameNumberString = accountNo + "!" + cusName + "!" + gender + "!" + branch + "!" + accType + "!" + balance;

                    }
                    // Add this contact in the temporary
                    // file
                    tmpraf.writeBytes(nameNumberString);

                    // Add the line separator in the
                    // temporary file
                    tmpraf.writeBytes(System.lineSeparator());
                }

                // The contact has been updated now
                // So copy the updated content from
                // the temporary file to original file.
                // Set both files pointers to start
                raf.seek(0);
                tmpraf.seek(0);

                // Copy the contents from
                // the temporary file to original file.
                while (tmpraf.getFilePointer() < tmpraf.length()) {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                }

                // Set the length of the original file
                // to that of temporary.
                raf.setLength(tmpraf.length());

                // Closing the resources.
                tmpraf.close();
                raf.close();

                // Deleting the temporary file
                tmpFile.delete();

                System.out.println(" Account updated. ");
            } else {

                // Closing the resources.
                raf.close();

                // Print the message
                System.out.println(" Account" + " does not exists. ");
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (NumberFormatException nef) {
            System.out.println(nef);
        }
    }
//Delete account

    public boolean deleteAccount(Account account) throws ClassNotFoundException {
        boolean rowDeleted = false;
        try {
            String[] data = null;

            // Get the name of the contact to be updated
            // from the Command line argument
            String nameNumberString;
            String name;
            long number;
            int index;

            // Using file pointer creating the file.
            File file = new File("Accounts.txt");

            if (!file.exists()) {
                // Create a new file if not exists.
                file.createNewFile();
            }

            // Opening file in reading and write mode.
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;

            // Checking whether the name of contact exists.
            // getFilePointer() give the current offset
            // value from start of the file.
            while (raf.getFilePointer() < raf.length()) {

                // reading line from the file.
                nameNumberString = raf.readLine();
                System.out.println(nameNumberString);
                // splitting the string to get name and
                // number
                String[] lineSplit = nameNumberString.split("!");

                // separating name and number.
                name = lineSplit[0];

                // if condition to find existence of record.
                if (name.equals(account.getAcctNo())) {
                    found = true;
                    break;
                }
            }

            // Delete the contact if record exists.
            if (found == true) {

                // Creating a temporary file
                // with file pointer as tmpFile.
                File tmpFile = new File("Temp.txt");

                // Opening this temporary file
                // in ReadWrite Mode
                RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");

                // Set file pointer to start
                raf.seek(0);

                // Traversing the Accounts.txt file
                while (raf.getFilePointer() < raf.length()) {

                    // Reading the contact from the file
                    nameNumberString = raf.readLine();

                    index = nameNumberString.indexOf('!');
                    name = nameNumberString.substring(0, index);

                    // Check if the fetched contact
                    // is the one to be deleted
                    if (name.equals(account.getAcctNo())) {

                        // Skip inserting this contact
                        // into the temporary file
                        continue;
                    }

                    // Add this contact in the temporary
                    // file
                    tmpraf.writeBytes(nameNumberString);

                    // Add the line separator in the
                    // temporary file
                    tmpraf.writeBytes(System.lineSeparator());
                }

                // The contact has been deleted now
                // So copy the updated content from
                // the temporary file to original file.
                // Set both files pointers to start
                raf.seek(0);
                tmpraf.seek(0);

                // Copy the contents from
                // the temporary file to original file.
                while (tmpraf.getFilePointer() < tmpraf.length()) {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                }

                // Set the length of the original file
                // to that of temporary.
                raf.setLength(tmpraf.length());

                // Closing the resources.
                tmpraf.close();
                raf.close();

                // Deleting the temporary file
                tmpFile.delete();

                System.out.println(" Account deleted. ");
                rowDeleted = true;
            } // The contact to be deleted
            // could not be found
            else {

                // Closing the resources.
                raf.close();

                // Print the message
                System.out.println(" Account does not exists. ");
                rowDeleted = false;
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
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
