package com.example.vmall.Controller;

import com.example.vmall.Entity.Product;
import com.example.vmall.Service.ProductService;
import com.example.vmall.dto.Request.ProductCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public Product create(@RequestBody ProductCreate request){
        return productService.create(request);
    }

    @GetMapping("/getproduct")
    public List<Product> getAll(){
        return productService.getAllProducts();
    }

    @GetMapping("/getproductByCategory")
    public List<Product> getProductByCategory(@RequestParam("category") String category){
        return productService.getProductByCategory(category);
    }
    @GetMapping("/getproductById")
    public Product getProductById(@RequestParam("id") int id){
        return productService.getProductById(id);
    }

    @PostMapping("/update/{id}")
    public Product update(@PathVariable int id,@RequestBody ProductCreate request){
        return productService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    String delete(@PathVariable int id){
        productService.delete(id);
        return "success";
    }

    @GetMapping("/search")
    public List<Product> searchProduct(@RequestParam("keyword") String keyword){
        return productService.searchProduct(keyword, keyword);
    }

    @GetMapping("/filterByPrice")
    public List<Product> filterByPrice(@RequestParam("priceMin") double priceMin,
                                       @RequestParam("priceMax") double priceMax) {
        return productService.filterProductByPriceBetween(priceMin, priceMax);
    }

}
