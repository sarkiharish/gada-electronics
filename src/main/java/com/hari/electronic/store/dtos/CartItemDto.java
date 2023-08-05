package com.hari.electronic.store.dtos;

import com.hari.electronic.store.entities.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;
    private Product product;
    private int quantity;
    private int totalPrice;

}
