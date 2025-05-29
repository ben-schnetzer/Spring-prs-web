package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.UserRepo;
import com.prs.model.User;

@CrossOrigin
@RestController
@RequestMapping("/api/Users")

public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/")
	public List<User> getAll() {
		return userRepo.findAll();
	}
	@GetMapping("/{id}")
	public Optional<User> getById(@PathVariable int id) {
		Optional<User> m = userRepo.findById(id);
		if (m.isPresent()) {
			return m;
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id: "+id);
		}
	}
	
	//TR1 TODO
	//@PostMapping("/login")
		//Description//Login
		//Input//Body: Username: str, Password: str
		
		//Output-Success//Single instance of User

		//Output-Other//NotFound(404)
	
	
	//What is this? Should I delete and remake it above?
	@PostMapping("/login")
	public User add(@RequestBody User user) {
		return userRepo.save(user);
	}
	
	 @PutMapping("/{id}")
	 public void update(@PathVariable int id, @RequestBody User user) {
	  if (id != user.getId()) {
	   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id mismatch vs URL.");
	  }
	  else if (userRepo.existsById(user.getId())) {
	   userRepo.save(user);
	  }
	  else {
	   throw new ResponseStatusException(
	     HttpStatus.NOT_FOUND, "User not found for id "+id);
	  }
	 }
	 
	 @DeleteMapping("/{id}")
	 public void delete(@PathVariable int id) {
	  if (userRepo.existsById(id)) {
	   userRepo.deleteById(id);
	  }
	  else {
	   throw new ResponseStatusException(
	     HttpStatus.NOT_FOUND, "User not found for id "+id);
	  }
	 }
}
