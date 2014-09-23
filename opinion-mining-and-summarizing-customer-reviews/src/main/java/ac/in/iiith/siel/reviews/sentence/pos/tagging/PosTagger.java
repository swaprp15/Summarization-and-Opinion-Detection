package ac.in.iiith.siel.reviews.sentence.pos.tagging;

import ac.in.iiith.siel.reviews.domain.Sentence;

public interface PosTagger {

  Sentence postag(final Sentence sentece);
}
