package ac.in.iiith.siel.reviews.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseRepository {

	protected void closeConnection(final Connection conn) {
		if(conn!=null) {
			try {
			conn.close();
			} catch(final SQLException e) {
				throw new RuntimeException("exception while closing connection", e);
			}
			
		}
	}
	
	protected void closeStatment(final Statement stmt) {
		if(stmt!=null) {
			try {
				stmt.close();
			} catch(final SQLException e) {
				throw new RuntimeException("exception while closing statement", e);
			}
			
		}
	}
	
	protected void closeResultset(final ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch(final SQLException e) {
				throw new RuntimeException("exception while closing resultset", e);
			}
			
		}
	}

}
