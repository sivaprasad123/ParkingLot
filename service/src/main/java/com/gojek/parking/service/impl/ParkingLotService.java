package com.gojek.parking.service.impl;

import com.gojek.parking.commons.model.Vehicle;
import com.gojek.parking.commons.utils.LogUtils;
import com.gojek.parking.service.IParkingLotService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by siva on 08/10/17.
 */
public class ParkingLotService implements IParkingLotService {

  private static ParkingLotService parkingLotService = null;
  private List<Integer> vacantSlots = null;
  private Set<String> parkedVehiclesRegistrationNumSet = null;
  private TreeMap<Integer, Vehicle> parkingLot = null;
  private Integer maxNumOfVehicles = null;
  private Boolean hasCreatedParkingLot = false;

  /**
   * This will be creating parking lot with given size and initializes all the variables.
   * @param maxNumOfVehicles
   */
  @Override
  public void createParkingLot(Integer maxNumOfVehicles) {
    if (null != maxNumOfVehicles && maxNumOfVehicles > 0) {
      this.maxNumOfVehicles = maxNumOfVehicles;
      parkingLot = new TreeMap<>();
      vacantSlots = new ArrayList<>();
      parkedVehiclesRegistrationNumSet = new HashSet<>();
      for (int i = 0; i < maxNumOfVehicles; i++) {
        vacantSlots.add(i + 1);
      }
      hasCreatedParkingLot = true;
      LogUtils.toConsole("Created a parking lot with " + maxNumOfVehicles + " slots");
    } else {
      LogUtils.toConsole(
          "Unable to create parking lot. Enter positive number for creating parking lot");
    }
  }

  /**
   * This will get the near by parkingSlot If parkingSlot is not full
   * then parks the vehicle in given slot.
   * @param vehicle
   */
  @Override
  public void park(Vehicle vehicle) {
    if (hasCreatedParkingLot) {
      if (hasVehicleNotParked(vehicle)) {
        if (isParkingLotNotFull()) {
          Integer parkingSlot = getNearByParkingSlot();
          parkingLot.put(parkingSlot, vehicle);
          parkedVehiclesRegistrationNumSet.add(vehicle.getRegistrationNum());
          vacantSlots.remove(parkingSlot);
          LogUtils.toConsole("Allocated slot number: " + parkingSlot);
        } else {
          LogUtils.toConsole("Sorry, parking lot is full");
        }
      } else {
        LogUtils.toConsole(
            "Vehicle with Registration Num " + vehicle.getRegistrationNum() + " already parked");
      }
    } else {
      LogUtils.toConsole("ParkingLot is not yet created");
    }
  }

  /**
   *Vacates the slot by slot id.
   * It will check whether ParkingLot is created or not
   * If yes then checks whether slot exist or not.
   * if yes then removes the slot from parkingLot and add this slot id to vacantSlots collection.
   * Finally sorts the vacantSlots collections to get the nearby slots.
   * @param slotNum
   */
  @Override
  public void leave(Integer slotNum) {
    if (hasCreatedParkingLot) {
      if (parkingLot.containsKey(slotNum)) {
        Vehicle vehicle = parkingLot.get(slotNum);
        parkingLot.remove(slotNum);
        parkedVehiclesRegistrationNumSet.remove(vehicle.getRegistrationNum());
        vacantSlots.add(slotNum);
        Collections.sort(vacantSlots);
        LogUtils.toConsole("Slot number " + slotNum + " is free");
      } else if (isValidSlotNumber(slotNum)) {
        LogUtils.toConsole("Parking slot is already vacant");
      } else {
        LogUtils.toConsole("Parking slot not exist");
      }
    } else {
      LogUtils.toConsole("ParkingLot is not yet created");
    }
  }

  /**
   * Prints all the vehicles slotNumber, registrationNum and vehicle color.
   * It will check whether ParkingLot is created or not.
   * If yes then iterate all the parking lot map and prints the slotNumber, registrationNum and vehicle color
   *
   */
  @Override
  public void status() {
    if (hasCreatedParkingLot) {
      Iterator<Integer> iterator = parkingLot.keySet().iterator();
      LogUtils.toConsole("Slot No" + "\t" + "Registration No." + "\t" + "Colour");
      while (iterator.hasNext()) {
        Integer slotNumber = iterator.next();
        Vehicle vehicle = parkingLot.get(slotNumber);
        LogUtils
            .toConsole(slotNumber + "\t" + vehicle.getRegistrationNum() + "\t" + vehicle
                .getColor());
      }
    } else {
      LogUtils.toConsole("ParkingLot is not yet created");
    }
  }

  /**
   * Returns registrationNumbers by color.
   * It will check whether ParkingLot is created or not.
   * If yes then iterate all the parking lot map and checks if the color matches then returns comma separated registration numbers.
   * @param color
   */

  @Override
  public void getRegistrationNumbersByColour(String color) {
    if (hasCreatedParkingLot) {
      String registrationNumbers = "";
      Iterator<Integer> iterator = parkingLot.keySet().iterator();
      while (iterator.hasNext()) {
        Integer slotNumber = iterator.next();
        Vehicle vehicle = parkingLot.get(slotNumber);
        if (vehicle.getColor().equalsIgnoreCase(color)) {
          registrationNumbers = registrationNumbers + vehicle.getRegistrationNum() + ", ";
        }
      }
      if (registrationNumbers.length() > 0) {
        LogUtils.toConsole(registrationNumbers.substring(0, registrationNumbers.length() - 2));
      } else {
        LogUtils.toConsole("There are no registration numbers with color " + color);
      }
    } else {
      LogUtils.toConsole("ParkingLot is not yet created");
    }
  }

  /***
   * Returns Slot numbers by color.
   * It will check whether ParkingLot is created or not.
   * If yes then iterate all the parking lot map and checks if the color matches then returns comma separated slot numbers.
   *
   * @param color
   */
  @Override
  public void getSlotNumbersByColor(String color) {
    if (hasCreatedParkingLot) {
      String slotNumbers = "";
      Iterator<Integer> iterator = parkingLot.keySet().iterator();
      while (iterator.hasNext()) {
        Integer slotNumber = iterator.next();
        Vehicle vehicle = parkingLot.get(slotNumber);
        if (vehicle.getColor().equalsIgnoreCase(color)) {
          slotNumbers = slotNumbers + slotNumber + ", ";
        }
      }
      if (slotNumbers.length() > 0) {
        LogUtils.toConsole(slotNumbers.substring(0, slotNumbers.length() - 2));
      } else {
        LogUtils.toConsole("There are no slot numbers with color " + color);
      }
    } else {
      LogUtils.toConsole("ParkingLot is not yet created");
    }
  }

  /**
   * This function will return the slot number by registrationNum number.
   * It will check whether ParkingLot is created or not.
   * If it is created then iterate parkingLot map and returns the slot number if it exists else returns "Not found"
   * @param registrationNum
   */
  @Override
  public void getSlotNumberByRegistrationNum(String registrationNum) {
    if (hasCreatedParkingLot) {
      Iterator<Integer> iterator = parkingLot.keySet().iterator();
      while (iterator.hasNext()) {
        Integer slotNumber = iterator.next();
        Vehicle vehicle = parkingLot.get(slotNumber);
        if (vehicle.getRegistrationNum().equalsIgnoreCase(registrationNum)) {
          LogUtils.toConsole(slotNumber.toString());
          return;
        }
      }
      LogUtils.toConsole("Not found");
    } else {
      LogUtils.toConsole("ParkingLot is not yet created");
    }
  }

  /**
   * This method is used to create the singleton object of ParkingLotService synchronized helps to
   * prevent creating multiple instances in multi threaded env
   */
  public static ParkingLotService getParkingLotService() {
    if (null == parkingLotService) {
      synchronized (ParkingLotService.class) {
        if (null == parkingLotService) {
          parkingLotService = new ParkingLotService();
        }
      }
    }
    return parkingLotService;
  }

  private Integer getNearByParkingSlot() {
    return vacantSlots.get(0);
  }

  private Boolean hasVehicleParked(Vehicle vehicle) {
    return parkedVehiclesRegistrationNumSet.contains(vehicle.getRegistrationNum());
  }

  private Boolean hasVehicleNotParked(Vehicle vehicle) {
    return !(hasVehicleParked(vehicle));
  }

  private Boolean isParkingLotNotFull() {
    return !(vacantSlots.size() == 0);
  }

  private Boolean isValidSlotNumber(Integer slotNum) {
    return slotNum > 0 && slotNum <= maxNumOfVehicles;
  }
}
