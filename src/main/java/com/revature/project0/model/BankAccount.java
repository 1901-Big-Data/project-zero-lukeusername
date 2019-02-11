package com.revature.project0.model;

import java.io.Serializable;

public class BankAccount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1822101841834902121L;
	/**
	 * 
	 */
	private int account_id;
	private double balance;
	private String accountName;
	private int member_id;
	
	public BankAccount(int account_id, double balance, String accountName, int member_id) {
		super();
		this.account_id = account_id;
		this.balance = balance;
		this.accountName = accountName;
		this.member_id = member_id;
	}
	public BankAccount() {
		super();
		// TODO Auto-generated constructor stub
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
}
