package ac.iiith.in.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ac.iiith.in.BaseRepository;

public class MysqlCategoryRepository extends BaseRepository implements
		CategoryRepository {

	private String FIND_ALL_CATEGORIES = "select distinct(category) from PRODUCT_INFO";
	
	private String FIND_PRODUCTS_FOR_CATEGORY = "select product_name, product_id, rating from PRODUCT_INFO where category=?";

	public List<String> findAllCategories() {

		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<String> categories = new ArrayList<String>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBUtils.getConnection();
			st = con.prepareStatement(FIND_ALL_CATEGORIES);
			rs = st.executeQuery();

			while (rs.next()) {
				categories.add(rs.getString("category"));
			}

		} catch (final Exception e) {
			throw new RuntimeException(
					"exception while retrieving categories in from mysql:", e);
		} finally {
			closeStatment(st);
			closeResultset(rs);
		}
		return categories;
	}
	
	
	public List<ProductInfo> findProductsForCategory(final String category) {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<ProductInfo> products = new ArrayList<ProductInfo>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DBUtils.getConnection();
			st = con.prepareStatement(FIND_PRODUCTS_FOR_CATEGORY);
			int i=1;
			
			st.setString(i++, category);
			rs = st.executeQuery();

			while (rs.next()) {
				ProductInfo pInfo = new ProductInfo(); 
				pInfo.setName(rs.getString("product_name"));
				pInfo.setProductId(rs.getInt("product_id"));
				pInfo.setRating(rs.getInt("rating"));
				products.add(pInfo);
			}

		} catch (final Exception e) {
			throw new RuntimeException(
					"exception while retrieving product info from mysql:", e);
		} finally {
			closeStatment(st);
			closeResultset(rs);
		}
		return products;
	}
}
