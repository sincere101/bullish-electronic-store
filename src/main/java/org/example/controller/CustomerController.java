package org.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.object.*;
import org.example.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    private static Map<String, Customer> customerCart = new HashMap<>();
    @Autowired private final AdminController admin = new AdminController();

    private int receiptCounter = 0;
    private Util util;

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.POST)
    public ResponseEntity<Object> add(@PathVariable("customerId") String customerId,@RequestBody Deal deal) {
        if (!admin.getProductRepo().containsKey(deal.getProductId())) {
            return new ResponseEntity<>("Product not found", HttpStatus.OK);
        }
        if (deal.getAmt() < 1) {
            return new ResponseEntity<>("Amount should be at least 1", HttpStatus.OK);
        }
        if (customerCart.containsKey(customerId)) {
            customerCart.get(customerId).addProduct(deal.getProductId(), deal.getAmt());
        } else {
            customerCart.put(customerId, new Customer(customerId));
            customerCart.get(customerId).addProduct(deal.getProductId(), deal.getAmt());
        }
        return new ResponseEntity<>("Product is added", HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("customerId") String customerId,@RequestBody Deal deal) {
        if (!admin.getProductRepo().containsKey(deal.getProductId())) {
            return new ResponseEntity<>("Product not found", HttpStatus.OK);
        } else if (deal.getAmt() < 1) {
            return new ResponseEntity<>("Amount should be at least 1", HttpStatus.OK);
        } else if (customerCart.containsKey(customerId)) {
            customerCart.get(customerId).removeProduct(deal.getProductId(), deal.getAmt());
            return new ResponseEntity<>("Product is removed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No cart for the customer", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/receipt/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<Object> receipt(@PathVariable("customerId") String customerId) throws JsonProcessingException {
        receiptCounter++;
        double total = 0.0;
        String msg = "";
        if (customerCart.containsKey(customerId)) {
            Customer customer = customerCart.get(customerId);
            Map<String, Integer> cart = customer.getCart();
            Map<String, Integer> productReceipt = new HashMap<>();
            List<Discount> discountList = customer.getDiscount();
            List<String> discountReceipt = new ArrayList<>();

            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                Product product =  (Product) admin.getProductRepo().get(entry.getKey());
                total = total + (product.getPrice() * entry.getValue());
                productReceipt.put(product.getName(), entry.getValue());
            }

            for (Discount discount: discountList) {
                discountReceipt.add(discount.getName());
            }

            Receipt receipt = new Receipt();
            receipt.setId(receiptCounter);
            receipt.setCustomerId(customerId);
            receipt.setProduct(productReceipt);
            receipt.setDiscount(discountReceipt);
            msg = util.asJsonString(receipt);
        } else {
            msg = "No cart from customer " + customerId;
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }


}