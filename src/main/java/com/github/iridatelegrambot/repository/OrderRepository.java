package com.github.iridatelegrambot.repository;

import com.github.iridatelegrambot.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    List<Order> findAllByStatusActiveTrue();
}
