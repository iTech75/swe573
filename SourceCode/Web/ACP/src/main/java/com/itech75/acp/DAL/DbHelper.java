package com.itech75.acp.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DbHelper {
	@Value("${db.driver}")
	private String DB_DRIVER;
	
	@Value("${db.url}")
	private String URL;
	
	@Value("${db.username}")
	private String USERNAME;
	
	@Value("${db.password}")
	private String PASSWORD;
	
//	public DbHelper()
//	{
//		try {
//			Class.forName(DB_DRIVER);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public Connection getConnection(){
		try {
			//return DriverManager.getConnection("jdbc:mysql://localhost:3306/acp?user=root&password=45733321");
			return datasource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Statement getStatement(Connection connection){
		try {
			Statement statement = connection.createStatement();
			return statement;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public PreparedStatement prepareStatement(Connection connection, String sql){
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			return statement;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static DriverManagerDataSource datasource;
	
	@Bean
	public DataSource setDataSource() {
		datasource = new DriverManagerDataSource();
		datasource.setDriverClassName(DB_DRIVER);
		datasource.setUrl(URL);
		datasource.setUsername(USERNAME);
		datasource.setPassword(PASSWORD);
		return datasource;
	}
}