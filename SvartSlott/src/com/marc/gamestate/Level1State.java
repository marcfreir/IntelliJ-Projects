package com.marc.gamestate;

import com.marc.main.GamePanel;
import com.marc.tilemap.TileMap;

import java.awt.*;

public class Level1State extends GameState
{
    private TileMap tileMap;

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
    }

    @Override
    public void updateGameState()
    {

    }

    @Override
    public void drawGameState(Graphics2D gameStateGraphics)
    {
        //Clear screen
        gameStateGraphics.setColor(Color.BLACK);
        gameStateGraphics.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        //Draw the tilemap
        tileMap.drawTileMap(gameStateGraphics);
    }

    @Override
    public void keyPressed(int key)
    {

    }

    @Override
    public void keyReleased(int key)
    {

    }


}
