/*
 * File:         App.java
 * Created Date: 2021-04-11
 * Author:       Brian Lucas
 * Purpose:      Drive the train....
 * -----
 * Last Modified: Mon Apr 12 2021
 * HISTORY:
 * Date        Comments
 * 2021-04-12  Initial Commit
 */


import java.awt.Color;

import lib.LDisplay;
import lib.LMouse;
import lib.LMover;
import lib.LVector;

public class App extends LDisplay{

    private static final long serialVersionUID = -6015850204183842450L;
    
    private transient LMouse mouse = super.getMouse();

    private transient LMover[] movers;


    App(int width, int height) {
        super(width, height);
    }
    public static void main(String[] args) {
        new App(800, 600);
    }


    @Override
    public void render() {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        for (LMover lMover : movers) {
            lMover.render(graphics);
        }
        graphics.drawString(Integer.toString(getFps()), 10, 20);
    }

    @Override
    public void update() {
        for (LMover lMover : movers) {
            if (mouse.isPressed()) {
                LVector dir = LVector.sub(mouse.getPosition(), lMover.getPosition());
                dir.setMagnitude(10);
                lMover.addForce(dir);
            }
            lMover.update();
       }
    }

    @Override
    public void start() {
        super.start();
        int numMovers = 60;
        
        movers = new LMover[numMovers];
        for (int i = 0; i < numMovers; i++) {
            double moverSize = Math.random() * 120 + 1;
            movers[i] = new LMover(
                new LVector(
                    Math.random() * (getWidth() - 2 * moverSize) + moverSize, 
                    Math.random() * (getHeight() - 2 * moverSize) + moverSize
                ), 
                new LVector(
                    Math.random() * 4 - 2, 
                    Math.random() * 4 - 2
                ),
                new LVector(0, 0),
                this
            );
            movers[i].setSize(moverSize);
            movers[i].setMass(moverSize);
            movers[i].setSpeedLimited(true);
            movers[i].setSpeedLimit(10);
        }
    }
}
