package com.revature.project0.dao;

import java.util.List;
import java.util.Optional;
import com.revature.project0.model.*;

public interface AccountActionsDao {
	Optional<List<BankAccount>> viewAccounts(int ownerID);
	Optional<Boolean> create(int ownerID, String accountName);
	Optional<Double> withdraw(int accountID, double amount);
	Optional<Double> deposit(int accountID, double amount);
	Optional<Boolean> delete(int acc_id);
}