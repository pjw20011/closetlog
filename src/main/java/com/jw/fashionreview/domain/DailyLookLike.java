package com.jw.fashionreview.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "dailylook_like")
public class DailyLookLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DailyLook dailyLook;

    @ManyToOne
    private User user;

    private LocalDateTime likedAt;
}
