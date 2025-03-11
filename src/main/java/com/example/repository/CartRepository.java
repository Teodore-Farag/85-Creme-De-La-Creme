package com.example.repository;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")

public class CartRepository extends MainRepository<Cart> {
    public CartRepository() {
    }

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/carts.json";
    }

    @Override
    protected Class<Cart[]> getArrayType() {
        return Cart[].class;
    }

    public Cart addCart(Cart cart) {
        try {
            if (cart.getId() == null) {
                cart.setId(UUID.randomUUID());
            }
            save(cart);
            return cart;
        } catch (Exception e) {
            throw new RuntimeException("error trying to add cart ", e);
        }
    }

    public ArrayList<Cart> getCarts() {
        return findAll();
    }

    public Cart getCartById(UUID cartId) {
        try {
            ArrayList<Cart> allCarts = findAll();
            for (Cart cart : allCarts) {
                if (cart.getId() != null && cart.getId().equals(cartId)) {
                    return cart;
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("error trying to get cart by ID ", e);
        }
    }

    public Cart getCartByUserId(UUID userId) {
        return findAll().stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public void addProductToCart(UUID cartId, Product product) {
        List<Cart> carts = findAll(); // Retrieve all carts

        for (Cart c : carts) {
            if (c.getId().equals(cartId)) { // Find the matching cart
                c.getProducts().add(product); // Modify the cart directly
                saveAll(carts); // Save the updated list
                return;
            }
        }
    }




}
