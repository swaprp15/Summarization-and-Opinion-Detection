package ac.in.iiith.siel.reviews.domain;

import java.util.List;

public class Dependency {

	private Governer governer;

	private Dependent dependent;

	private String dependencyName;

	public Dependency(final Governer governer, final Dependent dependent,
			final String dependencyName) {
		this.governer = governer;
		this.dependent = dependent;
		this.dependencyName = dependencyName;
	}

	public Governer getGoverner() {
		return governer;
	}

	public Dependent getDependent() {
		return dependent;
	}

	public String getDependencyName() {
		return dependencyName;
	}
	
	public String getOpinionWord() {
		if(dependencyName.equals("amod")) {
			return dependent.getLabel();
		} else if(dependencyName.equals("nsubj")) {
			return governer.getLabel();
		}
		
		return null;
	}
	
	public String getFeature() {
		if(dependencyName.equals("amod")) {
			return governer.getLabel();
		} else if(dependencyName.equals("nsubj")) {
			return dependent.getLabel();
		}
		
		return null;
	}
	
	public boolean ContainsGovernerWithLableAtPosition(List<Dependency> deps, String label, int position)
	{	
		for(Dependency dep : deps)
		{
			if(dep.getGoverner().getLabel().equals(label) && dep.getGoverner().getWordNumber() == position)
				return true;
		}
		
		return false;
	}

}
