package szoftarch.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import szoftarch.bookstore.model.User;
import szoftarch.bookstore.repository.RegistrationRepository;

@Service
public class RegistrationService {
	@Autowired
	private RegistrationRepository repo;
	
	public User saveUser(User user) {
		return repo.save(user);
	}
	public User fetchUserByEmail(String email) {
		return repo.findByEmail(email);
	}
	public User fetchUserByEmailAndPassword(String email, String password) {
		return repo.findByEmailAndPassword(email, password);
	}
}
