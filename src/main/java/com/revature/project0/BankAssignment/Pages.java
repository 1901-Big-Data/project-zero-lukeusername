package com.revature.project0.BankAssignment;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.revature.project0.model.BankAccount;
import com.revature.project0.model.BankMember;
import com.revature.project0.service.*;

public class Pages {
	protected static BankMember session;

	// for routing between pages
	enum appPage { 
	    LOGIN, MANAGE_ACCOUNT, CREATE_ACCOUNT, DELETE_ACCOUNT, EXIT, REGISTER, DONOTHING; 
	}
	
	protected static void landingPage() {
    	boolean keepRunning = true;
        boolean verifiedInput = false;
    	appPage page = appPage.LOGIN;
    	int nav;
    	
    	do {
            System.out.println( "- - - - - - - - - - - - - -\n\nWelcome to LukeBank!\n\nType '1', '2', or '3'"
            		+ "\n 1. Login"
            		+ "\n 2. Register" 
            		+ "\n 3. Exit Application");
            
            // validating input so that no exceptions are thrown
            do {

            	// it must be an int
    	        while (!App.sc.hasNextInt()) {
    	        	System.out.println("Invalid input.  Only '1', '2', or '3' can be entered.");
    	        	App.sc.next();
    	        }
    	        
    	        // it must be one of the given options
    	        nav = App.sc.nextInt();
    	        if (nav < 1 || nav > 3) {
    	        	System.out.println("Invalid input.  Only '1', '2', or '3' can be entered.");
    	        }
    	        else {
    	        	verifiedInput = true;
    	        }
            }while(!verifiedInput);
            
            // convert user choice to page value
            if (nav == 1)
            	page = appPage.LOGIN;
            else if (nav == 2)
            	page = appPage.REGISTER;
            else
            	page = appPage.EXIT;
    		
            // page routing
            switch (page) {
                case LOGIN:  loginPage();
                         break;
                case REGISTER:  registerPage();
                         break;
                default:
                	keepRunning = false;
                	System.out.println("Closing application...");
            }
    	} while (keepRunning);
    	App.sc.close();
    }
    protected static int loginPage() {
    	String username = "", password = "";
    	boolean db_said_yes = false;
    	App.sc.nextLine();
    	do {
    		System.out.println("- - - - - - - - - - - - - -\n\nLOGIN PAGE\n"
	    			+ "(Type 'b' to go back to Main Page)\n\n"
	    			+ "Enter username:\n");
	    	
	    	// grab username
	        boolean verifiedInput = false;
	        do {
	        	username = App.sc.nextLine();
	        	username = username.toLowerCase();
		        if (username.length() < 6) {
		        	if (username.length() == 1 && username.charAt(0) == 'b') {
		        		return 0;
		        	}
	
		        	System.out.println("Username must be at least 6 characters long");
		        }
		        else {
		        	verifiedInput = true;
		        }
	        }while(!verifiedInput);
	        
	        System.out.println("\nEnter password:\n");
	        
	        // grab pass
	        verifiedInput = false;
	        do {
	        	password = App.sc.nextLine();
		        if (password.length() < 6) {
		        	if (password.length() == 1 && password.charAt(0) == 'b') {
		        		return 0;
		        	}
		        	System.out.println("Password must be at least 6 characters long");
		        }
		        else {
		        	verifiedInput = true;
		        }
	        }while(!verifiedInput);
	        
	        System.out.println("Attempting login...");
	        
	    	try {
	    		session = UserService.getService().login(username, password).get();
	    		if (session == null) {
	    			db_said_yes = false;
	    		}
	    		else
	    			db_said_yes = true;
	    	}
	    	catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	        
	        if (db_said_yes)
	        	System.out.println("Login successful.");
	        else {
	        	System.out.println("Username and password do not match.");
	        }
	        
    	} while(!db_said_yes);
    	
    	// ROUTE TO USER PAGE
    	accountsLandingPage();
    	
    	return 0;
    }
    protected static int registerPage() {
    	App.sc.nextLine();
    	boolean passwordMatches = false;
    	String username = "", password = "", passwordCheck = "", lastName = "", firstName = "", 
    			email = "", address = "", state = "", city = "";
    	
    	System.out.println("- - - - - - - - - - - - - -\n\nREGISTER PAGE\n"
    			+ "(Type 'b' to go back to Main Page)\n\n"
    			+ "Username Rules\n"
    			+ "  1. English alphabet only\n"
    			+ "  2. Minimum of 6 letters\n"
    			+ "  3. Username will be case-insensitive\n\n"
    			+ "Enter desired username:\n");
    	
    	
        boolean verifiedInput = false;
        do {
        	username = App.sc.nextLine();
        	username = username.toLowerCase();
	        if (username.length() < 6) {
	        	if (username.length() == 1 && username.charAt(0) == 'b') {
	        		return 0;
	        	}

	        	System.out.println("Username must be at least 6 characters long");
	        }
	        else {
	        	boolean usernameAvailable = UserService.getService().checkUsername(username).get();
	        	
	        	if (usernameAvailable) {
		        	verifiedInput = true;
		        	System.out.println("Username is available.");
	        	}
	        	else {
	        		System.out.println("Username not available.");
	        	}

	        }
        }while(!verifiedInput);
    	
        do {
	        System.out.println("Chosen username is available: " + username);
	        
	    	System.out.println("(Type 'b' to go back to Main Page)\n\n"
	    			+ "Password Rules\n"
	    			+ "  1. English alphabet only\n"
	    			+ "  2. Minimum of 6 letters\n"
	    			+ "  3. Password will be case-sensitive\n\n"
	    			+ "Enter desired password:");
	    	
	        verifiedInput = false;
	        do {
	        	password = App.sc.nextLine();
	        	if (password.length() < 6) {
		        	if (password.length() == 1 && password.charAt(0) == 'b') {
		        		return 0;
		        	}
	
		        	System.out.println("Password must be at least 6 characters long");
		        }
		        else {
		        	verifiedInput = true;
		        }
	        }while(!verifiedInput);
	       
	    	System.out.println("(Type 'b' to go back at any time)\n\n"
	    			+ "Re-enter password for verification:");
	    	
	    	passwordCheck = App.sc.nextLine();
	    	if (password.length() == 1 && password.charAt(0) == 'b') {
	    		return 0;
	    	}
	        if (passwordCheck.equals(password)) {
	        	System.out.println("Passwords match.");
	        	passwordMatches = true;
	        }
	        else {
	        	System.out.println("\nPasswords do not match!\n");
		    }
        } while(!passwordMatches);
        
    	lastName = grabValidString(2, "Last name");
    	firstName = grabValidString(2, "First name");
    	email = grabValidString(7, "Email");
    	address = grabValidString(5, "Address");
    	state = grabValidString(2, "State");
    	city = grabValidString(2, "City");
    	
    	try {
    		if(UserService.getService().register(username, password, false, lastName, firstName, email, address, state, city).get()) {
    			System.out.println("Account successfully created. You may log in at any time.");
    		}
    		else {
    			System.out.println("Registration failed.  Try again or contact system administrator if the problem persists.");
    		}
    	}
    	catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    	return 0;              
    }
    protected static int accountsLandingPage() {
    	boolean keepRunning = true;
        boolean verifiedInput = false;
    	appPage page = appPage.DONOTHING;
    	int nav;
    	
    	do {
            System.out.println( "- - - - - - - - - - - - - -\n\nYOUR BANKING PAGE\n\n"
            		+ "Logged in as: " + session.getUsername() + ". Please make a selection.\n\nType '1', '2', '3', or '4'"
            		+ "\n 1. View/Manage Bank Account(s)"
            		+ "\n 2. View Personal Information" 
            		+ "\n 3. Create Banking Account"
            		+ "\n 4. Delete Banking Account (must be empty)"
            		+ "\n 5. Logout");
            
            // validating input so that no exceptions are thrown
            do {
            	// it must be an int
    	        while (!App.sc.hasNextInt()) {
    	        	System.out.println("Invalid input.  Only '1', '2', '3', '4', or '5' can be entered.");
    	        	App.sc.next();
    	        }
    	        // it must be one of the given options
    	        nav = App.sc.nextInt();
    	        if (nav < 1 || nav > 5) {
    	        	System.out.println("Invalid input.  Only '1', '2', '3', '4', or '5' can be entered.");
    	        }
    	        else {
    	        	verifiedInput = true;
    	        }
            }while(!verifiedInput);
            
            // convert user choice to page value
            if (nav == 1)
            	page = appPage.MANAGE_ACCOUNT;
            else if (nav == 2) {
            	System.out.println("Member ID: " + session.getMemberId());
            	System.out.println("First name: " + session.getFirstName());
            	System.out.println("Last name: " + session.getLastName());
            	System.out.println("Email: " + session.getEmail());
            	System.out.println("State: " + session.getState());
            	System.out.println("City: " + session.getCity());
            	System.out.println("Address: " + session.getAddress());
            }
            else if (nav == 3)
            	page = appPage.CREATE_ACCOUNT;
            else if (nav == 4)
            	page = appPage.DELETE_ACCOUNT;
            else {
            	System.out.println("Logging out...");
            	session = null;
               	System.out.println("You have been successfully logged out.");
            	return 0;
            }

            // page routing
            switch (page) {
                case MANAGE_ACCOUNT:  manageAccountPage();
                         break;
                case CREATE_ACCOUNT:  createAccountPage();
                         break;
                case DELETE_ACCOUNT:  deleteAccountPage();
                	break;
			default:
				break;
            }
    	} while (keepRunning);
    	return 0;
    }
    protected static int manageAccountPage() {
    	NumberFormat formatter = NumberFormat.getCurrencyInstance();
    	String bal; 
    	System.out.println("- - - - - - - - - - - - - -\n\nVIEW/MANAGE ACCOUNTS\n"
    			+ "(Type 'b' to go back to YOUR BANKING PAGE)\n\n"
    			+ "Select the account you would like to view/manage:\n\n");
    	
    	// list all bank account info
    	
    	List<BankAccount> temp = null;
		try {
			temp = AccountService.getService().viewAccounts(session.getMemberId()).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	if (temp.isEmpty()) {
    		System.out.println("You currently have no accounts open.");
    	}
    	else {
    		System.out.println("You currently have " + temp.size() + " open account(s).");
    		for (int i = 0; i < temp.size(); i++) {
    			System.out.println("Account name: " + temp.get(i).getAccountName());
    			System.out.println("Account #: " + temp.get(i).getAccount_id());
    			bal = formatter.format(temp.get(i).getBalance());
    			System.out.println("Balance: " + bal + "\n");
    		}
    	}
    	
    	boolean keepRunning = true;
        boolean verifiedInput;
    	appPage page = appPage.DONOTHING;
    	int withOrDep;
    	int whichAcc = 1;
    	String selection;
    	
    	do {
            System.out.println( "\n(Type 'b' to go back to YOUR BANKING PAGE)\n"
            		+ "Logged in as: " + session.getUsername() + ". Please make a selection.\n\nType '1' or '2'"
            		+ "\n 1. Withdraw"
            		+ "\n 2. Deposit");
            
            verifiedInput=false;
            // retrieving / validating input
            do {
            	if (App.sc.hasNext("b"))
            		return 0;
            	// it must be an int
    	        while (!App.sc.hasNextInt()) {
    	        	
    	        	System.out.println("Invalid input.  Only '1' or '2' can be entered.");
    	        	App.sc.next();
    	        }
    	        // it must be one of the given options
    	        withOrDep = App.sc.nextInt();
    	        if (withOrDep < 1 || withOrDep > 2) {
    	        	System.out.println("Invalid input.  Only '1' or '2' can be entered.");
    	        }
    	        else {
    	        	verifiedInput = true;
    	        }
            }while(!verifiedInput);
            
            if (withOrDep == 1)
            	selection = "withdraw";
            else
            	selection = "deposit";
            
    		for (int i = 0; i < temp.size(); i++) {
    			System.out.println((i+1) + ".");
    			System.out.println("Account name: " + temp.get(i).getAccountName());
    			System.out.println("Account #: " + temp.get(i).getAccount_id());
    			bal = formatter.format(temp.get(i).getBalance());
    			System.out.println("Balance: " + bal + "\n");
    		}
    		
    		if (temp.size() == 1) {
    			System.out.println("You have one bank account.");
    		}
    		else {
    			verifiedInput=false;
	            System.out.println( "(Type 'b' to go back to YOUR BANKING PAGE)\n"
	            		+ "For which account would you like to " + selection + "?\n");
    			// retrieving / validating input
	            do {
	            	if (App.sc.hasNext("b"))
	            		return 0;
	            	// it must be an int
	    	        while (!App.sc.hasNextInt()) {
	    	        	System.out.println("Invalid input.  Only '1' through '" + temp.size() + "' may be entered.");
	    	        	App.sc.next();
	    	        }
	    	        // it must be one of the given options
	    	        whichAcc = App.sc.nextInt();
	    	        if (whichAcc < 1 || whichAcc > temp.size()) {
	    	        	System.out.println("Invalid input.  Only '1' through '" + temp.size() + "' may be entered.");
	    	        }
	    	        else {
	    	        	verifiedInput = true;
	    	        }
	            }while(!verifiedInput);
    		}
            System.out.println("How much would you like to " + selection + "?  Select a number.");
            
            verifiedInput=false;
            double money;
            do {
            	if (App.sc.hasNext("b"))
            		return 0;
            	// it must be an int
    	        while (!App.sc.hasNextDouble()) {
    	        	System.out.println("Must be a number.");
    	        	App.sc.next();
    	        }
    	        // it must be one of the given options
    	        money = App.sc.nextDouble();
    	        if (money <= 0) {
    	        	System.out.println("Must choose a value greater than $0.00");
    	        }
    	        else if (selection.equals("withdraw") && money > temp.get(whichAcc-1).getBalance()) {
    	        	System.out.println("You cannot withdraw more than the current balance.  Try a smaller amount.");
    	        }
    	        else {
    	        	verifiedInput = true;
    	        }
            }while(!verifiedInput);
            
            Double newBalance = 0.0;
            // variables: money, withOrDep, whichAcc
            // withdraw
            if (withOrDep == 1) {
            	try {
					newBalance = AccountService.getService().withdraw(temp.get(whichAcc-1).getAccount_id(), money).get();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            // deposit
            else if (withOrDep == 2) {
            	try {
					newBalance = AccountService.getService().deposit(temp.get(whichAcc-1).getAccount_id(), money).get();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            
            System.out.println("New balance: "  + formatter.format(newBalance));
            
            verifiedInput = false;
            String yesOrNo = "";
            System.out.println("Would you like to perform another transaction?\n\n     (Y/N)");
            do {
            	if (App.sc.hasNext("b"))
            		return 0;
            	// it must be an int            	
    	        else if (App.sc.hasNext("Y") || App.sc.hasNext("y") || App.sc.hasNext("n") || App.sc.hasNext("N")) {
    	        	verifiedInput = true;
    	        	yesOrNo = App.sc.next().toLowerCase();
    	        }
            }while(!verifiedInput);
            if (yesOrNo.equals("n"))
            	keepRunning = false;
    	} while (keepRunning);
    	return 0;
    }
    protected static int createAccountPage() {
    	String accountName = "";

    	System.out.println("- - - - - - - - - - - - - -\n\nCREATE ACCOUNT PAGE\n"
    			+ "(Type 'b' to go back to Main Page)\n\n"
    			+ "Account Naming Rules\n"
    			+ "  1. English alphabet only\n"
    			+ "  2. Minimum of 6 letters\n"
    			+ "  3. Account name will be case-insensitive\n\n"
    			+ "Enter desired account name:\n");
    	
        boolean verifiedInput = false;
        do {
        	accountName = App.sc.nextLine();
        	if (accountName.length() < 6) {
	        	if (accountName.length() == 1 && accountName.charAt(0) == 'b') {
	        		return 0;
	        	}

	        	System.out.println("Account name must be at least 6 characters long");
	        }
	        else {
	        	verifiedInput = true;
	        }
        }while(!verifiedInput);

        try {
			AccountService.getService().createAccount(session.getMemberId(),accountName).get();
		} catch (Exception e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
        
        System.out.println("Account has been created.");
        
    	return 0;
    }
    protected static int deleteAccountPage() {
    	System.out.println("- - - - - - - - - - - - - -\n\nDELETE ACCOUNT PAGE\n"
    			+ "(Type 'b' to go back to Main Page)\n\n"
    			+ "An account must have a balance of $0.00 in order to be eligible for deletion.\n");
    	
    	NumberFormat formatter = NumberFormat.getCurrencyInstance();
    	ArrayList<Integer> eligible = new ArrayList<Integer>();
    	boolean canDelete = false;
    	String bal;
    	List<BankAccount> temp = null;
		try {
			temp = AccountService.getService().viewAccounts(session.getMemberId()).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	if (temp.isEmpty()) {
    		System.out.println("You currently have no accounts open.");
    	}
    	else {
    		System.out.println("The follow are all accounts eligible for deletion:");
    		for (int i = 0; i < temp.size(); i++) {
    			if (temp.get(i).getBalance() == 0.0) {
    				System.out.println("(" + (i+1) + ")");
    				eligible.add(temp.get(i).getAccount_id());
	    			System.out.println("Account name: " + temp.get(i).getAccountName());
	    			System.out.println("Account #: " + temp.get(i).getAccount_id());
	    			bal = formatter.format(temp.get(i).getBalance());
	    			System.out.println("Balance: " + bal + "\n");
    			}
    		}
    		if (eligible.size() == 0) {
    			System.out.println("You do not have any accounts eligible for deletion.  Returning to 'YOUR BANKING PAGE'.");
    			return 0;
    		}
    	}
    	
    	
    	Integer select = 0;
        boolean verifiedInput=false;
        // retrieving / validating input
        do {
        	System.out.println("Which account would you like to delete?  Enter the account number.");
        	
        	if (App.sc.hasNext("b"))
        		return 0;
        	// it must be an int
	        while (!App.sc.hasNextInt()) {
	        	System.out.println("Invalid input.  Enter a valid account number.");
	        	App.sc.next();
	        }
	        // it must be one of the given options
	        select = App.sc.nextInt();
	        if (!eligible.contains(select.intValue())) {
	        	System.out.println("Invalid input.  Enter a valid account number.");
	        }
	        else {
	        	verifiedInput = true;
	        }
        }while(!verifiedInput);
    	
        try {
			if (AccountService.getService().delete(select).get()== true)
				System.out.println("Deletion successful.  Returning to YOUR BANKING PAGE.");
			else {
				System.out.println("Deletion unsuccessful.  Returning to YOUR BANKING PAGE.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	return 0;
    }

    // returns "0" if they say 'b'
    protected static String grabValidString (int reqCharCount, String dataTitle) {
    	System.out.println("Enter " + dataTitle.toUpperCase() + ":");
    	
    	String userInput;
    	// grab username
        boolean verifiedInput = false;
        do {
        	userInput = App.sc.nextLine();
        	userInput = userInput.toLowerCase();
	        if (userInput.length() < reqCharCount) {
	        	if (userInput.length() == 1 && userInput.charAt(0) == 'b') {
	        		return "0";
	        	}

	        	System.out.println(dataTitle + " must be at least " + reqCharCount + " characters long.");
	        }
	        else {
	        	verifiedInput = true;
	        }
        }while(!verifiedInput);
        
        return userInput;
    }
}
