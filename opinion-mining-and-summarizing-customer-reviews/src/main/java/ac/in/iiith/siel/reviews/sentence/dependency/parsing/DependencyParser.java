package ac.in.iiith.siel.reviews.sentence.dependency.parsing;

import ac.in.iiith.siel.reviews.domain.Sentence;

public interface DependencyParser {
	
	Sentence parse(final Sentence sentence);
	
}
