package com.angadi.repository;

import com.angadi.model.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Suppliers, Integer> {
    public Suppliers findBySupplierName(String supplierName);
}
