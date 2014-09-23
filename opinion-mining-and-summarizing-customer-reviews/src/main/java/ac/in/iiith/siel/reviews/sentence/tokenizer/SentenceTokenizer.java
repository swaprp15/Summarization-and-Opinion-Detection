package ac.in.iiith.siel.reviews.sentence.tokenizer;

import java.util.List;

import ac.in.iiith.siel.reviews.domain.Sentence;

public interface SentenceTokenizer {

  List<Sentence> toSentences(final String text);
}
