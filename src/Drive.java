/*
 * File:         Drive.java
 * Created Date: 2021-04-17
 * Author:       Brian Lucas
 * Purpose:      Test LVehicle
 * -----
 * Last Modified: Sat Apr 17 2021
 * HISTORY:
 * Date        Comments
 * 2021-04-17  Initial Commit
 */

import java.awt.Color;
import java.awt.event.MouseEvent;

import lib.LDisplay;
import lib.LVector;
import lib.LVehicle;

public class Drive extends LDisplay {

  /**
   * Generated serialVersionUID
   */
  private static final long serialVersionUID = -208099980091768496L;

  transient LVehicle vehicle;

  @Override
  public void start() {
    vehicle = new LVehicle(this);
    vehicle.setPosition(new LVector(getWidth() / 2, getHeight() / 2));
    vehicle.setMaxAcceleration(0.5);
    vehicle.setMaxVelocity(5);
    vehicle.setBreakingDistance(100);
  }

  @Override
  public void render() {
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, getWidth(), getHeight());
    graphics.setColor(Color.GREEN);
    vehicle.render(graphics);
  }

  @Override
  public void update() {
    vehicle.update();
  }

  public static void main(String[] args) {
    new Drive();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    vehicle.setTarget(new LVector(e.getX(), e.getY()));
  }
  
}
