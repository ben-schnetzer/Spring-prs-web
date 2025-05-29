package com.prs.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class Request {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    
    
	@ManyToOne
	@JoinColumn(name="UserId", referencedColumnName="id", nullable=false)
	private User user;
	
    @Column(nullable = true) 
    private String requestNumber;

    @Column(nullable = false) 
    private String description;

    @Column(nullable = false)
    private String justification;

    @Column(nullable = false) 
    private Date dateNeeded;

    @Column(nullable = false)
    private String deliveryMode;

    @Column(nullable = true) 
    private String status;

    @Column(nullable = true) 
    private Double total;

    @Column(nullable = true) 
    private Date submittedDate;

    @Column(nullable = true) 
    private String reasonForRejection;

    public Request(int id, User user, String requestNumber, String description, String justification, Date dateNeeded, 
                   String deliveryMode, String status, Double total, Date submittedDate, String reasonForRejection) {
        super();
        this.id = id;
        this.user = user;
        this.requestNumber = requestNumber;
        this.description = description;
        this.justification = justification;
        this.dateNeeded = dateNeeded;
        this.deliveryMode = deliveryMode;
        this.status = status;
        this.total = total;
        this.submittedDate = submittedDate;
        this.reasonForRejection = reasonForRejection;
    }

    public Request() {
        super();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }   
    public void setUser(User user) { this.user = user; }  

    public String getRequestNumber() { return requestNumber; }
    public void setRequestNumber(String requestNumber) { this.requestNumber = requestNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }

    public Date getDateNeeded() { return dateNeeded; }
    public void setDateNeeded(Date dateNeeded) { this.dateNeeded = dateNeeded; }

    public String getDeliveryMode() { return deliveryMode; }
    public void setDeliveryMode(String deliveryMode) { this.deliveryMode = deliveryMode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Date getSubmittedDate() { return submittedDate; }
    public void setSubmittedDate(Date submittedDate) { this.submittedDate = submittedDate; }

    public String getReasonForRejection() { return reasonForRejection; }
    public void setReasonForRejection(String reasonForRejection) { this.reasonForRejection = reasonForRejection; }

    @Override
    public String toString() {
        return "Request [id=" + id + ", userId=" + user + ", requestNumber=" + requestNumber + ", description=" + description 
               + ", justification=" + justification + ", dateNeeded=" + dateNeeded + ", deliveryMode=" + deliveryMode 
               + ", status=" + status + ", total=" + total + ", submittedDate=" + submittedDate 
               + ", reasonForRejection=" + reasonForRejection + "]";
    }
}