package com.driver.repository;

import com.driver.model.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer>{

    @Query(value = "SELECT * FROM spot WHERE parkingLotId=?1 AND occupied=false AND  ",
          nativeQuery = true)
    List<Spot> getAvailableSpotListFromParking(Integer parkingLotId, Integer numberOfWheels);
}
