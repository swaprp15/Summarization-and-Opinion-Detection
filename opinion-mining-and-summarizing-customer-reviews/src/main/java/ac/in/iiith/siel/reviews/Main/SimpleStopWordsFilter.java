package ac.in.iiith.siel.reviews.Main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SimpleStopWordsFilter   {

	private Set<String> STOP_WORDS = new HashSet<String>(Arrays.asList("a",
			"an", "and", "are", "as", "at", "be", "but", "by", "for", "if",
			"in", "into", "is", "it", "no", "not", "of", "on", "or", "such",
			"that", "the", "their", "then", "there", "these", "they", "this",
			"to", "was", "will", "with"));


	
	public boolean isStopWord(final String token) {
		return STOP_WORDS.contains(token);
	}

}
