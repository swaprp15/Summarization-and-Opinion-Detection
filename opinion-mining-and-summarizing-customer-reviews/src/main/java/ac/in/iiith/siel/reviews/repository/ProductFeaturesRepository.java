package ac.in.iiith.siel.reviews.repository;

import java.util.List;

public interface ProductFeaturesRepository {

	public void saveProductFeatures(final int productId, final List<String> allFeatures, 
														 final List<String> frequentFeatures);
	
	public List<String> findProductFeatures(final int productId, final boolean isFrequent);
}
