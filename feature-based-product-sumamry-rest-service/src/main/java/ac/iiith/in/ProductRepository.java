package ac.iiith.in;

import ac.iiith.in.repository.ProductInfo;

public interface ProductRepository {
	String findReviewTextForProduct(final int productId, final int reviewId);
	ProductInfo findProductInfo(final int productId);
}
