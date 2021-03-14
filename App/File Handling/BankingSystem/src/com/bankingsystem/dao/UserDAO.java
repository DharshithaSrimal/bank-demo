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
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.NumberFormatException;

/**
 *
 * @author 
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
    private static final String UPDATE_CASHIER_SQL = "UPDATE user SET username = ?,  password = ? WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT id, username, role FROM user";

    public String checkPassword(String username, String password) throws ClassNotFoundException, SQLException, IOException {
        String role = "";
        User user = null;
        String nameNumberString;
        String un;
        String pw;
        long number;
        int index;

        // Using file pointer creating the file.
        File file = new File("AccountUsers.txt");
        if (!file.exists()) {
            // Create a new file if not exists.
            file.createNewFile();
            System.out.println("File not found");
        }
        else{
            System.out.println("Files found");
        }

        // Opening file in reading and write mode.
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
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
            un = lineSplit[0];
            pw = lineSplit[1];
            role = lineSplit[2];
            if(username.equals(un) && ("Manager".equals(role) || "Cashier".equals(role)) && password.equals(pw)){
                return role;
            }
            //Print the contact data
            System.out.println("Username: " + un + "\n" + "Contact Number: " + pw + "\n");
        }
      
        role = "";
        return role;
    }

    //Create account
    public void createUser(User user) throws ClassNotFoundException {

        /*try {
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
        }*/
        try {
            String[] data = null;
            String nameNumberString;

            String username = user.getUsername();
            String password = user.getPassword();
            String role = "Cashier";

            // Using file pointer creating the file.
            File file = new File("AccountUsers.txt");

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
            nameNumberString = username + "!" + password + "!" + role;

            // writeBytes function to write a string
            // as a sequence of bytes.
            raf.writeBytes(nameNumberString);

            // To insert the next record in new line.
            raf.writeBytes(System.lineSeparator());

            // Print the message
            System.out.println(" Cashier Account Added. ");

            // Closing the resources.
            raf.close();
        } catch (IOException ioe) {

            System.out.println(ioe);
        } catch (NumberFormatException nef) {

            System.out.println(nef);
        }
    }

    //View account by ID
    public User selectAccount(String username) throws ClassNotFoundException {

        User account = null;
        try {
            String nameNumberString;

            String un;
            String pw;
            String role;
        
            // Using file pointer creating the file.
            File file = new File("AccountUsers.txt");

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
                un = lineSplit[0];
                pw = lineSplit[1];
                role = lineSplit[2];

                if (username.equals(un)) {
                    account = new User(un, pw, role);
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

    //Delete account
    public boolean deleteAccount(String username) throws ClassNotFoundException {
        boolean rowDeleted = false;
        /*try {
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
        }*/
        try {
            String[] data = null;

            // Get the name of the contact to be updated
            // from the Command line argument
            String nameNumberString;
            String un;
            long number;
            int index;

            // Using file pointer creating the file.
            File file = new File("AccountUsers.txt");

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
                un = lineSplit[0];

                // if condition to find existence of record.
                if (un.equals(username)) {
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
                    un = nameNumberString.substring(0, index);

                    // Check if the fetched contact
                    // is the one to be deleted
                    if (un.equals(username)) {

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
    //Update account

    public void updateCashier(User cashier) throws ClassNotFoundException {
        /*try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, "root", "");
            statement = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_CASHIER_SQL);
            preparedStatement.setString(1, cashier.getUsername());
            preparedStatement.setString(2, cashier.getPassword());
            preparedStatement.setInt(3, cashier.getId());
            System.out.println(preparedStatement);

            // Execute the query or update query
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }*/
        
        try {
            String un = cashier.getUsername();
            String pw = cashier.getPassword();
            String role = "Cashier";
            String nameNumberString;

            int index = 0;
            int indexEnd = 0;

            File file = new File("AccountUsers.txt");
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
                un = lineSplit[0];
                // if condition to find existence of record.
                if (un.equals(cashier.getUsername())) {
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
                    un = nameNumberString.substring(0, index);

                    // Check if the fetched contact
                    // is the one to be updated
                    if (un.equals(cashier.getUsername())) {
                        System.out.println(un);
                        indexEnd = nameNumberString.indexOf('!', index + 1);
                        System.out.println(indexEnd);
                        // Update the number of this contact
                        nameNumberString = un + "!" + pw + "!" + role;

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

                System.out.println(" User Account Updated. ");
            } else {

                // Closing the resources.
                raf.close();

                // Print the message
                System.out.println(" User account" + " does not exists. ");
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (NumberFormatException nef) {
            System.out.println(nef);
        }
    }

    //View aall accounts
    public ArrayList<User> viewAllUsers() throws ClassNotFoundException {

        ArrayList<User> users;
        users = new ArrayList<User>();
      
        User user = null;
        try {
            String nameNumberString;
            
            int id = 1;
            String username;
            String role;
            
            // Using file pointer creating the file.
            File file = new File("AccountUsers.txt");

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
                username = lineSplit[0];
                role = lineSplit[2];
                
                users.add(new User(id, username, role));
                System.out.println(users);
                id++;
            }
    
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (NumberFormatException nef) {
            System.out.println(nef);
        }
        
        return users;
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
