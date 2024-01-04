package com.example.wineydomain.badge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Badge {
    YOUNG_SOUMELIER("YOUNG_SOUMELIER","소믈리에 배지","YOUNG 소믈리에", "와인 소믈리에 중 첫번째 등급이에요. WINEY의 와인 소믈리에가 되신 걸 축하해요!", 1),
    INTERMEDIATE_SOUMELIER("INTERMEDIATE_SOUMELIER","소믈리에 배지", "INTERMEDIATE 소믈리에", "와인 소믈리에 중 두번째 등급이에요. 어느덧 테이스팅 노트를 50개나 작성하셨네요!", 50),

    ADVANCED_SOUMELIER("ADVANCED_SOUMELIER", "소믈리에 배지","ADVANCED 소믈리에", "와인 소믈리에 중 세번째 등급이에요. 테이스팅 노트가 500개를 돌파했어요! 이대로 MASTER까지 도전해볼까요?", 500),
    MASTER_SOUMELIER("MASTER_SOUMELIER", "소믈리에 배지","MASTER 소믈리에", "와인 소믈리에 중 네번째이자 가장 높은 등급이에요. WINEY의 MASTER 소믈리에인 당신, 진짜 소믈리에를 도전해보는건 어떤가요?", 1000),

    // 활동 배지
    SMELL_ENTHUSIAST("SMELL_ENTHUSIAST","활동 배지","향의 매력", "노트를 작성할 때 와인의 향을 신경쓰시네요. 역시 와인을 진정으로 즐기고 음미할 줄 아시는군요!", 10),
    FAVORITE_WINE("FAVORITE_WINE", "활동 배지", "내가 좋아하는 와인","같은 와인을 3번이나 마셨어요! 나의 취향에 맞는 와인을 찾은 걸 축하해요.", 3),
    GRAPE_LOVER("GRAPE_LOVER","활동 배지","포도에 빠지다", "내가 평소에 마시는 와인의 품종이 계속 겹치다면, 그 품종의 맛이 내 취향일 가능성이 높아요!", 5),
    ENJOYMENT("ENJOYMENT","활동 배지","마시는 재미", "테이스팅 노트 10개를 작성했어요. 테이스팅 노트를 작성하며 와인에 흥미가 생겼길 바라요!", 10),
    WINE_EXCITEMENT("WINE_EXCITEMENT","활동 배지","와인의 설렘", "일주일 동안 하루도 빠짐없이 WINEY에 접속했어요! 와인의 재미를 알기 시작했네요.", 7),
    WINE_ADDICT("WINE_ADDICT","활동 배지","와인 홀릭", "한 달 동안 하루도 빠짐없이 WINEY에 접속했어요! 와인의 매력에 푹 빠졌네요.", 30),
    TASTE_DISCOVERY("TASTE_DISCOVERY","활동 배지","취향의 발견", "나의 와인 취향을 알아보셨군요! 취향에 맞는 와인을 찾아 마셔볼까요?", 1),
    NON_ALCOHOLIC("NON_ALCOHOLIC","활동 배지","주류보다는 비주류", "레드, 화이트 뿐만 아니라 이제는 다양한 와인을 맛보는 그대! 앞으로는 더욱 다양한 와인과 함께 WINEY를 즐겨봐요.", 1);

    private final String badge;
    private final String type;
    private final String badgeName;
    private final String badgeDescription;
    private final int requiredActivity;
}
