package com.marc.main;

import com.marc.entities.Entity;
import com.marc.entities.Player;
import com.marc.graphics.SpriteSheet;
import com.marc.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable, KeyListener {

    public static JFrame frame;
    //For game thread
    private Thread thread;
    private boolean isRunning = true;

    //Dimensions
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    private BufferedImage gameImage;

    public List<Entity> entityList;
    public static SpriteSheet spriteSheet;

    public static World world;

    private Player player;

    public Game() {
        addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        //Starting objects
        world = new World("/Map/mapStage01.png");
        gameImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        entityList = new ArrayList<Entity>();
        spriteSheet = new SpriteSheet("/SpriteSheet/spriteSheet.png");

        player = new Player(0, 0, 40, 40, spriteSheet.getSpritesheet(0, 160, 40, 40));
        entityList.add(player);

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
        for(int indexEntity = 0; indexEntity < entityList.size(); indexEntity++) {
            Entity entity = entityList.get(indexEntity);
            entity.updateEntity();
        }
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
        //Graphics2D gameGraphics2D = (Graphics2D) gameGraphics;

        world.renderWorld(gameGraphics);

        for(int indexEntity = 0; indexEntity < entityList.size(); indexEntity++) {
            Entity entity = entityList.get(indexEntity);
            entity.renderEntity(gameGraphics);
        }

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
                //System.out.println("FPS: " + framesPerSecond); //Just for debugging
                framesPerSecond = 0;
                gameTimer += 1000;
            }
            //System.out.println("The game is running..."); //Just for debugging
        }
        stopThread();
    }

    @Override
    public void keyTyped(KeyEvent event) {
        //
    }

    @Override
    public void keyPressed(KeyEvent event) {
        //Move to the right
        if(event.getKeyCode() == KeyEvent.VK_RIGHT ||
        event.getKeyCode() == KeyEvent.VK_D) {
            //System.out.println("Key Right"); //Just for debugging
            player.playerDirectionRight = true;
        }
        //Move to the left
        else if(event.getKeyCode() == KeyEvent.VK_LEFT ||
                event.getKeyCode() == KeyEvent.VK_A) {
            //System.out.println("Key Left"); //Just for debugging
            player.playerDirectionLeft = true;
        }
        //Move upward
        if(event.getKeyCode() == KeyEvent.VK_UP ||
                event.getKeyCode() == KeyEvent.VK_W) {
            //System.out.println("Key Up"); //Just for debugging
            player.playerDirectionUp = true;
        }
        //Move downward
        else if(event.getKeyCode() == KeyEvent.VK_DOWN ||
                event.getKeyCode() == KeyEvent.VK_S) {
            //System.out.println("Key Down"); //Just for debugging
            player.playerDirectionDown = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        //Stop moving to the right
        if(event.getKeyCode() == KeyEvent.VK_RIGHT ||
                event.getKeyCode() == KeyEvent.VK_D) {
            //System.out.println("Key Right"); //Just for debugging
            player.playerDirectionRight = false;
        }
        //Stop moving to the left
        else if(event.getKeyCode() == KeyEvent.VK_LEFT ||
                event.getKeyCode() == KeyEvent.VK_A) {
            //System.out.println("Key Left"); //Just for debugging
            player.playerDirectionLeft = false;
        }
        //Stop moving upward
        if(event.getKeyCode() == KeyEvent.VK_UP ||
                event.getKeyCode() == KeyEvent.VK_W) {
            //System.out.println("Key Up"); //Just for debugging
            player.playerDirectionUp = false;
        }
        //Stop moving downward
        else if(event.getKeyCode() == KeyEvent.VK_DOWN ||
                event.getKeyCode() == KeyEvent.VK_S) {
            //System.out.println("Key Down"); //Just for debugging
            player.playerDirectionDown = false;
        }
    }
}
