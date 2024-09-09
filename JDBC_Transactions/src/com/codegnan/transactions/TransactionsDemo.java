package com.codegnan.transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TransactionsDemo {
	static final String JDBC_URL ="jdbc:mysql://localhost:3306/adjava";
	static final String USERNAME = "root";
	static final String PASSWORD= "root";
	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			// establish connection
			connection = DriverManager.getConnection(JDBC_URL,USERNAME,PASSWORD);
			
			// create the statement object
			statement = connection.createStatement();
			
			// display data before transaction.
			System.out.println("Data before Transactions : ");
			ResultSet resBefore=statement.executeQuery("select *from accounts");
			while(resBefore.next()) {
				System.out.println(resBefore.getString(1)+"\t"+resBefore.getDouble(2));
			}
			
			// start transaction
			System.out.println("\n Transaction Begin ");
			
			// disable autocommit mode
			connection.setAutoCommit(false);
			statement.executeUpdate("update accounts set balance = balance-10000 where name = 'Ganesh'");
			statement.executeUpdate("Update accounts set balance = balance+10000 Where name = 'Ram'");
			System.out.println("Can You Please Confirm This Transaction of 10000 Rupees(Yes/No) :");
			
			// confirm the Transaction
			Scanner scanner = new Scanner(System.in);
			String option=scanner.next();
			
			if(option.equalsIgnoreCase("yes")) {
				connection.commit();
				System.out.println("Transaction Committed ");
			}else {
				connection.rollback();
				System.out.println("Transaction RollBacked");
			}
			System.out.println("Data After Transaction :");
			ResultSet resAfter = statement.executeQuery("select *from accounts");
			while(resAfter.next()) {
				System.out.println(resAfter.getString(1)+"\t"+resAfter.getDouble(2));
			}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			finally {
				if(statement!=null) {
					try {
						statement.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
				if(resultSet!=null) {
					try {
						resultSet.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
				if(connection!=null) {
					try {
						connection.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
				//scanner.close();
			}
	}
}
