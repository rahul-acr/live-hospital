package org.social.it.domain.vo;

import java.util.Objects;

import static java.lang.Math.*;

public class Coordinate {
    public static final int EARTH_RADIUS_IN_KM = 6371;
    private final double latitude, longitude;

    private Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private static double square(double number) {
        return pow(number, 2);
    }

    public static Coordinate createFromDegrees(double latitude, double longitude) {
        return new Coordinate(toRadians(latitude), toRadians(longitude));
    }

    public static Coordinate createFromRadians(double latitude, double longitude) {
        return new Coordinate(latitude, longitude);
    }

    /**
     * Calculates distance from two points as per Haversine formula
     *
     * @param otherCoordinate second co-ordinate
     * @return distance in kilometers
     */
    public double distanceFrom(Coordinate otherCoordinate) {
        double h = sqrt(square(sin((this.latitude - otherCoordinate.latitude) / 2))
                + cos(this.latitude) * cos(otherCoordinate.latitude) * square(sin((this.longitude - otherCoordinate.longitude) / 2)));
        return 2 * EARTH_RADIUS_IN_KM * asin(h);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0;
    }

    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
