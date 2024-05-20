package com.example.wineycommon.constants;

import java.util.List;

import org.springframework.data.redis.connection.RedisServer;

public class WineyStatic {
    public static final String BEARER = "Bearer ";

    public static final String AUTHORIZATION_HEADER = "X-AUTH-TOKEN";

    public static final String REFRESH_TOKEN_HEADER = "X-REFRESH-TOKEN";


    public static final Integer SRID = 4326;

    public static final double EARTH_RADIUS = 6371.01;

    public static final Integer ONE_EIGHTY = 180;

    public static final Integer THREE_SIXTY = 360;

    public static final Integer FIVE_FORTY = 540;

    public static final Integer YOUNG_SOMMELIER_INDEX = 0;

    public static final Integer INTERMEDIATE_SOMMELIER_INDEX = 1;

    public static final Integer ADVANCED_SOMMELIER_INDEX = 2;

    public static final Integer MASTER_SOMMELIER_INDEX = 3;

    public static final Integer SMELL_ENTHUSIAST_INDEX = 4;

    public static final Integer FAVORITE_WINE_INDEX = 5;

    public static final Integer GRAPE_LOVER_INDEX = 6;

    public static final Integer ENJOYMENT_INDEX = 7;

    public static final Integer WINE_EXCITEMENT_INDEX = 8;

    public static final Integer WINE_ADDICT_INDEX = 9;

    public static final String TASTE_DISCOVERY = "TASTE_DISCOVERY";

    public static final Integer NON_ALCOHOLIC_INDEX = 11;

    public static final List<String> IGNORE_METHODS = List.of(new String[]{"healthCheck", ""});
    public static final String BOOKMARK_CANCEL_MESSAGE = "북마크 취소";
    public static final String BOOKMARK_REGISTER_MESSAGE = "북마크 등록";

    public static final String VERIFICATION_MESSAGE_PREFIX = "[WINEY]\n인증번호 : ";

    public static final int VERIFICATION_MESSAGE_NUMBER_LENGTH = 6;

    public static final int VERIFICATION_MESSAGE_EXPIRE_AT = 5;

    public static final String DEFAULT_RANDOM_NICKNAME = "하품하는 와이니";
}
