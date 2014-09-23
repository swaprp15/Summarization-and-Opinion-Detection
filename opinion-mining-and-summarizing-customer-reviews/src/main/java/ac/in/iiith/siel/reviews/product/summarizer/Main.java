package ac.in.iiith.siel.reviews.product.summarizer;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import ac.in.iiith.siel.reviews.crawler.ProductInfoCrawler;
import ac.in.iiith.siel.reviews.crawler.SimpleProductInfoCrawler;
import ac.in.iiith.siel.reviews.domain.ProductInfo;
import ac.in.iiith.siel.reviews.repository.DBUtils;

public class Main {

	public static void main(String[] args) throws Exception {
		
		ProductInfoCrawler crawler = new SimpleProductInfoCrawler(
				"product_urls.txt");
		List<String> crawledProductInfo = new ArrayList<String>();

		while (crawler.hasNext()) {
			ProductInfo info = crawler.crawlNextProductInfo();
			crawledProductInfo.add(info.getId() + ":" + info.getName());
		}

		ProductSummarizer productSummarizer = new ProductSummarizer();

		for (String pInfo : crawledProductInfo) {
			int pid = Integer.parseInt(pInfo.split(":")[0]);
			String name = pInfo.split(":")[1];
			FileOutputStream fos = new FileOutputStream(name);
			productSummarizer.generateSummaryForProduct(pid);
			ProductSummary summary = productSummarizer.getSummaryForProduct(pid);
			System.out.println("Summarization for product :[" + name + "," + pid + "]");
			System.out.println(summary.toString());
			fos.write(summary.toString().getBytes());
			fos.close();
		}
		
		DBUtils.closeConnection();
	}

}
