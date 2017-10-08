package com.gojek.parking.service;

import com.gojek.parking.commons.model.Vehicle;
import java.util.List;

/**
 * Created by siva on 05/10/17.
 */
public interface IParkingLotService {

  public void createParkingLot(Integer maxNumOfVehicles);

  public void park(Vehicle vehicle);

  public void leave(Integer slotNum);

  public void status();

  public void getRegistrationNumbersByColour(String color);

  public void getSlotNumbersByColor(String color);

  public void getSlotNumberByRegistrationNum(String registrationNum);
}
