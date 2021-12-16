import ScreenRenderer.ScreenRenderer;
import SimulationModel.SimulationModel;

import javax.swing.*;

/**
 * This is a class
 * Created 2021-10-18
 *
 * @author Magnus Silverdal
 */
public class Controller implements Runnable{
    private Thread thread;
    private boolean running = false;
    private int fps = 25;
    private int ups = 4;
    private int width = 400;
    private int height = 400;
    private int scale = 40;
    private JFrame frameNative;
    //private JFrame frameSprite;
    private String title = "";
    private ScreenRenderer viewNative;
    //private ScreenRenderer viewSprite;
    private SimulationModel model;

    public Controller() {
        viewNative = new ScreenRenderer(width,height,scale);
        //viewSprite = new ScreenRenderer(width,height,scale);
        model = new SimulationModel();
        // Frame data
        frameNative = new JFrame(title+"Native");
        frameNative.add(viewNative);
        frameNative.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameNative.setResizable(false);
        frameNative.pack();
        frameNative.setLocationRelativeTo(null);
        frameNative.setVisible(true);
        frameNative.requestFocus();

        //frameSprite = new JFrame(title+"Sprite");
        //frameSprite.add(viewSprite);
        //frameSprite.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frameSprite.setResizable(false);
        //frameSprite.pack();
        //frameSprite.setLocationRelativeTo(null);
        //frameSprite.setVisible(true);
        //frameSprite.requestFocus();
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double nsFPS = 1000000000.0 / fps;
        double nsUPS = 1000000000.0 / ups;
        double deltaFPS = 0;
        double deltaUPS = 0;
        int frames = 0;
        int updates = 0;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            deltaFPS += (now - lastTime) / nsFPS;
            deltaUPS += (now - lastTime) / nsUPS;
            lastTime = now;

            while(deltaUPS >= 1) {
                model.update();
                viewNative.draw(model.getShapes());
                //viewSprite.drawSprites(model.getSprites());
                updates++;
                deltaUPS--;
            }

            while (deltaFPS >= 1) {
                viewNative.render();
                //viewSprite.render();
                frames++;
                deltaFPS--;
            }

            if(System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
                frameNative.setTitle(this.title + "Native  |  " + updates + " ups, " + frames + " fps");
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        c.start();
    }
}