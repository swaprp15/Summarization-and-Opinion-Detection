package ac.in.iiith.siel.reviews.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {
	private static String connectionString = "jdbc:mysql://localhost:3306/ire";
	private static String userName = "ire";
	private static String password = "ire";

	private static Connection connection = null;

	public static Connection getConnection() {
		if (connection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(connectionString,
						userName, password);
				connection.setAutoCommit(false);
			} catch (final Exception e) {
				throw new RuntimeException(
						"exception while opening connection:", e);
			}

		}

		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (final Exception e) {
				throw new RuntimeException(
						"exception while closing connection:", e);
			}

		}
	}
}
