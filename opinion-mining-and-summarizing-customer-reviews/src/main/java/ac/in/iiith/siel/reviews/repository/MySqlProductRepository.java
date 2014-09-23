package ac.in.iiith.siel.reviews.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ac.in.iiith.siel.reviews.domain.ProductInfo;
import ac.in.iiith.siel.reviews.domain.Review;


public class MySqlProductRepository extends BaseRepository implements ProductRepository {

	private static String FIND_REVIEWS_FOR_PRODUCT = "select review_id, product_id, commented_user, commented_date_time, comment_text, rating from PRODUCT_REVIEWS where product_id=?";

	private String EscapeSingeQuote(String str) {
		return str.replaceAll("'", "''");

	}

	public int saveProductInfo(final ProductInfo product) {
		/**
		 * Here you need to write code to insert product info in product
		 * argument into mysql table.
		 */

		try {
			Connection con = DBUtils.getConnection();
			Statement st = con.createStatement();

			int productId = product.getId();
			String name = EscapeSingeQuote(product.getName());

			int rating = product.getRating();
			String category = EscapeSingeQuote(product.getCategory());

			// int res = st.executeUpdate("insert into PRODUCT_INFO values (" +
			// id + ", '" + name + "', " + rating + ", '" + category + "')");

			int res = st
					.executeUpdate("insert into PRODUCT_INFO (product_id, product_name, rating, category) values ("
							+ productId
							+ ", '"
							+ EscapeSingeQuote(name)
							+ "', "
							+ rating
							+ ", '"
							+ EscapeSingeQuote(category) + "')");

			for (Review review : product.getReviews()) {
				res = st.executeUpdate("insert into PRODUCT_REVIEWS (review_id, product_id, commented_user, commented_date_time, comment_text, rating) values ("
						+ review.getReviewId()
						+ ", "
						+ productId
						+ ", '"
						+ EscapeSingeQuote(review.getCommentedUserName())
						+ "', '"
						+ EscapeSingeQuote(review.getCommentedDate())
						+ "', '"
						+ EscapeSingeQuote(review.getComment())
						+ "', " + review.getRating() + ")");
			}

			con.commit();
			closeStatment(st);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return 0;
	}

	public List<Review> findReviewsForProduct(int productId) {
		List<Review> reviews = new ArrayList<Review>();
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBUtils.getConnection();
			
			 st = con
					.prepareStatement(FIND_REVIEWS_FOR_PRODUCT);
			 st.setInt(1, productId);
			 rs = st.executeQuery();

			while (rs.next()) {
				Review review = new Review();
				review.setReviewId(rs.getInt("review_id"));
				review.setProductId(rs.getInt("product_id"));
				review.setRating(rs.getInt("rating"));
				review.setCommentedUserName(rs.getString("commented_user"));
				review.setCommentedDate(rs.getString("commented_date_time"));
				review.setComment(rs.getString("comment_text"));
				reviews.add(review);
			}

		} catch (final Exception e) {
			throw new RuntimeException(
					"exception while fetching reviews from mysql:", e);
		} finally {
			closeStatment(st);
			closeResultset(rs);
		}
		return reviews;
	}
	
}
