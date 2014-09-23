package ac.in.iiith.siel.reviews.sentiment.analysis;
import java.io.*;
import java.util.*;

public class SeedList 
{
	public Map<String,Integer> GetSeedList(){
		//System.out.println("Hello I am SeedList");
		Readfile readfile = new Readfile();
		//Scanner inp = new Scanner(System.in);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map = readfile.openFile();
		//System.out.println(map);
		
		return map;
	}

}