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
import lib.LPendulum;

public class App extends LDisplay{

    private static final long serialVersionUID = -6015850204183842450L;
    
    private transient LPendulum pendulum;

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

        pendulum.render(graphics);
        
        graphics.drawString(Integer.toString(getFps()), 10, 20);
    }

    @Override
    public void update() {
        pendulum.requestUpdate();
    }

    @Override
    public void start() {
        pendulum = new LPendulum(this);
        pendulum.getThread().start();
    }
}
