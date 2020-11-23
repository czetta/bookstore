package szoftarch.bookstore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

public class Book {
	@Id
	private int id;
	
	private String title;
	private List<String> authors;
	//private byte[] picByte;
	private String description;
	private List<String> comments;
	private Map<Integer, Integer> ratings; //<userid, rating>
	private double rating;
	
	public Book() {}
	public Book(String title, List<String> authors, /*byte[] picByte, */String description) {
		this.title=title;
		this.authors=authors;
		//this.picByte=picByte;
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
	public List<String> getAuthors() {
		return authors;
	}
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	/*public byte[] getPicByte() {
		return picByte;
	}
	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}*/
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
	public Map<Integer, Integer> getRatings() {
		return ratings;
	}
	public void setRatings(Map<Integer, Integer> ratings) {
		this.ratings = ratings;
	}
	public double getRating() {
		double sum=0;
		if (ratings!=null) {
			for(Integer i : ratings.values()) {
				sum+=i;
			}
		}
		if(ratings != null && ratings.size() > 0) return Math.round(sum*100.0/ratings.size())/100.0;
		else return 0.00;
	}
	public void setRating(double rating) {
		this.rating=rating;
	}
}
