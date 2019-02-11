package com.revature.project0.dao;

import java.sql.SQLException;
import java.sql.Types;
import java.sql.CallableStatement;
import java.sql.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Optional;
import com.revature.project0.util.*;
import com.revature.project0.model.BankMember;

public class UserActionsOracle implements UserActionsDao {
	private static UserActionsOracle instance;
	private static final Logger log = LogManager.getLogger(UserActionsOracle.class);

	private UserActionsOracle() {
	}

	public static UserActionsOracle getDao() {
		if (instance == null) {
			instance = new UserActionsOracle();
		}
		return instance;
	}

	public Optional<BankMember> login(String username, String password) throws Exception {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "call login(?,?,?,?,?,?,?,?,?,?)";
			CallableStatement cs = con.prepareCall(sql);
			cs.setString(1, username);
			cs.setString(2, password);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.registerOutParameter(4, Types.INTEGER);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.registerOutParameter(6, Types.VARCHAR);
			cs.registerOutParameter(7, Types.VARCHAR);
			cs.registerOutParameter(8, Types.VARCHAR);
			cs.registerOutParameter(9, Types.VARCHAR);
			cs.registerOutParameter(10, Types.VARCHAR);
			cs.execute();

			Integer id = cs.getInt(3);
			Integer admin = cs.getInt(4);
			String last = cs.getString(5);
			String first = cs.getString(6);
			String email = cs.getString(7);
			String address = cs.getString(8);
			String state = cs.getString(9);
			String city = cs.getString(10);

			if (id == 0) {
				throw new Exception("Invalid username / password combination");
			}
			BankMember user = new BankMember(id, username, "", ((admin > 0) ? true : false), last, first, email,
					address, state, city);
			return Optional.of(user);

		} catch (SQLException e) {
			System.out.println("database problem");
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

	}

	public Optional<Boolean> register(String username, String password, Boolean isAdmin, String lastName,
			String firstName, String email, String address, String state, String city) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "call addUser(?,?,?,?,?,?,?,?,?,?)";
			CallableStatement cs = con.prepareCall(sql);
			cs.setString(1, username);
			cs.setString(2, password);
			cs.setInt(3, (isAdmin) ? 1 : 0);
			cs.setString(4, lastName);
			cs.setString(5, firstName);
			cs.setString(6, email);
			cs.setString(7, address);
			cs.setString(8, state);
			cs.setString(9, city);
			cs.registerOutParameter(10, Types.INTEGER);
			cs.execute();

			Integer success = cs.getInt(10);

			if (success == 1)
				return Optional.of(true);
			else
				return Optional.of(false);
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	public Optional<Boolean> logout() {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<Boolean> usernameAvailability(String username) {
		log.traceEntry();
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			String sql = "call getUsername(?,?)";
			CallableStatement cs = con.prepareCall(sql);
			cs.setString(1, username);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.execute();

			Integer avail = cs.getInt(2);

			if (avail == 0)
				return Optional.of(true);
			else
				return Optional.of(false);
		} catch (SQLException e) {
			return Optional.empty();
		}
	}
}
