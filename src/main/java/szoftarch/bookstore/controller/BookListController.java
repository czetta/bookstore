package szoftarch.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import szoftarch.bookstore.model.BookList;
import szoftarch.bookstore.service.BookListService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class BookListController {
	@Autowired
	private BookListService service;
	
	@PostMapping("/booklist/create")
	public ResponseEntity<BookList> createBookList(@RequestBody BookList bookList){
		bookList.setId(generateBookListId());
		BookList bookListStored=service.saveBookList(bookList);
		return new ResponseEntity<BookList>(bookListStored, HttpStatus.OK);
	}
	
	@GetMapping("/booklist/getallbooklist")
	public List<BookList> getAllBookList(){
		return service.fetchAllBookList();
	}
	
	private synchronized int generateBookListId() {
		List<BookList> templist = getAllBookList();
		int tempid = 0;
		for(BookList bl : templist) {
			if(bl.getId()>=tempid) tempid=bl.getId()+1;
		}
		return tempid;
	}
}
