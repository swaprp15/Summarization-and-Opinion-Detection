package ac.in.iiith.siel.reviews.Main;

public class SimpleStemmer {

	private EnglishStemmer engStemmer = new EnglishStemmer();

	public String stem(String token) {
		engStemmer.setCurrent(token);
		engStemmer.stem();
		return engStemmer.getCurrent();
	}

}
