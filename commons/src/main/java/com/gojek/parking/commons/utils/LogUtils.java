package com.gojek.parking.commons.utils;

/**
 * Created by siva on 08/10/17.
 */
public class LogUtils {

  private LogUtils() {
  }

  /**
   * Util function for console log. We can extend this function to log later to write it file or something else.
   * @param str
   */
  public static void toConsole(String str) {
    System.out.println(str);
  }
}
