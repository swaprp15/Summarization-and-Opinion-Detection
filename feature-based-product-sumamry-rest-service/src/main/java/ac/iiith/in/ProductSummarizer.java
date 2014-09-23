package ac.iiith.in;

import java.util.List;

import ac.iiith.in.repository.MySqlProductFeaturesRepository;
import ac.iiith.in.repository.MySqlSentenceRepository;
import ac.iiith.in.repository.ProductFeaturesRepository;
import ac.iiith.in.repository.ProductInfo;

public class ProductSummarizer {

	private ProductFeaturesRepository featuresRepository = new MySqlProductFeaturesRepository();
	private MySqlSentenceRepository sentenceRepository = new MySqlSentenceRepository();
	private ProductRepository productRepository = new MySqlProductRepository();
	 

	public ProductSummarizer() {
	}
	
	public ProductSummary getSummaryForProduct(final int productId) {
		List<String> features = featuresRepository.findProductFeatures(productId, true);
		ProductInfo productInfo = productRepository.findProductInfo(productId);
		ProductSummary productSummary = new ProductSummary();
		productSummary.setProductId(productInfo.getProductId());
		productSummary.setProductName(productInfo.getName());
		productSummary.setRating(productInfo.getRating());
		
		for(String feature : features) {
			FeatureSummary fs = sentenceRepository.findFeatureSummary(productId, feature);
			productSummary.addFeatrueSummary(fs);
		}
		
		return productSummary;
	}

}
