package com.ttek.carparkfinder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParkInfoDto {

  @JsonProperty("total_lots")
  private int totalLots;
  @JsonProperty("lot_type")
  private String lotType;
  @JsonProperty("lots_available")
  private int availableLots;

}
