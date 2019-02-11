package com.revature.project0.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {

	private static Connection connectionInstance = null;
	private static final Logger log = LogManager.getLogger(ConnectionUtil.class);

	private ConnectionUtil() {

	}

	public static Connection getConnection() {
		if (connectionInstance != null) {
			return connectionInstance;
		}

		InputStream in = null;

		try {
			// load information from properties file
			Properties props = new Properties();
			in = new FileInputStream(
					"src/main/Resources/connection.properties");
			props.load(in);

			// get the connection object
			Class.forName("oracle.jdbc.OracleDriver");

			String endpoint = props.getProperty("jdbc.endpoint");
			String username = props.getProperty("jdbc.username");
			String password = props.getProperty("jdbc.password");

			connectionInstance = DriverManager.getConnection(endpoint, username, password);
			return connectionInstance;
		} catch (Exception e) {
			log.error("Unable to get connection to database");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				log.error("Could not close connection");
			}
		}
		return null;
	}
}
