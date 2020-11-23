package szoftarch.bookstore.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class BookList {
	@Id
	private String id;
	
	private String name;
	private List<Book> books;
	private boolean isPublic;
}
