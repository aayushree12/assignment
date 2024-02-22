package com.ttek.carparkfinder.controller;

import com.ttek.carparkfinder.exception.MissingCoordinatesException;
import com.ttek.carparkfinder.response.CarParkResponse;
import com.ttek.carparkfinder.service.CarParkService;
import com.ttek.carparkfinder.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/carparks")
public class CarParkController {

  @Autowired
  private CarParkService carParkService;

  @GetMapping("/nearest")
  public ResponseEntity<?> getNearestCarParks(@RequestParam Double latitude,
      @RequestParam Double longitude,
      @RequestParam int page, @RequestParam int perPage) {
    if (!Helper.isValidCoordinate(latitude, longitude)) {
      log.warn("Latitude and longitude parameters are missing.");
      throw new IllegalArgumentException("Latitude and longitude are required parameters.");
    }
    try {
      List<CarParkResponse> nearestCarParkDetails = carParkService.getNearestCarParks(latitude,
          longitude,
          page, perPage);
      log.info("Successfully fetched nearest available car parks");
      return new ResponseEntity<>(nearestCarParkDetails, HttpStatus.OK);
    } catch (Exception e) {
      log.error("An error occurred while processing the request.", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
          "An error occurred while processing the request.");
    }
  }
}
