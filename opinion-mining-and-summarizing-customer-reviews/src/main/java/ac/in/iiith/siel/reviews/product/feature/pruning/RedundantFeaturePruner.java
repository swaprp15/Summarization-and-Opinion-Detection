package ac.in.iiith.siel.reviews.product.feature.pruning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ac.in.iiith.siel.reviews.repository.MySqlSentimentRepository;
import ac.in.iiith.siel.reviews.repository.SentimentRepository;

public class RedundantFeaturePruner {
	
	private SentimentRepository sentimentRepository = new MySqlSentimentRepository();
	
	private int minPureSupport;
	
	public RedundantFeaturePruner(final int minPureSupport) {
		this.minPureSupport = minPureSupport;
	}

	public List<String> pruneRedundantFeatures(final List<String> features, final int productId) {
		List<RedunantFeature> redundantFeatures = new ArrayList<RedunantFeature>();
		
		for (int i = 0; i < features.size(); i++) {
			for (int j = i; j < features.size(); j++) {
				if (isFeaturePhrase(features.get(i))) {
					String feature = features.get(j);
					String featurePhrase = features.get(i);
					if (featurePhrase.contains(feature)) {
						int pureSupport = sentimentRepository.getPuresupportForFeature(feature, featurePhrase, productId);
						addRedundantFeature(feature, featurePhrase, pureSupport, redundantFeatures);
					}

				} else if (isFeaturePhrase(features.get(j))) {
					String feature = features.get(i);
					String featurePhrase = features.get(j);

					if (featurePhrase.contains(feature)) {
						int pureSupport = sentimentRepository.getPuresupportForFeature(feature, featurePhrase, productId);
						addRedundantFeature(feature, featurePhrase, pureSupport, redundantFeatures);
					}
				}
			}
		}

		Iterator<RedunantFeature> it = redundantFeatures.iterator();
		List<String> filteredFeatures = new ArrayList<String>();
		
		while(it.hasNext()) {
			RedunantFeature rf = it.next();
			if(rf.getPureSupport() > minPureSupport) {
				filteredFeatures.add(rf.getFeature());
			}
		}
		
		features.removeAll(filteredFeatures);
		
		return features;
	}
	

	private void addRedundantFeature(final String feature, final String featurePhrase, final int pureSupport, final List<RedunantFeature> redundantFeatures) {
		RedunantFeature rf = new RedunantFeature();
		rf.setFeature(feature);
		rf.setFeaturePhrase(featurePhrase);
		rf.setPureSupport(pureSupport);
		redundantFeatures.add(rf);
	}
	
	private boolean isFeaturePhrase(final String feature) {
		return feature.split(" ").length > 1 ? true : false;
	}

	private static final class RedunantFeature {
		private String feature;
		private String featurePhrase;
		private int pureSupport;

		public String getFeature() {
			return feature;
		}

		public void setFeature(String feature) {
			this.feature = feature;
		}

		public String getFeaturePhrase() {
			return featurePhrase;
		}

		public void setFeaturePhrase(String featurePhrase) {
			this.featurePhrase = featurePhrase;
		}

		public int getPureSupport() {
			return pureSupport;
		}

		public void setPureSupport(int pureSupport) {
			this.pureSupport = pureSupport;
		}

	}
}
