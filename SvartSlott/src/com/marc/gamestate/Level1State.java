package com.marc.gamestate;

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
        tileMap = new TileMap(tileSize);
    }

    @Override
    public void updateGameState()
    {

    }

    @Override
    public void drawGameState(Graphics2D gameStateGraphics)
    {

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
