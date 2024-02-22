/*
Test generated by RoostGPT for test Aayushree-Test using AI Type Open AI and AI Model gpt-4

Test Scenario 1: Positive Scenario - Same Object
Test the function by passing the same object for comparison. The function should return true as it's the same object.

Test Scenario 2: Negative Scenario - Null Object
Test the function by passing a null object for comparison. The function should return false as the object is null.

Test Scenario 3: Negative Scenario - Different Class Object
Test the function by passing an object of a different class for comparison. The function should return false as the class of the object does not match.

Test Scenario 4: Negative Scenario - Different Latitude
Test the function by passing an object of the same class, but with a different latitude for comparison. The function should return false as the latitude of the object does not match.

Test Scenario 5: Negative Scenario - Different Longitude
Test the function by passing an object of the same class, but with a different longitude for comparison. The function should return false as the longitude of the object does not match.

Test Scenario 6: Positive Scenario - Same Latitude and Longitude
Test the function by passing an object of the same class with the same latitude and longitude for comparison. The function should return true as both the latitude and longitude match with the object.

Test Scenario 7: Negative Scenario - Different Latitude and Longitude
Test the function by passing an object of the same class, but with different latitude and longitude for comparison. The function should return false as both the latitude and longitude of the object do not match.
*/
package com.ttek.carparkfinder.gps;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LatLonCoordinate_equals_039c844c47_Test {

    @Test
    public void testEquals_SameObject() {
        LatLonCoordinate latLon1 = new LatLonCoordinate(1.2, 3.4);
        assertTrue(latLon1.equals(latLon1));
    }

    @Test
    public void testEquals_NullObject() {
        LatLonCoordinate latLon1 = new LatLonCoordinate(1.2, 3.4);
        assertFalse(latLon1.equals(null));
    }

    @Test
    public void testEquals_DifferentClassObject() {
        LatLonCoordinate latLon1 = new LatLonCoordinate(1.2, 3.4);
        String otherClassObj = "Test String";
        assertFalse(latLon1.equals(otherClassObj));
    }

    @Test
    public void testEquals_DifferentLatitude() {
        LatLonCoordinate latLon1 = new LatLonCoordinate(1.2, 3.4);
        LatLonCoordinate latLon2 = new LatLonCoordinate(4.5, 3.4);
        assertFalse(latLon1.equals(latLon2));
    }

    @Test
    public void testEquals_DifferentLongitude() {
        LatLonCoordinate latLon1 = new LatLonCoordinate(1.2, 3.4);
        LatLonCoordinate latLon2 = new LatLonCoordinate(1.2, 4.5);
        assertFalse(latLon1.equals(latLon2));
    }

    @Test
    public void testEquals_SameLatitudeLongitude() {
        LatLonCoordinate latLon1 = new LatLonCoordinate(1.2, 3.4);
        LatLonCoordinate latLon2 = new LatLonCoordinate(1.2, 3.4);
        assertTrue(latLon1.equals(latLon2));
    }

    @Test
    public void testEquals_DifferentLatitudeLongitude() {
        LatLonCoordinate latLon1 = new LatLonCoordinate(1.2, 3.4);
        LatLonCoordinate latLon2 = new LatLonCoordinate(4.5, 5.6);
        assertFalse(latLon1.equals(latLon2));
    }
}