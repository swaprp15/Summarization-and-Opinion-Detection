package ac.iiith.in;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ac.iiith.in.repository.DBUtils;
import ac.iiith.in.repository.ProductInfo;



public class MySqlProductRepository extends BaseRepository implements ProductRepository {

	private static String FIND_REVIEW_FOR_PRODUCT = "select comment_text from PRODUCT_REVIEWS where product_id=? and review_id=?";
	private static String FIND_INFO_FOR_PRODUCT = "select product_id, product_name, rating from PRODUCT_INFO where product_id=?";


	@Override
	public String findReviewTextForProduct(int productId, int reviewId) {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String review = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBUtils.getConnection();

			st = con.prepareStatement(FIND_REVIEW_FOR_PRODUCT);
			int i = 1;
			st.setInt(i++, productId);
			st.setInt(i++, reviewId);
			rs = st.executeQuery();
			
			while(rs.next()) {
				review = rs.getString("comment_text");
			}
			

		} catch (final Exception e) {
			throw new RuntimeException(
					"exception while finding review text for productId:" + productId + " and reviewId:" + reviewId, e);
		} finally {
			closeStatment(st);
			closeResultset(rs);
		}	
		
		return review;
		
	}


	@Override
	public ProductInfo findProductInfo(int productId) {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ProductInfo pInfo = new ProductInfo();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBUtils.getConnection();

			st = con.prepareStatement(FIND_INFO_FOR_PRODUCT);
			int i = 1;
			st.setInt(i++, productId);
			rs = st.executeQuery();
			
			while(rs.next()) {
				pInfo.setProductId(rs.getInt("product_id"));
				pInfo.setName(rs.getString("product_name"));
				pInfo.setRating(rs.getInt("rating"));
			}
			
		} catch (final Exception e) {
			throw new RuntimeException(
					"exception while finding info  for productId:" + productId , e);
		} finally {
			closeStatment(st);
			closeResultset(rs);
		}	
		
		return pInfo;	
	}
	
	
	
}
