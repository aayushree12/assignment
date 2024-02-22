package com.ttek.carparkfinder.repository;

import com.ttek.carparkfinder.model.ParkingLotStatus;
import com.ttek.carparkfinder.model.ParkingLotDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotStatusRepository extends JpaRepository<ParkingLotStatus, Long> {

  List<ParkingLotStatus> findByLotsAvailableGreaterThan(int lotsAvailable);
  ParkingLotStatus findByParkingLotDetails(ParkingLotDetails parkingLotDetails);

}
