package org.example.object;

public class Product {
    private String id;
    private String name;
    private Double price;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getPrice() {
        return price;
    }
}