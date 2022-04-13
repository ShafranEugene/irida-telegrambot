package com.github.iridatelegrambot.test.repository;


import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryIT {
    @Autowired
    private OrderRepository orderRepository;

    @Sql(scripts = {"/sql/delete_table.sql"})
    @Test
    void shouldProperlySaveOrder(){
        //given
        Order order = new Order();
        order.setNumber("1000");
        order.setStatusActive(true);
        orderRepository.save(order);

        //when
        List<Order> orders = orderRepository.findAllByStatusActiveTrue();
        Order orderOfDatabase = orders.get(0);

        //then
        Assertions.assertEquals(order,orderOfDatabase);
    }

    @Sql(scripts = {"/sql/delete_table.sql","/sql/orders.sql"})
    @Test
    void shouldProperlyFindAllActiveOrders(){
        //when
        List<Order> orders = orderRepository.findAllByStatusActiveTrue();

        //then
        Assertions.assertEquals(3,orders.size());
    }
}
