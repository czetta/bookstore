package szoftarch.bookstore.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import szoftarch.bookstore.model.BookList;

public interface BookListRepository extends MongoRepository<BookList, Integer> {
	public List<BookList> findAll();
	
}
