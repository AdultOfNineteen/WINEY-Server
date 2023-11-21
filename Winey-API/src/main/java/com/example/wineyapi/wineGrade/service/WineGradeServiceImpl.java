package com.example.wineyapi.wineGrade.service;

import com.example.wineyapi.wineGrade.converter.WineGradeConverter;
import com.example.wineyapi.wineGrade.dto.WineGradeResponse;
import com.example.wineycommon.exception.NotFoundException;
import com.example.wineydomain.common.WineGrade;
import com.example.wineydomain.tastingNote.repository.TastingNoteRepository;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.exception.UserErrorCode;
import com.example.wineydomain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Period;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WineGradeServiceImpl implements WineGradeService {

    private final UserRepository userRepository;
    private final TastingNoteRepository tastingNoteRepository;

    @Override
    public WineGradeResponse.GetWineGradeDTO getWineGrade(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(UserErrorCode.NOT_EXIST_USER));
        WineGrade currentGrade = user.getWineGrade();
        Integer threeMonthsNoteCount = tastingNoteRepository.countByUserAndCreatedAtAfter(user, getThreeMonthsAgo());
        WineGrade nextGrade = calculateNextWineGrade(threeMonthsNoteCount);
        return WineGradeConverter.toGetWineGradeDTO(currentGrade, nextGrade, threeMonthsNoteCount);
    }

    private WineGrade calculateNextWineGrade(Integer threeMonthsNoteCount) {
        for (WineGrade grade : WineGrade.values()) {
            if (threeMonthsNoteCount >= grade.getMinCount() && threeMonthsNoteCount <= grade.getMaxCount()) {
                return grade;
            }
        }
        throw new IllegalArgumentException("적합한 와인 등급을 찾을 수 없습니다.");
    }


    private LocalDateTime getThreeMonthsAgo() {
        // 현재 LocalDateTime을 가져옵니다.
        LocalDateTime now = LocalDateTime.now();

        // 3개월을 나타내는 Period를 생성합니다.
        Period threeMonths = Period.ofMonths(3);

        // 현재로부터 3개월 전의 날짜와 시간을 계산합니다.
        return now.minus(threeMonths);
    }
}
