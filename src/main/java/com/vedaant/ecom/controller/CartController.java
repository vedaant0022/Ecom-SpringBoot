package com.vedaant.ecom.controller;

import com.vedaant.ecom.DTO.CartItemDTO;
import com.vedaant.ecom.DTO.CartResponseDTO;
import com.vedaant.ecom.model.Cart;
import com.vedaant.ecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService service;

    @PostMapping("/add")
    public Cart addToCart(
            @RequestParam int userId,
            @RequestParam int productId,
            @RequestParam int quantity) {

        return service.addToCart(userId,productId,quantity);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable int userId) {
        return ResponseEntity.ok(service.getCart(userId));
    }

    // 🔹 Increase quantity
    @PutMapping("/increase")
    public ResponseEntity<String> increaseQuantity(
            @RequestParam int userId,
            @RequestParam int productId) {

        service.increaseQuantity(userId, productId);
        return ResponseEntity.ok("Quantity increased");
    }

    // 🔹 Decrease quantity
    @PutMapping("/decrease")
    public ResponseEntity<String> decreaseQuantity(
            @RequestParam int userId,
            @RequestParam int productId) {

        service.decreaseQuantity(userId, productId);
        return ResponseEntity.ok("Quantity decreased");
    }

    // 🔹 Remove product
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProduct(
            @RequestParam int userId,
            @RequestParam int productId) {

        service.removeProduct(userId, productId);
        return ResponseEntity.ok("Product removed from cart");
    }

}
