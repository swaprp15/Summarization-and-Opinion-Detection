package ac.in.iiith.siel.reviews.product.feature.pruning;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JunkFeaturePruner {
	
	private List<String> junkFeatures = new ArrayList<String>();
	
	public JunkFeaturePruner(final String junkFeaturesFile) {
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(junkFeaturesFile));
			String feature = null;
			while((feature = reader.readLine()) != null) {
				junkFeatures.add(feature);
			}
		} catch(final IOException e) {
			e.printStackTrace();
		} finally {
			if( reader!= null) {
				try {
				reader.close();
				} catch(final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<String> pruneJunkFeatures(final List<String> features) {
		List<String> featuresAfterPruning = new ArrayList<String>();
		for(String f: features) {
			if(!junkFeatures.contains(f)) {
				featuresAfterPruning.add(f);
			}
		}
		
		return featuresAfterPruning;
	}

}
