package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderItemException;
import com.angadi.model.OrderItem;
import com.angadi.service.OrderItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderDetails")
public class OrderDetailsController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/addOrderDetails/{email}")
    public ResponseEntity<OrderItem> saveOrderDetailsHandler(@Valid @RequestBody OrderItem orderItem, @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(orderItemService.addOrderDetails(orderItem, email), HttpStatus.CREATED);
    }

    @PutMapping("/updateOrderDetails/{email}")
    public ResponseEntity<OrderItem> updateOrderDetailsHandler(@Valid @RequestBody OrderItem orderItem, @PathVariable String email) throws CustomerException, OrderItemException {
        return new ResponseEntity<>(orderItemService.updateOrderDetails(orderItem, email), HttpStatus.OK);
    }

    @DeleteMapping("/removeOrderDetails/{email}")
    public ResponseEntity<OrderItem> removeOrderDetailsHandler(@Valid @RequestBody OrderItem orderItem, @PathVariable String email) throws CustomerException, OrderItemException {
        return new ResponseEntity<>(orderItemService.removeOrderDetails(orderItem, email), HttpStatus.OK);
    }

    @GetMapping("/priceOfAnOrderDetails/{email}")
    public ResponseEntity<Double> getPriceOfAnOrderDetailHandler(@Valid @RequestBody Integer orderDetailsId, @PathVariable String email) throws CustomerException, OrderItemException {
        return new ResponseEntity<>(orderItemService.getPriceOfOrderDetails(orderDetailsId, email), HttpStatus.OK);
    }


}
