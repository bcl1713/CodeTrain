/*
 * File:         LRunnable.java
 * Created Date: 2021-04-15
 * Author:       Brian Lucas
 * Purpose:      Make an runnable abstract
 * -----
 * Last Modified: Thu Apr 15 2021
 * HISTORY:
 * Date        Comments
 */


package lib;

import java.awt.Canvas;
import java.awt.Graphics2D;

public abstract class LRunnable implements Runnable {
  
  Canvas container;

  boolean running = false;
  static final int MAX_UPDATES_PER_SECOND = 120;
  Thread thread;
  private int updatesRequested = 0;

  protected LRunnable(Canvas container) {
    thread = new Thread(this, "LRunnable");
    thread.start();
    this.container = container;
  }

  public abstract void update();

  public abstract void render(Graphics2D graphics);

  public void requestUpdate() {
    updatesRequested++;
  }

  public Thread getThread() {
    return thread;
  }

  public void run() {
    running = true;
    while (running) {
      if (updatesRequested > 0) {
        update();
        updatesRequested--;
      }
      try {
        Thread.sleep(1000 / MAX_UPDATES_PER_SECOND);
      } catch (InterruptedException e) {
        e.printStackTrace();
        thread.interrupt();
        return;
      }
    }
  }

  public void setRunning(boolean running) {
    this.running = running;
  }

  public boolean isRunning() {
    return running;
  }

}
