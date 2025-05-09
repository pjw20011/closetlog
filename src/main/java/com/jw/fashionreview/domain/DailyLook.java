package com.jw.fashionreview.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class DailyLook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // User 엔티티 연결

    private String imageUrl;

    @Column(length = 500)
    private String comment;

    private boolean isPublic;

    private LocalDate createdAt;
}
