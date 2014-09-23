package ac.in.iiith.siel.reviews.product.summarizer;

import java.util.ArrayList;
import java.util.List;

import ac.in.iiith.siel.reviews.repository.FeatureSummary;
import ac.in.iiith.siel.reviews.repository.SentenceAndSentiment;

public class ProductSummary {

	private int productId;

	private List<FeatureSummary> summaries = new ArrayList<FeatureSummary>();

	public void addFeatrueSummary(final FeatureSummary summary) {
		summaries.add(summary);
	}

	public List<FeatureSummary> getSummaries() {
		return summaries;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("##########################################################################");
		sb.append(System.getProperty("line.separator"));
		for (FeatureSummary summary : summaries) {
			if (summary.postiveOpinionCount() > 0
					|| summary.negativeOpinionCount() > 0) {
				sb.append("Feature:" + summary.getFeatureName());
				sb.append(System.getProperty("line.separator"));
				if (summary.postiveOpinionCount() > 0) {
					sb.append("Positive:" + summary.postiveOpinionCount());
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
				
				if (summary.negativeOpinionCount() > 0) {
					sb.append("Negative:" + summary.negativeOpinionCount());
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
