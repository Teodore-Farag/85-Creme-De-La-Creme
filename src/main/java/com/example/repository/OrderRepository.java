package com.example.repository;

import org.springframework.stereotype.Repository;
import com.example.model.Order;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public class OrderRepository extends MainRepository<Order> {

    private static final String DATA_PATH = "src/main/java/com/example/data/orders.json";
    ;

    @Override
    protected String getDataPath() {
        return DATA_PATH;
    }

    @Override
    protected Class<Order[]> getArrayType() {
        return Order[].class;
    }

    public OrderRepository() {
        super();
    }

    public void addOrder(Order order) {
        save(order);
    }

    public ArrayList<Order> getOrders() {
        return findAll();
    }

    public Order getOrderById(UUID orderId) {
        ArrayList<Order> orders = findAll();
        for (Order order : orders) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public void deleteOrderById(UUID orderId) {
        ArrayList<Order> orders = findAll();
        boolean removed = orders.removeIf(order -> order.getId().equals(orderId));

        if (removed) {
            saveAll(orders);
        }
    }
}
