package com.marc.main;

import com.marc.gamestate.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener
{
    //Dimensions
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    //Game Thread
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;

    //Image
    private BufferedImage image;
    private Graphics2D gameGraphics;

    //Game State Manager
    private GameStateManager gameStateManager;

    //Constructor
    public GamePanel()
    {
        super();
        setPreferredSize(new Dimension((WIDTH * SCALE), (HEIGHT * SCALE)));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify()
    {
        super.addNotify();

        if (thread == null)
        {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void initGamePanel()
    {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        gameGraphics = (Graphics2D) image.getGraphics();
        running = true;
        gameStateManager = new GameStateManager();
    }

    @Override
    public void keyTyped(KeyEvent event)
    {
        //
    }

    @Override
    public void keyPressed(KeyEvent event)
    {
        gameStateManager.keyPressed(event.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent event)
    {
        gameStateManager.keyReleased(event.getKeyCode());
    }

    @Override
    public void run()
    {
        initGamePanel();

        long start;
        long elapsed;
        long wait;

        //Game Loop
        while (running)
        {
            start = System.nanoTime();

            updateGame();
            drawGame();
            drawToScreen();

            elapsed = System.nanoTime() - start;
            wait = (targetTime - (elapsed / 1000000));

            if (wait < 0)
            {
                wait = 5;
            }

            try
            {
                Thread.sleep(wait);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    private void updateGame()
    {
        gameStateManager.updateGameState();
    }

    private void drawGame()
    {
        gameStateManager.drawGameState(gameGraphics);
    }

    private void drawToScreen()
    {
        Graphics gamePanelObjectsGraphics = getGraphics();
        gamePanelObjectsGraphics.drawImage(image, 0, 0, (WIDTH * SCALE), (HEIGHT * SCALE), null);
        gamePanelObjectsGraphics.dispose();
    }
}
