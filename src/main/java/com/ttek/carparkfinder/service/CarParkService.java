package com.ttek.carparkfinder.service;

import com.ttek.carparkfinder.dto.CarParkDto;
import com.ttek.carparkfinder.response.CarParkResponse;
import com.ttek.carparkfinder.model.ParkingLotDetails;
import com.ttek.carparkfinder.model.ParkingLotStatus;
import com.ttek.carparkfinder.repository.ParkingLotDetailsRepository;
import com.ttek.carparkfinder.repository.ParkingLotStatusRepository;
import com.ttek.carparkfinder.util.Helper;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarParkService {

  @Autowired
  private ParkingLotStatusRepository parkingLotStatusRepository;

  public List<CarParkResponse> getNearestCarParks(double latitude, double longitude, int pageNo,
      int perPage) {
    try {
      log.info("Fetching nearest car parks...");
      List<ParkingLotStatus> availableCarParks = parkingLotStatusRepository.findByLotsAvailableGreaterThan(
          0);
      List<CarParkDto> carParkDtos = calculateDistancesAndMapToDTOs(availableCarParks, latitude,
          longitude);

      List<CarParkResponse> response = convertToResponse(carParkDtos);

      int start = (pageNo - 1) * perPage;
      int end = Math.min(start + perPage, response.size());

      log.info("Fetching nearest car parks successful.");
      return response.subList(start, end);
    } catch (Exception e) {
      log.error("An error occurred while fetching nearest car parks.", e);
      throw new RuntimeException("An error occurred while fetching nearest car parks.", e);
    }
  }

  private List<CarParkDto> calculateDistancesAndMapToDTOs(List<ParkingLotStatus> availableCarParks,
      double latitude, double longitude) {
    return availableCarParks.stream().map(parkingLotStatus -> {
      ParkingLotDetails parkingLotDetails = parkingLotStatus.getParkingLotDetails();
      if (Objects.isNull(parkingLotDetails)) {
        throw new RuntimeException(
            "Car park details not found for availability: " + parkingLotStatus.getId());
      }
      double distance = Helper.calculateDistance(latitude, longitude, parkingLotDetails.getXCoord(),
          parkingLotDetails.getYCoord());
      return new CarParkDto(parkingLotDetails.getAddress(), parkingLotDetails.getXCoord(),
          parkingLotDetails.getYCoord(), parkingLotStatus.getTotalLots(),
          parkingLotStatus.getLotsAvailable(), distance);
    }).sorted(Comparator.comparingDouble(CarParkDto::getDistance)).collect(Collectors.toList());
  }

  private List<CarParkResponse> convertToResponse(List<CarParkDto> carParkDtos) {
    return carParkDtos.stream().map(carParkDto -> {
      CarParkResponse carParkResponse = new CarParkResponse();
      carParkResponse.setAddress(carParkDto.getAddress());
      carParkResponse.setLatitude(carParkDto.getLatitude());
      carParkResponse.setLongitude(carParkDto.getLongitude());
      carParkResponse.setAvailableLots(carParkDto.getAvailableLots());
      carParkResponse.setTotalLots(carParkDto.getTotalLots());
      return carParkResponse;
    }).collect(Collectors.toList());
  }
}
