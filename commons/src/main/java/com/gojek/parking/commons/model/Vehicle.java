package com.gojek.parking.commons.model;

import java.io.Serializable;

/**
 * Created by siva on 08/10/17.
 */
public class Vehicle implements Serializable {

  private static final long serialVersionUID = 1L;

  private String registrationNum;
  private String color;

  public Vehicle(String registrationNum, String color) {
    this.registrationNum = registrationNum;
    this.color = color;
  }

  public String getColor() {
    return color;
  }

  public String getRegistrationNum() {
    return registrationNum;
  }
}
