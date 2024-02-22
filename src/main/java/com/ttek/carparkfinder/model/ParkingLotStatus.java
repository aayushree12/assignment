package com.ttek.carparkfinder.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parking_lot_status")
public class ParkingLotStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "car_park_details")
  private ParkingLotDetails parkingLotDetails;  // Reference to CarParkDetails entity

  @Column(name = "lots_available")
  private int lotsAvailable;

  @Column(name = "total_lots")
  private int totalLots;
}
