package com.example.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Order {
    private UUID id;
    private UUID userId;
    private double totalPrice;
    private List<Product> products = new ArrayList<>();
    private Order order;

    public UUID getId() {
        return id;
    }

    // Setter for id
    public void setId(UUID id) {
        this.id = id;
    }

    // Getter for userId
    public UUID getUserId() {
        return userId;
    }

    // Setter for userId
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    // Getter for totalPrice
    public double getTotalPrice() {
        return totalPrice;
    }

    // Setter for totalPrice
    public void setTotalPrice(double totalPrice) {
        order.totalPrice = totalPrice;
    }

    // Getter for products
    public List<Product> getProducts() {
        return products;
    }

    // Setter for products
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    // Optional: Method to add a product to the list
    public void addProduct(Product product) {
        this.products.add(product);
    }

    // Optional: Method to remove a product from the list
    public void removeProduct(Product product) {
        this.products.remove(product);
    }
}