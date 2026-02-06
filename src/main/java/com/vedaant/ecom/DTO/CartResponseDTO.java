package com.vedaant.ecom.DTO;

import java.math.BigDecimal;
import java.util.List;

public class CartResponseDTO {

    private List<CartItemDTO> items;
    private BigDecimal totalAmount;

    public CartResponseDTO(List<CartItemDTO> items, BigDecimal totalAmount) {
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
