package szoftarch.bookstore.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import szoftarch.bookstore.model.Book;
import szoftarch.bookstore.service.BookService;

@RestController
public class BookController {
	@Autowired
	private BookService service;
	
	@PostMapping("/upload")
	public Book uploadBook(@RequestParam("file") MultipartFile file, @RequestBody Book book) throws IOException{
		Book bookStored=new Book(book.getTitle(), book.getAuthor(), compressBytes(file.getBytes()), book.getDescription());
		service.saveBook(bookStored);
		return bookStored;
	}
	
	@GetMapping(path={"/get/{title}"})
	public Book getBook(@PathVariable("title") String title) throws Exception{
		Book book=null;
		book=service.fetchBookByTitle(title);
		if(book==null) throw new Exception("Invalid title");
		return new Book(book.getTitle(), book.getAuthor(), decompressBytes(book.getPicByte()), book.getDescription());
	}
	
	@GetMapping(path= {"/getallbook"})
	public List<Book> getAllBook() throws Exception{
		return service.fetchAllBook();
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
}
