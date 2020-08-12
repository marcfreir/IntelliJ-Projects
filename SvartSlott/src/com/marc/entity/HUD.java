package com.marc.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD
{
    private Player player;

    private BufferedImage hudImage;
    private Font font;

    //Constructor
    public HUD(Player player)
    {
        this.player = player;

        try
        {
            hudImage = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.png"));
            font = new Font("Arial", Font.PLAIN, 14);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void drawHUD(Graphics2D hudGraphics)
    {
        hudGraphics.drawImage(hudImage, 0, 10, null);
        hudGraphics.setFont(font);
        hudGraphics.setColor(Color.RED);
        hudGraphics.drawString(player.getPlayerHealth() + "/" + player.getPlayerMaxHeath(), 30, 25);
        hudGraphics.drawString(player.getPlayerMagicPower() / 100 + "/" + player.getPlayerMaxMagicPower() / 100, 30, 45);
    }
}
