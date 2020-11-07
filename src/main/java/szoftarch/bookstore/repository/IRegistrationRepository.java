package szoftarch.bookstore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import szoftarch.bookstore.model.User;

public interface IRegistrationRepository extends MongoRepository<User, Integer>{
	
}
