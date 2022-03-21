package com.github.iridatelegrambot.repository;


import com.github.iridatelegrambot.entity.UserTelegram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTelegramRepositoryIT {
    @Autowired
    private UserTelegramRepository userTelegramRepository;

    @Sql(scripts = {"/sql/delete_table.sql","/sql/users_telegram.sql"})
    @Test
    void shouldProperlyFindAllActiveUsers(){
        //when
        List<UserTelegram> users = userTelegramRepository.findAllByActiveTrue();

        //then
        Assertions.assertEquals(6,users.size());
    }

    @Sql(scripts = {"/sql/delete_table.sql"})
    @Test
    void shouldProperlySaveUser(){
        //given
        UserTelegram user = new UserTelegram();
        user.setChatId(100000010L);
        user.setActive(true);
        userTelegramRepository.save(user);

        //when
        Optional<UserTelegram> userFromDB = userTelegramRepository.findById(100000010L);

        //then
        Assertions.assertTrue(userFromDB.isPresent());
        Assertions.assertEquals(user,userFromDB.get());
    }
}
