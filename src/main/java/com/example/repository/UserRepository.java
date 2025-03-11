package com.example.repository;

import com.example.model.Order;
import com.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class UserRepository extends MainRepository<User> {
    public UserRepository() {
    }

    @Override
    protected String getDataPath() {
        // Path to your JSON file, e.g., in src/main/resources or src/main/java
        return "src/main/java/com/example/data/users.json";
    }

    @Override
    protected Class<User[]> getArrayType() {
        return User[].class;
    }

    public ArrayList<User> getUsers() {
        return findAll();
    }


    public User getUserById(UUID userId) {
        try {
            ArrayList<User> allUsers = findAll();

            for (User user : allUsers) {
                if (user.getId() != null && user.getId().equals(userId)) {
                    return user;
                }
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException("error trying to get user by ID ", e);
        }
    }

    public User addUser(User user) {
        try {
            if (user.getId() == null ) {
                user.setId(UUID.randomUUID());
            }
            if(getUserById(user.getId())!=null){
                throw new RuntimeException("User with ID " + user.getId() + " already exists.");
            }
            save(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("error trying to add user ", e);
        }
    }

    public List<Order> getOrdersByUserId(UUID userId) {
        try {
            ArrayList<User> allUsers = findAll();
            for (User user : allUsers) {
                if (user.getId() != null && user.getId().equals(userId)) {
                    return user.getOrders();
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("error getting orders by userId", e);

        }
    }

    public void addOrderToUser(UUID userId, Order order) {
        try {
            ArrayList<User> allUsers = findAll();
            for (User user : allUsers) {
                if (user.getId() != null && user.getId().equals(userId)) {
                    user.getOrders().add(order);
                    saveAll(allUsers);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while adding order to user", e);
        }
    }

    public void removeOrderFromUser(UUID userId, UUID orderId) {
        try {
            ArrayList<User> allUsers = findAll();
            for (User user : allUsers) {
                if (user.getId() != null && user.getId().equals(userId)) {
                    List<Order> orders = user.getOrders();
                    for (Order order : orders) {
                        if (order.getId() != null && order.getId().equals(orderId)) {
                            user.getOrders().remove(order);
                            saveAll(allUsers);
                            return;
                        }
                    }
                    throw new RuntimeException("Couldn't find order with id " + orderId + " in user " + userId);
                }
            }
            throw new RuntimeException("Couldn't find user with id " + userId);
        } catch (Exception e) {
            throw new RuntimeException("Error while removing order from user", e);
        }
    }

    public void deleteUserById(UUID userId) {
        try {
            ArrayList<User> allUsers = findAll();
            for (User user : allUsers) {
                if (user.getId() != null && user.getId().equals(userId)) {
                    allUsers.remove(user);
                    saveAll(allUsers);
                    return;
                }

            }
            throw new RuntimeException("User does not exist");
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting user", e);
        }
    }


}