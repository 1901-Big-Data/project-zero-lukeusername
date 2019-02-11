package com.revature.project0.service;

import java.util.Optional;
import com.revature.project0.dao.*;
import com.revature.project0.model.*;

public class UserService {
	private static UserService service;
	private static UserActionsDao dao = UserActionsOracle.getDao();
	
	private UserService() {
		
	}
	
	public static UserService getService() {
		if (service == null) {
			service = new UserService();
		}
		return service;
	}
	
	public Optional<BankMember> login(String user, String pass) throws Exception {
		return dao.login(user, pass);
	}
	
	/**
	 * if
	 * 1. if boolean true: success
	 * 2. if boolean false: database success but username taken
	 * 3. empty optional: database failure
	 */
	public Optional<Boolean> register(String user, String pass, Boolean isAdmin, String lastName, 
			String firstName, String email, String address, String state, String city) throws Exception {
		return dao.register(user, pass, isAdmin, lastName, firstName, email, address, state, city);
	}
	
	public Optional<Boolean> checkUsername(String username){
		return dao.usernameAvailability(username);
	}
}
