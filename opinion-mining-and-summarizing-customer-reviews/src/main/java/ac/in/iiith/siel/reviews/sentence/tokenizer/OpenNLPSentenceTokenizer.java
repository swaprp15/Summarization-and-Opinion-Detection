package ac.in.iiith.siel.reviews.sentence.tokenizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import ac.in.iiith.siel.reviews.domain.Sentence;
import ac.in.iiith.siel.reviews.domain.Word;

public class OpenNLPSentenceTokenizer implements SentenceTokenizer {

  private SentenceDetectorME sentenceDetector;

  private Tokenizer tokenizer;

  public OpenNLPSentenceTokenizer(final String pathToSentenceModel, final String pathToTokenizerModel) {
    SentenceModel model = loadSentenceDetectionModel(pathToSentenceModel);
    TokenizerModel tokenizerModel = loadTokenizerModel(pathToTokenizerModel);
    sentenceDetector = new SentenceDetectorME(model);
    tokenizer = new TokenizerME(tokenizerModel);
  }

  public List<Sentence> toSentences(final String text) {
    String sentences[] = sentenceDetector.sentDetect(text);
    Span[] pos = sentenceDetector.sentPosDetect(text);

    List<Sentence> sentencesList = splitSentences(sentences, pos, text);

    for (Sentence sen : sentencesList) {
      String[] tokens = tokenizer.tokenize(sen.getText());
      Span tokenSpans[] = tokenizer.tokenizePos(sen.getText());
      addWords(tokens, tokenSpans, sen);
    }

    return sentencesList;
  }

  private List<Sentence> splitSentences(final String sentences[], final Span[] pos, final String text) {
    List<Sentence> sentencesList = new ArrayList<Sentence>();

    for (int i = 0; i < sentences.length; i++) {
      Sentence sent = new Sentence();
      sent.setText(sentences[i]);
      sent.setStartPos(pos[i].getStart());
      sent.setEndPos(pos[i].getEnd());
      sentencesList.add(sent);
    }

    return sentencesList;
  }

  private void addWords(final  String[] tokens, final Span tokenSpans[], final Sentence sentence) {
    for(int i=0; i < tokens.length; i++) {
      Word word = new Word();
      word.setStartPostion(tokenSpans[i].getStart());
      word.setEndPosition(tokenSpans[i].getEnd());
      word.setText(tokens[i]);
      sentence.addWord(word);
    }
  }

  private SentenceModel loadSentenceDetectionModel(final String pathToModel) {
    InputStream sentenceModelIn = null;

    SentenceModel sentenceModel = null;

    try {
      sentenceModelIn = new FileInputStream(pathToModel);
      sentenceModel = new SentenceModel(sentenceModelIn);
    } catch (IOException e) {
      throw new IllegalStateException("unable to load the model :", e);
    } finally {
      if (sentenceModelIn != null) {
        try {
          sentenceModelIn.close();
        } catch (IOException e) {
        }
      }
    }

    return sentenceModel;

  }

  private TokenizerModel loadTokenizerModel(final String pathToModel) {
    InputStream modelIn = null;
    TokenizerModel model = null;

    try {
      modelIn = new FileInputStream(pathToModel);
      model = new TokenizerModel(modelIn);
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
      tokenizer
          .toSentences("if you considere a prise its a good product but if you willing to take this phone I recomment instead of purchasing this just add some more money into your budget & buy some other phone of Sony xperia series. Look wise phone is low. music is good.");
    for (Sentence sent : sentences) {
      System.out.println("start:" + sent.getStartPos() + ", end:" + sent.getEndPos() + ", text:" + sent.toString());
    }

    for(Sentence sent : sentences) {
      List<Word> words = sent.getWords();
      System.out.println("no of words:" + sent.getWords().size());

      for(Word word : words) {
        System.out.println("word start:" + word.getStartPostion() + ", word end:" + word.getEndPosition() + ", word text:" + word.getText());
      }
    }
  }

}
