package com.jw.fashionreview.repository;

import com.jw.fashionreview.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<Object> findByNickname(String nickname);
}
