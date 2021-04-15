/*
 * File:         LSpring.java
 * Created Date: 2021-04-15
 * Author:       Brian Lucas
 * Purpose:      
 * -----
 * Last Modified: Thu Apr 15 2021
 * HISTORY:
 * Date        Comments
 */


package lib;

import java.awt.Canvas;
import java.awt.Graphics2D;

public class LSpring extends LRunnable {

  LMover bob;
  LVector anchor;
  double k = 0.01;
  double restLength = 200;

  public LSpring(Canvas container) {
    super(container);
  }

  @Override
  public void update() {
    LVector springForce = LVector.sub(bob.getPosition(), anchor);
    double currentLengh = springForce.getMagnitude();
    springForce.normalize();
    double stretch = currentLengh - restLength;
    springForce.scale(-k * stretch);
    bob.addForce(springForce);
  }

  @Override
  public void render(Graphics2D graphics) {
    graphics.drawLine(
      (int) anchor.getX(), 
      (int) anchor.getY(), 
      (int) bob.getPosition().getX(), 
      (int) bob.getPosition().getY()
    );
  }

  public LMover getBob() {
    return bob;
  }

  public void setBob(LMover bob) {
    this.bob = bob;
  }

  public LVector getAnchor() {
    return anchor;
  }

  public void setAnchor(LVector anchor) {
    this.anchor = anchor;
  }

  public double getK() {
    return k;
  }

  public void setK(double k) {
    this.k = k;
  }

  public double getRestLength() {
    return restLength;
  }

  public void setRestLength(double restLength) {
    this.restLength = restLength;
  }
  
  
}
