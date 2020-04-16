package gr.hua.dit.oopii.lec6.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.sql.ResultSet;


public class DBServiceCRUD {
	static Connection db_con_obj = null; //A connection (session) with a specific database. SQL statements are executed and results are returned within the context 
	//of a connection. A Connection object's database is able to provide information describing its tables, its supported SQL grammar, its stored procedures, 
	//the capabilities of this connection, and so on. This information is obtained with the getMetaData method.
	static PreparedStatement db_prep_obj = null;//An object that represents a precompiled SQL statement.
	//A SQL statement is precompiled and stored in a PreparedStatement object. This object can then be used to efficiently execute this statement multiple times.
	
	private static void makeJDBCConnection() {
		 
		try {//We check that the DB Driver is available in our project.		
			Class.forName("com.mysql.jdbc.Driver"); //This code line is to check that JDBC driver is available. Or else it will throw an exception. Check it with 2. 
			System.out.println("Congrats - Seems your MySQL JDBC Driver Registered!"); 
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			e.printStackTrace();
			return;
		}
 
		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.	 //We connect to a DBMS.
			db_con_obj = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBName","username", "password");// Returns a connection to the URL.
			//Attempts to establish a connection to the given database URL. The DriverManager attempts to select an appropriate driver from the set of registered JDBC drivers.
			if (db_con_obj != null) { 
				System.out.println("Connection Successful! Enjoy. Now it's time to CRUD data. ");
				
			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (SQLException e) {
			System.out.println("MySQL Connection Failed!");
			e.printStackTrace();
			return;
		}
 
	}
	private static void getDataFromDBwith2Queries() {
		 
		try {
			// A simple MySQL Select Query. The SQL query is just a String. 
			String getQueryStatement = "SELECT * FROM patient;";
			
			// We make a statement to the connected DBMS. We pass to the statement a query.
			db_prep_obj = db_con_obj.prepareStatement(getQueryStatement); //Creates a PreparedStatement object for sending parameterized SQL statements to the database.
			//A SQL statement with or without IN parameters can be pre-compiled and stored in a PreparedStatement object. This object can then be used to 
			//efficiently execute this statement multiple times.		
			
			// Execute the Query, and get a java ResultSet
			ResultSet rs = db_prep_obj.executeQuery(); //Executes the SQL query in this PreparedStatement object and returns the ResultSet object generated by the query.
			//Returns:	a ResultSet object that contains the data produced by the query; 
			
			// Let's iterate through the java ResultSet
			while (rs.next()) { //Moves the cursor forward one row from its current position. A ResultSet cursor is initially positioned before the first row; 
				//the first call to the method next makes the first row the current row; the second call makes the second row the current row, and so on.
				//Integer id = rs.getInt("ID");
				String uid = rs.getString("UID");//Retrieves the value of the designated column in the current row of this ResultSet object as a String in the Java programming language.
				Integer dateKey = rs.getInt("DATE_OF_BIRTH_ID"); //We took the key based on which we will make the 2nd query.
				String getQueryDate = "SELECT * FROM dt_date WHERE id="+dateKey+";";	//We make the 2nd query
				db_prep_obj = db_con_obj.prepareStatement(getQueryDate); 
				ResultSet rs2 = db_prep_obj.executeQuery();
				while (rs2.next()) {
					Integer year = rs2.getInt("YEAR");
					Integer month = rs2.getInt("MONTH");
					Integer day = rs2.getInt("DAY");
					
					System.out.println("Patient: "+uid+" Date of birth Year: "+year+ " Month: "+month+ " Day: "+day);
				}
			}
		} catch (
		SQLException e) {
			e.printStackTrace();
		}
 
	}
	
	private static void getDataFromDB() {
		 
		try {
			// A simple MySQL Select Query 
			String getQueryStatement = "SELECT * FROM dt_amount;";		
			// We make a statement to the connected DBMS. We pass to the statement a query.
			db_prep_obj = db_con_obj.prepareStatement(getQueryStatement); 
 
			// Execute the Query, and get a java ResultSet
			ResultSet rs = db_prep_obj.executeQuery(); 
			
			// Let's iterate through the java ResultSet
			while (rs.next()) {
				Integer id = rs.getInt("ID");
				Float value = rs.getFloat("VALUE");
				System.out.println("id: "+id+" value: "+value); // Simply Print the results				
			}
 
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
 
	}
	
	private static void getDataFromDBwithJoin() {
		 
		try {
			// A simple MySQL Select Query 
			String getQueryStatement = "SELECT patient.UID, lifestyle_smoking.STATUS_ID,  lifestyle_smoking.AMOUNT_ID\n" + 
					"FROM patient\n" + 
					"INNER JOIN lifestyle_smoking ON patient.ID=lifestyle_smoking.ID;";		
			// We make a statement to the connected DBMS. We pass to the statement a query.
			db_prep_obj = db_con_obj.prepareStatement(getQueryStatement); 
 
			// Execute the Query, and get a java ResultSet
			ResultSet rs = db_prep_obj.executeQuery(); 
			
			// Let's iterate through the java ResultSet
			while (rs.next()) {
				String uid = rs.getString("UID");
				Float status = rs.getFloat("STATUS_ID");
				Float amount = rs.getFloat("AMOUNT_ID");
				System.out.println("Patient: "+uid+" Status: "+status+" Amount:"+amount); // Simply Print the results				
			}
 
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
 
	}
	private static void addDataToDB(int newKey, int year, int month, int day) {
		 
		try {
			String insertQueryStatement = "INSERT  INTO  dt_date  VALUES  (?,?,?,?,?,?)";
			
			//static Connection db_con_obj = null;
			//static PreparedStatement db_prep_obj = null;
			
			db_prep_obj = db_con_obj.prepareStatement(insertQueryStatement);
			db_prep_obj.setInt(1, newKey);//.setString
			db_prep_obj.setInt(2, year);
			db_prep_obj.setInt(3, month);
			db_prep_obj.setInt(4, day);
			db_prep_obj.setInt(5, 1);//.setString
			db_prep_obj.setInt(6, 15);
			//db_prep_obj.setInt(7, 1);
			//db_prep_obj.setInt(8, 1);
			//db_prep_obj.setObject(9, new Date());
			
			// execute insert SQL statement Executes the SQL statement in this PreparedStatement object, which must be an SQL Data Manipulation Language (DML) statement
			int numRowChanged = db_prep_obj.executeUpdate(); //either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
			System.out.println("Rows "+numRowChanged+" changed.");
			
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void deleteDataFromDB(int year) {
		 
		try {
			String deleteQueryStatement = "DELETE FROM dt_date WHERE year = "+year+";";
			db_prep_obj = db_con_obj.prepareStatement(deleteQueryStatement);
			
			// execute insert SQL statement
			int numRowChanged = db_prep_obj.executeUpdate();
			System.out.println("Rows "+numRowChanged+" changed.");
			
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String [] args) {
		makeJDBCConnection();
		//getDataFromDB();
		//getDataFromDBwith2Queries();
		//getDataFromDBwithJoin();
		//addDataToDB(76,2022,4,4);
		deleteDataFromDB(2021);
	}
}
