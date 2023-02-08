package com.angadi.controller;

import com.angadi.exception.CustomerException;
import com.angadi.exception.OrderDetailsException;
import com.angadi.model.OrderDetails;
import com.angadi.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderDetails")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @PostMapping("/addOrderDetails/{email}")
    public ResponseEntity<OrderDetails> saveOrderDetailsHandler(@RequestBody OrderDetails orderDetails, @PathVariable String email) throws CustomerException {
        return new ResponseEntity<>(orderDetailsService.addOrderDetails(orderDetails, email), HttpStatus.CREATED);
    }

    @PutMapping("/updateOrderDetails/{email}")
    public ResponseEntity<OrderDetails> updateOrderDetailsHandler(@RequestBody OrderDetails orderDetails, @PathVariable String email) throws CustomerException, OrderDetailsException {
        return new ResponseEntity<>(orderDetailsService.updateOrderDetails(orderDetails, email), HttpStatus.OK);
    }

    @DeleteMapping("/removeOrderDetails/{email}")
    public ResponseEntity<OrderDetails> removeOrderDetailsHandler(@RequestBody OrderDetails orderDetails, @PathVariable String email) throws CustomerException, OrderDetailsException {
        return new ResponseEntity<>(orderDetailsService.removeOrderDetails(orderDetails, email), HttpStatus.OK);
    }

    @GetMapping("/priceOfAnOrderDetails/{email}")
    public ResponseEntity<Double> getPriceOfAnOrderDetailHandler(@RequestBody Integer orderDetailsId, @PathVariable String email) throws CustomerException, OrderDetailsException {
        return new ResponseEntity<>(orderDetailsService.getPriceOfOrderDetails(orderDetailsId, email), HttpStatus.OK);
    }


}
