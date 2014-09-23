package ac.in.iiith.siel.reviews.Main;

public class FeatureIdMapping implements Comparable<FeatureIdMapping>{
	
	private String feature;
	
	private int id;
	
	public FeatureIdMapping(final String feature, final int id) {
		this.feature = feature;
		this.id = id;
	}

	public String getFeature() {
		return feature;
	}


	public int getId() {
		return id;
	}

	public int compareTo(FeatureIdMapping other) {
		// TODO Auto-generated method stub
		return (this.id - other.id);
	}
	
	
	
}
