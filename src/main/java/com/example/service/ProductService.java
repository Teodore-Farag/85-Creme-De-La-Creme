package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")

public class ProductService extends MainService<Product>{
    private final ProductRepository productRepository;
    @Autowired

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product addProduct(Product product){
        return productRepository.addProduct(product);
    }

    public ArrayList<Product> getProducts() {
        return productRepository.getProducts();
    }

    //getProductById
    public Product getProductById(UUID productId) {
        Optional<Product> product = productRepository.getProductById(productId);
        if (product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return product.get();
    }
//update product
        public Product updateProduct(UUID productId, String newName, double newPrice) {
            productRepository.updateProduct(productId, newName, newPrice);
            return productRepository.getProductById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        }
 //Apply discount
    public void applyDiscount(double discount, ArrayList<UUID> productIds)
    {
        productRepository.applyDiscount(discount, productIds);
    }
 //Delete product
    public void deleteProductById(UUID productId)
    {
        productRepository.deleteProductById( productId);
    }


    }
