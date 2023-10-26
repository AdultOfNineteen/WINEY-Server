package com.example.wineyapi.common.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class TimeUtils {

    public boolean checkNowDate(LocalDateTime updatedAt) {
        LocalDate nowDate = LocalDateTime.now().toLocalDate();
        LocalDate targetDate = updatedAt.toLocalDate();

        System.out.println("checkNowDate : " + nowDate.isEqual(targetDate));
        return nowDate.isEqual(targetDate);
    }

    public boolean checkOneDayBefore(LocalDateTime updatedAt) {
        LocalDate nowDate = LocalDateTime.now().toLocalDate();
        LocalDate targetDate = updatedAt.toLocalDate();

        return targetDate.isEqual(nowDate.minusDays(1));
    }

}
