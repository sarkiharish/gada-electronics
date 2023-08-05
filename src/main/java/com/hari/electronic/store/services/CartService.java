package com.hari.electronic.store.services;

import com.hari.electronic.store.dtos.AddItemToCartRequest;
import com.hari.electronic.store.dtos.CartDto;

public interface CartService {
    //add item to cart
    //case1: cart for user is not available: we will create the cart and then add items
    //case2: cart available, add the items to cart
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from cart:
    void removeItemFromCart(String userId, int cartItem);

    //remove all items from cart:
    void clearCart(String userId);

    CartDto getCaryByUser(String userId);
}
