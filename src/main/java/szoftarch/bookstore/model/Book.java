package szoftarch.bookstore.model;

import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

public class Book {
	@Id
	private String id;
	
	private String title;
	private String author;
	private byte[] picByte;
	private String description;
	
	public Book() {}
	public Book(String title, String author, byte[] picByte, String description) {
		this.title=title;
		this.author=author;
		this.picByte=picByte;
		this.description=description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
}
