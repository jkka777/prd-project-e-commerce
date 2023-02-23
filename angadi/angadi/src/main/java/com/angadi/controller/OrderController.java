package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.model.Orders;
import com.angadi.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/saveOrder")
    public ResponseEntity<Orders> saveOrderHandler(@Valid @RequestBody Orders orders, @RequestParam Integer orderItemId, @RequestParam Integer addressId) throws CustomerException {
        return new ResponseEntity<>(orderService.saveOrder(orders, orderItemId, addressId), HttpStatus.CREATED);
    }

    @PutMapping("/updateOrder/{orderId}")
    public ResponseEntity<Orders> updateOrderHandler(@Valid @PathVariable Integer orderId, @RequestParam Integer addressId) throws OrderException, CustomerException {
        return new ResponseEntity<>(orderService.updateOrder(orderId, addressId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/cancelOrder/{orderId}")
    public ResponseEntity<Orders> cancelOrderHandler(@Valid @PathVariable Integer orderId) throws OrderException, CustomerException {
        return new ResponseEntity<>(orderService.cancelOrder(orderId), HttpStatus.OK);
    }

    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<Orders> getOrderByIdHandler(@Valid @PathVariable Integer orderId) throws OrderException, CustomerException {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    @GetMapping("/getOrdersOfCustomer")
    public ResponseEntity<List<Orders>> getCustomerOrdersHandler() throws OrderException, CustomerException {
        return new ResponseEntity<>(orderService.getAllOrdersOfCustomer(), HttpStatus.OK);
    }

    @GetMapping("/getOrdersBetweenDates/{dateFrom}/{dateTo}")
    public ResponseEntity<List<Orders>> viewOrdersBetweenDatesHandler(@Valid @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo) throws OrderException, CustomerException {
        return new ResponseEntity<>(orderService.getAllOrdersBetweenDates(dateFrom, dateTo), HttpStatus.OK);
    }

    @GetMapping("/getOrdersByAddress/{addressId}")
    public ResponseEntity<List<Orders>> getOrdersByAddressHandler(@Valid @PathVariable Integer addressId) throws OrderException, CustomerException{
        return new ResponseEntity<>(orderService.getAllOrdersByDeliveryAddress(addressId),HttpStatus.OK);
    }

    /* admin specific functionality */
    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Orders>> getAllOrdersHandler() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

}
