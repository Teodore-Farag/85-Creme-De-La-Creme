package com.example.service;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class OrderService extends MainService<Order> {

    private static final java.util.UUID UUID = java.util.UUID.randomUUID();
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Order order) {
        if (order.getId() == null) {
            order.setId(UUID);
        } else if (orderRepository.getOrderById(order.getId()) != null) {
            throw new IllegalArgumentException("Order ID already exists!");
        }
        orderRepository.addOrder(order);
    }

    public ArrayList<Order> getOrders() {
        return orderRepository.getOrders();
    }

    public Order getOrderById(UUID orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        Order order = orderRepository.getOrderById(orderId);

        if (order == null) {
            throw new RuntimeException("Order with ID " + orderId + " not found");
        }

        return order;
    }

    public void deleteOrderById(UUID orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        Order order = orderRepository.getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order with ID " + orderId + " not found");
        }

        orderRepository.deleteOrderById(orderId);
    }

}
