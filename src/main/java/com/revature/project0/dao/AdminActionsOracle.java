package com.revature.project0.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project0.model.BankAccount;
import com.revature.project0.model.BankMember;
import com.revature.project0.service.UserService;
import com.revature.project0.util.ConnectionUtil;

public class AdminActionsOracle implements AdminActionsDao {
	private static AdminActionsOracle instance;
	private static final Logger log = LogManager.getLogger(AdminActionsOracle.class);

	private AdminActionsOracle() {
		
	}

	public static AdminActionsOracle getDao() {
		if (instance == null) {
			instance = new AdminActionsOracle();
		}
		return instance;
	}
	
	public Optional<List<BankMember>> getAllUsers() {
		log.traceEntry();
		
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "SELECT * FROM registered_user";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			List<BankMember> listOfUsers = new ArrayList<BankMember>();

			while (rs.next()) {
				listOfUsers.add(new BankMember(rs.getInt("user_id"), rs.getString("username"), "", ((rs.getInt("is_admin") > 0) ? true : false),
						rs.getString("last_name"), rs.getString("first_name"), rs.getString("email_address"), rs.getString("physical_address"),
						rs.getString("usa_state"), rs.getString("city")));

			}
			
			return Optional.of(listOfUsers);
		} catch (SQLException e) {
			log.catching(e);
			log.error("Database error.", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}

	@Override
	public Optional<Boolean> register(String user, String pass, Boolean isAdmin, String lastName, String firstName,
			String email, String address, String state, String city) {
		try {
			return UserService.getService().register(user, pass, isAdmin, lastName, firstName, email, address, state, city);
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public Optional<Boolean> updateBankMember(int memberID, String column, String value) {
		log.traceEntry();
		
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "UPDATE registered_user SET " + column + " = '" + value + "' WHERE user_id = " + memberID;
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
	public Optional<Boolean> deleteBankMember(int memberID) {
		log.traceEntry();
		
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "DELETE FROM registered_user WHERE user_id = " + memberID;
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
}
