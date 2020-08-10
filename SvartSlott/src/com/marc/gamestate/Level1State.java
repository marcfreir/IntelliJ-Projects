package com.marc.gamestate;

import com.marc.entity.Player;
import com.marc.main.GamePanel;
import com.marc.tilemap.Background;
import com.marc.tilemap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Level1State extends GameState
{
    private TileMap tileMap;
    private Background background;

    private Player player;

    //Constructor
    public Level1State(GameStateManager gameStateManager)
    {
        this.gameStateManager = gameStateManager;
        initGameState();
    }


    @Override
    public void initGameState()
    {
        int tileProportion = 30;
        tileMap = new TileMap(tileProportion);
        tileMap.loadTiles("/Tilesets/terrainTiles.png");
        tileMap.loadMap("/Maps/level1Map.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(1);

        background = new Background("/Backgrounds/level1StateBackgroundDark.png", 0.1);

        player = new Player(tileMap);
        player.setPosition(100, 100);

        //System.out.println("Current player: " + player);
    }

    @Override
    public void updateGameState()
    {
        //Update player
        player.updatePlayer();
        tileMap.setPosition(GamePanel.WIDTH / 2.0 - player.getVectorPositionX(), GamePanel.HEIGHT / 2.0 - player.getVectorPositionY());
    }

    @Override
    public void drawGameState(Graphics2D gameStateGraphics)
    {
        //Clear screen - just for testing the background
        //Color backgroundColor = new Color(2, 0, 5);
        //gameStateGraphics.setColor(backgroundColor);
        //gameStateGraphics.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        //Draw the background
        background.drawBackground(gameStateGraphics);

        //Draw the tilemap
        tileMap.drawTileMap(gameStateGraphics);

        //Draw the player
        player.drawPlayer(gameStateGraphics);
    }

    @Override
    public void keyPressed(int key)
    {
        //Player move to the left
        if (key == KeyEvent.VK_LEFT)
        {
            player.setMovementLeft(true);
        }
        //Player move to the right
        if (key == KeyEvent.VK_RIGHT)
        {
            player.setMovementRight(true);
        }
        //Player move up
        if (key == KeyEvent.VK_UP)
        {
            player.setMovementUp(true);
        }
        //Player move down
        if (key == KeyEvent.VK_DOWN)
        {
            player.setMovementDown(true);
        }
        //Player jumping
        if (key == KeyEvent.VK_A)
        {
            player.setMovementJumping(true);
        }
        //Player flying
        if (key == KeyEvent.VK_S)
        {
            player.setPlayerFlying(true);
        }
        //Player punching
        if (key == KeyEvent.VK_D)
        {
            player.setPlayerPunching();
        }
        //Player Shooting Magic Power
        if (key == KeyEvent.VK_F)
        {
            player.setPlayerShootingMagicPower();
        }
    }

    @Override
    public void keyReleased(int key)
    {
        //Player stop move to the left
        if (key == KeyEvent.VK_LEFT)
        {
            player.setMovementLeft(false);
        }
        //Player stop move to the right
        if (key == KeyEvent.VK_RIGHT)
        {
            player.setMovementRight(false);
        }
        //Player stop move up
        if (key == KeyEvent.VK_UP)
        {
            player.setMovementUp(false);
        }
        //Player stop move down
        if (key == KeyEvent.VK_DOWN)
        {
            player.setMovementDown(false);
        }
        //Player stop jumping
        if (key == KeyEvent.VK_A)
        {
            player.setMovementJumping(false);
        }
        //Player stop flying
        if (key == KeyEvent.VK_S)
        {
            player.setPlayerFlying(false);
        }
    }


}
