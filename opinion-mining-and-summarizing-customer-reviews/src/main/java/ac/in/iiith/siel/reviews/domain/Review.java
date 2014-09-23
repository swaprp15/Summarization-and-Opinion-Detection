package ac.in.iiith.siel.reviews.domain;

import java.util.ArrayList;
import java.util.List;

public class Review {

	private String commentedUserName;

	private String commentedDate;

	private String comment;

	private int rating;
	
	private int productId;
	
	private int reviewId;

	private List<Sentence> sentences = new ArrayList<Sentence>();

	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}


	public String getCommentedUserName() {
		return commentedUserName;
	}

	public void setCommentedUserName(final String commentedUserName) {
		this.commentedUserName = commentedUserName;
	}

	public String getCommentedDate() {
		return commentedDate;
	}

	public void setCommentedDate(final String commentedDate) {
		this.commentedDate = commentedDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public List<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(final List<Sentence> sentences) {
		this.sentences = sentences;
	}

	public void addSetence(final Sentence sentence) {
		sentences.add(sentence);
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	
}
