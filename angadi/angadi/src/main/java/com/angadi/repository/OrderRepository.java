package com.angadi.repository;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    public List<Orders> findByOrdersBetweenDates(LocalDate dateFrom, LocalDate dateTo) throws OrderException, CustomerException;
}
