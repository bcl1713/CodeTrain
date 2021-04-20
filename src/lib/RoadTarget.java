/*
 * File:         RoadTarget.java
 * Created Date: 2021-04-20
 * Author:       Brian Lucas
 * Purpose:      
 * -----
 * Last Modified: Tue Apr 20 2021
 * HISTORY:
 * Date        Comments
 */


package lib;

public class RoadTarget extends LVector {
  
  private boolean occupied;
  public RoadSegment source;
  public RoadSegment target;

  public RoadTarget(double x, double y, boolean occupied) {
    super(x, y);
    this.occupied = occupied;
  }

  public boolean isOccupied() {
    return occupied;
  }

  public RoadSegment getSource() {
    return source;
  }

  public void setSource(RoadSegment source) {
    this.source = source;
  }

  public RoadSegment getTarget() {
    return target;
  }

  public void setTarget(RoadSegment target) {
    this.target = target;
  }

  public void setOccupied(boolean occupied) {
    this.occupied = occupied;
  }

}
