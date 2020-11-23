package szoftarch.bookstore.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Book {
	@Id
	private int id;
	
	private String title;
	private String author;
	private byte[] picByte;
	private String description;
	private List<String> comments;
	private List<Integer> ratings;
	
	public Book() {}
	public Book(String title, String author, byte[] picByte, String description) {
		this.title=title;
		this.author=author;
		this.picByte=picByte;
		this.description=description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public byte[] getPicByte() {
		return picByte;
	}
	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public List<Integer> getRatings() {
		return ratings;
	}
	public void setRatings(List<Integer> ratings) {
		this.ratings = ratings;
	}
	public double getRating() {
		double sum=0;
		for(Integer i:ratings) {
			sum+=i;
		}
		return Math.round(sum*100.0/ratings.size())/100.0;
	}
}
