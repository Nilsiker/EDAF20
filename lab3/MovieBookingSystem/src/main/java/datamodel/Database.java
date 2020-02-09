package datamodel;

import java.sql.*;
/**
 * Database is a class that specifies the interface to the 
 * movie database. Uses JDBC and the MySQL Connector/J driver.
 */
public class Database {
    /** 
     * The database connection.
     */
    private Connection conn;
        
    /**
     * Create the database interface object. Connection to the database
     * is performed later.
     */
    public Database() {
        conn = null;
    }
       
    /* --- TODO: Change this method to fit your choice of DBMS --- */
    /** 
     * Open a connection to the database, using the specified user name
     * and password.
     *
     * @param userName The user name.
     * @param password The user's password.
     * @return true if the connection succeeded, false if the supplied
     * user name and password were not recognized. Returns false also
     * if the JDBC driver isn't found.
     */
    public boolean openConnection(String userName, String password) {
        try {
        	// Connection strings for included DBMS clients:
        	// [MySQL]       jdbc:mysql://[host]/[database]
        	// [PostgreSQL]  jdbc:postgresql://[host]/[database]
        	// [SQLite]      jdbc:sqlite://[filepath]
        	
        	// Use "jdbc:mysql://puccini.cs.lth.se/" + userName if you using our shared server
        	// If outside, this statement will hang until timeout.
            conn = DriverManager.getConnection 
                ("jdbc:sqlite:movieBookings.db3", userName, password);
        }
        catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
        return true;
    }
        
    /**
     * Close the connection to the database.
     */
    public void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        }
        catch (SQLException e) {
        	e.printStackTrace();
        }
        conn = null;
        
        System.err.println("Database connection closed.");
    }
        
    /**
     * Check if the connection to the database has been established
     *
     * @return true if the connection has been established
     */
    public boolean isConnected() {
        return conn != null;
    }
	
  	public Show getShowData(String mTitle, String mDate) {
		Integer mFreeSeats = 42;
		String mVenue = "Kino 2";
		
		/* --- TODO: add code for database query --- */
		
		return new Show(mTitle, mDate, mVenue, mFreeSeats);
	}

    /* --- TODO: insert more own code here --- */
}
