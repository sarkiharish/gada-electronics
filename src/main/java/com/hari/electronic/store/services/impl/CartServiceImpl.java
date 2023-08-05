package com.hari.electronic.store.services.impl;

import com.hari.electronic.store.dtos.AddItemToCartRequest;
import com.hari.electronic.store.dtos.CartDto;
import com.hari.electronic.store.entities.Cart;
import com.hari.electronic.store.entities.CartItem;
import com.hari.electronic.store.entities.Product;
import com.hari.electronic.store.entities.User;
import com.hari.electronic.store.exceptions.BadApiRequest;
import com.hari.electronic.store.exceptions.ResourceNotFoundException;
import com.hari.electronic.store.repositories.CartItemRepository;
import com.hari.electronic.store.repositories.CartRepository;
import com.hari.electronic.store.repositories.ProductRepository;
import com.hari.electronic.store.repositories.UserRepository;
import com.hari.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if(quantity <= 0) {
            throw new BadApiRequest("Requested quantity is not valid!!");
        }

        //fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with given id doesn't exist."));

        //fetch the user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id doesn't exist."));

        Cart cart = null;

        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        //perform cart operations:
        //if cart itmes already present: then update
        AtomicReference<Boolean> updated=new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();

        List<CartItem> updatedItems = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                //item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                updated.set(true);
            }

            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedItems);


        //create items
        if(!updated.get()){
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItem);
        }

        cart.setUser(user);
        Cart savedCart = cartRepository.save(cart);

        return mapper.map(savedCart, CartDto.class);


    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart item doesn't exist."));
        cartItemRepository.delete(cartItem1);

    }

    @Override
    public void clearCart(String userId) {
        //fetch the user from db:
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id doesn't exist."));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCaryByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id doesn't exist."));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found"));
        return mapper.map(cart, CartDto.class);
    }
}
