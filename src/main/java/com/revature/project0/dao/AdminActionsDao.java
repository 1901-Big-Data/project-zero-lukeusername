package com.revature.project0.dao;

import java.util.List;
import java.util.Optional;

import com.revature.project0.model.BankAccount;
import com.revature.project0.model.BankMember;

public interface AdminActionsDao {
	Optional<List<BankMember>> getAllUsers() throws Exception;
	
	Optional<Boolean> register(String user, String pass, Boolean isAdmin, String lastName, 
			String firstName, String email, String address, String state, String city);
	
	Optional<Boolean> updateBankMember(int memberID, String column, String value);
	
	Optional<Boolean> deleteBankMember(int memberID);
}