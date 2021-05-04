/*
 * File:         RoadSegment.java
 * Created Date: 2021-04-20
 * Author:       Brian Lucas
 * Purpose:      
 * -----
 * Last Modified: Tue Apr 20 2021
 * HISTORY:
 * Date        Comments
 */


package lib;

import java.awt.Canvas;
import java.util.ArrayList;

public class RoadSegment extends LNode{

  private ArrayList<RoadTarget> targets;

  public ArrayList<RoadTarget> getTargets() {
    return targets;
  }

  public void setTargets(ArrayList<RoadTarget> targets) {
    this.targets = targets;
  }

  public RoadSegment(Canvas container, LVector position, double radius) {
    super(container, position, radius);
    targets = new ArrayList<>();
  }
  
}
