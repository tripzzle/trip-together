package com.tgd.trip.global.util;

public record Coordinate(double latitude, double longitude) {

    private static final double EARTH_RADIUS = 6371;

    public static double getHaversineDistance(Coordinate first, Coordinate second) {
        double lat = Math.toRadians(first.latitude() - second.latitude());
        double lng = Math.toRadians(first.longitude() - second.longitude());

        double dist = Math.sin(lat / 2) * Math.sin(lat / 2)
                + Math.cos(Math.toRadians(first.latitude())) * Math.cos(Math.toRadians(second.latitude()))
                * Math.sin(lng / 2) * Math.sin(lng / 2);

        return 2 * Math.atan2(Math.sqrt(dist), Math.sqrt(1 - dist)) * EARTH_RADIUS;
    }

    public Pair<Coordinate> getSquareCoordinate(double height, double width) {
        // 경도 변화량 계산 (단위: 도)
        double longitudeDelta = Math.toDegrees(width / EARTH_RADIUS / Math.cos(Math.toRadians(longitude)));

        // 위도 변화량 계산 (단위: 도)
        double latitudeDelta = Math.toDegrees(height / EARTH_RADIUS);
        Delta delta = new Delta(longitudeDelta, latitudeDelta);

        return new Pair<>(
                new Coordinate(latitude - delta.longitudeDelta(), longitude - delta.latitudeDelta()),
                new Coordinate(latitude + delta.longitudeDelta(), longitude + delta.latitudeDelta())
        );
    }

    private record Delta(double longitudeDelta, double latitudeDelta) {
    }
}