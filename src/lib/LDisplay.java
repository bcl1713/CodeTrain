/*
 * File:         Display.java
 * Created Date: 2021-04-11
 * Author:       Brian Lucas
 * Purpose:      Create a way to quickly generate a window to draw in
 * -----
 * Last Modified: Sun Apr 11 2021
 * HISTORY:
 * Date        Comments
 * 2021-04-12  Initial commit
 */


package lib;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputListener;

public abstract class LDisplay extends Canvas implements Runnable, MouseInputListener{

  private static final int DEFAULT_WIDTH = 800;
  private static final int DEFAULT_HEIGHT = 600;
  private static final int UPDATES_PER_SECOND = 60;

  public boolean running = true;

  private int fps;

  protected transient BufferStrategy bufferStrategy;
  protected transient Graphics2D graphics;

  /**
   * Generated UID
   */
  private static final long serialVersionUID = -8089968404943202953L;

  protected LDisplay() {
    this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

  protected LDisplay(int width, int height) {
    super();
    setSize(width, height);
    setLocation(0, 0);
    JFrame frame = new JFrame();
    frame.setSize(getWidth()+100, getHeight()+100);
    frame.setResizable(false);
    frame.getContentPane().setLayout(new GridLayout(1,1));
    frame.add(this);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    createBufferStrategy(2);
    bufferStrategy = getBufferStrategy();
    Thread thread = new Thread(this, "Display");
    graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
    this.addMouseListener(this);
    thread.start();
    running = true;
  }

  public abstract void start();


  @Override
  public void run() {
    start();    
    long lastTime = System.nanoTime();
    long timer = System.currentTimeMillis();
    double delta = 0;
    int frames = 0;

    while (running) {
      long now = System.nanoTime();
      delta += (now - lastTime) / (1000000000.0 / UPDATES_PER_SECOND);
      lastTime = now;

      while (delta >= 1) {
        update();
        delta--;
      }

      //graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
    
      render();
      
      bufferStrategy.show();
      //graphics.dispose();

      frames++;

      if (System.currentTimeMillis() - timer > 1000) {
        timer += 1000;
        fps = frames;
        frames = 0;
      }

    }
  }

  public abstract void render(); 

  public abstract void update();

  public int getFps() {
    return fps;
  }
  
}
