package com.marc.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.marc.entities.Enemy;
import com.marc.entities.Entity;
import com.marc.entities.Player;
import com.marc.entities.BulletShoot;
import com.marc.graphics.LifeUI;
import com.marc.graphics.Spritesheet;
import com.marc.sounds.Sound;
import com.marc.world.World;

/**
 * Graphics
 */
public class Game extends Canvas implements Runnable, KeyListener, MouseListener
{


    private static final long serialVersionUID = 1L;

    public static JFrame frame;

    private Thread thread;

    private boolean isRunning = true;

    public static final int WIDTH = 240;
    public static final int HEIGHT = 160;
    public static final int SCALE = 3;

    private int CURRENT_LEVEL = 1;
    private int MAX_LEVEL = 2;

    private BufferedImage image;

    public static List<Entity> entities;

    public static List<Enemy> enemies;

    public static List<BulletShoot> bullets;

    public static Spritesheet spritesheet;

    public static World world;

    public static Player player;

    public static Random random;

    public LifeUI lifeUI;

    public static String gameState = "MENU";

    private boolean showMessageGameOver = true;
    private int gameOverFrames = 0;

    private boolean restartGame = false;

    public Menu menu;

    // Constructor
    public Game() {
        Sound.musicbg.loop();
        //Sound.musicBackground.loop();
        random = new Random();
        addKeyListener(this);
        addMouseListener(this);
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        //Starting objects
        lifeUI = new LifeUI();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        entities = new ArrayList<Entity>();
        enemies = new ArrayList<Enemy>();
        bullets = new ArrayList<BulletShoot>();

        spritesheet = new Spritesheet("/spriteSheet.png");
        //Based on the spriteSheetNewPosition.png File - set the coordinates in getSprite
        player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
        entities.add(player);
        world = new World("/level1.png");

        menu = new Menu();
    }

    public void initFrame()
    {
        frame = new JFrame("Game #PlatformerOne");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        // Centered window
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void startGame()
    {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
        //Sound.musicbg.loop();
    }

    public synchronized void stopGame()
    {
        isRunning = false;
        try
        {
            thread.join();
        }
        catch (InterruptedException exception)
        {
            exception.printStackTrace();
            //Thread.currentThread().interrupt();
        }
    }


    public static void main(String[] args)
    {
        Game game = new Game();
        game.startGame();
    }

    public void updateGame()
    {
        //Check game State
        if (gameState.equals("NORMAL"))
        {
            this.restartGame = false;

            for (Entity player : entities) {
                //Entity entity = entities.get(index);
                //entity.updateEntity();
                //Changed to player - return to the original code above in case of errors
                player.updateEntity();
            }

            for (BulletShoot bullet : bullets) {
                bullet.updateEntity();
            }

            checkIfEnemiesHaveBeenDestroyed();
        }
        else if (gameState.equals("GAME_OVER"))
        {
            //System.out.println("Game Over!"); <-//Just for debugging
            this.gameOverFrames++;

            if (this.gameOverFrames == 20)
            {
                this.gameOverFrames = 0;

                if (this.showMessageGameOver)
                {
                    this.showMessageGameOver = false;
                }
                else
                {
                    this.showMessageGameOver = true;
                }
            }

            if (restartGame)
            {
                this.restartGame = false;
                gameState = "NORMAL";
                CURRENT_LEVEL = 1;
                String newWorld = ("Level" + CURRENT_LEVEL + ".png");
                System.out.println(newWorld);
                World.restartGame(newWorld);
            }
        }
        else if (gameState.equals("MENU"))
        {
            //Start Menu
            menu.updateMenu();
        }
    }

    public void checkIfEnemiesHaveBeenDestroyed()
    {
        if (enemies.size() == 0)
        {
            //System.out.println("Next Level!"); <-//Just for debugging
            //Move on to the next level
            CURRENT_LEVEL++;

            if (CURRENT_LEVEL > MAX_LEVEL)
            {
                CURRENT_LEVEL = 1;
            }

            String newWorld = ("Level" + CURRENT_LEVEL + ".png");
            System.out.println(newWorld);
            World.restartGame(newWorld);
        }
    }

    public void renderGame()
    {
        //Sound.musicbg.loop();
        //To organize complex memory on the Canvas
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        //To draw onto Canvas
        Graphics gameGraphics = image.getGraphics();
        gameGraphics.setColor(new Color(19,19,19));
        gameGraphics.fillRect(0, 0, WIDTH, HEIGHT);

        //Game Rendering
        //Graphics2D graphic2D = (Graphics2D) gameGraphics;
        world.renderWorld(gameGraphics);
        for (Entity entity : entities) {
            entity.renderEntity(gameGraphics);
        }

        for (BulletShoot bullet : bullets) {
            bullet.renderEntity(gameGraphics);
        }

        lifeUI.renderLifeUI(gameGraphics);

        /***/
        gameGraphics.dispose();
        gameGraphics = bs.getDrawGraphics();
        gameGraphics.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        gameGraphics.setFont(new Font("Arial", Font.BOLD, 17));
        gameGraphics.setColor(Color.white);
        gameGraphics.drawString("Ammo:   " + player.ammo, ((WIDTH * SCALE) - 120), ((HEIGHT * SCALE) - ((HEIGHT * SCALE) - 30)));

        if (gameState.equals("GAME_OVER"))
        {
            Graphics2D gameOverScreenGraphics = (Graphics2D) gameGraphics;
            gameOverScreenGraphics.setColor(new Color( 0, 0, 0, 100));
            gameOverScreenGraphics.fillRect(0, 0, (WIDTH * SCALE), (HEIGHT * SCALE));

            //Game Over Message
            gameGraphics.setFont(new Font("Arial", Font.BOLD, 30));
            gameGraphics.setColor(Color.white);
            gameGraphics.drawString("GAME OVER!", (((WIDTH * SCALE)) / 2) - 100, ((HEIGHT * SCALE) / 2));

            //Press Enter Message
            gameGraphics.setFont(new Font("Arial", Font.BOLD, 24));
            gameGraphics.setColor(Color.white);

            if (showMessageGameOver)
            {
                gameGraphics.drawString("[Press ENTER to ReStart the Game]", (((WIDTH * SCALE)) / 2) - 200, ((HEIGHT * SCALE) / 2) + 50);
            }
        }
        else if (gameState.equals("MENU"))
        {
            menu.renderMenu(gameGraphics);
        }

        bs.show();
    }

    @Override
    public void run()
    {
        long lastTime = System.nanoTime();
        double amountOfBeats = 60.0;
        double nanosecPerAmountOfBeats = 1000000000 / amountOfBeats;
        double delta = 0;
        int framesPerSecond = 0;
        double timer = System.currentTimeMillis();
        requestFocus();
        while (isRunning)
        {
            long now = System.nanoTime();
            delta+= (now - lastTime) / nanosecPerAmountOfBeats;
            lastTime = now;

            if (delta >= 1)
            {
                updateGame();
                renderGame();
                framesPerSecond++;
                delta--;
            }

            if ((System.currentTimeMillis() - timer) >= 1000)
            {
                System.out.println("FPS: " + framesPerSecond);
                framesPerSecond = 0;
                timer += 1000;
            }
            /**System.out.println("My game is running!...");*/
        }
        stopGame();

    }

    @Override
    public void keyTyped(KeyEvent event)
    {
        //TODO
    }

    @Override
    public void keyPressed(KeyEvent event)
    {
        //Keyboard Event Directions - Move
        //Right
        if (event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D)
        {
            player.playerRight = true;
            System.out.println("Right Direction Key - Moved");
        }
        //Left
        else if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A)
        {
            player.playerLeft = true;
            System.out.println("Left Direction Key - Moved");
        }
        //Up
        if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_W)
        {
            player.playerUp = true;
            System.out.println("Up Direction Key - Moved");

            if (gameState.equals("MENU"))
            {
                menu.menuUp = true;
            }
        }
        //Down
        else if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S)
        {
            player.playerDown = true;
            System.out.println("Down Direction Key - Moved");

            if (gameState.equals("MENU"))
            {
                menu.menuDown = true;
            }
        }

        //Shoot
        if (event.getKeyCode() == KeyEvent.VK_X)
        {
            player.shootWithKeyboard = true;
        }

        //Restart Game
        if (event.getKeyCode() == KeyEvent.VK_ENTER)
        {
            this.restartGame = true;

            if (gameState.equals("MENU"))
            {
                menu.menuEnter = true;
            }
        }

        //Pause Game
        if (event.getKeyCode() == KeyEvent.VK_SPACE || event.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            gameState = "MENU";
            menu.pauseGame = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent event)
    {
        //Keyboard Event Directions - Stop
        //Right
        if (event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D)
        {
            player.playerRight = false;
            System.out.println("Right Direction Key - Stoped");
        }
        //Left
        else if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A)
        {
            player.playerLeft = false;
            System.out.println("Left Direction Key - Stoped");
        }
        //Up
        if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_W)
        {
            player.playerUp = false;
            System.out.println("Up Direction Key - Stoped");
        }
        //Down
        else if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S)
        {
            player.playerDown = false;
            System.out.println("Down Direction Key - Stoped");
        }

    }

    //Mouser Controller (MouseListner)

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent event) {
        player.shootWithMouse = true;
        player.mousePositionX = (event.getX() / SCALE);
        player.mousePositionY = (event.getY() / SCALE);

        //System.out.println(player.mousePositionX); <-//Just for debugging
        //System.out.println(player.mousePositionY); <-//Just for debugging

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

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
