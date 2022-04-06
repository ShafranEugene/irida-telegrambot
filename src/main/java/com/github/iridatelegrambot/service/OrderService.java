package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void save(Order order);

    List<Order> getAllOrdersActive();

    List<Order> getAllOrders();

    Optional<Order> getOrderById(int Id);

}
