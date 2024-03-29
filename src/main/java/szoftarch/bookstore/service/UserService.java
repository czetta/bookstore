package szoftarch.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import szoftarch.bookstore.model.User;
import szoftarch.bookstore.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repo;
	
	public User saveUser(User user) {
		return repo.save(user);
	}
	public User fetchUserByEmail(String email) {
		return repo.findByEmail(email);
	}
	public User fetchUserByEmailAndPassword(String email, String password) {
		return repo.findByEmailAndPassword(email, password);
	}
	public List<User> fetchAllUser() {
		return repo.findAll();
	}
	public User fetchUserById(int id) {
		return repo.findById(id);
	}
	public void deleteUser(User user) {
		repo.delete(user);
	}
	public Boolean fetchUserExistsByUsername(String username) {
		return repo.existsByUsername(username);
	}
	public Boolean fetchUserExistsByEmail(String email) {
		return repo.existsByEmail(email);
	}
}
