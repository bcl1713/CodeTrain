/*
 * File:         LPath.java
 * Created Date: 2021-04-18
 * Author:       Brian Lucas
 * Purpose:      
 * -----
 * Last Modified: Sun Apr 18 2021
 * HISTORY:
 * Date        Comments
 */


package lib;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.BasicStroke;

public class LPath {
  
  ArrayList<LVector> points;

  double radius;

  public LPath(double radius) {
    points = new ArrayList<>();
    this.radius = radius;
  }

  public LPath() {
    this(25);
  }

  public ArrayList<LVector> getPoints() {
    return points;
  }

  public void addPoint(LVector point) {
    points.add(point);
  }

  public void render(Graphics2D graphics) {
    
    int[] x = new int[points.size()];
    int[] y = new int[points.size()];

    for(int i = 0; i < points.size(); i++) {
      x[i] = (int)points.get(i).getX();
      y[i] = (int)points.get(i).getY();
    } 

    graphics.setStroke(new BasicStroke((float)radius * 8));
    graphics.setColor(Color.GRAY);
    

    graphics.drawPolyline(x, y, points.size());
    
    graphics.setStroke(new BasicStroke((1)));
    graphics.setColor(Color.BLACK);
    
    graphics.drawPolyline(x, y, points.size());
  
  }

  public double getRadius() {
    return radius;
  }

  public void setRadius(double radius) {
    this.radius = radius;
  }

}
