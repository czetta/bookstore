package szoftarch.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import szoftarch.bookstore.model.BookList;
import szoftarch.bookstore.repository.BookListRepository;

@Service
public class BookListService {
	@Autowired
	private BookListRepository repo;

	public List<BookList> fetchAllBookList() {
		return repo.findAll();
	}

	public BookList saveBookList(BookList bookList) {
		return repo.save(bookList);
	}

	public List<BookList> fetchPublicBookLists() {
		return repo.findByIsPublic(true);
	}

	public List<BookList> fetchUserBookLists(int id) {
		return repo.findByUserid(id);
	}

	public BookList fetchBookListById(int id) {
		return repo.findById(id);
	}

	public void deleteBookList(BookList booklist) {
		repo.delete(booklist);
	}
	
	
}
