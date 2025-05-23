package com.example.repository;

import com.example.model.Cart;
import com.example.model.Order;
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
        try {
            if(getCartById(cartId)==null){
                throw new IllegalArgumentException("CartID not Valid");
            }
            if(product==null){
                throw new IllegalArgumentException("product is null");
            }
            ArrayList<Cart> allCarts = findAll();
            for (Cart cart : allCarts) {
                if (cart.getId() != null && cart.getId().equals(cartId)) {
                    cart.getProducts().add(product);
                    saveAll(allCarts);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while adding product to cart", e);
        }

    }
    public void deleteProductFromCart(UUID cartId, Product product) {
        try {
            ArrayList<Cart> allCarts = findAll();

            for (Cart cart : allCarts) {
                if (cart.getId() != null && cart.getId().equals(cartId)) {
                    List<Product> products = cart.getProducts();
                    boolean removed = products.removeIf(p -> p.getId() != null && p.getId().equals(product.getId()));
                    if (removed) {
                        saveAll(allCarts);
                        return;
                    } else {
                        throw new RuntimeException("Couldn't find product in cart");
                    }
                }
            }
            throw new RuntimeException("Couldn't find cart");
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting product from cart", e);
        }
    }

    public void deleteCartById(UUID cartId) {
            if(cartId==null){
                throw new IllegalArgumentException("CartNotFound");
            }
            if(getCartById(cartId)==null){
                throw new IllegalArgumentException("CartNotFound");
            }

            ArrayList<Cart> carts = findAll();
            boolean removed = carts.removeIf(cart -> cart.getId().equals(cartId));
            if (removed) {
                saveAll(carts);
            }
    }


}
