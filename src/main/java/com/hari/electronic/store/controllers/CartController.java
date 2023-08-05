package com.hari.electronic.store.controllers;

import com.hari.electronic.store.dtos.AddItemToCartRequest;
import com.hari.electronic.store.dtos.ApiResponseMessage;
import com.hari.electronic.store.dtos.CartDto;
import com.hari.electronic.store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    //add item to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return  new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    //remove item from cart
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId, @PathVariable int itemId) {
        cartService.removeItemFromCart(userId, itemId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Item is removed Successfully!")
                .success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    //clear the cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Cart is cleared Successfully!")
                .success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    //get cart of user
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCartOfUser(@PathVariable String userId) {
        CartDto cartDto = cartService.getCaryByUser(userId);
        return  new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}
