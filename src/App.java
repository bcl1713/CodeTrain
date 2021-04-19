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
import java.util.ArrayList;

import lib.LDisplay;
import lib.LMover;
import lib.LVector;

public class App extends LDisplay{

    private static final long serialVersionUID = -6015850204183842450L;
    
    private transient ArrayList<LMover> movers = new ArrayList<>();

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
        for (LMover lMover : movers) {
            for (LMover lMover2 : movers) {
                LVector pull = LVector.sub(lMover2.getPosition(), lMover.getPosition());
                pull.setMagnitude(0.0008);
                lMover.addForce(pull);
            }
            lMover.requestUpdate();
        }
    }

    @Override
    public void start() {
        int numMovers = 128;
        for (int i = 0; i < numMovers; i++) {
            movers.add(new LMover(this));
            movers.get(i).setPosition(new LVector(
                Math.random() * getWidth(),
                Math.random() * getHeight()
            ));
            movers.get(i).setVelocity(new LVector(
                Math.random() * 4 - 2, 
                Math.random() * 4 - 2
                ));
            movers.get(i).setEdgeBehavior(LMover.WRAP);
            movers.get(i).setMaxVelocity(4);
            movers.get(i).setSize(Math.random() * 32 + 32);
            movers.get(i).setMass(movers.get(i).getSize() / 8);
            System.out.println(movers.get(i).getMass());
        }
    }
}
