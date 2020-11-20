package szoftarch.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import szoftarch.bookstore.model.Book;
import szoftarch.bookstore.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository repo;
	
	public Book saveBook(Book book) {
		return repo.save(book);
	}
	public Book fetchBookByTitle(String title) {
		return repo.findByTitle(title);
	}
	public List<Book> fetchAllBook(){
		return repo.findAll();
	}
}
