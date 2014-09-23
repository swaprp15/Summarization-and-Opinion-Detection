package ac.iiith.in;

import java.util.ArrayList;
import java.util.List;

public class FeatureSummary {

	private String featureName;
	private int positiveCount;
	private int negtiveCount;

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
			positiveCount += 1;
		} else if(sentenceAndSentiment.getSentiment() < 0) {
			negativeSentiments.add(sentenceAndSentiment);	
			negtiveCount += 1;
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
	
	public int getPositiveCount() {
		return positiveCount;
	}

	public void setPositiveCount(int positiveCount) {
		this.positiveCount = positiveCount;
	}

	public int getNegtiveCount() {
		return negtiveCount;
	}

	public void setNegtiveCount(int negtiveCount) {
		this.negtiveCount = negtiveCount;
	}
	
	
}
