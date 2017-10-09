package com.gojek.parking.client;

import com.gojek.parking.commons.model.Vehicle;
import com.gojek.parking.commons.utils.LogUtils;
import com.gojek.parking.service.IParkingLotService;
import com.gojek.parking.service.impl.ParkingLotService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by siva on 09/10/17.
 */
public class ParkingLotClient {

  private static IParkingLotService parkingLotService = null;
  private static BufferedReader bufferedReader = null;
  private static String line = null;
  private static String[] inputs = null;

  public static void main(String[] args) {
    parkingLotService = ParkingLotService.getParkingLotService();
    if (args.length > 0) {
      try {
        bufferedReader = new BufferedReader(new FileReader(args[0]));
        for (String line; (line = bufferedReader.readLine()) != null; ) {
          inputs = line.trim().split("\\s+");
          if (!inputs[0].isEmpty()) {
            switch (inputs[0]) {
              case "create_parking_lot": {
                if (inputs.length == 2) {
                  try {
                    parkingLotService.createParkingLot(Integer.parseInt(inputs[1]));
                  } catch (NumberFormatException e) {
                    LogUtils.toConsole("Invalid input passed");
                  }
                } else {
                  LogUtils.toConsole("Maximum input accepted is 2");
                }
                break;
              }
              case "park": {
                if (inputs.length == 3) {
                  parkingLotService.park(new Vehicle(inputs[1], inputs[2]));
                } else {
                  LogUtils.toConsole("Maximum input accepted is 3");
                }
                break;
              }
              case "leave": {
                if (inputs.length == 2) {
                  try {
                    parkingLotService.leave(Integer.parseInt(inputs[1]));
                  } catch (NumberFormatException e) {
                    LogUtils.toConsole("Invalid input passed");
                  }
                } else {
                  LogUtils.toConsole("Maximum input accepted is 2");
                }
                break;
              }
              case "status": {
                parkingLotService.status();
                break;
              }
              case "registration_numbers_for_cars_with_colour": {
                if (inputs.length == 2) {
                  parkingLotService.getRegistrationNumbersByColour(inputs[1]);
                } else {
                  LogUtils.toConsole("Maximum input accepted is 2");
                }
                break;
              }
              case "slot_numbers_for_cars_with_colour": {
                if (inputs.length == 2) {
                  parkingLotService.getSlotNumbersByColor(inputs[1]);
                } else {
                  LogUtils.toConsole("Maximum input accepted is 2");
                }
                break;
              }
              case "slot_number_for_registration_number": {
                if (inputs.length == 2) {
                  parkingLotService.getSlotNumberByRegistrationNum(inputs[1]);
                } else {
                  LogUtils.toConsole("Maximum input accepted is 2");
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
  }
}
