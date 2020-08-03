package com.marc.gamestate;

public abstract class GameState
{
    protected GameStateManager gameStateManager;

    public abstract void init();
    public abstract void updateGameState();
    public abstract void drawGameState(java.awt.Graphics2D gameStateGraphics);
    public abstract void keyPressed(int key);
    public abstract void keyReleased(int key);
}
