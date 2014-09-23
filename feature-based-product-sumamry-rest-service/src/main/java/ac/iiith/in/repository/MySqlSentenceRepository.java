package ac.iiith.in.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ac.iiith.in.BaseRepository;
import ac.iiith.in.FeatureSummary;
import ac.iiith.in.SentenceAndSentiment;

public class MySqlSentenceRepository extends BaseRepository {

	private final String FEATURE_SUMAMRY = "select a.review_id, a.sentence_id, b.sentence_text, a.sentiment from SENTENCE_SENTIMENT a, REVIEW_SENTENCES b where a.product_id = b.product_id and a.review_id = b.review_id and a.sentence_id = b.sentence_id and a.product_id = ? and a.feature = ?";

	public FeatureSummary findFeatureSummary(final int productId, final String feature) {
		
		FeatureSummary featureSummary = new FeatureSummary();
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBUtils.getConnection();

			st = con.prepareStatement(FEATURE_SUMAMRY);
			int i = 1;
			st.setInt(i++, productId);
			st.setString(i++, feature);
			rs = st.executeQuery();
			
			while(rs.next()) {
				SentenceAndSentiment ss = new SentenceAndSentiment();
				ss.setReviewId(rs.getInt("review_id"));
				ss.setSentenceId(rs.getInt("sentence_id"));
				ss.setSentence(rs.getString("sentence_text"));
				ss.setSentiment(rs.getInt("sentiment"));
				featureSummary.addSentenceAndSentiment(ss);
			}
			
			featureSummary.setFeatureName(feature);

		} catch (final Exception e) {
			throw new RuntimeException(
					"exception while finding sumamry for feature:" + feature, e);
		} finally {
			closeStatment(st);
			closeResultset(rs);
		}

		return featureSummary;
	}

}
