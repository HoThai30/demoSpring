package com.example.vmall.Repository;

import com.example.vmall.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByCategory(String category);

    List<Product> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String keyword1, String keyword2);

    List<Product> findByPriceBetween(Double priceMin, Double priceMax);

}
