package ac.in.iiith.siel.reviews.sentence.dependency.parsing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import ac.in.iiith.siel.reviews.Main.SimpleStemmer;
import ac.in.iiith.siel.reviews.Main.SimpleStopWordsFilter;
import ac.in.iiith.siel.reviews.domain.Dependency;
import ac.in.iiith.siel.reviews.domain.Dependent;
import ac.in.iiith.siel.reviews.domain.FeatureOpinionPair;
import ac.in.iiith.siel.reviews.domain.Governer;
import ac.in.iiith.siel.reviews.domain.Sentence;
import ac.in.iiith.siel.reviews.sentence.tokenizer.OpenNLPSentenceTokenizer;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.ArrayCoreMap;
import edu.stanford.nlp.util.CoreMap;

public class StanfordDependencyParser implements DependencyParser {

	private StanfordCoreNLP pipeline;
	private SimpleStopWordsFilter stopWordsFilter = new SimpleStopWordsFilter();
	private SimpleStemmer stemmer = new SimpleStemmer();

	private List<String> pronounsList = Arrays.asList("all", "another", "any",
			"anybody", "anyone", "anything", "both", "each", "each other",
			"either", "everybody", "everyone", "everything", "few", "he",
			"her", "hers", "herself", "him", "himself", "his", "i", "it",
			"its", "itself", "little", "many", "me", "mine", "more", "most",
			"much", "my", "myself", "neither", "no one", "nobody", "none",
			"nothing", "one", "one another", "other", "others", "our", "ours",
			"ourselves", "several", "she", "some", "somebody", "someone",
			"something", "that", "their", "theirs", "them", "themselves",
			"these", "they", "this", "those", "us", "we", "what", "whatever",
			"which", "whichever", "who", "whoever", "whom", "whomever",
			"whose", "you", "your", "yours", "yourself", "yourselves");

	public StanfordDependencyParser() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, parse");
		pipeline = new StanfordCoreNLP(props);
	}
	
	public boolean ContainsGovernerWithLableAtPositionAndDependantIs(List<Dependency> deps, String label, int position, String dependent)
	{	
		for(Dependency dep : deps)
		{
			if(dep.getGoverner().getLabel().equals(label) && dep.getGoverner().getWordNumber() == position && dep.getDependent().getLabel().equals(dependent))
				return true;
		}
		
		return false;
	}
	
	public boolean ContainsGovernerWithLableAndDependantIs(List<Dependency> deps, String label, String dependent)
	{	
		for(Dependency dep : deps)
		{
			if(dep.getGoverner().getLabel().equalsIgnoreCase(label)  && dep.getDependent().getLabel().equalsIgnoreCase(dependent))
				return true;
		}
		
		return false;
	}

	public Sentence parse(final Sentence sentence) {

		Annotation annotation = new Annotation(sentence.getText().replaceAll(
				"[^a-zA-Z'\\s]", " "));
		pipeline.annotate(annotation);
		List<CoreMap> sentences = annotation
				.get(CoreAnnotations.SentencesAnnotation.class);
		if (sentences.size() > 0) {
			ArrayCoreMap sentencemap = (ArrayCoreMap) sentences.get(0);
			SemanticGraph basicSematicGraph = sentencemap
					.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);

			List<Dependency> nsubjDeps = new ArrayList<Dependency>();
			List<Dependency> amodDeps = new ArrayList<Dependency>();
			List<Dependency> nnDeps = new ArrayList<Dependency>();
			List<Dependency> negDeps = new ArrayList<Dependency>();
			List<Dependency> auxDeps = new ArrayList<Dependency>();
			List<Dependency> dobjDeps = new ArrayList<Dependency>();
			List<Dependency> copDeps = new ArrayList<Dependency>();
			List<Dependency> prepDeps = new ArrayList<Dependency>();
			List<Dependency> detDeps = new ArrayList<Dependency>();

			for (SemanticGraphEdge edge : basicSematicGraph.edgeListSorted()) {
				String reln = edge.getRelation().toString();
				String gov = (edge.getSource()).word();
				int govIdx = (edge.getSource()).index();
				String govTag = edge.getSource().tag();
				String dep = (edge.getTarget()).word();
				int depIdx = (edge.getTarget()).index();
				String depTag = edge.getTarget().tag();

				Governer governer = new Governer();
				governer.setLabel(gov);
				governer.setWordNumber(govIdx);
				governer.setTag(govTag);

				Dependent dependent = new Dependent();
				dependent.setLabel(dep);
				dependent.setWordNumber(depIdx);
				dependent.setTag(depTag);

				Dependency dependency = new Dependency(governer, dependent,
						reln);

				if (reln.equals("nsubj"))
				/*		&& edge.getSource().tag().contains("JJ")
						&& edge.getTarget().tag().contains("NN")) */{

					nsubjDeps.add(dependency);

				} else if (reln.equals("amod")
						&& edge.getTarget().tag().contains("JJ")
						&& edge.getSource().tag().contains("NN")) {
					amodDeps.add(dependency);
				} else if (reln.equals("nn")) {
					nnDeps.add(dependency);
				} else if (reln.equals("neg")) {
					negDeps.add(dependency);
				}
				else if (reln.equals("aux"))
				{
					auxDeps.add(dependency);
				}
				else if(reln.equals("dobj"))
				{
					dobjDeps.add(dependency);
				}
				else if(reln.equals("cop"))
				{
					copDeps.add(dependency);
				}
				else if(reln.equals("prep"))
				{
					prepDeps.add(dependency);
				}
				else if(reln.equals("det"))
				{
					detDeps.add(dependency);
				}
					
			}

			for (Dependency nnDep : nnDeps) {
				String feature = (nnDep.getDependent().getLabel() + " " + nnDep
						.getGoverner().getLabel()).toLowerCase();
				FeatureOpinionPair foPair = new FeatureOpinionPair();
				for (Dependency nsubjDep : nsubjDeps) {
					if (nnDep.getGoverner().getWordNumber() == nsubjDep
							.getDependent().getWordNumber()) {
						if (!pronounsList.contains(feature)
								&& !stopWordsFilter.isStopWord(feature)) {

							String opinion = nsubjDep.getGoverner().getLabel();
							foPair.setFeature(feature);
							foPair.setOpinion(opinion);
							sentence.addFeatureOpinionPair(foPair);
						}
					}
				}

				for (Dependency amodDep : amodDeps) {
					if (nnDep.getGoverner().getWordNumber() == amodDep
							.getGoverner().getWordNumber()) {
						if (!pronounsList.contains(feature)
								&& !stopWordsFilter.isStopWord(feature)) {

							String opinion = amodDep.getDependent().getLabel();
							foPair.setFeature(feature);
							foPair.setOpinion(opinion);
							sentence.addFeatureOpinionPair(foPair);
						}
					}
				}
			}

			Iterator<Dependency> nsubjItr = nsubjDeps.iterator();

			while (nsubjItr.hasNext()) {
				Dependency nsubjDep = nsubjItr.next();
				String feature = nsubjDep.getDependent().getLabel()
						.toLowerCase();
				
				if (pronounsList.contains(feature)
						|| stopWordsFilter.isStopWord(feature))
					continue;
				
				boolean alreadyAdded = false;
				
				for (Dependency dep : negDeps) {
					if (nsubjDep.getGoverner().getWordNumber() == dep
							.getGoverner().getWordNumber()) {
						if (!pronounsList.contains(feature)
								&& !stopWordsFilter.isStopWord(feature)) {

							String opinion = dep.getDependent().getLabel()
									+ " " + nsubjDep.getGoverner().getLabel();
							FeatureOpinionPair fopair = new FeatureOpinionPair();
							fopair.setFeature(feature);
							fopair.setOpinion(opinion);
							sentence.addFeatureOpinionPair(fopair);
							alreadyAdded = true;
							nsubjItr.remove();
						}
					}
				}
				
				if(alreadyAdded)
					continue;
				
				// Else check for sentence like 'doesn't have <adj> <noun>'
				for(Dependency dobjDep : dobjDeps)
				{
					if( nsubjDep.getGoverner().getLabel().equals(dobjDep.getDependent().getLabel()) && 
					    nsubjDep.getGoverner().getWordNumber() == dobjDep.getDependent().getWordNumber())
					{
						for(Dependency negDep : negDeps)
						{
							if( dobjDep.getGoverner().getLabel().equals(negDep.getGoverner().getLabel()) &&
									dobjDep.getGoverner().getWordNumber() == negDep.getGoverner().getWordNumber())
							{
								for(Dependency auxDep : auxDeps)
								{
									if( auxDep.getGoverner().getLabel().equals(negDep.getGoverner().getLabel()) &&
									    auxDep.getGoverner().getWordNumber() == negDep.getGoverner().getWordNumber() &&
									    auxDep.getDependent().equals("does"))
									{
										
									}
								}
							}
						}
					}
				}
				
				if(alreadyAdded)
					continue;
				
				// Rule for <adjective> should be <opinion>
				if( ContainsGovernerWithLableAtPositionAndDependantIs(auxDeps, nsubjDep.getGoverner().getLabel(), nsubjDep.getGoverner().getWordNumber(), "should") &&
				    ContainsGovernerWithLableAtPositionAndDependantIs(copDeps, nsubjDep.getGoverner().getLabel(), nsubjDep.getGoverner().getWordNumber(), "be"))
				{
						FeatureOpinionPair fopair = new FeatureOpinionPair();
						fopair.setFeature(feature);
						fopair.setOpinion("not" + " " + nsubjDep.getGoverner().getLabel());
						sentence.addFeatureOpinionPair(fopair);
						
						alreadyAdded = true;
						nsubjItr.remove();
				}
				
				if(alreadyAdded)
					continue;
				
				// Rule for <adjective> should have been <opinion>
				if( ContainsGovernerWithLableAtPositionAndDependantIs(auxDeps, nsubjDep.getGoverner().getLabel(), nsubjDep.getGoverner().getWordNumber(), "should") &&
					ContainsGovernerWithLableAtPositionAndDependantIs(auxDeps, nsubjDep.getGoverner().getLabel(), nsubjDep.getGoverner().getWordNumber(), "have")  &&
					ContainsGovernerWithLableAtPositionAndDependantIs(copDeps, nsubjDep.getGoverner().getLabel(), nsubjDep.getGoverner().getWordNumber(), "been"))
				{
					FeatureOpinionPair fopair = new FeatureOpinionPair();
					fopair.setFeature(feature);
					fopair.setOpinion("not" + " " + nsubjDep.getGoverner().getLabel());
					sentence.addFeatureOpinionPair(fopair);
					
					alreadyAdded = true;
					nsubjItr.remove();
				}
				
				if(alreadyAdded)
					continue;
				

			}

			nsubjItr = nsubjDeps.iterator();

			while (nsubjItr.hasNext()) {
				Dependency nsubjDep = nsubjItr.next();
				String feature = nsubjDep.getDependent().getLabel()
						.toLowerCase();
				String opinion = nsubjDep.getGoverner().getLabel();
				
				if(!nsubjDep.getGoverner().getTag().contains("JJ") && !nsubjDep.getDependent().getTag().contains("NN"))
					continue;
				
				if (!pronounsList.contains(feature)
						|| !stopWordsFilter.isStopWord(feature)) {

					FeatureOpinionPair fopair = new FeatureOpinionPair();
					fopair.setFeature(feature);
					fopair.setOpinion(opinion);
					sentence.addFeatureOpinionPair(fopair);
				}
			}

			Iterator<Dependency> amodItr = amodDeps.iterator();

			while (amodItr.hasNext()) {
				Dependency amodDep = amodItr.next();
				String feature = amodDep.getGoverner().getLabel().toLowerCase();
				
				if (pronounsList.contains(feature)
						|| stopWordsFilter.isStopWord(feature))
					continue;
				
				boolean alreadyAdded = false;
				
				for (Dependency dep : negDeps) {
					if (amodDep.getGoverner().getWordNumber() == dep
							.getGoverner().getWordNumber()) {
						if (!pronounsList.contains(feature)
								|| !stopWordsFilter.isStopWord(feature)) {

							String opinion = dep.getDependent().getLabel()
									+ " " + amodDep.getDependent().getLabel();
							FeatureOpinionPair fopair = new FeatureOpinionPair();
							fopair.setFeature(feature);
							fopair.setOpinion(opinion);
							sentence.addFeatureOpinionPair(fopair);
							alreadyAdded = true;
							amodItr.remove();
						}
					}
				}
				
				if(alreadyAdded)
					continue;
				
				for(Dependency dobjDep : dobjDeps)
				{
					if( amodDep.getGoverner().getLabel().equals(dobjDep.getDependent().getLabel()) && 
							amodDep.getGoverner().getWordNumber() == dobjDep.getDependent().getWordNumber())
					{
						for(Dependency negDep : negDeps)
						{
							if( dobjDep.getGoverner().getLabel().equals(negDep.getGoverner().getLabel()) &&
									dobjDep.getGoverner().getWordNumber() == negDep.getGoverner().getWordNumber())
							{
								for(Dependency auxDep : auxDeps)
								{
									if( auxDep.getGoverner().getLabel().equals(negDep.getGoverner().getLabel()) &&
									    auxDep.getGoverner().getWordNumber() == negDep.getGoverner().getWordNumber() &&
									    (auxDep.getDependent().getLabel().equals("does") || auxDep.getDependent().getLabel().equals("do") ))
									{
										    String opinion = "not"
													+ " " + amodDep.getDependent().getLabel();
											FeatureOpinionPair fopair = new FeatureOpinionPair();
											fopair.setFeature(feature);
											fopair.setOpinion(opinion);
											sentence.addFeatureOpinionPair(fopair);
											alreadyAdded = true;
											amodItr.remove();
									}
								}
							}
						}
						
						if(alreadyAdded)
							continue;
						
						// negation not found. Check for 'should have'
						
						for(Dependency auxDep : auxDeps)
						{
							if( dobjDep.getGoverner().getLabel().equals(auxDep.getGoverner().getLabel()) && 
								dobjDep.getGoverner().getWordNumber() == auxDep.getGoverner().getWordNumber() &&
								auxDep.getDependent().getLabel().equals("should"))
							{
								if (!pronounsList.contains(feature)
										|| !stopWordsFilter.isStopWord(feature)) {

									String opinion = "not"
											+ " " + amodDep.getDependent().getLabel();
									FeatureOpinionPair fopair = new FeatureOpinionPair();
									fopair.setFeature(feature);
									fopair.setOpinion(opinion);
									sentence.addFeatureOpinionPair(fopair);
									alreadyAdded = true;
									amodItr.remove();
								}
							}
						}
						
						if(alreadyAdded)
							continue;
					}
				}
				
				// One of the <> <featue> prular
				if( ContainsGovernerWithLableAndDependantIs(prepDeps, "one", "of") &&
					ContainsGovernerWithLableAtPositionAndDependantIs(detDeps, feature, amodDep.getGoverner().getWordNumber(), "the") &&
					feature.endsWith("s") )
					{
						String opinion = amodDep.getDependent().getLabel();
						FeatureOpinionPair fopair = new FeatureOpinionPair();
						fopair.setFeature(feature.substring(0, feature.length()-1));
						fopair.setOpinion(opinion);
						sentence.addFeatureOpinionPair(fopair);
						
						alreadyAdded = true;
						amodItr.remove();
				
					}
			}

			amodItr = amodDeps.iterator();

			while (amodItr.hasNext()) {
				Dependency amodDep = amodItr.next();
				String feature = amodDep.getGoverner().getLabel().toLowerCase();
				String opinion = amodDep.getDependent().getLabel();
				if (!pronounsList.contains(feature)
						|| !stopWordsFilter.isStopWord(feature)) {

					FeatureOpinionPair fopair = new FeatureOpinionPair();
					fopair.setFeature(feature);
					fopair.setOpinion(opinion);
					sentence.addFeatureOpinionPair(fopair);
				}
			}
		}
		
		return sentence;
	}

	public static void main(final String args[]) {
		DependencyParser parser = new StanfordDependencyParser();
		OpenNLPSentenceTokenizer tokenizer = new OpenNLPSentenceTokenizer(
				"models/en-sent.bin", "models/en-token.bin");
		// List<Sentence> sentences =
		// tokenizer.toSentences("1, Good Display . Good viewing angle from three sides."
		// +
		// "2, Good Sound quality through speakers and headphone Dolby Digital feature is awesome."
		// +
		// "3, Good battery backup since it is LI-Po.." +
		// "4, Good look and nice build quality." +
		// "5, 2G calling & internet." +
		// "6, Brand Value." +
		// "7, 1.2GZ Dual core + power VRSGX531 GPU." +
		// "1, No headphone in the pack you must buy if you don't have one. No HDMI port"
		// +
		// "2, Touch is not feather smooth but it is ok type." +
		// "3, Battery and back cover is not removable but service centers are available for lenevo.and it is their own product and not re-branded so service will be better."
		// +
		// "4, Memory for applications is only around 500Mb free so it will be difficult to install application with size more than 500MB.although 'Apps to SD' app is working fine and installed apps can be moved to SD after installation.");

		// List<Sentence> sentences = tokenizer
		// .toSentences("1}Superb battery back up!" +
		// "2}Amazing front speaker audio quality/clarity!" +
		// "3}Mp3 quality through 3.5 mm headset jack even more better than speaker!"
		// +
		// "4}can pair very easily with bluetooth version 3.0 enabled wireless headset!"
		// +
		// "5}Sexy Manly Looks! Especially the all black in color one! I just loved it!"
		// +
		// "1} Call receiver quality sound through earpiece is very poor!" +
		// "2} Never get messed/muddled with OTG & 2g internet because this device only supports 2G sim internet. No one can insert any of 3g dongles into that OTG female connector what meant only for pen drives or other normal USB port!"
		// +
		// "3}internal memory one could have of merely 503 MB(?) out of that 1 GB DDR2 RAM which is way pathetic especially for downloading other apps differently from marketplace! The device only gives warning to uninstall a few apps to free spaces very frequently which I personally think a major limitation of this phone!"
		// +
		// "4}This device always get heated from the backwards while charging; no matter whether you put it into switch on or off mode!"
		// +
		// "5}I'm not even bothering about the rear camera but front one which comes with a 0.3 MP, but by quality it's simply way deplorable even if me compare it with micromax funbook 0.3 MP camera one. Not expected at least this from Lenovo."
		// +
		// "6} Tricky travel adapter one is not even a branded lenovo product but been manufactured by some china based third party named HuntKey."
		// +
		// "7}UI and Touch is desperately needs massive improvement & upgradation. Well I personally assume, regarding UI & Touch sensitivity no one would been dare to level its shoulder with SAMSUNG!"
		// +
		// "Apart from all these this device is not that bad in this price amount especially after a self renowned branded company followed by Samsung!");

		List<Sentence> sentences = tokenizer
				.toSentences("One of the best books on India's recent history.");

		System.out.println("***** all feature opinion pairs: *****");

		for (Sentence sentence : sentences) {
			parser.parse(sentence);
			for (FeatureOpinionPair fop : sentence.getFeatureOpinionPairs()) {
				System.out.println(fop.getFeature() + ":" + fop.getOpinion());
			}
		}

	}

}
