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

import com.prs.db.RequestRepo;
import com.prs.model.Request;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")

public class RequestController {
	
	@Autowired
	private RequestRepo requestRepo;
	
	@GetMapping("/")
	public List<Request> getAll() {
		return requestRepo.findAll();
	}
	@GetMapping("/{id}")
	public Optional<Request> getById(@PathVariable int id) {
		Optional<Request> m = requestRepo.findById(id);
		if (m.isPresent()) {
			return m;
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: "+id);
		}
	}
	
	@PostMapping("")
		public Request add(@RequestBody Request request) {
			return requestRepo.save(request);
		}
	
	 @PutMapping("/{id}")
	 public void update(@PathVariable int id, @RequestBody Request request) {
	  if (id != request.getId()) {
	   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request id mismatch vs URL.");
	  }
	  else if (requestRepo.existsById(request.getId())) {
	   requestRepo.save(request);
	  }
	  else {
	   throw new ResponseStatusException(
	     HttpStatus.NOT_FOUND, "Request not found for id "+id);
	  }
	 }
	 
	 @DeleteMapping("/{id}")
	 public void delete(@PathVariable int id) {
	  if (requestRepo.existsById(id)) {
	   requestRepo.deleteById(id);
	  }
	  else {
	   throw new ResponseStatusException(
	     HttpStatus.NOT_FOUND, "Request not found for id "+id);
	  }
	 }
}