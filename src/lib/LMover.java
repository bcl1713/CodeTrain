/*
 * File:         LMover.java
 * Created Date: 2021-04-12
 * Author:       Brian Lucas
 * Purpose:      Move some stuff
 * -----
 * Last Modified: Mon Apr 12 2021
 * HISTORY:
 * Date        Comments
 * 2021-04-15  Move threading to super class LRunner
 * 2021-04-15  Add edge behavior selection
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

public class LMover extends LRunnable {

  LVector position = new LVector(0, 0);
  LVector velocity = new LVector(0, 0);
  LVector acceleration = new LVector(0, 0);

  double angle = 0;
  double aVelocity = 0;
  double aAcceleration = 0;

  Color color;

  double maxVelocity = -1;
  double maxAcceleration = -1;
  
  double size = 30;
  double mass = 1;

  double heading;

  public static final int WRAP = 0;
  public static final int REFLECT = 1;
  public static final int NONE = 2;

  private int edgeBehavior = 2;

  public LMover(Canvas container) {
    super(container);
  }

  public void addForce(LVector force) {
    LVector f = LVector.scale(force, 1 / mass);
    acceleration.add(f);
  }

  public void update() {
    if (maxAcceleration > 0) {
      acceleration.limit(maxAcceleration);
    }
    velocity.add(acceleration);
    if (maxVelocity > 0) {
      velocity.limit(maxVelocity);
    }
    position.add(velocity);
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
    graphics.rotate(getHeading());
    graphics.translate(-size / 2, -size / 2);
    graphics.fillOval(0, 0, (int)size, (int)size);
    // int[] yPoints = {(int)-size / 3, 0, (int)size / 3};
    // int[] xPoints = {(int)-size / 2, (int)size / 2, (int)-size / 2};
    // graphics.fillPolygon(xPoints, yPoints, 3);
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
    return maxVelocity;
  }

  public void setSpeedLimit(double speedLimit) {
    this.maxVelocity = speedLimit;
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
    switch (edgeBehavior) {
      case NONE:
        break;
      case REFLECT:
        if (position.getX() < 0) {
          velocity.setX(velocity.getX() * -1);
          position.setX(0);
        }
        if (position.getX() > container.getWidth()) {
          velocity.setX(velocity.getX() * -1);
          position.setX(container.getWidth());
        }
        if (position.getY() < 0) {
          velocity.setY(velocity.getY() * -1);
          position.setY(0);
        }
        if (position.getY() > container.getHeight()) {
          velocity.setY(velocity.getY() * -1);
          position.setY(container.getHeight());
        }
        break;
      case WRAP:
        if (position.getX() < 0) {
          position.setX(container.getWidth());
        }
        if (position.getX() > container.getWidth()) {
          position.setX(0);
        }
        if (position.getY() < 0) {
          position.setY(container.getHeight());
        }
        if (position.getY() > container.getHeight()) {
          position.setY(0);
        }
        break;
      default:
        break;
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

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public int getEdgeBehavior() {
    return edgeBehavior;
  }

  public void setEdgeBehavior(int edgeBehavior) {
    this.edgeBehavior = edgeBehavior;
  }

  public double getMaxVelocity() {
    return maxVelocity;
  }

  public void setMaxVelocity(double maxVelocity) {
    this.maxVelocity = maxVelocity;
  }

  public double getMaxAcceleration() {
    return maxAcceleration;
  }

  public void setMaxAcceleration(double maxAcceleration) {
    this.maxAcceleration = maxAcceleration;
  }

  public double getHeading() {
    if (velocity.getMagnitude() != 0) {
      heading = velocity.getHeading();
    }
    return heading;
  }

 

}
