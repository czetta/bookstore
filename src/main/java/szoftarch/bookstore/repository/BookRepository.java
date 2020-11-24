package szoftarch.bookstore.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import szoftarch.bookstore.model.Book;

public interface BookRepository extends MongoRepository<Book, Integer>{
	public Book findByTitle(String title);
	public List<Book> findAll();
	public Book findById(int id);
	public List<Book> findByTitleContainingIgnoreCaseOrAuthorListContainingIgnoreCase(String filter1, String filter2);
}
