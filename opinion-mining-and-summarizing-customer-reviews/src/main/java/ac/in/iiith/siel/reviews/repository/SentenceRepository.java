package ac.in.iiith.siel.reviews.repository;

import ac.in.iiith.siel.reviews.domain.Sentence;

public interface SentenceRepository {

	public int saveSentence(final int productId, final int reviewId, final Sentence sentence);
	
	public FeatureSummary findFeatureSummary(final int productId, final String feature);
}
