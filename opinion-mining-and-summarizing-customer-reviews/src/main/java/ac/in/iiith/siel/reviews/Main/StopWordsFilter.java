package ac.in.iiith.siel.reviews.Main;

import java.util.List;

public interface StopWordsFilter {

	List<String> filterStopWords(List<String> tokens);
	
	String[] filterStopWords(String[] tokens);
	
	boolean isStopWord(final String token);
}
