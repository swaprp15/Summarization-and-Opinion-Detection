package ac.iiith.in;

import java.util.ArrayList;
import java.util.List;

public class ProductSummary {

	private int productId;
	private String productName;
	private int rating;
	private int totalPostiveCount;
	private int totalNegativeCount;

	private List<FeatureSummary> featureSummaries = new ArrayList<FeatureSummary>();

	public void addFeatrueSummary(final FeatureSummary summary) {
		featureSummaries.add(summary);
		totalPostiveCount += summary.getPositiveCount();
		totalNegativeCount += summary.getNegtiveCount();
	}

	
	public int getTotalPostiveCount() {
		return totalPostiveCount;
	}


	public void setTotalPostiveCount(int totalPostiveCount) {
		this.totalPostiveCount = totalPostiveCount;
	}


	public int getTotalNegativeCount() {
		return totalNegativeCount;
	}


	public void setTotalNegativeCount(int totalNegativeCount) {
		this.totalNegativeCount = totalNegativeCount;
	}

	public List<FeatureSummary> getFeatureSummaries() {
		return featureSummaries;
	}


	public void setFeatureSummaries(List<FeatureSummary> featureSummaries) {
		this.featureSummaries = featureSummaries;
	}


	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("##########################################################################");
		sb.append(System.getProperty("line.separator"));
		for (FeatureSummary summary : featureSummaries) {
			if (summary.getPositiveCount() > 0
					|| summary.getNegtiveCount() > 0) {
				sb.append("Feature:" + summary.getFeatureName());
				sb.append(System.getProperty("line.separator"));
				if (summary.getPositiveCount() > 0) {
					sb.append("Positive:" + summary.getPositiveCount());
					sb.append(System.getProperty("line.separator"));
					for (SentenceAndSentiment ss : summary
							.getPostiveSentiments()) {
						sb.append("     " + ss.getSentence());
						sb.append(System.getProperty("line.separator"));
						sb.append("     " + "review id:" +ss.getReviewId());
						sb.append(System.getProperty("line.separator"));
						sb.append(System.getProperty("line.separator"));

					}
				}
				
				sb.append(System.getProperty("line.separator"));
				
				if (summary.getNegtiveCount() > 0) {
					sb.append("Negative:" + summary.getNegtiveCount());
					sb.append(System.getProperty("line.separator"));
					for (SentenceAndSentiment ss : summary
							.getNegativeSentiments()) {
						sb.append("     " + ss.getSentence());
						sb.append(System.getProperty("line.separator"));
						sb.append("     " + "review id:" +ss.getReviewId());
						sb.append(System.getProperty("line.separator"));
						sb.append(System.getProperty("line.separator"));
					}
				}
				sb.append(System.getProperty("line.separator"));
				sb.append(System.getProperty("line.separator"));
				sb.append(System.getProperty("line.separator"));
				sb.append("------------------------------------------------------------------------");
				sb.append(System.getProperty("line.separator"));
			}
		}
		sb.append(System.getProperty("line.separator"));
		sb.append("##########################################################################");
		return sb.toString();
	}

}
