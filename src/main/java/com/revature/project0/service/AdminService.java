package com.revature.project0.service;

import java.util.List;
import java.util.Optional;
import com.revature.project0.dao.*;
import com.revature.project0.model.*;

public class AdminService {
	private static AdminService service;
	private static AdminActionsDao dao = AdminActionsOracle.getDao();
	
	private AdminService() {
		
	}
	
	public static AdminService getService() {
		if (service == null) {
			service = new AdminService();
		}
		return service;
	}
	
	public Optional<Boolean> login(String user, String pass) throws Exception {
		return Optional.empty();
	}
	
	/**
	 * if
	 * 1. if boolean true: success
	 * 2. if boolean false: database success but username taken
	 * 3. empty optional: database failure
	 */
	public Optional<Boolean> register(String user, String pass, Boolean isAdmin, String lastName, String firstName,
			String email, String address, String state, String city) throws Exception {
		return dao.register( user,  pass,  isAdmin,  lastName,  firstName,
				email, address, state, city);
	}
	
	public Optional<List<BankMember>> getAllUsers() throws Exception{
		return dao.getAllUsers();
	}
	
	public Optional<Boolean> deleteBankMember(int accID) throws Exception{
		return dao.deleteBankMember(accID);
	}
	
	public Optional<Boolean> updateBankMember(int memberID, String column, String value) {
		return dao.updateBankMember(memberID, column, value);
	}
}