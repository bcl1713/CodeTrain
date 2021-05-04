
/*
 * File:         Test.java
 * Created Date: 2021-04-17
 * Author:       Brian Lucas
 * Purpose:      
 * -----
 * Last Modified: Sat Apr 17 2021
 * HISTORY:
 * Date        Comments
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


import lib.LDisplay;
import lib.LMover;
import lib.LPath;
import lib.LVector;
import lib.LVehicle;


public class Test extends LDisplay {

  /**
   * Generated seralVersionUID
   */
  private static final long serialVersionUID = -1570645570118871214L;

  private transient List<LVehicle> movers;
  private transient LPath path;
  private Test container;

  public Test(int w, int h) {
    super(w, h);
    movers = Collections.synchronizedList(new ArrayList<LVehicle>());
  }

  public static void main(String[] args) {
    new Test(800, 600);
  }

  @Override
  public void buildInterface() {
    JButton button = new JButton("Add Mover");
    button.addActionListener(e -> addVehicle());
    
    this.setLocation(0, 0);
    this.setSize(myWidth, myHeight);

    JPanel panel = new JPanel();
    panel.setSize(200, getHeight());
    panel.setLocation(getWidth(), 0);
    panel.add(button);

    JFrame frame = new JFrame();
    frame.setSize(getWidth() + 200, getHeight());
    frame.setResizable(false);
    frame.getContentPane().setLayout(null);
    this.setLocation(0, 0);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.addMouseListener(this);
    this.addMouseMotionListener(this);

    
    
    
    
    frame.add(this);
    frame.add(panel);

    createBufferStrategy(2);
    bufferStrategy = getBufferStrategy();
    graphics = (Graphics2D) bufferStrategy.getDrawGraphics();

    container = this;

  }

  @Override
  public void start() {
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, myWidth, myHeight);
    
    for (int i = 0; i < 300; i++) {
     addVehicle();
    }

    path = new LPath();
    path.addPoint(new LVector(100, 100));
    path.addPoint(new LVector(700, 100));
    path.addPoint(new LVector(700, 500));
    path.addPoint(new LVector(100, 500));
    path.addPoint(new LVector(100, 100));
    path.setRadius(6);
  
  }

  private void addVehicle(){
    LVehicle mover = new LVehicle(
      container,
      Math.random() * myWidth,
      Math.random() * myHeight
    );
    mover.setVelocity(
      new LVector(
        Math.random() * 4 - 2, 
        Math.random() * 4 - 2));
    mover.setEdgeBehavior(LMover.WRAP);
    mover.setMaxAcceleration(0.3);
    mover.setMaxVelocity(3);
    mover.setSize(12);
    movers.add(mover);
  }

  @Override
  public void render() {
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, myWidth, myHeight);
    path.render(graphics);
    graphics.setColor(Color.WHITE);
    synchronized (movers) {
      for (LMover lMover : movers) {
        lMover.render(graphics);
      }
    }
    graphics.setColor(Color.GREEN);
    graphics.drawString(Integer.toString(movers.size()) + " " + getFps(), 10, 20);  
  }



  @Override
  public void update() {
    synchronized (movers) {
      for (LVehicle lMover : movers) {
        lMover.requestUpdate();
      }
    }
  }

}
