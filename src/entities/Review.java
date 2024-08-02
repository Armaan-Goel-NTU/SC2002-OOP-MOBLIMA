package entities;

import java.io.Serializable;

public class Review implements Serializable {
	private static final long serialVersionUID = 1L;
	private String content;
	private double rating;
	private String author;

	public Review(double rating, String content, String author) {
		this.content = content;
		this.rating = rating;
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public double getRating() {
		return rating;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
