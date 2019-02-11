package com.revature.project0.BankAssignment;

import java.util.Scanner;
import java.sql.Connection;

import com.revature.project0.model.BankMember;
import com.revature.project0.util.ConnectionUtil;

/**
 * Hello world!
 *
 */
public class App 
{
	static final Scanner sc = new Scanner(System.in);
	
    public static void main(String[] args) {
    	Connection test = ConnectionUtil.getConnection();
    	Pages.landingPage();
    }
}