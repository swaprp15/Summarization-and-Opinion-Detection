package ac.in.iiith.siel.reviews.sentiment.basic;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.smu.tspell.wordnet.WordSense;


public class SynsetSimilarity {
	
	public static String wordnetDBPath = "../../../MajorProject/ire/opinion-mining-and-summarizing-customer-reviews/WordNet-3.0/dict";

	public static double getScore(String word, SentimentAnalyzer sentimentAnalyzer)
	{
		double score = -2.0;
		
		System.setProperty("wordnet.database.dir", wordnetDBPath);
		
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		Synset[] synsets = database.getSynsets(word);
		
		// Search synsets
		outer:
		for (int i = 0; i < synsets.length; i++)
		{
			String[] wordForms = synsets[i].getWordForms();
			
			for (int j = 0; j < wordForms.length; j++)
			{
				score = sentimentAnalyzer.extract(wordForms[j].toLowerCase(), "a");
				
				if(score != -2.0)
					break outer;
				
			}
		}
		
		return score;
	}

}
