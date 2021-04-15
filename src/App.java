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
import lib.LMover;
import lib.LUtilities;
import lib.LVector;

public class App extends LDisplay{

    private static final long serialVersionUID = -6015850204183842450L;
    
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
        
        graphics.setColor(Color.GREEN);

        for (LMover lMover : movers) {
            lMover.render(graphics);    
        }
        
        graphics.drawString(Integer.toString(getFps()), 10, 20);
    }

    @Override
    public void update() {
        for (int i = 0; i < movers.length; i++) {
            for (int j = 0; j < movers.length; j++) {
                LVector dif = LVector.sub(
                    movers[j].getPosition(), 
                    movers[i].getPosition()
                );
                dif.setMagnitude(0.00075);
                movers[i].addForce(dif);
                movers[i].setSize(movers[i].getSize() + 0.00001);
                movers[i].setMass(movers[i].getSize() / 16);
            }
        }
        // lMover.update();
    }

    @Override
    public void start() {
        int numMovers = 500;
        movers = new LMover[numMovers];
        for (int i = 0; i < numMovers; i++) {
            movers[i] = new LMover(this);
            movers[i].setPosition(
                new LVector(
                    LUtilities.map(Math.random(), 0, 1, 0, getWidth()), 
                    LUtilities.map(Math.random(), 0, 1, 0, getHeight())
                )
            );
            movers[i].setVelocity(
                new LVector(
                    Math.random() * 2 - 1,
                    Math.random() * 2 - 1)
                );
            movers[i].setSpeedLimited(true);
            movers[i].setSpeedLimit(4);
            movers[i].getThread().start();
            movers[i].setColor(new Color(

                (int)(Math.random() * 255),
                (int)(Math.random() * 255),
                (int)(Math.random() * 255)
            ));
            movers[i].setSize((Math.random() * 24) + 24);
            movers[i].setMass(movers[i].getSize() / 16);
            movers[i].setEdgeBehavior(LMover.WRAP);
        }
        
    }
}
