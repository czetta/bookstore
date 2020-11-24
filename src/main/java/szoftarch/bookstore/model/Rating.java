package szoftarch.bookstore.model;

public class Rating {
	private int userid;
	private int rating;
	
	public Rating() {}
	public Rating(int userid, int rating) {
		this.userid=userid;
		this.rating=rating;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
}
