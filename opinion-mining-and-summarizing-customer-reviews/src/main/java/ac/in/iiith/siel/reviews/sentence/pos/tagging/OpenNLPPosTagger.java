package ac.in.iiith.siel.reviews.sentence.pos.tagging;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import ac.in.iiith.siel.reviews.domain.PennTreeBankPOSTag;
import ac.in.iiith.siel.reviews.domain.Sentence;
import ac.in.iiith.siel.reviews.domain.Word;
import ac.in.iiith.siel.reviews.sentence.tokenizer.OpenNLPSentenceTokenizer;

public class OpenNLPPosTagger implements PosTagger {

  private POSTaggerME posTagger;

  public OpenNLPPosTagger(final String pathToModel) {
    POSModel posModel = loadPosModel(pathToModel);
    posTagger = new POSTaggerME(posModel);
  }

  public Sentence postag(final Sentence sentence) {
    String[] wordText = sentence.toWordsTextArray();

    List<Word> wordObjects = sentence.getWords();

    String[] posTags = posTagger.tag(wordText);

    for(int i=0; i < wordObjects.size(); i++) {
      try {
      wordObjects.get(i).setPosTag(PennTreeBankPOSTag.valueOf(posTags[i]));
      } catch(final Exception e) {
      }
    }

    return sentence;
  }

  private POSModel loadPosModel(final String pathToModel) {
    InputStream modelIn = null;
    POSModel model = null;

    try {
      modelIn = new FileInputStream(pathToModel);
      model = new POSModel(modelIn);
    } catch (IOException e) {
      throw new IllegalStateException("unable to load the model :", e);
    } finally {
      if (modelIn != null) {
        try {
          modelIn.close();
        } catch (IOException e) {
        }
      }
    }

    return model;

  }

  public static void main(final String[] args) {
    OpenNLPSentenceTokenizer tokenizer = new OpenNLPSentenceTokenizer("models/en-sent.bin", "models/en-token.bin");
    List<Sentence> sentences =
      tokenizer.toSentences("if you considere a prise its a good product but if you willing to take this phone I recomment instead of purchasing this just add some more money into your budget & buy some other phone of Sony xperia series. Look wise phone is low. music is good.");

    OpenNLPPosTagger posTagger = new OpenNLPPosTagger("models/en-pos-maxent.bin");

    for(Sentence sentence : sentences) {
      Sentence posTagSent = posTagger.postag(sentence);
      List<Word> words = posTagSent.getWords();
      for(Word w : words) {
        System.out.println(w.getText() + ":" + w.getPosTag());
      }
    }

  }


}
