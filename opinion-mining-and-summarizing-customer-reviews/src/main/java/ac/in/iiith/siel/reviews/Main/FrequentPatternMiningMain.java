package ac.in.iiith.siel.reviews.Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.mapred.OutputCollector;
import org.apache.mahout.common.Pair;
import org.apache.mahout.fpm.pfpgrowth.convertors.StatusUpdater;
import org.apache.mahout.fpm.pfpgrowth.fpgrowth.FPGrowth;

public class FrequentPatternMiningMain {

	private static List<String> items = new ArrayList<String>();
	private static List<Pair<List<String>, Long>> transactions = new ArrayList<Pair<List<String>, Long>>();
	
	private static String mappingsFileName = "features.list";

	private static String cvsFileName = "transaction.list";
	
	private static List<Pair<String, Long>> frequencies = new ArrayList<Pair<String, Long>>();

	private static FPGrowth<String> fps = new FPGrowth<String>();
	
	public static void main(String[] args) throws Exception {
		readItemsName();
		readTransactions();
		findFrequencies();
		fps.generateTopKFrequentPatterns(transactions.iterator(), frequencies, 2, 1000, null, new FpOutputCollector(), new FpStatusUpdater());
	}
	
	private static void readItemsName() {
		  if (items != null) 
		    items.clear();
		  BufferedReader br = null;
		  String line = "";
		  String cvsSplitBy = ",";
		  items = new ArrayList<String>();
		  try {
		    br = new BufferedReader(new FileReader(mappingsFileName));
		    line = br.readLine();
		    line = br.readLine();
		    String[] itemss = line.split(",");
		    for(int i=0;i<itemss.length;i++)
		    {
//		      System.out.println("adding item " + itemss[i]);
		      items.add(itemss[i]);
		    }
		  } catch (FileNotFoundException e) {
		    e.printStackTrace();
		  } catch (IOException e) {
		    e.printStackTrace();
		  } finally {
		    if (br != null) {
		      try {
		          br.close();
		      } catch (IOException e) {
		          e.printStackTrace();
		      }
		  }
		  }
		  
		}
	
	private static void readTransactions() 
	{        
	        if (transactions != null)  transactions.clear();
	        System.out.println("adding transactions");

	        BufferedReader br = null;
	        String line = "";
	        
	        try {
	                br = new BufferedReader(new FileReader(cvsFileName));
	                line = br.readLine();          
	                while ((line = br.readLine()) != null) { 
	            // use comma as separator
	      String[] itemsintransaction = line.split(",");
	                        ArrayList<String> ar = new ArrayList<String>();
	                        for (int i = 0; i <  itemsintransaction.length; i++)
	                        {
	                            if (Integer.parseInt(itemsintransaction[i]) > 0 )
	                            {
	                               ar.add(items.get(i));
	                            }
	                        } 
	                        if (ar.size() > 0)
	                        {
//	                        	System.out.println("adding a transaction of " + ar.toString());
	                            transactions.add( new Pair<List<String>, Long>(ar,1L) );  
	                        }                                
	      }                
	        } catch (FileNotFoundException e) {
	        	System.out.println(e.getMessage());
	        } catch (IOException e) {
	        	System.out.println(e.getMessage());
	        } finally {
	                if (br != null) {
	                        try {
	                                br.close();
	                        } catch (IOException e) {
	            	        	System.out.println(e.getMessage());
	                        }
	                }
	        }                       
	}

	private static void findFrequencies() {
		  frequencies = fps.generateFList(transactions.iterator(), 2);
		   for (Pair<String, Long> frequency : frequencies)
		  {
			   System.out.println("frequency of item : " + frequency.toString() + " up to " + transactions.size()  );
		  }
		}
	
	private static class FpStatusUpdater implements StatusUpdater {

		public void update(String status) {
//			System.out.println("updating status:" + status);
		}
		
	}
	
	private static class FpOutputCollector implements OutputCollector<String, List<Pair<List<String>, Long>>> {

		public void collect(String x1, List<Pair<List<String>, Long>> listPair)
				throws IOException {
			StringBuffer sb = new StringBuffer();
		    sb.append(x1 + ":");
		    for (Pair<List<String>, Long> pair : listPair) {
		    sb.append("[");
		      String sep = "";
		      for (String item : pair.getFirst()) {
		      sb.append(item + sep);
		      sep = ", ";
		      }
		      sb.append("]:" + pair.getSecond());
		    }
		    System.out.println("createOutput:  " + sb.toString());
		    }			
		}
		
}
