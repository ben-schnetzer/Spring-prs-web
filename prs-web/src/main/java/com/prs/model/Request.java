package com.prs.model;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Request {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int userId;
    private String requestNumber;
    private String description;
    private String justification;
    private Date dateNeeded;
    private String deliveryMode;
    private String status;
    private double total;
    private Timestamp submittedDate;
    private String reasonForRejection;

    public Request(int id, int userId, String requestNumber, String description, String justification, Date dateNeeded, 
                   String deliveryMode, String status, double total, Timestamp submittedDate, String reasonForRejection) {
        super();
        this.id = id;
        this.userId = userId;
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

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

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

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Timestamp getSubmittedDate() { return submittedDate; }
    public void setSubmittedDate(Timestamp submittedDate) { this.submittedDate = submittedDate; }

    public String getReasonForRejection() { return reasonForRejection; }
    public void setReasonForRejection(String reasonForRejection) { this.reasonForRejection = reasonForRejection; }

    @Override
    public String toString() {
        return "Request [id=" + id + ", userId=" + userId + ", requestNumber=" + requestNumber + ", description=" + description 
               + ", justification=" + justification + ", dateNeeded=" + dateNeeded + ", deliveryMode=" + deliveryMode 
               + ", status=" + status + ", total=" + total + ", submittedDate=" + submittedDate 
               + ", reasonForRejection=" + reasonForRejection + "]";
    }
}