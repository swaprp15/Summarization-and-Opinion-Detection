//package ac.in.iiith.siel.reviews.sentiment.basic;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
////class Constants
////{
////	public static String SentiWordNetFile = "/home/swapnil/Swapnil/Dropbox/Sem2/IRE/Trials/SentiWordNet/SentiWordNet_3.0.0.txt";
////}
//
//public class SentimentAnalyzer 
//{
//
//  private Map<String, Double> dictionary;
//  public static String SentiWordNetFile = "/home/swapnil/Swapnil/Dropbox/Sem2/IRE/Trials/SentiWordNet/SentiWordNet_3.0.0.txt";
//
//  public SentimentAnalyzer() throws IOException 
//  {
//    // This is our main dictionary representation
//    dictionary = new HashMap<String, Double>();
//
//    // From String to list of doubles.
//    HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();
//
//    BufferedReader reader = null;
//    
//    try 
//    {
//      reader = new BufferedReader(new FileReader(SentiWordNetFile));
//      int lineNumber = 0;
//
//      String line;
//      
//      while ((line = reader.readLine()) != null) 
//      {
//    	  lineNumber++;
//
//		// If it's a comment, skip this line.
//		if (!line.trim().startsWith("#")) 
//		{
//			  // We use tab separation
//			  String[] data = line.split("\t");
//			  String wordTypeMarker = data[0];
//		
//			  // Example line:
//			  // POS ID PosS NegS SynsetTerm#sensenumber Desc
//			  // a 00009618 0.5 0.25 spartan#4 austere#3 ascetical#2
//			  // ascetic#2 practicing great self-denial;...etc
//		
//			  // Is it a valid line? Otherwise, through exception.
//			  if (data.length != 6) 
//			  {
//			    throw new IllegalArgumentException(
//							       "Incorrect tabulation format in file, line: "
//							       + lineNumber);
//			  }
//	
//			  // Calculate synset score as score = PosS - NegS
//			  Double synsetScore = Double.parseDouble(data[2]) - Double.parseDouble(data[3]);
//		
//			  // Get all Synset terms
//			  String[] synTermsSplit = data[4].split(" ");
//		
//			  // Go through all terms of current synset.
//			  for (String synTermSplit : synTermsSplit) 
//			  {
//				    // Get synterm and synterm rank
//				    String[] synTermAndRank = synTermSplit.split("#");
//				    String synTerm = synTermAndRank[0] + "#"
//				      + wordTypeMarker;
//			
//				    // Number after #
//				    int synTermRank = Integer.parseInt(synTermAndRank[1]);
//				    // What we get here is a map of the type:
//				    // term -> {score of synset#1, score of synset#2...}
//			
//				    // Add map to term if it doesn't have one
//				    if (!tempDictionary.containsKey(synTerm)) {
//				      tempDictionary.put(synTerm,
//							 new HashMap<Integer, Double>());
//				    }
//			
//				    // Add synset link to synterm
//				    tempDictionary.get(synTerm).put(synTermRank, synsetScore);
//			  }
//		}
//      }
//
//      // Go through all the terms.
//      for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary.entrySet()) 
//      {
//		String word = entry.getKey();
//		Map<Integer, Double> synSetScoreMap = entry.getValue();
//	
//		// Calculate weighted average. Weigh the synsets according to
//		// their rank.
//		// Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
//		// Sum = 1/1 + 1/2 + 1/3 ...
//		double score = 0.0;
//		double sum = 0.0;
//		
//		for (Map.Entry<Integer, Double> setScore : synSetScoreMap.entrySet()) 
//		{
//		  score += setScore.getValue() / (double) setScore.getKey();
//		  sum += 1.0 / (double) setScore.getKey();
//		}
//		
//		score /= sum;
//	
//		dictionary.put(word, score);
//      }
//    }
//    catch (Exception e) 
//    {
//    		e.printStackTrace();
//    }
//    finally 
//    {
//    	if (reader != null) 
//    	{
//    		reader.close();
//    	}
//    }
//  }
//
//  public double extract(String word, String pos) 
//  {
//	  if(dictionary.containsKey(word + "#" + pos))
//		  return dictionary.get(word + "#" + pos);
//	  else
//		  return -2.0;
//  }
//  
//  public double getAdjectiveSentiment(String word)
//  {
//	  	double score = extract(word, "a");
//  	
//  		if(score == -2.0)
//  			score = SynsetSimilarity.getScore(word, this);
//  	
//  		return score;
//  }
//  
//  public double getAdverbSentiment(String word)
//  {
//	  	double score = extract(word, "r");
//  	
//  		if(score == -2.0)
//  			score = SynsetSimilarity.getScore(word, this);
//  	
//  		return score;
//  }
//  
////  public static void main(String [] args) throws IOException 
////  {
////	  SentimentAnalyzer sentiwordnet = new SentimentAnalyzer(Constants.SentiWordNetFile);
//////    
//////	    System.out.println("good#a " + sentiwordnet.extract("good", "a"));
//////	    System.out.println("bad#a " + sentiwordnet.extract("bad", "a"));
//////	    System.out.println("blue#a " + sentiwordnet.extract("blue", "a"));
//////	    System.out.println("blue#n " + sentiwordnet.extract("blue", "n"));
////	    
////	    Scanner scanner = new Scanner(System.in);
////	    
////	    while(true)
////	    {
////	    	String line = scanner.next();
////	    	
////	    	// Do we need to do steiing for words like awesomest, smartest
////	    	
////	    	double score = sentiwordnet.extract(line, "a");
////	    	
////	    	if(score == -2.0)
////	    		score = SynsetSimilarity.getScore(line, sentiwordnet);
////	    	
////	    	System.out.println(score);
////	    }
////  }
//}