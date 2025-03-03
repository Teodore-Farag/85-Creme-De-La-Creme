package com.example.model;


@Component
public class User {
    private UUID id;
    private String name;
    private List<Order> orders=new ArrayList<>();
}
