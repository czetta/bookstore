package szoftarch.bookstore.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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
import szoftarch.bookstore.model.Comment;
import szoftarch.bookstore.model.Rating;
import szoftarch.bookstore.model.User;
import szoftarch.bookstore.service.BookListService;
import szoftarch.bookstore.service.BookService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class BookController {
	@Autowired
	private BookService service;
	@Autowired
	private BookListService bls;
	
	@PostMapping("/book/upload")
	public ResponseEntity<Book> uploadBook(/*@RequestParam("file") MultipartFile file, */@RequestBody Book book) {
		book.setId(generateBookId());
		Book bookStored=service.saveBook(book);
		return new ResponseEntity<Book>(bookStored, HttpStatus.OK);
	}
	
	@GetMapping("/book/getallbook")
	public List<Book> getAllBook() {
		return service.fetchAllBook();
	}
	
	@GetMapping("/book/get/{bookid}")
	public ResponseEntity<Book> getBook(@PathVariable String bookid) {
		Book book=null;
		int id=Integer.parseInt(bookid);
		book=service.fetchBookById(id);
		if(book==null) return new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Book>(book, HttpStatus.OK);//(book.getTitle(), book.getAuthor(), decompressBytes(book.getPicByte()), book.getDescription());
	}
	
	@PutMapping("/book/update/{bookid}")
	public ResponseEntity<Book> updateBook(@PathVariable String bookid, @RequestBody Book book){
		Book tempbook = null;
		int id = Integer.parseInt(bookid);
		tempbook = service.fetchBookById(id);
		if(tempbook==null) return new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
		tempbook.setTitle(book.getTitle());
		tempbook.setAuthorList(book.getAuthorList());
		tempbook.setDescription(book.getDescription());
		if(id==0) service.deleteBook(service.fetchBookById(id));
		return new ResponseEntity<Book>(service.saveBook(tempbook), HttpStatus.OK);
	}
	
	@DeleteMapping("/book/del/{bookid}")
	public ResponseEntity<Book> delBook(@PathVariable String bookid){
		int id = Integer.parseInt(bookid);
		Book book = null;
		book = service.fetchBookById(id);
		if(book==null) return new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
		List<BookList> booklists = bls.fetchAllBookList();
		if(booklists!=null) {
			for(BookList bl : booklists) {
				bl.removeBook(id);
				bls.saveBookList(bl);
			}
		}
		service.deleteBook(book);
		return new ResponseEntity<Book>(HttpStatus.OK);
	}
	
	@GetMapping("/book/find/{filter}")
	public ResponseEntity<List<Book>> findBooks(@PathVariable String filter){
		List<Book> books = new ArrayList<Book>();
		books = service.fetchBookByFilter(filter);
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	}
	
	@PutMapping("/book/rate/{bookid}")
	public ResponseEntity<Book> rateBook(@PathVariable String bookid, @RequestBody Rating map){
		int id = Integer.parseInt(bookid);
		Book book = null;
		book = service.fetchBookById(id);
		if(book==null) return new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
		book.rateBook(map.getUserid(), map.getRating());
		return new ResponseEntity<Book>(service.saveBook(book), HttpStatus.OK);
	}
	
	@PutMapping("/book/comment/{bookid}") //TODO: id:0 crash
	public ResponseEntity<Book> commentBook(@PathVariable String bookid, @RequestBody Comment comment){
		int id = Integer.parseInt(bookid);
		Book book = null;
		book = service.fetchBookById(id);
		if(book==null) return new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
		book.commentBook(comment.getComment());
		return new ResponseEntity<Book>(service.saveBook(book), HttpStatus.OK);
	}
	
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater=new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream(data.length);
		byte[] buffer=new byte[1024];
		while(!deflater.finished()) {
			int count=deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch(IOException e) {}
		return outputStream.toByteArray();
	}
	
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater=new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream(data.length);
		byte[] buffer=new byte[1024];
		try {
			while(!inflater.finished()) {
				int count=inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch(IOException ioe) {
		} catch(DataFormatException e) {}
		return outputStream.toByteArray();
	}
	
	private synchronized int generateBookId() {
		List<Book> templist = getAllBook();
		int tempid = 0;
		for(Book b : templist) {
			if(b.getId()>=tempid) tempid=b.getId()+1;
		}
		return tempid;
	}
	
	private void init() {
		List<Book> books=new ArrayList<>();
		int maxid=0;
		for(Book book : getAllBook()) {
			Book tempbook=new Book();
			tempbook.setTitle(book.getTitle());
			tempbook.setAuthorList(book.getAuthorList());
			tempbook.setDescription(book.getDescription());
			books.add(tempbook);
			if(book.getId()>maxid) maxid=book.getId();
		}
		for(int i=0; i<=maxid; i++) {
			delBook(((Integer)i).toString());
		}
		for(Book b : books) {
			uploadBook(b);
		}
	}
}
