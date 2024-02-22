package com.ttek.carparkfinder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParkDataDto {

  @JsonProperty("carpark_info")
  private List<CarParkInfoDto> carParkDetails;
  @JsonProperty("carpark_number")
  private String carparkNumber;
  @JsonProperty("update_datetime")
  private String updateDatetime;

}
