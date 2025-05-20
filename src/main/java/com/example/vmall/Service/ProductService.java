package com.example.vmall.Service;

import com.example.vmall.Entity.Product;
import com.example.vmall.Repository.ProductRepository;
import com.example.vmall.dto.Request.ProductCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product create(ProductCreate request){
        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        return productRepository.save(product);
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }


    public List<Product> getProductByCategory(String  category) {
        List<Product> products = productRepository.findByCategory(category);
        if(products.isEmpty())throw new RuntimeException("404 not found"+category);
        return products;
    }

    public Product getProductById(Integer id){
        return productRepository.findById(id).get();
    }
    public Product update(int id, ProductCreate request){
        Product product = getProductById(id);
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        return productRepository.save(product);

    }
    public void delete(int id){
        Product product = getProductById(id);
        productRepository.deleteById(id);
    }

    public List<Product> searchProduct(String keyword){
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found with keyword: \"" + keyword + "\"");
        }
        return products;
    }


}
