package com.example.repository;

import com.example.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.logging.log4j.util.LambdaUtil.getAll;

@Repository
@SuppressWarnings("rawtypes")
public class ProductRepository extends MainRepository<Product> {

    public ProductRepository() {
        super();
    }

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/products.json";
    }

    @Override
    protected Class<Product[]> getArrayType() {
        return Product[].class;
    }

    //Add Product
    public Product addProduct(Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID());
        }
        save(product);
        return product;
    }

    //Get All Products
    public ArrayList<Product> getProducts() {
        return findAll();
    }

    //getProductById
    public Optional<Product> getProductById(UUID productId) {
        try {
            return findAll().stream()
                    .filter(product -> product.getId().equals(productId))
                    .findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to read users.json");
        }
    }

    //Update product
    public Product updateProduct(UUID productId, String newName, double newPrice) {
        ArrayList<Product> products = findAll();
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                if (newName != null) {
                    product.setName(newName);
                }
                if (newPrice >= 0) {
                    product.setPrice(newPrice);
                } else {
                    throw new IllegalArgumentException("Price Cannot be less than 0");
                }
                saveAll(products);
                return product;
            }
        }
        return null;
    }

    //Discount
    public void applyDiscount(double discount, ArrayList<UUID> productIds) {
        ArrayList<Product> products = findAll();
        double dis = 0;
        if (discount < 0) {
            throw new IllegalArgumentException("Discount Cannot be Negative");
        }
        for (Product product : products) {
            for (int i = 0; i < productIds.size(); i++) {
                if (productIds.get(i).equals(product.getId())) {
                    dis = product.getPrice() * (discount / 100);
                    product.setPrice(product.getPrice() - dis);
                }
                saveAll(products);
            }
        }
    }

    //Delete a product
    public void deleteProductById(UUID productId) {
        ArrayList<Product> products = findAll();
        boolean removed = products.removeIf(product -> product.getId().equals(productId));

        if (removed) {
            saveAll(products);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

}


