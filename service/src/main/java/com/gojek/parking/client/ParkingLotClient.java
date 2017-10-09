package com.gojek.parking.client;

import com.gojek.parking.service.IParkingLotService;
import com.gojek.parking.service.impl.ParkingLotService;

/**
 * Created by siva on 09/10/17.
 */
public class ParkingLotClient {

  private static IParkingLotService parkingLotService = null;

  public static void main(String[] args) {
    parkingLotService = ParkingLotService.getParkingLotService();
    if (args.length > 0) {

    } else {

    }
  }
}
