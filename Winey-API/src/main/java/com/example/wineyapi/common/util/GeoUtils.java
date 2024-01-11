package com.example.wineyapi.common.util;

import static com.example.wineycommon.constants.WineyStatic.*;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import com.example.wineyapi.common.dto.Location;

public class GeoUtils {
    public static Point createPoint(double latitude, double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

    public static Location calculate(Double baseLatitude, Double baseLongitude, Double distance,
        Double bearing) {
        Double radianLatitude = toRadian(baseLatitude);
        Double radianLongitude = toRadian(baseLongitude);
        Double radianAngle = toRadian(bearing);
        Double distanceRadius = distance / EARTH_RADIUS;

        Double latitude = Math.asin(sin(radianLatitude) * cos(distanceRadius) +
            cos(radianLatitude) * sin(distanceRadius) * cos(radianAngle));
        Double longitude = radianLongitude + Math.atan2(sin(radianAngle) * sin(distanceRadius) *
            cos(radianLatitude), cos(distanceRadius) - sin(radianLatitude) * sin(latitude));

        longitude = normalizeLongitude(longitude);
        return new Location(toDegree(latitude), toDegree(longitude));
    }

    private static Double toRadian(Double coordinate) {
        return coordinate * Math.PI / ONE_EIGHTY;
    }

    private static Double toDegree(Double coordinate) {
        return coordinate * ONE_EIGHTY / Math.PI;
    }

    private static Double sin(Double coordinate) {
        return Math.sin(coordinate);
    }

    private static Double cos(Double coordinate) {
        return Math.cos(coordinate);
    }

    private static Double normalizeLongitude(Double longitude) {
        return (longitude + FIVE_FORTY) % THREE_SIXTY - ONE_EIGHTY;
    }
}
