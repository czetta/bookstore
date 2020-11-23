package szoftarch.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import szoftarch.bookstore.model.User;
import szoftarch.bookstore.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService service;
	
	//TODO: format checking (xx@xx.xx)
	@PostMapping("/register")
	@CrossOrigin(origins="https://localhost:8090")
	public synchronized ResponseEntity<User> registerUser(@RequestBody User user) {
		String email=user.getEmail();
		if(email!=null && !email.equals("")) {
			User userobj=service.fetchUserByEmail(email);
			if(userobj!=null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		else return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		user.setId(generateId());
		User userObj=null;
		userObj=service.saveUser(user);
		return new ResponseEntity<User>(userObj, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	@CrossOrigin(origins="https://localhost:8090")
	public ResponseEntity<User> loginUser(@RequestBody User user) {
		String email=user.getEmail();
		String password=user.getPassword();
		User userObj=null;
		if(email==null || email=="" || password==null || password =="") return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		else{
			userObj=service.fetchUserByEmailAndPassword(email, password);
			if(userObj==null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
			userObj.setLoggedIn(true);
			return new ResponseEntity<User>(userObj, HttpStatus.OK);
		}
	}
	
	@GetMapping("/user/get/{userid}")
	@CrossOrigin(origins="https://localhost:8090")
	public ResponseEntity<User> getUser(@PathVariable String userid) {
		int id = Integer.parseInt(userid);
		User user = null;
		user = service.fetchUserById(id);
		if(user==null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("/usergetall")
	@CrossOrigin(origins="https://localhost:8090")
	public List<User> getAllUser() {
		return service.fetchAllUser();
	}
	
	private synchronized int generateId() {
		List<User> templist = getAllUser();
		int tempid = 0;
		for(User u : templist) {
			if(u.getId()>=tempid) tempid=u.getId()+1;
		}
		return tempid;
	}
}
