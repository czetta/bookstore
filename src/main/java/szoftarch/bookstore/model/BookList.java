package szoftarch.bookstore.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class BookList {
	@Id
	private int id;
	
	private String name;
	private List<Integer> bookids;
	private boolean isPublic;
	private int userid;
	public BookList() {}
	public BookList(String name, List<Integer> bookids, boolean isPublic, int userid) {
		this.name = name;
		this.bookids = bookids;
		this.isPublic = isPublic;
		this.userid = userid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getBookids() {
		return bookids;
	}
	public void setBookids(List<Integer> bookids) {
		this.bookids = bookids;
	}
	public boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
}
