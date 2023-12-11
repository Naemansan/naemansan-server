package org.naemansan.courseapi.utility;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;
import org.naemansan.courseapi.domain.Course;
import org.naemansan.courseapi.dto.common.LocationDto;
import org.naemansan.courseapi.dto.persistent.CourseLocationsPersistent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseUtil {
    private static final GeometryFactory geometryFactory = new GeometryFactory();
    private static final double X_mean = 37.554812;
    private static final double Y_mean = 126.988204;
    private static final double X_std = Math.sqrt(0.0031548);
    private static final double Y_std = Math.sqrt(0.00720859);

    public static Point location2Point(LocationDto location) {
        return geometryFactory.createPoint(new Coordinate(location.longitude(), location.latitude()));
    }

    public static LocationDto point2Location(Point point) {
        return new LocationDto(point.getCoordinate().getY(), point.getCoordinate().getX());
    }

    public static MultiPoint locations2MultiPoint(List<LocationDto> locations) {
        Point[] points = new Point[locations.size()];

        for (int i = 0; i < locations.size(); i++) {
            LocationDto location = locations.get(i);
            points[i] = geometryFactory.createPoint(new Coordinate(location.longitude(), location.latitude()));
        }

        return geometryFactory.createMultiPoint(points);
    }

    public static List<LocationDto> multiPoint2Locations(MultiPoint multiPoint) {
        List<LocationDto> locations = new ArrayList<>();
        for (int i = 0; i < multiPoint.getNumGeometries(); i++) {
            locations.add(new LocationDto(multiPoint.getGeometryN(i).getCoordinate().getY(),
                    multiPoint.getGeometryN(i).getCoordinate().getX()));
        }

        return locations;
    }

    public static double calculateDistanceSum(List<LocationDto> locations) {
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

    public static List<Long> analyzeSimilarity(
            List<LocationDto> inputLocations,
            List<Course> courses) {

        if (courses.isEmpty()) {
            return List.of();
        }

        // MultiPoint to List<LocationDto>
        List<CourseLocationsPersistent> courseLocations = courses.stream()
                .map(course -> new CourseLocationsPersistent(
                        course.getId(),
                        multiPoint2Locations(course.getLocations())))
                .toList();

        // Standardization
        List<StandardLocation> standardInputLocation = inputLocations.stream()
                .map(StandardLocation::of)
                .toList();

        List<List<StandardLocation>> standardCourseLocations = courseLocations.stream()
                .map(courseLocation -> courseLocation.locations().stream()
                        .map(StandardLocation::of)
                        .toList())
                .toList();

        // 비슷한 코스 찾기
        Map<Long, Double> similarityScores = new HashMap<>();
        for (int i = 0; i < standardCourseLocations.size(); i++) {
            // 길이 조정
            Map<String, List<StandardLocation>> adjustedLocations = adjustLocationLength(
                    standardInputLocation,
                    standardCourseLocations.get(i));

            // 원점 조정
            Map<String, List<StandardLocation>> originAdjustedLocations = adjustToOrigin(
                    adjustedLocations.get("inputLocations"),
                    adjustedLocations.get("courseLocations"));

            List<StandardLocation> adjustedInputLocations = new ArrayList<>(originAdjustedLocations.get("originAdjustInputLocations"));
            List<StandardLocation> adjustedCourseLocations = new ArrayList<>(originAdjustedLocations.get("originAdjustCourseLocations"));

            // 첫번째 위치 제거(유사도 검사시 첫번째 위치는 같은 값이라서 필요 없음)
            adjustedInputLocations.remove(0);
            adjustedCourseLocations.remove(0);

            // 유사도 검사
            double similarityScore = Math.abs(calculateSimilarityScore(adjustedInputLocations, adjustedCourseLocations));
            System.out.println("similarityScore = " + similarityScore);

            if (similarityScore > 0.95) {
                similarityScores.put(courseLocations.get(i).id(), similarityScore);
            }
        }

        return similarityScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
    }

    private record StandardLocation(
            double latitude,
            double longitude
    ) {
        public static StandardLocation of(LocationDto location) {
            return new StandardLocation(
                    (location.latitude() - X_mean) / X_std,
                    (location.longitude() - Y_mean) / Y_std);
        }

        @Override
        public String toString() {
            return String.format("%f,%f", longitude, latitude);
        }
    }

    private static double calculateDistance(LocationDto locationOne, LocationDto locationTwo) {
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

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    private static Map<String, List<StandardLocation>> adjustLocationLength(
            List<StandardLocation> inputLocations,
            List<StandardLocation> courseLocations) {
        int N = inputLocations.size();
        int M = courseLocations.size();
        int K = Math.min(N, M);
        int Q = Math.max(N, M);
        List<StandardLocation> vector;
        if (K == N) {
            vector = courseLocations;
        } else {
            vector = inputLocations;
        }

        List<Integer> selectedIndices = new ArrayList<>();
        selectedIndices.add(0);
        for (int i = 1; i < K - 1; i++) {
            int idx = ((i / (K - 1)) * (Q - 1)) + 1;
            selectedIndices.add(idx);
        }

        selectedIndices.add(K - 1);

        List<StandardLocation> transformedVector = new ArrayList<>();
        for (Integer selectedIndex : selectedIndices) {
            transformedVector.add(vector.get(selectedIndex));
        }

        if (N == M) {
            return Map.of("inputLocations", inputLocations, "courseLocations", courseLocations);
        } else if (N > M) {
            return Map.of("inputLocations", transformedVector, "courseLocations", courseLocations);
        } else {
            return Map.of("inputLocations", transformedVector, "courseLocations", inputLocations);
        }
    }

    // 원점 조정
    private static Map<String, List<StandardLocation>> adjustToOrigin(List<StandardLocation> inputLocations, List<StandardLocation> courseLocations) {
        StandardLocation originDifference = new StandardLocation(
                0.0 - inputLocations.get(0).latitude(),
                0.0 - inputLocations.get(0).longitude());

        StandardLocation originCourseDifference = new StandardLocation(
                0.0 - courseLocations.get(0).latitude(),
                0.0 - courseLocations.get(0).longitude());

        return Map.of(
                "originAdjustInputLocations", inputLocations.stream()
                        .map(standardLocation -> new StandardLocation(
                                standardLocation.latitude() + originDifference.latitude(),
                                standardLocation.longitude() + originDifference.longitude()))
                        .toList(),
                "originAdjustCourseLocations", courseLocations.stream()
                        .map(standardLocation -> new StandardLocation(
                                standardLocation.latitude() + originCourseDifference.latitude(),
                                standardLocation.longitude() + originCourseDifference.longitude()))
                        .toList());
    }

    private static double calculateSimilarityScore(List<StandardLocation> inputLocations, List<StandardLocation> courseLocations) {
        RealVector inputVector = convertToRealVectorList(inputLocations);
        RealVector courseVector = convertToRealVectorList(courseLocations);

        return cosineSimilarity(inputVector, courseVector);
    }

    private static double cosineSimilarity(RealVector vector1, RealVector vector2) {
        double dotProduct = vector1.dotProduct(vector2);
        double norm1 = vector1.getNorm();
        double norm2 = vector2.getNorm();

        if (norm1 != 0.0 && norm2 != 0.0) {
            return dotProduct / (norm1 * norm2);
        } else {
            return 0.0;
        }
    }

    private static RealVector convertToRealVectorList(List<StandardLocation> locations) {
        double[] data = new double[locations.size() * 2];
        int index = 0;

        for (StandardLocation location : locations) {
            data[index] = location.latitude();
            data[index + 1] = location.longitude();
            index += 2;
        }

        return new ArrayRealVector(data);
    }
}
