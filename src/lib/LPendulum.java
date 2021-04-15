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

public class LPendulum implements Runnable {

  Canvas container;

  double angle;
  double aVelocity;
  double aAcceleration;

  double length;
  LVector origin;
  LVector bob;

  static final double GRAVITY_CONSTANT = -0.002;

  int updatesRequested = 0;
  boolean running;
  private static final int MAX_UPDATES_PER_SECOND = 120;
  Thread thread;

  public LPendulum(Canvas container) {
    this.container = container;
    angle = Math.PI / 4;
    length = 180;
    origin = new LVector(container.getWidth() / 2.0, 0);
    bob = new LVector(Math.sin(angle) * length, Math.cos(angle) * length);
    bob.add(origin);
    thread = new Thread(this, "Pendulum");
  }
  
  @Override
  public void run() {
    running = true;
    while (running) {
      if (updatesRequested > 0) {
        update();
        updatesRequested--;
      }
      try {
        Thread.sleep(1000 / MAX_UPDATES_PER_SECOND);
      } catch (InterruptedException e) {
        e.printStackTrace();
        thread.interrupt();
      }
    }
  }

  public void render(Graphics2D graphics) {
    graphics.setColor(Color.GREEN);

    graphics.drawLine((int)origin.getX(), (int)origin.getY(), (int)bob.getX(), (int)bob.getY());
    graphics.fillOval((int)bob.getX() - 16, (int)bob.getY() - 16, 32, 32);
  }

  void update() {
    aAcceleration = Math.sin(angle) * GRAVITY_CONSTANT;
    aVelocity += aAcceleration;
    angle += aVelocity;
    bob.setX(Math.sin(angle) * length);
    bob.setY(Math.cos(angle) * length);
    bob.add(origin);
    aVelocity *= 0.9999;

  }

  public void requestUpdate() {
    updatesRequested++;
  }

  public Thread getThread() {
    return thread;
  }
}
