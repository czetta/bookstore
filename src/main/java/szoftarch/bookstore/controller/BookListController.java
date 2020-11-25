package szoftarch.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import szoftarch.bookstore.model.Book;
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
	
	@GetMapping("/booklist/getbooklists")
	public List<BookList> getBookLists(){
		return service.fetchPublicBookLists();
	}
	
	@GetMapping("/booklist/user/{userid}")
	public List<BookList> getUserBookLists(@PathVariable String userid){
		int id=Integer.parseInt(userid);
		return service.fetchUserBookLists(id);
	}
	
	@GetMapping("/booklist/get/{booklistid}")
	public ResponseEntity<BookList> getBookList(@PathVariable String booklistid) {
		BookList booklist=null;
		int id=Integer.parseInt(booklistid);
		booklist=service.fetchBookListById(id);
		if(booklist==null) return new ResponseEntity<BookList>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<BookList>(booklist, HttpStatus.OK);
	}
	
	@PutMapping("/booklist/update/{booklistid}")
	public ResponseEntity<BookList> updateBookList(@PathVariable String booklistid, @RequestBody BookList booklist){
		BookList tempbooklist = null;
		int id = Integer.parseInt(booklistid);
		tempbooklist = service.fetchBookListById(id);
		if(tempbooklist==null) return new ResponseEntity<BookList>(HttpStatus.BAD_REQUEST);
		tempbooklist.setIsPublic(booklist.getIsPublic());
		//if(id==0) service.deleteBookList(service.fetchBookListById(id));
		return new ResponseEntity<BookList>(service.saveBookList(tempbooklist), HttpStatus.OK);
	}
	
	@PutMapping("/booklist/add/{booklistid}")
	public ResponseEntity<BookList> addBook(@PathVariable String booklistid, @RequestBody Book book){
		int id=Integer.parseInt(booklistid);
		BookList booklist=null;
		booklist=service.fetchBookListById(id);
		if(booklist==null) return new ResponseEntity<BookList>(HttpStatus.BAD_REQUEST);
		booklist.addBook(book.getId());
		return new ResponseEntity<BookList>(service.saveBookList(booklist), HttpStatus.OK);
	}
	
	@PutMapping("/booklist/remove/{booklistid}")
	public ResponseEntity<BookList> removeBook(@PathVariable String booklistid, @RequestBody Book book){
		int id=Integer.parseInt(booklistid);
		BookList booklist=null;
		booklist=service.fetchBookListById(id);
		if(booklist==null || booklist.getBookids().size()==0) return new ResponseEntity<BookList>(HttpStatus.BAD_REQUEST);
		booklist.removeBook(book.getId());
		return new ResponseEntity<BookList>(service.saveBookList(booklist), HttpStatus.OK);
	}
	
	@DeleteMapping("/booklist/del/{booklistid}")
	public ResponseEntity<BookList> delBookList(@PathVariable String booklistid){
		int id = Integer.parseInt(booklistid);
		BookList booklist = null;
		booklist = service.fetchBookListById(id);
		if(booklist==null) return new ResponseEntity<BookList>(HttpStatus.BAD_REQUEST);
		service.deleteBookList(booklist);
		return new ResponseEntity<BookList>(HttpStatus.OK);
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
