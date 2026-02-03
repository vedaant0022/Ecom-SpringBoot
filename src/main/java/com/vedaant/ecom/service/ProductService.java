package com.vedaant.ecom.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vedaant.ecom.model.Product;
import com.vedaant.ecom.repository.ProductRepo;

@Service
public class ProductService {
    @Autowired
    ProductRepo repo;


    public List<Product> GetAllProducts() {
        return repo.findAll();
    }


    public Product findByID(int id) {
        return repo.findById(id).orElse(null);
    }


    public Product addproduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getName());
        product.setImageDate(imageFile.getBytes());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getName());
        product.setImageDate(imageFile.getBytes());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }


    public void deleteProduct(int id) {
        repo.deleteById(id);
    }
}
