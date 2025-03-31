package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.User;

import java.util.Optional;

public interface UserService {
    void register(User user);

    Optional<User> findByUsername(String username);
}
