package szoftarch.bookstore.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class BookList {
	@Id
	private String id;
	
	private String name;
	private List<Book> books;
	private boolean isPublic;
	public BookList() {}
	public BookList(String name, List<Book> books, boolean isPublic) {
		this.name = name;
		this.books = books;
		this.isPublic = isPublic;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
}
