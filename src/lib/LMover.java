/*
 * File:         LMover.java
 * Created Date: 2021-04-12
 * Author:       Brian Lucas
 * Purpose:      Move some stuff
 * -----
 * Last Modified: Mon Apr 12 2021
 * HISTORY:
 * Date        Comments
 * 2021-04-14  Growing
 * 2021-04-13  Put checkposition in here.. add bounce wrap or destroy later
 * 2021-04-12  Add mass
 * 2021-04-12  Initial Commit
 */


package lib;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class LMover implements Runnable {

  LVector position = new LVector(0, 0);
  LVector velocity = new LVector(0, 0);
  LVector acceleration = new LVector(0, 0);

  double angle = 0;
  double aVelocity = 0;
  double aAcceleration = 0;

  Canvas container;
  Color color;

  double speedLimit = 0;
  boolean speedLimited = false;
  
  boolean running = false;
  static final int UPDATES_PER_SECOND = 60;
  Thread thread;

  double size = 30;
  double mass = 1;

  public LMover(Canvas container) {
    thread = new Thread(this, "Mover");
    this.container = container;
  }

  public void addForce(LVector force) {
    LVector f = LVector.scale(force, 1 / mass);
    acceleration.add(f);
  }

  public void update() {
    velocity.add(acceleration);
    position.add(velocity);
    if (speedLimited) {
      velocity.limit(speedLimit);
    }
    acceleration.scale(0);
    aVelocity += aAcceleration;
    angle += aVelocity;
    aAcceleration = 0;
    checkPosition();
  }

  public void render(Graphics2D graphics) {
    graphics.setColor(color);
    AffineTransform t = graphics.getTransform();

    graphics.translate(position.getX(), position.getY());
    graphics.rotate(velocity.getHeading());
    int[] yPoints = {(int)-size / 3, 0, (int)size / 3};
    int[] xPoints = {(int)-size / 2, (int)size / 2, (int)-size / 2};
    graphics.fillPolygon(xPoints, yPoints, 3);
    graphics.setTransform(t);
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
    if (position.getX() < this.size / 2) {
      velocity.setX(velocity.getX() * -1);
      position.setX(this.size / 2);
    }
    if (position.getX() > container.getWidth() - this.size / 2) {
      velocity.setX(velocity.getX() * -1);
      position.setX(container.getWidth() - this.size / 2);
    }
    if (position.getY() < this.size / 2) {
      velocity.setY(velocity.getY() * -1);
      position.setY(this.size / 2);
    }
    if (position.getY() > container.getHeight() - this.size / 2) {
      velocity.setY(velocity.getY() * -1);
      position.setY(container.getHeight() - this.size / 2);
    }
  }

  public double getAngle() {
    return angle;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  public double getaVelocity() {
    return aVelocity;
  }

  public void setaVelocity(double aVelocity) {
    this.aVelocity = aVelocity;
  }

  public double getaAcceleration() {
    return aAcceleration;
  }

  public void setaAcceleration(double aAcceleration) {
    this.aAcceleration = aAcceleration;
  }

  public Canvas getContainer() {
    return container;
  }

  public void setContainer(Canvas container) {
    this.container = container;
  }

  public Thread getThread() {
    return thread;
  }

  @Override
  public void run() {
    running = true;
    
    while (running) {
      update();
      try {
        Thread.sleep(1000 / UPDATES_PER_SECOND);
      } catch (InterruptedException e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
        return;
      }
    }
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

}
