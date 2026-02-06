package com.vedaant.ecom.service;

import com.vedaant.ecom.DTO.CartItemDTO;
import com.vedaant.ecom.DTO.CartResponseDTO;
import com.vedaant.ecom.model.Cart;
import com.vedaant.ecom.model.CartItem;
import com.vedaant.ecom.model.Product;
import com.vedaant.ecom.model.User;
import com.vedaant.ecom.repository.CartRepository;
import com.vedaant.ecom.repository.ProductRepo;
import com.vedaant.ecom.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    // ✅ Constructor Injection (MANDATORY)
    public CartService(CartRepository cartRepo,
                       ProductRepo productRepo,
                       UserRepo userRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public Cart addToCart(int userId, int productId, int quantity) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User does not exist"));

        Cart cart = cartRepo.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepo.save(newCart);
                });

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getItems().add(item);
        }

        return cartRepo.save(cart);
    }

public CartResponseDTO getCart(int userId) {

    List<CartItemDTO> items = cartRepo.getCartItems(userId);
    BigDecimal total = cartRepo.calculateTotal(userId);

    return new CartResponseDTO(items, total);
}
    public void increaseQuantity(int userId, int productId) {
        int updated = cartRepo.increaseQuantity(userId, productId);
        if (updated == 0) {
            throw new RuntimeException("Product not found in cart");
        }
    }

    public void decreaseQuantity(int userId, int productId) {
        int updated = cartRepo.decreaseQuantity(userId, productId);
        if (updated == 0) {
            cartRepo.removeProduct(userId, productId);
        }
    }

    public void removeProduct(int userId, int productId) {
        int deleted = cartRepo.removeProduct(userId, productId);
        if (deleted == 0) {
            throw new RuntimeException("Product not found in cart");
        }
    }
}
