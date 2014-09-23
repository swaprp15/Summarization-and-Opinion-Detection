//package ac.in.iiith.siel.reviews.Main;
//
//import java.io.IOException;
//import java.util.Scanner;
//
//public class SentimentAnalysisMain {
//
//	static SentimentAnalyzer sentimentAnalyzer;
//	
//	public static double RefineResult(String adverb, double orignalValue)
//	{
//		// For now only negation 
//		if(adverb.equalsIgnoreCase("not"))
//			return  -1 * orignalValue; 
//		else
//			return getAdverbScore(adverb, orignalValue);
//	}
//	
//	private static double getAdverbScore(String adverb, double originalValue)
//	{
//		double adverbScore = sentimentAnalyzer.getAdverbSentiment(adverb);
//		
//		double tmp;
//		
//		if(originalValue < 0)
//		{
//			tmp  = -1* (Math.abs(originalValue) + Math.abs(adverbScore));
//			
//			return tmp < -1 ? -1 : tmp;
//		}
//		else
//		{
//			tmp = originalValue + adverbScore;
//			
//			return tmp > 1 ? 1 : tmp;
//		}
//		
//	}
//	
//	public static void main(String[] args) throws IOException {
//
//		//System.setProperty("wordnet.database.dir", opinion.wordnetDBPath);
//
//
//		System.out.println("enter a word to find sentiment:");
//
//		Scanner inp = new Scanner(System.in);
//
////		opinion opinion = new opinion();
////		opinion.loadSeedList();
//		
//		sentimentAnalyzer = new SentimentAnalyzer();
//		
//		String  input, words[];
//		int totalWords = 0;	
//		
//		while (true) {
//			input = inp.nextLine();
//
//			// Split the input on space.
//			words = input.split(" ");
//			totalWords = words.length;
//			
//			double result = sentimentAnalyzer.getAdjectiveSentiment(words[totalWords-1]);
//
//			// Assuming that there will be at most two separate words.
//			if(totalWords > 1)
//				result = RefineResult(words[totalWords-2], result);
//
//			System.out.println(result);
//		}
//
//	}
//
//}
