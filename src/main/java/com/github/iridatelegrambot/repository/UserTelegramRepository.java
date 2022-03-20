package com.github.iridatelegrambot.repository;

import com.github.iridatelegrambot.entity.UserTelegram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTelegramRepository extends JpaRepository<UserTelegram,Long> {

    List<UserTelegram> findAllByActiveTrue();

    List<UserTelegram> findAllByActiveFalse();
}
