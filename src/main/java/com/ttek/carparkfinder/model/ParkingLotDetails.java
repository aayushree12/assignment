package com.ttek.carparkfinder.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parking_lot_details")
public class ParkingLotDetails {

  @Id
  private String carParkNo;

  @Column(nullable = false)
  private String address;

  @Column(name = "x_coord", nullable = false)
  private Double xCoord;

  @Column(name = "y_coord", nullable = false)
  private Double yCoord;

  @Column
  private String carParkType;

  @Column
  private String typeOfParkingSystem;

  @Column
  private String shortTermParking;

  @Column
  private String freeParking;

  @Column
  private String nightParking;

  @Column
  private Long carParkDecks;

  @Column
  private Double gantryHeight;

  @Column
  private String carParkBasement;

}
