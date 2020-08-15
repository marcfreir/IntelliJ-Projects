package com.marc.main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    public static JFrame frame;
    //For game thread
    private Thread thread;
    private boolean isRunning = true;

    //Dimensions
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    private BufferedImage gameImage;

    public Game() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        gameImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public void initFrame() {
        frame = new JFrame("Against The World v. 0.1");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void startThread() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stopThread() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startThread();
    }

    public void updateGame() {

    }

    public void renderGame() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if(bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics gameGraphics = gameImage.getGraphics();
        gameGraphics.setColor(new Color(0, 0, 0));
        gameGraphics.fillRect(0, 0, WIDTH, HEIGHT);

        //Rendering game
        Graphics2D gameGraphics2D = (Graphics2D) gameGraphics;

        /***/
        gameGraphics.dispose();
        gameGraphics = bufferStrategy.getDrawGraphics();
        gameGraphics.drawImage(gameImage, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        bufferStrategy.show();
    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double nanosecPerAmountOfTicks = 1000000000 / amountOfTicks;
        double delta = 0.0;
        int framesPerSecond = 0;
        double gameTimer = System.currentTimeMillis();

        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nanosecPerAmountOfTicks;
            lastTime = now;

            if(delta >= 1) {
                updateGame();
                renderGame();
                framesPerSecond++;
                delta--;
            }

            if(System.currentTimeMillis() - gameTimer >= 1000) {
                System.out.println("FPS: " + framesPerSecond);
                framesPerSecond = 0;
                gameTimer += 1000;
            }
            //System.out.println("The game is running..."); //Just for debugging
        }
        stopThread();
    }
}
