package ac.in.iiith.siel.reviews.repository;

public interface SentimentRepository {

	public void saveSentiment(final int productId, final int reviewId, 
												   final int sentenceId, 
												   final String feature, 
												   final String opinion, 
												   final int sentiment);
	public int getPuresupportForFeature(final String feature,
										final String featurePhrase, final int productId); 
}
