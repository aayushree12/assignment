package com.ttek.carparkfinder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class CarParkAvailabilityItem {

  @JsonProperty("timestamp")
  private String timestamp;
  @JsonProperty("carpark_data")
  private List<CarParkDataDto> carparkData;
}
