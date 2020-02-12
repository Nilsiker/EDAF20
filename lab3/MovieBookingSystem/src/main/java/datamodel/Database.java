package datamodel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Database is a class that specifies the interface to the movie database. Uses
 * JDBC and the MySQL Connector/J driver.
 */
public class Database {
	/**
	 * The database connection.
	 */
	private Connection conn;

	/**
	 * Create the database interface object. Connection to the database is performed
	 * later.
	 */
	public Database() {
		conn = null;
	}

	/**
	 * Open a connection to the database, using the specified user name and
	 * password.
	 *
	 * @param userName The user name.
	 * @param password The user's password.
	 * @return true if the connection succeeded, false if the supplied user name and
	 *         password were not recognized. Returns false also if the JDBC driver
	 *         isn't found.
	 */
	public boolean openConnection(String userName, String password) {
		try {
			// Connection strings for included DBMS clients:
			// [MySQL] jdbc:mysql://[host]/[database]
			// [PostgreSQL] jdbc:postgresql://[host]/[database]
			// [SQLite] jdbc:sqlite://[filepath]

			// Use "jdbc:mysql://puccini.cs.lth.se/" + userName if you using our shared
			// server
			// If outside, this statement will hang until timeout.
			conn = DriverManager.getConnection("jdbc:mysql://puccini.cs.lth.se/" + userName, userName, password);
		} catch (SQLException e) {
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
		} catch (SQLException e) {
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
		PreparedStatement query;
		ResultSet result;

		try {
			System.out.println(mTitle + "   " + mDate);
			query = conn.prepareStatement("SELECT name, nbrOfSeats, count(name) FROM Shows "
					+ "INNER JOIN Theaters "
					+ "ON Theaters.name = Shows.theaterName "
					+ "INNER JOIN Reservations"
					+ "ON Theaters."	// TODO fix
					+ "WHERE movieName= ? and showDate= ?");
			query.setString(1, mTitle);
			query.setString(2, mDate);
			result = query.executeQuery();
			result.next();
			mVenue = result.getString("name");	
			mFreeSeats = result.getInt("nbrOfSeats");	// TODO make it subtract already booked count!
		} catch (SQLException e) {
			System.out.println("The getShowData query didn't work out as intended...");
			e.printStackTrace();
			return null;
		}
		
		return new Show(mTitle, mDate, mVenue, mFreeSeats);
	}

	public boolean login(String uname) {
		PreparedStatement query = null;
		ResultSet users = null;
		try {
			query = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
			query.setString(1, uname);
			users = query.executeQuery();
			return users.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<String> getAllMovies() {
		List<String> list = new ArrayList<>();
		try {
			Statement query = conn.createStatement();
			ResultSet movies = query.executeQuery("SELECT name FROM Movies");
			;
			while (movies.next()) {
				list.add(movies.getString(1));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getDates(String m) {
		List<String> list = new ArrayList<String>();

		try {
			PreparedStatement query = conn.prepareStatement("SELECT showDate FROM Shows WHERE movieName=?");
			;
			query.setString(1, m);
			ResultSet dates = query.executeQuery();
			while (dates.next()) {
				list.add(dates.getDate(1).toString());
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Reservation> getReservations(String uname) {
		List<Reservation> list = new ArrayList<Reservation>();
		try {
			PreparedStatement query = conn.prepareStatement("SELECT * FROM Reservations NATURAL JOIN Shows WHERE username=?");
			query.setString(1, uname);
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				list.add(new Reservation(rs.getInt(3), rs.getString(1), rs.getDate(2).toString(), rs.getString(5)));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
