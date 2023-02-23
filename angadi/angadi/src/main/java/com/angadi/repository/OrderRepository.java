package com.angadi.repository;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT o FROM Orders o WHERE o.orderDate BETWEEN :fromDate AND :toDate")
    List<Orders> findOrdersBetweenDates(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
