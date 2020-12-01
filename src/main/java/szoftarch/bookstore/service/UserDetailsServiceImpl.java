package szoftarch.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import szoftarch.bookstore.model.User;
import szoftarch.bookstore.model.UserDetailsImpl;
import szoftarch.bookstore.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(final String username) {
		final User user = userRepository.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImpl(user);
	}
}
