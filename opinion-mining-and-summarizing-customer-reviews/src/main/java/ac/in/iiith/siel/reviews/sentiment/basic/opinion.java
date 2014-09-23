package ac.in.iiith.siel.reviews.sentiment.basic;

import java.util.HashMap;
import java.util.Map;

import ac.in.iiith.siel.reviews.sentiment.analysis.SeedList;
import ac.in.iiith.siel.reviews.sentiment.analysis.writeFile;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.smu.tspell.wordnet.WordSense;


public class opinion
{
	
	private Map<String, Integer> seedmap;
	private SeedList seedlist;
	public static String wordnetDBPath = "../../ire/opinion-mining-and-summarizing-customer-reviews/WordNet-3.0/dict";
	public static String positiveAdjectivesFilePath = "../../ire/opinion-mining-and-summarizing-customer-reviews/SentimentSeeds/PositiveAdjectives";
	public static String negativeAdjectivesFilePath = "../../ire/opinion-mining-and-summarizing-customer-reviews/SentimentSeeds/NegativeAdjectives";

	// If returns 0, couldn't find the sentiment
	public int getSentiment(String opinion)
	{
		int result = 0;
		
		int flag = 0;
		
		
		for(String key : seedmap.keySet()) 
		{
			//key=key.toLowerCase();
			opinion = opinion.toLowerCase();
		    
			if(key.equals(opinion))
		    {
		        //result.put(key, yourMap.get(key);
		    	//System.out.println(key);
		    	
		    	flag=1;
		    	result  = seedmap.get(key);
		    	
		    	// found on the seedlist
		    	return result;
		    }
		}
		
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		Synset[] synsets = database.getSynsets(opinion);
		
		// Search synsets
		for (int i = 0; i < synsets.length; i++)
		{
			String[] wordForms = synsets[i].getWordForms();
			
			for (int j = 0; j < wordForms.length; j++)
			{
				
				if(seedmap.keySet().contains(wordForms[j].toLowerCase()))
				{
					seedmap.put(opinion, seedmap.get(wordForms[j].toLowerCase()));
			    	
			    	writeFile writefile = new writeFile();
			    	
			    	if(seedmap.get(opinion) == 1)
			    	{
			    		writefile.writetoSeedlist(opinion, 1);
			    		result = 1;
			    	}
			    	else
					{
			    		writefile.writetoSeedlist(opinion, -1);
			    		result = -1;
			    	}
			    	
			    	return result;
				}
				else
				{
					WordSense[] antonyms  = synsets[i].getAntonyms(wordForms[j]);
					
					for(WordSense sense : antonyms)
					{
						
						if(seedmap.keySet().contains(sense.getWordForm().toLowerCase()))
						{
							// alter opinion
							seedmap.put(opinion, -1*seedmap.get(sense.getWordForm().toLowerCase()));
					    	
					    	writeFile writefile = new writeFile();
					    	
					    	if(seedmap.get(opinion) == 1)
					    	{
					    		writefile.writetoSeedlist(opinion, 1);
					    		result = 1;
					    	}
					    	else
							{
					    		writefile.writetoSeedlist(opinion, -1);
					    		result = -1;
					    	}
					    	
					    	return result;
						}
					}
				}
			}
		}
		
		return result;
	}
	
	public void loadSeedList() {
		seedmap = new HashMap<String, Integer>();
		seedlist = new SeedList();
		seedmap = seedlist.GetSeedList();
		
	}
	
	
	
//	public static void main(String args[])
//	{
//		seedmap = new HashMap<String, Integer>();
//		
//		seedlist = new SeedList();
//		seedmap = seedlist.GetSeedList();
//		
//		
//		System.setProperty("wordnet.database.dir", wordnetDBPath);
//		
//		Scanner inp = new Scanner(System.in);
//		//WordNetDatabase database = WordNetDatabase.getFileInstance(); 
//		
//		
//		String feature, opinion;
//		
//		while(true)
//		{
////			System.out.println("Enter feature");
////			feature = inp.nextLine();	
//			
//			//System.out.println("Enter adjective");
//			opinion = inp.nextLine();
//			
//			int result = GetSentiment(opinion);
//			
//			System.out.println((result == 1 ? "positive" : (result == -1 ? "negative" : "Could not determine")));
//		}

//	}
}