package com.example.wineydomain.shop.repository;

import com.example.wineydomain.shop.entity.Shop;
import com.example.wineydomain.shop.entity.ShopType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import javax.validation.constraints.NotNull;

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

    @Query(value = "SELECT"
        + "    S.id AS shopId,"
        + "    S.shopType AS shopType, "
        + "    ST_X(S.point) AS latitude, ST_Y(S.point) AS longitude, "
        + "    ST_DISTANCE_SPHERE(ST_SRID(POINT(:longitude, :latitude), 4326), S.point)  AS meter, \n"
        + "    IF((SELECT EXISTS (SELECT * FROM ShopBookMark SBM WHERE SBM.userId = :userId AND S.id = SBM.shopId)), 'true', 'false') AS 'bookMark',\n"
        + "    (SELECT GROUP_CONCAT(SM.mood SEPARATOR ',') FROM ShopMood SM WHERE SM.shopId = S.id) AS shopMoods, "
        + "    S.name, S.businessHour, S.address, S.imgUrl, S.phone \n"
        + "FROM\n"
        + "    Shop S JOIN ShopBookMark BM ON S.id = BM.shopId and BM.userId = :userId \n"
        + "WHERE\n"
        + " MBRContains(\n"
        + " ST_GeomFromText(\n"
        + " CONCAT(\n"
        + " 'Polygon((',\n"
        + " :leftTopLatitude, ' ', :leftTopLongitude, ', ',\n"
        + " :leftTopLatitude, ' ', :rightBottomLongitude, ', ',\n"
        + " :rightBottomLatitude, ' ', :rightBottomLongitude, ', ',\n"
        + " :rightBottomLatitude, ' ', :leftTopLongitude, ', ',\n"
        + " :leftTopLatitude, ' ', :leftTopLongitude,\n"
        + " '))'\n"
        + " ),\n"
        + " 4326\n"
        + " ),\n"
        + "S.point\n"
        + " )\n"
        + "ORDER BY\n"
        + "    meter ASC;\n",
        nativeQuery = true)
    List<ShopMapList> getLikeShopMapLists(
        @Param("userId") Long userId,
        @Param("latitude") double latitude,
        @Param("longitude") double longitude,
        @Param("leftTopLatitude") double leftTopLatitude,
        @Param("leftTopLongitude") double leftTopLongitude,
        @Param("rightBottomLatitude") double rightBottomLatitude,
        @Param("rightBottomLongitude") double rightBottomLongitude);

    @Query(value = "SELECT"
        + "    S.id AS shopId,"
        + "    S.shopType AS shopType, "
        + "    ST_X(S.point) AS latitude, ST_Y(S.point) AS longitude, "
        + "    ST_DISTANCE_SPHERE(ST_SRID(POINT(:longitude, :latitude), 4326), S.point)  AS meter, \n"
        + "    IF((SELECT EXISTS (SELECT * FROM ShopBookMark SBM WHERE SBM.userId = :userId AND S.id = SBM.shopId)), 'true', 'false') AS 'bookMark',\n"
        + "    (SELECT GROUP_CONCAT(SM.mood SEPARATOR ',') FROM ShopMood SM WHERE SM.shopId = S.id) AS shopMoods, "
        + "    S.name, S.businessHour, S.address, S.imgUrl, S.phone \n"
        + "FROM\n"
        + "    Shop S\n"
        + "WHERE\n"
        + " MBRContains(\n"
        + " ST_GeomFromText(\n"
        + " CONCAT(\n"
        + " 'Polygon((',\n"
        + " :leftTopLatitude, ' ', :leftTopLongitude, ', ',\n"
        + " :leftTopLatitude, ' ', :rightBottomLongitude, ', ',\n"
        + " :rightBottomLatitude, ' ', :rightBottomLongitude, ', ',\n"
        + " :rightBottomLatitude, ' ', :leftTopLongitude, ', ',\n"
        + " :leftTopLatitude, ' ', :leftTopLongitude,\n"
        + " '))'\n"
        + " ),\n"
        + " 4326\n"
        + " ),\n"
        + "S.point\n"
        + " )\n"
        + "ORDER BY\n"
        + "    meter ASC;\n",
            nativeQuery = true)
    List<ShopMapList> getShopMapLists(
        @Param("userId") Long userId,
        @Param("latitude") double latitude,
        @Param("longitude") double longitude,
        @Param("leftTopLatitude") double leftTopLatitude,
        @Param("leftTopLongitude") double leftTopLongitude,
        @Param("rightBottomLatitude") double rightBottomLatitude,
        @Param("rightBottomLongitude") double rightBottomLongitude);

    @Query(value = "SELECT"
        + "    S.id AS shopId,"
        + "    S.shopType AS shopType, "
        + "    ST_X(S.point) AS latitude, ST_Y(S.point) AS longitude, "
        + "    ST_DISTANCE_SPHERE(ST_SRID(POINT(:longitude, :latitude), 4326), S.point) AS meter, \n"
        + "    IF((SELECT EXISTS (SELECT * FROM ShopBookMark SBM WHERE SBM.userId = :userId AND S.id = SBM.shopId)), 'true', 'false') AS 'bookMark',\n"
        + "    (SELECT GROUP_CONCAT(SM.mood SEPARATOR ',') FROM ShopMood SM WHERE SM.shopId = S.id) AS shopMoods, "
        + "    S.name, S.businessHour, S.address, S.imgUrl, S.phone \n"
        + "FROM\n"
        + "    Shop S\n"
        + "WHERE\n"
        + " MBRContains(\n"
        + " ST_GeomFromText(\n"
        + " CONCAT(\n"
        + " 'Polygon((',\n"
        + " :leftTopLatitude, ' ', :leftTopLongitude, ', ',\n"
        + " :leftTopLatitude, ' ', :rightBottomLongitude, ', ',\n"
        + " :rightBottomLatitude, ' ', :rightBottomLongitude, ', ',\n"
        + " :rightBottomLatitude, ' ', :leftTopLongitude, ', ',\n"
        + " :leftTopLatitude, ' ', :leftTopLongitude,\n"
        + " '))'\n"
        + " ),\n"
        + " 4326\n"
        + " ),\n"
        + "S.point\n"
        + " ) AND S.shopType = :shopType\n"
        + "ORDER BY\n"
        + "    meter ASC;\n",
        nativeQuery = true)
    List<ShopMapList> getFilterShopMapLists(
        @Param("userId") Long userId,
        @Param("shopType") String shopType,
        @Param("latitude") double latitude,
        @Param("longitude") double longitude,
        @Param("leftTopLatitude") double leftTopLatitude,
        @Param("leftTopLongitude") double leftTopLongitude,
        @Param("rightBottomLatitude") double rightBottomLatitude,
        @Param("rightBottomLongitude") double rightBottomLongitude);
    interface ShopMapList {
        Long getShopId();

        boolean getBookMark();

        double getLatitude();

        double getLongitude();

        String getName();

        String getBusinessHour();

        String getAddress();

        String getImgUrl();

        String getPhone();

        ShopType getShopType();

        String getShopMoods();

        double getMeter();
    }
}
