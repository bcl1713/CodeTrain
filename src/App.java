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
import java.awt.event.MouseEvent;

import lib.LDisplay;
import lib.LMover;
import lib.LVector;

public class App extends LDisplay{

    private static final long serialVersionUID = -6015850204183842450L;
    
    private boolean mouseDown;
    private transient LVector mouse;

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
            if (mouseDown) {
                LVector dir = new LVector(
                    mouse.getX() - lMover.getPosition().getX(), 
                    mouse.getY() - lMover.getPosition().getY());
                dir.setMagnitude(0.05);
                lMover.setAcceleration(dir);
            } else {
                lMover.setAcceleration(new LVector(0, 0));
            }
            lMover.update();
            if (lMover.getPosition().getX() > getWidth() - lMover.getSize()) {
                lMover.setVelocity(new LVector(lMover.getVelocity().getX() * -1, lMover.getVelocity().getY()));
                lMover.setPosition(new LVector(getWidth() - lMover.getSize(), lMover.getPosition().getY()));
            }
            
            if (lMover.getPosition().getX() < 0) {
                lMover.setVelocity(new LVector(lMover.getVelocity().getX() * -1, lMover.getVelocity().getY()));
                lMover.setPosition(new LVector(0, lMover.getPosition().getY()));
            }
            
            if (lMover.getPosition().getY() > getHeight() -lMover.getSize()) {
                lMover.setVelocity(new LVector(lMover.getVelocity().getX(), lMover.getVelocity().getY() * -1));
                lMover.setPosition(new LVector(lMover.getPosition().getX(), getHeight() - lMover.getSize()));
            }
            
            if (lMover.getPosition().getY() < 0) {
                lMover.setVelocity(new LVector(lMover.getVelocity().getX(), lMover.getVelocity().getY() * -1));
                lMover.setPosition(new LVector(lMover.getPosition().getX(), 0));
            }
       }
    }

    @Override
    public void start() {
        int numMovers = 30;
        int moverSize = 10;
        
        movers = new LMover[numMovers];
        for (int i = 0; i < numMovers; i++) {
            movers[i] = new LMover(
                new LVector(
                    Math.random() * (getWidth() - 2 * moverSize) + moverSize, 
                    Math.random() * (getHeight() - 2 * moverSize) + moverSize
                ), 
                new LVector(
                    Math.random() * 4 - 2, 
                    Math.random() * 4 - 2
                ),
                new LVector(0, 0)
            );
            movers[i].setSize(moverSize);
            movers[i].setSpeedLimited(true);
            movers[i].setSpeedLimit(5);
        }
        mouse = new LVector(0, 0);
        addMouseMotionListener(this);
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        mouse.setX(e.getX());
        mouse.setY(e.getY());        
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println(e);
        mouse.setX(e.getX());
        mouse.setY(e.getY());
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e);
        this.mouseDown = true;        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        this.mouseDown = false;        
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
