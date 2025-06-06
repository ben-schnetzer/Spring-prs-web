package com.prs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class LineItem {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    //private int requestId;
    
	@ManyToOne
	@JoinColumn(name="RequestId", referencedColumnName="id", nullable=false)
	
	private Request request;
    //private int productId;
    
	@ManyToOne
	@JoinColumn(name="ProductId", referencedColumnName="id", nullable=false)
	private Product product;
	
    private int quantity;

    public LineItem(int id, Request requestId, Product productId, int quantity) {
        super();
        this.id = id;
        this.request = requestId;
        this.product = productId;
        this.quantity = quantity;
    }

    public LineItem() {
        super();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Request getRequest() { return request; }  
    public void setRequest(Request request) { this.request = request; }  

    public Product getProduct() { return product; }  
    public void setProduct(Product product) { this.product = product; }  

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

   

    @Override
    public String toString() {
        return "Product [id=" + id + ", requestId=" + request + ", productId=" + product + ", quantity=" + quantity + "]";
    }
}