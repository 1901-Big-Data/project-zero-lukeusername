package com.revature.project0.BankAssignment;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotEquals;

import com.revature.project0.model.BankAccount;
import com.revature.project0.model.BankMember;
import com.revature.project0.service.AccountService;
import com.revature.project0.service.AdminService;
import com.revature.project0.service.UserService;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BankTest {
	
    @Test
    public void testing123() throws Exception
    {
     	assertEquals(1, -1 + 2);
    }
	
    /*
     	-UserService methods-
     		----------
     	1 negative test per pethod
     	1 positive test per method
      
      	Optional<BankMember> login(String username, String password) throws Exception;
		Optional<Boolean> register(String user, String pass, Boolean isAdmin, String lastName, 
			String firstName, String email, String address, String state, String city);
		Optional<Boolean> usernameAvailability(String username);
     */
    
// 	Optional<BankMember> login(String username, String password) throws Exception;
    @Test
    public void positiveLogin() throws Exception
    {
        BankMember loginTest = new BankMember(42,"lukeuser","", false, "davis", "luke", "lukedavis3@gmail.com", "2004 stanchion st", "nc", "haw river");
    	assertEquals(loginTest.getMemberId(), (UserService.getService().login("lukeuser", "safepass").get().getMemberId()));
    }
    @Test(expected = Exception.class)
    public void negativeLogin() throws Exception
    {
        BankMember loginTest = new BankMember(42,"lukeuser","", false, "davis", "luke", "lukedavis3@gmail.com", "2004 stanchion st", "nc", "haw river");
    	assertNotEquals(loginTest.getMemberId(), (UserService.getService().login("lukeuser", "wrongpass").get().getMemberId()));
    }
    
//	Optional<Boolean> register(String user, String pass, Boolean isAdmin, String lastName, 
//			String firstName, String email, String address, String state, String city);
    @Test
    public void positiveRegister() throws Exception
    {
    	assertEquals(true, (UserService.getService().register("testuser", "testpass", false, "lastnam", "firstnam", "a@aa.com", "101 computer", "CA", "san fran").get()));
    }
    @Test(expected = NoSuchElementException.class)
    public void negativeRegister() throws Exception
    {
    	assertEquals(true, (UserService.getService().register("lukeuser", "testpass", false, "lastnam", "firstnam", "a@aa.com", "101 computer", "CA", "san fran").get()));
    }
    
//  Optional<Boolean> usernameAvailability(String username);
    @Test
    public void positiveUsernameCheck() throws Exception
    {
    	assertEquals(false, (UserService.getService().checkUsername("lukeuser").get()));
    }
    @Test
    public void negativeUsernameCheck() throws Exception
    {
    	assertEquals(true, (UserService.getService().checkUsername("uniqueusername").get()));
    }
    
    /*
	 	-AccountService methods-
	 		----------
	 	1 negative test
	 	1 positive test
	  
		Optional<List<BankAccount>> viewAccounts(int ownerID);
		Optional<Boolean> create(int ownerID, String accountName);
		Optional<Double> withdraw(int accountID, double amount);
		Optional<Double> deposit(int accountID, double amount);
		Optional<Boolean> delete(int acc_id);
     */
    
//	Optional<List<BankAccount>> viewAccounts(int ownerID);
    @Test
    public void positiveViewAccounts() throws Exception
    {
    	assertEquals(42, (AccountService.getService().viewAccounts(42).get().get(0).getMember_id()));
    }
    @Test
    public void negativeViewAccounts() throws Exception
    {
    	assertNotEquals(43, (AccountService.getService().viewAccounts(42).get().get(0).getMember_id()));
    }
    
//	Optional<Boolean> create(int ownerID, String accountName);
    @Test
    public void posCreateAccount() throws Exception
    {
    	boolean success = false;
    	AccountService.getService().createAccount(42, "testaccount");
    	List<BankAccount> test = AccountService.getService().viewAccounts(42).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccountName().equals("testaccount")){
    			success = true;
    			break;
    		}
    	}
    	assertEquals(true, success);
    }
    @Test
    public void negCreateAccount() throws Exception
    {
    	AccountService.getService().createAccount(10001, "testaccount");
    	boolean success = false;

    	List<BankAccount> test = AccountService.getService().viewAccounts(10001).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccountName().equals("testaccount")){
    			success = true;
    			break;
    		}
    	}
    	assertEquals(false, success);
    }
    
//  Optional<Double> withdraw(int accountID, double amount);
    @Test
    public void posWithdraw() throws Exception
    {
    	int before = 0, after = 0;
    	List<BankAccount> test = AccountService.getService().viewAccounts(42).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccountName().equals("bigmoney")){
    			before = (int)test.get(i).getBalance();
    			break;
    		}
    	}
    	
    	AccountService.getService().withdraw(83, 20);
    	
    	test = AccountService.getService().viewAccounts(42).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccountName().equals("bigmoney")){
    			after = (int)test.get(i).getBalance();
    			break;
    		}
    	}
    	assertTrue(before != after);
    }
    @Test
    public void negWithdraw() throws Exception
    {
    	int before = 0, after = 0;
    	List<BankAccount> test = AccountService.getService().viewAccounts(100000).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccountName().equals("bigmoney")){
    			before = (int)test.get(i).getBalance();
    			break;
    		}
    	}
    	
    	AccountService.getService().withdraw(100000, 10);
    	
    	test = AccountService.getService().viewAccounts(100000).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccountName().equals("bigmoney")){
    			after = (int)test.get(i).getBalance();
    			break;
    		}
    	}
    	assertFalse(before != after);
    }
    
//  Optional<Double> deposit(int accountID, double amount);
    @Test
    public void posDeposit() throws Exception
    {
    	int before = 0, after = 0;
    	List<BankAccount> test = AccountService.getService().viewAccounts(42).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccountName().equals("bigmoney")){
    			before = (int)test.get(i).getBalance();
    			break;
    		}
    	}
    	
    	AccountService.getService().deposit(83, 20);
    	
    	test = AccountService.getService().viewAccounts(42).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccountName().equals("bigmoney")){
    			after = (int)test.get(i).getBalance();
    			break;
    		}
    	}
    	assertTrue(before != after);
    }
    @Test
    public void negDeposit() throws Exception
    {
    	int before = 0, after = 0;
    	List<BankAccount> test = AccountService.getService().viewAccounts(100000).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccountName().equals("bigmoney")){
    			before = (int)test.get(i).getBalance();
    			break;
    		}
    	}
    	
    	AccountService.getService().deposit(100000, 20);
    	
    	test = AccountService.getService().viewAccounts(100000).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccountName().equals("bigmoney")){
    			after = (int)test.get(i).getBalance();
    			break;
    		}
    	}
    	assertFalse(before != after);
    }
    
//	Optional<Boolean> delete(int acc_id);
    @Test
    public void posDelete() throws Exception
    {
    	boolean answer;
    	boolean exists = false;
    	List<BankAccount> test = AccountService.getService().viewAccounts(42).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccount_id()==87){
    			exists = true;
    			break;
    		}
    	}
    	if (exists != true) {
    		fail("account doesn't exist");
    	}
    	assertTrue(AccountService.getService().delete(87).get());
    	
    	test = AccountService.getService().viewAccounts(42).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccount_id()==87){
    			exists = true;
    			break;
    		}
    		if (i == test.size()-1) {
    			exists = false;
    		}
    	}
    	assertFalse(exists);
    }
    @Test
    public void negDelete() throws Exception
    {
    	boolean answer;
    	boolean exists = false;
    	List<BankAccount> test = AccountService.getService().viewAccounts(42).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccount_id()==1000){
    			exists = true;
    			break;
    		}
    	}
    	if (exists != true) {
    		assertTrue(true);
    	}
    	assertTrue(AccountService.getService().delete(1000).get());
    	
    	test = AccountService.getService().viewAccounts(42).get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getAccount_id()==1000){
    			exists = true;
    			break;
    		}
    		if (i == test.size()-1) {
    			exists = false;
    		}
    	}
    	assertFalse(exists);
    }
    /*
	 	-AccountService methods-
	 		----------
	 	1 negative test
	 	1 positive test
	  
		Optional<List<BankMember>> getAllUsers() throws Exception;		
		Optional<Boolean> register(String user, String pass, Boolean isAdmin, String lastName, 
				String firstName, String email, String address, String state, String city);	
		Optional<Boolean> updateBankMember(int memberID, String column, String value);
		Optional<Boolean> deleteBankMember(int memberID);
     */
    
//	Optional<List<BankAccount>> viewAccounts(int ownerID);
    @Test
    public void posGetUsers() throws Exception
    {
    	boolean success = false;
    	List<BankMember> test = AdminService.getService().getAllUsers().get();
    	for (int i = 0; i < test.size(); i++) {
    		if (test.get(i).getMemberId() == 42) {
    			success = true;
    		}
    	}
    	
    	assertTrue(success);
    }
    
//	Optional<Boolean> register(String user, String pass, Boolean isAdmin, String lastName, 
//	String firstName, String email, String address, String state, String city);
	@Test
	public void posRegister() throws Exception
	{
		assertEquals(true, (AdminService.getService().register("testuser2", "testpass", false, "lastnam", "firstnam", "a@aa.com", "101 computer", "CA", "san fran").get()));
	}
	@Test(expected = NoSuchElementException.class)
	public void negRegister() throws Exception
	{
		assertEquals(true, (AdminService.getService().register("lukeuser", "testpass", false, "lastnam", "firstnam", "a@aa.com", "101 computer", "CA", "san fran").get()));
	}
}
