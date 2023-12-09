package org.naemansan.courseapi.utility;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseUtil {
    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public Point location2Point(LocationDto location) {
        return geometryFactory.createPoint(new Coordinate(location.longitude(), location.latitude()));
    }

    public LocationDto point2Location(Point point) {
        return new LocationDto(point.getCoordinate().getY(), point.getCoordinate().getX());
    }

    public MultiPoint locations2MultiPoint(List<LocationDto> locations) {
        Point[] points = new Point[locations.size()];

        for (int i = 0; i < locations.size(); i++) {
            LocationDto location = locations.get(i);
            points[i] = geometryFactory.createPoint(new Coordinate(location.longitude(), location.latitude()));
        }

        return geometryFactory.createMultiPoint(points);
    }

    public List<LocationDto> multiPoint2Locations(MultiPoint multiPoint) {
        List<LocationDto> locations = new ArrayList<>();
        for (int i = 0; i < multiPoint.getNumGeometries(); i++) {
            locations.add(new LocationDto(multiPoint.getGeometryN(i).getCoordinate().getY(),
                    multiPoint.getGeometryN(i).getCoordinate().getX()));
        }

        return locations;
    }

    public double calculateDistanceSum(List<LocationDto> locations) {
        LocationDto locationOne = null;
        LocationDto locationTwo = null;
        double distance = 0.0;
        for (LocationDto pointDto : locations) {
            locationOne = locationTwo;
            locationTwo = pointDto;
            distance += calculateDistance(locationOne, locationTwo);
        }

        return distance;
    }

    private double calculateDistance(LocationDto locationOne, LocationDto locationTwo) {
        if (locationOne == null || locationTwo == null) {
            return 0.0;
        }

        double theta = locationOne.longitude() - locationTwo.longitude();
        double distance = Math.sin(deg2rad(locationOne.latitude())) * Math.sin(deg2rad(locationTwo.latitude()))
                + Math.cos(deg2rad(locationOne.latitude())) * Math.cos(deg2rad(locationTwo.latitude()))
                * Math.cos(deg2rad(theta));

        distance = Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance * 60 * 1.1515 * 1609.344;
        return distance;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
