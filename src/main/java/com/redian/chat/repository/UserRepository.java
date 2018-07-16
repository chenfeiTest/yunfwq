package com.redian.chat.repository;

import com.redian.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByOpenId(String openId);
}
