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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prs.db.RequestRepo;
import com.prs.model.Request;
//import com.prs.model.User;

@CrossOrigin
@RestController
@RequestMapping("/api/Requests")

public class RequestController {
	
	private static final Logger log = LoggerFactory.getLogger(RequestController.class); // ✅ Add this
	
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
		log.info("Received Request: {}", request.getUser());
			return requestRepo.save(request);
			//Front End passes UserId, Desc., Just., DateNeeded, and DlvMode. Rest of fields are managed by Back end
		}
	
	//TR6 TODO
	//@PutMapping("/submit-review/{id}")
		//Description//Submit Request for review
		//Input//Body: id:int
		
		//Output-Success//Single instance of Request

		//Output-Other//?
	
	//If total is <= $50, set request status to 'APPROVED', else set to 'REVIEW' 
			//Change submittedDate to current date
	
	//TR7 TODO
	//@GetMapping("/list-review/{userId}")
		//Description//Get Requests ready for review
		//Input//Body: userId:int
		
		//Output-Success//List of Requests

		//Output-Other//?
	
	//Get requests in REVIEW status and req.userId != to userId
	
	//TR11 TODO
	//@PutMapping("/approve/{id}")
	//Description//Approve Request
	//Input//Body: id:int
	
	//Output-Success//Single Instance of Request
	
	//Get the request for id, set status to APPROVED, and then save request.
	
	//TR12 TODO
	//@PutMapping("/reject/{id}")
	//Description//Reject Request
	//Input//Body: id:int, reason: str [from body]
	
	//Output-Success//NoContent(204)
	
	//Get the request for id, set status to REJECTED, set the reasonForRejection t- reason, and then save request.
	
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
	  
		@GetMapping("/by-user/{userId}")
		public List<Request> getAllIdsForUserId(@PathVariable int userId) {
			return requestRepo.findAllByUser_Id(userId);
			//Find all by UserId
			//"Find all" -> select
			//from request
			//"by" ->where
			//"userId" -> userId
			// = userId (parameter passed into method)
	 }
}