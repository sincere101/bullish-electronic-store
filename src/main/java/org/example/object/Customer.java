package org.example.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer {
    private String id;
    private Map<String, Integer> cart = new HashMap<>();
    private List<Discount> discountList = new ArrayList<>();

    public Customer(String id) {
        this.id = id;
    }
    public Map getCart() {
        return cart;
    }
    public List getDiscount() {
        return discountList;
    }
    public void addProduct(String id, int amt) {
        if (cart.containsKey(id)) {
            Integer newAmt = Integer.sum(cart.get(id), amt);
            cart.put(id, newAmt);
        } else {
            cart.put(id, amt);
        }
    }

    public void removeProduct(String id, int amt) {
        if (cart.containsKey(id)) {
            Integer newAmt = cart.get(id) - amt;
            if (newAmt < 0) newAmt = 0;
            cart.put(id, newAmt);
            if (cart.get(id) == 0) cart.remove(id);
        }
    }

    public void addDiscount(Discount discount) {
        discountList.add(discount);
    }
    public void removeDiscount(Discount discount) {
        discountList.remove(discount);
    }
    public Customer getReceipt() {



        return this;
    }
}
