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
@RequestMapping("/orderItems")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/addOrderItem")
    public ResponseEntity<OrderItem> saveOrderItemHandler(@Valid @RequestBody OrderItem orderItem, @RequestParam Integer cartItemId) throws CustomerException {
        return new ResponseEntity<>(orderItemService.addOrderItems(orderItem, cartItemId), HttpStatus.CREATED);
    }

    @PutMapping("/updateOrderItem")
    public ResponseEntity<OrderItem> updateOrderItemHandler(@Valid @RequestBody OrderItem orderItem, @RequestParam Integer quantity) throws CustomerException, OrderItemException {
        return new ResponseEntity<>(orderItemService.updateOrderItems(orderItem, quantity), HttpStatus.OK);
    }

    @DeleteMapping("/removeOrderItem")
    public ResponseEntity<OrderItem> removeOrderItemHandler(@Valid @RequestBody OrderItem orderItem) throws CustomerException, OrderItemException {
        return new ResponseEntity<>(orderItemService.removeOrderItems(orderItem), HttpStatus.OK);
    }

    @GetMapping("/priceOfAnOrderItem")
    public ResponseEntity<Integer> getPriceOfAnOrderItemHandler(@Valid @RequestBody Integer orderDetailsId) throws CustomerException, OrderItemException {
        return new ResponseEntity<>(orderItemService.getPriceOfOrderItems(orderDetailsId), HttpStatus.OK);
    }


}
