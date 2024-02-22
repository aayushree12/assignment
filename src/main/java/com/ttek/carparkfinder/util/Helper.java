package com.ttek.carparkfinder.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Helper {

  private static final double EARTH_RADIUS = 6371.0; // Earth radius in kilometers

  public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    // Convert latitude and longitude from degrees to radians
    double radLat1 = Math.toRadians(lat1);
    double radLon1 = Math.toRadians(lon1);
    double radLat2 = Math.toRadians(lat2);
    double radLon2 = Math.toRadians(lon2);

    // Calculate differences
    double deltaLat = radLat2 - radLat1;
    double deltaLon = radLon2 - radLon1;

    // Haversine formula
    double a =
        Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(
            Math.sin(deltaLon / 2), 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    // Calculate distance
    return EARTH_RADIUS * c;
  }

  public static boolean isValidCoordinate(Double latitude, Double longitude) {
    return (latitude != null && !String.valueOf(latitude).trim().isEmpty()) &&
        (longitude != null && !String.valueOf(longitude).trim().isEmpty());
  }


}
