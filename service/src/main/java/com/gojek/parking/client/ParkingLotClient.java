package com.gojek.parking.client;

import com.gojek.parking.commons.model.Vehicle;
import com.gojek.parking.commons.utils.LogUtils;
import com.gojek.parking.service.IParkingLotService;
import com.gojek.parking.service.impl.ParkingLotService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by siva on 09/10/17.
 */
public class ParkingLotClient {

  private static IParkingLotService parkingLotService = null;
  private static BufferedReader bufferedReader = null;
  private static String[] inputs = null;

  public static void main(String[] args) {
    try {
      parkingLotService = ParkingLotService.getParkingLotService();
      if (args.length > 0) {
        bufferedReader = new BufferedReader(new FileReader(args[0]));
      } else {
        LogUtils.toConsole("Enter Command");
        bufferedReader = new BufferedReader(new InputStreamReader(
            System.in));
      }
      for (String line; (line = bufferedReader.readLine()) != null; ) {
        inputs = line.trim().split("\\s+");
        if (!inputs[0].isEmpty()) {
          switch (inputs[0]) {
            case "create_parking_lot": {
              if (hasValidNumOfArgs(2)) {
                try {
                  parkingLotService.createParkingLot(Integer.parseInt(inputs[1]));
                } catch (NumberFormatException e) {
                  LogUtils.toConsole("Invalid input passed");
                }
              }
              break;
            }
            case "park": {
              if (hasValidNumOfArgs(3)) {
                parkingLotService.park(new Vehicle(inputs[1], inputs[2]));
              }
              break;
            }
            case "leave": {
              if (hasValidNumOfArgs(2)) {
                try {
                  parkingLotService.leave(Integer.parseInt(inputs[1]));
                } catch (NumberFormatException e) {
                  LogUtils.toConsole("Invalid input passed");
                }
              }
              break;
            }
            case "status": {
              parkingLotService.status();
              break;
            }
            case "registration_numbers_for_cars_with_colour": {
              if (hasValidNumOfArgs(2)) {
                parkingLotService.getRegistrationNumbersByColour(inputs[1]);
              }
              break;
            }
            case "slot_numbers_for_cars_with_colour": {
              if (hasValidNumOfArgs(2)) {
                parkingLotService.getSlotNumbersByColor(inputs[1]);
              }
              break;
            }
            case "slot_number_for_registration_number": {
              if (hasValidNumOfArgs(2)) {
                parkingLotService.getSlotNumberByRegistrationNum(inputs[1]);
              }
              break;
            }
            default: {
              LogUtils.toConsole("Invalid command entered");
              break;
            }
          }
        }
      }
    } catch (FileNotFoundException e) {
      LogUtils.toConsole("File not exist..." + e.getMessage());
    } catch (IOException e) {
      LogUtils.toConsole("Error while reading the file..." + e.getMessage());
    }
  }

  private static boolean hasValidNumOfArgs(Integer size) {
    if (inputs.length == size) {
      return true;
    }
    LogUtils.toConsole("Maximum input accepted is " + size);
    return false;
  }
}
