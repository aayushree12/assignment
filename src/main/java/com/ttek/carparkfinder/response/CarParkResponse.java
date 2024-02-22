package com.ttek.carparkfinder.response;

import lombok.Data;

@Data
public class CarParkResponse {

  private String address;
  private double latitude;
  private double longitude;
  private int totalLots;
  private int availableLots;

}
