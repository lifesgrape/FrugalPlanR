package backend;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import container.Account;
import container.Transaction;
import exception.InvalidAccountException;
import intermediate.TransactionAccessor;

public class TransactionAccessorImpl implements TransactionAccessor {
	private static final int MAX_STRING = 45;

	Connection dbConnection;

	public TransactionAccessorImpl() {
		dbConnection = FrugalDBConnection.getConnection();
	}

	@Override
	public void create(Account a, double value, String name, Date date) throws SQLException, InvalidAccountException {
		// Check that account is valid
		AccountAccessorImpl accountImpl = new AccountAccessorImpl();
		if (!accountImpl.isValidAccount(a)) {
			throw new InvalidAccountException("Account is invalid");
		}
		accountImpl.close();

		// Make sure item is less than 45 characters
		if (!isValidLength(name))
			throw new IllegalArgumentException(name + " length is too long (" + name.length() + "), should be less than " + MAX_STRING);

		// Create SQL query
		String sqlQuery = "INSERT INTO transaction (value, name, date, account_id) "
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setDouble(1, value);
		statement.setString(2, name);
		statement.setDate(3, date);
		statement.setInt(4, a.getID());

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void delete(Transaction t) throws SQLException {
		// Do nothing if transaction does not exists.
		if (!isValidTransaction(t))
			return;

		// Prepare SQL
		String sqlQuery = "DELETE FROM transaction "
				+ "WHERE id like ? AND value like ? "
				+ "AND name like ? AND date like ? "
				+ "AND account_id like ?";

		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, t.getId());
		statement.setDouble(2, t.getValue());
		statement.setString(3, t.getName());
		statement.setDate(4, t.getDate());
		statement.setInt(5, t.getAccountID());

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public List<Transaction> getTransaction(Account a) throws SQLException {
		// List to return
		List<Transaction> transactionList = new ArrayList<Transaction>();

		// Prepare SQL
		String sqlQuery = "SELECT * "
				+ "FROM budget "
				+ "WHERE account_id like ?";
		
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, a.getID());
		
		// Execute SQL and retrieve result
		ResultSet result = statement.executeQuery();
		statement.close();

		// Place result into list
		while (result.next()) {
			// Retrieve results
			int id = result.getInt("id");
			double value = result.getDouble("value");
			String name= result.getString("startname");
			Date date = result.getDate("date");
			
			// Create transaction & add to the list
			Transaction newT = new Transaction(id, value, name, date, a.getID());
			transactionList.add(newT);
		}
		result.close();

		// If list is empty, return null
		// Otherwise return the list
		if (transactionList.size() == 0)
			return null;
		return transactionList;
	}

	/**
	 * Return true if s is smaller than MAX_STRING
	 * @param s String to check
	 * @return true if s is smaller than MAX_STRING
	 */
	private boolean isValidLength(String s) {
		if (s.length() > MAX_STRING)
			return false;
		return true;
	}

	/**
	 * Checks if the transaction exists in the DB
	 * @param t Transaction to check
	 * @return True if transaction found, false otherwise
	 * @throws SQLException If a database error occurs
	 */
	private boolean isValidTransaction(Transaction t) throws SQLException {
		// Prepare sql query
		String sqlQuery = "SELECT * "
				+ "FROM transaction "
				+ "WHERE id like ? AND value like ? "
				+ "AND name like ? AND date like ? "
				+ "AND account_id like ?";

		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, t.getId());
		statement.setDouble(2, t.getValue());
		statement.setString(3, t.getName());
		statement.setDate(4, t.getDate());
		statement.setInt(5, t.getAccountID());

		// Execute and retrieve result
		ResultSet result = statement.executeQuery();
		statement.close();

		// If results found, return true
		// Otherwise return false;
		if (result.next()) {
			result.close();
			return true;
		}		
		result.close();
		return false;
	}

}
