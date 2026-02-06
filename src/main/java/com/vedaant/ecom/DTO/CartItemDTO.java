package com.vedaant.ecom.DTO;

import java.math.BigDecimal;

public class CartItemDTO {

    private int productId;
    private String name;
    private String desc;
    private BigDecimal price;
    private int quantity;

    public CartItemDTO(int productId,
                       String name,
                       String desc,
                       BigDecimal price,
                       int quantity) {
        this.productId = productId;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
