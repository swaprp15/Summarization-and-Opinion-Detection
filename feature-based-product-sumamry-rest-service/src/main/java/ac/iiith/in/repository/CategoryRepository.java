package ac.iiith.in.repository;

import java.util.List;

public interface CategoryRepository {

	List<String> findAllCategories();
	List<ProductInfo> findProductsForCategory(final String category);
}
