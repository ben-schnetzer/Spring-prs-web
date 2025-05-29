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
import com.prs.db.RequestRepo;
import com.prs.model.LineItem;
import com.prs.model.Request;

@CrossOrigin
@RestController
@RequestMapping("/api/LineItems")

public class LineItemController {
	
	@Autowired
	private LineItemRepo lineItemRepo;
	
	@Autowired
	private RequestRepo requestRepo;
	
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
	public LineItem add(@RequestBody LineItem lineItem) {
	    if (lineItem.getRequest() == null || lineItem.getProduct() == null) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request or Product cannot be null.");
	    }

	    LineItem savedLineItem = lineItemRepo.save(lineItem);
	    
	    // ✅ Explicitly re-fetch request before recalculating total
	    recalcTotal(savedLineItem.getRequest().getId());

	    return savedLineItem;
	}

	@PutMapping("/{id}")
	public void update(@PathVariable int id, @RequestBody LineItem lineItem) {
	    if (id != lineItem.getId()) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id mismatch vs URL.");
	    } else if (lineItemRepo.existsById(lineItem.getId())) {
	        lineItemRepo.save(lineItem);
	        recalcTotal(lineItem.getRequest().getId()); // ✅ Update total after updating
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id " + id);
	    }
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
	    Optional<LineItem> lineItemOpt = lineItemRepo.findById(id);
	    if (lineItemOpt.isPresent()) {
	        LineItem lineItem = lineItemOpt.get();
	        lineItemRepo.deleteById(id);
	        recalcTotal(lineItem.getRequest().getId()); // ✅ Update total after deleting
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id " + id);
	    }
	}
	
		@GetMapping("/by-product/{productId}")
		public List<LineItem> getAllIdsForProductId(@PathVariable int productId) {
			return lineItemRepo.findAllByProduct_Id(productId);
			//Find all by ProductId
			//"Find all" -> select
			//from lineItem
			//"by" ->where
			//"productId" -> productId
			// = productId (parameter passed into method)
	 }
		//TR4
		@GetMapping("/lines-for-req/{requestId}")
		public List<LineItem> getAllIdsForRequestId(@PathVariable int requestId) {
			return lineItemRepo.findAllByRequest_Id(requestId);
			//Description//Get LineItems for Request
			//Input//Body: requestId:int
			//Output-Success//List of LineItems

			//Output-Other//?
	 }
		
		private void recalcTotal(int requestId) {
			{
			System.out.println("🚀 Running recalcTotal for Request ID: " + requestId);
		    Optional<Request> requestOpt = requestRepo.findById(requestId);
		    if (requestOpt.isEmpty()) {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + requestId);
		    }

		    Request request = requestOpt.get();
		    Double newTotal = lineItemRepo.findByRequest_Id(requestId) // Get all LineItems for this request
		                                  .stream()
		                                  .mapToDouble(li -> li.getProduct().getPrice() * li.getQuantity()) // ✅ Correctly multiplies price by quantity
		                                  .sum();

		    request.setTotal(newTotal);
		    requestRepo.save(request); // ✅ Save updated Request total
			}
		}
}