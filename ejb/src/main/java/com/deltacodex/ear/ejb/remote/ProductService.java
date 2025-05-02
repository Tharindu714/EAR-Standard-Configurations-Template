package com.deltacodex.ear.ejb.remote;

import com.deltacodex.ear.core.model.Product;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ProductService {
    Product getProductById(int id);
    List<Product> getAllProducts();
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
}
