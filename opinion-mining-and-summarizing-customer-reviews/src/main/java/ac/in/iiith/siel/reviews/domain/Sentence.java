package ac.in.iiith.siel.reviews.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sentence {
	private String text;
	private int startPos;
	private int endPos;
	private List<Word> words = new ArrayList<Word>();	
	private Set<Integer> featureIds = new HashSet<Integer>();
	private List<Dependency> basicDependencies = new ArrayList<Dependency>();
	private List<FeatureOpinionPair> featureOpinionPairs = new ArrayList<FeatureOpinionPair>();
	

	public void addFeatureId(final int id) {
		featureIds.add(id);
	}
	
	public List<Integer> getFeatureIds() {
		List<Integer> listOfIds = new ArrayList<Integer>(featureIds);
		 Collections.sort(listOfIds);
		 return listOfIds;
	}
	
	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(final List<Word> words) {
		this.words = words;
	}

	public void addWord(final Word word) {
		words.add(word);
	}

	public int getStartPos() {
		return startPos;
	}

	public void setStartPos(final int startPos) {
		this.startPos = startPos;
	}

	public int getEndPos() {
		return endPos;
	}

	public void setEndPos(final int endPos) {
		this.endPos = endPos;
	}

	public List<Word> getAllOpinionWords() {
		List<Word> opinionWords = new ArrayList<Word>();
		for (Word w : words) {

			if (w.getPosTag() != null
					&& w.getPosTag().equals(PennTreeBankPOSTag.JJ)) {
				opinionWords.add(w);
			}
		}

		return opinionWords;
	}
	
	

	public String[] toWordsTextArray() {
		String[] wordsArray = new String[words.size()];
		for (int i = 0; i < words.size(); i++) {
			wordsArray[i] = words.get(i).getText();
		}

		return wordsArray;
	}

	public List<FeatureOpinionPair> getFeatureOpinionPairs() {
		
		return featureOpinionPairs;
	}
	
	
	public void addBasicDependency(final Dependency dependency) {
		this.basicDependencies.add(dependency);
	}

	
	public List<Dependency> getBasicDependencies() {
		return basicDependencies;
	}

	public void addFeatureOpinionPair(final FeatureOpinionPair featureOpinionPair) {
		this.featureOpinionPairs.add(featureOpinionPair);
	}

	public void setFeatureOpinionPairs(List<FeatureOpinionPair> featureOpinionPairs) {
		this.featureOpinionPairs = featureOpinionPairs;
	}

	@Override
	public String toString() {
		return text;
	}
	


}
