package com.marc.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

//import com.marc.entities.Player;
import com.marc.main.Game;

public class LifeUI
{
    public void renderLifeUI(Graphics lifeUIGraphics)
    {
        //Losing life
        lifeUIGraphics.setColor(Color.red);
        lifeUIGraphics.fillRect(4, 4, 70, 8);
        //Current life
        lifeUIGraphics.setColor(Color.blue);
        lifeUIGraphics.fillRect(4, 4, (int)((Game.player.playerLife / Game.player.playerMaxLife) * 70), 8);

        //Life in numbers
        lifeUIGraphics.setColor(Color.white);
        lifeUIGraphics.setFont(new Font("Arial", Font.BOLD, 8));

        //Fix the decimal places e.g.: from 1 to 001
        if ((int)Game.player.playerLife < 100)
        {
            if ((int)Game.player.playerLife < 10)
            {
                lifeUIGraphics.drawString(("00" + (int)Game.player.playerLife + "/" + (int)Game.player.playerMaxLife), 26, 11);
            }
            else
            {
                lifeUIGraphics.drawString(("0" + (int)Game.player.playerLife + "/" + (int)Game.player.playerMaxLife), 26, 11);
            }
        }
        else if ((int)Game.player.playerLife == 100)
        {
            lifeUIGraphics.drawString(((int)Game.player.playerLife + "/" + (int)Game.player.playerMaxLife), 26, 11);
        }
        //lifeUIGraphics.drawString(("0" + (int)Game.player.playerLife + "/" + (int)Game.player.playerMaxLife), 26, 11);
    }
}