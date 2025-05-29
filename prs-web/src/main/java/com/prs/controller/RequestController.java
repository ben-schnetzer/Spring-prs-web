package com.prs.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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


@CrossOrigin
@RestController
@RequestMapping("/api/Requests")

public class RequestController {
	
	private static final Logger log = LoggerFactory.getLogger(RequestController.class); 
	
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
    String requestNumber = generateRequestNumber();
    request.setRequestNumber(requestNumber);
    request.setStatus("NEW");
    request.setTotal(0.0); 
    request.setSubmittedDate(new java.sql.Date(System.currentTimeMillis()));
	
		return requestRepo.save(request);
		//Front End passes UserId, Desc., Just., DateNeeded, and DlvMode. Rest of fields are managed by Back end
	}
	
	//TR6
	@PutMapping("/submit-review/{id}")
	public Request submitReview(@PathVariable int id) {
	    Optional<Request> request = requestRepo.findById(id);
	    if (request.isEmpty()) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);
	    }

	    Request r = request.get();
	    r.setSubmittedDate(new java.sql.Date(System.currentTimeMillis())); // ✅ Fixing the Date type issue

	    if (r.getTotal() != null && r.getTotal() <= 50) {
	        r.setStatus("APPROVED");
	    } else {
	        r.setStatus("REVIEW");
	    }

	    return requestRepo.save(r);
	}
	//If total is <= $50, set request status to 'APPROVED', else set to 'REVIEW' 
	//Change submittedDate to current date
	 
	//TR7
	@GetMapping("/list-review/{userId}")
	public List<Request> listReview(@PathVariable int userId) {
	    return requestRepo.findAllByStatus("REVIEW") // Fetch all REVIEW requests first
	                      .stream()
	                      .filter(req -> req.getUser().getId() != userId) // Ensure it does NOT belong to the given user
	                      .toList();
	}
	//Get requests in REVIEW status and req.userId != to userId
	  
	//TR11
	  @PutMapping("/approve/{id}")
	    public Request approveRequest(@PathVariable int id) {
	        Optional<Request> request = requestRepo.findById(id);
	        if (request.isEmpty()) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);
	        }

	        Request r = request.get();
	        r.setStatus("APPROVED");

	        return requestRepo.save(r);
	    }
		//Get the request for id, set status to APPROVED, and then save request.
	  
	//TR12
	  @PutMapping("/reject/{id}")
	    public void rejectRequest(@PathVariable int id, @RequestBody Map<String, String> body) {
	        Optional<Request> request = requestRepo.findById(id);
	        if (request.isEmpty()) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);
	        }

	        Request r = request.get();
	        r.setStatus("REJECTED");
	        r.setReasonForRejection(body.get("reasonForRejection"));
	        requestRepo.save(r);
	    }
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
	 
	 private String generateRequestNumber() {
		    // Get current date in YYMMDD format
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		    String datePart = dateFormat.format(new Date());

		    
		    Request lastRequest = requestRepo.findTopByOrderByIdDesc(); 

		    int nextNumber = 1; 

		    if (lastRequest != null && lastRequest.getRequestNumber() != null) {
		        String lastRequestNumber = lastRequest.getRequestNumber();

		      
		        if (lastRequestNumber.length() >= 7) {
		            String lastNumber = lastRequestNumber.substring(7); 
		            nextNumber = Integer.parseInt(lastNumber) + 1; 
		        }
		    }

		 
		    return "R" + datePart + String.format("%04d", nextNumber);
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