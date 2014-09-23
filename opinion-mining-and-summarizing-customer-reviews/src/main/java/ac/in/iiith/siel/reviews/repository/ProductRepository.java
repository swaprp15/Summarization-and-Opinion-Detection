package ac.in.iiith.siel.reviews.repository;

import java.util.List;

import ac.in.iiith.siel.reviews.domain.ProductInfo;
import ac.in.iiith.siel.reviews.domain.Review;

public interface ProductRepository {
	int saveProductInfo(final ProductInfo product);
	List<Review> findReviewsForProduct(final int productId);
}
