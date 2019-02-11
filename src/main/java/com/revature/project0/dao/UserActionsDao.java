package com.revature.project0.dao;

import java.util.Optional;
import com.revature.project0.model.BankMember;

public interface UserActionsDao {
	Optional<BankMember> login(String username, String password) throws Exception;
	
	Optional<Boolean> register(String user, String pass, Boolean isAdmin, String lastName, 
			String firstName, String email, String address, String state, String city);
	
	Optional<Boolean> usernameAvailability(String username);
	
	Optional<Boolean> logout();
}