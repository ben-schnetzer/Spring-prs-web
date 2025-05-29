package com.prs.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.Product;
//import com.prs.model.Vendor;

public interface ProductRepo extends JpaRepository<Product, Integer>{
	List<Product> findAllByVendor_Id(int vendorId);
}
