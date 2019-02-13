package com.revature.project0.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project0.model.BankAccount;
import com.revature.project0.util.ConnectionUtil;

public class AccountActionsOracle implements AccountActionsDao{
	private static AccountActionsOracle instance;
	private static final Logger log = LogManager.getLogger(AccountActionsOracle.class);

	private AccountActionsOracle() {
	}

	public static AccountActionsOracle getDao() {
		if (instance == null) {
			instance = new AccountActionsOracle();
		}
		return instance;
	}
	
	public Optional<Double> withdraw(int accID, double amount) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "call withdraw(?,?,?)";
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, accID);
			cs.setDouble(2, amount);
			cs.registerOutParameter(3, Types.DOUBLE);
			cs.execute();
			
			Double newBal = cs.getDouble(3);
			
			return Optional.of(newBal);
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	//account_id_in number, dep_amount_in IN number, new_bal_out OUT number
	public Optional<Double> deposit(int accID, double amount) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "call deposit(?,?,?)";
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, accID);
			cs.setDouble(2, amount);
			cs.registerOutParameter(3, Types.DOUBLE);
			cs.execute();
			
			Double newBal = cs.getDouble(3);
			
			return Optional.of(newBal);
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	public Optional<Boolean> delete(int acc_id) {
		log.traceEntry();
		
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "DELETE FROM open_accounts WHERE account_id=" + acc_id;
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeQuery();
			
			return Optional.of(true);
		} catch (SQLException e) {
			log.catching(e);
			log.error("Database error.", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}

	@Override
	public Optional<Boolean> create(int ownerID, String accountName) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "call createAccount(?,?)";
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, ownerID);
			cs.setString(2, accountName);
			cs.execute();

			return Optional.of(true);
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<List<BankAccount>> viewAccounts(int ownerID) {
		log.traceEntry();
		
		Connection con = ConnectionUtil.getConnection();
		
		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			String sql = "SELECT * FROM open_accounts WHERE user_id = " + ownerID;
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			List<BankAccount> listOfAccounts = new ArrayList<BankAccount>();
			while (rs.next()) {
				listOfAccounts.add(new BankAccount(rs.getInt("account_id"), rs.getInt("balance"),
						rs.getString("account_name"),rs.getInt("user_id")));
			}
			
			return Optional.of(listOfAccounts);
		} catch (SQLException e) {
			log.catching(e);
			log.error("Database error.", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}
}
