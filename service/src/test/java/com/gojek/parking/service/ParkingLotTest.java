package com.gojek.parking.service;

import static org.junit.Assert.assertTrue;

import com.gojek.parking.commons.model.Vehicle;
import com.gojek.parking.service.impl.ParkingLotService;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by siva on 08/10/17.
 */
public class ParkingLotTest {

  private static IParkingLotService parkingLotService = null;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @BeforeClass
  public static void init() {
    parkingLotService = ParkingLotService.getParkingLotService();
  }

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void cleanUpStreams() {
    System.setOut(null);
  }

  @Test
  public void createParkingLot() {
    parkingLotService.createParkingLot(2);
    assertTrue("Created a parking lot with 2 slots".equals(outContent.toString().trim()));
  }

  @Test
  public void park() {
    parkingLotService.park(new Vehicle("KA-01- HH-1234", "White"));
    assertTrue("Allocated slot number: 1".equals(outContent.toString().trim()));
  }
}
