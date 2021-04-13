/*
 * File:         LMouse.java
 * Created Date: 2021-04-13
 * Author:       Brian Lucas
 * Purpose:      Keep track of the mouse
 * -----
 * Last Modified: Tue Apr 13 2021
 * HISTORY:
 * Date        Comments
 * 2021-04-13  initial commit
 */


package lib;

public class LMouse {

  private LVector position;
  private boolean pressed;

  public LMouse() {
    this.position = new LVector(0,0);
    this.pressed = false;
  }

  public LVector getPosition() {
    return position;
  }

  public void setPosition(LVector position) {
    this.position = position;
  }

  public boolean isPressed() {
    return pressed;
  }

  public void setPressed(boolean pressed) {
    this.pressed = pressed;
  }
    
}
