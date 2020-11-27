package szoftarch.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import szoftarch.bookstore.model.BookList;
import szoftarch.bookstore.model.User;
import szoftarch.bookstore.service.UserService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserService service;
	
	@PostMapping("/register")//CHANGED
	public synchronized ResponseEntity<User> registerUser(@RequestBody User user) {
		String email=user.getEmail();
		if(email!=null && !email.equals("")) {
			User userobj=service.fetchUserByEmail(email);
			if(userobj!=null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		else return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		user.setId(generateUserId());
		User userObj=null;
		userObj=service.saveUser(user);
		return new ResponseEntity<User>(userObj, HttpStatus.OK);
	}
	
	@PostMapping("/login")//CHANGED
	public ResponseEntity<User> loginUser(@RequestBody User user) {
		String email=user.getEmail();
		String password=user.getPassword();
		User userObj=null;
		if(email==null || email=="" || password==null || password =="") return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		else{
			userObj=service.fetchUserByEmailAndPassword(email, password);
			if(userObj==null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
			userObj.setIsLoggedIn(true);
			return new ResponseEntity<User>(service.saveUser(userObj), HttpStatus.OK);
		}
	}
	
	@PutMapping("/user/logout/{userid}")
	public ResponseEntity<User> logoutUser(@PathVariable String userid) {
		int id = Integer.parseInt(userid);
		User tempuser = null;
		tempuser = service.fetchUserById(id);
		if(tempuser==null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		tempuser.setIsLoggedIn(false);
		return new ResponseEntity<User>(service.saveUser(tempuser), HttpStatus.OK);
	}
	
	@GetMapping("/user/get/{userid}")
	public ResponseEntity<User> getUser(@PathVariable String userid) {
		int id = Integer.parseInt(userid);
		User user = null;
		user = service.fetchUserById(id);
		if(user==null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PutMapping("/user/update/{userid}")
	public ResponseEntity<User> updateUser(@PathVariable String userid, @RequestBody User user){
		int id = Integer.parseInt(userid);
		User tempuser = null;
		tempuser = service.fetchUserById(id);
		if(tempuser==null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		tempuser.setEmail(user.getEmail());
		tempuser.setPassword(user.getPassword());
		return new ResponseEntity<User>(service.saveUser(tempuser), HttpStatus.OK);
	}
	
	@DeleteMapping("/user/del/{userid}")
	public ResponseEntity<User> delUser(@PathVariable String userid){
		int id = Integer.parseInt(userid);
		User user = null;
		user = service.fetchUserById(id);
		if(user==null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		service.deleteUser(user);
		return new ResponseEntity<User>(HttpStatus.OK);
	}
	
	@GetMapping("/user/getalluser")
	public List<User> getAllUser() {
		return service.fetchAllUser();
	}
	
	private synchronized int generateUserId() {
		List<User> templist = getAllUser();
		int tempid = 0;
		for(User u : templist) {
			if(u.getId()>=tempid) tempid=u.getId()+1;
		}
		return tempid;
	}
}
