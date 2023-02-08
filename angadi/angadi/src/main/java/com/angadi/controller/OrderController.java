package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderException;
import com.angadi.model.Orders;
import com.angadi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping("/updateOrder/{email}")
    public ResponseEntity<Orders> updateOrderHandler(@RequestBody Orders orders, @PathVariable String email) throws OrderException, CustomerException {
        return new ResponseEntity<>(orderService.updateOrder(orders, email), HttpStatus.OK);
    }

    @DeleteMapping("/cancelOrder/{email}/{orderId}")
    public ResponseEntity<Orders> cancelOrderHandler(@PathVariable Integer orderId, @PathVariable String email) throws OrderException, CustomerException {
        return new ResponseEntity<>(orderService.cancelOrder(orderId, email), HttpStatus.OK);
    }

    @GetMapping("/getOrder/{email}/{orderId}")
    public ResponseEntity<Orders> getOrderByIdHandler(@PathVariable Integer orderId, @PathVariable String email) throws OrderException, CustomerException {
        return new ResponseEntity<>(orderService.getOrderById(orderId, email), HttpStatus.OK);
    }

    @GetMapping("/getOrdersOfCustomer/{email}/")
    public ResponseEntity<List<Orders>> getCustomerOrdersHandler(@PathVariable String email) throws OrderException, CustomerException {
        return new ResponseEntity<>(orderService.getOrdersByCustomerEmail(email), HttpStatus.OK);
    }


    /* admin specific functionality */
    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Orders>> getAllOrdersHandler() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

}
