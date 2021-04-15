/*
 * File:         LPendulum.java
 * Created Date: 2021-04-15
 * Author:       Brian Lucas
 * Purpose:      Simulate a pendulum
 * -----
 * Last Modified: Thu Apr 15 2021
 * HISTORY:
 * Date        Comments
 * 2021-04-15  Initial
 */


package lib;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;

public class LPendulum extends LRunnable {

  double angle;
  double aVelocity;
  double aAcceleration;

  double length;
  LVector origin;
  LVector bob;

  static final double GRAVITY_CONSTANT = -0.002;

  int updatesRequested = 0;

  public LPendulum(Canvas container) {
    super(container);
    angle = Math.PI / 4;
    length = 180;
    origin = new LVector(container.getWidth() / 2.0, 0);
    bob = new LVector(Math.sin(angle) * length, Math.cos(angle) * length);
    bob.add(origin);
  }

  public void render(Graphics2D graphics) {
    graphics.setColor(Color.GREEN);
    graphics.drawLine((int)origin.getX(), (int)origin.getY(), (int)bob.getX(), (int)bob.getY());
    graphics.fillOval((int)bob.getX() - 16, (int)bob.getY() - 16, 32, 32);
  }

  public void update() {
    aAcceleration = Math.sin(angle) * GRAVITY_CONSTANT;
    aVelocity += aAcceleration;
    angle += aVelocity;
    bob.setX(Math.sin(angle) * length);
    bob.setY(Math.cos(angle) * length);
    bob.add(origin);
    aVelocity *= 0.9999;

  }
}
