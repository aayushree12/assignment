package com.ttek.carparkfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParkDto {

    private String address;
    private Double latitude;
    private Double longitude;
    private int totalLots;
    private int availableLots;
    private Double distance;
}