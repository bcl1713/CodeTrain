import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * File:         Test2.java
 * Created Date: 2021-04-19
 * Author:       Brian Lucas
 * Purpose:      
 * -----
 * Last Modified: Mon Apr 19 2021
 * HISTORY:
 * Date        Comments
 */

import lib.LDisplay;
import lib.LMover;
import lib.LNode;
import lib.LVector;
import lib.LVehicle;

public class Test2 extends LDisplay {

  private static final int NODE_RADIUS = 12;
  private static final int STARTING_VEHICLES = 3;
  private static final int START_INTERSECTIONS = 3;
  private static final int BUFFER = 10;
  private int intersections;
  private int nodeRows;
  private int nodeCols;

  private transient List<LVehicle> vehicles;
  private transient LNode[][] nodes;  

  public Test2(int w, int h) {
    super(w, h);
    vehicles = Collections.synchronizedList(new ArrayList<>());
  }

  public static void main(String[] args) {
    new Test2(800, 600);
  }

  @Override
  public void start() {
    nodeRows = (getHeight() - BUFFER * 2)/(NODE_RADIUS*2);
    nodeCols = (getWidth() - BUFFER * 2)/(NODE_RADIUS*2);
    nodes = new LNode[nodeRows][nodeCols];
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, myWidth, myHeight);
    intersections = START_INTERSECTIONS;
    buildGrid();
    
    for (int i = 0; i < STARTING_VEHICLES; i++) {
      addVehicle();
    }
    
  }

  private void addVehicle() {
    LVehicle vehicle = new LVehicle(this, Math.random() * 800, Math.random() * 600);
    vehicle.setMaxVelocity(Math.random() * (NODE_RADIUS / 100.0) + 2) ;
    vehicle.setMaxAcceleration(vehicle.getMaxVelocity() / 10);
    vehicle.setEdgeBehavior(LMover.WRAP);
    vehicle.setSize(NODE_RADIUS * 0.5);
    vehicle.setBreakingDistance(NODE_RADIUS * 1.75);
    vehicle.setColor(Color.BLUE);
    vehicles.add(vehicle);

    
    double bestDistance = 1000000000;
    LNode bestNode = null;
    for (LNode[] lNodes : nodes) {
      for (LNode lNode : lNodes) {
        double distance = LVector.distance(vehicle.getPosition(), lNode.getPosition());
        if (distance < bestDistance && lNode.isEnabled()) {
          bestDistance = distance;
          bestNode = lNode;
        }
      }
    }
    if (bestNode != null) {
      vehicle.setTarget(bestNode.getPosition());
      bestNode.getOccupiers().add(vehicle);
    }
  

  }

  private void buildGrid() {
    for (int i = 0; i < nodeRows; i++) {
      for (int j = 0; j < nodeCols; j++) {
        LNode node = new LNode(
            this, 
            new LVector(
                j*(NODE_RADIUS * 2) + (double)NODE_RADIUS + BUFFER, 
                i*(NODE_RADIUS * 2) + (double)NODE_RADIUS + BUFFER
                ), 
            NODE_RADIUS);
        nodes[i][j] = node;
      }
    }

    System.out.println("nodecols: " + nodeCols);
    System.out.println("intersections: " + intersections);
    System.out.println(nodeCols / (intersections + 1));

    int intersectionSpacing = nodeCols / (intersections + 1);
    int middleRow = nodeRows / 2;

    //enable the middle row, right to left
    for (int i = nodeCols-1; i >= 1; i--) {
      LNode thisNode = nodes[middleRow][i];
      LNode nextNode = nodes[middleRow][i - 1];
      thisNode.setEnabled(true);
      thisNode.setNextNode(nextNode);
    }
    
    //enable the first column from middle up
    for (int i = middleRow; i >= 1; i--) {
      LNode thisNode = nodes[i][0];
      LNode nextNode = nodes[i-1][0];
      thisNode.setEnabled(true);
      thisNode.setNextNode(nextNode);
    }

    //build the intersections
    for (int i = 1; i <= intersections; i++) {
      if (i % 2 == 1) {
        for (
            int j = (i * intersectionSpacing) - intersectionSpacing;  
            j < (i * intersectionSpacing); 
            j++) {
          LNode thisNode = nodes[0][j];
          LNode nextNode = nodes[0][j + 1];
          thisNode.setNextNode(nextNode);
          thisNode.setEnabled(true);
        }
        for (int j = 0; j < nodeRows - 1; j++) {
          LNode thisNode = nodes[j][i*intersectionSpacing];
          LNode nextNode = nodes[j+1][i*intersectionSpacing];
          thisNode.setNextNode(nextNode);
          thisNode.setEnabled(true);
        }
      } else {
        for (
            int j = (i * intersectionSpacing) - intersectionSpacing;
            j < (i * intersectionSpacing);
            j++) {
          LNode thisNode = nodes[nodeRows - 1][j];
          LNode nextNode = nodes[nodeRows - 1][j+1];
          thisNode.setNextNode(nextNode);
          thisNode.setEnabled(true);
        }
        for (int j = nodeRows - 1; j > 0; j--) {
          LNode thisNode = nodes[j][i*intersectionSpacing];
          LNode nextNode = nodes[j-1][i*intersectionSpacing];
          thisNode.setNextNode(nextNode);
          thisNode.setEnabled(true);
        }
      }
    }

    //build the last bit
    for (int i = intersections * intersectionSpacing; i < nodeCols - 1; i++) {
      int row = 0;
      if (intersections % 2 == 1) {
        row = nodeRows - 1;
      } 
      LNode thisNode = nodes[row][i];
      LNode nextNode = nodes[row][i+1];
      thisNode.setNextNode(nextNode);
      thisNode.setEnabled(true);
    }

    if (intersections % 2 == 1) {
      for (int i = nodeRows - 1; i > middleRow; i--) {
        LNode thisNode = nodes[i][nodeCols-1];
        LNode nextNode = nodes[i-1][nodeCols-1];
        thisNode.setNextNode(nextNode);
        thisNode.setEnabled(true);
      }
    } else {
      for (int i = 0; i < middleRow; i++) {
        LNode thisNode = nodes[i][nodeCols-1];
        LNode nextNode = nodes[i+1][nodeCols -1];
        thisNode.setNextNode(nextNode);
        thisNode.setEnabled(true);
      }
    }

  }

  @Override
  public void render() {
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, getWidth(), getHeight());
    graphics.setColor(Color.WHITE);
    for (LNode[] lNodes : nodes) {
      for (LNode lNode : lNodes) {
        lNode.render(graphics);
      }
    }
    synchronized(vehicles) {
      for (LVehicle lVehicle : vehicles) {
        lVehicle.render(graphics);
      }
    }
  }

  @Override
  public void update() {
    for (LNode[] lNodes : nodes) {
      for (LNode lNode : lNodes) {
        lNode.update();
      }
    }
    synchronized(vehicles) {
      for (LVehicle lVehicle : vehicles) {
        lVehicle.update();
      }
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // intersections++;
    // for (LVehicle vehicle : vehicles) {
    //   vehicle.setRunning(false);
    // }
    // vehicles.clear();
    // for (int i = 0; i < nodeRows; i++) {
    //   for (int j = 0; j < nodeCols; j++) {
    //     nodes[i][j].setRunning(false);
    //   }
    // }
    // buildGrid();
    // for (int i = 0; i < STARTING_VEHICLES; i++) {
    //   addVehicle();
    // }
    // super.mouseClicked(e);
    addVehicle();
  }


  
}
