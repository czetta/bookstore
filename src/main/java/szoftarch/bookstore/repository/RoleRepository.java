package szoftarch.bookstore.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import szoftarch.bookstore.model.ERole;
import szoftarch.bookstore.model.Role;

public interface RoleRepository extends MongoRepository<Role, Integer> {
	public Optional<Role> findByName(ERole name);
}
