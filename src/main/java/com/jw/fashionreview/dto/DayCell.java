package com.jw.fashionreview.dto;

import com.jw.fashionreview.domain.DailyLook;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DayCell {
    private LocalDate date;
    private DailyLook dailyLook;  // null이면 그날 데일리룩 없음
}
