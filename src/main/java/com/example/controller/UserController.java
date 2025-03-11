package com.example.controller;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.service.CartService;
import com.example.service.ProductService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public UserController(UserService userService, CartService cartService, ProductService productService) {
        this.userService = userService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // 8.1.2.2 Get All Users
    @GetMapping("/")
    public ArrayList<User> getUsers() {
        return userService.getUsers();
    }

    // 8.1.2.3 Get Specific User
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    // 8.1.2.4 Get a User’s Orders
    @GetMapping("/{userId}/orders")
    public List<Order> getOrdersByUserId(@PathVariable UUID userId) {
        return userService.getOrdersByUserId(userId);
    }

    @PostMapping("/{userId}/checkout")
    public String addOrderToUser(@PathVariable UUID userId) {
        try {
            userService.addOrderToUser(userId);
            return "Order added successfully" ;

        } catch (Exception e) {
            return "failed to add order to user with UserId: " + userId + "\n" + e.getMessage();
        }

    }

    @PostMapping("/{userId}/removeOrder")
    public String removeOrderFromUser(@PathVariable UUID userId, @RequestParam UUID orderId) {
        try {
            userService.removeOrderFromUser(userId, orderId);
            return "Order removed successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/{userId}/emptyCart")
    public String emptyCart(@PathVariable UUID userId) {
        try {
            userService.emptyCart(userId);
            return "Cart emptied successfully" ;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping("/addProductToCart")
    public String addProductToCart(@RequestParam UUID userId, @RequestParam UUID productId) {
        try {
            Cart userCart = cartService.getCartByUserId(userId);
            if (userCart == null) {
                userCart = cartService.addCart(new Cart(userId,new ArrayList<Product>()));
            }
            Product product = productService.getProductById(productId);
            cartService.addProductToCart(userCart.getId(), product);
            return "Product added to cart";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    //    TODO  8.1.2.9 need to be done after the cart service is done
    @PutMapping("/deleteProductFromCart")
    public String deleteProductFromCart(@RequestParam UUID userId, @RequestParam UUID productId) {
        try {
            Cart userCart = cartService.getCartByUserId(userId);
            Product product = productService.getProductById(productId);
            cartService.deleteProductFromCart(userCart.getId(), product);
            return "Product deleted from cart";
        } catch (Exception e) {
            return "Cart is empty";
        }

    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUserById(@PathVariable UUID userId) {
        try {
            userService.deleteUserById(userId);
            return  "User deleted successfully" ;
        } catch (Exception e) {
            return "User not found";
        }
    }

}