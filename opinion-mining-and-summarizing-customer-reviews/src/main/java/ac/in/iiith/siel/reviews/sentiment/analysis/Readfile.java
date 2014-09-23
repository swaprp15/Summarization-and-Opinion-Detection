package ac.in.iiith.siel.reviews.sentiment.analysis;
import java.io.*;
import java.util.*;

public class Readfile 
{
	private Scanner x;
	private Scanner y;
	
	public Map<String, Integer> openFile()
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		try
		{
			x= new Scanner(new File(SentimentAnalyzer.positiveAdjectivesFilePath));
			y= new Scanner(new File(SentimentAnalyzer.negativeAdjectivesFilePath));
			
			map=readFile(x,y);	
			
			x.close();
			y.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println(map);
		return map;
		
	}
	
	public Map<String,Integer> readFile(Scanner x,Scanner y)
	{
		//String list[];
		Map<String, Integer> map = new HashMap<String, Integer>();
		while(x.hasNextLine())
		{
			map.put(x.nextLine().toLowerCase(), 1);			
			//System.out.println(x.nextLine());
		}
		
		while(y.hasNextLine())
		{
			map.put(y.nextLine().toLowerCase(), -1);						
			//System.out.println(x.nextLine());
		}
		return map;
	}
}