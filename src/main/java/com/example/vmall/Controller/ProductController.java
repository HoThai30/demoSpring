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
    @PostMapping("/update")
    public Product update(@PathVariable int id,@RequestBody ProductCreate request){
        return productService.update(id, request);
    }
    @DeleteMapping("/delete")
    String delete(@PathVariable int id){
        productService.delete(id);
        return "success";
    }
    @GetMapping("/search")
    public List<Product> searchProduct(@RequestParam("keyword") String keyword){
        return productService.searchProduct(keyword);
    }
}
