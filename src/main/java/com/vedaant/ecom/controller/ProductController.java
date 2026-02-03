package com.vedaant.ecom.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vedaant.ecom.model.Product;
import com.vedaant.ecom.service.ProductService;

@Controller
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProduct() {
        List<Product> resultant = service.GetAllProducts();
        if (resultant.isEmpty()) {
            return new ResponseEntity<>("No Products Found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(resultant, HttpStatus.FOUND);
        }

    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getbyId(@PathVariable int id) {
//		return service.findByID(id);
        Product product = service.findByID(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(@RequestPart("product") Product product,
                                        @RequestPart("imageFile") MultipartFile imageFile) {
        if (imageFile == null) {
            return new ResponseEntity<String>("Image Not Found", HttpStatus.NOT_FOUND);
        }
        if (product == null) {
            return new ResponseEntity<String>("Product Not Found", HttpStatus.NOT_FOUND);
        }
        try {
            Product prod = service.addproduct(product, imageFile);
            return new ResponseEntity<>(prod, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
@GetMapping("/products/{productId}/image")
public ResponseEntity<?> getProductImage(@PathVariable int productId) {

    Product product = service.findByID(productId);

    if (product == null || product.getImageDate() == null) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("No image found for this product");
    }

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(product.getImageType()))
            .body(product.getImageDate());
}

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) {
        Product prod = service.findByID(id);
        if (prod != null) {
            try {
                service.updateProduct(id, product, imageFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new ResponseEntity<>("Updated", HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Failed to update", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        try {
            Product prod = service.findByID(id);
            if (prod != null) {
                service.deleteProduct(id);
                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
