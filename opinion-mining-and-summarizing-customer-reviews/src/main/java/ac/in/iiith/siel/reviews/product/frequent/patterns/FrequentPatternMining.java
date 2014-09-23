package ac.in.iiith.siel.reviews.product.frequent.patterns;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.common.Pair;
import org.apache.mahout.fpm.pfpgrowth.fpgrowth.FPGrowth;

public class FrequentPatternMining {

	private static FPGrowth<String> fps = new FPGrowth<String>();
	
	public List<String> findFrequentFeatures(final List<String> allFeatures, final List<Pair<List<String>, Long>> featuresInSentences, final int frequencyThreshold) {
		List<String> frequentFeatures = new ArrayList<String>();
		List<Pair<String, Long>> frequencies = fps.generateFList(featuresInSentences.iterator(), frequencyThreshold);
		for(Pair<String, Long> pair : frequencies) {
			frequentFeatures.add(pair.getFirst());
		}
	
		return frequentFeatures;
	}
	
}
