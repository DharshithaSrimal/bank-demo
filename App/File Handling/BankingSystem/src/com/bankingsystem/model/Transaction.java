/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankingsystem.model;

import java.util.Date;

/**
 *
 * @author DHARSHITHA
 */
public class Transaction {
    private String acctNo;
    private String customerName;
    private double deposit;
    private double withdraw;
    private double balance;
    private Date date;

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public void setWithdraw(double withdraw) {
        this.withdraw = withdraw;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getDeposit() {
        return deposit;
    }

    public double getWithdraw() {
        return withdraw;
    }

    public double getBalance() {
        return balance;
    }

    public Date getDate() {
        return date;
    }

    public Transaction(String acctNo, String customerName, double deposit, double withdraw, double balance, Date date) {
	super();
	this.acctNo = acctNo;
        this.customerName = customerName;
        this.deposit = deposit;
        this.withdraw = withdraw; 
        this.balance = balance;
        this.date = date;
    }
    
    public Transaction(String acctNo, double deposit, Date date) {
	super();
	this.acctNo = acctNo;
        this.deposit = deposit;
        this.date = date;
    }
    
    public Transaction(String acctNo, String customerName, double deposit, double withdraw, double balance) {
	super();
	this.acctNo = acctNo;
        this.customerName = customerName;
        this.deposit = deposit;
        this.withdraw = withdraw; 
        this.balance = balance;
    }
}
