package ac.in.iiith.siel.reviews.repository;

import java.util.ArrayList;
import java.util.List;

public class FeatureSummary {

	private String featureName;

	private List<SentenceAndSentiment> sentencesAndSentiments = new ArrayList<SentenceAndSentiment>();
	private List<SentenceAndSentiment> postiveSentiments = new ArrayList<SentenceAndSentiment>();
	private List<SentenceAndSentiment> negativeSentiments = new ArrayList<SentenceAndSentiment>();

	public void addSentenceAndSentiment(
			final SentenceAndSentiment sentenceAndSentiment) {
		if (!sentencesAndSentiments.contains(sentenceAndSentiment)) {
			sentencesAndSentiments.add(sentenceAndSentiment);
		}
		
		if(sentenceAndSentiment.getSentiment() > 0) {
			postiveSentiments.add(sentenceAndSentiment);
		} else if(sentenceAndSentiment.getSentiment() < 0) {
			negativeSentiments.add(sentenceAndSentiment);	
		}
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public List<SentenceAndSentiment> getSentencesAndSentiments() {
		return sentencesAndSentiments;
	}

	public void setSentencesAndSentiments(
			List<SentenceAndSentiment> sentencesAndSentiments) {
		this.sentencesAndSentiments = sentencesAndSentiments;
	}

	
	public List<SentenceAndSentiment> getPostiveSentiments() {
		return postiveSentiments;
	}

	public void setPostiveSentiments(List<SentenceAndSentiment> postiveSentiments) {
		this.postiveSentiments = postiveSentiments;
	}

	public List<SentenceAndSentiment> getNegativeSentiments() {
		return negativeSentiments;
	}

	public void setNegativeSentiments(List<SentenceAndSentiment> negativeSentiments) {
		this.negativeSentiments = negativeSentiments;
	}

	public int postiveOpinionCount() {
		return postiveSentiments.size();
		
	}

	public int negativeOpinionCount() {
		return negativeSentiments.size();
	}
}
