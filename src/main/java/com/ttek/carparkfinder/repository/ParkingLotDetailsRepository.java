package com.ttek.carparkfinder.repository;

import com.ttek.carparkfinder.model.ParkingLotDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotDetailsRepository extends JpaRepository<ParkingLotDetails, Long> {

  ParkingLotDetails findByCarParkNo(String carParkNo);

}
