package com.marc.gamestate;

import java.awt.*;
import java.util.ArrayList;

public class GameStateManager
{
    private ArrayList<GameState> gameStates;
    private int currentState;

    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;

    //Constructor
    public GameStateManager()
    {
        gameStates = new ArrayList<GameState>();

        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new Level1State(this));
    }

    public void setGameState(int state)
    {
        currentState = state;
        gameStates.get(currentState).initGameState();
    }

    public void updateGameState()
    {
        gameStates.get(currentState).updateGameState();
    }

    public void drawGameState(Graphics2D gameStateGraphics)
    {
        gameStates.get(currentState).drawGameState(gameStateGraphics);
    }

    public void keyPressed(int key)
    {
        gameStates.get(currentState).keyPressed(key);
    }

    public void keyReleased(int key)
    {
        gameStates.get(currentState).keyReleased(key);
    }
}
