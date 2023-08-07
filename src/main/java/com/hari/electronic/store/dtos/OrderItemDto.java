package com.hari.electronic.store.dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItemDto {
    private int orderItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;

}
