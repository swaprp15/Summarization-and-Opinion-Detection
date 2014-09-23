package ac.in.iiith.siel.reviews.repository;

public class SentenceAndSentiment {

	private String sentence;
	private int sentiment;
	private int sentenceId;
	private int reviewId;

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public int getSentiment() {
		return sentiment;
	}

	public void setSentiment(int sentiment) {
		this.sentiment = sentiment;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getSentenceId() {
		return sentenceId;
	}

	public void setSentenceId(int sentenceId) {
		this.sentenceId = sentenceId;
	}

	@Override
	public boolean equals(final Object other) {
		SentenceAndSentiment ss = (SentenceAndSentiment) other;
		if (this.reviewId == ss.reviewId
				&& this.sentenceId == ss.getSentenceId()
				&& this.sentence.equals(ss.getSentence())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return 31 * 7 + (new Integer(this.reviewId).hashCode()
				+ new Integer(this.sentenceId).hashCode() + sentence.hashCode());
	}

}
