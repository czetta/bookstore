package szoftarch.bookstore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import szoftarch.bookstore.model.ERole;
import szoftarch.bookstore.model.Role;
import szoftarch.bookstore.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	RoleRepository repo;
	
	public Optional<Role> fetchRoleByName(ERole name) {
		return repo.findByName(name);
	}
}
