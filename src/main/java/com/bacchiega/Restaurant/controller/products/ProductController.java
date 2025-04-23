package com.bacchiega.Restaurant.controller.products;

import com.bacchiega.Restaurant.dto.product.ProductDto;
import com.bacchiega.Restaurant.service.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody ProductDto productDto){
        ProductDto product = productService.createProduct(productDto);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message: ", "Product created successfully");
        response.put("data: ", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("listAll")
    public ResponseEntity<Map<String, Object>> listAll(){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message: ", "Products listed successfully");
        response.put("data: ", productService.listAllProduct());
        return ResponseEntity.ok().body(response);
    }
}
