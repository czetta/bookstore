package szoftarch.bookstore.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class User {
	@Id
	private int id;
	
	private String email;
	private String password;
	private boolean isAdmin;
	private boolean isLoggedIn;
	private List<BookList> lists;
	
	public User() {}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.isAdmin = false;
		this.isLoggedIn = false;
	}

	public int getId() {
		return id;
	}

	public synchronized void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public List<BookList> getLists() {
		return lists;
	}

	public void setLists(List<BookList> lists) {
		this.lists = lists;
	}
	public void addBookList(BookList bookList) {
		if(lists==null) lists = new ArrayList<BookList>();
		lists.add(bookList);
	}
}
