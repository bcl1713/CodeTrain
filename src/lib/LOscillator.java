/*
 * File:         LOscillator.java
 * Created Date: 2021-04-13
 * Author:       Brian Lucas
 * Purpose:      Oscillator class
 * -----
 * Last Modified: Tue Apr 13 2021
 * HISTORY:
 * Date        Comments
 * 2021-04-13  Initial commit
 */


package lib;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class LOscillator {

  LVector angle;
  LVector velocity;
  LVector amplitude;
  LVector acceleration;
  Canvas container;

  public LOscillator(Canvas container) {
    angle = new LVector(0,0);
    velocity = new LVector((Math.random() / 10) - 0.05, (Math.random() / 10) - 0.05);
    amplitude = new LVector(Math.random() * container.getWidth() / 2, 
        Math.random() * container.getHeight() / 2);
    acceleration = new LVector(-0.00001, -0.00001);
    this.container = container;
  }
  
  public void update() {
    velocity.add(acceleration);
    angle.add(velocity);
  }

  public void render(Graphics2D graphics) {
    graphics.setColor(Color.GREEN);
    double x = Math.sin(angle.getX()) * amplitude.getX();
    double y = Math.sin(angle.getY()) * amplitude.getY();

    AffineTransform t = graphics.getTransform();
    graphics.translate(container.getWidth() / 2, container.getHeight() / 2);
    graphics.drawLine(0, 0, (int)x, (int)y);
    graphics.translate(x - 8, y - 8);
    graphics.fillOval(0, 0, 16, 16);
    graphics.setTransform(t);
  }
}
