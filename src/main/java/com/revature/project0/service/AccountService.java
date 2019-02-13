package com.revature.project0.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import com.revature.project0.dao.*;
import com.revature.project0.model.*;

public class AccountService {
	private static AccountService service;
	private static AccountActionsDao dao = AccountActionsOracle.getDao();
	
	private AccountService() {
		
	}
	
	public static AccountService getService() {
		if (service == null) {
			service = new AccountService();
		}
		return service;
	}
	
	/**
	 * if
	 * 1. if boolean true: success
	 * 2. if boolean false: database success but username taken
	 * 3. empty optional: database failure
	 */
	public Optional<Boolean> createAccount(int ownerID, String accountName) throws SQLException {
		return dao.create(ownerID, accountName);
	}
	
	public Optional<List<BankAccount>> viewAccounts(int ownerID) throws Exception{
		return dao.viewAccounts(ownerID);
	}
	
	public Optional<Double> deposit(int accID, double amt) throws Exception{
		return dao.deposit(accID, amt);
	}
	
	public Optional<Double> withdraw(int accID, double amt) throws Exception{
		return dao.withdraw(accID, amt);
	}
	
	public Optional<Boolean> delete(int accID) throws Exception{
		return dao.delete(accID);
	}
}