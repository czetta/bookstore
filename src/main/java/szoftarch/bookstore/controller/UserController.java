package szoftarch.bookstore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import szoftarch.bookstore.model.*;
import szoftarch.bookstore.security.JwtUtils;
import szoftarch.bookstore.service.RoleService;
import szoftarch.bookstore.service.UserService;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
public class UserController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/auth/register")
	public synchronized ResponseEntity<?> registerUser(@RequestBody SignupRequest req) {
		if(userService.fetchUserExistsByUsername(req.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Username is already taken"));
		}
		if(userService.fetchUserExistsByEmail(req.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Email is already in use"));
		}
		User user = new User(req.getUsername(), req.getEmail(), encoder.encode(req.getPassword()));
		Set<Role> roles = new HashSet<Role>();
        roles.add(roleService.fetchRoleByName(ERole.ROLE_USER).orElseThrow(()->new RuntimeException("Roles not found")));
		user.setRoles(roles);
		user.setId(generateUserId());
		userService.saveUser(user);
		return ResponseEntity.ok(new MessageResponse("Registration was successfull"));
		
		/*String email=user.getEmail();
		if(email!=null && !email.equals("")) {
			User userobj=userService.fetchUserByEmail(email);
			if(userobj!=null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		else return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		user.setId(generateUserId());
		User userObj=null;
		userObj=userService.saveUser(user);
		return new ResponseEntity<User>(userObj, HttpStatus.OK);*/
	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
		Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUser().getId(), userDetails.getUsername(), userDetails.getUser().getEmail(), roles));
		
		/*String email=user.getEmail();
		String password=user.getPassword();
		User userObj=null;
		if(email==null || email=="" || password==null || password =="") return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		else{
			userObj=userService.fetchUserByEmailAndPassword(email, password);
			if(userObj==null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
			userObj.setIsLoggedIn(true);
			return new ResponseEntity<User>(userService.saveUser(userObj), HttpStatus.OK);
		}*/
	}
	
	@PutMapping("/user/logout/{userid}")
	public ResponseEntity<User> logoutUser(@PathVariable String userid) {
		int id = Integer.parseInt(userid);
		User tempuser = null;
		tempuser = userService.fetchUserById(id);
		if(tempuser==null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<User>(HttpStatus.OK);
	}
	
	@GetMapping("/user/get/{userid}")
	public ResponseEntity<User> getUser(@PathVariable String userid) {
		int id = Integer.parseInt(userid);
		User user = null;
		user = userService.fetchUserById(id);
		if(user==null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PutMapping("/user/update/{userid}")
	public ResponseEntity<User> updateUser(@PathVariable String userid, @RequestBody User user){
		int id = Integer.parseInt(userid);
		User tempuser = null;
		tempuser = userService.fetchUserById(id);
		if(tempuser==null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		tempuser.setEmail(user.getEmail());
		tempuser.setPassword(user.getPassword());
		return new ResponseEntity<User>(userService.saveUser(tempuser), HttpStatus.OK);
	}
	
	@DeleteMapping("/user/del/{userid}")
	public ResponseEntity<User> delUser(@PathVariable String userid){
		int id = Integer.parseInt(userid);
		User user = null;
		user = userService.fetchUserById(id);
		if(user==null) return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		userService.deleteUser(user);
		return new ResponseEntity<User>(HttpStatus.OK);
	}
	
	@GetMapping("/user/getalluser")
	public List<User> getAllUser() {
		return userService.fetchAllUser();
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
