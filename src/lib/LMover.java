/*
 * File:         LMover.java
 * Created Date: 2021-04-12
 * Author:       Brian Lucas
 * Purpose:      Move some stuff
 * -----
 * Last Modified: Mon Apr 12 2021
 * HISTORY:
 * Date        Comments
 * 2021-04-12  Initial Commit
 */


package lib;

import java.awt.Color;
import java.awt.Graphics2D;

public class LMover {

  LVector position;
  LVector velocity;
  LVector acceleration;

  double speedLimit = 0;
  boolean speedLimited = false;

  int size = 30;

  public LMover(LVector position, LVector velocity, LVector acceleration) {
    this.position = position;
    this.velocity = velocity;
    this.acceleration = acceleration;
  }

  public void update() {
    velocity.add(acceleration);
    position.add(velocity);
    if (speedLimited) {
      velocity.limit(speedLimit);
    }
  }

  public void render(Graphics2D graphics) {
    graphics.setColor(new Color(0, 255, 0, 128));
    graphics.fillOval((int)position.getX(), (int)position.getY(), size, size);
  }

  public LVector getPosition() {
    return position;
  }

  public void setPosition(LVector position) {
    this.position = position;
  }

  public LVector getVelocity() {
    return velocity;
  }

  public void setVelocity(LVector velocity) {
    this.velocity = velocity;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public LVector getAcceleration() {
    return acceleration;
  }

  public void setAcceleration(LVector acceleration) {
    this.acceleration = acceleration;
  }

  public double getSpeedLimit() {
    return speedLimit;
  }

  public void setSpeedLimit(double speedLimit) {
    this.speedLimit = speedLimit;
  }

  public boolean isSpeedLimited() {
    return speedLimited;
  }

  public void setSpeedLimited(boolean speedLimited) {
    this.speedLimited = speedLimited;
  }  
}
