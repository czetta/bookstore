package szoftarch.bookstore.model;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
	User user;
	
	public UserDetailsImpl(User user) {
		this.user=user;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return this.user.getRoles().stream()
				.map(role->new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
	}
	@Override
	public String getUsername() {
		return this.user.getUsername();
	}
	@Override
	public String getPassword() {
		return this.user.getPassword();
	}
	public User getUser() {
		return this.user;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(o==null || getClass() != o.getClass()) return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(this.user.getId(), user.user.getId());
	}
}
