package szoftarch.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import szoftarch.bookstore.model.User;
import szoftarch.bookstore.service.RegistrationService;

@RestController
public class RegistrationController {
	@Autowired
	private RegistrationService service;
	
	//TODO: format checking (xx@xx.xx)
	@PostMapping("/register")
	public User registerUser(@RequestBody User user) throws Exception {
		String email=user.getEmail();
		if(email!=null && !email.equals("")) {
			User userobj=service.fetchUserByEmail(email);
			if(userobj!=null) throw new Exception(email+" is already in use");
		}
		User userObj=null;
		userObj=service.saveUser(user);
		return userObj;
	}
	
	@PostMapping("/login")
	public User loginUser(@RequestBody User user) throws Exception{
		String email=user.getEmail();
		String password=user.getPassword();
		User userObj=null;
		if(email==null || email=="" || password==null || password =="") throw new Exception("Format incorrect!");
		else{
			userObj=service.fetchUserByEmailAndPassword(email, password);
			if(userObj==null) throw new Exception("There is no such user!");
			return userObj;
		}
	}
}
