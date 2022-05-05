package com.cognixia.jump.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.AuthenticationResponse;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.Purchase;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.CustomerRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.util.JwtUtil;

@RequestMapping("/api")
@RestController
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private CustomerRepository custRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@GetMapping("/hello")
	public String hello() {
		return "Hello World";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
		
		try {
			
			authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
					);
			
		} catch (Exception e) {
			
			throw new Exception("Incorrect username or password", e);
			
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		
		final String JWT = jwtUtil.generateTokens(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(JWT));
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> createNewUser(@RequestBody AuthenticationRequest newUser)
	{
		Optional<User> isAlreadyRegistered = userRepo.findByUsername(newUser.getUsername());
		
		if (isAlreadyRegistered.isPresent())
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body(newUser.getUsername() + " already exists! ");
		}
		
		String submittedPassword = newUser.getPassword();
		String encodedPassword = passwordEncoder.encode(submittedPassword);
		
		User registerUser = new User();
		registerUser.setUsername(newUser.getUsername());
		registerUser.setPassword(encodedPassword);
		registerUser.setRole(Role.valueOf("ROLE_USER"));
		
		userRepo.save(registerUser);

		Customer tempCust = new Customer(-1L, "New User", new Date(), new User(), new ArrayList<Purchase>());
		custRepo.save(tempCust);
		
		return ResponseEntity.ok(newUser.getUsername() + " created");
	}
	
	@PutMapping("/promote/{username}")
	public ResponseEntity<?> promoteUser(@PathVariable String username)
	{

		Optional<User> foundUser = userRepo.findByUsername(username);
		
		if (foundUser.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(username + " was not found!");
		}
		if (foundUser.get().getRole() == Role.ROLE_ADMIN)
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body(username + " is already an admin!");
		}
		
		User updated = foundUser.get();
		updated.setRole(Role.ROLE_ADMIN);
		userRepo.save(updated);
		
		return ResponseEntity.status(HttpStatus.OK).body(username + " was promoted to admin");
	}

	@DeleteMapping("delete/user/{username}")
	public ResponseEntity<Object> deleteUserByUsername(@PathVariable String username)
	{
		Optional<User> foundItem = userRepo.findByUsername(username);
		
		if (foundItem.isPresent())
		{
			userRepo.deleteById(foundItem.get().getId());
			return ResponseEntity.status(HttpStatus.OK).body(username + " was found and removed from userbase");
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(username + " was not found!");
	}
	
}
