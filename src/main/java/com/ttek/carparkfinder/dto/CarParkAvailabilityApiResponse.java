package com.ttek.carparkfinder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParkAvailabilityApiResponse {

  @JsonProperty("items")
  private List<CarParkAvailabilityItem> items;
}
