/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankingsystem.model;

/**
 *
 * @author 
 */
public abstract class Account {
    private String acctNo;
    private String customerName;
    private String sex;
    private String branch;
    private String accountType;
    private double initialBalance;

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

     public String getAcctNo() {
        return acctNo;
    }
     
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
     public String getCustomerName() {
        return customerName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

     public String getSex() {
        return sex;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranch() {
        return branch;
    }
    
    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }
    

    public Account(String acctNo, String customerName, String sex, String branch, double initialBalance) {
	super();
	this.acctNo = acctNo;
        this.customerName = customerName;
        this.sex = sex;
        this.branch = branch;
        this.initialBalance = initialBalance;
    }
    
    public Account(String acctNo, String customerName, String sex, String branch, String accountType, double initialBalance) {
	super();
	this.acctNo = acctNo;
        this.customerName = customerName;
        this.sex = sex;
        this.branch = branch;
        this.initialBalance = initialBalance;
        this.accountType = accountType;
    }
    
}
