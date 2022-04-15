package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrdersActive() {
        return orderRepository.findAllByStatusActiveTrue();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        Order order = getOrderById(id).get();
        orderRepository.delete(order);
    }
}
