package com.hari.electronic.store.dtos;

import com.hari.electronic.store.entities.OrderItem;
import com.hari.electronic.store.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderDto {

    private String orderId;
    private String orderStatus = "PENDING";
    private String paymentStatus = "NOT PAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderedDate = new Date();
    private Date deliveredDate;

//    private UserDto user;

    private List<OrderItem> orderItems = new ArrayList<>();
}
