package com.marc.gamestate;

import com.marc.tilemap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState
{
    private Background menuStateBackground;
    private int currentChoice = 0;
    private String[] options = {"Start", "Help", "Quit"};
    private Color titleColor;
    private Font titleFont;
    private Font regularFont;

    //Constructor
    public MenuState(GameStateManager gameStateManager)
    {
        this.gameStateManager = gameStateManager;

        try
        {
            menuStateBackground = new Background("/Backgrounds/menubackground.png", 1);
            menuStateBackground.setBackgroundVector(-0.1, 0);

            titleColor = new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            regularFont = new Font("Arial", Font.PLAIN, 12);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public void initGameState()
    {

    }

    @Override
    public void updateGameState()
    {
        menuStateBackground.updateBackground();
    }

    @Override
    public void drawGameState(Graphics2D gameStateGraphics)
    {
        //Draw the background
        menuStateBackground.drawBackground(gameStateGraphics);

        //Draw the title
        gameStateGraphics.setColor(titleColor);
        gameStateGraphics.setFont(titleFont);
        gameStateGraphics.drawString("Svart Slott", 80, 70);

        //Draw the menu options
        gameStateGraphics.setFont(regularFont);

        for (int index = 0; index < options.length; index++)
        {
            if (index == currentChoice)
            {
                gameStateGraphics.setColor(Color.BLACK);
            }
            else
            {
                gameStateGraphics.setColor(Color.RED);
            }

            gameStateGraphics.drawString(options[index], 145, 140 + (index * 15));
        }
    }

    private void selectOption()
    {
        if (currentChoice == 0)
        {
            //(Start)
            gameStateManager.setGameState(GameStateManager.LEVEL1STATE);
        }
        if (currentChoice == 1)
        {
            //Do something (Help)
        }
        if (currentChoice == 2)
        {
            //(Quit)
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int key)
    {
        if (key == KeyEvent.VK_ENTER)
        {
            selectOption();
        }
        if (key == KeyEvent.VK_UP)
        {
            currentChoice--;

            if (currentChoice == -1)
            {
                currentChoice = options.length - 1;
            }
        }
        if (key == KeyEvent.VK_DOWN)
        {
            currentChoice++;

            if (currentChoice == options.length)
            {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int key)
    {

    }
}
