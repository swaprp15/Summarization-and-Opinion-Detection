package ac.in.iiith.siel.reviews.domain;

public enum PennTreeBankPOSTag {

  CC("Coordinating conjuntion"), CD("Cardinal number"), DT("Determiner"), EX("Existential there"), FW("Foreign word"), IN("Preposition or subordinating conjuction"),
  JJ("Adjective"), JJR("Adjective, comparative"), JJS("Adjective, superlative"), LS("List item marker"), MD("Modal"), NN("Noun, singular or mass"), NNS("Noun, plural"),
  NNP("Proper noun, singular"), NNPS("Proper noun, plural"), PDT("Predeterminer"), POS("Possessive ending"), PRP("Personal pronoun"), PRP$("Possessive pronoun"),
  RB("Adverb"), RBR("Adverb, comparative"), RBS("Adverb, superlative"), RP("Particle"), SYM("Symbol"), TO("to"), UH("Interjection"), VB("verb, base form"), VBD("verb past tense"),
  VBG("Verb, gerund or present participle"), VBN("Verb, past participle"), VBP("Verb, non-3rd person singular present"), VBZ("Verb, 3rd position singular present"),
  WDT("Wh-determiner"), WP("Wh-pronoun"), WP$("Possessive wh-pronoun"), WRB("Wh-adverb");

  private String description;

  private PennTreeBankPOSTag(final String description) {
    this.description = description;
  }

  public String description() {
    return description;
  }

}
