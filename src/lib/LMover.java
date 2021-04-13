/*
 * File:         LMover.java
 * Created Date: 2021-04-12
 * Author:       Brian Lucas
 * Purpose:      Move some stuff
 * -----
 * Last Modified: Mon Apr 12 2021
 * HISTORY:
 * Date        Comments
 * 2021-04-13  Put checkposition in here.. add bounce wrap or destroy later
 * 2021-04-12  Add mass
 * 2021-04-12  Initial Commit
 */


package lib;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;

public class LMover {

  LVector position;
  LVector velocity;
  LVector acceleration;

  Canvas container;

  double speedLimit = 0;
  boolean speedLimited = false;

  double size = 30;
  double mass = 1;

  public LMover(LVector position, LVector velocity, LVector acceleration, Canvas container) {
    this.position = position;
    this.velocity = velocity;
    this.acceleration = acceleration;
    this.container = container;
  }

  public void addForce(LVector force) {
    LVector f = LVector.mul(force, 1 / mass);
    acceleration.add(f);
  }

  public void update() {
    velocity.add(acceleration);
    position.add(velocity);
    if (speedLimited) {
      velocity.limit(speedLimit);
    }
    acceleration.scale(0);
    checkPosition();
  }

  public void render(Graphics2D graphics) {
    graphics.setColor(new Color(0, 255, 0, 128));
    graphics.fillOval((int)position.getX(), (int)position.getY(), (int) size, (int) size);
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

  public double getSize() {
    return size;
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

  public void setSize(double size) {
    this.size = size;
  }

  public double getMass() {
    return mass;
  }

  public void setMass(double mass) {
    this.mass = mass;
  }
  
  public void checkPosition() {
    if (position.getX() < 0) {
      velocity.setX(velocity.getX() * -1);
      position.setX(0);
    }
    if (position.getX() > container.getWidth() - this.size) {
      velocity.setX(velocity.getX() * -1);
      position.setX(container.getWidth() - this.size);
    }
    if (position.getY() < 0) {
      velocity.setY(velocity.getY() * -1);
      position.setY(0);
    }
    if (position.getY() > container.getHeight() - this.size) {
      velocity.setY(velocity.getY() * -1);
      position.setY(container.getHeight() - this.size);
    }
  }

}
