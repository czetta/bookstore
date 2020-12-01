package szoftarch.bookstore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import szoftarch.bookstore.model.User;

public interface UserRepository extends MongoRepository<User, Integer>{
	public User findByEmail(String email);

	public User findByEmailAndPassword(String email, String password);

	public User findById(int id);
	
	public User findByUsername(String username);
	
	public Boolean existsByUsername(String username);
	
	public Boolean existsByEmail(String email);
}
