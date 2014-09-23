package ac.iiith.in.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ac.iiith.in.BaseRepository;

public class MySqlProductFeaturesRepository extends BaseRepository implements ProductFeaturesRepository {

	private String SAVE_PRODUCT_FEATURES = "insert into PRODUCT_FEATURES (product_id, feature, is_frequent_feature) values (?, ?, ?)";
	private String FIND_PRODUCT_FEATURES = "select feature from PRODUCT_FEATURES where product_id=? and is_frequent_feature=?";
	
	public void saveProductFeatures(final int productId, final List<String> allFeatures, final List<String> frequentFeatures) {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBUtils.getConnection();

			st = con.prepareStatement(SAVE_PRODUCT_FEATURES);
			for(String feature : allFeatures) {
				int i = 1;
				st.setInt(i++, productId);
				st.setString(i++, feature);
				if(frequentFeatures.contains(feature)) {
					st.setString(i++, "YES");
				} else {
					st.setString(i++, "NO");
				}
				st.addBatch();
				st.executeBatch();
			}
			con.commit();
		} catch (final Exception e) {
			throw new RuntimeException(
					"exception while inserting product features in from mysql:", e);
		} finally {
			closeStatment(st);
			closeResultset(rs);
		}

	}

	public List<String> findProductFeatures(int productId, boolean isFrequent) {
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<String> features = new ArrayList<String>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBUtils.getConnection();
			st = con.prepareStatement(FIND_PRODUCT_FEATURES);
			int i=1;
			st.setInt(i++, productId);
			if(isFrequent) {
				st.setString(i++, "YES");
			} else {
				st.setString(i++, "NO");
			}
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				features.add(rs.getString("feature"));
			}
			
		} catch (final Exception e) {
			throw new RuntimeException(
					"exception while retrieving product features in from mysql:", e);
		} finally {
			closeStatment(st);
			closeResultset(rs);
		}	
		return features;
	}

	
}
