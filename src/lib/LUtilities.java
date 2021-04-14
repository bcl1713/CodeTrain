/*
 * File:         LUtilities.java
 * Created Date: 2021-04-14
 * Author:       Brian Lucas
 * Purpose:      
 * -----
 * Last Modified: Wed Apr 14 2021
 * HISTORY:
 * Date        Comments
 */


package lib;

public class LUtilities {

  private LUtilities(){}

  /**
   * Return a mapped value between low2 and high2.
   * @param in the value to map
   * @param low1 the lowest possible input value
   * @param high1 the highest possible input value
   * @param low2 the lowest possible output value
   * @param high2 the highest possible output value
   * @return
   */
  public static double map(double in, double low1, double high1, double low2, double high2) {
    return low2 + (in - low1) * (high2 - low2) / (high1 - low1);
  }
  
}
