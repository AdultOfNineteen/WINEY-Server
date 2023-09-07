package com.example.wineydomain.tastingNote.entity;

import com.example.wineydomain.common.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@AllArgsConstructor
public enum SmellKeyword {
    FRUIT("FRUIT","FRUIT"),
    BERRY("FRUIT","BERRY"),
    LEMONANDLIME("FRUIT","LEMONANDLIME"),
    APPLEPEAR("FRUIT","APPLEPEAR"),
    PEACHPLUM("FRUIT","PEACHPLUM"),
    TROPICALFRUIT("FRUIT","TROPICALFRUIT"),
    FLOWER("NATURAL", "FLOWER"),
    GRASSWOOD("NATURAL", "GRASSWOOD"),
    HERB("NATURAL","HERB"),
    OAK("OAK","OAK"),
    SPICE("OAK","SPICE"),
    NUTS("OAK","NUTS"),
    VANILLA("OAK","VANILLA"),
    CARAMELCHOCOLATE("OAK","CARAMELCHOCOLATE"),
    FLINT("OTHER","FLINT"),
    BREAD("OTHER", "BREAD"),
    RUBBER("OTHER","RUBBER"),
    EARTHASH("OTHER","EARTASH"),
    MEDICINE("OTHER", "MEDICNE");

   private final String type;

   private final String value;
}
