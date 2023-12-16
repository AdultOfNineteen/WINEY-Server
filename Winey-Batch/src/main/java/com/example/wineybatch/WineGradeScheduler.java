package com.example.wineybatch;

import com.example.wineydomain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.example.wineydomain", "com.example.wineybatch"})
public class WineGradeScheduler {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 9 1 * ?") // 매월 1일 오전 9시
    @Transactional
    public void runUpdateWineGrade() {
        userRepository.updateWineGradeForAllUsers();
    }
}