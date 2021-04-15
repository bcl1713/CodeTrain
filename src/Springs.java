
/*
 * File:         Springs.java
 * Created Date: 2021-04-15
 * Author:       Brian Lucas
 * Purpose:      
 * -----
 * Last Modified: Thu Apr 15 2021
 * HISTORY:
 * Date        Comments
 */

import lib.LDisplay;
import lib.LMover;
import lib.LSpring;
import lib.LVector;

import java.awt.Color;

public class Springs extends LDisplay {

  private static final long serialVersionUID = 7558082771920619338L;

  transient LSpring spring;
  transient LMover bob;
  float restLength;

  @Override
  public void render() {
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, getWidth(), getHeight());
    graphics.setColor(Color.GREEN);
    spring.render(graphics); 
    bob.render(graphics);
   }

  @Override
  public void update() {
    spring.requestUpdate();
    
    LVector wind = new LVector(0.2,0);
    
    if (mouse.isPressed()) {
      bob.addForce(wind);
    }

    LVector gravity = new LVector(0, 0.4);
    gravity.scale(bob.getMass());
    bob.addForce(gravity);

    bob.setVelocity(LVector.scale(bob.getVelocity(), 0.99));

    bob.requestUpdate();
  }

  @Override
  public void start() {
    spring = new LSpring(this);
    bob = new LMover(this);
    spring.setRestLength(200);
    spring.setK(0.01);
    spring.setAnchor(new LVector(getWidth() / 2.0, 0));
    bob = new LMover(this);
    bob.setPosition(new LVector(getWidth() / 2.0, 200));
    bob.setMass(2);
    spring.setBob(bob);
  }

  public static void main(String[] args) {
    new Springs();
  }
  
}
