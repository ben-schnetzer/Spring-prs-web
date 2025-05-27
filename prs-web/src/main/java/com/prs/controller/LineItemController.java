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

import com.prs.db.LineItemRepo;
import com.prs.model.LineItem;

@CrossOrigin
@RestController
@RequestMapping("/api/lineitems")

public class LineItemController {
	
	@Autowired
	private LineItemRepo lineItemRepo;
	
	@GetMapping("/")
	public List<LineItem> getAll() {
		return lineItemRepo.findAll();
	}
	@GetMapping("/{id}")
	public Optional<LineItem> getById(@PathVariable int id) {
		Optional<LineItem> m = lineItemRepo.findById(id);
		if (m.isPresent()) {
			return m;
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id: "+id);
		}
	}
	
	@PostMapping("")
		public LineItem add(@RequestBody LineItem product) {
			return lineItemRepo.save(product);
		}
	
	 @PutMapping("/{id}")
	 public void update(@PathVariable int id, @RequestBody LineItem lineItem) {
	  if (id != lineItem.getId()) {
	   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id mismatch vs URL.");
	  }
	  else if (lineItemRepo.existsById(lineItem.getId())) {
	   lineItemRepo.save(lineItem);
	  }
	  else {
	   throw new ResponseStatusException(
	     HttpStatus.NOT_FOUND, "LineItem not found for id "+id);
	  }
	 }
	 
	 @DeleteMapping("/{id}")
	 public void delete(@PathVariable int id) {
	  if (lineItemRepo.existsById(id)) {
	   lineItemRepo.deleteById(id);
	  }
	  else {
	   throw new ResponseStatusException(
	     HttpStatus.NOT_FOUND, "LineItem not found for id "+id);
	  }
	 }
}