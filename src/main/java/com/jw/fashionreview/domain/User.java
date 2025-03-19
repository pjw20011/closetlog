package com.jw.fashionreview.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // 로그인 ID

    @Column(nullable = false)
    private String password; // 암호화 비밀번호

    @Column(nullable = false)
    private String nickname; // 닉네임

    @Column(nullable = false)
    private String phoneNumber; // 전화번호

    @Column(nullable = false)
    private String email;

    private LocalDateTime createdAt = LocalDateTime.now();


}
