package szoftarch.bookstore.model;

import org.springframework.data.annotation.Id;

public class Role {
	@Id
	private int id;
	
	private ERole name;
	
	public Role() {}
	public Role(ERole name) {
		this.name=name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ERole getName() {
		return name;
	}
	public void setName(ERole name) {
		this.name = name;
	}
	
}
