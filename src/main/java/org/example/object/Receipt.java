package org.example.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Receipt {
    private int id;
    private String customerId;
    private Map<String, Integer> product = new HashMap<>();
    private List<String> discount = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setProduct(Map<String, Integer> product) {
        this.product = product;
    }

    public void setDiscount(List<String> discount) {
        this.discount = discount;
    }

    public int getId() {
        return this.id;
    }
    public String getCustomerId() {
        return this.customerId;
    }
    public Map getProduct() {
        return product;
    }
    public List getDiscount() {
        return discount;
    }
}
