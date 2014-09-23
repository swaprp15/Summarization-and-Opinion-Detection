package ac.in.iiith.siel.reviews.product.summarizer;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.mahout.common.Pair;

import ac.in.iiith.siel.reviews.domain.FeatureOpinionPair;
import ac.in.iiith.siel.reviews.domain.Review;
import ac.in.iiith.siel.reviews.domain.Sentence;
import ac.in.iiith.siel.reviews.product.feature.pruning.JunkFeaturePruner;
import ac.in.iiith.siel.reviews.product.feature.pruning.RedundantFeaturePruner;
import ac.in.iiith.siel.reviews.product.frequent.patterns.FrequentPatternMining;
import ac.in.iiith.siel.reviews.repository.FeatureSummary;
import ac.in.iiith.siel.reviews.repository.MySqlProductFeaturesRepository;
import ac.in.iiith.siel.reviews.repository.MySqlProductRepository;
import ac.in.iiith.siel.reviews.repository.MySqlSentenceRepository;
import ac.in.iiith.siel.reviews.repository.MySqlSentimentRepository;
import ac.in.iiith.siel.reviews.repository.ProductFeaturesRepository;
import ac.in.iiith.siel.reviews.repository.ProductRepository;
import ac.in.iiith.siel.reviews.repository.SentenceRepository;
import ac.in.iiith.siel.reviews.repository.SentimentRepository;
import ac.in.iiith.siel.reviews.sentence.dependency.parsing.DependencyParser;
import ac.in.iiith.siel.reviews.sentence.dependency.parsing.StanfordDependencyParser;
import ac.in.iiith.siel.reviews.sentence.tokenizer.OpenNLPSentenceTokenizer;
import ac.in.iiith.siel.reviews.sentence.tokenizer.SentenceTokenizer;
import ac.in.iiith.siel.reviews.sentiment.analysis.SentimentAnalyzer;

public class ProductSummarizer {

	private ProductRepository productRepository = new MySqlProductRepository();
	private SentenceRepository sentenceRepository = new MySqlSentenceRepository();
	private SentimentRepository sentimentRepository = new MySqlSentimentRepository();
	private SentenceTokenizer sentenceTokenizer = new OpenNLPSentenceTokenizer("models/en-sent.bin", "models/en-token.bin");
	private DependencyParser dependencyParser = new StanfordDependencyParser();
	private SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
	private ProductFeaturesRepository featuresRepository = new MySqlProductFeaturesRepository();
	private FrequentPatternMining frequentPatternMining = new FrequentPatternMining();
	private JunkFeaturePruner junkFeaturePruner = new JunkFeaturePruner("junk_features.txt");
	private RedundantFeaturePruner redundantFeaturePruner = new RedundantFeaturePruner(3);


	public ProductSummarizer() {
		sentimentAnalyzer.loadSeedList();
	}
	
	public void generateSummaryForProduct(final int productId) {
		List<Review> reviews = productRepository.findReviewsForProduct(productId);
		Set<String> features = new HashSet<String>();
		List<Pair<List<String>, Long>> featuresInAllSentences = new ArrayList<Pair<List<String>, Long>>();
		
		for(Review review : reviews) {
			System.out.println("parsing review:" + review.getReviewId() + " in product:" + review.getProductId());
			List<Sentence> sentences = sentenceTokenizer.toSentences(review.getComment());
			for(Sentence sent : sentences) {
				dependencyParser.parse(sent);
				int sentenceId = sentenceRepository.saveSentence(productId, review.getReviewId(), sent);
				List<FeatureOpinionPair> pairs = sent.getFeatureOpinionPairs();
				
				Set<String> sentenceFeatures = new HashSet<String>();
				for(FeatureOpinionPair fop : pairs) {
					String opinionWord = fop.getOpinion();
					if(opinionWord != null) {
						int sentiment = sentimentAnalyzer.getSentiment(opinionWord);
						String feature = fop.getFeature().toLowerCase();
						features.add(feature);
						sentenceFeatures.add(feature);
						sentimentRepository.saveSentiment(productId, review.getReviewId(),   
																	 sentenceId, 
																	 feature, 
																	 opinionWord, 
																	 sentiment);
					}
				}
				featuresInAllSentences.add(new Pair<List<String>, Long>(new ArrayList<String>(sentenceFeatures),1L));	
			}
		}
		List<String> allFeatures = new ArrayList<String>(features);
		System.out.println("all features count:" + allFeatures.size());
		List<String> frequentFeatures = frequentPatternMining.findFrequentFeatures(allFeatures, featuresInAllSentences, 3);
		System.out.println("frequent features count:" + frequentFeatures.size());
		List<String> featuresAfterPruningJunk = junkFeaturePruner.pruneJunkFeatures(frequentFeatures);
		System.out.println("frequent features after pruning junk:" + featuresAfterPruningJunk.size());
		List<String> featuresAfterPruningRedundantFeatures = redundantFeaturePruner.pruneRedundantFeatures(featuresAfterPruningJunk, productId);
		System.out.println("frequent features after pruning redundancy:" + featuresAfterPruningRedundantFeatures.size());
		featuresRepository.saveProductFeatures(productId, allFeatures, featuresAfterPruningRedundantFeatures);
	}
	
	public ProductSummary getSummaryForProduct(final int productId) {
		List<String> features = featuresRepository.findProductFeatures(productId, true);

		ProductSummary productSummary = new ProductSummary();
		
		for(String feature : features) {
			FeatureSummary fs = sentenceRepository.findFeatureSummary(productId, feature);
			productSummary.addFeatrueSummary(fs);
		}
		
		return productSummary;
	}
	
	
	public static void main(final String[] args) throws Exception {
		ProductSummarizer summarizer = new ProductSummarizer();
		ProductSummary summary = summarizer.getSummaryForProduct(1);
		FileOutputStream fos = new FileOutputStream("product_summary.txt");
		fos.write(summary.toString().getBytes());
		fos.close();

	}

}
