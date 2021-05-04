/*
 * File:         LNode.java
 * Created Date: 2021-04-19
 * Author:       Brian Lucas
 * Purpose:      
 * -----
 * Last Modified: Mon Apr 19 2021
 * HISTORY:
 * Date        Comments
 */


package lib;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class LNode extends LRunnable{

  private double updateInterval;
  private long lastTime;
  private double delta = 0;

  private LVector position;
  private ArrayList<LNode> nextNodes;
  private ArrayList<LNode> receivingNodes;
  private double radius;
  private boolean occupied;
  private boolean enabled;
  private ArrayList<LVehicle> occupiers;
  private LNode acceptedNode;
  private LNode fromNode = null;
  private LNode lastAcceptedNode;

  public LNode(Canvas container, LVector position, double radius) {
    super(container);
    this.position = position;
    this.radius = radius;
    this.occupied = false;
    this.occupiers = new ArrayList<>();
    this.receivingNodes = new ArrayList<>();
    this.nextNodes = new ArrayList<>();
    this.enabled = false;
    lastTime = System.nanoTime();
    updateInterval = Math.random() * 3 + 5;
  }



  public LVector getPosition() {
    return position;
  }



  public void setPosition(LVector position) {
    this.position = position;
  }



  public double getRadius() {
    return radius;
  }



  public void setRadius(double radius) {
    this.radius = radius;
  }


  public LNode getAcceptedNode() {
    return acceptedNode;
  }



  public void setAcceptedNode(LNode acceptedNode) {
    this.acceptedNode = acceptedNode;
  }



  public boolean isOccupied() {
    return occupied;
  }



  public void setOccupied(boolean occupied) {
    this.occupied = occupied;
  }



  public List<LVehicle> getOccupiers() {
    return occupiers;
  }

  public void setNextNode(LNode nextNode) {
    nextNodes.add(nextNode);
    nextNode.getReceivingNodes().add(this);
    nextNode.setAcceptedNode(this);
  }



  public List<LNode> getReceivingNodes() {
    return receivingNodes;
  }

  public void setFromNode(LNode fromNode) {
    this.fromNode = fromNode;
  }



  @Override
  public void update() {
    if (!occupiers.isEmpty() && !nextNodes.isEmpty()) {
      for(int i = 0; i < occupiers.size(); i++) {
        if (LVector.distance(occupiers.get(i).getPosition(), position) < radius) {
          LNode nextNode;
          if (fromNode != null && !receivingNodes.isEmpty()) {
            nextNode = nextNodes.get(receivingNodes.indexOf(fromNode));
          } else {
            nextNode = nextNodes.get(0);
          }
          if (nextNode.isAccpting(this)) {
            occupiers.get(i).setTarget(nextNode.getPosition());
            nextNode.getOccupiers().add(occupiers.get(i));
            nextNode.setFromNode(this);
            occupiers.remove(i);
            fromNode = null;
          }
        }
      }
    }
    if (receivingNodes.size() > 1) {
      long now = System.nanoTime();
      delta += (now - lastTime) / (1000000000.0 * updateInterval);
      lastTime = now;

      if (delta >= 1) {
        nextAcceptedNode();
        delta--;
      }

      if (delta >= 0.8) {
        acceptNone();
      }

    }
  }

  private boolean isAccpting(LNode lNode) {
    return (acceptedNode == lNode && occupiers.isEmpty()); 
  }

  private void nextAcceptedNode() {
    acceptedNode = receivingNodes.get((receivingNodes.indexOf(lastAcceptedNode) + 1) % receivingNodes.size());
  }

  private void acceptNone() {
    if (acceptedNode != null) {
      lastAcceptedNode = acceptedNode;
      acceptedNode = null;
    }
  }



  @Override
  public void render(Graphics2D graphics) {
    if(enabled) {
      AffineTransform t = graphics.getTransform();
      graphics.translate(position.getX() - radius, position.getY() - radius);
      graphics.setColor(Color.GRAY);
      graphics.fillRect(0, 0, (int)radius * 2, (int)radius * 2);
      graphics.setTransform(t);
    }
  }


  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isEnabled() {
    return enabled;
  }
  
}
