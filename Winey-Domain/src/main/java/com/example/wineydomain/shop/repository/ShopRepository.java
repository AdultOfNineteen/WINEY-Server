package com.example.wineydomain.shop.repository;

import com.example.wineydomain.shop.entity.Shop;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Modifying
    @Query(value = "UPDATE Shop SET point = ST_GeomFromText(:wktPoint, 4326) WHERE id = :id", nativeQuery = true)
    void updateShopPoint(@Param("wktPoint") String wktPoint, @Param("id") Long id);

}
