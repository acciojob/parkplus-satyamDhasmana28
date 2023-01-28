package com.driver.services.impl;

import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.driver.model.*;

import java.util.ArrayList;
import java.util.List;

import static com.driver.model.SpotType.*;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot newParkingLot = new ParkingLot();
        newParkingLot.setName(name);
        newParkingLot.setAddress(address);
        parkingLotRepository1.save(newParkingLot);
        return newParkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        Spot newSpot = new Spot();
        newSpot.setPricePerHour(pricePerHour);
        newSpot.setOccupied(false);
        newSpot.setParkingLot(parkingLot);
        if(numberOfWheels<=2){
            newSpot.setSpotType(TWO_WHEELER);
        } else if (numberOfWheels<=4) {
            newSpot.setSpotType(FOUR_WHEELER);
        }else{
            newSpot.setSpotType(OTHERS);
        }
        parkingLot.getSpotList().add(newSpot);

        parkingLotRepository1.save(parkingLot);

        return newSpot;
    }

    @Override
    public void deleteSpot(int spotId) {
        Spot spot = spotRepository1.findById(spotId).get();
        spot.getParkingLot().getSpotList().remove(spot);
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Spot spot = spotRepository1.findById(spotId).get();
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        parkingLot.getSpotList().remove(spot);

        spot.setPricePerHour(pricePerHour);
        parkingLot.getSpotList().add(spot);

        spotRepository1.save(spot);
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
      parkingLotRepository1.deleteById(parkingLotId);
    }
}
