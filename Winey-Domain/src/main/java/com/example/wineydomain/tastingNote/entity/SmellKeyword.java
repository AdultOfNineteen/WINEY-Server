package com.example.wineydomain.tastingNote.entity;

import com.example.wineydomain.common.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@AllArgsConstructor
public enum SmellKeyword {
    FRUIT("FRUIT","FRUIT","과일향"),
    BERRY("FRUIT","BERRY", "베리류"),
    LEMONANDLIME("FRUIT","LEMONANDLIME","레몬/라임"),
    APPLEPEAR("FRUIT","APPLEPEAR","사과/배"),
    PEACHPLUM("FRUIT","PEACHPLUM","복숭아/자두"),
    TROPICALFRUIT("FRUIT","TROPICALFRUIT","열대과일"),
    FLOWER("NATURAL", "FLOWER","꽃향"),
    GRASSWOOD("NATURAL", "GRASSWOOD","풀/나무"),
    HERB("NATURAL","HERB","허브향"),
    OAK("OAK","OAK","오크향"),
    SPICE("OAK","SPICE","향신료"),
    NUTS("OAK","NUTS","견과류"),
    VANILLA("OAK","VANILLA","바닐라"),
    CARAMELCHOCOLATE("OAK","CARAMELCHOCOLATE","케러멜/초콜릿"),
    FLINT("OTHER","FLINT","부싯돌"),
    BREAD("OTHER", "BREAD","빵"),
    RUBBER("OTHER","RUBBER","고무"),
    EARTHASH("OTHER","EARTASH","흙/재"),
    MEDICINE("OTHER", "MEDICNE","약품");

   private final String type;

   private final String value;

   private final String name;
}
