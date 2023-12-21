package com.example.wineydomain.tastingNote;


import com.example.wineydomain.common.model.Status;
import com.example.wineydomain.config.DomainTestConfig;
import com.example.wineydomain.tastingNote.entity.TastingNote;
import com.example.wineydomain.tastingNote.repository.TastingNoteRepository;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.repository.UserRepository;
import com.example.wineydomain.wine.entity.Wine;
import com.example.wineydomain.wine.repository.WineRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = DomainTestConfig.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TastingNoteRepositoryTest {

    @Autowired
    private TastingNoteRepository tastingNoteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WineRepository wineRepository;

    @Test
    @DisplayName("직전 3개월 노트 작성 수 조회 테스트")
    void testCountByUserAndCreatedAtAfter() {
        // given
        // 사용자 엔티티를 생성하고 저장
        User user =  User.builder()
                .profileImgUrl("")
                .password("")
                .socialId("")
                .nickName("")
                .username("")
                .socialType(SocialType.KAKAO)
                .level(1)
                .status(Status.INACTIVE)
                .isTastingNoteAnalyzed(false)
                .build();

        Wine wine = Wine.builder().build();
        wineRepository.save(wine);

        // user 필드들을 적절히 설정
        userRepository.save(user);

        // TastingNote 엔티티를 생성하고 저장
        TastingNote note0 = TastingNote.builder().build();
        note0.setUser(user);
        note0.setWine(wine);
        note0.setCreatedAt(LocalDateTime.now().minusDays(10)); // 10일 전에 생성된 노트
        tastingNoteRepository.save(note0);

        // TastingNote 엔티티를 생성하고 저장
        TastingNote note1 = TastingNote.builder().build();
        note1.setUser(user);
        note1.setWine(wine);
        note1.setCreatedAt(LocalDateTime.now().minusDays(29)); // 29일 전에 생성된 노트
        tastingNoteRepository.save(note1);

        TastingNote note2 = TastingNote.builder().build();
        note2.setUser(user);
        note2.setWine(wine);
        note2.setCreatedAt(LocalDateTime.now().minusDays(100)); // 100일 전에 생성된 노트
        tastingNoteRepository.save(note2);

        // when
        // 3달 전 이후에 생성된 노트의 수를 확인
        Integer count = tastingNoteRepository.countByUserAndCreatedAtAfter(user, LocalDateTime.now().minus(Period.ofMonths(3)));

        // then
        assertEquals(2, count); // note0, note1만 해당 기간에 생성됨
    }
}