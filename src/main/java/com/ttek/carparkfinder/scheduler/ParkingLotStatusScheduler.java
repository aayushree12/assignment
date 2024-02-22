package com.ttek.carparkfinder.scheduler;

import com.ttek.carparkfinder.model.ParkingLotStatus;
import com.ttek.carparkfinder.model.ParkingLotDetails;
import com.ttek.carparkfinder.repository.ParkingLotStatusRepository;
import com.ttek.carparkfinder.repository.ParkingLotDetailsRepository;
import com.ttek.carparkfinder.dto.CarParkAvailabilityApiResponse;
import com.ttek.carparkfinder.dto.CarParkAvailabilityItem;
import com.ttek.carparkfinder.dto.CarParkDataDto;
import com.ttek.carparkfinder.dto.CarParkInfoDto;
import com.ttek.carparkfinder.service.CarParkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class ParkingLotStatusScheduler {

  private final String CAR_PARK_AVAILABILITY_API = "https://api.data.gov.sg/v1/transport/carpark-availability";
  Logger logger = LoggerFactory.getLogger(ParkingLotStatusScheduler.class);
  @Autowired
  private CarParkService carParkService;
  @Autowired
  private ParkingLotDetailsRepository parkingLotDetailsRepository;
  @Autowired
  private ParkingLotStatusRepository parkingLotStatusRepository;

  @Scheduled(cron = "${process.sync.cron.dateTime}")
  public void updateParkingLotStatus() {
    try {
      logger.info("Scheduler running for car park availability");

      RestTemplate restTemplate = new RestTemplate();
      CarParkAvailabilityApiResponse response = restTemplate.getForObject(CAR_PARK_AVAILABILITY_API,
          CarParkAvailabilityApiResponse.class);

      if (response != null && response.getItems() != null) {
        Map<String, Integer> totalLotsMap = new HashMap<>();
        Map<String, Integer> availableLotsMap = new HashMap<>();

        for (CarParkAvailabilityItem item : response.getItems()) {
          for (CarParkDataDto carParkDataDto : item.getCarparkData()) {
            String carparkNumber = carParkDataDto.getCarparkNumber();

            for (CarParkInfoDto carParkInfoDto : carParkDataDto.getCarParkDetails()) {
              int totalLots = carParkInfoDto.getTotalLots();
              int availableLots = carParkInfoDto.getAvailableLots();

              totalLotsMap.merge(carparkNumber, totalLots, Integer::sum);
              availableLotsMap.merge(carparkNumber, availableLots, Integer::sum);
            }
          }
        }

        for (String carparkNumber : totalLotsMap.keySet()) {
          int totalLots = totalLotsMap.get(carparkNumber);
          int availableLots = availableLotsMap.get(carparkNumber);

          saveLotsInfoIntoDatabase(carparkNumber, totalLots, availableLots);
        }
      }

      logger.info("Parking availability updated");
    } catch (Exception e) {
      logger.error("An error occurred while updating parking lot data: {}", e.getMessage());
    }
  }

  private void saveLotsInfoIntoDatabase(String carparkNumber, int totalLots, int availableLots) {
    try {
      ParkingLotDetails parkingLotDetails = parkingLotDetailsRepository.findByCarParkNo(
          carparkNumber);

      if (parkingLotDetails != null) {
        ParkingLotStatus parkingLotStatus = parkingLotStatusRepository.findByParkingLotDetails(
            parkingLotDetails);
        if (parkingLotStatus != null) {
          parkingLotStatus.setLotsAvailable(availableLots);
          parkingLotStatus.setTotalLots(totalLots);
          parkingLotStatusRepository.save(parkingLotStatus);
        } else {
          parkingLotStatus = new ParkingLotStatus();
          parkingLotStatus.setLotsAvailable(availableLots);
          parkingLotStatus.setTotalLots(totalLots);
          parkingLotStatus.setParkingLotDetails(parkingLotDetails);
          parkingLotStatusRepository.save(parkingLotStatus);
        }
      } else {
        logger.warn("Car park details not found for car park number: {}", carparkNumber);
      }
    } catch (Exception e) {
      logger.error("Failed to save data in database: {}", e.getMessage());
    }
  }
}
