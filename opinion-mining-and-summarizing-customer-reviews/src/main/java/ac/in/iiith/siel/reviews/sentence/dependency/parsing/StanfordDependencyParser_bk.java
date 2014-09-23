package ac.in.iiith.siel.reviews.sentence.dependency.parsing;

import java.util.ArrayList;
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

public class StanfordDependencyParser_bk implements DependencyParser {

	private StanfordCoreNLP pipeline;
	

	public StanfordDependencyParser_bk() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, parse");
		pipeline = new StanfordCoreNLP(props);
	}

	public Sentence parse(final Sentence sentence) {
		Annotation annotation = new Annotation(sentence.getText());
		pipeline.annotate(annotation);
		List<CoreMap> sentences = annotation
				.get(CoreAnnotations.SentencesAnnotation.class);
		ArrayCoreMap sentencemap = (ArrayCoreMap) sentences.get(0);
		SemanticGraph basicSematicGraph = sentencemap
				.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);

		List<Dependency> nsubjDeps = new ArrayList<Dependency>();
		List<Dependency> amodDeps = new ArrayList<Dependency>();
		List<Dependency> nnDeps = new ArrayList<Dependency>();
		List<Dependency> dobjDeps = new ArrayList<Dependency>();
		List<Dependency> negDeps = new ArrayList<Dependency>();

		for (SemanticGraphEdge edge : basicSematicGraph.edgeListSorted()) {
			String reln = edge.getRelation().toString();
			String gov = (edge.getSource()).word();
			int govIdx = (edge.getSource()).index();
			String dep = (edge.getTarget()).word();
			int depIdx = (edge.getTarget()).index();

			if ((reln.equals("nsubj") && edge.getSource().tag().contains("JJ"))
					|| (reln.equals("amod") && edge.getTarget().tag()
							.contains("JJ"))
					|| (reln.equals("dobj") && edge.getSource().tag()
							.contains("JJ")) || reln.equals("nn")
					|| reln.equals("neg")) {
				Governer governer = new Governer();
				governer.setLabel(gov);
				governer.setWordNumber(govIdx);

				Dependent dependent = new Dependent();
				dependent.setLabel(dep);
				dependent.setWordNumber(depIdx);

				Dependency dependency = new Dependency(governer, dependent,
						reln);

				if (reln.equals("nsubj")) {
					nsubjDeps.add(dependency);
				} else if (reln.equals("amod")) {
					amodDeps.add(dependency);
				} else if (reln.equals("nn")) {
					nnDeps.add(dependency);
				} else if (reln.contains("dobj")) {
					dobjDeps.add(dependency);
				} else if (reln.contains("neg")) {
					negDeps.add(dependency);
				}

			}

		}
		List<Dependency> finalDependencies = new ArrayList<Dependency>();

		for (Dependency nnDep : nnDeps) {
			String label = nnDep.getGoverner().getLabel();
			int wordnum = nnDep.getGoverner().getWordNumber();
			for (Dependency nSubjDep : nsubjDeps) {
				if (nSubjDep.getDependent().getLabel().equals(label)
						&& nSubjDep.getDependent().getWordNumber() == wordnum) {
					if (!negDeps.isEmpty()) {
						for (Dependency negDep : negDeps) {
							if (nSubjDep.getGoverner().getLabel()
									.equals(negDep.getGoverner().getLabel())
									&& (nSubjDep.getGoverner().getWordNumber() == negDep
											.getGoverner().getWordNumber())) {
								Governer finalGov = new Governer();
								finalGov.setLabel(negDep.getDependent()
										.getLabel()
										+ " "
										+ negDep.getGoverner().getLabel());
								Dependent finalDependent = new Dependent();
								finalDependent.setLabel(nnDep.getDependent()
										.getLabel()
										+ " "
										+ nnDep.getGoverner().getLabel());
								Dependency finalDep = new Dependency(finalGov,
										finalDependent, "nsubj");
								finalDependencies.add(finalDep);
							} else {
								Governer finalGov = new Governer();
								finalGov.setLabel(nSubjDep.getGoverner()
										.getLabel());
								Dependent finalDependent = new Dependent();
								finalDependent.setLabel(nnDep.getDependent()
										.getLabel()
										+ " "
										+ nnDep.getGoverner().getLabel());
								Dependency finalDep = new Dependency(finalGov,
										finalDependent, "nsubj");
								finalDependencies.add(finalDep);
							}
						}
					} else {
						Governer finalGov = new Governer();
						finalGov.setLabel(nSubjDep.getGoverner()
								.getLabel());
						Dependent finalDependent = new Dependent();
						finalDependent.setLabel(nnDep.getDependent()
								.getLabel()
								+ " "
								+ nnDep.getGoverner().getLabel());
						Dependency finalDep = new Dependency(finalGov,
								finalDependent, "nsubj");
						finalDependencies.add(finalDep);
					}
				}
			}
		}

		for (Dependency nSubjDep : nsubjDeps) {
			if (!negDeps.isEmpty()) {
				for (Dependency negDep : negDeps) {
					if (nSubjDep.getGoverner().getLabel()
							.equals(negDep.getGoverner().getLabel())
							&& (nSubjDep.getGoverner().getWordNumber() == negDep
									.getGoverner().getWordNumber())) {
						Governer finalGov = new Governer();
						finalGov.setLabel(negDep.getDependent().getLabel()
								+ " " + negDep.getGoverner().getLabel());
						Dependent finalDependent = new Dependent();
						finalDependent.setLabel(nSubjDep.getDependent()
								.getLabel());
						Dependency finalDep = new Dependency(finalGov,
								finalDependent, "nsubj");
						finalDependencies.add(finalDep);
					} else {
						finalDependencies.add(nSubjDep);
					}
				}
			} else {
				finalDependencies.add(nSubjDep);
			}
		}

		for (Dependency dobjDep : dobjDeps) {
			if (!negDeps.isEmpty()) {
				for (Dependency negDep : negDeps) {
					if (dobjDep.getGoverner().getLabel()
							.equals(negDep.getGoverner().getLabel())
							&& (dobjDep.getGoverner().getWordNumber() == negDep
									.getGoverner().getWordNumber())) {
						Governer finalGov = new Governer();
						finalGov.setLabel(negDep.getDependent().getLabel()
								+ " " + negDep.getGoverner().getLabel());
						Dependent finalDependent = new Dependent();
						finalDependent.setLabel(dobjDep.getDependent()
								.getLabel());
						Dependency finalDep = new Dependency(finalGov,
								finalDependent, "nsubj");
						finalDependencies.add(finalDep);
					} else {
						finalDependencies.add(dobjDep);
					}
				}
			} else {
				finalDependencies.add(dobjDep);
			}
		}

		for (Dependency finalDep : finalDependencies) {
			sentence.addBasicDependency(finalDep);
		}

		return sentence;
	}

	public static void main(final String args[]) {
		DependencyParser parser = new StanfordDependencyParser_bk();
		OpenNLPSentenceTokenizer tokenizer = new OpenNLPSentenceTokenizer(
				"models/en-sent.bin", "models/en-token.bin");

		// pictures are good. music is bad. headphones are not good. I like the
		// phone overall. camera quality is good. I like pictures. i don't like
		// apps.
		
		List<Sentence> sentences = tokenizer.toSentences("1, Good Display . Good viewing angle from three sides." +
														 "2, Good Sound quality through speakers and headphone Dolby Digital feature is awesome." +
														 "3, Good battery backup since it is LI-Po.." +
														 "4, Good look and nice build quality." +
														 "5, 2G calling & internet." +
														 "6, Brand Value." +
														 "7, 1.2GZ Dual core + power VRSGX531 GPU." +
														 "1, No headphone in the pack you must buy if you don't have one. No HDMI port" +
														 "2, Touch is not feather smooth but it is ok type." +
														 "3, Battery and back cover is not removable but service centers are available for lenevo.and it is their own product and not re-branded so service will be better." +
														 "4, Memory for applications is only around 500Mb free so it will be difficult to install application with size more than 500MB.although 'Apps to SD' app is working fine and installed apps can be moved to SD after installation.");
//		List<Sentence> sentences = tokenizer
//				.toSentences("1}Superb battery back up!" +
//							 "2}Amazing front speaker audio quality/clarity!" +
//							 "3}Mp3 quality through 3.5 mm headset jack even more better than speaker!" +
//							 "4}can pair very easily with bluetooth version 3.0 enabled wireless headset!" +
//							 "5}Sexy Manly Looks! Especially the all black in color one! I just loved it!" +
//							 "1} Call receiver quality sound through earpiece is very poor!" +
//							 "2} Never get messed/muddled with OTG & 2g internet because this device only supports 2G sim internet. No one can insert any of 3g dongles into that OTG female connector what meant only for pen drives or other normal USB port!" +
//							 "3}internal memory one could have of merely 503 MB(?) out of that 1 GB DDR2 RAM which is way pathetic especially for downloading other apps differently from marketplace! The device only gives warning to uninstall a few apps to free spaces very frequently which I personally think a major limitation of this phone!" +
//							 "4}This device always get heated from the backwards while charging; no matter whether you put it into switch on or off mode!" +
//							 "5}I'm not even bothering about the rear camera but front one which comes with a 0.3 MP, but by quality it's simply way deplorable even if me compare it with micromax funbook 0.3 MP camera one. Not expected at least this from Lenovo." +
//							 "6} Tricky travel adapter one is not even a branded lenovo product but been manufactured by some china based third party named HuntKey." +
//							 "7}UI and Touch is desperately needs massive improvement & upgradation. Well I personally assume, regarding UI & Touch sensitivity no one would been dare to level its shoulder with SAMSUNG!" +
//							 "Apart from all these this device is not that bad in this price amount especially after a self renowned branded company followed by Samsung!");

		System.out.println("***** all feature opinion pairs: *****");

		for (Sentence sentence : sentences) {
			parser.parse(sentence);
			for (FeatureOpinionPair fop : sentence.getFeatureOpinionPairs()) {
				System.out.println(fop.getFeature() + ":" + fop.getOpinion());
			}
		}

	}

}
