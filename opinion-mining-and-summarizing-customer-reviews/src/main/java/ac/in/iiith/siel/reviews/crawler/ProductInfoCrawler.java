package ac.in.iiith.siel.reviews.crawler;

import ac.in.iiith.siel.reviews.domain.ProductInfo;

public interface ProductInfoCrawler  {
	boolean hasNext();
	ProductInfo crawlNextProductInfo();
}
