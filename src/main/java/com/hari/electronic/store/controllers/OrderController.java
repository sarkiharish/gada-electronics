package com.hari.electronic.store.controllers;

import com.hari.electronic.store.dtos.ApiResponseMessage;
import com.hari.electronic.store.dtos.CreateOrderRequest;
import com.hari.electronic.store.dtos.OrderDto;
import com.hari.electronic.store.dtos.PageableResponse;
import com.hari.electronic.store.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        OrderDto order = orderService.createOrder(createOrderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
        ApiResponseMessage orderDeletedSuccessfully = ApiResponseMessage.builder().message("Order deleted successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(orderDeletedSuccessfully, HttpStatus.OK);
    }

    //get orders of the user
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderOfUser(@PathVariable String userId) {
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getOrderOfUser(

            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
