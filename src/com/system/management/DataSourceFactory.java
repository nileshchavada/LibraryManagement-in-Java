package com.system.management;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import com.mysql.cj.jdbc.MysqlDataSource;

public class DataSourceFactory {
	private final DataSource dsdao;
	private static final Logger logger = Logger.getLogger(DataSourceFactory.class.getName());

	DataSourceFactory() {

		MysqlDataSource myDs = new MysqlDataSource();
		String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("database.properties")).getPath();
		InputStream input = null;

		try {
			input = new FileInputStream(rootPath);
			Properties prop = new Properties();
			prop.load(input);
			myDs.setDatabaseName(prop.getProperty("database"));
			myDs.setServerName(prop.getProperty("serverName"));
			myDs.setUser(prop.getProperty("user"));
			myDs.setPort(Integer.parseInt(prop.getProperty("port")));
			myDs.setPassword(prop.getProperty("password"));
		} catch (FileNotFoundException e) {
			logger.error("Exception occured in method DataSourceFactory " + e.getStackTrace());
		} catch (IOException e) {
			logger.error("Exception occured in method DataSourceFactory " + e.getStackTrace());
		}catch (Exception e) {
			logger.error("Exception occured in method DataSourceFactory " + e.getStackTrace());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Exception occured in method DataSourceFactory " + e.getStackTrace());
				}
			}

		}
		this.dsdao = myDs;
	}

	public static Connection getConnection() throws SQLException {
		return SingletonHelper.INSTANCE.dsdao.getConnection();
	}

	public static class SingletonHelper {
		private static final DataSourceFactory INSTANCE = new DataSourceFactory();
	}
}
