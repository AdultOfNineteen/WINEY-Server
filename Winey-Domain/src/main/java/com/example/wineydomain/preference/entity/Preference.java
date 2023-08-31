package com.example.wineydomain.preference.entity;

import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "`Preference`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Preference extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // todo : 기획 확정되면 필드들 추가
    /*
    a.평소 선호 입맛을 기반으로 좋아하는 와인을 추천해주기 위한 정보 수집으로 필수 가입절차이다.
    b. 제시되어 있는 2가지 선택지 중 하나만 선택가능하며, 선택과 동시에 다음 화면으로 이동한다.
    c. 입력된 정보는 아래의 데이터 정책을 따라 [오늘의 추천와인]에서의 적합한 와인 노출을 위한 데이터로 사용된다

    [데이터 정책]
    1)초콜릿 : 당도와 산도를 의미
    a. 밀크초콜릿 : 당도 4, 산도 2 설정
    b. 다크초콜릿 : 당도 2, 산도 4 설정
    2)커피 : 바디를 의미
    a. 아메리카노 : 바디 2 설정
    b. 카페 라떼 : 바디 4 설정
    3)과일 : 탄닌을 의미
    a. 복숭아 : 탄닌 4 설정
    b. 파인애플 : 탄닌 2 설정
     */

    /**
     * 당도
     */
    @Column(name = "sweetness")
    private Integer sweetness;

    /**
     * 산도
     */
    @Column(name = "acidity")
    private Integer acidity;

    /**
     * 바디
     */
    @Column(name = "body")
    private Integer body;

    /**
     * 탄닌
     */
    @Column(name = "tannins")
    private Integer tannins;


    /**
     * 양방향 연관관계 편의 메소드입니다.
     * @param user - 유저 엔티티
     */
    public void setUser(User user) {
        if (this.user != null) {
            this.user.setPreference(null);
        }

        this.user = user;

        if (user != null) {
            user.setPreference(this);
        }
    }

}