package com.prs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class Product {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
  
    
	@ManyToOne
	@JoinColumn(name="VendorId", referencedColumnName="id", nullable=false)
	private Vendor vendor;
    
    private String partNumber;
    private String name;
    private double price;
    private String unit;
    private String photoPath;

    public Product(int id, Vendor vendorId, String partNumber, String name, double price, String unit, String photoPath) {
        super();
        this.id = id;
        this.vendor = vendorId;
        this.partNumber = partNumber;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.photoPath = photoPath;
    }

    public Product() {
        super();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Vendor getVendor() { return vendor; }  
    public void setVendor(Vendor vendor) { this.vendor = vendor; }   

    public String getPartNumber() { return partNumber; }
    public void setPartNumber(String partNumber) { this.partNumber = partNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    @Override
    public String toString() {
        return "Product [id=" + id + ", vendorId=" + vendor + ", partNumber=" + partNumber + ", name=" + name 
               + ", price=" + price + ", unit=" + unit + ", photoPath=" + photoPath + "]";
    }
}