package org.example.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.object.Product;
import org.example.object.Discount;

@RestController
public class AdminController {
    private Map<String, Product> productRepo = new HashMap<>();
    private Map<String, Discount> discountRepo = new HashMap<>();

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Product product) {
        productRepo.put(product.getId(), product);
        String msg = "Product " + product.getId() + " is created successfully";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        productRepo.remove(id);
        String msg = "Product " + id + " is deleted successfully";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/deals", method = RequestMethod.POST)
    public ResponseEntity<Object> discount(@RequestBody Discount discount) {
        discountRepo.put(discount.getId(), discount);
        String msg = "Discount " + discount.getId() + " is created successfully";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public Map getProductRepo() {
        return productRepo;
    }
    public Map getDiscountRepo() {
        return discountRepo;
    }
}