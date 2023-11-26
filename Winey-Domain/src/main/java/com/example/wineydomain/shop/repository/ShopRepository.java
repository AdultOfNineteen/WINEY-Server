package com.example.wineydomain.shop.repository;

import com.example.wineydomain.shop.entity.Shop;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long>, ShopCustomRepository {

    @Modifying
    @Query(value = "UPDATE Shop SET point = ST_PointFromText(:wktPoint, 4326) WHERE id = :id", nativeQuery = true)
    void updateShopPoint(@Param("wktPoint") String wktPoint, @Param("id") Long id);

    @Query(value = "SELECT \n" +
            "    S.id AS 'shopId', \n" +
            "    ST_DISTANCE_SPHERE(ST_SRID(POINT(:longitude, :latitude), 4326), S.point)/1000 AS meter,\n" +
            "    IF((\n" +
            "        SELECT EXISTS (\n" +
            "            SELECT *\n" +
            "            FROM ShopBookMark SBM\n" +
            "            WHERE SBM.userId = :userId\n" +
            "            AND S.id = SBM.shopId\n" +
            "        )\n" +
            "    ), 'true', 'false') AS 'likes',\n" +
            "    S.name AS 'name' \n" +
            "FROM Shop S ORDER BY meter ASC",
            nativeQuery = true)
    List<ShopMapList> getAllShopMapLists(@Param("userId") Long userId, @Param("latitude") double latitude,@Param("longitude") double longitude);

    @Query(value = "SELECT \n" +
            "    S.id AS 'shopId', \n" +
            "    ST_DISTANCE_SPHERE(ST_SRID(POINT(:longitude, :latitude), 4326), S.point)/1000 AS meter,\n" +
            "    IF((\n" +
            "        SELECT EXISTS (\n" +
            "            SELECT *\n" +
            "            FROM ShopBookMark SBM\n" +
            "            WHERE SBM.userId = :userId\n" +
            "            AND S.id = SBM.shopId\n" +
            "        )\n" +
            "    ), 'true', 'false') AS 'likes',\n" +
            "    S.name AS 'name' \n" +
            "FROM Shop S JOIN ShopBookMark M on S.id = M.shopId WHERE M.userId = :userId ORDER BY meter ASC",
            nativeQuery = true)
    List<ShopMapList> getLikeShopMapLists(@Param("userId") Long userId, @Param("latitude") double latitude,@Param("longitude") double longitude);

    @Query(value = "SELECT \n" +
            "    S.id AS 'shopId', \n" +
            "    ST_DISTANCE_SPHERE(ST_SRID(POINT(:longitude, :latitude), 4326), S.point)/1000 AS meter,\n" +
            "    IF((\n" +
            "        SELECT EXISTS (\n" +
            "            SELECT *\n" +
            "            FROM ShopBookMark SBM\n" +
            "            WHERE SBM.userId = :userId\n" +
            "            AND S.id = SBM.shopId\n" +
            "        )\n" +
            "    ), 'true', 'false') AS 'likes',\n" +
            "    S.name AS 'name' \n" +
            "FROM Shop S  WHERE S.shopType = :shopFilter ORDER BY meter ASC",
            nativeQuery = true)
    List<ShopMapList> getFilterShopMapLists(@Param("userId") Long userId, @Param("latitude") double latitude,@Param("longitude") double longitude, @Param("shopFilter") String shopFilter);

    @Query(value = "SELECT " +
            "    S.id AS 'shopId', " +
            "    ST_DISTANCE_SPHERE(ST_SRID(POINT(:longitude, :latitude), 4326), S.point)/1000 AS meter, " +
            "    IF((SELECT EXISTS (SELECT * FROM ShopBookMark SBM WHERE SBM.userId = :userId AND S.id = SBM.shopId)), 'true', 'false') AS 'likes', " +
            "    S.name AS 'name' " +
            "FROM Shop S WHERE MBRContains(ST_GeomFromText(CONCAT('Polygon((', " +
            " :leftTopLongitude, ' ', :leftTopLatitude, ', ', " +
            " :rightBottomLongitude, ' ', :leftTopLatitude, ', ', " +
            " :rightBottomLongitude, ' ', :rightBottomLatitude, ', ', " +
            " :leftTopLongitude, ' ', :rightBottomLatitude, ', ', " +
            " :leftTopLongitude, ' ', :leftTopLatitude, '))'), 4326), S.point) ORDER BY meter ASC",
            nativeQuery = true)
    List<ShopMapList> getShopMapLists(@Param("userId") Long userId, @Param("latitude") double latitude, @Param("longitude") double longitude,
                                      @Param("leftTopLatitude") double leftTopLatitude, @Param("leftTopLongitude") double leftTopLongitude,
                                      @Param("rightBottomLatitude") double rightBottomLatitude, @Param("rightBottomLongitude") double rightBottomLongitude);

    interface ShopMapList {
        Long getShopId();

        boolean getLikes();

        String getName();

        double getMeter();
    }
}
