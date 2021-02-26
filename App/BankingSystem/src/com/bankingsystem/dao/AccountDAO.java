/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankingsystem.dao;

/**
 *
 * @author DHARSHITHA
 */
public class AccountDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/userdb?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";
	private String jdbcDriver = "com.mysql.jdbc.Driver";

	private static final String INSERT_ACCOUNT_SQL = "INSERT INTO users" + "  (name, email, country) VALUES "
			+ " (?, ?, ?);";
	private static final String SELECT_ACCOUNT_BY_ID = "select id,name,email,country from users where id =?";
	private static final String SELECT_ALL_ACCOUNTS = "select * from users";
	private static final String DELETE_ACCOUNT_SQL = "delete from users where id = ?;";
	private static final String UPDATE_ACCOUNT_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
	
    
}
