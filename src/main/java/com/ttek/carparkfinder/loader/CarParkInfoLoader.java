package com.ttek.carparkfinder.loader;


import com.ttek.carparkfinder.gps.LatLonCoordinate;
import com.ttek.carparkfinder.gps.SVY21Coordinate;
import com.ttek.carparkfinder.model.ParkingLotDetails;
import com.ttek.carparkfinder.repository.ParkingLotDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Component
@Slf4j
public class CarParkInfoLoader {

  @Autowired
  ResourceLoader resourceLoader;
  //service k through
  @Autowired
  ParkingLotDetailsRepository parkingLotDetailsRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void loadCarParkDetailsFromCsv() {
    if (!parkingLotDetailsRepository.findAll().isEmpty()) {
      log.info("Car park details dataset already loaded. Skipping loading process.");
      return;
    }

    log.info("Starting car park details dataset loading.");

    try {
      Resource resource = resourceLoader.getResource("classpath:/HDBCarparkInformation.csv");
      ArrayList<ParkingLotDetails> datasets = new ArrayList<>();

      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(resource.getInputStream()));
          CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

        for (CSVRecord csvRecord : csvParser) {
          ParkingLotDetails carPark = parseCarParkDetails(csvRecord);
          datasets.add(carPark);
        }

        parkingLotDetailsRepository.saveAll(datasets);
        log.info("Car park details dataset loaded successfully.");
      } catch (Exception e) {
        log.error("Error occurred while parsing CSV records.", e);
        throw new RuntimeException("Error occurred while parsing CSV records.", e);
      }
    } catch (Exception e) {
      log.error("Error occurred while loading the car park details dataset.", e);
      throw new RuntimeException("Error occurred while loading the car park details dataset.", e);
    }
  }

  private ParkingLotDetails parseCarParkDetails(CSVRecord csvRecord) {
    double xCoord = Double.parseDouble(csvRecord.get("x_coord"));
    double yCoord = Double.parseDouble(csvRecord.get("y_coord"));
    LatLonCoordinate latLon = convertSVY21ToLatLon(xCoord, yCoord);

    return new ParkingLotDetails(
        csvRecord.get("car_park_no"),
        csvRecord.get("address"),
        latLon.getLatitude(),
        latLon.getLongitude(),
        csvRecord.get("car_park_type"),
        csvRecord.get("type_of_parking_system"),
        csvRecord.get("short_term_parking"),
        csvRecord.get("free_parking"),
        csvRecord.get("night_parking"),
        Long.valueOf(csvRecord.get("car_park_decks")),
        Double.valueOf(csvRecord.get("gantry_height")),
        csvRecord.get("car_park_basement")
    );
  }

  private LatLonCoordinate convertSVY21ToLatLon(double xCoord, double yCoord) {
    SVY21Coordinate svy21Coordinate = new SVY21Coordinate(xCoord, yCoord);
    return svy21Coordinate.asLatLon();
  }
}
