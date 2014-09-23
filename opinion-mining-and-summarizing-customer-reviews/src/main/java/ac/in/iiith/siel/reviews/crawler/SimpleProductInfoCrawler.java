package ac.in.iiith.siel.reviews.crawler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.ObjectInputStream.GetField;
import java.net.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ac.in.iiith.siel.reviews.domain.ProductInfo;
import ac.in.iiith.siel.reviews.domain.Review;
import ac.in.iiith.siel.reviews.repository.MySqlProductRepository;
import ac.in.iiith.siel.reviews.repository.ProductRepository;

class Constants
{
	public static String flipkartBaseUrl = "http://www.flipkart.com";
	
	// ToDo : Need to make it relative...
	//public static String productUrlsFile = "/home/swapnil/Swapnil/Dropbox/Sem2/IRE/MajorProject/flipkart_items/flipkart_devices.txt";
	public static String productUrlsFile = "/home/swapnil/Swapnil/Dropbox/Sem2/IRE/MajorProject/flipkart_items/flipkart_books.txt";
}

public class SimpleProductInfoCrawler implements ProductInfoCrawler {
	
	private List<String> productUrls = new ArrayList<String>();
	private int currentIndex = 0;
	int productId = 0;
    private ProductRepository dbObj = new MySqlProductRepository();

	public SimpleProductInfoCrawler(final String propertyFileName) {
		/**
		 * TODO: Shubham/Swapnil : Here you need to load the product_urls.txt file under src/main/resources and read all urls and add them to the productUrls list.
		 */
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(propertyFileName));
			
			String line;
			
			while((line = reader.readLine()) != null)
			{
				productUrls.add(line);
			}
			
			reader.close();
		}
		catch(Exception e)
		{
			throw new RuntimeException("exception while reading product urls:", e);
		}
	}
	
	public static void main(String [] args)
	{
		SimpleProductInfoCrawler obj = new SimpleProductInfoCrawler(Constants.productUrlsFile);
		
		while(obj.hasNext())
			obj.crawlNextProductInfo();
	}

	public boolean hasNext() {
		return (productUrls.size() > currentIndex);
	}

	public ProductInfo crawlNextProductInfo() {
		
		String url = productUrls.get(currentIndex);
		System.out.println("crawling product:" + url);
		ProductInfo product = crawlProductInfo(url);
		currentIndex++;
		return product;
	}
	
	
	private String GetPageContent(String inputUrl) throws IOException
	{
		URL url = new URL(inputUrl);
//		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.iiit.ac.in", 8080)); // or whatever your proxy is
//		HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);
		HttpURLConnection uc = (HttpURLConnection)url.openConnection();

		uc.connect();

	    String line = null;
	    StringBuffer tmp = new StringBuffer();
	    BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
	    
	    while ((line = in.readLine()) != null)
	    {
	      tmp.append(line);
	    }
	    
	    return tmp.toString();
	}
	
	@SuppressWarnings("deprecation")
	private ProductInfo crawlProductInfo(final String url) {
		
		/**
		 * TODO : Shubham/Swapnil Here you need to implement crawling the given url using jsoup library. You need to crawl following properties
		 * 		1) product name
		 * 		2) ISBN number
		 * 		3) product rating
		 * 		4) category
		 * 		5) reviews that user expressed. 
		 * 
		 *   Once you got all the information, populate the information in product object created above using setters. Look ProductInfo class to see what it contains.
		 *   	
		 */
		
		ProductInfo productInfo = new ProductInfo();
		productId++;
		productInfo.setId(productId);
		
		List<Review> reviews = new ArrayList<Review>();
    	int reviewId = 1;

		String url1 = null;
		
		try
		{
			url1 = GetPageContent(url);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return productInfo;
		}
		
	    boolean onReadAllReviewsPage = false;
	    
	    try {
	    while(url1 != null)
	    {
		    Document doc = Jsoup.parse(url1);
		    
		    String prouctName;	    
		    
		    // Product name
		    if(!onReadAllReviewsPage)
		    {
		    	Element h1 = doc.select("h1").first();
		    	
		    	// To handle page which contains different items and not the single
		    	if(h1 == null)
		    	{
		    		// find out each item and go to it.
		    		Element div = doc.select("div").first();
		    		Elements productsCols = div.select("div[class=gd-col gu3]");
		    		
		    		for(Element productCol : productsCols)
		    		{
		    			Element browseProductDiv = productCol.child(0);
		    			
		    			Element visualSectionDiv = browseProductDiv.select("div[class=pu-visual-section]").first();
		    			
		    			Element imageA = visualSectionDiv.select("a").first();
		    			
		    			url1 = imageA.attr("href");
						
				    	if(url1.charAt(0) == '/')
				    	{
				    		url1 = Constants.flipkartBaseUrl + url1;
				    		
				    		// Recursive call
				    		crawlProductInfo(url1);
				    	}
		    		}
		    	}
		    	else
		    	{
			    	prouctName = h1.text();
			    	
			    	// In case url supplied is directly of all reviews
			    	if(prouctName.contains("Reviews of"))
			    	{
			    		url1 = h1.select("a").attr("href");
						
				    	if(url1.charAt(0) == '/')
				    	{	    		
				    		url1 = Constants.flipkartBaseUrl + url1;
				    		
				    		// Recursive call
				    		crawlProductInfo(url1);
				    	}
			    		
			    		break;
			    	}
			    	
			    	productInfo.setName(prouctName);
	
				    Element div = doc.select("div").first();		    
				    Element spans = div.select("[class=line fk-lbreadbcrumb]").first();
				    
				    String category = null;
				    
				    for(Element subCategory : spans.children())
				    {
				    	if(!subCategory.tagName().equalsIgnoreCase("span"))
				    		break;
				    	
				    	String tmp = subCategory.text();
				    	category = tmp.substring(0, tmp.length()-1);
				    }
				    
				    productInfo.setCategory(category);
			    	onReadAllReviewsPage = true;
		    	}
		    }
		    
		    
		    Element l = doc.select("a:contains(Read all reviews)").first();
		    
		    if(l != null)
		    {
		    	url1 = l.attr("href");
		    	
		    	if(url1.charAt(0) == '/')
		    	{
		    		url1 = Constants.flipkartBaseUrl + url1;
		    		
		    		try
		    		{
		    			url1 = GetPageContent(url1);
		    		}
		    		catch(Exception e)
		    		{
		    			e.printStackTrace();
		    			break;
		    		}
		    	}
		    	
		    	//System.out.println("Read all reviews URL : " + url1);
		    	continue;
		    }
		    else // Two cases... either this page contains no reviews OR it is All reviews page
		    {
		    	Element allDivs = doc.select("[class=review-list]").first();
		    	
		    	if(allDivs == null)
		    	{
		    		url1 = null;
		    		continue;
		    	}
		    	
		    	
		    	for(Element review : allDivs.children())
		    	{
		    		if(!review.attr("class").equalsIgnoreCase("fclear fk-review fk-position-relative line "))
		    			continue;
		    		
					Elements ps = review.select("p");
			    	Element reviewText = ps.select("[class=line bmargin10]").first();
			    	
			    	Review reviewObj = new Review();
			    	
			    	//Title
			    	Element titleOuterDiv = review.select("[class=lastUnit size4of5 section2]").first();
			    	
			    	//Element titleDiv = titleOuterDiv.select("div [class=line fk-font-normal bmargin5 dark-gray]").first();
			    	Element titleDiv = titleOuterDiv.select("[class=line fk-font-normal bmargin5 dark-gray]").first();
			    	
			    	// First add title
			    	String reviewComment = titleDiv.select("strong").first().text();
			    	
			    	// Then add actual review text
			    	reviewComment += ". ";
			    	reviewComment += reviewText.text();
			    	reviewObj.setComment(reviewComment);
			    	
			    	
			    			    	
			    	// User name
			    	Element userDetails = review.select("[class=unit size1of5 section1]").first();
			    	
			    	for(Element line : userDetails.children())
			    	{
			    		if(line.attr("class").equalsIgnoreCase("line"))
			    		{
			    			Element child = line.child(0);
			    			
			    			if(child.tagName().equalsIgnoreCase("div")) // Stars
			    			{
			    				int rating = child.attr("title").charAt(0) - 48;
			    				
			    				reviewObj.setRating(rating);
			    			}
			    			else // Username
			    			{
			    				String userName = child.text();
			    				
			    				reviewObj.setCommentedUserName(userName);
			    				
			    			}
			    		}
			    		else if(line.attr("class").equalsIgnoreCase("date line fk-font-small"))
			    		{
			    			String date = line.text();
			    			reviewObj.setCommentedDate(date);
			    		}
			    	}
			    	System.out.println("crawled review:" + reviewId);
			    	reviewObj.setReviewId(reviewId++);
			    	reviews.add(reviewObj);
//			    	if(reviews.size() >= 10) {
//			    		throw new RuntimeException("prematurely exiting the crawling");
//			    	}
//			    	try {
//			    	Thread.sleep(1000);
//			    	} catch(final InterruptedException e) {
//			    		throw new RuntimeException("interrupted while sleeping to query next review", e);
//			    	}
		    	}
					
				Elements nextPages = doc.select("a:contains(Next Page )");

				if(nextPages == null)
					url1 = null;
				else
				{
					Element nextPage = nextPages.first();
					if(nextPage == null)
						url1 = null;
					else
					{
						url1 = nextPage.attr("href");
					
				    	if(url1.charAt(0) == '/')
				    	{
				    		
				    		url1 = Constants.flipkartBaseUrl + url1;
				    		
				    		try
				    		{
				    			url1 = GetPageContent(url1);
				    		}
				    		catch(Exception e)
				    		{
				    			e.printStackTrace();
				    		}
				    	}
					}
				}
		    }
	    }
	    } catch(final Exception e) {
	    	e.printStackTrace();
	    }
	    productInfo.setReviews(reviews);
		
	    // 
	    dbObj.saveProductInfo(productInfo);
	    
	    System.out.println("completed crawling product:" +url );
	    
		return productInfo;
	}

}
