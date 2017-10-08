package com.gojek.parking.service;

import static org.junit.Assert.assertTrue;

import com.gojek.parking.commons.model.Vehicle;
import com.gojek.parking.service.impl.ParkingLotService;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Created by siva on 08/10/17.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
  public void t1CreateParkingLot() {
    parkingLotService.createParkingLot(10);
    assertTrue("Created a parking lot with 10 slots".equals(outContent.toString().trim()));
  }

  @Test
  public void t2Park() {
    parkingLotService.park(new Vehicle("KA-01-HH-1234", "White"));
    assertTrue("Allocated slot number: 1".equals(outContent.toString().trim()));
  }

  @Test
  public void t3Park() {
    parkingLotService.park(new Vehicle("KA-01-HH-9999", "White"));
    assertTrue("Allocated slot number: 2".equals(outContent.toString().trim()));
  }

  @Test
  public void t4Park() {
    parkingLotService.park(new Vehicle("KA-01-BB-0001", "Black"));
    assertTrue("Allocated slot number: 3".equals(outContent.toString().trim()));
  }

  @Test
  public void t5Status() {
    parkingLotService.status();
  }

  @Test
  public void t6GetRegistrationNumbersByColour() {
    parkingLotService.getRegistrationNumbersByColour("White");
    assertTrue("KA-01-HH-1234, KA-01-HH-9999".equals(outContent.toString().trim()));
  }

  @Test
  public void t7GetSlotNumbersByColor() {
    parkingLotService.getSlotNumbersByColor("White");
    assertTrue("1, 2".equals(outContent.toString().trim()));
  }

  @Test
  public void t8GetSlotNumberByRegistrationNum() {
    parkingLotService.getSlotNumberByRegistrationNum("KA-01-HH-9999");
    assertTrue("2".equals(outContent.toString().trim()));
  }

  @Test
  public void t9Leave() {
    parkingLotService.leave(1);
    assertTrue("Slot number 1 is free".equals(outContent.toString().trim()));
  }
}
