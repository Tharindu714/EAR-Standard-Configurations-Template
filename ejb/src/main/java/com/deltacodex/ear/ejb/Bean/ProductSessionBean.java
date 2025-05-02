package com.deltacodex.ear.ejb.Bean;

import com.deltacodex.ear.core.model.Product;
import com.deltacodex.ear.ejb.remote.ProductService;
import jakarta.ejb.Stateless;

import java.util.List;
@Stateless
public class ProductSessionBean implements ProductService {
    @Override
    public Product getProductById(int id) {
        return new Product(1,"Playstation 5 New Edition","Description not added yet",98000.00,100);
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of(
        new Product(1,"Playstation 5 New Edition","Description not added yet",98000.00,100),
        new Product(2,"Playstation 4 Pro Edition","Description not added yet",85000.00,50),
        new Product(3,"Nintendo Switch (RED)","Description not added yet",90000.00,110)
        );
    }

    @Override
    public void addProduct(Product product) {

    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(Product product) {

    }
}
