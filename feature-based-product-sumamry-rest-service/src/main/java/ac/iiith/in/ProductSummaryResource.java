package ac.iiith.in;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import ac.iiith.in.repository.CategoryRepository;
import ac.iiith.in.repository.MysqlCategoryRepository;
import ac.iiith.in.repository.ProductInfo;

@Path("/")
public class ProductSummaryResource {

	private ProductSummarizer productSummarizer = new ProductSummarizer();
	private CategoryRepository cateGoryRepository = new MysqlCategoryRepository();
	private ProductRepository productRepository = new MySqlProductRepository();
	
	private ObjectMapper mapper = new ObjectMapper();

	@GET
	@Produces("application/json")
	@Path("/productsummary/{productId}")
	public Response getProductSummary(@PathParam("productId") String productId) {
		int pid = Integer.valueOf(productId);
		ProductSummary summary = productSummarizer.getSummaryForProduct(pid);
		return Response.ok(marshalProductSummary(summary)).header("Allow-Control-Allow-Methods", "POST,GET,OPTIONS")
				.header("Access-Control-Allow-Origin", "*")
				.build();
	}
		
	@GET
	@Produces("application/json")
	@Path("/review/{productId}/{reviewId}")
	public Response getReviewText(@PathParam("productId") int productId, @PathParam("reviewId") int reviewId)  {
		String reviewText = productRepository.findReviewTextForProduct(productId, reviewId);
		return Response.ok(reviewText).header("Allow-Control-Allow-Methods", "POST,GET,OPTIONS")
				.header("Access-Control-Allow-Origin", "*")
				.build();
	}
	
	
	@GET
	@Produces("application/json")
	@Path("/categories")
	public Response findAllCategories() {
		List<String> allCategories = cateGoryRepository.findAllCategories();
		return Response.ok(marshalCategories(allCategories)).header("Allow-Control-Allow-Methods", "POST,GET,OPTIONS")
															.header("Access-Control-Allow-Origin", "*")
															.build();
	}
	
	
	@GET
	@Produces("application/json")
	@Path("/products/{category}")
	public Response findAllProductsInfo(@PathParam("category") String category) {
		List<ProductInfo> allProducts = cateGoryRepository.findProductsForCategory(category);
		return Response.ok(marshalProductInfo(allProducts)).header("Allow-Control-Allow-Methods", "POST,GET,OPTIONS")
				.header("Access-Control-Allow-Origin", "*")
				.build();
	}
	

	private String marshalProductInfo(final List<ProductInfo> allProducts) {
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, allProducts);
		} catch (final IOException e) {
			throw new RuntimeException("failed to marshal product info:", e);
		}

		return sw.toString();
	}
	
	
	private String marshalCategories(final List<String> categories) {
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, categories);
		} catch (final IOException e) {
			throw new RuntimeException("failed to marshal  categories:", e);
		}

		return sw.toString();
	}

	
	private String marshalProductSummary(final ProductSummary summary) {
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, summary);
		} catch (final IOException e) {
			throw new RuntimeException("failed to marshal product summary:", e);
		}

		return sw.toString();
	}
}
