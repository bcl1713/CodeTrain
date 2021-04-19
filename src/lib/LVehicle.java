/*
 * File:         LVehicle.java
 * Created Date: 2021-04-17
 * Author:       Brian Lucas
 * Purpose:      
 * -----
 * Last Modified: Mon Apr 19 2021
 * HISTORY:
 * Date        Comments
 */


package lib;

import java.awt.Canvas;

public class LVehicle extends LMover {

  private LVector target;
  private double breakingDistance;

  public LVehicle(Canvas container, double x, double y) {
    super(container);
    this.position = new LVector(x, y);
  }

  private void seek(LVector target) {
    LVector desired = LVector.sub(target, position);

    double d = desired.getMagnitude();
    desired.normalize();

    if (breakingDistance > 0 && d < breakingDistance) {
      double m = LUtilities.map(d, 0, breakingDistance, 0, maxVelocity);
      desired.scale(m);
    } else {
      desired.scale(maxVelocity);
    }

    LVector steer = LVector.sub(desired, velocity);
    steer.limit(maxAcceleration);
    addForce(steer);
  }

  public LVector getTarget() {
    return target;
  }

  public void setTarget(LVector target) {
    this.target = target;
  }

  public double getBreakingDistance() {
    return breakingDistance;
  }

  public void setBreakingDistance(double breakingDistance) {
    this.breakingDistance = breakingDistance;
  }
  
  @Override
  public void update() {
    if(target != null) {
      seek(target);
    }
    super.update();
  }

}
