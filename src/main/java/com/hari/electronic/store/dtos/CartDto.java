package com.hari.electronic.store.dtos;

import com.hari.electronic.store.entities.CartItem;
import com.hari.electronic.store.entities.User;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private String cartId;
    private Date createdAt;
    private User user;
    private List<CartItem> items = new ArrayList<>();
}
