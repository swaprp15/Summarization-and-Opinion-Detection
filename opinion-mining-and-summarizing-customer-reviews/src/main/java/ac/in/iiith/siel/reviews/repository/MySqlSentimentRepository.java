package ac.in.iiith.siel.reviews.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MySqlSentimentRepository extends BaseRepository implements
		SentimentRepository {

	private static String SAVE_SENTIMENT = "insert into SENTENCE_SENTIMENT (product_id, review_id, sentence_id, feature, opinion, sentiment) values (?, ?, ?, ?, ?, ?)";
	private static String PURE_SUPPORT_FOR_FEATURE = "select sentence_id from  SENTENCE_SENTIMENT where feature=? and product_id=?";

	public void saveSentiment(int productId, int reviewId, int sentenceId,
			String feature, String opinion, int sentiment) {

		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBUtils.getConnection();

			st = con.prepareStatement(SAVE_SENTIMENT);
			int i = 1;
			st.setInt(i++, productId);
			st.setInt(i++, reviewId);
			st.setInt(i++, sentenceId);
			st.setString(i++, feature);
			st.setString(i++, opinion);
			st.setInt(i++, sentiment);

			st.executeUpdate();

			con.commit();

		} catch (final Exception e) {
			throw new RuntimeException(
					"exception while inserting sentiment in mysql:", e);
		} finally {
			closeStatment(st);
			closeResultset(rs);
		}

	}

	public int getPuresupportForFeature(final String feature,
			final String featurePhrase, final int productId) {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Integer> sentenceIdsForFeature = new ArrayList<Integer>();
		List<Integer> sentenceIdsForFeaturePhrase = new ArrayList<Integer>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBUtils.getConnection();

			st = con.prepareStatement(PURE_SUPPORT_FOR_FEATURE);
			int i = 1;
			st.setString(i++, feature);
			st.setInt(i++, productId);
			rs = st.executeQuery();
			
			while(rs.next()) {
				sentenceIdsForFeature.add(rs.getInt("sentence_id"));
			}
			
			st = con.prepareStatement(PURE_SUPPORT_FOR_FEATURE);
			i = 1;
			st.setString(i++, featurePhrase);
			st.setInt(i++, productId);
			rs = st.executeQuery();
			
			while(rs.next()) {
				sentenceIdsForFeaturePhrase.add(rs.getInt("sentence_id"));
			}


		} catch (final Exception e) {
			throw new RuntimeException(
					"exception while inserting sentiment in mysql:", e);
		} finally {
			closeStatment(st);
			closeResultset(rs);
		}
		
		sentenceIdsForFeature.removeAll(sentenceIdsForFeaturePhrase);
		return sentenceIdsForFeature.size();
	}

}
