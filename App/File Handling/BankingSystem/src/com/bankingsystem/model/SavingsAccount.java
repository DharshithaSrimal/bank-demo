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
public class SavingsAccount extends Account{
        
    public SavingsAccount(String acctNo, String customerName, String sex, String branch, String accountType, double initialBalance) {
	super(acctNo, customerName, sex, branch, accountType, initialBalance);
    }
    
}
